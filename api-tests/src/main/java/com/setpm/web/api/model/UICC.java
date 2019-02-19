package com.setpm.web.api.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data

public class UICC{

	@JsonProperty("userIds")
	private List<String> userIds;

	@JsonProperty("description")
	private String description;

}