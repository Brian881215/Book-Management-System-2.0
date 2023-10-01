package com.cathay.libraryManagement.platform.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OverdueBookList {
	
	String bookISBN;
	String bookLanguage;
	String bookName;
	String bookAuthor;
	String bookPublisher;
	LocalDate bookPubDate;
	LocalDate bookCreateDate;
	String bookStatus;
	String bookBorrowerId;
	LocalDate borrowDate;
	int overdueDays;
}
