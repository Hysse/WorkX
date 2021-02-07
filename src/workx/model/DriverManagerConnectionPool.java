package workx.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DriverManagerConnectionPool {

	private static List<Connection> freeDbConnections;

	static {
		freeDbConnections = new LinkedList<Connection>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("DB driver not found:"+ e.getMessage());
		} 
	}
	
	private static synchronized Connection createDBConnection() throws SQLException {
		Connection newConnection = null;
		String ip = "127.0.0.1";
		String port = "3722";
		String db = "workx";
		String username = "login";
		String password = "adminadmin";

		newConnection = DriverManager.getConnection("jdbc:mysql://"+ ip+":"+ 
					port+"/"+db+"?serverTimezone=UTC", username, password);
		newConnection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
		return newConnection;
	}	
	
	public static synchronized Connection getConnection() throws SQLException {
		Connection connection;

		if (!freeDbConnections.isEmpty()) {
			connection = (Connection) freeDbConnections.get(0);
			freeDbConnections.remove(0);

			try {
				if (connection.isClosed())
					connection = getConnection();
			} catch (SQLException e) {
				connection.close();
				connection = getConnection();
			}
		} else {
			connection = createDBConnection();		
		}

		return connection;
	}
	
	public static synchronized void releaseConnection(Connection connection) 
			throws SQLException {
		if(connection != null) freeDbConnections.add(connection);
	}	
	
	
}