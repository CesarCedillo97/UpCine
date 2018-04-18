/*
    Modelo por parte del formulario de productos
 */
package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author adria
 */
public class ModAdmFormProductos
{
    public String[][] obtenerProveedores(){
        Conexion conexion = new Conexion();
        try
        {
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select idProveedor, Nombre_empresa from proveedor");
            rs.last();
            int rows = rs.getRow();
            rs.beforeFirst();
            String[][] arrayProv = new String[rows][2];
            int x =0 ;
            while(rs.next()){
                arrayProv[x][0] = String.valueOf(rs.getInt("idProveedor"));
                arrayProv[x][1] = rs.getString("Nombre_empresa");
                x++;
            }
            conexion.cerrarConexion(con);
            return arrayProv;
        } catch (SQLException e) {
            return null;
        }
    }
    
    public boolean insertarProductos(String cantidad,String costo,String precio,String prov,String descripcion){
        Conexion conexion = new Conexion();
        try
        {
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            s.executeUpdate("insert into producto(Cantidad, Costo, Precio_venta, proveedor_idProveedor, Descripcion) values('"+cantidad+"', '"+costo+"', '"+precio+"', '"+prov+"', '"+descripcion+"')");
            conexion.cerrarConexion(con);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean modificarProductos(String id,String cantidad,String costo,String precio,String prov,String descripcion){
        Conexion conexion = new Conexion();
        try
        {
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            s.executeUpdate("update producto set Cantidad = "+cantidad+", Costo = "+costo+", Precio_venta = "+precio+", proveedor_idProveedor = "+prov+", Descripcion = '"+descripcion+"' where IdProducto = "+id+"");
            conexion.cerrarConexion(con);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean eliminarProductos(String id){
        Conexion conexion = new Conexion();
        try
        {
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            s.executeUpdate("delete from producto where IdProducto = "+id+"");
            conexion.cerrarConexion(con);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
