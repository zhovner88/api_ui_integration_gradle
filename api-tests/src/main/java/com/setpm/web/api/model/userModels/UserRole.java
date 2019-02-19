package com.setpm.web.api.model.userModels;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data

public class UserRole{

	@JsonProperty("permissions")
	private List<String> permissions;

	@JsonProperty("roleName")
	private String roleName;

	@JsonProperty("id")
	private String id;

}