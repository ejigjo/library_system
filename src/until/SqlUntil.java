package until;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlUntil {
	private static final String URL = "jdbc:mysql://localhost:3306/library_system?useSSL=false&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASSWORD = "781211";
	private static Connection connection = null;

	public static Connection getConnection() {

		try {
			if (connection == null || connection.isClosed()) {
				
				Class.forName("com.mysql.cj.jdbc.Driver");

				// 建立連線
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;

	}

	public static <T> T mapResultSetToObject(ResultSet rs, Class<T> clazz) {
		try {

			if (rs == null || rs.isClosed()) {
				throw new SQLException("ResultSet is null or closed");
			}

			T instance = clazz.getDeclaredConstructor().newInstance();
			Field[] fields = clazz.getDeclaredFields();

			for (Field field : fields) {
				field.setAccessible(true);
				Object value = rs.getObject(field.getName());

				if (value != null) {
					if (field.getType().equals(java.time.LocalDateTime.class) && value instanceof java.sql.Timestamp) {
						value = ((java.sql.Timestamp) value).toLocalDateTime();
					}
					field.set(instance, value);
				}

			}
			return instance;
		}

		catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static <T> T excuteSingleQuery(String sql, Class<T> clazz, Object... params) {
		try (PreparedStatement stmt = SqlUntil.getConnection().prepareStatement(sql)) {
			for (int i = 0; i < params.length; i++) {
				stmt.setObject(i + 1, params[i]);
			}
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				return mapResultSetToObject(rs, clazz);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static <T> List<T> excuteQuery(String sql, Class<T> clazz, Object... params) {
		List<T> resultList = new ArrayList<>();
		try (PreparedStatement stmt = SqlUntil.getConnection().prepareStatement(sql)) {
			for (int i = 0; i < params.length; i++) {
				stmt.setObject(i + 1, params[i]);
			}
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				T obj = mapResultSetToObject(rs, clazz);
				resultList.add(obj);
			}
			return resultList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean excuteUpdate(String sql, Object... params) {
		if (params == null || params.length == 0) {
			throw new IllegalArgumentException("參數不能為空");
		}
		try (PreparedStatement stmt = SqlUntil.getConnection().prepareStatement(sql)) {
			for (int i = 0; i < params.length; i++) {
				stmt.setObject(i + 1, params[i]);
			}
			if (stmt.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static void beginTransaction() {
		try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void commitTransaction() {
		try {
			getConnection().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void rollbackTransaction() {
		try {
			getConnection().rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
				connection = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
