/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import controlador.ConAlert;
import vista.GenAlert;

/**
 *
 * @author Cesar Cedillo
 */
public class Conexion {
    
 
    public Connection abrirConexion() throws SQLException{
        Connection con;
        try{
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/upcine","root","");
        }catch(SQLException e){
            GenAlert vista = new GenAlert();
            ConAlert alert = new ConAlert(vista, "No se pudo abrir la conexion");
            System.out.println(e.getMessage());
            alert.iniciarVista();
            con = null;
        }
        return con;
    }
    
    public void cerrarConexion(Connection c) throws SQLException{
        try{
            if(!c.isClosed()){
                c.close();
            }
            }catch(SQLException e){
                System.out.println("Error al cerrar la conexion");
            }
        }   
}

