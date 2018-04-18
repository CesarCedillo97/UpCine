/*
    Modelo de formulario de proveedores por parte del admin
 */
package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author adria
 */
public class ModAdmFormProveedores
{
    Conexion conexion = new Conexion();
    public boolean insertarProveedores(String empresa, String responsable, String direccion, String telefono){
        try
        {
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            s.executeUpdate("insert into proveedor(Nombre_empresa, Nombre_responsable, Direccion, Telefono) values('"+empresa+"', '"+responsable+"', '"+direccion+"', '"+telefono+"')");
            conexion.cerrarConexion(con);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean modificarProveedores(String id, String empresa, String responsable, String direccion, String telefono){
        try
        {
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            s.executeUpdate("update proveedor set Nombre_empresa = '"+empresa+"', Nombre_responsable = '"+responsable+"', Direccion = '"+direccion+"', Telefono = '"+telefono+"' where idProveedor = "+id+"");
            conexion.cerrarConexion(con);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean eliminarProveedores(String id){
        try
        {
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            s.executeUpdate("delete from proveedor where idProveedor = "+id+"");
            conexion.cerrarConexion(con);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
}
