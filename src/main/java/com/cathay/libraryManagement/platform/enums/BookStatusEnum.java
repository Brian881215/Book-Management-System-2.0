package com.cathay.libraryManagement.platform.enums;

public enum BookStatusEnum { 
	
	Status1("1","該書尚未出租"),
	Status2("2","該書已經出租"),
	Status3("3","該書已經註銷或損毀");

	private String code;
	private String desc;
	
	BookStatusEnum(String code, String desc) {
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
