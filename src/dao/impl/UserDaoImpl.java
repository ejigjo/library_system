package dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.UserDao;
import model.User;
import until.SqlUntil;

public class UserDaoImpl implements UserDao{
	public boolean insertUser(User user) {
		String sql = "INSERT INTO user(username,password,name,email,level)" + "VALUES(?,?,?,?,?)";

		return SqlUntil.excuteUpdate(sql, user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),
				user.getLevel());
	}

	public User getUserByUsername(String username) {
		String sql = "SELECT * FROM user WHERE username = ?";
		User user = new User();
		try (PreparedStatement stmt = SqlUntil.getConnection().prepareStatement(sql)) {
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				user.setId(rs.getInt("id")); // 資料庫中的 "id" 對應 User 的屬性
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setLevel(rs.getInt("level"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

}
