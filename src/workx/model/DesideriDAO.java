package workx.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import workx.model.DriverManagerConnectionPool;

public class DesideriDAO implements ProductModel<Desideri>{
	private static final String TABLE_NAME = "desideri";
	
	public Desideri doRetrieveByKey(int code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Desideri bean = new Desideri();
		
		String selectSQL = "SELECT * FROM "+ DesideriDAO.TABLE_NAME + " WHERE ID_Desideri = ?";
			try {
				connection = DriverManagerConnectionPool.getConnection();
				preparedStatement = connection.prepareStatement(selectSQL);
				preparedStatement.setInt(1,code);
				
				ResultSet rs = preparedStatement.executeQuery();
				
				while (rs.next()) {
					bean.setId(rs.getString("ID_Desideri"));
					bean.setUtente(rs.getString("ID_Utente"));
					bean.setAnnuncio(rs.getString("ID_Annuncio"));
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

	public Collection<Desideri> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Collection<Desideri> products = new LinkedList<Desideri>();
		
		String selectSQL = "SELECT * FROM "+ DesideriDAO.TABLE_NAME;
		if(order != null && !order.equals("")) {
			selectSQL += " ORDER BY" + order;
		}
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				Desideri bean = new Desideri();
				bean.setId(rs.getString("ID_Desideri"));
				bean.setUtente(rs.getString("ID_Utente"));
				bean.setAnnuncio(rs.getString("ID_Annuncio"));
				
				products.add(bean);
			}
		} finally {
			if(preparedStatement != null)
				preparedStatement.close();
		
			DriverManagerConnectionPool.releaseConnection(connection);
		}
		
		return (Collection<Desideri>) products;
	}

	public void doSave(Desideri product) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO " + DesideriDAO.TABLE_NAME +
				" (ID_Utente, ID_Annuncio) "+
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
				preparedStatement.setString(1, product.getUtente());
				preparedStatement.setString(2, product.getAnnuncio());
				
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

	public void doUpdate(Desideri product) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "UPDATE " + DesideriDAO.TABLE_NAME + " SET " +
		"ID_Utente = ?, ID_Annuncio = ?"+
				" WHERE ID_Desideri = ?";
		try
		{
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);
		}
		finally
		{
			try
			{
				preparedStatement.setString(1, product.getUtente());
				preparedStatement.setString(2, product.getAnnuncio());
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
		
		String deleteSQL = "DELETE FROM " + DesideriDAO.TABLE_NAME + " WHERE ID_Desideri = ?";
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
