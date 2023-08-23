package com.example.cathayLibrarySystemDemo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.cathay.libraryManagement.platform.entity.BookInfo;
import com.cathay.libraryManagement.platform.enums.ReturnCodeEnum;
import com.cathay.libraryManagement.platform.model.BookAddDTO;
import com.cathay.libraryManagement.platform.model.Response;
import com.cathay.libraryManagement.platform.repository.BookJpaRepository;
import com.cathay.libraryManagement.platform.service.impl.BookServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTests {
	
	@InjectMocks
	private BookServiceImpl bookService;
	
	@Mock
	BookJpaRepository bookJpaRepository;
	
	
	//BookInfo object for each testing
	public BookInfo newBookInfo() {
		BookInfo bookInfo = new BookInfo();
		bookInfo.setBookISBN("111356030");
		bookInfo.setBookLanguage("1");
		bookInfo.setBookName("單元測試用的bookInfo假資料");
		bookInfo.setBookAuthor("黃誠恩");
		bookInfo.setBookPublisher("黃誠恩");
		final LocalDate myTestDate = LocalDate.of(2023,9,20);
		bookInfo.setBookPubDate(myTestDate);
		LocalDate localDate = LocalDate.now();
		bookInfo.setBookCreateDate(localDate);
		bookInfo.setBookStatus("1");
		return bookInfo; 
	}
	
	//AddBookTestingObject
	public BookAddDTO newBookAddDTO() {
		BookAddDTO bookAddDTO = new BookAddDTO();
		bookAddDTO.setBookISBN("9787115488811");
		bookAddDTO.setBookLanguage("1");
		bookAddDTO.setBookName("單元測試用的bookAddDTO假資料");
		final LocalDate myTestDate = LocalDate.of(2023,8,20);
		bookAddDTO.setBookAuthor("");
		bookAddDTO.setBookPublisher("人民郵電");
		bookAddDTO.setBookPubDate(myTestDate);
		return bookAddDTO;
	}
//addBook	
	@Test
	void testExistingAddBook() {
		//測試一個已經存在的書籍
		String existingISBN = "9787115488820";
		BookInfo existingBook = new BookInfo();
		existingBook.setBookISBN(existingISBN);
		
		when(bookJpaRepository.findByBookISBN(Mockito.anyString())).thenReturn(java.util.Optional.of(existingBook));
		
		//創建一個新的書籍DTO
		BookAddDTO bookAddDTO = newBookAddDTO();
		
		Response response = bookService.addBook(bookAddDTO);
		
		assertEquals(ReturnCodeEnum.E001.getCode(), response.getMwheader().getRETURNCODE());
		assertEquals("交易失敗資料已存在", response.getMwheader().getRETURNDESC());
		
		verify(bookJpaRepository, never()).saveAndFlush(any(BookInfo.class));
	}
	
	@Test
	void testNullAddBook() {
		
		BookAddDTO bookAddDTO = newBookAddDTO();
		
		Response response = bookService.addBook(bookAddDTO);
		
		assertEquals(ReturnCodeEnum.E001.getCode(), response.getMwheader().getRETURNCODE());
		assertEquals("交易成功", response.getMwheader().getRETURNDESC());
		
		verify(bookJpaRepository, never()).saveAndFlush(any(BookInfo.class));
	}
}
