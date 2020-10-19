package com.example.seq.demo.exeption;

import com.example.seq.demo.dto.response.ErrorRegisterResponse;
import lombok.Data;

@Data
public class RegisterException extends RuntimeException {
    private ErrorRegisterResponse errorRegisterResponse;

    public RegisterException(ErrorRegisterResponse errorRegisterResponse) {
        this.errorRegisterResponse = errorRegisterResponse;
    }
}
