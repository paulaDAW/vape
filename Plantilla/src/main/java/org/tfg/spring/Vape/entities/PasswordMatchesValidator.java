package org.tfg.spring.Vape.entities;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.tfg.spring.Vape.dto.UserDto;
import org.tfg.spring.Vape.repositories.PasswordMatches;

public class PasswordMatchesValidator
implements ConstraintValidator<PasswordMatches, Object> {
  
  @Override
  public void initialize(PasswordMatches passwordMatches) {
  }
  @Override
  public boolean isValid(Object obj, ConstraintValidatorContext context){
      UserDto user = (UserDto) obj;
      return user.getPassword().equals(user.getMatchingPassword());
  }
}
