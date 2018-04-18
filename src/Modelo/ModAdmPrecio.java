/*
    Modelo para administrar algunos precios
 */
package Modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Jesus
 */
public class ModAdmPrecio {
    private modelo.Conexion conexion = new modelo.Conexion();
    
    public boolean insertarPrecioBoleto(String descripcion,int precio){
        try
        {
            //para abrir una conexion a la BD
            Connection con = conexion .abrirConexion();
            //Para ejecutar la consulta
            Statement s = con.createStatement();
            int registro = s.executeUpdate("insert into mantenimiento(Descripcion,Precio) values('"+descripcion+"',"+precio+");");
         
            conexion.cerrarConexion(con);
            return true;
        
        } catch (SQLException e) {
            return false;
            
        }
    }
    public boolean modificarPrecioBoleto(String id,String descripcion,int precio){
        try
        {
            //para abrir una conexion a la BD
            Connection con = conexion .abrirConexion();
            //Para ejecutar la consulta
            Statement s = con.createStatement();
            int registro = s.executeUpdate("update mantenimiento set Descripcion='"+descripcion+"' Precio="+precio+" where id='"+id+"';");
            conexion.cerrarConexion(con);
            return true;
        
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean deletePrecioBoleto(int id){
        try
        {
            Connection con = conexion .abrirConexion();
            Statement q = con.createStatement();
            q.executeUpdate("delete from mantenimiento where id = "+id+"");
            conexion.cerrarConexion(con);
            return true;
        
        } catch (SQLException e) {
            return false;
        }
    }
    
}
