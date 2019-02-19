package com.setpm.web.api.model.userModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data

public class UserObject {

	@JsonProperty("password")
	private String password;

	@JsonProperty("uiccIds")
	private List<String> uiccIds;

	@JsonProperty("roleId")
	private String roleId;

	@JsonProperty("groupId")
	private String groupId;

	@JsonProperty("username")
	private String username;

	@JsonProperty("id")
	private String id;

}