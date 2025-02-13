package controller;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Book;
import model.SessionManager;
import service.BooksService;
import service.impl.BooksServiceImpl;

import java.awt.Font;

public class AddBookUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField authorField;
	private JTextField nameField;
	private JTextField quantityField;
	private JTextField levelField;
	private BooksService booksService = new BooksServiceImpl();

	public AddBookUI() {
		setTitle("新增書籍");
		setBounds(150, 150, 400, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel authorLabel = new JLabel("作者:");
		authorLabel.setBounds(30, 70, 80, 25);
		contentPane.add(authorLabel);

		authorField = new JTextField();
		authorField.setBounds(120, 70, 200, 25);
		contentPane.add(authorField);

		JLabel nameLabel = new JLabel("書名:");
		nameLabel.setBounds(30, 110, 80, 25);
		contentPane.add(nameLabel);

		nameField = new JTextField();
		nameField.setBounds(120, 110, 200, 25);
		contentPane.add(nameField);

		JLabel quantityLabel = new JLabel("數量:");
		quantityLabel.setBounds(30, 150, 80, 25);
		contentPane.add(quantityLabel);

		quantityField = new JTextField();
		quantityField.setBounds(120, 150, 200, 25);
		contentPane.add(quantityField);

		JLabel levelLabel = new JLabel("等級:");
		levelLabel.setBounds(30, 190, 80, 25);
		contentPane.add(levelLabel);

		levelField = new JTextField();
		levelField.setBounds(120, 190, 200, 25);
		contentPane.add(levelField);

		JButton saveButton = new JButton("保存");
		saveButton.setBounds(150, 230, 100, 30);
		contentPane.add(saveButton);

		JLabel addBookLabel = new JLabel("新增書籍");
		addBookLabel.setFont(new Font("標楷體", Font.PLAIN, 20));
		addBookLabel.setBounds(139, 22, 100, 31);
		contentPane.add(addBookLabel);
		

		// 保存按鈕點擊事件
		saveButton.addActionListener(e -> saveBook());
	}

	private void saveBook() {
		String author = authorField.getText().trim();
		String name = nameField.getText().trim();
		String quantityText = quantityField.getText().trim();
		String levelText = levelField.getText().trim();

		// 簡單檢查輸入數據
		if (author.isEmpty() || name.isEmpty() || quantityText.isEmpty() || levelText.isEmpty()) {
			JOptionPane.showMessageDialog(null, "所有欄位都必須填寫！", "錯誤", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (!(quantityText.matches("\\d+")) || !(levelText.matches("\\d+"))) {
			JOptionPane.showMessageDialog(null, "書本數量與等級必須是正整數！", "錯誤", JOptionPane.ERROR_MESSAGE);
			return;
		}

		int quantity = Integer.parseInt(quantityField.getText().trim());
		int level = Integer.parseInt(levelField.getText().trim());

		if (!(quantity > 0)) {
			JOptionPane.showMessageDialog(null, "書本數量必須大於0！", "錯誤", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!(0 < level && level<= 3)) {
			JOptionPane.showMessageDialog(null, "書本等級必須1~3！", "錯誤", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(booksService.repeatBookName(name)) {
			JOptionPane.showMessageDialog(null, "不能新增同一本書喔!", "錯誤", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		Book book = new Book();
		book.setAuthor(author);
		book.setLevel(level);
		book.setName(name);
		book.setQuantity(quantity);

		if (!booksService.insertBook(book)) {
			JOptionPane.showMessageDialog(null, "新增失敗請聯絡系統管理員！", "錯誤", JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(null, "新增成功!", "資訊", JOptionPane.INFORMATION_MESSAGE);
		SessionManager.getSessionManager().loadBookUITable();
		dispose();
	}
}
