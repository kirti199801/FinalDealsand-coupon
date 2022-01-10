package com.cg.dealscoupan.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "userProfile")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserProfile {
    @Id
    private String id;

    private int profileId;
    private String fullName;
   
    private String email;
   
   
    private String role;
    private String password;
    private ClientType clientType;

   
}
