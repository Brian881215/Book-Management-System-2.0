package com.cathay.libraryManagement.platform.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="BOOK_INFO")
@Getter
@Setter
public class BookInfo {
//Entity不該未經處理直接對外，通常會再轉DTO，Entity也不該直接下@Data，應僅宣告需求功能
	@Id
	@Column(name = "Book_ISBN", columnDefinition = "VARCHAR(100)")
	private String bookISBN;
	
	@Column(name = "Book_language")
	private String bookLanguage;
	//1: 中文（繁體）,2: 中文（簡體）, 3: 英文
	
	@Column(name = "Book_name")
	private String bookName;
	
	@Column(name = "Book_author")
	private String bookAuthor;
	
	@Column(name = "Book_publisher")
	private String bookPublisher;
	
	@Column(name = "Book_pub_date")
	private LocalDate bookPubDate;
	
	@Column(name = "Book_create_date")
	private LocalDate bookCreateDate;
	
	@Column(name = "Book_status")
	private String bookStatus;
	//1: 未借出 ，2: 已借出，3: 遺失或損毀
	
	@Column(name = "Book_borrower_ID")
	private String bookBorrowerId;
	
	@Column(name = "Borrow_date")
	private LocalDate borrowDate;
	
}
