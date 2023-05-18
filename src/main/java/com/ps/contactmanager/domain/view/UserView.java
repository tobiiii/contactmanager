package com.ps.contactmanager.domain.view;

import com.ps.contactmanager.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserView {


    private Long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private Boolean isLocked;
    private ProfileView profile;

    private String defaultPass;


    public UserView(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.emailAddress = user.getEmailAddress();
        this.profile = new ProfileView(user.getProfile());
    }

}
