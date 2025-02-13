package controller;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import until.RegisterUntil;

public class RegisterUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public RegisterUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// USERNAME Label and TextField
		JLabel usernameLabel = new JLabel("USERNAME:");
		usernameLabel.setBounds(50, 30, 100, 25);
		contentPane.add(usernameLabel);

		JTextField usernameField = new JTextField();
		usernameField.setBounds(150, 30, 200, 25);
		contentPane.add(usernameField);
		usernameField.setColumns(10);

		// PASSWORD Label and PasswordField
		JLabel passwordLabel = new JLabel("PASSWORD:");
		passwordLabel.setBounds(50, 70, 100, 25);
		contentPane.add(passwordLabel);

		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(150, 70, 200, 25);
		contentPane.add(passwordField);

		// NAME Label and TextField
		JLabel nameLabel = new JLabel("NAME:");
		nameLabel.setBounds(50, 110, 100, 25);
		contentPane.add(nameLabel);

		JTextField nameField = new JTextField();
		nameField.setBounds(150, 110, 200, 25);
		contentPane.add(nameField);
		nameField.setColumns(10);

		// EMAIL Label and TextField
		JLabel emailLabel = new JLabel("EMAIL:");
		emailLabel.setBounds(50, 150, 100, 25);
		contentPane.add(emailLabel);

		JTextField emailField = new JTextField();
		emailField.setBounds(150, 150, 200, 25);
		contentPane.add(emailField);
		emailField.setColumns(10);

		// Register Button
		JButton registerButton = new JButton("註冊");
		registerButton.setBounds(108, 200, 100, 30);
		contentPane.add(registerButton);

		JButton backButton = new JButton("回上一頁");
		backButton.setBounds(239, 200, 100, 30);
		contentPane.add(backButton);

		registerButton.addActionListener(e -> {
			String username = usernameField.getText().trim();
			String password = passwordField.getText().trim();
			String email = emailField.getText().trim();
			String name = nameField.getText().trim();
			RegisterUntil.registerAction(username, password, email, name);
			new LoginUI().setVisible(true);
			dispose();
		});
		
		backButton.addActionListener(e ->{
			new LoginUI().setVisible(true);
			dispose();
		});

	}

}
