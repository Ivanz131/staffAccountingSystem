package net.ivanz.staffAccountingSystem.controller;

import lombok.extern.log4j.Log4j2;
import net.ivanz.staffAccountingSystem.models.User;
import net.ivanz.staffAccountingSystem.models.UserInDTO;
import net.ivanz.staffAccountingSystem.services.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserManagementService userManagementService;

    @GetMapping("")
    public List<User> getAllUsers() {
        log.info("getAllUsers");
        return userManagementService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        log.info("getUserById: id = {}", id);
        return userManagementService.getUserById(id);
    }

    @PostMapping("")
    public User createUser(@AuthenticationPrincipal User requester, @RequestBody UserInDTO userInDTO) {
        log.info("createUser: body = {}", userInDTO);
        return userManagementService.createUser(requester, userInDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@AuthenticationPrincipal User requester, @PathVariable String id) {
        log.info("deleteUser: id = {}", id);
        userManagementService.deleteUser(requester, id);
    }

    @PutMapping("/{id}")
    public User updateUser(@AuthenticationPrincipal User requester, @RequestBody UserInDTO data, @PathVariable String id) {
        log.info("updateUser: data = {}, id = {}", data, id);
        return userManagementService.updateUser(requester,data,id);
    }
}
