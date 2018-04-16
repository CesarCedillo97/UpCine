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
            ResultSet rs = s.executeQuery("SELECT pelicula.IdPelicula, pelicula.Nombre, pelicula.Idioma, pelicula.Subtitulos, pelicula.Formato, funcion.Hora_inicio, pelicula.Imagen FROM funcion,pelicula WHERE pelicula.IdPelicula = funcion.pelicula_IdPelicula AND '2018-04-15' >= funcion.Fecha_inicio  AND '2018-04-15' <= funcion.Fecha_fin AND Estatus = 1");
            rs.last();
            int size = rs.getRow();
            int x = 0;
            rs.beforeFirst();
            String[][] listaPelis = new String[size][7];
            while(rs.next()){
                listaPelis[x][0] = rs.getString("IdPelicula");
                listaPelis[x][1] = rs.getString("Nombre");
                listaPelis[x][2] = rs.getString("Idioma");
                listaPelis[x][3] = rs.getString("Subtitulos");
                listaPelis[x][4] = rs.getString("Formato");
                listaPelis[x][5] = rs.getString("Hora_inicio");
                listaPelis[x][6] = rs.getString("Imagen");
                x++;
            }
            conexion.cerrarConexion(con);
            return listaPelis;
        }
        catch(SQLException e){
            return null;
        }
    }
}
