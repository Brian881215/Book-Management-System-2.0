package com.cathay.libraryManagement.platform.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cathay.libraryManagement.platform.enums.ReturnCodeEnum;
import com.cathay.libraryManagement.platform.model.Response;
import com.cathay.libraryManagement.platform.service.impl.BookServiceImpl;

@ControllerAdvice
public class BookSystemExceptionHandler {
	
	@Autowired
	BookServiceImpl bookServiceImpl;
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handle(RuntimeException exception){
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("RuntimeException:"+ exception.getMessage());
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handle(IllegalArgumentException exception){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("IllegalArgumentException:"+ exception.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response> handle(MethodArgumentNotValidException exception){
		
		BindingResult bindingResult =  exception.getBindingResult();
		StringBuilder stringBuilder = new StringBuilder();
		if (bindingResult.hasErrors()) {
	        for (FieldError fieldError : bindingResult.getFieldErrors()) {
	        	stringBuilder.append(fieldError.getDefaultMessage()+",");
	        }
	    }
		//去除掉最後一個error的","
		CharSequence charErrorMessage = stringBuilder.subSequence(0, stringBuilder.length()-1);
		
		Response response = new Response();
		response.getMwheader().setRETURNCODE(ReturnCodeEnum.V001.getCode());
		response.getMwheader().setRETURNDESC(ReturnCodeEnum.V001.getDesc() + ": " + charErrorMessage);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
}
