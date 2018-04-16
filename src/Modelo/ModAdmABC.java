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
 * @author adria
 */
public class ModAdmABC
{   
    String txtConsulta;
    
    private Conexion conexion = new Conexion();
    public DefaultTableModel consulta(int opc){
        switch(opc){
            case 1://empleados
                txtConsulta = "select IdEmpleado as ID, Usuario, Contraseña, Nombre, Telefono, Direccion, Edad, Fecha_Inicio, \n" +
                                "case when Tipo = 1 then 'Administrador'\n" +
                                "     when Tipo = 2 then 'Empleado' \n" +
                                "     end as 'Tipo'\n" +
                                " from empleado, login where empleado.IdEmpleado = login.empleado_IdEmpleado order by Nombre;";
                break;
            case 2://Funciones
                txtConsulta = "select IdFuncion as ID, sala_IdSala as Sala, pelicula_IdPelicula, pelicula.Nombre as Pelicula, Fecha_inicio, Fecha_fin, Hora_inicio, Hora_fin, \n" +
                                "case when Estatus = 1 then 'Disponible' \n" +
                                "	 when Estatus =  0 then 'No disponible'\n" +
                                "end as Estatus\n" +
                                "from funcion, pelicula where pelicula.IdPelicula = funcion.pelicula_IdPelicula;";
                break;
            case 3://peliculas
                txtConsulta = "select * from pelicula";
                break;
            case 4://productos
                txtConsulta = "select IdProducto as ID,Descripcion, Cantidad, Costo, Precio_venta, proveedor.Nombre_empresa as Proveedor from producto, proveedor where proveedor_idProveedor = idProveedor;";
                break;
            case 5:
                txtConsulta = "select id,Descripcion, Precio  from mantenimiento where id!=0";    
                break;
            case 6: 
                txtConsulta = "select IdSala as ID, Filas, Columnas, Num_Asientos as Num_Asientos, Tipo,  \n" +
                                "case when Estatus = 0 then 'No disponible'\n" +
                                "	 when Estatus = 1 then 'Disponible'\n" +
                                "     end as 'Estatus'\n" +
                                "from sala";
                break;
            case 7:
                txtConsulta = "select * from proveedor";
                break;
            case 8:
                txtConsulta = "select idCompra as ID, Subtotal, IVA, Total, Fecha, "
                        + "case when Estado = 0 then 'En espera'"
                        + "     when Estado = 1 then 'Entregada'"
                        + "     end as 'Estado', empleado.Nombre "
                        + "from compra, empleado where empleado_IdEmpleado = IdEmpleado";
                break;
            case 9: 
            txtConsulta = "select * from combos";
                break;
            case 10: 
            txtConsulta = "select * from venta";
                break;
        }
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
}
