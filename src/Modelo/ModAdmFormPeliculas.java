/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author adria
 */
public class ModAdmFormPeliculas
{
    private Conexion conexion = new Conexion();
    
    public boolean insertarPelicula(String nombre, String director, int duracion, String clasif, String generos, String actores, String idioma, String subti, String formato, String imagen){
        try
        {
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            s.executeUpdate("insert into pelicula(Nombre, Director, Duracion, Clasificacion, Generos, Actores, Idioma, Subtitulos, Formato, Imagen) values('"+nombre+"', '"+director+"', "+duracion+", '"+clasif+"', '"+generos+"', '"+actores+"', '"+idioma+"', '"+subti+"', '"+formato+"', '"+imagen+"');");
            conexion.cerrarConexion(con);
            return true;
        } catch (SQLException e) {
            return false;
        }
        
    }
    
    public boolean modificarPeli(String id,String nombre, String director, int duracion, String clasif, String generos, String actores, String idioma, String subti, String formato, String imagen){
        try
        {
            Connection con = conexion .abrirConexion();
            Statement s = con.createStatement();
            System.out.println("update pelicula set Nombre = '"+nombre+"', Director = '"+director+"', Duracion = "+duracion+", Clasificacion = '"+clasif+"', Generos = '"+generos+"', Actores = '"+actores+"', Idioma = '"+idioma+"', Subtitulos  = '"+subti+"', Formato = '"+formato+"', Imagen = '"+imagen+"' where IdPelicula = "+id+"");
            s.executeUpdate("update pelicula set Nombre = '"+nombre+"', Director = '"+director+"', Duracion = "+duracion+", Clasificacion = '"+clasif+"', Generos = '"+generos+"', Actores = '"+actores+"', Idioma = '"+idioma+"', Subtitulos  = '"+subti+"', Formato = '"+formato+"', Imagen = '"+imagen+"' where IdPelicula = "+id+"");

            conexion.cerrarConexion(con);
            return true;
        
        } catch (SQLException e) {
            return false;
        } 
    }
    
    public boolean deletePelicula(int id){
        try
        {
            Connection con = conexion .abrirConexion();
            Statement s = con.createStatement();
            s.executeUpdate("delete from pelicula where IdPelicula = "+id+"");
            conexion.cerrarConexion(con);
            return true;
        
        } catch (SQLException e) {
            return false;
        } 
    }
}
