package com.cathay.libraryManagement.platform.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OverdueBookDTO { //這個要升冪排序
	
	@JsonProperty("MWHEADER")
	MWHEADER mWHEADER;
	
	@JsonProperty("book_count")
	int queryCount;
	
	@JsonProperty("book_list")
	List<OverdueBookList> overDueQueryList = new ArrayList<OverdueBookList>();	
}
