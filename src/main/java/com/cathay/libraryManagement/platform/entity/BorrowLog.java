package com.cathay.libraryManagement.platform.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="BORROW_LOG")
@Getter
@Setter
public class BorrowLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Borrow_serial_no")
	private int borrowSerialNo;
	//由DB自動產生
	
	@Column(name = "Borrow_ID")
	private String borrowId;
	
	@Column(name = "Borrow_book_ISBN")
	private String borrowBookISBN;
	
	@Column(name = "Borrow_action")
	private String borrowAction;
	//1:為借出，2：為歸還
	
	@Column(name = "Action_date")
	private LocalDate actioinDate;
}
