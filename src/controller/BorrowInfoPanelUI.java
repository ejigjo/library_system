package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import model.BooksInfo;
import model.SessionManager;
import model.User;
import service.BooksService;
import service.impl.BooksServiceImpl;
import until.DueDateCellRenderer;
import until.PdfUntil;

public class BorrowInfoPanelUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private BooksService booksService = new BooksServiceImpl();
	private DefaultTableModel tableModel;
	private JTable borrowTable;
	private int booksInfoId;
	private User user = SessionManager.getSessionManager().getCurrentUser();
	private String[] columnNames = { "借閱ID", "書籍名稱", "借閱日期", "歸還日期" };
	private List<String> tauntList = List.of("欠錢不還錢，難怪她會離開你。", "這點錢都拿不出來，難怪她會背叛你。", "你以為不給錢她就會回來找你嗎？",
			"與其後悔要給錢，不如後悔沒給她安全感。", "你這麼省錢，應該省下了不少眼淚吧？");

	/**
	 * Constructor
	 */
	public BorrowInfoPanelUI() {
		setLayout(null);

		JLabel titleLabel = new JLabel("借書資訊");
		titleLabel.setFont(new java.awt.Font("標楷體", java.awt.Font.PLAIN, 20));
		titleLabel.setBounds(160, 10, 100, 40);
		add(titleLabel);

		tableModel = new DefaultTableModel(columnNames, 0);
		borrowTable = new JTable(tableModel);

		JScrollPane scrollPane = new JScrollPane(borrowTable);
		scrollPane.setBounds(50, 60, 300, 300);
		add(scrollPane);

		JButton returnButton = new JButton("歸還書本");
		returnButton.setBounds(100, 382, 85, 23);
		add(returnButton);

		JButton exportButton = new JButton("匯出PDF");
		exportButton.setBounds(227, 382, 85, 23);
		add(exportButton);

		loadTableData();
		returnButton.addActionListener(e -> bookReturn());
		
		exportButton.addActionListener(e ->{
			String filePath = "books_list.pdf";
			List<BooksInfo> booksInfosList = booksService.getBooksInfoByUsername(user.getUsername());
			PdfUntil.exportPdf(booksInfosList, filePath);
	});
		
		SessionManager.getSessionManager().setLoadBookInfoUItable(() -> loadTableData());
	}

	private void bookReturn() {
		int selectedRow = borrowTable.getSelectedRow();
		booksInfoId = (int) borrowTable.getValueAt(selectedRow, 0);

		BooksInfo booksInfo = booksService.getBooksInfoById(booksInfoId);
		if (booksInfo.getReturnTime().isBefore(LocalDateTime.now())) {
			int response = showConfirmDialog();

			while (response == JOptionPane.NO_OPTION) {
				String tauntStr = tauntList.get(new Random().nextInt(tauntList.size()));
				JOptionPane.showMessageDialog(null, tauntStr, "錯誤", JOptionPane.ERROR_MESSAGE, null);
				response = showConfirmDialog();
			}
			JOptionPane.showMessageDialog(null, "支付成功,下次記得準時還書", "支付成功", JOptionPane.INFORMATION_MESSAGE, null);
		}

		if (booksService.returnBook(booksInfoId, booksInfo.getBookId())) {
			JOptionPane.showMessageDialog(null, "歸還成功!", "訊息", JOptionPane.INFORMATION_MESSAGE, null);
			loadTableData();
		} else {
			JOptionPane.showMessageDialog(null, "歸還失敗!請聯絡管理員!", "錯誤", JOptionPane.ERROR_MESSAGE, null);
			return;
		}
	}

	private int showConfirmDialog() {
		return JOptionPane.showConfirmDialog(null, "您的書籍已經逾期！是否要支付逾期費用10萬？", "逾期還書", JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE);
	}

	private void loadTableData() {
		TableColumn returnDateColumn = borrowTable.getColumnModel().getColumn(3);
		returnDateColumn.setCellRenderer(new DueDateCellRenderer());
		tableModel.setRowCount(0);
		List<BooksInfo> booksInfosList = booksService.getBooksInfoByUsername(user.getUsername());
		for (BooksInfo booksInfo : booksInfosList) {
			Object[] row = { booksInfo.getBooksInfoId(), booksInfo.getName(), booksInfo.getBorrowTime(),
					booksInfo.getReturnTime() };
			tableModel.addRow(row);
		}
	}
}
