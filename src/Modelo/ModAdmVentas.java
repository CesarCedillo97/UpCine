/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Jesus
 */
public class ModAdmVentas {
    private modelo.Conexion conexion = new modelo.Conexion();
    
    public DefaultTableModel cargarTabla(){
        String txtConsulta = "select venta.idventa AS ID,venta.TipoVenta,venta.Fecha,venta.Subtotal,venta.Descuento,venta.IVA,venta.Total, empleado.Nombre AS NOMBRE_EMPLEADO,  \n" +
                            "CASE WHEN venta.cliente_idCliente is not null THEN cliente.Nombre\n" +
                            "	 WHEN venta.cliente_idCliente is null THEN \"\"\n" +
                            "     end\n" +
                            "AS NOMBRE_CLIENTE FROM venta, empleado, cliente where venta.empleado_IdEmpleado = empleado.IdEmpleado and (venta.cliente_idCliente = cliente.idCliente or venta.cliente_idCliente is null)";
        try{
            //Para abrir la conexion a la BD
            Connection con = conexion.abrirConexion();
            //Para generar la consulta
            Statement s = con.createStatement();
            //PAra establecer el modelo al JTable
            DefaultTableModel modelo;
            try
            {
                //Ejecutar la consulta
                ResultSet rs = s.executeQuery(txtConsulta);
                //Para establecer el modelo al JTable
                modelo = new DefaultTableModel();
                //Obteniendo la informacion. de las columnas
                //que estan siendo consultadas

                ResultSetMetaData rsMd = rs.getMetaData();
                //La cantidad de columnas que tiene la consulta
                int cantidadColumnas = rsMd.getColumnCount();
                
                
                for(int i= 1; i<=cantidadColumnas; i++){
                    modelo.addColumn(rsMd.getColumnLabel(i));
                }
                //Creando las filas para la JTable
                while(rs.next()) {
                    Object[] fila =  new Object[cantidadColumnas];
                    for (int i = 0; i<cantidadColumnas; i++){
                        fila[i] = rs.getObject(i+1);
                    }
                    modelo.addRow(fila);
                }
                return modelo;
            }finally{
                conexion.cerrarConexion(con);
            }

           }
           catch(SQLException e){
               return null;
           }
    }
    
   /* public boolean BuscarVenta(int buscar){
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
        */
        public DefaultTableModel BuscarVenta(String fechaInic, String fechaFin, String nomEmpleado, int tipoVenta)
        {   
            try{
                String query = "";
                if(!"".equals(fechaInic) && !"".equals(fechaFin) && !"".equals(nomEmpleado) && (tipoVenta == 1 || tipoVenta == 2 )){
                    query = "select venta.idventa AS ID,venta.TipoVenta,venta.Fecha,venta.Subtotal,venta.Descuento,venta.IVA,venta.Total, empleado.Nombre AS NOMBRE_EMPLEADO,  \n" +
                            "CASE WHEN venta.cliente_idCliente is not null THEN cliente.Nombre\n" +
                            "	 WHEN venta.cliente_idCliente is null THEN \"\"\n" +
                            "     end\n" +
                            "AS NOMBRE_CLIENTE FROM venta, empleado, cliente where venta.empleado_IdEmpleado = empleado.IdEmpleado and (venta.cliente_idCliente = cliente.idCliente or venta.cliente_idCliente is null) and (venta.Fecha >= '"+fechaInic+"' and venta.Fecha <= '"+fechaFin+"' and empleado.Nombre = '"+nomEmpleado+"' and venta.TipoVenta = "+tipoVenta+")";
                }
                else if(!"".equals(fechaInic) && !"".equals(fechaFin) && !"".equals(nomEmpleado) && (tipoVenta == 3)){
                    query = "select venta.idventa AS ID,venta.TipoVenta,venta.Fecha,venta.Subtotal,venta.Descuento,venta.IVA,venta.Total, empleado.Nombre AS NOMBRE_EMPLEADO,  \n" +
                            "CASE WHEN venta.cliente_idCliente is not null THEN cliente.Nombre\n" +
                            "	 WHEN venta.cliente_idCliente is null THEN \"\"\n" +
                            "     end\n" +
                            "AS NOMBRE_CLIENTE FROM venta, empleado, cliente where venta.empleado_IdEmpleado = empleado.IdEmpleado and (venta.cliente_idCliente = cliente.idCliente or venta.cliente_idCliente is null) and (venta.Fecha >= '"+fechaInic+"' and venta.Fecha <= '"+fechaFin+"' and empleado.Nombre = '"+nomEmpleado+"')";
                }
                else if(!"".equals(fechaInic)&& !"".equals(fechaFin) && (tipoVenta == 1 || tipoVenta == 2 )){
                    query = "select venta.idventa AS ID,venta.TipoVenta,venta.Fecha,venta.Subtotal,venta.Descuento,venta.IVA,venta.Total, empleado.Nombre AS NOMBRE_EMPLEADO,  \n" +
                            "CASE WHEN venta.cliente_idCliente is not null THEN cliente.Nombre\n" +
                            "	 WHEN venta.cliente_idCliente is null THEN \"\"\n" +
                            "     end\n" +
                            "AS NOMBRE_CLIENTE FROM venta, empleado, cliente where venta.empleado_IdEmpleado = empleado.IdEmpleado and (venta.cliente_idCliente = cliente.idCliente or venta.cliente_idCliente is null) and (venta.Fecha >= '"+fechaInic+"' and venta.Fecha <= '"+fechaFin+"' and venta.TipoVenta = "+tipoVenta+")";
                }
                else if(!"".equals(fechaInic)&& !"".equals(fechaFin) && tipoVenta == 3){
                    query = "select venta.idventa AS ID,venta.TipoVenta,venta.Fecha,venta.Subtotal,venta.Descuento,venta.IVA,venta.Total, empleado.Nombre AS NOMBRE_EMPLEADO,  \n" +
                            "CASE WHEN venta.cliente_idCliente is not null THEN cliente.Nombre\n" +
                            "	 WHEN venta.cliente_idCliente is null THEN \"\"\n" +
                            "     end\n" +
                            "AS NOMBRE_CLIENTE FROM venta, empleado, cliente where venta.empleado_IdEmpleado = empleado.IdEmpleado and (venta.cliente_idCliente = cliente.idCliente or venta.cliente_idCliente is null) and (venta.Fecha >= '"+fechaInic+"' and venta.Fecha <= '"+fechaFin+"')";
                }
                else if(!"".equals(nomEmpleado) && (tipoVenta == 1 || tipoVenta == 2 )){
                    query = "select venta.idventa AS ID,venta.TipoVenta,venta.Fecha,venta.Subtotal,venta.Descuento,venta.IVA,venta.Total, empleado.Nombre AS NOMBRE_EMPLEADO,  \n" +
                            "CASE WHEN venta.cliente_idCliente is not null THEN cliente.Nombre\n" +
                            "	 WHEN venta.cliente_idCliente is null THEN null\n" +
                            "     end\n" +
                            "AS NOMBRE_CLIENTE FROM venta, empleado, cliente where venta.empleado_IdEmpleado = empleado.IdEmpleado and (venta.cliente_idCliente = cliente.idCliente or venta.cliente_idCliente is null) and (venta.TipoVenta = "+tipoVenta+" && empleado.Nombre = '"+nomEmpleado+"')";
                    System.out.println(query);
                }   
                else if(!"".equals(nomEmpleado) && tipoVenta == 3){
                    query = "select venta.idventa AS ID,venta.TipoVenta,venta.Fecha,venta.Subtotal,venta.Descuento,venta.IVA,venta.Total, empleado.Nombre AS NOMBRE_EMPLEADO,  \n" +
                            "CASE WHEN venta.cliente_idCliente is not null THEN cliente.Nombre\n" +
                            "	 WHEN venta.cliente_idCliente is null THEN \"\"\n" +
                            "     end\n" +
                            "AS NOMBRE_CLIENTE FROM venta, empleado, cliente where venta.empleado_IdEmpleado = empleado.IdEmpleado and (venta.cliente_idCliente = cliente.idCliente or venta.cliente_idCliente is null) and (empleado.Nombre = '"+nomEmpleado+"')";
                }
                else if((tipoVenta == 1 || tipoVenta == 2)){
                    query = "select venta.idventa AS ID,venta.TipoVenta,venta.Fecha,venta.Subtotal,venta.Descuento,venta.IVA,venta.Total, empleado.Nombre AS NOMBRE_EMPLEADO,  \n" +
                            "CASE WHEN venta.cliente_idCliente is not null THEN cliente.Nombre\n" +
                            "	 WHEN venta.cliente_idCliente is null THEN null \n" +
                            "     end\n" +
                            "AS NOMBRE_CLIENTE FROM venta, empleado, cliente where venta.empleado_IdEmpleado = empleado.IdEmpleado and (venta.cliente_idCliente = cliente.idCliente or venta.cliente_idCliente is null) and (venta.TipoVenta = "+tipoVenta+")"; 
                }
                else if(tipoVenta == 3){
                    query = "select venta.idventa AS ID,venta.TipoVenta,venta.Fecha,venta.Subtotal,venta.Descuento,venta.IVA,venta.Total, empleado.Nombre AS NOMBRE_EMPLEADO,  \n" +
                            "CASE WHEN venta.cliente_idCliente is not null THEN cliente.Nombre\n" +
                            "	 WHEN venta.cliente_idCliente is null THEN null\n"+
                            "     end\n" +
                            "AS NOMBRE_CLIENTE FROM venta, empleado, cliente where venta.empleado_IdEmpleado = empleado.IdEmpleado and (venta.cliente_idCliente = cliente.idCliente or venta.cliente_idCliente is null)";
                }
             //Para abrir la conexion a la BD
             Connection con = conexion.abrirConexion();
             //Para generar la consulta
             Statement s = con.createStatement();
             //PAra establecer el modelo al JTable
             DefaultTableModel modelo;
             try
             {
                 //Ejecutar la consulta
                 ResultSet rs = s.executeQuery(query);
                 //Para establecer el modelo al JTable
                 modelo = new DefaultTableModel();
                 //Obteniendo la informacion. de las columnas
                 //que estan siendo consultadas

                 ResultSetMetaData rsMd = rs.getMetaData();
                 //La cantidad de columnas que tiene la consulta
                 int cantidadColumnas = rsMd.getColumnCount();

                 for(int i= 1; i<=cantidadColumnas; i++){
                     modelo.addColumn(rsMd.getColumnLabel(i));
                 }
                 //Creando las filas para la JTable
                 while(rs.next()) {
                     Object[] fila =  new Object[cantidadColumnas];
                     for (int i = 0; i<cantidadColumnas; i++){
                         fila[i] = rs.getObject(i+1);
                     }
                     modelo.addRow(fila);
                 }
                 return modelo;
             }finally{

                 //Cerrar objeto resultSet
                 conexion.cerrarConexion(con);
             }

            }
            catch(SQLException e){
                return null;
            }
    }
        
    
}
