package workx.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import workx.model.DriverManagerConnectionPool;

public class CredenzialiDAO implements ProductModel<Credenziali>{
	private static final String TABLE_NAME = "credenziali";
	
	public Credenziali doRetrieveByKey(int code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Credenziali bean = new Credenziali();
		
		String selectSQL = "SELECT * FROM "+ CredenzialiDAO.TABLE_NAME + " WHERE ID_Credenziali = ?";
			try {
				connection = DriverManagerConnectionPool.getConnection();
				preparedStatement = connection.prepareStatement(selectSQL);
				preparedStatement.setInt(1,code);
				
				ResultSet rs = preparedStatement.executeQuery();
				
				while (rs.next()) {
					bean.setId(rs.getString("ID_Credenziali"));
					bean.setUsername(rs.getString("Username"));
					bean.setPassword(rs.getString("Password"));
				}
			} finally {
				try {
					if(preparedStatement != null)
						preparedStatement.close();
				} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
				}
			}
			
			return bean;
	}

	public Collection<Credenziali> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Collection<Credenziali> products = new LinkedList<Credenziali>();
		
		String selectSQL = "SELECT * FROM "+ CredenzialiDAO.TABLE_NAME;
		if(order != null && !order.equals("")) {
			selectSQL += " ORDER BY" + order;
		}
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				Credenziali bean = new Credenziali();
				bean.setId(rs.getString("ID_Credenziali"));
				bean.setUsername(rs.getString("Username"));
				bean.setPassword(rs.getString("Password"));
				
				products.add(bean);
			}
		} finally {
			if(preparedStatement != null)
				preparedStatement.close();
		
			DriverManagerConnectionPool.releaseConnection(connection);
		}
		
		return (Collection<Credenziali>) products;
	}

	public void doSave(Credenziali product) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO " + CredenzialiDAO.TABLE_NAME +
				" (Username, Password) "+
				" VALUES (?, ?) ";
		try
		{
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
		}
		finally
		{
			try
			{
				preparedStatement.setString(1, product.getUsername());
				preparedStatement.setString(2, product.getPassword());
				
				preparedStatement.executeUpdate();
			}
			finally
			{
				if(preparedStatement != null)
					preparedStatement.close();
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
	}

	public void doUpdate(Credenziali product) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "UPDATE " + CredenzialiDAO.TABLE_NAME + " SET " +
		"Username = ?, Password = ?"+
				" WHERE ID_Credenziali = ?";
		try
		{
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);
		}
		finally
		{
			try
			{
				preparedStatement.setString(1, product.getUsername());
				preparedStatement.setString(2, product.getPassword());
				preparedStatement.setString(3, product.getId());
				
				preparedStatement.executeUpdate();
			}
			finally
			{
				if(preparedStatement != null)
					preparedStatement.close();
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
	}

	public boolean doDelete(int code) throws SQLException {
		int retvalue;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String strcode = Integer.toString(code);
		
		String deleteSQL = "DELETE FROM " + CredenzialiDAO.TABLE_NAME + " WHERE ID_Credenziali = ?";
		try
		{
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
		}
		finally
		{
			try
			{
				preparedStatement.setString(1, strcode);
				
				retvalue = preparedStatement.executeUpdate();
			}
			finally
			{
				if(preparedStatement != null)
					preparedStatement.close();
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return retvalue != 0;
	}

}
