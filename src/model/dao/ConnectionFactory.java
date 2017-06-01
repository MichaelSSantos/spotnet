package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {

	private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());

	private static Connection connection;

	public static final String HOST = "localhost";
	public static final String DB = "spotynet";
	public static final String URL = "jdbc:postgresql://" + HOST + "/" + DB;
	public static final String USER = "postgres";
	public static final String PASSWORD = "postgres";
	public static final String POSTGRESQLDRIVER = "org.postgresql.Driver";

	public Connection getConnection() {
		try {
			Class.forName(POSTGRESQLDRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			connection.setAutoCommit(false);
			return connection;

		} catch (ClassNotFoundException | SQLException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage());
			throw new RuntimeException(ex);
		}
	}

}
