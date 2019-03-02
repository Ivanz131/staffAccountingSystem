package net.ivanz.staffAccountingSystem.services;

import net.ivanz.staffAccountingSystem.BasicTest;
import net.ivanz.staffAccountingSystem.integration.UserRoles;
import net.ivanz.staffAccountingSystem.models.User;
import net.ivanz.staffAccountingSystem.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;

public class MyUserDetailServiceTest extends BasicTest {
    @Autowired
    private MyUserDetailService userDetailService;
    @Autowired
    private UserRepository userRepository;


    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameNull() {
        userDetailService.loadUserByUsername(null);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameEmpty() {
        userDetailService.loadUserByUsername("");
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameSpace() {
        userDetailService.loadUserByUsername(" ");
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameTrash() {
        userDetailService.loadUserByUsername("adf hias  adshfi*^( sfA(*F&5( SA fad f857A");
    }

    @Test
    public void testLoadUserByUsername() {
        userRepository.save(new User("user1", "password1", new ArrayList<>(Arrays.asList(UserRoles.ADMIN))));
        UserDetails ud = userDetailService.loadUserByUsername("user1");

        Assert.assertEquals("ud.getUsername() = " + ud.getUsername(), "user1", ud.getUsername());
        Assert.assertEquals("ud.getPassword() = " + ud.getPassword(), "password1", ud.getPassword());

        userRepository.deleteUserByUsername(ud.getUsername());
    }
}