/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import modelo.Conexion;
/**
 *
 * @author adria
 */
public class ModAdmFormBoletos
{
    Conexion conexion = new Conexion();
    public boolean modificarBoletos(String descripcion, String precio){
        try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            s.executeUpdate("UPDATE mantenimiento SET Precio = "+precio+" WHERE Descripcion = '"+descripcion+"'");
            conexion.cerrarConexion(con);
            return true;
        }
        catch(SQLException e){
            return false;
        }
    }
}
    