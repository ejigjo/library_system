package controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.BorderLayout;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import model.Book;
import model.SessionManager;
import model.User;
import service.BooksService;
import service.impl.BooksServiceImpl;

public class LibraryUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel tableModel;
	private BooksService booksService = new BooksServiceImpl();
	private int bookId = -1;
	private final String[] columnNames = { "書籍ID", "作者", "書名", "剩餘數量" };
	private User user = SessionManager.getSessionManager().getCurrentUser();
	private List<Book> booksList = booksService.getBooksAll();
	private JTextField searchField;
	private JButton searchButton;
	private JButton borrowButton;
	private JButton addButton;
	private JButton deleteButton;
	private JComboBox<String> selectedComboBox;
	private JLabel timeLabel;
	JLabel sortLabel;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private boolean isAscending = true;
	JComboBox<String> sortComboBox = new JComboBox<String>();

	/**
	 * Constructor
	 */
	public LibraryUI() {
		setLayout(null);
		initUI();
		addEventListeners();
	}

	@SuppressWarnings("null")
	private void initUI() {
		JLabel titleLabel = new JLabel("書本才不會劈腿x圖書館");
		titleLabel.setFont(new Font("標楷體", Font.PLAIN, 20));
		titleLabel.setBounds(78, 10, 210, 40);
		add(titleLabel);

		searchField = new JTextField();
		searchField.setBounds(52, 60, 138, 21);
		add(searchField);

		searchButton = new JButton("搜尋");
		searchButton.setBounds(261, 59, 62, 23);
		add(searchButton);

		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
		table.setRowSorter(sorter);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(52, 123, 271, 204);
		add(scrollPane);

		borrowButton = new JButton("借閱書籍");
		borrowButton.setBounds(52, 359, 85, 23);
		add(borrowButton);

		addButton = new JButton("新增書籍");
		addButton.setBounds(143, 359, 85, 23);
		add(addButton);

		deleteButton = new JButton("刪除書籍");
		deleteButton.setBounds(238, 359, 85, 23);
		add(deleteButton);

		managerButtonDisplay();

		selectedComboBox = new JComboBox<>();
		selectedComboBox.setBounds(200, 59, 51, 23);
		selectedComboBox.addItem("書名");
		selectedComboBox.addItem("作者");
		add(selectedComboBox);

		sortComboBox.setBounds(200, 90, 51, 23);
		add(sortComboBox);
		sortComboBox.addItem("依書籍ID");
		sortComboBox.addItem("依書名");
		sortComboBox.addItem("依作者");
		sortComboBox.addItem("依數量");

		sortLabel = new JLabel("排序");
		sortLabel.setBounds(279, 94, 29, 15);
		add(sortLabel);

		timeLabel = new JLabel();
		timeLabel.setBounds(261, 412, 161, 15);
		timeLabel.setFont(new Font("Arial", Font.BOLD, 16));
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(timeLabel, BorderLayout.CENTER);

		Timer timer = new Timer(1000, e -> {
			timeLabel.setText(dateFormat.format(new Date()));
		});

		timer.start();

		loadTableData("ALL");
	}

	private void addEventListeners() {
		addButton.addActionListener(e -> bookAdd());

		searchButton.addActionListener(e -> loadTableData((String) (selectedComboBox.getSelectedItem()), searchField.getText()));

		deleteButton.addActionListener(e -> bookDelete());

		borrowButton.addActionListener(e -> bookBorrow());
		
		sortComboBox.addActionListener(e -> sortBooksList());

		SessionManager.getSessionManager().setLoadBookUITable(() -> loadTableData("ALL"));
	}

	private void sortBooksList() {
		booksList = booksService.getBooksAll();
		String sortSelected = (String) (sortComboBox.getSelectedItem());
		Comparator<Book> comparator = null;
		
		switch (sortSelected) {
		  case "依書籍ID":
		        comparator = Comparator.comparing(Book::getBookId);
		        break;
		    case "依書名":
		        comparator = Comparator.comparing(Book::getName);
		        break;
		    case "依作者":
		        comparator = Comparator.comparing(Book::getAuthor);
		        break;
		    case "依數量":
		        comparator = Comparator.comparing(Book::getQuantity);
		        break;
		    default:
		        break;
		}
	    
		if (comparator != null) {
		    if (!isAscending) {
		        comparator = comparator.reversed(); // 反轉排序
		    }
		    booksList.sort(comparator);
		    isAscending = !isAscending; // 切換排序方向
		}

		tableModel.setRowCount(0);
		for (Book book : booksList) {
			Object[] row = { book.getBookId(), book.getAuthor(), book.getName(), book.getQuantity() };
			tableModel.addRow(row);
		}
		
	}
	
	private void bookAdd() {
		AddBookUI addBookUI = new AddBookUI();
		addBookUI.setVisible(true);
	}

	private void bookBorrow() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(null, "沒有選擇書籍!", "錯誤", JOptionPane.ERROR_MESSAGE, null);
			return;
		}
		bookId = (int) table.getValueAt(selectedRow, 0);
		Book book = booksService.getBookById(bookId);
		if (book.getQuantity() <= 0) {
			JOptionPane.showMessageDialog(null, "書已經被借完囉!", "錯誤", JOptionPane.ERROR_MESSAGE, null);
			return;
		}
		if (booksService.checkRepeatBorrow(bookId)) {
			JOptionPane.showMessageDialog(null, "這本書已經借過囉!同一本書只能借一本!", "錯誤", JOptionPane.ERROR_MESSAGE, null);
			return;
		}

		if (booksService.borrowBook(book, user.getUsername())) {
			JOptionPane.showMessageDialog(null, "借閱成功!", "訊息", JOptionPane.INFORMATION_MESSAGE, null);
			loadTableData("ALL");
			SessionManager.getSessionManager().loadBookInfoUITable();
		} else {
			JOptionPane.showMessageDialog(null, "借閱失敗!請聯絡管理員!", "錯誤", JOptionPane.ERROR_MESSAGE, null);
			return;
		}
	}

	private void bookDelete() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(null, "沒有選擇書籍!", "錯誤", JOptionPane.ERROR_MESSAGE, null);
			return;
		}
		bookId = (int) table.getValueAt(selectedRow, 0);
		if (!booksService.deleteBookById(bookId)) {
			JOptionPane.showMessageDialog(null, "刪除失敗請聯繫系統管理員!", "錯誤", JOptionPane.ERROR_MESSAGE, null);
			return;
		}
		JOptionPane.showMessageDialog(null, "刪除成功!", "訊息", JOptionPane.INFORMATION_MESSAGE, null);
		loadTableData("ALL");
	}

	private void loadTableData(String criteria, String... values) {
		switch (criteria) {
		case "ALL":
			booksList = booksService.getBooksAll();
			break;
		case "書名":
			booksList = booksService.getBooksByName(values[0]);
			break;
		case "作者":
			booksList = booksService.getBooksByAuthor(values[0]);
			break;
		default:
			JOptionPane.showMessageDialog(null, "畫面加載錯誤！", "錯誤", JOptionPane.ERROR_MESSAGE);
			return;
		}

		tableModel.setRowCount(0);
		for (Book book : booksList) {
			Object[] row = { book.getBookId(), book.getAuthor(), book.getName(), book.getQuantity() };
			tableModel.addRow(row);
		}
	}

	private void managerButtonDisplay() {
		boolean isManger = (user.getLevel() == 2);
		addButton.setVisible(isManger);
		deleteButton.setVisible(isManger);

	}
}
