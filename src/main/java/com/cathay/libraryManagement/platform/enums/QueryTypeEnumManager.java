package com.cathay.libraryManagement.platform.enums;

public enum QueryTypeEnumManager {
	
	 ISBN("1","FindByBookISBN"),
	 Status("2","FindByBookStatus"),
	 Name("3","針對bookName進行模糊搜尋");
	
	private String value;
	private String desc;
	
	QueryTypeEnumManager(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getDesc() {
		return desc;
	}
}
