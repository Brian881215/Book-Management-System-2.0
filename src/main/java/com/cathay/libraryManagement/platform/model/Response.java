package com.cathay.libraryManagement.platform.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Response {
	
	public Response() {
		mwheader = new MWHEADER();
	}
	
	@JsonProperty("MWHEADER")
	MWHEADER mwheader;
}
