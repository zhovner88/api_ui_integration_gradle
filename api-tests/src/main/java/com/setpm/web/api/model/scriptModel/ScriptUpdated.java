package com.setpm.web.api.model.scriptModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.annotation.Generated;

@Accessors(chain = true)
@Data

@Generated("com.robohorse.robopojogenerator")
public class ScriptUpdated{

	@JsonProperty("scriptName")
	private String scriptName;

	@JsonProperty("description")
	private String description;

	@JsonProperty("id")
	private String id;

	@JsonProperty("script")
	private String script;
}