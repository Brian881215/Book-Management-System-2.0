package com.cathay.libraryManagement.platform.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cathay.libraryManagement.platform.model.BookAddDTO;
import com.cathay.libraryManagement.platform.model.BookDTO;
import com.cathay.libraryManagement.platform.model.BookModifyDTO;
import com.cathay.libraryManagement.platform.model.BookQueryDTO;
import com.cathay.libraryManagement.platform.model.OverdueBookDTO;
import com.cathay.libraryManagement.platform.model.Response;
import com.cathay.libraryManagement.platform.service.BookService;

@RestController
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@PostMapping(value = "/book/add")
	public ResponseEntity<Response> addOneBook(@RequestBody BookAddDTO bookAddDTO){
        return ResponseEntity.ok(bookService.addBook(bookAddDTO));
	}
	
	@PostMapping(value = "/book/modify")
	public ResponseEntity<Response> modifyOneBook(@RequestBody BookModifyDTO bookModifyDTO){	
        return ResponseEntity.ok(bookService.modifyBook(bookModifyDTO));
	}
	
	@PostMapping(value = "/book/delete")
	public ResponseEntity<Response> deleteOneBook(@RequestParam(name = "book_ISBN")  String bookISBN){
        return ResponseEntity.ok(bookService.deleteBook(bookISBN));
	}
	
	@GetMapping(value = "/book/query")
	public BookDTO queryOneBook(@RequestBody @Valid BookQueryDTO bookQueryDTO){	
            return bookService.queryBook(bookQueryDTO);
	}
	
	@PostMapping(value = "/book/borrow")
	public ResponseEntity<Response> borrowOneBook(
			@RequestParam(name = "book_ISBN")  String bookISBN,
			@RequestParam(name = "book_borrower_ID")  String bookBorrowerId
		){	
	        return ResponseEntity.ok(bookService.borrowBook(bookISBN,bookBorrowerId));
	} 
	
	@PostMapping(value = "/book/return")
	public ResponseEntity<Response> returnOneBook(
			@RequestParam(name = "book_ISBN")  String bookISBN,
			@RequestParam(name = "book_borrower_ID")  String bookBorrowerId
		){	
	        return ResponseEntity.ok(bookService.returnBook(bookISBN,bookBorrowerId));
	}
	
	@PostMapping(value = "/book/overdue")
	public OverdueBookDTO overdueOneBook(@RequestParam int overdue){	
           return bookService.overdueBook(overdue);
	}	
}
