package com.cathay.libraryManagement.platform.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
	
	public Response() {
		mwheader = new MWHEADER();
	}
	
	@JsonProperty("MWHEADER")
	MWHEADER mwheader;
}
