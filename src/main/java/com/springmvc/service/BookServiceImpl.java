package com.springmvc.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.Book;
import com.springmvc.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookRepository bookRepository;

	@Override
	public List<Book> getAllBookList() {
		return bookRepository.getAllBookList();
	}
	@Override
	public List<Book> getBookListByCategory(String category) {
		return bookRepository.getBookListByCategory(category);
	}
	
	@Override
	public Set<Book> getBookListByFilter(Map<String, List<String>> filter) {
		return bookRepository.getBookListByFilter(filter);
	}
	
	@Override
	public Book getBookById(String bookId) {
		return bookRepository.getBookById(bookId);
	}
	
	@Override
	public void setNewBook(Book book) {
		bookRepository.setNewBook(book);
	}
	@Override
	public void setUpdateBook(Book book) {
		bookRepository.setUpdateBook(book);
	}
	@Override
	public void setDeleteBook(String bookID) {
		bookRepository.setDeleteBook(bookID);
	}

}
