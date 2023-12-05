package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Objects;

@Schema(description = "Details about the response")
public class Detail {
    @JsonProperty("loc")
    private List<String> loc;

    @JsonProperty("msg")
    private String msg;

    @JsonProperty("type")
    private String type;
    
    public Detail() {
		// TODO Auto-generated constructor stub
	}

	public List<String> getLoc() {
		return loc;
	}

	public void setLoc(List<String> loc) {
		this.loc = loc;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	



    
}
