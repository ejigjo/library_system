package model;

import java.time.LocalDateTime;

public class BooksInfo {
	private int books_info_id;
	private String name;
	private String username;
	private int book_id;
	private LocalDateTime borrow_time;
	private LocalDateTime return_time;
	
	public BooksInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BooksInfo(int booksInfoId,String name ,String username, int bookId,LocalDateTime borrowTime,
			LocalDateTime returnTime) {
		super();
		this.books_info_id = booksInfoId;
		this.name = name;
		this.username = username;
		this.borrow_time = borrowTime;
		this.return_time = returnTime;
		this.book_id=bookId;
	}

	public int getBooksInfoId() {
		return books_info_id;
	}

	public void setBooksInfoId(int booksInfoId) {
		this.books_info_id = booksInfoId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name=name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDateTime getBorrowTime() {
		return borrow_time;
	}

	public void setBorrowTime(LocalDateTime borrowTime) {
		this.borrow_time = borrowTime;
	}

	public LocalDateTime getReturnTime() {
		return return_time;
	}

	public void setReturnTime(LocalDateTime returnTime) {
		this.return_time = returnTime;
	}

	public int getBookId() {
		return book_id;
	}

	public void setBookId(int bookId) {
		this.book_id = bookId;
	}
	
}
