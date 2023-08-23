package com.cathay.libraryManagement.platform.service;

import org.springframework.stereotype.Service;
import com.cathay.libraryManagement.platform.model.*;

@Service
public interface BookService {
	
	Response addBook(BookAddDTO bookAddDTO);
	Response modifyBook(BookModifyDTO bookModifyDTO);
	Response deleteBook(String bookISBN);
	BookDTO queryBook(BookQueryDTO bookQueryDTO);
	Response borrowBook(String bookISBN, String bookBorrowerId);
	Response returnBook(String bookISBN,String bookBorrowerId);
	OverdueBookDTO overdueBook(int overdue);
}
