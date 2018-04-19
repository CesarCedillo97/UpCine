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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Cesar Cedillo
 */
public class ModEmpVentaPeli {
    Conexion conexion = new Conexion();
    public String[][] consultarPeliculas(){
        try{
            Connection con = conexion.abrirConexion();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = new Date();
            String date = dateFormat.format(date1);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT pelicula.IdPelicula, pelicula.Nombre, pelicula.Idioma, pelicula.Subtitulos, pelicula.Formato, funcion.Hora_inicio, pelicula.Imagen,funcion.sala_IdSala, funcion.IdFuncion FROM funcion,pelicula WHERE pelicula.IdPelicula = funcion.pelicula_IdPelicula AND '"+date+"' >= funcion.Fecha_inicio  AND '"+date+"' <= funcion.Fecha_fin AND Estatus = 1");
            rs.last();
            int x = 0;
            int size = 0;
            rs.beforeFirst();
            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            Date date2 = new Date();
            while(rs.next()){
                if(rs.getTime("Hora_inicio").getHours() >= date2.getHours() && rs.getTime("Hora_inicio").getMinutes() >= date2.getMinutes())
                    size++;
            }
            rs.beforeFirst();
            String[][] listaPelis = new String[size][9];
            while(rs.next()){
                if(rs.getTime("Hora_inicio").getHours() >= date2.getHours() && rs.getTime("Hora_inicio").getMinutes() >= date2.getMinutes()){
                    listaPelis[x][0] = rs.getString("IdPelicula");
                    listaPelis[x][1] = rs.getString("Nombre");
                    listaPelis[x][2] = rs.getString("Idioma");
                    listaPelis[x][3] = rs.getString("Subtitulos");
                    listaPelis[x][4] = rs.getString("Formato");
                    listaPelis[x][5] = rs.getString("Hora_inicio");
                    listaPelis[x][6] = rs.getString("Imagen");
                    listaPelis[x][7] = rs.getString("sala_IdSala");
                    listaPelis[x][8] = rs.getString("IdFuncion");
                    x++;
                }
            }
            conexion.cerrarConexion(con);
            return listaPelis;
        }
        catch(SQLException e){
            return null;
        }
    }
    
    public String[][] obtenerTipoBoletos(){
        try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select Descripcion, Precio from mantenimiento where descripcion != 'IVA'");
            rs.last();
            int rows = rs.getRow();
            rs.beforeFirst();
            String[][] arrayTipoBol = new String[rows][2];
            int x =0 ;
            while(rs.next()){
                arrayTipoBol[x][0] = rs.getString("Descripcion");
                arrayTipoBol[x][1] = rs.getString("Precio");
                x++;
            }
            return arrayTipoBol;
        }
        catch(SQLException e){
            return null;
        }
    }
    
    public float obtenerIVA(){
        try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select Precio from mantenimiento where Descripcion = 'IVA'");
            rs.next();
            float fIVA= rs.getFloat("Precio");
            return fIVA;
        }
        catch(SQLException e){
            return 0f;
        }
    }
    
    public int[][] obtenerAsientos(int idSala){
        int id = -1;
        int fila = 0, columna = 0;
        int filas = 0, cols =0;
        try{
            Connection con = conexion.abrirConexion();
            Statement q = con.createStatement();
            //obtiene filas y columnas de la sala
            ResultSet rs2 = q.executeQuery("select Filas, Columnas from sala where IdSala = "+idSala+"");
            rs2.next();
            filas = rs2.getInt("Filas");
            cols = rs2.getInt("Columnas");
            int[][] asientos = new int[filas][cols];
            for (int[] asiento : asientos)
            {
                for (int j = 0; j < cols; j++)
                {
                    asiento[j] = -1;
                }                
            }
            
            Statement w = con.createStatement();
            ResultSet rs3 = w.executeQuery("select Fila, Columna from asiento where sala_IdSala = "+idSala+"");
            while(rs3.next()){
                fila = rs3.getInt("Fila");
                columna = rs3.getInt(("Columna"));
                asientos[fila][columna] = 0;
            }
            //obtiene los asientos
            Statement s = con.createStatement();
            Date date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            ResultSet rs = s.executeQuery("select Fila, Columna from asiento, venta, boleto where asiento.sala_IdSala = "+idSala+" and boleto.venta_idventa = venta.idventa and venta.Fecha = '"+df.format(date)+"' and boleto.asiento_idAsiento = asiento.idAsiento");
            while(rs.next()){
                fila = rs.getInt("Fila");
                columna = rs.getInt(("Columna"));
                asientos[fila][columna] = 1;
            }
            conexion.cerrarConexion(con);
            return asientos;
        }
        catch(SQLException e){
            return null;
        }
    }
    
    public int getNumAsientos(int idSala){
        try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT Num_Asientos FROM sala WHERE IdSala = "+idSala+" AND Estatus = 1");
            rs.next();
            int num = rs.getInt("Num_Asientos");
            conexion.cerrarConexion(con);
            return num;
        }
        catch(SQLException e){
            return -1;
        }
    }
    
    public boolean insertarVenta(float subtotal, float iva, float total, int idEmp, int idCliente, int[][] selectedSeats, int idSala, int idFuncion) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Connection con = conexion.abrirConexion();
            PreparedStatement s = con.prepareStatement("INSERT INTO venta(TipoVenta, Fecha, Subtotal, IVA, Total, empleado_IdEmpleado)VALUES (1,'"+df.format(date)+"', "+subtotal+", "+iva+", "+total+", "+idEmp+")",Statement.RETURN_GENERATED_KEYS);
            s.executeUpdate();
            ResultSet rs = s.getGeneratedKeys();
            rs.next();
            int idVenta = rs.getInt(1);
            Statement q = con.createStatement();
            for (int[] selectedSeat : selectedSeats)
            {
                q.executeUpdate("INSERT INTO boleto(funcion_IdFuncion, asiento_idAsiento, venta_idventa) VALUES ("+idFuncion+", (SELECT idAsiento FROM asiento where Fila = " + selectedSeat[0] + " and Columna = " + selectedSeat[1] + " and sala_IdSala = " + idSala + "), " + idVenta + ")");
            }
            conexion.cerrarConexion(con);
            return true;
        }
        catch(SQLException e){
            return false;
        }
        finally{
            System.out.println("Se ha terminado");
        }
    }
    
    public Float[] obtenerBoletos(){
        try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT Precio FROM mantenimiento WHERE Descripcion != 'IVA'");
            Float[] boletos = new Float[3];
            int x = 0;
            while(rs.next()){
                boletos[x] = rs.getFloat("Precio");
                x++;
            }
            return boletos;
        }
        catch(SQLException e){
            return null;
        }
    }
}
