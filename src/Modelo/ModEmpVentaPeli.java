/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
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
            ResultSet rs = s.executeQuery("SELECT pelicula.IdPelicula, pelicula.Nombre, pelicula.Idioma, pelicula.Subtitulos, pelicula.Formato, funcion.Hora_inicio, pelicula.Imagen,funcion.sala_IdSala FROM funcion,pelicula WHERE pelicula.IdPelicula = funcion.pelicula_IdPelicula AND '2018-04-15' >= funcion.Fecha_inicio  AND '2018-04-15' <= funcion.Fecha_fin AND Estatus = 1");
            rs.last();
            int size = rs.getRow();
            int x = 0;
            rs.beforeFirst();
            String[][] listaPelis = new String[size][8];
            while(rs.next()){
                listaPelis[x][0] = rs.getString("IdPelicula");
                listaPelis[x][1] = rs.getString("Nombre");
                listaPelis[x][2] = rs.getString("Idioma");
                listaPelis[x][3] = rs.getString("Subtitulos");
                listaPelis[x][4] = rs.getString("Formato");
                listaPelis[x][5] = rs.getString("Hora_inicio");
                listaPelis[x][6] = rs.getString("Imagen");
                listaPelis[x][7] = rs.getString("sala_IdSala");
                x++;
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
            ResultSet rs2 = q.executeQuery("select Filas, Columnas from sala where IdSala = "+idSala+"");
            rs2.next();
            filas = rs2.getInt("Filas");
            cols = rs2.getInt("Columnas");
            int[][] asientos = new int[filas][cols];
            for (int[] asiento : asientos)
            {
                for (int j = 0; j < 10; j++)
                {
                    asiento[j] = -1;
                }                
            }
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select Fila, Columna, Estatus from asiento where sala_IdSala = "+idSala+" ");
            while(rs.next()){
                fila = rs.getInt("Fila");
                columna = rs.getInt(("Columna"));
                if(rs.getInt("Estatus")==1){
                    asientos[fila][columna] = 1;
                }
                else{
                    asientos[fila][columna] = 0;
                }
                System.out.println("Fila: "+fila+"Col: "+columna);
            }
            conexion.cerrarConexion(con);
            return asientos;
        }
        catch(SQLException e){
            System.out.println("no se pudo realizar la consulta");
            return null;
        }
    }
}
