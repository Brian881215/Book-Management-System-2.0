package com.example.cathayLibrarySystemDemo;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cathay.libraryManagement.platform.entity.BookInfo;
import com.cathay.libraryManagement.platform.entity.BorrowLog;
import com.cathay.libraryManagement.platform.enums.BookStatusEnum;
import com.cathay.libraryManagement.platform.enums.QueryTypeEnumManager;
import com.cathay.libraryManagement.platform.enums.ReturnCodeEnum;
import com.cathay.libraryManagement.platform.model.BookAddDTO;
import com.cathay.libraryManagement.platform.model.BookDTO;
import com.cathay.libraryManagement.platform.model.BookModifyDTO;
import com.cathay.libraryManagement.platform.model.BookQueryDTO;
import com.cathay.libraryManagement.platform.model.OverdueBookDTO;
import com.cathay.libraryManagement.platform.model.Response;
import com.cathay.libraryManagement.platform.repository.BookJpaRepository;
import com.cathay.libraryManagement.platform.repository.LogJpaRepository;
import com.cathay.libraryManagement.platform.service.impl.BookServiceImpl;


@ExtendWith(MockitoExtension.class)
public class BookServiceImplTests {
	
	@InjectMocks
	private BookServiceImpl bookService;
	
	@Mock
	BookJpaRepository bookJpaRepository;
	
	@Mock
	LogJpaRepository logJpaRepository;
	
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
		bookInfo.setBookBorrowerId("111356030");
		bookInfo.setBorrowDate(localDate);
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
	
	//AddBookTestingObject
	public BookModifyDTO newBookModifyDTO() {
		BookModifyDTO bookModifyDTO = new BookModifyDTO();
		bookModifyDTO.setBookISBN("9787115488811");
		bookModifyDTO.setBookLanguage("1");
		bookModifyDTO.setBookName("單元測試用的bookAddDTO假資料");
		final LocalDate myTestDate = LocalDate.of(2023,8,20);
		bookModifyDTO.setBookAuthor("");
		bookModifyDTO.setBookPublisher("人民郵電");
		bookModifyDTO.setBookPubDate(myTestDate);
		LocalDate localDate = LocalDate.now();
		bookModifyDTO.setBookCreateDate(localDate);
		bookModifyDTO.setBookStatus("1");
		return bookModifyDTO;
	}
	
	//AddBookQueryDTO
	public BookQueryDTO newBookQueryDTO() {
		BookQueryDTO bookQueryDTO = new BookQueryDTO();
//		bookQueryDTO.setBookISBN("111356030");
		bookQueryDTO.setBookName("Brian1215");
		bookQueryDTO.setBookStatus("1");
//		bookQueryDTO.setQueryType("1");
		return bookQueryDTO;
	}
	
	
//addBook	
	@Test
	void testAddExistingBook() {
		
		//測試一個已經存在的書籍
		BookInfo existingBook = newBookInfo();
		when(bookJpaRepository.findByBookISBN(Mockito.anyString())).thenReturn(java.util.Optional.of(existingBook));
		//創建一個新的書籍DTO
		BookAddDTO bookAddDTO = newBookAddDTO();
		Response response = bookService.addBook(bookAddDTO);
		assertEquals(ReturnCodeEnum.E001.getCode(), response.getMwheader().getRETURNCODE());
		assertEquals("交易失敗資料已存在", response.getMwheader().getRETURNDESC());
		verify(bookJpaRepository, never()).saveAndFlush(any(BookInfo.class));
	}
	
	@Test
	void testAddNullBook() {
		
		BookAddDTO bookAddDTO = newBookAddDTO();
		Response response = bookService.addBook(bookAddDTO);
		assertEquals(ReturnCodeEnum.As_SUSS.getCode(), response.getMwheader().getRETURNCODE());
		assertEquals("交易成功", response.getMwheader().getRETURNDESC());
		//測驗saveAndFlush是否有成功被調用一次
		verify(bookJpaRepository, times(1)).saveAndFlush(any(BookInfo.class));
	}
	
//updateBook	
	@Test
	void testModifyExistingBook() {
		
		//測試一個已經存在的書籍
		BookInfo existingBook = newBookInfo();
		when(bookJpaRepository.findByBookISBN(Mockito.anyString())).thenReturn(java.util.Optional.of(existingBook));
		//創建一個新的書籍DTO
		BookModifyDTO bookAddDTO = newBookModifyDTO();
		Response response = bookService.modifyBook(bookAddDTO);
		assertEquals(ReturnCodeEnum.As_SUSS.getCode(), response.getMwheader().getRETURNCODE());
		assertEquals("交易成功", response.getMwheader().getRETURNDESC());
		verify(bookJpaRepository, times(1)).saveAndFlush(any(BookInfo.class));
	}
	
	@Test
	void testModifyNullBook() {
		
		BookModifyDTO bookModifyDTO = new BookModifyDTO();
		Response response = bookService.modifyBook(bookModifyDTO);
		assertEquals(ReturnCodeEnum.E001.getCode(), response.getMwheader().getRETURNCODE());
		assertEquals("交易失敗資料不存在", response.getMwheader().getRETURNDESC());
		verify(bookJpaRepository, never()).saveAndFlush(any(BookInfo.class));
	}
	
//deleteBook
	@Test
	void testDeleteExistingBook() {
		
		String bookISBN = "111356030";
		BookInfo existingBook = newBookInfo();
		when(bookJpaRepository.findByBookISBN(Mockito.anyString())).thenReturn(java.util.Optional.of(existingBook));
		Response response = bookService.deleteBook(bookISBN);
		assertEquals(ReturnCodeEnum.As_SUSS.getCode(), response.getMwheader().getRETURNCODE());
		assertEquals("交易成功", response.getMwheader().getRETURNDESC());
		verify(bookJpaRepository, times(1)).deleteByBookISBN(bookISBN);
	}
	
	@Test
	void testDeleteNullBook() {
		
		String bookISBN = "111356030";
		Response response = bookService.deleteBook(bookISBN);
		assertEquals(ReturnCodeEnum.E001.getCode(), response.getMwheader().getRETURNCODE());
		assertEquals("交易失敗資料不存在", response.getMwheader().getRETURNDESC());
		verify(bookJpaRepository, never()).deleteByBookISBN(bookISBN);
	}
	
//queryBook
	@ParameterizedTest
	@ValueSource(strings = {"","  "})
	void testQueryBookType1EmptyISBN(String param) {
		List<BookInfo> bookInfos = new ArrayList<BookInfo>();
		bookInfos.add(newBookInfo()); 
		BookQueryDTO bookQueryDTO = newBookQueryDTO();
		bookQueryDTO.setQueryType("1");
		bookQueryDTO.setBookISBN(param);
		when(bookJpaRepository.findAll()).thenReturn(bookInfos);
		BookDTO response = bookService.queryBook(bookQueryDTO);
		assertEquals(ReturnCodeEnum.As_SUSS.getCode(), response.getReturnContent().getRETURNCODE());
		assertEquals("交易成功", response.getReturnContent().getRETURNDESC());
	}
	
	@Test
	void testQueryBookType1NullISBN() {
		List<BookInfo> bookInfos = new ArrayList<BookInfo>();
		bookInfos.add(newBookInfo()); 
		BookQueryDTO bookQueryDTO = newBookQueryDTO();
		bookQueryDTO.setQueryType("1");
		when(bookJpaRepository.findAll()).thenReturn(bookInfos);
		BookDTO response = bookService.queryBook(bookQueryDTO);
		assertEquals(ReturnCodeEnum.As_SUSS.getCode(), response.getReturnContent().getRETURNCODE());
		assertEquals("交易成功", response.getReturnContent().getRETURNDESC());
	}
	
	@Test
	void testQueryBookType1HasCorrectISBN() {
		BookInfo existingBook = newBookInfo();
		when(bookJpaRepository.findByBookISBN(Mockito.anyString())).thenReturn(java.util.Optional.of(existingBook));
		BookQueryDTO bookQueryDTO = newBookQueryDTO();
		bookQueryDTO.setQueryType("1");
		bookQueryDTO.setBookISBN("111356030");
		BookDTO response = bookService.queryBook(bookQueryDTO);
		assertEquals(ReturnCodeEnum.As_SUSS.getCode(), response.getReturnContent().getRETURNCODE());
		assertEquals("交易成功", response.getReturnContent().getRETURNDESC());
	}
	
	@Test
	void testQueryBookType1HasWrongISBN() {
		List<BookInfo> bookInfos = new ArrayList<BookInfo>();
		bookInfos.add(newBookInfo()); 
		BookQueryDTO bookQueryDTO = newBookQueryDTO();
		bookQueryDTO.setQueryType("1");
		bookQueryDTO.setBookISBN("11135603");
		BookDTO response = bookService.queryBook(bookQueryDTO);
		assertEquals(ReturnCodeEnum.E001.getCode(), response.getReturnContent().getRETURNCODE());
		assertEquals("交易失敗，找不到對應的ISBN", response.getReturnContent().getRETURNDESC());
	}
	
	
	
	@Test
	void testQueryBookType2HasBook() {
		List<BookInfo> bookInfos = new ArrayList<BookInfo>();
		bookInfos.add(newBookInfo()); 
		BookQueryDTO bookQueryDTO = newBookQueryDTO();
		bookQueryDTO.setQueryType(QueryTypeEnumManager.Status.getValue());
		when(bookJpaRepository.findByBookStatus(Mockito.anyString())).thenReturn(bookInfos);
		BookDTO response = bookService.queryBook(bookQueryDTO);
		assertEquals(ReturnCodeEnum.As_SUSS.getCode(), response.getReturnContent().getRETURNCODE());
		assertEquals("交易成功", response.getReturnContent().getRETURNDESC());
	}
	
	@Test
	void testQueryBookType2NullBook() {
		BookQueryDTO bookQueryDTO = newBookQueryDTO();
		bookQueryDTO.setQueryType(QueryTypeEnumManager.Status.getValue());
		BookDTO response = bookService.queryBook(bookQueryDTO);
		assertEquals(ReturnCodeEnum.As_SUSS.getCode(), response.getReturnContent().getRETURNCODE());
		assertEquals("交易成功", response.getReturnContent().getRETURNDESC());
	}
	
	@Test
	void testQueryBookType3HasBook() {
		List<BookInfo> bookInfos = new ArrayList<BookInfo>();
		bookInfos.add(newBookInfo());
		BookQueryDTO bookQueryDTO = newBookQueryDTO();
		bookQueryDTO.setQueryType(QueryTypeEnumManager.Name.getValue());
		when(bookJpaRepository.findByBookNameLike(Mockito.anyString())).thenReturn(bookInfos);
		BookDTO response = bookService.queryBook(bookQueryDTO);
		assertEquals(ReturnCodeEnum.As_SUSS.getCode(), response.getReturnContent().getRETURNCODE());
		assertEquals("交易成功", response.getReturnContent().getRETURNDESC());
		
	}
	
	@Test
	void testQueryBookType3NullBook() {
		BookQueryDTO bookQueryDTO = newBookQueryDTO();
		bookQueryDTO.setQueryType(QueryTypeEnumManager.Name.getValue());
		BookDTO response = bookService.queryBook(bookQueryDTO);
		assertEquals(ReturnCodeEnum.As_SUSS.getCode(), response.getReturnContent().getRETURNCODE());
		assertEquals("交易成功", response.getReturnContent().getRETURNDESC());
	}
	
	
//borrowBook
	@Test
	void testBorrowNullBook() {
		String bookISBN = "111356030";
		String borrowerId = "111356030";
		Response response = bookService.borrowBook(bookISBN,borrowerId);
		assertEquals(ReturnCodeEnum.E001.getCode(), response.getMwheader().getRETURNCODE());
		assertEquals("交易失敗資料不存在", response.getMwheader().getRETURNDESC());
		verify(bookJpaRepository, never()).saveAndFlush(any(BookInfo.class));
		verify(logJpaRepository, never()).saveAndFlush(any(BorrowLog.class));
	}
	
	@Test
	void testBorrowStatus3Book() {
		BookInfo existingBook = newBookInfo();
		existingBook.setBookStatus(BookStatusEnum.Status3.getCode());
		String bookISBN = "111356030";
		String borrowerId = "111356030";
		when(bookJpaRepository.findByBookISBN(Mockito.anyString())).thenReturn(java.util.Optional.of(existingBook));
		Response response = bookService.borrowBook(bookISBN,borrowerId);
		assertEquals(ReturnCodeEnum.E001.getCode(), response.getMwheader().getRETURNCODE());
		assertEquals("交易失敗資料不存在", response.getMwheader().getRETURNDESC());
		verify(bookJpaRepository, never()).saveAndFlush(any(BookInfo.class));
		verify(logJpaRepository, never()).saveAndFlush(any(BorrowLog.class));
	}
	
	@Test
	void testBorrowBorrowedBook() {
		BookInfo existingBook = newBookInfo();
		existingBook.setBookStatus(BookStatusEnum.Status2.getCode());
		String bookISBN = "111356030";
		String borrowerId = "111356030";
		when(bookJpaRepository.findByBookISBN(Mockito.anyString())).thenReturn(java.util.Optional.of(existingBook));
		Response response = bookService.borrowBook(bookISBN,borrowerId);
		assertEquals(ReturnCodeEnum.E001.getCode(), response.getMwheader().getRETURNCODE());
		assertEquals("交易失敗資料已借出", response.getMwheader().getRETURNDESC());
		verify(bookJpaRepository, never()).saveAndFlush(any(BookInfo.class));
		verify(logJpaRepository, never()).saveAndFlush(any(BorrowLog.class));
	}
	
	@Test
	void testBorrowNotBorrowBook() {
		BookInfo existingBook = newBookInfo();
		String bookISBN = "111356030";
		String borrowerId = "111356030";
		when(bookJpaRepository.findByBookISBN(Mockito.anyString())).thenReturn(java.util.Optional.of(existingBook));
		Response response = bookService.borrowBook(bookISBN,borrowerId);
		assertEquals(ReturnCodeEnum.As_SUSS.getCode(), response.getMwheader().getRETURNCODE());
		assertEquals("交易成功", response.getMwheader().getRETURNDESC());
		//verify裡面放的物件要用any(...class)，不然會跑出物件位址不相同然後沒有過的錯誤
		verify(bookJpaRepository, times(1)).saveAndFlush(any(BookInfo.class));
		verify(logJpaRepository, times(1)).saveAndFlush(any(BorrowLog.class));
		
	} 
	
//returnBook
	@Test
	void testReturnNullBook() {
		String bookISBN = "111356030";
		String borrowerId = "111356030";
		Response response = bookService.returnBook(bookISBN,borrowerId);
		assertEquals(ReturnCodeEnum.E001.getCode(), response.getMwheader().getRETURNCODE());
		assertEquals("交易失敗資料不存在", response.getMwheader().getRETURNDESC());
		verify(bookJpaRepository, never()).saveAndFlush(any(BookInfo.class));
		verify(logJpaRepository, never()).saveAndFlush(any(BorrowLog.class));
	}
	
	@Test
	void testReturnStatus3Book() {
		BookInfo existingBook = newBookInfo();
		existingBook.setBookStatus(BookStatusEnum.Status3.getCode());
		String bookISBN = "111356030";
		String borrowerId = "111356030";
		when(bookJpaRepository.findByBookISBN(Mockito.anyString())).thenReturn(java.util.Optional.of(existingBook));
		Response response = bookService.returnBook(bookISBN,borrowerId);
		assertEquals(ReturnCodeEnum.E001.getCode(), response.getMwheader().getRETURNCODE());
		assertEquals("交易失敗資料不存在", response.getMwheader().getRETURNDESC());
		verify(bookJpaRepository, never()).saveAndFlush(any(BookInfo.class));
		verify(logJpaRepository, never()).saveAndFlush(any(BorrowLog.class));
	}
	
	@Test
	void testReturnReturnedBook() {
		BookInfo existingBook = newBookInfo();
		existingBook.setBookStatus(BookStatusEnum.Status1.getCode());
		String bookISBN = "111356030";
		String borrowerId = "111356030";
		when(bookJpaRepository.findByBookISBN(Mockito.anyString())).thenReturn(java.util.Optional.of(existingBook));
		Response response = bookService.returnBook(bookISBN,borrowerId);
		assertEquals(ReturnCodeEnum.E001.getCode(), response.getMwheader().getRETURNCODE());
		assertEquals("交易失敗該書已歸還", response.getMwheader().getRETURNDESC());
		verify(bookJpaRepository, never()).saveAndFlush(any(BookInfo.class));
		verify(logJpaRepository, never()).saveAndFlush(any(BorrowLog.class));
	}
	
	@Test
	void testReturnNotReturnBook() {
		BookInfo existingBook = newBookInfo();
		existingBook.setBookStatus(BookStatusEnum.Status2.getCode());
		//要初始化status為2才表示這本書已經被借走了
		String bookISBN = "111356030";
		String borrowerId = "111356030";
		when(bookJpaRepository.findByBookISBN(Mockito.anyString())).thenReturn(java.util.Optional.of(existingBook));
		Response response = bookService.returnBook(bookISBN,borrowerId);
		assertEquals(ReturnCodeEnum.As_SUSS.getCode(), response.getMwheader().getRETURNCODE());
		assertEquals("交易成功", response.getMwheader().getRETURNDESC());
		//verify裡面放的物件要用any(...class)，不然會跑出物件位址不相同然後沒有過的錯誤
		verify(bookJpaRepository, times(1)).saveAndFlush(any(BookInfo.class));
		verify(logJpaRepository, times(1)).saveAndFlush(any(BorrowLog.class));
	}
	
//OverdueBook
	@Test
	void testOverdueNullBook() {
		OverdueBookDTO response = bookService.overdueBook(0);
		assertEquals(ReturnCodeEnum.As_SUSS.getCode(), response.getMWHEADER().getRETURNCODE());
		assertEquals("交易成功", response.getMWHEADER().getRETURNDESC());
		
	}
	
	@Test
	void testOverdueBooks() {
		List<BookInfo> bookInfos = new ArrayList<BookInfo>();
		//將空的list加入一個新物件
		bookInfos.add(newBookInfo());
		when(bookJpaRepository.findByBookStatus(Mockito.anyString())).thenReturn(bookInfos);
		OverdueBookDTO response = bookService.overdueBook(Mockito.anyInt());
		assertEquals(ReturnCodeEnum.As_SUSS.getCode(), response.getMWHEADER().getRETURNCODE());
		assertEquals("交易成功", response.getMWHEADER().getRETURNDESC());	
	}
	
	
	
	
	
	
}
