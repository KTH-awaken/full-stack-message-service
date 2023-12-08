package com.example.messageservice.View.ViewModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountVm {
    private String firstName;
    private String lastName;
    private String email;
    private UserType userType;
    private String token;
}
