package workx.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import workx.model.DriverManagerConnectionPool;

public class TransazioneDAO implements ProductModel<Transazione>{
	private static final String TABLE_NAME = "transazione";
	
	public Transazione doRetrieveByKey(int code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Transazione bean = new Transazione();
		
		String selectSQL = "SELECT * FROM "+ TransazioneDAO.TABLE_NAME + " WHERE ID_Transazione = ?";
			try {
				connection = DriverManagerConnectionPool.getConnection();
				preparedStatement = connection.prepareStatement(selectSQL);
				preparedStatement.setInt(1,code);
				
				ResultSet rs = preparedStatement.executeQuery();
				
				while (rs.next()) {
					bean.setId(rs.getString("ID_Transazione"));
					bean.setUtente(rs.getString("ID_Utente"));
					bean.setAnnuncio(rs.getString("ID_Annuncio"));
					bean.setData(rs.getString("Data"));
					bean.setStato(rs.getString("Stato"));
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

	public Collection<Transazione> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Collection<Transazione> products = new LinkedList<Transazione>();
		
		String selectSQL = "SELECT * FROM "+ TransazioneDAO.TABLE_NAME;
		if(order != null && !order.equals("")) {
			selectSQL += " ORDER BY" + order;
		}
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				Transazione bean = new Transazione();
				bean.setId(rs.getString("ID_Transazione"));
				bean.setUtente(rs.getString("ID_Utente"));
				bean.setAnnuncio(rs.getString("ID_Annuncio"));
				bean.setData(rs.getString("Data"));
				bean.setStato(rs.getString("Stato"));
				
				products.add(bean);
			}
		} finally {
			if(preparedStatement != null)
				preparedStatement.close();
		
			DriverManagerConnectionPool.releaseConnection(connection);
		}
		
		return (Collection<Transazione>) products;
	}

	public void doSave(Transazione product) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO " + TransazioneDAO.TABLE_NAME +
				" (ID_Utente, ID_Annuncio, Data, Stato) "+
				" VALUES (?, ?, ?, ?) ";
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
				preparedStatement.setString(3, product.getData());
				preparedStatement.setString(4, product.getStato());
				
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

	public void doUpdate(Transazione product) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "UPDATE " + TransazioneDAO.TABLE_NAME + " SET " +
		"ID_Utente = ?, ID_Annuncio = ?, Data = ?, Stato = ? "+
				" WHERE ID_Transazione = ?";
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
				preparedStatement.setString(3, product.getData());
				preparedStatement.setString(4, product.getStato());
				preparedStatement.setString(5, product.getId());
				
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
		
		String deleteSQL = "DELETE FROM " + TransazioneDAO.TABLE_NAME + " WHERE ID_Transazione = ?";
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
