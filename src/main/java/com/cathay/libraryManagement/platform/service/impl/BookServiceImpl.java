package com.cathay.libraryManagement.platform.service.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cathay.libraryManagement.platform.entity.BookInfo;
import com.cathay.libraryManagement.platform.entity.BorrowLog;
import com.cathay.libraryManagement.platform.enums.BookStatusEnum;
import com.cathay.libraryManagement.platform.enums.BorrowActionEnum;
import com.cathay.libraryManagement.platform.enums.QueryTypeEnumManager;
import com.cathay.libraryManagement.platform.enums.ReturnCodeEnum;
import com.cathay.libraryManagement.platform.model.BookAddDTO;
import com.cathay.libraryManagement.platform.model.BookDTO;
import com.cathay.libraryManagement.platform.model.BookInfoDTO;
import com.cathay.libraryManagement.platform.model.BookModifyDTO;
import com.cathay.libraryManagement.platform.model.BookQueryDTO;
import com.cathay.libraryManagement.platform.model.MWHEADER;
import com.cathay.libraryManagement.platform.model.OverdueBookDTO;
import com.cathay.libraryManagement.platform.model.OverdueBookList;
import com.cathay.libraryManagement.platform.model.Response;
import com.cathay.libraryManagement.platform.repository.BookJpaRepository;
import com.cathay.libraryManagement.platform.repository.LogJpaRepository;
import com.cathay.libraryManagement.platform.service.BookService;

@Service
@Transactional
public class BookServiceImpl implements BookService{

	@Autowired
	private BookJpaRepository bookJpaRepository;
	
	@Autowired
	private LogJpaRepository logJpaRepository;
	
//	@Autowired
//	private Response response; 
	
	public Response returnCode(ReturnCodeEnum value,String desc) {
		//傳Enum物件，可以彈性擴充未來需要的不同回傳資訊
		Response response = new Response();
		response.getMwheader().setRETURNCODE(value.getCode());
		if(desc == null) 
			response.getMwheader().setRETURNDESC(value.getDesc());
		else 
			response.getMwheader().setRETURNDESC(value.getDesc()+desc);
		
		return response;
	}
	
	@Override
	public Response addBook(BookAddDTO bookAddDTO) {
			
		BookInfo bookInfo = bookJpaRepository.findByBookISBN(bookAddDTO.getBookISBN()).orElse(null);
		if(bookInfo != null) {
			return returnCode(ReturnCodeEnum.E001, "資料已存在");
		}
		
		BookInfo book = new BookInfo();
		book.setBookISBN(bookAddDTO.getBookISBN());
		book.setBookLanguage(bookAddDTO.getBookLanguage());
		book.setBookName(bookAddDTO.getBookName());
		book.setBookAuthor(bookAddDTO.getBookAuthor());
		book.setBookPublisher(bookAddDTO.getBookPublisher());
		book.setBookPubDate(bookAddDTO.getBookPubDate());	
		
		bookJpaRepository.saveAndFlush(book);
		
		return returnCode(ReturnCodeEnum.As_SUSS,null);
	}

	@Override
	public Response modifyBook(BookModifyDTO bookModifyDTO) {
		
		BookInfo bookInfo = bookJpaRepository.findByBookISBN(bookModifyDTO.getBookISBN()).orElse(null);
		if(bookInfo == null) {
			return returnCode(ReturnCodeEnum.E001,"資料不存在");
		}
		
		BookInfo book = new BookInfo();
		book.setBookISBN(bookModifyDTO.getBookISBN());
		book.setBookLanguage(bookModifyDTO.getBookLanguage());
		book.setBookName(bookModifyDTO.getBookName());
		book.setBookAuthor(bookModifyDTO.getBookAuthor());
		book.setBookPublisher(bookModifyDTO.getBookPublisher());
		book.setBookPubDate(bookModifyDTO.getBookPubDate());
		book.setBookCreateDate(bookModifyDTO.getBookCreateDate());
		book.setBookStatus(bookModifyDTO.getBookStatus());
		
		bookJpaRepository.saveAndFlush(book);
		
		return returnCode(ReturnCodeEnum.As_SUSS,null);
	}

	@Override
	public Response deleteBook(String bookISBN) {
		
		BookInfo bookInfo = bookJpaRepository.findByBookISBN(bookISBN).orElse(null);
		if(bookInfo==null) {
			return returnCode(ReturnCodeEnum.E001,"資料不存在");
		}
		bookJpaRepository.deleteByBookISBN(bookISBN);

		return returnCode(ReturnCodeEnum.As_SUSS,null);
	}

	@Override
	public BookDTO queryBook(BookQueryDTO bookQueryDTO) {
		
		BookDTO bookDTO = new BookDTO();
		BookInfo bookInfo = bookJpaRepository.findByBookISBN(bookQueryDTO.getBookISBN()).orElse(null);
		MWHEADER responseObject = new MWHEADER();
		int queryCount = 0;
		String type = bookQueryDTO.getQueryType();
		
		if(type.equals(QueryTypeEnumManager.ISBN.getValue())){
			
			if(bookQueryDTO.getBookISBN() ==null || bookQueryDTO.getBookISBN().isBlank()) {
				List<BookInfo> bookInfos = bookJpaRepository.findAll();
				List<BookInfoDTO> bookInfosDTO = transferBookInfoEntityToDTO(bookInfos);
				bookDTO.getBookList().addAll(bookInfosDTO);
				queryCount += bookInfos.size();
			}else {
				if(bookInfo != null) {
					BookInfoDTO bookInfoDTO = new BookInfoDTO(); 
					bookInfoDTO.setBookISBN(bookInfo.getBookISBN());
					bookInfoDTO.setBookLanguage(bookInfo.getBookISBN());
					bookInfoDTO.setBookName(bookInfo.getBookName());
					bookInfoDTO.setBookAuthor(bookInfo.getBookAuthor());
					bookInfoDTO.setBookPublisher(bookInfo.getBookPublisher());
					bookInfoDTO.setBookPubDate(bookInfo.getBookPubDate());
					bookInfoDTO.setBookCreateDate(bookInfo.getBookCreateDate());
					bookInfoDTO.setBookStatus(bookInfo.getBookStatus());
					bookInfoDTO.setBookBorrowerId(bookInfo.getBookBorrowerId());
					bookInfoDTO.setBorrowDate(bookInfo.getBorrowDate());
					bookDTO.getBookList().add(bookInfoDTO);
					queryCount++;
				}else { //表示bookQueryDTO的ISBN有值，但是是錯誤的
					responseObject.setRETURNCODE(ReturnCodeEnum.E001.getCode());
					responseObject.setRETURNDESC(ReturnCodeEnum.E001.getDesc()+ "，找不到對應的ISBN");
					bookDTO.setReturnContent(responseObject);
					return bookDTO;
				}
			}	
		}else if(type.equals(QueryTypeEnumManager.Status.getValue())) {
			List<BookInfo> bookStatusInfos = bookJpaRepository.findByBookStatus(bookQueryDTO.getBookStatus());
			if(bookStatusInfos != null) {
				List<BookInfoDTO> bookInfosDTO = transferBookInfoEntityToDTO(bookStatusInfos);
				bookDTO.getBookList().addAll(bookInfosDTO);
				queryCount += bookStatusInfos.size();
			}
		}else if(type.equals(QueryTypeEnumManager.Name.getValue())) {
			List<BookInfo> bookJPQLList = bookJpaRepository.findByBookNameLike(bookQueryDTO.getBookName());
			if(bookJPQLList != null) {
				List<BookInfoDTO> bookInfosDTO = transferBookInfoEntityToDTO(bookJPQLList);
				bookDTO.getBookList().addAll(bookInfosDTO);
				queryCount+= bookJPQLList.size();
			}
		}
		bookDTO.setQueryCount(queryCount);
		//這支API不管怎樣都會顯示交易成功（照文件上面所述的話）
		responseObject.setRETURNCODE(ReturnCodeEnum.As_SUSS.getCode());
		responseObject.setRETURNDESC(ReturnCodeEnum.As_SUSS.getDesc());
		bookDTO.setReturnContent(responseObject);
		
		return bookDTO;
	}
	
	public List<BookInfoDTO> transferBookInfoEntityToDTO(List<BookInfo> bookInfos){
		List<BookInfoDTO> bookInfoDTOs = new ArrayList<BookInfoDTO>();
		bookInfos.forEach(n->{
			BookInfoDTO bookInfoDTO = new BookInfoDTO();
			bookInfoDTO.setBookISBN(n.getBookISBN());
			bookInfoDTO.setBookLanguage(n.getBookISBN());
			bookInfoDTO.setBookName(n.getBookName());
			bookInfoDTO.setBookAuthor(n.getBookAuthor());
			bookInfoDTO.setBookPublisher(n.getBookPublisher());
			bookInfoDTO.setBookPubDate(n.getBookPubDate());
			bookInfoDTO.setBookCreateDate(n.getBookCreateDate());
			bookInfoDTO.setBookStatus(n.getBookStatus());
			bookInfoDTO.setBookBorrowerId(n.getBookBorrowerId());
			bookInfoDTO.setBorrowDate(n.getBorrowDate());
			bookInfoDTOs.add(bookInfoDTO);
		});
		return bookInfoDTOs;
	}

	@Override
	public Response borrowBook(String bookISBN, String bookBorrowerId) {
		
		BookInfo bookInfo = bookJpaRepository.findByBookISBN(bookISBN).orElse(null);
		
		if(bookInfo == null || bookInfo.getBookStatus().equals(BookStatusEnum.Status3.getCode())) {
			return returnCode(ReturnCodeEnum.E001, "資料不存在");
		}
		if(bookInfo.getBookStatus().equals(BookStatusEnum.Status2.getCode())) {
			return returnCode(ReturnCodeEnum.E001, "資料已借出");
		}
		
		bookInfo.setBookBorrowerId(bookBorrowerId);
		bookInfo.setBookStatus(BookStatusEnum.Status2.getCode());
		LocalDate localDate = LocalDate.now();
		bookInfo.setBorrowDate(localDate);
		bookJpaRepository.saveAndFlush(bookInfo);
		
		BorrowLog borrowLog = new BorrowLog();
		borrowLog.setBorrowId(bookBorrowerId);
		borrowLog.setBorrowBookISBN(bookISBN);
		borrowLog.setBorrowAction(BorrowActionEnum.Status1.getCode());
		borrowLog.setActioinDate(localDate);
		logJpaRepository.saveAndFlush(borrowLog);
		
		return returnCode(ReturnCodeEnum.As_SUSS,null);
	}
      
	@Override
	public Response returnBook(String bookISBN, String bookBorrowerId) {
		
		BookInfo  bookInfo = bookJpaRepository.findByBookISBN(bookISBN).orElse(null);
		
		if(bookInfo == null || bookInfo.getBookStatus().equals(BookStatusEnum.Status3.getCode())) {
			//status為3，表示書籍已經毀損或遺失
			return returnCode(ReturnCodeEnum.E001, "資料不存在");
		}
		if(bookInfo.getBookStatus().equals(BookStatusEnum.Status1.getCode())) {
			return returnCode(ReturnCodeEnum.E001, "該書已歸還");
		}
		
		bookInfo.setBookBorrowerId(null);
		bookInfo.setBorrowDate(null);
		bookInfo.setBookStatus(BookStatusEnum.Status1.getCode());
		bookJpaRepository.saveAndFlush(bookInfo);
		
		BorrowLog borrowLog = new BorrowLog();
		borrowLog.setBorrowId(bookBorrowerId);
		borrowLog.setBorrowBookISBN(bookISBN);
		borrowLog.setBorrowAction(BorrowActionEnum.Status2.getCode());
		LocalDate localDate = LocalDate.now();
		borrowLog.setActioinDate(localDate);
		logJpaRepository.saveAndFlush(borrowLog);
		
		return returnCode(ReturnCodeEnum.As_SUSS,null);
	}
	
	@Override
	public OverdueBookDTO overdueBook(int overdue) {
		
		MWHEADER responseObject = new MWHEADER();
		OverdueBookDTO  overdueBookDTO = new OverdueBookDTO();
		
		List<OverdueBookList> overdueBookLists = new ArrayList<OverdueBookList>();
		List<BookInfo> bookInfos = bookJpaRepository.findByBookStatus(BookStatusEnum.Status2.getCode());
		if(bookInfos == null) {
			responseObject.setRETURNCODE(ReturnCodeEnum.As_SUSS.getCode());
			responseObject.setRETURNDESC(ReturnCodeEnum.As_SUSS.getDesc());
			overdueBookDTO.setQueryCount(0);
			overdueBookDTO.setOverDueQueryList(null);
			return overdueBookDTO;
		}
		bookInfos.sort(Comparator
            .comparing(BookInfo::getBorrowDate)
            .thenComparing(BookInfo::getBookBorrowerId)
	    );
		for(int count=0;count<bookInfos.size();count++) {
			BookInfo bookInfo = bookInfos.get(count);
			OverdueBookList overdueBookList = new OverdueBookList();
			overdueBookList.setBookISBN(bookInfo.getBookISBN());
			overdueBookList.setBookLanguage(bookInfo.getBookISBN());
			overdueBookList.setBookName(bookInfo.getBookName());
			overdueBookList.setBookAuthor(bookInfo.getBookAuthor());
			overdueBookList.setBookPublisher(bookInfo.getBookPublisher());
			overdueBookList.setBookPubDate(bookInfo.getBookPubDate());
			overdueBookList.setBookCreateDate(bookInfo.getBookCreateDate());
			overdueBookList.setBookStatus(bookInfo.getBookStatus());
			overdueBookList.setBookBorrowerId(bookInfo.getBookBorrowerId());
			overdueBookList.setBorrowDate(bookInfo.getBorrowDate());
			LocalDate localDate = LocalDate.now();
			Duration durationObject = Duration.between(localDate, bookInfo.getBorrowDate());
			int difference = (int)durationObject.toDays();
			overdueBookList.setOverdueDays(difference);
			if(difference > overdue)
				overdueBookLists.add(overdueBookList);
		}
		responseObject.setRETURNCODE(ReturnCodeEnum.As_SUSS.getCode());
		responseObject.setRETURNDESC(ReturnCodeEnum.As_SUSS.getDesc());
		overdueBookDTO.setMWHEADER(responseObject);
		overdueBookDTO.setQueryCount(bookInfos.size());
		overdueBookDTO.setOverDueQueryList(overdueBookLists);
		
		return overdueBookDTO;
	}
}
