package service;

import java.util.List;

import model.Book;
import model.BooksInfo;

public interface BooksService {
	
	public boolean repeatBookName(String inputBookName);
	
	public boolean returnBook(int booksInfoId, int bookId);
	
	public boolean borrowBook(Book book, String username);
	
	public boolean checkRepeatBorrow(int id);
	
	public BooksInfo getBooksInfoById(int id);
	
	public boolean deleteBooksInfoById(int id);
	
	public boolean updateBooksQuantityPlus(Book book);
	
	public boolean deleteBookById(int id);
	
	public boolean insertBook(Book book);
	
	public List<Book> getBooksByAuthor(String author);
	
	public List<Book> getBooksByName(String name);
	
	public List<Book> getBooksAll();

	public Book getBookById(int id);

	public boolean updateBooksQuantity(Book book);

	public boolean insertBooksInfo(String username, Book book);

	public List<BooksInfo> getBooksInfoByUsername(String username);

}
