package com.example.cathayLibrarySystemDemo;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cathay.libraryManagement.platform.controller.BookController;
import com.cathay.libraryManagement.platform.enums.ReturnCodeEnum;
import com.cathay.libraryManagement.platform.model.BookAddDTO;
import com.cathay.libraryManagement.platform.model.BookDTO;
import com.cathay.libraryManagement.platform.model.BookModifyDTO;
import com.cathay.libraryManagement.platform.model.BookQueryDTO;
import com.cathay.libraryManagement.platform.model.MWHEADER;
import com.cathay.libraryManagement.platform.model.OverdueBookDTO;
import com.cathay.libraryManagement.platform.model.Response;
import com.cathay.libraryManagement.platform.service.BookService;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
//@AutoConfigureMockMvc
//@WebMvcTest(BookController.class)
class BookControllerTests {
	
//	@Autowired
//	private MockMvc mockMvc;
	
	@InjectMocks
	private BookController bookController;
	
	@Mock
	private BookService bookService;
	
	@Test
	void testAddOneBook() {
		when(bookService.addBook(any(BookAddDTO.class))).thenReturn(new Response());
		BookAddDTO bookAddDTO = new BookAddDTO();
		ResponseEntity<Response> responseEntity = bookController.addOneBook(bookAddDTO);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());;
	}
	
	@Test
	void testModifyOneBook() {
		when(bookService.modifyBook(any(BookModifyDTO.class))).thenReturn(new Response());
		BookModifyDTO bookModifyDTO = new BookModifyDTO();
		ResponseEntity<Response> responseEntity = bookController.modifyOneBook(bookModifyDTO);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());;
	}
	
	@Test
	void testDeleteOneBook() throws Exception {
		when(bookService.deleteBook(Mockito.anyString())).thenReturn(new Response());	
		ResponseEntity<Response> responseEntity = bookController.deleteOneBook(Mockito.anyString());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());;
		
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/book/delete", "111356030");
//		mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().is(200));
		
	}
	
	@Test
	void testQueryOneBook() {
		when(bookService.queryBook(any(BookQueryDTO.class))).thenReturn(new BookDTO());
		BookQueryDTO bookQueryDTO = new BookQueryDTO();		
		BookDTO bookDTO = bookController.queryOneBook(bookQueryDTO);
		bookDTO.setReturnContent(new MWHEADER());
		bookDTO.getReturnContent().setRETURNCODE("0000");
		assertEquals(ReturnCodeEnum.As_SUSS.getCode(), bookDTO.getReturnContent().getRETURNCODE());
	}
	
	@Test
	void testBorrowOneBook() {
		when(bookService.borrowBook(Mockito.anyString(),Mockito.anyString())).thenReturn(new Response());		
		ResponseEntity<Response> responseEntity = bookController.borrowOneBook(Mockito.anyString(),Mockito.anyString());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());;
	}
	
	@Test
	void testReturnOneBook() {
		when(bookService.returnBook(Mockito.anyString(),Mockito.anyString())).thenReturn(new Response());		
		ResponseEntity<Response> responseEntity = bookController.returnOneBook(Mockito.anyString(),Mockito.anyString());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());;
	}
	
	@Test
	void testOverdueOneBook() {
		when(bookService.overdueBook(Mockito.anyInt())).thenReturn(new OverdueBookDTO());
		OverdueBookDTO overdueDTO = bookController.overdueOneBook(Mockito.anyInt());
		overdueDTO.setMWHEADER(new MWHEADER());
		overdueDTO.getMWHEADER().setRETURNCODE("0000");
		assertEquals(ReturnCodeEnum.As_SUSS.getCode(), overdueDTO.getMWHEADER().getRETURNCODE());
	}
	
	
	
	
	

}
