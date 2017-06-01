/*package model.dao;

import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;


*//**
 *
 * @author contdiego
 *//*
public class ConexaoPostGres {

    private static Connection connection;

    *//**
     * Chama m�todo criaConexao e retorna conexao aberta com o BD
     * @return Conexão aberta com o banco 
     *//*
    public Connection conectar() {
        try {
            return criaConexao(false);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ConexaoPostGres.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    *//**
     * Cria conexão com o banco de dados
     * @param autocommit   
     * @return
     * @throws Exception 
     *//*
    private Connection criaConexao(Boolean autocommit) throws Exception {
    	
    	final String host = "localhost";
        final String db = "spotynet";
        final String url = "jdbc:postgresql://" + host + "/" + db;
        final String user = "postgres";
        final String password = "postgres";
        final String postgresqlDriver = "org.postgresql.Driver";
        
        
        
        
        try
        {
            Class.forName(postgresqlDriver);
        }
        catch (ClassNotFoundException e)
        {
            java.util.logging.Logger.getLogger(ConexaoPostGres.class.getName()).log(Level.SEVERE, null, e);
        }
        try
        {
            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(autocommit);
        }
        catch (SQLException e)
        {
            java.util.logging.Logger.getLogger(ConexaoPostGres.class.getName()).log(Level.SEVERE, null, e);
        }
        return connection;
    }

    *//**
     * Fecha conexão com o BD
     *//*
    protected void fecharConexao() {

        System.out.println("fechaConexao");
        
        try 
        {
            connection.close();
        }
        catch (SQLException e) 
        {
            java.util.logging.Logger.getLogger(ConexaoPostGres.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    *//**
     * Método para consultar dado no BD
     * @param stmt
     * @return 
     *//*
    protected ResultSet executarSelect(PreparedStatement stmt) {

        try {
            return stmt.executeQuery();

        } catch (SQLException e) {
            java.util.logging.Logger.getLogger(ConexaoPostGres.class.getName()).log(Level.SEVERE, null, e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(ConexaoPostGres.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }

    *//**
     * Método para deletar dado no BD
     * @param stmt 
     *//*
    protected void executarDelete(PreparedStatement stmt) {

        try {
            stmt.executeUpdate();

        } catch (SQLException e) {
            java.util.logging.Logger.getLogger(ConexaoPostGres.class.getName()).log(Level.SEVERE, null, e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(ConexaoPostGres.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
*/