package com.kjs.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kjs.Entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	
	Page<Book> findByTitleContainingIgnoreCaseAndCategoryContainingIgnoreCase(
	        String keyword, String category, Pageable pageable);
}
