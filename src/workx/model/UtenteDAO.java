package workx.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import workx.model.DriverManagerConnectionPool;

public class UtenteDAO implements ProductModel<Utente>{
	private static final String TABLE_NAME = "utente";
	
	public Utente doRetrieveByKey(int code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Utente bean = new Utente();
		
		String selectSQL = "SELECT * FROM "+ UtenteDAO.TABLE_NAME + " WHERE ID_Utente = ?";
			try {
				connection = DriverManagerConnectionPool.getConnection();
				preparedStatement = connection.prepareStatement(selectSQL);
				preparedStatement.setInt(1,code);
				
				ResultSet rs = preparedStatement.executeQuery();
				
				while (rs.next()) {
					bean.setId(rs.getString("ID_Utente"));
					bean.setNome(rs.getString("Nome"));
					bean.setCognome(rs.getString("Cognome"));
					bean.setSaldo(Float.parseFloat(rs.getString("Saldo")));
					bean.setRuolo(rs.getString("Ruolo"));
					bean.setProfessione(rs.getString("ID_Professione"));
					bean.setCredenziali(rs.getString("ID_Credenziali"));
					bean.setTelefono(rs.getString("Telefono"));
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

	public Collection<Utente> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Collection<Utente> products = new LinkedList<Utente>();
		
		String selectSQL = "SELECT * FROM "+ UtenteDAO.TABLE_NAME;
		if(order != null && !order.equals("")) {
			selectSQL += " ORDER BY" + order;
		}
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				Utente bean = new Utente();
				bean.setId(rs.getString("ID_Utente"));
				bean.setNome(rs.getString("Nome"));
				bean.setCognome(rs.getString("Cognome"));
				bean.setSaldo(Float.parseFloat(rs.getString("Saldo")));
				bean.setRuolo(rs.getString("Ruolo"));
				bean.setProfessione(rs.getString("ID_Professione"));
				bean.setCredenziali(rs.getString("ID_Credenziali"));
				bean.setTelefono(rs.getString("Telefono"));
				
				products.add(bean);
			}
		} finally {
			if(preparedStatement != null)
				preparedStatement.close();
		
			DriverManagerConnectionPool.releaseConnection(connection);
		}
		
		return (Collection<Utente>) products;
	}

	public void doSave(Utente product) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO " + UtenteDAO.TABLE_NAME +
				" (Nome, Cognome, Saldo, Ruolo, ID_Professione, ID_Credenziali, Telefono) "+
				" VALUES (?, ?, ?, ?, ?, ?, ?) ";
		try
		{
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
		}
		finally
		{
			try
			{
				preparedStatement.setString(1, product.getNome());
				preparedStatement.setString(2, product.getCognome());
				preparedStatement.setFloat(3, product.getSaldo());
				preparedStatement.setString(4, product.getRuolo());
				preparedStatement.setString(5, product.getProfessione());
				preparedStatement.setString(6, product.getCredenziali());
				preparedStatement.setString(7, product.getTelefono());
				
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

	public void doUpdate(Utente product) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "UPDATE " + UtenteDAO.TABLE_NAME + " SET " +
		"Nome = ?, Cognome = ?, Saldo = ?, Ruolo = ?, ID_Professione = ?, ID_Credenziali = ?, Telefono = ? "+
				" WHERE ID_Utente = ?";
		try
		{
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);
		}
		finally
		{
			try
			{
				preparedStatement.setString(1, product.getNome());
				preparedStatement.setString(2, product.getCognome());
				preparedStatement.setFloat(3, product.getSaldo());
				preparedStatement.setString(4, product.getRuolo());
				preparedStatement.setString(5, product.getProfessione());
				preparedStatement.setString(6, product.getCredenziali());
				preparedStatement.setString(7, product.getTelefono());
				preparedStatement.setString(8, product.getId());
				
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
		
		String deleteSQL = "DELETE FROM " + UtenteDAO.TABLE_NAME + " WHERE ID_Utente = ?";
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
