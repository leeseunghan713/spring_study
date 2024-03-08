package com.springmvc.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.Book;
import com.springmvc.exception.BookIdException;

@Repository
public class BookRepositoryImpl implements BookRepository {

	private JdbcTemplate template;

	@Autowired
	public void setJdbctemplate(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	private List<Book> listOfBooks = new ArrayList<Book>();

	public BookRepositoryImpl() {
		Book book1 = new Book("ISBN1234", "코딩 자율학습 스프링 부트 3 자바 백엔드 개발 입문", 33000);
		book1.setAuthor("홍팍");
		book1.setDescription(
				"스프링 부트를 처음 접하는 입문자와 이미 공부했지만 부족하다고 느끼는 분들을 위한 책입니다. 게시판을 만들며 클라이언트와 서버가 데이터를 주고받을 때 적용되는 핵심 개념 3가지, MVC 패턴, JPA, REST API를 배우고 그 과정에서 자바 백엔드 개발의 전반을 이해할 수 있습니다. 책을 마치고 나면 자신만의 프로젝트를 만들 수 있고, 스프링 심화 학습을 따라갈 수 있는 수준이 됩니다. 단계별로 진행되는 실습을 따라 하다 보면 자연스럽게 필수 개념과 용어를 이해하게 되고 어느새 백엔드 개발에 성공적으로 입문해 있을 겁니다.");
		book1.setPublisher("길벗");
		book1.setCategory("IT전문서");
		book1.setUnitsInStock(1000);
		book1.setReleaseDate("2020/05/29");

		Book book2 = new Book("ISBN1235", "만화로 보는 IT 상식사전", 19800);
		book2.setAuthor("윤진,이솔");
		book2.setDescription(
				"*하나, 만화로 풀어낸 신기술 지식\r\n"
				+ "\r\n"
				+ "-인공지능, 블록체인, 양자컴퓨터 등 미래 기술 들여다보기\r\n"
				+ "\r\n"
				+ "*둘, 국내외 테크 기업의 동향 수록\r\n"
				+ "\r\n"
				+ "-구글, 아마존, 카카오, KT 등 기업들의 기술 전쟁 엿보기\r\n"
				+ "\r\n"
				+ "*셋, 디지털 경제 생태계 완벽 설명\r\n"
				+ "\r\n"
				+ "-웹 3.0 특징과 함께 이더리움, 다오, NFT 톺아보기\r\n"
				+ "직장인부터 취준생, 초보 투자자까지 쉽고 빠르게 IT 상식을 쌓을 수 있는 단 한 권의 책!");
		book2.setPublisher("길벗");
		book2.setCategory("IT전문서");
		book2.setUnitsInStock(1000);
		book2.setReleaseDate("2020/07/25");

		Book book3 = new Book("ISBN1236", "맥OS 무작정 따라하기", 24000);
		book3.setAuthor("고경희");
		book3.setDescription(
				"macOS로 뭘 할 수 있을까요? \r\n"
				+ "\r\n"
				+ "원하는 작업을 바로 시작할 수 있도록 사용자를 기준으로 구분할 수 있습니다. 파일 관리, 정보 수집, 기록과 편집, 유지 및 관리까지! macOS의 다양한 기능과 앱을 사용 목적에 맞게 구분하여 설명합니다.\r\n"
				+ "\r\n"
				+ "MS Office를 대체할 수 있는 앱은 없을까요?\r\n"
				+ "\r\n"
				+ "macOS에는 MS Offie를 완벽하게 대체할 수 있는 iWork가 있습니다. 무료로 사용할 수 있는 Pages, Numbers, Keynote로 생산성을 높일 수 있는 구체적인 방법을 설명합니다.\r\n"
				+ "\r\n"
				+ "입맛대로 설정해 마음대로 사용하고 싶나요?\r\n"
				+ "\r\n"
				+ "Mac 유지/관리를 위한 기능은 물론, 시스템 환경설정의 모든 설정을 자세히 정리했습니다. 이제 입맛대로 설정해 마음대로 사용해보세요.\r\n"
				+ "\r\n"
				+ "정말 제대로 사용하고 있나요?\r\n"
				+ "\r\n"
				+ "macOS만의 특별한 기능을 제대로 설명합니다. 어렵지 않습니다. 그리고 편리합니다. 알아 두면 두고두고 써먹을 수 있는 Automator, 터미널, Boot Camp의 사용법을 친절하게 알려드립니다.");
		book3.setPublisher("길벗");
		book3.setCategory("IT활용서");
		book3.setUnitsInStock(1000);
		book3.setReleaseDate("2019/05/29");

		listOfBooks.add(book1);
		listOfBooks.add(book2);
		listOfBooks.add(book3);

	}

	@Override
	public List<Book> getAllBookList() {
		String SQL = "SELECT * FROM book";
		List<Book> listOfBooks = template.query(SQL, new BookRowMapper());
		return listOfBooks;
	}

	@Override
	public List<Book> getBookListByCategory(String category) {
		List<Book> booksByCategory = new ArrayList<Book>();
		String SQL = "SELECT * FROM book where b_category LIKE '%" + category + "%'";
		booksByCategory = template.query(SQL, new BookRowMapper());
		return booksByCategory;
	}

	@Override
	public Set<Book> getBookListByFilter(Map<String, List<String>> filter) {
		Set<Book> booksByPublisher = new HashSet<Book>();
		Set<Book> booksByCategory = new HashSet<Book>();
		Set<String> criterias = filter.keySet();
		if (criterias.contains("publisher")) {
			for (int j = 0; j < filter.get("publisher").size(); j++) {
				String publisherName = filter.get("publisher").get(j);
				String SQL = "SELECT * FROM book where b_publisher LIKE '%" + publisherName + "%'";
				booksByPublisher.addAll(template.query(SQL, new BookRowMapper()));
			}
		}

		if (criterias.contains("category")) {
			for (int i = 0; i < filter.get("category").size(); i++) {
				String category = filter.get("category").get(i);
				String SQL = "SELECT * FROM book where b_category LIKE '%" + category + "%'";
				booksByCategory.addAll(template.query(SQL, new BookRowMapper()));
			}
		}
		booksByCategory.retainAll(booksByPublisher);
		return booksByCategory;
	}

	@Override
	public Book getBookById(String bookId) {
		Book bookInfo = null;
		String SQL = "SELECT count(*) FROM book where b_bookId=?";
		int rowCount = template.queryForObject(SQL, Integer.class, bookId);
		if (rowCount != 0) {
			SQL = "SELECT * FROM book where b_bookId=?";
			bookInfo = template.queryForObject(SQL, new Object[] { bookId }, new BookRowMapper());
		}
		if (bookInfo == null)
			throw new BookIdException(bookId);
		return bookInfo;
	}

	@Override
	public void setNewBook(Book book) {
        String SQL = "INSERT INTO book (b_bookId, b_name, b_unitPrice, b_author, b_description, b_publisher, b_category, b_unitsInStock, b_releaseDate, b_condition, b_fileName) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        template.update(SQL, book.getBookId(), book.getName(), book.getUnitPrice(), book.getAuthor(), book.getDescription(), book.getPublisher(), book.getCategory(), book.getUnitsInStock(), book.getReleaseDate(), book.getCondition(), book.getFileName());
    }
	
	@Override
	public void setUpdateBook(Book book) {
	    if (book.getFileName() != null) {
	        String SQL = "UPDATE book SET b_name = ?, b_unitPrice = ?, b_author = ?, b_description = ?, b_publisher = ?, b_category = ?, b_unitsInStock = ?, b_releaseDate = ?, b_condition = ?, b_fileName = ? where b_bookId = ? ";

	        template.update(SQL, book.getName(), book.getUnitPrice(), book.getAuthor(), book.getDescription(), book.getPublisher(), book.getCategory(), book.getUnitsInStock(), book.getReleaseDate(), book.getCondition(), book.getFileName(), book.getBookId());
	    } else if (book.getFileName() == null) {

	    String SQL = "UPDATE book SET b_name = ?, b_unitPrice = ?, b_author = ?, b_description = ?, b_publisher = ?, b_category = ?, b_unitsInStock = ?, b_releaseDate = ?, b_condition = ? where b_bookId = ? ";

	    template.update(SQL, book.getName(), book.getUnitPrice(), book.getAuthor(), book.getDescription(), book.getPublisher(), book.getCategory(), book.getUnitsInStock(), book.getReleaseDate(), book.getCondition(), book.getBookId());
	    }
	}
	
	@Override
	public void setDeleteBook(String bookID) {
		String SQL = "DELETE from Book where b_bookId = ? ";
        this.template.update(SQL, bookID);
	}
}
