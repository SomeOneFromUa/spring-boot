package com.example.seq.demo.dto.response;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ErrorRegisterResponse extends BasicResponse {
private Map<String, String> fields = new HashMap<>();

    public ErrorRegisterResponse(int status, String message, String error) {
        super(status, message, error);
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }
}
