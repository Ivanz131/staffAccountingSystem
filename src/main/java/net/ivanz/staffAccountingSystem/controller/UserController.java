package net.ivanz.staffAccountingSystem.controller;

import net.ivanz.staffAccountingSystem.exceptions.ErrorCodes;
import net.ivanz.staffAccountingSystem.exceptions.RestException;
import net.ivanz.staffAccountingSystem.models.User;
import net.ivanz.staffAccountingSystem.models.UserInDTO;
import net.ivanz.staffAccountingSystem.repositories.UserRepository;
import net.ivanz.staffAccountingSystem.services.UserManagmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserManagmentService userManagmentService;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public List<User> getUsers() {
        return userManagmentService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userManagmentService.getUserById(id);
    }

    @PostMapping("")
    public User createUser(@AuthenticationPrincipal User requester, @RequestBody UserInDTO userInDTO) {
        return userManagmentService.createUser(requester, userInDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@AuthenticationPrincipal User requester, @PathVariable String id) {
        userManagmentService.deleteUser(requester, id);
    }

    @PutMapping("/{id}")
    public User updateUser(@AuthenticationPrincipal User requester, @RequestBody UserInDTO data, @PathVariable String id) {
        User userFromDB = userRepository.findById(id).orElseThrow(() -> new RestException(ErrorCodes.USER_NOT_EXIST));
        return userRepository.save(User.merge(userFromDB, data));
    }
}
