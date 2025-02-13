package model;

public class Book implements Comparable<Book> {
	private int book_id;
	private String author;
	private String name;
	private int quantity;
	private int level;

	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Book(int bookId, String author, String name, int quantity, int level) {
		super();
		this.book_id = bookId;
		this.author = author;
		this.name = name;
		this.quantity = quantity;
		this.level = level;
	}

	public int getBookId() {
		return book_id;
	}

	public void setBookId(int bookId) {
		this.book_id = bookId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public int compareTo(Book other) {
		return Integer.compare(this.book_id, other.getBookId());
	}

}
