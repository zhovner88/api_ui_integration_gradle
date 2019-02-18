package com.setpm.web.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data

public class UICCgroup {

	@JsonProperty("description")
	private String description;

	@JsonProperty("iccids")
	private List<String> iccids;

}