package com.cathay.libraryManagement.platform.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cathay.libraryManagement.platform.entity.BookInfo;

public interface BookJpaRepository extends JpaRepository<BookInfo, String>{

	Optional<BookInfo> findByBookISBN(String bookISBN);
	void deleteByBookISBN(String bookISBN);
	List<BookInfo> findAll();
	List<BookInfo> findByBookStatus(String bookStatus);
	
// 下面改用jpa有的方法	
//	@Query("SELECT i FROM BookInfo i WHERE i.bookName LIKE %:keyword%")
//  List<BookInfo> findByKeywordContaining(@Param("keyword") String keyword);
	List<BookInfo> findByBookNameLike(String bookName);
}
