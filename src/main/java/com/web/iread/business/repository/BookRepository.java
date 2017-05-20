package com.web.iread.business.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.web.iread.business.entity.Book;


public interface BookRepository extends
		PagingAndSortingRepository<Book, Long>,
		JpaSpecificationExecutor<Book> {
	public Book findByIdAndStatusNot(Long id,Integer status);
}
