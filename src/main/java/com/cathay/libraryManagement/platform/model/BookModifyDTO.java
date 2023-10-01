package com.cathay.libraryManagement.platform.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookModifyDTO {
	
	@JsonProperty("book_ISBN")
	String bookISBN;
	
	@JsonProperty("book_language")
	String bookLanguage;
	
	@JsonProperty("book_name")
	String bookName;
	
	@JsonProperty("book_author")
	String bookAuthor;
	
	@JsonProperty("book_publisher")
	String bookPublisher;
	
	@JsonProperty("book_pub_date")
	LocalDate bookPubDate;
	
	@JsonProperty("book_create_date")
	LocalDate bookCreateDate;
	
	@JsonProperty("book_status")
	String bookStatus;
}
