/*
    Modelo para la venta de boletos por parte del empleado
 */
package Modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Cesar Cedillo
 */
public class ModEmpFormVentaBoleto {
    private modelo.Conexion conexion = new modelo.Conexion();
    
    public String[] obtenerPeliculas(){
        try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select Nombre from pelicula");
            rs.last();
            int rows = rs.getRow();
            rs.beforeFirst();
            String[] arrayPelis = new String[rows];
            int x =0 ;
            while(rs.next()){
                arrayPelis[x] = rs.getString("Nombre");
                x++;
            }
            return arrayPelis;
        }
        catch(SQLException e){
            return null;
        }
    }
    
    public String[] obtenerHoraPelicula(String peli){
        try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select Hora_inicio from funcion, pelicula where '"+peli+"' = 'Nombre' ");
            rs.last();
            int rows = rs.getRow();
            rs.beforeFirst();
            String[] arrayPelis = new String[rows];
            int x =0 ;
            while(rs.next()){
                arrayPelis[x] = rs.getString("Hora_inicio");
                x++;
            }
            return arrayPelis;
        }
        catch(SQLException e){
            return null;
        }
    }
    
    
}
