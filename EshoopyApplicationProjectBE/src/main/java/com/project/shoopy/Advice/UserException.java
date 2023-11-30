package com.project.shoopy.Advice;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class UserException {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleMethodArgument(MethodArgumentNotValidException error)
	{
		Map<String, String> err=new HashMap<>();
		error.getBindingResult().getFieldErrors().forEach(e->{
			err.put(e.getField(),e.getDefaultMessage());
		});
		return err;
	}
	
	
}
