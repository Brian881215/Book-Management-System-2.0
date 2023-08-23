package com.cathay.libraryManagement.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cathay.libraryManagement.platform.entity.BorrowLog;

public interface LogJpaRepository extends JpaRepository<BorrowLog, String>{
	
}
