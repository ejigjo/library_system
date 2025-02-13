package dao.impl;

import java.util.List;

import dao.BooksDao;
import model.Book;
import model.BooksInfo;
import until.SqlUntil;

public class BooksDaoImpl implements BooksDao {

	public List<BooksInfo> getBooksInfoByBookId(int id) {
		String sql = "SELECT * FROM books_info WHERE book_id = ?";
		return SqlUntil.excuteQuery(sql, BooksInfo.class, id);
	}

	public BooksInfo getBooksInfoById(int id) {
		String sql = "SELECT * FROM books_info WHERE books_info_id = ?";
		return SqlUntil.excuteSingleQuery(sql, BooksInfo.class, id);
	}

	public boolean updateBooksQuantityPlus(Book book) {
		String sql = "update books set quantity = ? where book_id = ? ";
		return SqlUntil.excuteUpdate(sql, book.getQuantity(), book.getBookId());
	}

	public boolean deleteBooksInfoById(int id) {
		String sql = "DELETE FROM books_info WHERE books_info_id =?";
		return SqlUntil.excuteUpdate(sql, id);
	}

	public boolean deleteBookById(int id) {
		String sql = "DELETE FROM books WHERE book_id =?";
		return SqlUntil.excuteUpdate(sql, id);
	}

	public boolean insertBook(Book book) {
		String sql = "INSERT INTO books(name,author,quantity,level) " + "VALUES(?,?,?,?)";
		return SqlUntil.excuteUpdate(sql, book.getName(), book.getAuthor(), book.getQuantity(), book.getLevel());
	}

	public List<Book> getBooksByName(String name) {
		String sql = "SELECT * FROM books WHERE name LIKE ?";
		return SqlUntil.excuteQuery(sql, Book.class, "%" + name + "%");
	}

	public List<Book> getBooksByAuthor(String author) {
		String sql = "SELECT * FROM books WHERE author LIKE ?";
		return SqlUntil.excuteQuery(sql, Book.class, "%" + author + "%");
	}

	public List<Book> getBooksAll() {
		String sql = "select * from books";
		return SqlUntil.excuteQuery(sql, Book.class);
	}

	public Book getBookById(int id) {
		String sql = "select * from books where book_id = ?";
		return SqlUntil.excuteSingleQuery(sql, Book.class, id);
	}

	public boolean updateBooksQuantity(Book book) {
		String sql = "update books set quantity = ? where book_id = ? ";
		int quantity = book.getQuantity();
		if (quantity <= 0) {
			return false;
		}
		return SqlUntil.excuteUpdate(sql, book.getQuantity(), book.getBookId());
	}

	public boolean insertBooksInfo(String username, BooksInfo booksInfo) {
		String sql = "INSERT INTO books_info(name,username,borrow_time,return_time,book_id)" + "VALUES(?,?,?,?,?)";
		return SqlUntil.excuteUpdate(sql, booksInfo.getName(), username, booksInfo.getBorrowTime(),
				booksInfo.getReturnTime(), booksInfo.getBookId());
	}

	public List<BooksInfo> getBooksInfoByUsername(String username) {
		String sql = "SELECT * FROM books_info WHERE username = ?";
		return SqlUntil.excuteQuery(sql, BooksInfo.class, username);
	}

}
