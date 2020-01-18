package com.github.equiz.db;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * This class contains all database utilities
 * 
 * @author Robin
 *
 */
public class DBUtils {

	static String mySqlDriverClass = null;//"com.mysql.jdbc.Driver";
	static String mysqlUrl = null;//"jdbc:mysql://localhost:3306/equiz";
	static String mysqlUser = null;//"root";
	static String mysqlPassword = null;//"root";
	static String path = "src/main/resources/application.properties";

	static {
		try {
			FileReader reader = new FileReader(path);
			Properties properties = new Properties();
			properties.load(reader);
			mySqlDriverClass = properties.getProperty("application.equiz.mysql.class", "com.mysql.jdbc.Driver");
			mysqlUrl = properties.getProperty("application.equiz.mysql.url", "jdbc:mysql://localhost:3306/equiz");
			mysqlUser = properties.getProperty("application.equiz.mysql.user", "root");
			mysqlPassword = properties.getProperty("application.equiz.mysql.password", "root");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection connection = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword);
		return connection;
	}

	public static int update(PreparedStatement preparedStatement) throws SQLException {
		int result = 0;
		System.out.println("SQL:" + preparedStatement);
		result = preparedStatement.executeUpdate();
		return result;
	}

	public static ResultSet fetch(PreparedStatement preparedStatement) throws SQLException {
		System.out.println("SQL:" + preparedStatement);
		ResultSet result = preparedStatement.executeQuery();
		return result;
	}

}
