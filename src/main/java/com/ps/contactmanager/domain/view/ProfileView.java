package com.ps.contactmanager.domain.view;

import com.ps.contactmanager.domain.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileView {

    private Long id;

    private String code;

    private String name;

    private Date created;
    private Date updated;
    @Size(min = 1, message = "Privilèges {REQUIRED}")
    private List<PrivilegeView> privileges;



    public ProfileView(Profile profile) {
        this.id = profile.getId();
        this.code = profile.getCode();
        this.name = profile.getName();
        this.created = profile.getCreated();
        this.updated = profile.getUpdated();
        this.privileges = profile.getPrivileges().stream().map(PrivilegeView::new).collect(Collectors.toList());
    }

}
