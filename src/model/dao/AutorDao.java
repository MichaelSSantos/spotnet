/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import model.entity.Autor;

/**
 * Classe para persistência de autor no banco de dados.
 * 
 * @author Infnet
 */
public class AutorDao implements Dao<Autor>{
    
	private static final Logger LOGGER = Logger.getLogger(AutorDao.class.getName());

	private Connection connection;
    
    public AutorDao() {
    	this.connection = new ConexaoPostGres().conectar();
    }
    
    @Override
    public List<Autor> buscar(Autor autor) {
    	
    	List<Autor> autores = new ArrayList<>();
    	String sql = "select * from autor where nome like '%?%' and foto is not null";

    	try {
    		PreparedStatement stmt = connection.prepareStatement(sql);
    		stmt.setString(1, autor.getNome());

    		ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                try {
                    byte[] imageData = rs.getBytes("foto");
                    
                    File tmpFile = new File("tmpImage");
                    FileOutputStream fos = new FileOutputStream(tmpFile);
                   
                    fos.write(imageData);
                    fos.close();
                    
                    String ss=tmpFile.getAbsolutePath();
                    BufferedImage bf=ImageIO.read(new File(ss));
                    
                    ImageIcon image = new ImageIcon(bf);
                    
                    Autor ObjAut = new Autor();
                    ObjAut.setFoto(image);
                    ObjAut.setNome(rs.getString("nome"));
                    ObjAut.setId_autor(rs.getInt("id_autor"));

                    autores.add(ObjAut);
                    
                } catch(Exception e) {
                    LOGGER.log(Level.SEVERE, e.getMessage());
                    throw new RuntimeException(e);
                
                } finally {
                	connection.close();
                }
                
            }
            
            return autores;
            
        } catch (SQLException ex) {
        	LOGGER.log(Level.SEVERE, ex.getMessage());
        	throw new RuntimeException(ex);
        }
        
    	
    }
    
    @Override
    public void alterar(Autor autor) {
    	
    	String sql = "update autor set nome=? where id_autor=?";
    	
    	try {
    		try {
    			PreparedStatement stmt = connection.prepareStatement(sql);   
	            stmt.setString(1, autor.getNome());
	            stmt.setInt(2, autor.getId_autor());
	            
	            stmt.execute();
	            
	    	 } catch (Exception ex) {
	         	LOGGER.log(Level.SEVERE, ex.getMessage());
	         	throw new RuntimeException(ex);
	         	
	         } finally {
	        	 connection.commit();
	             connection.close();
	         }
    	
    	} catch (SQLException ex) {
         	LOGGER.log(Level.SEVERE, ex.getMessage());
         	throw new RuntimeException(ex);
    	}
    }
    
    @Override
    public void excluir(Autor autor) {
        
    	String sql = "delete from autor where id_autor=?";
        
        try {
        	try {
        		PreparedStatement stmt = connection.prepareStatement(sql);
        		stmt.setInt(1, autor.getId_autor());
                
        		stmt.execute();
            
        	} catch (Exception ex) {
	         	LOGGER.log(Level.SEVERE, ex.getMessage());
	         	throw new RuntimeException(ex);
            
        	} finally {
                 connection.commit();
                 connection.close();
            }
        	
        } catch (SQLException ex) {
         	LOGGER.log(Level.SEVERE, ex.getMessage());
         	throw new RuntimeException(ex);
    	}
        
    }
    
    @Override
    public void inserir(Autor autor) {
    	
    	String sql = " insert into autor (nome,foto) values (?,?)";
    	
    	try {
    		try {
    			File image = new File(autor.getSelFile().getPath());
    			FileInputStream  fis = new FileInputStream(image);

                PreparedStatement stmt = connection.prepareStatement(sql);               
                stmt.setString(1, autor.getNome());
                stmt.setBinaryStream(2,fis,(int) (image.length()));

                stmt.execute();
    	
            } catch (Exception ex) {
            	LOGGER.log(Level.SEVERE, ex.getMessage());
            	throw new RuntimeException(ex);
    		
    		} finally {
                connection.commit();
                connection.close();
            }
    	
    	} catch (SQLException ex) {
         	LOGGER.log(Level.SEVERE, ex.getMessage());
         	throw new RuntimeException(ex);
    	}
    	
    }
    
    
    
   /* public List<Autor> BuscaAutor(String nome) {
        connection = conexao.conectar();
        Statement stmt = null;
        List<Autor> autores = new ArrayList();

        String sql = "select * from autor where nome like '%"+nome+"%' and foto is not null";
        System.out.println(sql);
        try
        {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            
            while (rs.next())
            {
                Autor ObjAut=new Autor();

                try
                {
                    byte[] imageData = rs.getBytes("foto");
                    
                    File tmpFile = new File("tmpImage");
                    FileOutputStream fos = new FileOutputStream(tmpFile);
                   
                    fos.write(imageData);
                    fos.close();
                    
                
                    String ss=tmpFile.getAbsolutePath();
                    BufferedImage bf=ImageIO.read(new File(ss));
                    
                    ImageIcon image = new ImageIcon(bf);
                    
                    ObjAut.setFoto(image);
                    ObjAut.setNome(rs.getString("nome"));
                    ObjAut.setId_autor(rs.getInt("id_autor"));
                    
                    autores.add(ObjAut);
                    
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                
            }
            connection.close();
            
        }
        catch (SQLException ex)
        {
            Logger.getLogger(SpotNet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return autores;

    }   */
    
    /*public void AlteraAutor(String nome, Autor objAutor) throws SQLException
    {
        connection = conexao.conectar();
        Statement stmt = null;
        
        try
            {
                String query = "update autor set nome='"+nome+"' where id_autor="+objAutor.getId_autor()+"";
                PreparedStatement preparedStmt = connection.prepareStatement(query);   
                System.out.println(preparedStmt);
                preparedStmt.execute();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                connection.commit();
                connection.close();
            }
        }*/
     
   /* public void ExcluiAutor(Autor objAutor) throws SQLException
    {
        connection = conexao.conectar();
        Statement stmt = null;
        
        try
            {
                String query = "delete from autor where id_autor="+objAutor.getId_autor()+"";
                PreparedStatement preparedStmt = connection.prepareStatement(query);   
                System.out.println(preparedStmt);
                preparedStmt.execute();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                connection.commit();
                connection.close();
            }
        }*/
    
    /*public void InsereAutor(String nome, File selFile) throws SQLException
    {
        connection = conexao.conectar();
        Statement stmt = null;
        
        try
            {
                File image = new File(selFile.getPath());

                FileInputStream  fis ;
                fis = new FileInputStream(image);

                String query = " insert into autor (nome,foto)values(?,?)";
                PreparedStatement preparedStmt = connection.prepareStatement(query);               
                preparedStmt.setString(1, nome);
                preparedStmt.setBinaryStream(2,fis,(int) (image.length()));

                System.out.println(preparedStmt);
                preparedStmt.execute();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                connection.commit();
                connection.close();
            }
        }*/
    
    /*public void importarAutores(BufferedReader br) throws IOException
    {
      
    }*/
}
