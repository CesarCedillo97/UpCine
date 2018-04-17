/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.AdmFormCombos;
import controlador.ControladorPrincipal;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import Modelo.ModAdmFormCombos;
import javax.swing.JFrame;
import Vista.AdmAddProduct;
import javax.swing.DefaultListModel;
import vista.GenAlert;
import controlador.ConAlert;
import vista.GenConfirm;
import controlador.ConConfirm;
import Vista.GenSucces;
import controlador.ConSucces;
import static java.lang.Float.parseFloat;
import java.util.ArrayList;
import javax.swing.ListModel;



/**
 *
 * @author Cesar Cedillo
 */
public class ConAdmFormCombos extends ControladorPrincipal implements MouseListener, ListSelectionListener{
    AdmFormCombos vista;
    ModAdmFormCombos modelo;
    public float precio;
    public String nombre;
    public String Descripcion;
    ArrayList productos;
    DefaultListModel listaModel = new DefaultListModel();
    AdmAddProduct vistaP = new AdmAddProduct();


    public ConAdmFormCombos(AdmFormCombos vista, ModAdmFormCombos modelo) {
        this.vista = vista;
        this.modelo = modelo;
    }
    
    
    //para iniciar la vista
    @Override
    public void iniciarVista() {
        vista.setTitle("Combos");
        vista.pack();
        vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        vista.panelAdd.addMouseListener(this);
        vista.panelBack.addMouseListener(this);

        vista.listProductos.setModel(listaModel);
        vista.panelAddProductos.addMouseListener(this);
        vista.panelEliminarProductos.addMouseListener(this);
        vistaP.panelAceptar.addMouseListener(this);
        vistaP.panelCancelar.addMouseListener(this);
        productos = new ArrayList<>();
        
        
        
    }
    
    
    
    public boolean quitarV(int pos){
        if (pos != -1) {
            return true;
        }else
            return false;
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
         if (vista.panelAddProductos==e.getSource()) {
             //Para cuando agregue productos
            ConAdmAddProduct controlP = new ConAdmAddProduct(vistaP,modelo,2);
            controlP.iniciarVista();
        }else if (vista.panelAdd==e.getSource()) {
             setDescripcion(vista.listProductos.getSelectedValue());
             if (!"".equals(vista.txtNombre.getText()) &&
                 !"".equals(vista.txtPrecio.getText()) &&
                 !"".equals(vista.listProductos.getSelectedValue())) {
                 ListModel a = vista.listProductos.getModel(); 
                 
                    if (modelo.insertarCombo(vista.txtNombre.getText(), vista.listProductos.getSelectedValue(), Float.parseFloat(vista.txtPrecio.getText()))) {
                    GenSucces vistaSucces = new GenSucces();
                    ConSucces success = new ConSucces(vistaSucces, "¡Hecho!", "Se agregó correctamente");
                    success.iniciarVista();
                 }else{
                        GenAlert vistaAlert = new GenAlert();
                ConAlert alert = new ConAlert(vistaAlert, "La operación no se concretó");
                alert.iniciarVista();
                    }
                    

             }else{
                 GenAlert vistaAlert = new GenAlert();
                ConAlert alert = new ConAlert(vistaAlert,"Por favor,", "Rellene todos los campos");
                alert.iniciarVista();
             }
             System.out.println(""+this.getDescripcion());
        }else if (vista.panelBack==e.getSource()) {
             vista.dispose();
        }else if (vista.panelEliminarProductos==e.getSource()) {
            int pos = vista.listProductos.getSelectedIndex();
             if (quitarV(pos)) {
                 listaModel.remove(pos);
                 resetColor(vista.panelEliminarProductos);
             }else{
                GenAlert vistaAlert = new GenAlert();
                ConAlert alert = new ConAlert(vistaAlert, "No hay registro seleccionado");
                alert.iniciarVista();
             }
        }else if (vistaP.panelAceptar==e.getSource()) {
            Number cant = (Number)vistaP.sCantidad.getValue();
            int cantidad;
            String producto;
            cantidad = (int)cant.intValue();
            producto = String.valueOf(vistaP.comboProducto.getSelectedItem());
            String nProducto = ("x"+cantidad+" "+producto);
            
            if (cantidad <= 0 && !"".equals(producto)) {
                GenAlert vistaAlert = new GenAlert();
                ConAlert alert = new ConAlert(vistaAlert, "Por favor", "rellene todos los campos");
                alert.iniciarVista();
            }else{
                DefaultListModel listmodel = (DefaultListModel) vista.listProductos.getModel();
                listmodel.addElement(nProducto);
                vista.listProductos.setModel(listmodel);
                vistaP.dispose();
            }
        }
        else if (vistaP.panelCancelar == e.getSource()) {
            vistaP.dispose();
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(vista.panelAdd == e.getSource()){
            setColorAceptar(vista.panelAdd);
        }
        else if(vista.panelBack == e.getSource()){
            setColorCancelar(vista.panelBack);
        }
        else if(vista.panelAddProductos == e.getSource()){
            setColorAceptar(vista.panelAddProductos);
        }
        else if(vista.panelEliminarProductos == e.getSource()){
            int pos = vista.listProductos.getSelectedIndex();
             if (quitarV(pos)) {
                 setColorAceptar(vista.panelEliminarProductos);
             }
        }
        else if (vistaP.panelAceptar==e.getSource()) {
            setColorAceptar(vistaP.panelAceptar);
            
        }else if (vistaP.panelCancelar == e.getSource()) {
            setColorCancelar(vistaP.panelCancelar);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(vista.panelAdd == e.getSource()){
            resetColor(vista.panelAdd);
        }
        else if(vista.panelBack == e.getSource()){
            resetColor(vista.panelBack);
        }
        else if(vista.panelAddProductos == e.getSource()){
            resetColor(vista.panelAddProductos);
        }
        else if(vista.panelEliminarProductos == e.getSource()){
            int pos = vista.listProductos.getSelectedIndex();
             if (quitarV(pos)) {
                 resetColor(vista.panelEliminarProductos);
             }else{
                 
             }
        }
        else if (vistaP.panelAceptar==e.getSource()) {
            resetColor(vistaP.panelAceptar);
            
        }else if (vistaP.panelCancelar == e.getSource()) {
            resetColor(vistaP.panelCancelar);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    /**
     * @return the precio
     */
    public float getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(float precio) {
        this.precio = precio;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the Descripcion
     */
    public String getDescripcion() {
        return Descripcion;
    }

    /**
     * @param Descripcion the Descripcion to set
     */
    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }


}
