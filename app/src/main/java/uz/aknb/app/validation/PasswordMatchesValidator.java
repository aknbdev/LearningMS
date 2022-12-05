package uz.aknb.app.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object>{

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
//        RegistrationDto dto =  (RegistrationDto) value;
//        return dto.getPassword().equals(dto.getMatchingPassword());
        return true;
    }
    
}
