package net.ivanz.staffAccountingSystem.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.ivanz.staffAccountingSystem.integration.UserRoles;

@Data
@NoArgsConstructor
public class UserInDTO {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private UserRoles role;
}
