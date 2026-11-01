package com.kjs.Controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kjs.Entity.Book;
import com.kjs.Service.BookService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/book")
public class BookController {
	
	private BookService service;
	
	

	@GetMapping("/search")
	public ResponseEntity<Page<Book>> searchBooks(@RequestParam String keyword,@RequestParam String category, @RequestParam(defaultValue = "0") int page,   @RequestParam(defaultValue = "5") int size) {

	    Page<Book> li = service.searchBooks(keyword, category, page, size);

	    return ResponseEntity.status(HttpStatus.OK).body(li);
	}
	
	@GetMapping("/getall")
	public ResponseEntity<List<Book>> getAll()
	{
		List<Book> li = service.getAll();
		
		if(!li.isEmpty())
			return ResponseEntity.status(HttpStatus.OK).body(li);
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@PostMapping("/insert")
	public ResponseEntity<Book> insert(@RequestBody Book book)
	{
			Book b = service.insert(book);
			if(b!=null)
			return ResponseEntity.status(HttpStatus.OK).body(b);
			else
				return ResponseEntity.status(HttpStatus.GONE).build();
	}
	
	

}
