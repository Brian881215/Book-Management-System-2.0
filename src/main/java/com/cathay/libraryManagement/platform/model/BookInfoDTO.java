package com.cathay.libraryManagement.platform.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookInfoDTO {

	@JsonProperty("book_ISBN")
	private String bookISBN;
	
	@JsonProperty("book_language")
	private String bookLanguage;
	//1: 中文（繁體）,2: 中文（簡體）, 3: 英文
	
	@JsonProperty("book_name")
	private String bookName;
	
	@JsonProperty("book_author")
	private String bookAuthor;
	
	@JsonProperty("book_publisher")
	private String bookPublisher;
	
	@JsonProperty("book_pub_date")
	private LocalDate bookPubDate;
	
	@JsonProperty("book_create_date")
	private LocalDate bookCreateDate;
	
	@JsonProperty("book_status")
	private String bookStatus;
	//1: 未借出 ，2: 已借出，3: 遺失或損毀
	
	@JsonProperty("book_borrower_ID")
	private String bookBorrowerId;
	
	@JsonProperty("borrow_date")
	private LocalDate borrowDate;
}
