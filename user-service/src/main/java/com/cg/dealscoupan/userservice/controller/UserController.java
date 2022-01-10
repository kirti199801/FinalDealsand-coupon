package com.cg.dealscoupan.userservice.controller;

import com.cg.dealscoupan.userservice.client.Coupon;
import com.cg.dealscoupan.userservice.client.CouponServiceClient;
import com.cg.dealscoupan.userservice.entity.ClientType;
import com.cg.dealscoupan.userservice.entity.Company;
import com.cg.dealscoupan.userservice.entity.Customer;
import com.cg.dealscoupan.userservice.entity.UserProfile;
import com.cg.dealscoupan.userservice.exceptions.CustomException;
import com.cg.dealscoupan.userservice.jwt.AuthenticationRequest;
import com.cg.dealscoupan.userservice.jwt.AuthenticationResponse;
import com.cg.dealscoupan.userservice.jwt.JwtUtils;
import com.cg.dealscoupan.userservice.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@CrossOrigin
public class UserController {

    private final UserProfileService userProfileService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;
    private final CouponServiceClient couponServiceClient;

    @PostMapping(value = "/createNewCustomer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfile> addNewCustomer(@RequestBody UserProfile userProfile) {
        userProfile.setRole("ROLE_USER");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userProfileService.addNewUserProfile(userProfile));
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws CustomException {
        UserProfile login = userProfileService.login(authenticationRequest);
        if (login != null) {
            String token = jwtUtils.generateToken(authenticationRequest, authenticationRequest.getClientType());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new AuthenticationResponse(token, authenticationRequest.getClientType(), login.getId()));
        } else {
            return new ResponseEntity<String>("Invalid Email or Password...", HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping(value = "/getAllCompanies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Company>> getAllUserProfiles() {
        List<UserProfile> allCompanies = userProfileService.getAllCompanies(ClientType.Company);
        List<Company> collect = allCompanies.stream().map(userProfile -> new Company(
                userProfile.getFullName(), userProfile.getEmail(), userProfile.getPassword())).collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }

    @PostMapping("/addCompany")
    public void addCompany(@RequestBody Company company) throws CustomException {
        userProfileService.addNewUserProfile(UserProfile
                .builder()
                .email(company.getEmail())
                        .password(encoder.encode(company.getPassword()))
                        .fullName(company.getName())
                        .clientType(ClientType.Company)
                .build());
    }

    @PostMapping("/addCustomer")
    public void addCustomer(@RequestBody Customer customer) throws CustomException {
        userProfileService.addNewUserProfile(UserProfile
                .builder()
                .email(customer.getEmail())
                .password(encoder.encode(customer.getPassword()))
                .fullName(customer.getFirstName() +' ' + customer.getLastName())
                .clientType(ClientType.Customer)
                .build());
    }

    @GetMapping(value = "/getAllCustomers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Customer>> getAlCustomers(@RequestHeader("Authorization") String token) {
        List<UserProfile> allCompanies = userProfileService.getAllCompanies(ClientType.Customer);
        List<Customer> collect = allCompanies
                .stream()
                .map(userProfile -> new Customer(
                        userProfile.getId(),
                        userProfile.getFullName().split("\\s+")[0],
                        userProfile.getFullName().split("\\s+")[1],
                        userProfile.getEmail(),
                        userProfile.getPassword(),
                        couponServiceClient.getCustomerCoupon(token, userProfile.getId()).getBody()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }


    @GetMapping(value = "/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> getById(@RequestHeader("Authorization") String token,
                                            @PathVariable ("customerId") String customerId ) {
        UserProfile userProfile = userProfileService.getById(customerId);

        return ResponseEntity.ok(new Customer(
                userProfile.getId(),
                userProfile.getFullName().split("\\s+")[0],
                userProfile.getFullName().split("\\s+")[1],
                userProfile.getEmail(),
                userProfile.getPassword(),
                couponServiceClient.getCustomerCoupon(token, userProfile.getId()).getBody()));
    }

    @GetMapping(value = "/mobile/{mobilePhone}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfile> getAllByMobilePhone(@RequestParam (value = "mobilePhone") long mobilePhone ) {
        return ResponseEntity.ok(userProfileService.getByMobileNumber(mobilePhone));
    }

    @GetMapping(value = "/username/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfile> getByName(@RequestParam (value = "userName") String userName ) {
        return ResponseEntity.ok(userProfileService.getByUserName(userName));
    }

    @GetMapping(value = "/company/{companyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Company> getByCompanyId(@RequestHeader("Authorization") String token,
                                                  @PathVariable("companyId") String companyId ) {
        UserProfile userProfile = userProfileService.getByCompanyId(companyId).get();

        ResponseEntity<List<Coupon>> companyCoupon = couponServiceClient.getCompanyCoupon(token, companyId);


        return ResponseEntity.ok(new Company(userProfile.getId(), userProfile.getFullName(), userProfile.getEmail(), userProfile.getPassword(), companyCoupon.getBody()));
    }


    @PutMapping(value = "/updateProfile", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateProfile(@RequestBody UserProfile userProfile) {
        userProfileService.updateUserProfile(userProfile);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/delete/{profileId}")
    public ResponseEntity<Void> deleteProfile(@RequestParam(value = "profileId") int profileId) {
        userProfileService.deleteUserProfile(profileId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
