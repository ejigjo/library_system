package until;

import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.BooksInfo;

public class PdfUntil {

	public static void exportPdf(List<BooksInfo> booksInfosList, String filePath) {
		Document document = new Document();
		try {

			PdfWriter.getInstance(document, new FileOutputStream(filePath));
			document.open();

			BaseFont baseFont = BaseFont.createFont("C:/Windows/Fonts/msjh.ttc,1", BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);
			Font titleFont = new Font(baseFont, 18, Font.BOLD);
			Font headerFont = new Font(baseFont, 12, Font.BOLD);
			Font textFont = new Font(baseFont, 10, Font.NORMAL);

			// 設置標題
			Paragraph title = new Paragraph("借書資訊", titleFont);
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);
			document.add(new Paragraph("\n"));

			// 創建表格
			PdfPTable table = new PdfPTable(4); // 4列
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2, 6, 4, 4 }); // 調整欄位寬度
			table.setHeaderRows(1); // 設置第一行為表頭

			// 設置表頭
			BaseColor headerColor = new BaseColor(192, 192, 192); // 灰色背景

			String[] headers = { "ID", "書名", "借書時間", "需要還書時間" };
			for (String header : headers) {
				PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
				headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				headerCell.setBackgroundColor(headerColor);
				table.addCell(headerCell);
			}

			// 填充書籍數據
			for (BooksInfo book : booksInfosList) {
				table.addCell(new PdfPCell(new Phrase(String.valueOf(book.getBookId()), textFont)));
				table.addCell(new PdfPCell(new Phrase(book.getName(), textFont)));
				table.addCell(new PdfPCell(new Phrase(book.getBorrowTime().toString(), textFont)));
				table.addCell(new PdfPCell(new Phrase(book.getReturnTime().toString(), textFont)));
			}

			document.add(table);
			document.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
