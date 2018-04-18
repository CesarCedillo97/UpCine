/*
    Modelo de Combos
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Cesar Cedillo
 */
public class ModAdmFormCombos {
    
    private modelo.Conexion conexion = new modelo.Conexion();
   
    public boolean insertarCombo(String nombre, String descripcion, float precio){
        try
        {
            Connection con = conexion .abrirConexion();
            PreparedStatement s = con.prepareStatement("insert into combos(nombre,descripcion, precio) values('"
                +nombre+"','"+descripcion+"', 'precio'');",Statement.RETURN_GENERATED_KEYS);
            Statement q = con.createStatement();
            s.executeUpdate();

            conexion.cerrarConexion(con);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public String[] obtenerProductos(){
        try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select Descripcion from producto");
            rs.last();
            int rows = rs.getRow();
            rs.beforeFirst();
            String[] arrayPelis = new String[rows];
            int x =0 ;
            while(rs.next()){
                arrayPelis[x] = rs.getString("Descripcion");
                x++;
            }
            conexion.cerrarConexion(con);
            return arrayPelis;
        }
        catch(SQLException e){
            return null;
        }
    }
    
    public String[] obtenerCombos(){
        try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select nombre from combos");
            rs.last();
            int rows = rs.getRow();
            rs.beforeFirst();
            String[] arrayPelis = new String[rows];
            int x =0 ;
            while(rs.next()){
                arrayPelis[x] = rs.getString("nombre");
                x++;
            }
            conexion.cerrarConexion(con);
            return arrayPelis;
        }
        catch(SQLException e){
            return null;
        }
    }
    
    
}
