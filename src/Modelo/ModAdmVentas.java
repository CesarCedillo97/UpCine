/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Jesus
 */
public class ModAdmVentas {
    private modelo.Conexion conexion = new modelo.Conexion();
    
    public boolean BuscarVenta(int buscar){
        try
        {
            //para abrir una conexion a la BD
            Connection con = conexion .abrirConexion();
            //Para ejecutar la consulta
            Statement s = con.createStatement();
            int registro = s.executeUpdate("SELECT venta.`idventa` AS ID,venta.Tipo,venta.Fecha,venta.Subtotal,venta.Descuento,venta.IVA,venta.Total,empleado.nombre AS NOMBRE_EMPLEADO, cliente.`Nombre` AS NOMBRE_CLIENTE FROM venta INNER JOIN empleado ON venta.`empleado_IdEmpleado` = empleado.`IdEmpleado` INNER JOIN cliente ON venta.`cliente_idCliente` = cliente.`idCliente` where idventa= "+ buscar +" ");
         
            conexion.cerrarConexion(con);
            return true;
        
        } catch (SQLException e) {
            return false;
            
        }
    }
}
