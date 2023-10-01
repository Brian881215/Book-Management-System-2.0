package com.cathay.libraryManagement.platform.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {
	
	@JsonProperty("MWHEADER")
	MWHEADER returnContent;
	
	@JsonProperty("book_count")
	int queryCount;
	
	@JsonProperty("book_list")
	List<BookInfoDTO> bookList = new ArrayList<BookInfoDTO>(); 
	//Ｑ: 如果要呈現少一個bookInfo裡面的變數，要怎麼設計, ANS: 用一個BookInfoDTO可以增加開發彈性，降低程式之間的耦合性，也確保entity資料不會外洩
}
