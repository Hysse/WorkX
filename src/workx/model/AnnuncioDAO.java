package workx.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import workx.model.DriverManagerConnectionPool;

public class AnnuncioDAO implements ProductModel<Annuncio>{
	private static final String TABLE_NAME = "annuncio";
	
	public Annuncio doRetrieveByKey(int code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Annuncio bean = new Annuncio();
		
		String selectSQL = "SELECT * FROM "+ AnnuncioDAO.TABLE_NAME + " WHERE ID_Annuncio = ?";
			try {
				connection = DriverManagerConnectionPool.getConnection();
				preparedStatement = connection.prepareStatement(selectSQL);
				preparedStatement.setInt(1,code);
				
				ResultSet rs = preparedStatement.executeQuery();
				
				while (rs.next()) {
					bean.setId(rs.getString("ID_Annuncio"));
					bean.setTitolo(rs.getString("Titolo"));
					bean.setDescrizione(rs.getString("Descrizione"));
					bean.setCosto(Float.parseFloat(rs.getString("Costo")));
					bean.setProfessione(rs.getString("ID_Professione"));
					bean.setUtente(rs.getString("ID_Utente"));
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

	public Collection<Annuncio> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Collection<Annuncio> products = new LinkedList<Annuncio>();
		
		String selectSQL = "SELECT * FROM "+ AnnuncioDAO.TABLE_NAME;
		if(order != null && !order.equals("")) {
			selectSQL += " ORDER BY" + order;
		}
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				Annuncio bean = new Annuncio();
				bean.setId(rs.getString("ID_Annuncio"));
				bean.setTitolo(rs.getString("Titolo"));
				bean.setDescrizione(rs.getString("Descrizione"));
				bean.setCosto(Float.parseFloat(rs.getString("Costo")));
				bean.setProfessione(rs.getString("ID_Professione"));
				bean.setUtente(rs.getString("ID_Utente"));
				
				products.add(bean);
			}
		} finally {
			if(preparedStatement != null)
				preparedStatement.close();
		
			DriverManagerConnectionPool.releaseConnection(connection);
		}
		
		return (Collection<Annuncio>) products;
	}

	public void doSave(Annuncio product) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO " + AnnuncioDAO.TABLE_NAME +
				" (Titolo, Costo, Descrizione, ID_Professione, ID_Utente) "+
				" VALUES (?, ?, ?, ?, ?) ";
		try
		{
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
		}
		finally
		{
			try
			{
				preparedStatement.setString(1, product.getTitolo());
				preparedStatement.setString(2, Float.toString(product.getCosto()));
				preparedStatement.setString(3, product.getDescrizione());
				preparedStatement.setString(4, product.getProfessione());
				preparedStatement.setString(5, product.getUtente());
				
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

	public void doUpdate(Annuncio product) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "UPDATE " + AnnuncioDAO.TABLE_NAME + " SET" +
		"Titolo = ?, Costo = ?, Descrizione = ?, ID_Professione = ?, ID_Utente = ?"+
				"WHERE ID_Annuncio = ?";
		try
		{
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);
		}
		finally
		{
			try
			{
				preparedStatement.setString(1, product.getTitolo());
				preparedStatement.setString(2, Float.toString(product.getCosto()));
				preparedStatement.setString(3, product.getDescrizione());
				preparedStatement.setString(4, product.getProfessione());
				preparedStatement.setString(5, product.getUtente());
				preparedStatement.setString(6, product.getId());
				
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
		
		String deleteSQL = "DELETE FROM " + AnnuncioDAO.TABLE_NAME + " WHERE ID_Annuncio = ?";
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
