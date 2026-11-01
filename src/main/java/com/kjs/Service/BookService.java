package com.kjs.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kjs.Entity.Book;
import com.kjs.repository.BookRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookService {
	
	private BookRepository repo;

	public Page<Book> searchBooks(String keyword, String category, int page, int size) {

	    Pageable pageable = PageRequest.of(page, size);

	    return repo.findByTitleContainingIgnoreCaseAndCategoryContainingIgnoreCase(
	            keyword, category, pageable);
	}
	
	public List<Book> getAll() {
		return repo.findAll();
	}

	public Book insert(Book book) {
		return repo.save(book);
	}
}
