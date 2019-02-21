package net.ivanz.staffAccountingSystem.models;

import lombok.Data;
import net.ivanz.staffAccountingSystem.integration.UserRoles;
import net.ivanz.staffAccountingSystem.utils.AuthHelper;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Document(collection = "users")
@Data
public class User implements UserDetails {
    @Id
    private String id;
    private String username;
    private String password;
    private String[] grantedAuthorities;
    private String email;
    private String firstName;
    private String lastName;

    /**
     * Creating user from Users Data Transfer Object
     * @param data DTO used to create a user
     * @return User created from DTO
     */
    public static User of(UserInDTO data) {
        return new User(data.getUsername(), data.getPassword(), data.getRole(), data.getEmail(), data.getFirstName(),
                data.getLastName());
    }

    /**
     * Update Users fields by Users DTO
     * @param fromDb User that got new data
     * @param data User than gave this data
     * @return User with updated field
     */
    public static User merge(User fromDb, UserInDTO data) {
        fromDb.setEmail(data.getEmail());
        fromDb.setFirstName(data.getFirstName());
        fromDb.setLastName(data.getLastName());
        fromDb.setGrantedAuthorities(new String[] {data.getRole().name()});
        return fromDb;
    }

    /**
     * Constructor, that create new User from username, password, role, email, first name and last name
     * @param username user login
     * @param password user password
     * @param grantedAuthorities user roles
     * @param email user email
     * @param firstName user first name
     * @param lastName user last name
     */
    public User(String username, String password, UserRoles grantedAuthorities, String email, String firstName,
                String lastName) {
        this.username = username;
        this.password = password;
        this.grantedAuthorities = AuthHelper.convertToAuthorities(grantedAuthorities);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(grantedAuthorities);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
