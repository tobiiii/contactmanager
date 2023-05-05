package com.ps.contactmanager.domain.view;

import com.ps.contactmanager.domain.Privilege;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivilegeView {

    private Long id;

    private String description;

    private String code;

    private Boolean status;

    public PrivilegeView(Privilege privilege, Boolean status) {
        this.id = privilege.getId();
        this.description = privilege.getDescription();
        this.status = status;
        this.code = privilege.getCode();
    }

    public PrivilegeView(Privilege privilege) {
        this.id = privilege.getId();
        this.description = privilege.getDescription();
        this.code = privilege.getCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrivilegeView that = (PrivilegeView) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
