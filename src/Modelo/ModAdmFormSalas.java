/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author adria
 */
public class ModAdmFormSalas
{
    private Conexion conexion = new Conexion();
  
    public boolean insertarSalas(int filas, int cols,int numAsientos,String tipo, int estatus, boolean[][] asientos){
        int id = -1;
        try{
            Connection con = conexion.abrirConexion();
            PreparedStatement s = con.prepareStatement("insert into sala(Filas, Columnas, Num_Asientos, Tipo, Estatus) values('"+filas+"', '"+cols+"', '"+numAsientos+"', '"+tipo+"', '"+estatus+"')", Statement.RETURN_GENERATED_KEYS);
            s.executeUpdate();
            ResultSet rs = s.getGeneratedKeys();
            while(rs.next()){
                id = rs.getInt(1);
            }
            if(id != -1){
                for (int i = 0; i < asientos.length; i++)
                {
                    for (int j = 0; j < asientos[0].length; j++)
                    {
                        if(asientos[i][j] == true){
                            s.executeUpdate("insert into asiento(Fila, Columna, sala_IdSala) values("+i+", "+j+", "+id+")");
                        }
                    }
                }
            }
            
            conexion.cerrarConexion(con);
            return true;
        }
        catch(SQLException e){
            System.out.println("no se pudo insertar");
            return false;
        }
    }
    
    public boolean modificarSala(int id,int filas, int cols,int numAsientos,String tipo, int estatus, boolean[][] asientos){
        System.out.println(id);
        try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            s.executeUpdate("update sala set Filas = "+filas+", Columnas = "+cols+", Num_Asientos = "+numAsientos+", Tipo = '"+tipo+"', Estatus = "+estatus+" where IdSala = "+id+" ");
            
            for (int i = 0; i < asientos.length; i++)
            {
                for (int j = 0; j < asientos[0].length; j++)
                {
                    if(asientos[i][j] == true){
                        s.executeUpdate("insert into asiento(Fila, Columna, sala_IdSala) values("+i+", "+j+", "+id+")");
                    }
                }
            }
            conexion.cerrarConexion(con);
            return true;
        }
        catch(SQLException e){
            return false;
        }
    }
    
    public boolean[][] consultarAsientos(int idSala,int filas, int cols){
        int id = -1;
        int fila = 0, columna = 0;
        boolean[][] asientos = new boolean[filas][cols];
        try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select Fila, Columna from asiento where sala_IdSala = "+idSala+" ");
            while(rs.next()){
                fila = rs.getInt("Fila");
                columna = rs.getInt(("Columna"));
                asientos[fila][columna] = true;
                System.out.println("Fila: "+fila+"Col: "+columna);
            }
            conexion.cerrarConexion(con);
            return asientos;
        }
        catch(SQLException e){
            System.out.println("no se pudo realizar la consulta");
            return null;
        }
    }
    
    public boolean eliminarAsientos(int idSala){
        try
        {
            Connection con = conexion .abrirConexion();
            Statement s = con.createStatement();
            s.executeUpdate("delete from asiento where sala_IdSala = "+idSala+"");
            conexion.cerrarConexion(con);
            return true;
        
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean eliminarSala(int idSala){
        try
        {
            Connection con = conexion .abrirConexion();
            Statement s = con.createStatement();
            s.executeUpdate("delete from sala where IdSala = "+idSala+"");
            conexion.cerrarConexion(con);
            return true;
        
        } catch (SQLException e) {
            return false;
        }
    }
}
