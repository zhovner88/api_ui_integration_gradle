package com.setpm.web.api.model.campaignModel;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data

public class UICCs{

	@JsonProperty("uiccIds")
	private List<Integer> uiccIds;

}