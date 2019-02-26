package net.ivanz.staffAccountingSystem.validators;

import net.ivanz.staffAccountingSystem.exceptions.ErrorCodes;
import net.ivanz.staffAccountingSystem.exceptions.RestException;
import net.ivanz.staffAccountingSystem.models.UserInDTO;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class Validator {

    public void validateUserInDTO(UserInDTO data){
        if (Objects.isNull(data)){
            throw new RestException(ErrorCodes.USER_EMPTY_DATA);
        }
        if (data.getFirstName().trim().isEmpty() || data.getLastName().trim().isEmpty() || data.getUsername().trim().isEmpty() || data.getPassword().trim().isEmpty() || data.getEmail().trim().isEmpty()){
            throw  new RestException(ErrorCodes.USER_EMPTY_DATA);
        }
    }

}
