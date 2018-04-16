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
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author adria
 */
public class ModAdmFormCompras
{
    Conexion conexion = new Conexion();
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
    
    public boolean insertarCompra(String subtotal,String iva,String total,String fecha,String estado,int idEmp, JList list){
        try{
            Connection con = conexion.abrirConexion();
            PreparedStatement s = con.prepareStatement("insert into compra (Subtotal,IVA,Total,Fecha,Estado,empleado_IdEmpleado) values ('"+subtotal+"', '"+iva+"', '"+total+"', '"+fecha+"', '"+estado+"', "+idEmp+")",Statement.RETURN_GENERATED_KEYS);
            s.executeUpdate();
            
            Statement q = con.createStatement();
            ResultSet rs = s.getGeneratedKeys();
            rs.next();
            int idCompra = rs.getInt(1);
            String[] sr;
            for (int i = 0; i < list.getModel().getSize(); i++)
            {
                sr = String.valueOf(list.getModel().getElementAt(i)).split(" ");
                list.getModel().getElementAt(0);
                q.executeUpdate("insert into detalles_compra (compra_idCompra,producto_IdProducto,Cantidad) values ("+idCompra+", (select IdProducto from producto where Descripcion = '"+sr[1]+"'),"+sr[0].replace('x', ' ')+")");
            }
            conexion.cerrarConexion(con);
            return true;
        }
        catch(SQLException e){
            return false;
        }
    }
    
    public DefaultListModel obtenerProductos(int idCompra){
        try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT producto.Descripcion, detalles_compra.Cantidad from detalles_compra, producto where IdProducto = producto_IdProducto and compra_idCompra = "+idCompra+"");
            DefaultListModel listmodel = new DefaultListModel();
            while(rs.next()){
                listmodel.addElement("x"+rs.getInt("detalles_compra.Cantidad")+" "+rs.getString("producto.Descripcion"));
                System.out.println("x"+rs.getInt("detalles_compra.Cantidad")+" "+rs.getString("producto.Descripcion"));
            }
            return listmodel;
        }
        catch(SQLException e){
            return null;
        }
    }
    
   public boolean modificarCompra(int idCompra,String subtotal,String iva,String total,String estado, JList list){
       try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            s.executeUpdate("update compra set Subtotal = "+subtotal+",IVA = "+iva+",Total = "+total+",Estado = "+estado+" where IdCompra = "+idCompra+"");
            Statement q = con.createStatement();
            q.executeUpdate("delete from detalles_compra where compra_idCompra = "+idCompra+"");
           
            String[] sr;
            for (int i = 0; i < list.getModel().getSize(); i++)
            {
                sr = String.valueOf(list.getModel().getElementAt(i)).split(" ");
                list.getModel().getElementAt(0);
                q.executeUpdate("insert into detalles_compra (compra_idCompra,producto_IdProducto,Cantidad) values ("+idCompra+", (select IdProducto from producto where Descripcion = '"+sr[1]+"'),"+sr[0].replace('x', ' ')+")");
            }
            conexion.cerrarConexion(con);
            return true;
        }
        catch(SQLException e){
            return false;
        }
   }
   
   public boolean eliminarCompra(int idCompra){
       try{
            Connection con = conexion.abrirConexion();
            Statement q = con.createStatement();
            q.executeUpdate("delete from detalles_compra where compra_idCompra = "+idCompra+"");
            Statement s = con.createStatement();
            s.executeUpdate("delete from compra where idCompra = "+idCompra+"");
            conexion.cerrarConexion(con);
            return true;
        }
        catch(SQLException e){
            return false;
        }
   }
}
