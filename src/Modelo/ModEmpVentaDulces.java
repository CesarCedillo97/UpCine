/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Cesar Cedillo
 */
public class ModEmpVentaDulces {
    String consulta;
    private final Conexion conexion = new Conexion();
    
    public DefaultTableModel mTabla(){
        consulta = "select Fecha, Nombre, Total from empleado,venta where empleado = empleado_IdEmpleado and Tipo = '1' ";
        
        try {
            //Para abrir la conexion a la BD
            Connection con = conexion.abrirConexion();
            //Para generar la consulta
            Statement s = con.createStatement();
            //PAra establecer el modelo al JTable
            DefaultTableModel modelo;
            try
            {
                //Ejecutar la consulta
                ResultSet rs = s.executeQuery(consulta);
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
        } catch (SQLException e) {
            
            return null;
        }
    }
    
   public float obtenerPrecioCom(String nombre){
       float precio=0;
       try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select precio from combos where '"+nombre+"' = nombre");
            rs.last();
            precio = rs.getFloat("precio");
            
            conexion.cerrarConexion(con);
            return precio;
        }
        catch(SQLException e){
            return precio;
        }
   }
   
   public float obtenerPrecioPro(String nombre){
       float precio=0;
       try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select Precio_venta from producto where '"+nombre+"' = Descripcion");
            rs.last();
            precio = rs.getFloat("Precio_venta");
            
            conexion.cerrarConexion(con);
            return precio;
        }
        catch(SQLException e){
            return precio;
        }
   }
}
    

