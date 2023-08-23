package com.cathay.libraryManagement.platform.enums;

public enum BorrowActionEnum {
	
	Status1("1","借出動作"),
	Status2("2","歸還動作");
	
	private String code;
	private String desc;
	
	BorrowActionEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
