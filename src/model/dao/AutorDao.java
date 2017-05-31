/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import SpotNet.ConexaoPostGres;
import SpotNet.SpotNet;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import model.entity.Autor;

/**
 *
 * @author contdiego
 */
public class AutorDAO 
{
    
    protected Connection connection;
    ConexaoPostGres conexao = new ConexaoPostGres();
    
    
    public List<Autor> BuscaAutor(String nome)
    {
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

    }   
    
    
    
    
    
    
     public void AlteraAutor(String nome, Autor objAutor) throws SQLException
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
        }
     
     
     
     
     
     
      public void ExcluiAutor(Autor objAutor) throws SQLException
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
        }
    
    
    
    
    
    public void InsereAutor(String nome, File selFile) throws SQLException
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
        }
    
    
    public void ImportaAutores(BufferedReader br) throws IOException
    {
      
    }
}
