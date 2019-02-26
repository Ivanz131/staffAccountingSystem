package net.ivanz.staffAccountingSystem.services;

import lombok.extern.log4j.Log4j2;
import net.ivanz.staffAccountingSystem.exceptions.ErrorCodes;
import net.ivanz.staffAccountingSystem.exceptions.RestException;
import net.ivanz.staffAccountingSystem.integration.UserRoles;
import net.ivanz.staffAccountingSystem.models.User;
import net.ivanz.staffAccountingSystem.models.UserInDTO;
import net.ivanz.staffAccountingSystem.repositories.UserRepository;
import net.ivanz.staffAccountingSystem.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Log4j2
@Service
public class UserManagementService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private Validator validator;

    public List<User> getAllUsers(){
        log.debug("getAllUsers");
        return userRepository.findAll();
    }

    public User getUserById(String id){
        log.debug("getUserById: id = {}", id);
        return userRepository.findById(id).orElseThrow(() -> new RestException(ErrorCodes.USER_NOT_EXIST));
    }

    public User createUser(User requester, UserInDTO data) {
        log.debug("createUser:requester = {}, body = {}", requester, data);
        if (Objects.isNull(requester)){
            throw new RestException(ErrorCodes.USER_EMPTY_DATA);
        }
        if (!Arrays.asList(requester.getGrantedAuthorities()).contains(UserRoles.ADMIN.name())) {
            throw new RestException(ErrorCodes.USER_WRONG_ROLES);
        }
        validator.validateUserInDTO(data);
        String salt = BCrypt.gensalt(8);
        String hashedPassword = BCrypt.hashpw(data.getPassword(), salt);
        data.setPassword(hashedPassword);
        String message = String.format(
                "Hello, %s! \n" +
                        "Welcome to staffAccountingSystem", data.getUsername()
        );
        mailSender.send(data.getEmail(), "Simple Test", message);
        return userRepository.save(User.of(data));
    }

    public void deleteUser(User requester, String id) {
        log.debug("deleteUser: requester = {}, id = {}", requester, id);
        if (Objects.isNull(id) || id.isEmpty()){
            throw new RestException(ErrorCodes.USER_WRONG_ID);
        }
        if (requester.getId().equals(id)){
            throw new RestException(ErrorCodes.USER_CANT_DELETE_HIMSELF);
        }
        if (!Arrays.asList(requester.getGrantedAuthorities()).contains(UserRoles.ADMIN.name())){
            throw new RestException(ErrorCodes.NO_PERMISSIONS);
        }
        userRepository.findById(id).orElseThrow(() -> new RestException(ErrorCodes.USER_NOT_EXIST));
        userRepository.deleteById(id);
    }

    public User updateUser(User requester, UserInDTO data, String id){
        log.debug("updateUser: requester = {}, id = {}, data = {}", requester, id, data);
        if (Objects.isNull(id) || id.isEmpty()){
            throw new RestException(ErrorCodes.USER_WRONG_ID);
        }
        if (!requester.getId().equals(id) || !Arrays.asList(requester.getGrantedAuthorities()).contains(UserRoles.ADMIN.name())){
            throw new RestException(ErrorCodes.USER_WRONG_ROLES);
        }
        validator.validateUserInDTO(data);
        User userFromDB = userRepository.findById(id).orElseThrow(() -> new RestException(ErrorCodes.USER_NOT_EXIST));
        return userRepository.save(User.merge(userFromDB, data));
    }
}
