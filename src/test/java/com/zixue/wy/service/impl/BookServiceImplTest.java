package com.zixue.wy.service.impl;

import static org.junit.Assert.fail;

import com.zixue.wy.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zixue.wy.dto.AppointExecution;
import com.zixue.wy.service.BookService;

public class BookServiceImplTest extends BaseTest {

	@Autowired
	private BookService bookService;

	@Test
	public void testAppoint() throws Exception {
		long bookId = 1001;
		long studentId = 12345678910L;
		AppointExecution execution = bookService.appoint(bookId, studentId);
		System.out.println(execution);
	}

}
