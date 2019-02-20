package com.setpm.web.api.model.userModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data

public class User {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

}
