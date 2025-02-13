package controller;

import javax.swing.*;

public class MainPanel extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTabbedPane tabbedPane;

    public MainPanel() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 500);
        setTitle("圖書館管理系統");

        // 創建選項卡面板
        tabbedPane = new JTabbedPane();
        setContentPane(tabbedPane);

        // 添加功能模塊
        tabbedPane.addTab("借書功能", new LibraryUI());
        tabbedPane.addTab("借書紀錄", new BorrowInfoPanelUI());
     
       
    }
}
