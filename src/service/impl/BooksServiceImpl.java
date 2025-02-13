package service.impl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import model.Book;
import model.BooksInfo;
import service.BooksService;
import until.SqlUntil;
import dao.BooksDao;
import dao.impl.BooksDaoImpl;

public class BooksServiceImpl implements BooksService {

	private BooksDao booksDao = new BooksDaoImpl();

	public boolean returnBook(int booksInfoId, int bookId) {
		try {
			SqlUntil.beginTransaction();

			Book book = booksDao.getBookById(bookId);
			boolean bookDeleteBoolean = booksDao.deleteBooksInfoById(booksInfoId);
			if (!bookDeleteBoolean) {
				throw new SQLException("刪除書籍失敗");
			}
			book.setQuantity((book.getQuantity() + 1));
			boolean bookUpdateBoolean = booksDao.updateBooksQuantityPlus(book);
			if (!bookUpdateBoolean) {
				throw new SQLException("借書資訊更新失敗");
			}
			SqlUntil.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			SqlUntil.rollbackTransaction();
			return false;
		} finally {
			SqlUntil.closeConnection();
		}

		return true;
	}

	public boolean borrowBook(Book book, String username) {

		try {

			SqlUntil.beginTransaction();
			book.setQuantity((book.getQuantity() - 1));
			boolean quantityUpdated = booksDao.updateBooksQuantity(book);

			if (!quantityUpdated) {
				throw new SQLException("更新書籍書量失敗");
			}
			boolean infoInserted = insertBooksInfo(username, book);
			if (!infoInserted) {
				throw new SQLException("新增借書資訊失敗");
			}

			SqlUntil.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			SqlUntil.rollbackTransaction();
			return false;
		} finally {
			SqlUntil.closeConnection();
		}

		return true;
	}

	public boolean checkRepeatBorrow(int id) {
		List<BooksInfo> booksInfosList = booksDao.getBooksInfoByBookId(id);
		return booksInfosList.size() > 0;
	}

	@Override
	public List<Book> getBooksAll() {
		return booksDao.getBooksAll();
	}

	@Override
	public Book getBookById(int id) {
		return booksDao.getBookById(id);
	}

	@Override
	public boolean updateBooksQuantity(Book book) {
		book.setQuantity(book.getQuantity() - 1);

		return booksDao.updateBooksQuantity(book);
	}

	@Override
	public boolean insertBooksInfo(String username, Book book) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime timeLimit = null;
		BooksInfo booksInfo = new BooksInfo();
		switch (book.getLevel()) {
		case 1:
			timeLimit = now.plusDays(30);
			break;
		case 2:
			timeLimit = now.plusDays(14);
			break;
		case 3:
			timeLimit = now.plusDays(7);
			break;
		}
		booksInfo.setBorrowTime(now);
		booksInfo.setReturnTime(timeLimit);
		booksInfo.setName(book.getName());
		booksInfo.setBookId(book.getBookId());
		return booksDao.insertBooksInfo(username, booksInfo);
	}

	@Override
	public List<BooksInfo> getBooksInfoByUsername(String username) {
		return booksDao.getBooksInfoByUsername(username);
	}

	@Override
	public List<Book> getBooksByName(String name) {
		return booksDao.getBooksByName(name);
	}

	@Override
	public List<Book> getBooksByAuthor(String author) {
		return booksDao.getBooksByAuthor(author);
	}
	
	public boolean repeatBookName(String inputBookName) {
		List<Book> booksList = getBooksAll();
		for(Book book:booksList) {
			if(book.getName().equals(inputBookName)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean insertBook(Book book) {
		return booksDao.insertBook(book);
	}

	@Override
	public boolean deleteBookById(int id) {
		return booksDao.deleteBookById(id);
	}

	@Override
	public boolean deleteBooksInfoById(int id) {
		return booksDao.deleteBooksInfoById(id);
	}

	@Override
	public boolean updateBooksQuantityPlus(Book book) {
		book.setQuantity(book.getQuantity() + 1);
		return booksDao.updateBooksQuantityPlus(book);
	}

	@Override
	public BooksInfo getBooksInfoById(int id) {
		return booksDao.getBooksInfoById(id);
	}

}
