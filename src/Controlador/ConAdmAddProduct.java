/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.AdmAddProduct;
import controlador.ControladorPrincipal;
import Modelo.ModAdmFormCombos;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerNumberModel;
import Vista.AdmFormCombos;



/**
 *
 * @author Cesar Cedillo
 */
public class ConAdmAddProduct extends ControladorPrincipal{

   AdmAddProduct vista;
   ModAdmFormCombos modelo;
   AdmFormCombos vistaAnt;
   Object list;
   
   
   
   private String Producto;
   private int Cantidad;
   

    public ConAdmAddProduct(AdmAddProduct vista, ModAdmFormCombos modelo, Object modeloLista, AdmFormCombos vistaAnt) {
        this.vista = vista;
        this.modelo = modelo;
        this.list = modeloLista;
        this.vistaAnt = vistaAnt;
    }
    
    public ConAdmAddProduct(AdmAddProduct vista, ModAdmFormCombos modelo) {
        this.vista = vista;
        this.modelo = modelo;
    }

   
    
    @Override
    public void iniciarVista() {
        vista.setTitle("Agregar producto al combo");
        vista.pack();
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        //vista.panelAceptar.addMouseListener(this);
        //vista.panelCancelar.addMouseListener(this);
        
        //PARA EL COMBO BOX
        String[] productos = modelo.obtenerProductos();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (String pro : productos){
            model.addElement(pro);
        }
        vista.comboProducto.setModel(model);
        
        //PARA EL SPINNER
        SpinnerNumberModel modeloSpinner = new SpinnerNumberModel( 
        new Integer(0), // Dato visualizado al inicio en el spinner 
        new Integer(0), // Límite inferior 
        new Integer(10), // Límite superior 
        new Integer(1) // incremento-decremento 
        ); 
        vista.sCantidad.setModel(modeloSpinner);
        
        
        
    }


    
}
