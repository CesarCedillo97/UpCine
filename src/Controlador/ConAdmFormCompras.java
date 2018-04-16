/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import Vista.GenSucces;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import modelo.ModAdmFormCompras;
import vista.AdmFormCompras;
import vista.GenAlert;
import Vista.AdmAddProduct;
import Controlador.ConAdmAddProduct;
import Modelo.ModAdmFormCombos;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.DefaultListModel;
/**
 *
 * @author adria
 */
public class ConAdmFormCompras extends ControladorPrincipal implements MouseListener, FocusListener
{
    //Variables
    ModAdmFormCompras modFormCompras;
    AdmFormCompras formCompras;
    int opcion;
    float IVA;
    private int idEmp;
    private int idCompra;
    private String subtotal, Iva, total, estado;
    GenAlert genAlert = new GenAlert();
    GenSucces genSuccess = new GenSucces();
    AdmAddProduct addProduct = new AdmAddProduct();
    
    //Constructor
    public ConAdmFormCompras(ModAdmFormCompras modFormCompras, AdmFormCompras formCompras, int opcion,int idEmp)
    {
        this.modFormCompras = modFormCompras;
        this.formCompras = formCompras;
        this.opcion = opcion;
        this.idEmp = idEmp;
    }
    
    @Override
    public void iniciarVista()
    {
        formCompras.setTitle("UpCine");
        formCompras.pack();
        formCompras.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        formCompras.setLocationRelativeTo(null);
        formCompras.setVisible(true);
        formCompras.panelAdd.addMouseListener((MouseListener) this);
        formCompras.panelBack.addMouseListener((MouseListener) this);
        formCompras.panelAddProductos.addMouseListener((MouseListener) this);
        formCompras.panelEliminarProductos.addMouseListener((MouseListener) this);
        formCompras.txtSubtotal.addFocusListener(this);
        IVA = modFormCompras.obtenerIVA();
        addProduct.panelAceptar.addMouseListener((MouseListener) this);
        addProduct.panelCancelar.addMouseListener((MouseListener) this);
        genSuccess.panelAceptar.addMouseListener((MouseListener) this);
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if(formCompras.panelAddProductos == e.getSource()){
            ModAdmFormCombos modAddProduct = new ModAdmFormCombos();
            ConAdmAddProduct conAddProduct = new ConAdmAddProduct(addProduct, modAddProduct,1);
            conAddProduct.iniciarVista();
        }
        else if(formCompras.panelEliminarProductos == e.getSource()){
            if(!formCompras.listProductos.isSelectionEmpty()){
                ((DefaultListModel)formCompras.listProductos.getModel()).remove(formCompras.listProductos.getSelectedIndex());
            }
            else{
                ConAlert alert = new ConAlert(genAlert, "No hay ningun producto seleccionado");
                alert.iniciarVista();
            }
        }
        else if (addProduct.panelAceptar==e.getSource()) {
            Number cant = (Number)addProduct.sCantidad.getValue();
            int cantidad;
            String producto;
            cantidad = (int)cant.intValue();
            producto = String.valueOf(addProduct.comboProducto.getSelectedItem());
            String nProducto = ("x"+cantidad+" "+producto);
            if (cantidad <= 0 && !"".equals(producto)) {
                GenAlert vistaAlert = new GenAlert();
                ConAlert alert = new ConAlert(vistaAlert, "Por favor, rellene todos los campos");
                alert.iniciarVista();
            }else{
                DefaultListModel listmodel = (DefaultListModel) formCompras.listProductos.getModel();
                listmodel.addElement(nProducto);
                formCompras.listProductos.setModel(listmodel);
                addProduct.dispose();
            }
        }
        else if(formCompras.panelAdd == e.getSource()){
            changeValues();
            if(!"".equals(formCompras.txtSubtotal.getText()) && formCompras.listProductos.getModel().getSize() != 0){
                subtotal = formCompras.txtSubtotal.getText();
                Iva = formCompras.txtIva.getText();
                total = formCompras.txtTotal.getText();
                String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                estado = String.valueOf(formCompras.txtEstado.getSelectedIndex());
                if(opcion == 1 && modFormCompras.insertarCompra(subtotal, Iva, total, fecha, estado, idEmp,formCompras.listProductos)){
                    ConSucces success = new ConSucces(genSuccess, "¡Éxito!", "El registro se ha insertado correctamente");
                    success.iniciarVista();
                }
                else if(opcion == 2 && modFormCompras.modificarCompra(idCompra, subtotal, Iva, total, estado, formCompras.listProductos)){
                    ConSucces success = new ConSucces(genSuccess, "¡Éxito!", "El registro se ha modificado correctamente");
                    success.iniciarVista();
                }
                else{
                    ConAlert alert = new ConAlert(genAlert, "No se ha "+(opcion==1?"agregado":"modificado")+" correctamente");
                    alert.iniciarVista();
                }
            }
            else{
                ConAlert alert = new ConAlert(genAlert, "Rellene todos los campos para proseguir");
                alert.iniciarVista();
            }
        }
        else if(formCompras.panelBack == e.getSource()){
            formCompras.dispose();
        }
        else if (addProduct.panelCancelar == e.getSource()) {
            addProduct.dispose();
        }
        else if(genSuccess.panelAceptar == e.getSource()){
            formCompras.dispose();
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
        if(formCompras.panelAdd == e.getSource()){
            setColorAceptar(formCompras.panelAdd);
        }
        else if(formCompras.panelBack == e.getSource()){
            setColorCancelar(formCompras.panelBack);
        }
        else if(formCompras.panelAddProductos == e.getSource()){
            setColorAceptar(formCompras.panelAddProductos);
        }
        else if(formCompras.panelEliminarProductos == e.getSource()){
            setColorCancelar(formCompras.panelEliminarProductos);
        }
        else if(genSuccess.panelAceptar == e.getSource()){
            setColorAceptar(genSuccess.panelAceptar);
        }
        else if (addProduct.panelAceptar==e.getSource()) {
            setColorAceptar(addProduct.panelAceptar);
            
        }else if (addProduct.panelCancelar == e.getSource()) {
            setColorCancelar(addProduct.panelCancelar);
        }
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        if(formCompras.panelAdd == e.getSource()){
            resetColor(formCompras.panelAdd);
        }
        else if(formCompras.panelBack == e.getSource()){
            resetColor(formCompras.panelBack);
        }
        else if(genSuccess.panelAceptar == e.getSource()){
            resetColor(genSuccess.panelAceptar);
        }
        else if(formCompras.panelAddProductos == e.getSource()){
            resetColor(formCompras.panelAddProductos);
        }
        else if(formCompras.panelEliminarProductos == e.getSource()){
            resetColor(formCompras.panelEliminarProductos);
        }
        else if (addProduct.panelAceptar==e.getSource()) {
            resetColor(addProduct.panelAceptar);
            
        }else if (addProduct.panelCancelar == e.getSource()) {
            resetColor(addProduct.panelCancelar);
        }
    }

    @Override
    public void focusGained(FocusEvent e)
    {
        
    }

    @Override
    public void focusLost(FocusEvent e)
    {
        if(formCompras.txtSubtotal == e.getSource() && !"".equals(formCompras.txtSubtotal.getText())){
            if(validacionNum(formCompras.txtSubtotal.getText(),1)){
                changeValues();
            }
            else{
                formCompras.txtSubtotal.setText("");
                ConAlert alert = new ConAlert(genAlert, "Solo se aceptan caracteres numericos");
                alert.iniciarVista();
            }
        }
    }
    
    public void changeValues(){
        formCompras.txtIva.setText(String.valueOf(Float.parseFloat(formCompras.txtSubtotal.getText())*(IVA/100)));
        formCompras.txtTotal.setText(String.valueOf(Float.parseFloat(formCompras.txtSubtotal.getText())*(IVA/100) + Float.parseFloat(formCompras.txtSubtotal.getText())));
    }
    public int getIdCompra()
    {
        return idCompra;
    }

    public void setIdCompra(int idCompra)
    {
        this.idCompra = idCompra;
    }
    
    
    
    
    
}
