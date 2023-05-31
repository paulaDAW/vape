package org.tfg.spring.Vape.repositories;
 
import org.tfg.spring.Vape.dto.UserDto;
import org.tfg.spring.Vape.entities.User;
import org.tfg.spring.Vape.exception.UserAlreadyExistException;
import org.tfg.spring.Vape.parteDos.VerificationToken;

public interface IUserService {
    
    User registerNewUserAccount(UserDto userDto) 
      throws UserAlreadyExistException;

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

	void deleteUserToken(User user, VerificationToken verificationToken) throws Exception;
}
