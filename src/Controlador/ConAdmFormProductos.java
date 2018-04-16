/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import Vista.GenSucces;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import vista.AdmFormProductos;
import modelo.ModAdmFormProductos;
import vista.GenAlert;
/**
 *
 * @author adria
 */
public class ConAdmFormProductos extends ControladorPrincipal implements MouseListener
{
    AdmFormProductos formProduct;
    ModAdmFormProductos modFormProduct;
    int opcion;
    GenAlert genAlert = new GenAlert();
    GenSucces genSuccess = new GenSucces();
    private String id, cantidad, costo, precio, prov, descripcion;
    String[][] arrayprovs;
    public ConAdmFormProductos(AdmFormProductos formProduct, ModAdmFormProductos modFormProduct, int opcion)
    {
        this.formProduct = formProduct;
        this.modFormProduct = modFormProduct;
        this.opcion = opcion;
    }
    
    @Override
    public void iniciarVista()
    {
        formProduct.setTitle("UpCine");
        formProduct.title.setText((opcion == 1?"Agregar ": "Modificar ")+"producto");
        formProduct.pack();
        formProduct.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        formProduct.setLocationRelativeTo(null);
        formProduct.setVisible(true);
        formProduct.panelAdd.addMouseListener((MouseListener) this);
        formProduct.panelBack.addMouseListener((MouseListener) this);
        genSuccess.panelAceptar.addMouseListener((MouseListener) this);
        arrayprovs = modFormProduct.obtenerProveedores();
        if(opcion != 2){
            DefaultComboBoxModel model = new DefaultComboBoxModel();
            for (String[] arrayprov : arrayprovs){
                model.addElement(arrayprov[1]);
            }
            formProduct.txtProv.setModel(model);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if(formProduct.panelAdd == e.getSource()){
            id = formProduct.txtId.getText();
            cantidad = String.valueOf(formProduct.txtCantidad.getValue());
            costo = formProduct.txtCosto.getText();
            precio = formProduct.txtPrecioVenta.getText();
            prov = arrayprovs[formProduct.txtProv.getSelectedIndex()][0];
            descripcion = formProduct.txtDescripcion.getText();
            if(opcion == 1 && !"".equals(cantidad) && !"".equals(costo) && !"".equals(precio) && !"".equals(descripcion)){
                boolean boolCosto = validacionNum(costo,1);
                boolean boolPrecio = validacionNum(precio,1);
                if(boolCosto && boolPrecio){
                        if(modFormProduct.insertarProductos(cantidad, costo, precio, prov, descripcion)){
                        ConSucces success = new ConSucces(genSuccess, "¡Éxito!", "El registro se ha insertado exitosamente");
                        success.iniciarVista();
                    }
                    else{
                        ConAlert conAlert = new ConAlert(genAlert,"No se ha podido agregar el registro");
                        conAlert.iniciarVista();
                    }
                }
                else{
                    String msg2 = "";
                    if(!boolCosto && !boolPrecio) msg2= "Costo y precio";
                    else if(!boolCosto)msg2 = "Costo";
                    else if(!boolPrecio) msg2= "Precio";
                    ConAlert alert = new ConAlert(genAlert, "Se han ingresado caracteres no numericos en: ", msg2);
                    alert.iniciarVista();
                }

            }
            else if(opcion == 2 && !"".equals(cantidad) && !"".equals(costo) && !"".equals(precio) && !"".equals(descripcion)){
                if(modFormProduct.modificarProductos(id,cantidad, costo, precio, prov, descripcion)){
                    ConSucces success = new ConSucces(genSuccess, "¡Éxito!", "El registro se ha modificado exitosamente");
                    success.iniciarVista();
                }
                else{
                    ConAlert conAlert = new ConAlert(genAlert,"No se ha podido modificar el registro");
                    conAlert.iniciarVista();
                }
            }
            else{
                ConAlert conAlert = new ConAlert(genAlert, "Rellene todos los campos para proseguir");
                conAlert.iniciarVista();
            }
            
        }
        else if(formProduct.panelBack == e.getSource()){
            formProduct.dispose();
        }
        else if(genSuccess.panelAceptar == e.getSource()){
            formProduct.dispose();
            genSuccess.dispose();
        }
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        if(formProduct.panelAdd == e.getSource()){
            setColorAceptar(formProduct.panelAdd);
        }
        else if(formProduct.panelBack == e.getSource()){
            setColorCancelar(formProduct.panelBack);
        }
        else if(genSuccess.panelAceptar == e.getSource()){
            setColorAceptar(genSuccess.panelAceptar);
        }
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        if(formProduct.panelAdd == e.getSource()){
            resetColor(formProduct.panelAdd);
        }
        else if(formProduct.panelBack == e.getSource()){
            resetColor(formProduct.panelBack);
        }
        else if(genSuccess.panelAceptar == e.getSource()){
            resetColor(genSuccess.panelAceptar);
        }
    }
    
}
