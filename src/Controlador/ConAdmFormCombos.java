/*
    Controlador para agregar combos de productos
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
import Vista.GenSucces;
import controlador.ConSucces;
import java.util.ArrayList;
import javax.swing.ListModel;



/**
 *
 * @author Cesar Cedillo
 */
public class ConAdmFormCombos extends ControladorPrincipal implements MouseListener, ListSelectionListener{
    AdmFormCombos vista;
    ModAdmFormCombos modelo;
    private float precio;
    private String Descripcion;
    ArrayList productos;
    ArrayList cant = new ArrayList();
    ArrayList nombre = new ArrayList();
    ArrayList idProducto = new ArrayList();
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
        vista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
    
    public void ActualizarLista(){
        listaModel.clear();
        for (int i = 0;  i<cant.size() ; i++) {
            String Lista = ""+nombre.get(i)+" X"+cant.get(i)+"  ID:"+idProducto.get(i);
            listaModel.addElement(Lista);
            
        }
        
    }
   
    @Override
    public void mouseClicked(MouseEvent e) {
         if (vista.panelAddProductos==e.getSource()) {
             //Para cuando agregue productos
            ConAdmAddProduct controlP = new ConAdmAddProduct(vistaP,modelo,2);
            controlP.iniciarVista();
        }else if (vista.panelAdd==e.getSource()) { //Para agregar un combo
             setDescripcion(vista.txtNombre.getText());
             setPrecio(Float.parseFloat(vista.txtPrecio.getText()));
             System.out.println(""+getPrecio());
             if (getPrecio() > 0 &&
                 !"".equals(getDescripcion()) &&
                     (int)cant.size()!=0) {
                    if (modelo.insertarCombo(getDescripcion(), idProducto, cant, getPrecio())) {
                    GenSucces vistaSucces = new GenSucces();
                    ConSucces success = new ConSucces(vistaSucces, "¡Hecho!", "Se agregó correctamente");
                    success.iniciarVista();
                    }else{
                        GenAlert vistaAlert = new GenAlert();
                    ConAlert alert = new ConAlert(vistaAlert, "Alerta", "La operación no se concretó");
                    alert.iniciarVista();
                    }
                    
             }else{
                 GenAlert vistaAlert = new GenAlert();
                ConAlert alert = new ConAlert(vistaAlert, "Por favor,", "Rellene todos los campos");
                alert.iniciarVista();
             }
        }else if (vista.panelBack==e.getSource()) {
             vista.dispose();
        }else if (vista.panelEliminarProductos==e.getSource()) {
            int pos = vista.listProductos.getSelectedIndex();
             if (quitarV(pos)) {
                 cant.remove(pos);
                 nombre.remove(pos);
                 idProducto.remove(pos);
                 ActualizarLista();
                 resetColor(vista.panelEliminarProductos);
             }else{
                GenAlert vistaAlert = new GenAlert();
                ConAlert alert = new ConAlert(vistaAlert,"Alerta", "No hay registro seleccionado");
                alert.iniciarVista();
             }
        }else if (vistaP.panelAceptar==e.getSource()) { //para cuando quieres agregar el nuevo producto
            int cantidad = (int)vistaP.sCantidad.getValue();
            String nombrePro = String.valueOf(vistaP.comboProducto.getSelectedItem());
            
            
            if (cantidad == 0){
               GenAlert vistaAlert = new GenAlert();
                ConAlert alert = new ConAlert(vistaAlert, "Por favor", "rellene todos los campos");
                alert.iniciarVista();
            
            }else{
                cant.add((int)vistaP.sCantidad.getValue());
                nombre.add(String.valueOf(vistaP.comboProducto.getSelectedItem()));
                int t = nombre.size();
                idProducto.add(((int)modelo.obtenerIProducto((String)nombre.get(t-1))));
                ActualizarLista();
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
