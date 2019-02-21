package net.ivanz.staffAccountingSystem.services;

import net.ivanz.staffAccountingSystem.exceptions.ErrorCodes;
import net.ivanz.staffAccountingSystem.exceptions.RestException;
import net.ivanz.staffAccountingSystem.integration.UserRoles;
import net.ivanz.staffAccountingSystem.models.User;
import net.ivanz.staffAccountingSystem.models.UserInDTO;
import net.ivanz.staffAccountingSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserManagmentService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSender mailSender;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(String id){
        return userRepository.findById(id).orElseThrow(() -> new RestException(ErrorCodes.USER_NOT_EXIST));
    }

    public User createUser(User requester, UserInDTO userInDTO) {
        if (!Arrays.asList(requester.getGrantedAuthorities()).contains(UserRoles.ADMIN.name())) {
            throw new RestException(ErrorCodes.USER_WRONG_ROLES);
        }
        String salt = BCrypt.gensalt(8);
        String hashedPassword = BCrypt.hashpw(userInDTO.getPassword(), salt);
        userInDTO.setPassword(hashedPassword);
        // TODO check userInDTO valid
        String message = String.format(
                "Hello, %s! \n" +
                        "Welcome to staffAccountingSystem", userInDTO.getUsername()
        );
        mailSender.send(userInDTO.getEmail(), "Simple Test", message);
        return userRepository.save(User.of(userInDTO));
    }

    public void deleteUser(User requester, String id) {
        // TODO validation
        userRepository.findById(id).orElseThrow(() -> new RestException(ErrorCodes.USER_NOT_EXIST));
        userRepository.deleteById(id);
    }

    public User updateUser(User requester, UserInDTO userInDTO, String id){
        return requester;
    }
}
