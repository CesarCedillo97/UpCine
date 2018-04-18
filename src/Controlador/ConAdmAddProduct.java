/*
    Controlador de ventana para agregar productos
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

    public int getOpc() {
        return opc;
    }

    public void setOpc(int opc) {
        this.opc = opc;
    }
   public int opc;
   
   private String Producto;
   private int Cantidad;
   

    public ConAdmAddProduct(AdmAddProduct vista, ModAdmFormCombos modelo, int opc) {
        this.vista = vista;
        this.modelo = modelo;
        this.opc = opc;
    }

    @Override
    public void iniciarVista() {
        vista.setTitle("Agregar producto al combo");
        vista.pack();
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);  
        vista.lblOpc.setText(String.valueOf(this.opc));

        
        switch(this.opc){
            //PAra solo produtos
            case 1:
                //PARA EL COMBO BOX
                String[] productos = modelo.obtenerCombos();
                DefaultComboBoxModel model = new DefaultComboBoxModel();
                for (String pro : productos){
                    model.addElement(pro);
                }
                vista.comboProducto.setModel(model);
                break;
                //para combos y productos
            case 2: 
                //PARA EL COMBO BOX
                String[] combos = modelo.obtenerProductos();
                DefaultComboBoxModel modelito = new DefaultComboBoxModel();
                for (String pro : combos){
                    modelito.addElement(pro);
                }
                vista.comboProducto.setModel(modelito);
                break;
            
        }
        
        
        
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
