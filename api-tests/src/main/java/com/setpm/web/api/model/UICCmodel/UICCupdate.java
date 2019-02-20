package com.setpm.web.api.model.UICCmodel;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.annotation.Generated;

@Accessors(chain = true)
@Data

@Generated("com.robohorse.robopojogenerator")
public class UICCupdate{

	@JsonProperty("iccids")
	private List<Integer> iccids;

	@JsonProperty("description")
	private String description;

	@JsonProperty("id")
	private String id;
}