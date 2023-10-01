package com.cathay.libraryManagement.platform.model;


import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookQueryDTO {
	
	@NotBlank(message = "query type不可以為null與空白字串")
	@JsonProperty("query_type")
	String queryType;
	
	@JsonProperty("book_ISBN")
	String bookISBN;
	
	@JsonProperty("book_status")
	String bookStatus;
	
	@JsonProperty("book_name")
	String bookName;
}
