/*
    Modelo de Combos
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Cesar Cedillo
 */
public class ModAdmFormCombos {
    
    private modelo.Conexion conexion = new modelo.Conexion();
   
    public boolean insertarCombo(String nombre, ArrayList a,ArrayList b, float precio){
        try
        {
            Connection con = conexion.abrirConexion();
            PreparedStatement s = con.prepareStatement("insert into combos(precio,nombre) values("+precio+",'"+nombre+"');",Statement.RETURN_GENERATED_KEYS);
            s.executeUpdate();
            
            Statement q = con.createStatement();
            ResultSet rs = s.getGeneratedKeys();
            rs.next();
            int id_IdCombos = rs.getInt(1);
            for (int i = 0; i < a.size(); i++)
            {
                q.executeUpdate("insert into detalles_combos (id_IdProductos,id_IdCombo,Cantidad) values ("+a.get(i)+","+id_IdCombos+","+b.get(i)+" )");
            }
            conexion.cerrarConexion(con);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public int obtenerIProducto(String nombre){
        int id = -1;
            try{
            Connection con = conexion.abrirConexion();
            if(con!=null){
                Statement s = con.createStatement();
                ResultSet rs = s.executeQuery("SELECT IdProducto FROM producto where  Descripcion ='"+nombre+"'");
                while(rs.next()){
                    id = rs.getInt("IdProducto");  
                }
            }
            conexion.cerrarConexion(con);
            return id;
            }catch(SQLException e){
            return -1;
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
