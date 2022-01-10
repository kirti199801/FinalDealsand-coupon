package com.cg.dealscoupan.userservice.service;

import com.cg.dealscoupan.userservice.dao.UserProfileRepository;
import com.cg.dealscoupan.userservice.entity.ClientType;
import com.cg.dealscoupan.userservice.entity.UserProfile;
import com.cg.dealscoupan.userservice.exceptions.CustomException;
import com.cg.dealscoupan.userservice.jwt.AuthenticationRequest;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder encoder;

    public UserProfile addNewUserProfile(UserProfile userProfile) {
        userProfile.setProfileId(getNextProfileId());
        return userProfileRepository.save(userProfile);
    }

    public UserProfile login(AuthenticationRequest authenticationRequest) throws CustomException {
        if(authenticationRequest.getClientType() == ClientType.Administrator) {
            if(authenticationRequest.getEmail().equals("admin@admin.com") && authenticationRequest.getPassword().equals("administrator")) {
                return UserProfile.builder().email(authenticationRequest.getEmail()).password(authenticationRequest.getPassword()).build();
            }
        }
        Optional<UserProfile> byEmail = userProfileRepository.findByEmail(authenticationRequest.getEmail());
        if (byEmail.isPresent()) {
            if (encoder.matches(authenticationRequest.getPassword(), byEmail.get().getPassword())) {
                return byEmail.get();
            } else {
                throw new CustomException("Invalid email or password");
            }
        }  else {
            throw new CustomException("Invalid email or password");
        }
    }
    public List<UserProfile> getAllUserProfiles() {
        return userProfileRepository.findAll();
    }

    public List<UserProfile> getAllCompanies(ClientType clientType) {
        return userProfileRepository.findAllByClientType(clientType);
    }
    public UserProfile getByProfileId(int profileId) {
        return userProfileRepository.findByProfileId(profileId).orElse(UserProfile.builder().build());
    }

    public UserProfile getByMobileNumber(long mobileNumber) {
        return userProfileRepository.findByMobileNumber(mobileNumber).orElse(UserProfile.builder().build());
    }

    public UserProfile getByUserName(String fullName) {
        return userProfileRepository.findByFullName(fullName).orElse(UserProfile.builder().build());
    }

    public void updateUserProfile(UserProfile userProfile) {
        userProfileRepository.save(userProfile);
    }

    public void deleteUserProfile(int profileId) {
        Optional<UserProfile> byProfileId = userProfileRepository.findByProfileId(profileId);
        if (byProfileId.isPresent()) {
            userProfileRepository.delete(byProfileId.get());
        }
    }

    @Synchronized
    public int getNextProfileId() {
        UserProfile userProfile = userProfileRepository.findTopByOrderByProfileIdDesc();
        int profileId = (userProfile ==null || userProfile.getProfileId() <= 0) ? 0 : userProfile.getProfileId();
        return ++profileId;
    }

    public Optional<UserProfile> getByCompanyId(String companyId) {
        return userProfileRepository.findById(companyId);
    }

    public UserProfile getById(String customerId) {
        return userProfileRepository.findById(customerId).get();
    }
}
