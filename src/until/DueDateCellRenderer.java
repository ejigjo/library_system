package until;

import java.awt.Color;
import java.awt.Component;
import java.time.LocalDateTime;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DueDateCellRenderer extends DefaultTableCellRenderer {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // 獲取默認渲染器的組件
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // 獲取當前單元格的值（假設是 LocalDateTime 或字符串形式）
        if (column == 3) { // 假設歸還日期在第4列（索引從0開始）
            try {
                LocalDateTime returnDate = LocalDateTime.parse(value.toString());
                if (returnDate.isBefore(LocalDateTime.now())) {
                    cellComponent.setForeground(Color.RED); // 過期的設置為紅色
                } else {
                    cellComponent.setForeground(Color.BLACK); // 正常的設置為黑色
                }
            } catch (Exception e) {
                // 如果解析失敗，設置為默認顏色
                cellComponent.setForeground(Color.BLACK);
            }
        } else {
            cellComponent.setForeground(Color.BLACK); // 其他列為黑色
        }

        return cellComponent;
    }
}
