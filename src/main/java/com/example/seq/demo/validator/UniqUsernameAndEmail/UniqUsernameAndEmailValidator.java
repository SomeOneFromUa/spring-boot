package com.example.seq.demo.validator.UniqUsernameAndEmail;

import com.example.seq.demo.dao.UserDao;
import com.example.seq.demo.dto.request.RegisterRequest;
import com.example.seq.demo.dto.response.BasicResponse;
import com.example.seq.demo.dto.response.ErrorRegisterResponse;
import com.example.seq.demo.exeption.RegisterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Type;
import java.util.Map;

public class UniqUsernameAndEmailValidator implements ConstraintValidator<UniqUsernameAndEmail, Object> {
   @Autowired
   private UserDao userDao;

   public void initialize(UniqUsernameAndEmail constraint) {
   }

   public boolean isValid(Object obj, ConstraintValidatorContext context) {
      if (userDao == null) return true;
      if (obj  instanceof RegisterRequest){
         RegisterRequest user = (RegisterRequest) obj;
         boolean isEmail = this.userDao.existsUserByEmail(user.getEmail());
         boolean isLogin = this.userDao.existsUserByLogin(user.getUsername());
         if (isEmail || isLogin){
            ErrorRegisterResponse errorRegisterResponse = new ErrorRegisterResponse();
            errorRegisterResponse.setError("invalid input data");
            errorRegisterResponse.setMessage("not uniq input data");
            errorRegisterResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            Map<String, String> fields = errorRegisterResponse.getFields();
            if (isEmail){
               fields.put("email", "email address is taken");
            }
            if (isLogin) {
               fields.put("login","username is taken");
            }
            throw new RegisterException(errorRegisterResponse);
         }
         return true;
      }

      return true;
   }
}
