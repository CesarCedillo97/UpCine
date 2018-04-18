/*
    Modelo del formulario de funciones por el administrador
 */
package modelo;
import java.sql.*;

/**
 *
 * @author Cesar Cedillo
 */
public class ModAdmFormFunciones {
    private Conexion conexion = new Conexion();
    
    public boolean funcionesInsertar(String fechai,String fechaf, String horai, String horaf, int status, int idpe, int idsa){
        try
        {
            //para abrir una conexion a la BD
            Connection con = conexion .abrirConexion();
            //Para ejecutar la consulta
            Statement s = con.createStatement();
            int registro = s.executeUpdate(
            
            "insert into funcion(Fecha_inicio,Fecha_fin,Hora_inicio,Hora_fin,Estatus,pelicula_IdPelicula,sala_IdSala) values"
                    + "('" + fechai +"',"
                    + "'" + fechaf + " ' , '" + horai + "' , '" 
                    +horaf + "', ' " + status+ "', ' " + idpe+ "', '" + idsa+"');");
         
            conexion.cerrarConexion(con);
            return true;
        
        } catch (SQLException e) {
            return false;
            
        }
    }
    
    public boolean deleteFuncion(int id){
        try
        {
            Connection con = conexion .abrirConexion();
            Statement s = con.createStatement();
            s.executeUpdate("delete from funcion where IdFuncion = "+id+"");
            conexion.cerrarConexion(con);
            return true;
        
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean funcionesActualizar(int idFun, String fechai,String fechaf, String horai, String horaf, int status, int idpe, int idsa){
        try
        {
            //para abrir una conexion a la BD
            Connection con = conexion .abrirConexion();
            //Para ejecutar la consulta
            Statement s = con.createStatement();
            
            int registro = s.executeUpdate(
            "update funcion set Fecha_inicio= '" + fechai + 
             "', Fecha_fin ='" + fechaf +
             "', Hora_inicio ='" + horai +
             "', Hora_fin ='" + horaf +
             "', Estatus ='" + status +
             "', pelicula_idPelicula ='" + idpe +
             "', sala_idSala ='" + idsa +
             "' where IdFuncion= " + idFun + ";");
            
            conexion.cerrarConexion(con);
            return true;
        
        } catch (SQLException e) {
           return false;
            // Logger.getLogger(ejemploconex.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Se obtiene las peliculas y devuelve un arreglo bidimensional de string
    public String[][] obtenerPeliculas(){
        try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select Nombre, Duracion, IdPelicula from pelicula");
            rs.last();
            int rows = rs.getRow();
            rs.beforeFirst();
            String[][] arrayPelis = new String[rows][3];
            int x =0 ;
            while(rs.next()){
                arrayPelis[x][0] = rs.getString("Nombre");
                arrayPelis[x][1] = String.valueOf(rs.getInt("Duracion"));
                arrayPelis[x][2] = String.valueOf(rs.getInt("IdPelicula"));
                x++;
            }
            return arrayPelis;
        }
        catch(SQLException e){
            return null;
        }
    }
    
    //se obtienen todas las salas y se retornan en un arreglo de strings
    public String[] obtenerSalas(){
        try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select IdSala from sala where Estatus = 1");
            rs.last();
            int rows = rs.getRow();
            rs.beforeFirst();
            String[] arraySalas = new String[rows];
            int x =0 ;
            while(rs.next()){
                arraySalas[x] = String.valueOf(rs.getInt("IdSala"));
                x++;
            }
            return arraySalas;
        }
        catch(SQLException e){
            return null;
        }
    }
}