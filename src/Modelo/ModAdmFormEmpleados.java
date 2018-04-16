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
public class ModAdmFormEmpleados
{
    private Conexion conexion = new Conexion();
    
    //Verifica que el usuario no este en existencia
    public int verificarUsuario(String user, int id){
        int retorno;
        try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT idlogin FROM login,empleado where BINARY Usuario = '"+user+"' and empleado_IdEmpleado != "+id+" ");
            retorno = rs.isBeforeFirst()?1:0;
            conexion.cerrarConexion(con);
            //0 no hay, 1 si hay 
            return retorno;
        }
        catch(SQLException e){
            return -1;
        }
    }
    
    //inserta el empleado, en las tablas empleado y login
    public boolean insertarEmpleado(String nombre, String telefono, String direccion, String edad, String fecha_ini, String tipo,String usuario,String contra){
        try
        {
            Connection con = conexion .abrirConexion();
            //primero inserta el empleado
            PreparedStatement s = con.prepareStatement("insert into empleado(Nombre,Telefono, Direccion, Edad, Fecha_Inicio, Tipo) values('"
                +nombre+"','"+telefono+"', '"+direccion+"', '"+edad+"', '"+fecha_ini+"','"+tipo+"');",Statement.RETURN_GENERATED_KEYS);
            s.executeUpdate();
            Statement q = con.createStatement();
            
            ResultSet rs = s.getGeneratedKeys();
            while(rs.next()){
                //inserta el usuario y contraseña con llave foranea del empleado insertado
                q.executeUpdate("insert into login(Usuario, Contraseña, empleado_IdEmpleado) values ('"+usuario+"', '"+contra+"', "+rs.getInt(1)+");");
            }

            conexion.cerrarConexion(con);
            return true;
        } catch (SQLException e) {
            return false;
        }
        
    }
    //modifica el empleado 
    public boolean modificarEmpleado(String id, String nombre, String telefono, String direccion, String edad, String fecha_ini, String tipo, String usuario, String contra){
        try
        {
            Connection con = conexion .abrirConexion();
            Statement s = con.createStatement();
            //modifica en la tabla empleado
            s.executeUpdate("update empleado set Nombre = '"+nombre+"', Telefono = '"+telefono+"', Direccion = '"
                        +direccion+"', Edad = '"+edad+"', Fecha_Inicio = '"+fecha_ini+"', Tipo = '"+tipo+"' where IdEmpleado = "+Integer.parseInt(id)+"");
            Statement q = con.createStatement();
            //modifica en la tabla login
            q.executeUpdate("update login set Usuario = '"+usuario+"', Contraseña='"+contra+"' where empleado_IdEmpleado = "+Integer.parseInt(id)+"");
            
            
            conexion.cerrarConexion(con);
            return true;
        
        } catch (SQLException e) {
            return false;
        }
    }
    //elimina empleado
    public boolean deleteEmpleado(int id){
        try
        {
            Connection con = conexion .abrirConexion();
            Statement s = con.createStatement();
            Statement q = con.createStatement();
            q.executeUpdate("delete from login where empleado_IdEmpleado = "+id+"");//elimina el registro de la tabla login
            s.executeUpdate("delete from empleado where IdEmpleado = "+id+"");//elimina el registro del empleado
            conexion.cerrarConexion(con);
            return true;
        
        } catch (SQLException e) {
            return false;
        }
    }
}
