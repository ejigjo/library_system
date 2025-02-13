package dao;

import java.util.List;

import model.Book;
import model.BooksInfo;

public interface BooksDao {
	public List<BooksInfo> getBooksInfoByBookId(int id);
	
	public BooksInfo getBooksInfoById(int id);
	
	public boolean updateBooksQuantityPlus(Book book);
	
	public boolean deleteBooksInfoById(int id);
	
	public boolean deleteBookById(int id);
	
	public boolean insertBook(Book book);
	
	public List<Book> getBooksByAuthor(String author);
	
	public List<Book> getBooksByName(String name);
	
	public List<Book> getBooksAll();
	
	public Book getBookById(int id);
	
	public boolean updateBooksQuantity(Book book);
	
	public boolean insertBooksInfo(String username, BooksInfo booksInfo);
	
	public List<BooksInfo> getBooksInfoByUsername(String username);
}
