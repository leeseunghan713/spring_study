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
		Book book1 = new Book("ISBN1234", "�ڵ� �����н� ������ ��Ʈ 3 �ڹ� �鿣�� ���� �Թ�", 33000);
		book1.setAuthor("ȫ��");
		book1.setDescription(
				"������ ��Ʈ�� ó�� ���ϴ� �Թ��ڿ� �̹� ���������� �����ϴٰ� ������ �е��� ���� å�Դϴ�. �Խ����� ����� Ŭ���̾�Ʈ�� ������ �����͸� �ְ���� �� ����Ǵ� �ٽ� ���� 3����, MVC ����, JPA, REST API�� ���� �� �������� �ڹ� �鿣�� ������ ������ ������ �� �ֽ��ϴ�. å�� ��ġ�� ���� �ڽŸ��� ������Ʈ�� ���� �� �ְ�, ������ ��ȭ �н��� ���� �� �ִ� ������ �˴ϴ�. �ܰ躰�� ����Ǵ� �ǽ��� ���� �ϴ� ���� �ڿ������� �ʼ� ����� �� �����ϰ� �ǰ� ����� �鿣�� ���߿� ���������� �Թ��� ���� �̴ϴ�.");
		book1.setPublisher("���");
		book1.setCategory("IT������");
		book1.setUnitsInStock(1000);
		book1.setReleaseDate("2020/05/29");

		Book book2 = new Book("ISBN1235", "��ȭ�� ���� IT ��Ļ���", 19800);
		book2.setAuthor("����,�̼�");
		book2.setDescription(
				"*�ϳ�, ��ȭ�� Ǯ� �ű�� ����\r\n"
				+ "\r\n"
				+ "-�ΰ�����, ���ü��, ������ǻ�� �� �̷� ��� �鿩�ٺ���\r\n"
				+ "\r\n"
				+ "*��, ������ ��ũ ����� ���� ����\r\n"
				+ "\r\n"
				+ "-����, �Ƹ���, īī��, KT �� ������� ��� ���� ������\r\n"
				+ "\r\n"
				+ "*��, ������ ���� ���°� �Ϻ� ����\r\n"
				+ "\r\n"
				+ "-�� 3.0 Ư¡�� �Բ� �̴�����, �ٿ�, NFT ��ƺ���\r\n"
				+ "�����κ��� ���ػ�, �ʺ� �����ڱ��� ���� ������ IT ����� ���� �� �ִ� �� �� ���� å!");
		book2.setPublisher("���");
		book2.setCategory("IT������");
		book2.setUnitsInStock(1000);
		book2.setReleaseDate("2020/07/25");

		Book book3 = new Book("ISBN1236", "��OS ������ �����ϱ�", 24000);
		book3.setAuthor("�����");
		book3.setDescription(
				"macOS�� �� �� �� �������? \r\n"
				+ "\r\n"
				+ "���ϴ� �۾��� �ٷ� ������ �� �ֵ��� ����ڸ� �������� ������ �� �ֽ��ϴ�. ���� ����, ���� ����, ��ϰ� ����, ���� �� ��������! macOS�� �پ��� ��ɰ� ���� ��� ������ �°� �����Ͽ� �����մϴ�.\r\n"
				+ "\r\n"
				+ "MS Office�� ��ü�� �� �ִ� ���� �������?\r\n"
				+ "\r\n"
				+ "macOS���� MS Offie�� �Ϻ��ϰ� ��ü�� �� �ִ� iWork�� �ֽ��ϴ�. ����� ����� �� �ִ� Pages, Numbers, Keynote�� ���꼺�� ���� �� �ִ� ��ü���� ����� �����մϴ�.\r\n"
				+ "\r\n"
				+ "�Ը���� ������ ������� ����ϰ� �ͳ���?\r\n"
				+ "\r\n"
				+ "Mac ����/������ ���� ����� ����, �ý��� ȯ�漳���� ��� ������ �ڼ��� �����߽��ϴ�. ���� �Ը���� ������ ������� ����غ�����.\r\n"
				+ "\r\n"
				+ "���� ����� ����ϰ� �ֳ���?\r\n"
				+ "\r\n"
				+ "macOS���� Ư���� ����� ����� �����մϴ�. ����� �ʽ��ϴ�. �׸��� ���մϴ�. �˾� �θ� �ΰ�ΰ� ����� �� �ִ� Automator, �͹̳�, Boot Camp�� ������ ģ���ϰ� �˷��帳�ϴ�.");
		book3.setPublisher("���");
		book3.setCategory("ITȰ�뼭");
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
