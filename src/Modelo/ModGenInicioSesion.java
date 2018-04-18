/*
    Modelo para el inicio de sesión
 */
package modelo;
import java.sql.*;
/**
 *
 * @author adria
 */
public class ModGenInicioSesion
{
    String User;
    String Pass;
    
    private final Conexion conexion = new Conexion();
    //checa si los datos introducidos coinciden con algun registro
    public int consultarInicio(String user, String pass){
        int id = -1;
        try{
            Connection con = conexion.abrirConexion();
            if(con!=null){
                Statement s = con.createStatement();
                ResultSet rs = s.executeQuery("SELECT empleado_IdEmpleado FROM login where BINARY Usuario='"+user+"' and BINARY Contraseña='"+pass+"'");
                while(rs.next()){
                    id = rs.getInt("empleado_IdEmpleado");  
                }
            }
            conexion.cerrarConexion(con);
            return id;
            
        }catch(SQLException e){
            return -1;
        }
    }
    //obtiene el nombre del empleado encontrado
    public String VerificarNombre(int idEmp){
        String nombre = null;
        try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT Nombre FROM empleado WHERE IdEmpleado='"+idEmp+"'");
            while(rs.next()){
                nombre = rs.getString("Nombre");
            }
            conexion.cerrarConexion(con);
            return (nombre);
            
        }catch(SQLException e){
            return ("x");
        }
    }
    //obtiene el tipo de usuario que inicio sesión
    public int verificarTipoAcceso(int idEmp){
        int tipo=-1;
        try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT Tipo FROM empleado WHERE IdEmpleado='"+idEmp+"'");
            while(rs.next()){
                tipo = rs.getInt("Tipo");
            }
            conexion.cerrarConexion(con);
            return tipo;
            
        }catch(SQLException e){
            return -1;
        }
    }
}