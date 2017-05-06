package com.zixue.wy.web;

import java.util.List;

import com.zixue.wy.enums.AppointStateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.zixue.wy.dto.AppointExecution;
import com.zixue.wy.dto.Result;
import com.zixue.wy.entity.Book;
import com.zixue.wy.exception.NoNumberException;
import com.zixue.wy.exception.RepeatAppointException;
import com.zixue.wy.service.BookService;

@Controller
@RequestMapping("/book") // url:/模块/资源/{id}/细分 /seckill/list
public class BookController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BookService bookService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	private List<Book> list(Model model) {
		List<Book> list = bookService.getList();
		// model.addAttribute("list", list);
		// list.jsp + model = ModelAndView
		return list;// WEB-INF/jsp/"list".jsp
	}

	@RequestMapping(value = "/{bookId}/detail", method = RequestMethod.GET)
	private String detail(@PathVariable("bookId") Long bookId, Model model) {
		if (bookId == null) {
			System.out.println(1);
			return "redirect:/book/list";
		}
		Book book = bookService.getById(bookId);
		if (book == null) {
			System.out.println(2);
			return "forward:/book/list";
		}
		model.addAttribute("book", book);
		System.out.println(3);
		return "forward:/book/list";
	}

	// ajax json
	@RequestMapping(value = "/{bookId}/appoint", method = RequestMethod.POST, produces = {
			"application/json; charset=utf-8" })
	@ResponseBody
	private Result<AppointExecution> appoint(@PathVariable("bookId") Long bookId, @RequestParam("studentId") Long studentId) {
		if (studentId == null || studentId.equals("")) {
			return new Result<AppointExecution>(false, "学号不能为空");
		}
		AppointExecution execution = null;
		try {
			execution = bookService.appoint(bookId, studentId);
		} catch (NoNumberException e1) {
			execution = new AppointExecution(bookId, AppointStateEnum.NO_NUMBER);
		} catch (RepeatAppointException e2) {
			execution = new AppointExecution(bookId, AppointStateEnum.REPEAT_APPOINT);
		} catch (Exception e) {
			execution = new AppointExecution(bookId, AppointStateEnum.INNER_ERROR);
		}
		return new Result<AppointExecution>(true, execution);
	}

}
