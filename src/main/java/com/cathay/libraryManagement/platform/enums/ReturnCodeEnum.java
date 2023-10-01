package com.cathay.libraryManagement.platform.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReturnCodeEnum {
	
	E001("E001","交易失敗"),
	E999("E999","其他錯誤"),
	V001("V001","缺乏必要輸入欄位"),
	As_SUSS("0000","交易成功");
	
	private String desc;
	private String code;

//	ReturnCodeEnum(String code, String desc) {
//		this.setCode(code);
//		this.setDesc(desc);
//	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
