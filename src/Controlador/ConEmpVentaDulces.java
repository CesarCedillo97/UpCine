/*
    Controlador para realizar venta de dulces
 */
package controlador;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import modelo.ModEmpVentaDulces;
import vista.EmpVentaDulces;
import Vista.AdmAddProduct;
import Modelo.ModAdmFormCombos;
import java.util.ArrayList;
import vista.GenAlert;
import Vista.EmpOpcVentaDulces;
import Controlador.ConEmpOpcVentaDulces;
import Controlador.ConEmpConfirmVentaDulces;
import Vista.EmpConfirmVentaDulces;
import Vista.GenSucces;
import controlador.ConSucces;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;
/**
 *
 * @author Cesar Cedillo
 */
public class ConEmpVentaDulces extends ControladorPrincipal implements MouseListener{
    ModEmpVentaDulces modelo;
    GenSucces genSucces = new GenSucces();
    EmpVentaDulces vista;
    AdmAddProduct vistaPro = new AdmAddProduct();
    ModAdmFormCombos modPro = new ModAdmFormCombos();
    ArrayList precio = new ArrayList();
    ArrayList cant = new ArrayList();
    ArrayList nombre = new ArrayList();
    DefaultListModel listaModel = new DefaultListModel();
    EmpOpcVentaDulces vistaOPC = new EmpOpcVentaDulces();
    
    private int idEmp;
    private float subTotal;
    private float iva;
    private float descuento;
    private float total;
    private int idCliente;
    private int opc;
    private int puntos;
    private float pago;
    EmpConfirmVentaDulces vistaConfirmD = new EmpConfirmVentaDulces();

    public ConEmpVentaDulces(ModEmpVentaDulces modVentaDulces, EmpVentaDulces empVentaDulces, int idEmp)
    {
        this.modelo = modVentaDulces;
        this.vista = empVentaDulces;
        this.idEmp = idEmp;
    }
    
    @Override
    public void iniciarVista()
    {
        vista.setTitle("Venta de dulces");
        vista.pack();
        vista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        vista.panelBack.addMouseListener(this);
        
        //Para poner os valores en cero
        vista.lblx.setText(String.valueOf(0.0f));
        vista.lblDescuento.setText(String.valueOf(0.0f));
        vista.lblSubTotal.setText(String.valueOf(0.0f));
        vista.lblIVA.setText(String.valueOf(0.0f));
        vista.lblCliente.setText("N/A");
        vista.lblPuntos.setText("0");
        
        
        
        //para los paneles
        vista.panelProceder.addMouseListener(this);
        vista.panelAdd.addMouseListener(this);
        vista.panelCancelarVenta.addMouseListener(this);
        vista.panelDelete.addMouseListener(this);
        vistaPro.panelAceptar.addMouseListener(this);
        vistaPro.panelCancelar.addMouseListener(this);
        vistaOPC.panelCombo.addMouseListener(this);
        vistaOPC.panelProductos.addMouseListener(this);
        //para la lista
        vista.listaProductos.setModel(listaModel);
        
        //para la tabla
        vista.tablaVentas.setModel(modelo.mTabla());
        
        //para los paneles de ConfirmVenta
        vistaConfirmD.panelConfirm.addMouseListener(this);
        vistaConfirmD.panelCanel.addMouseListener(this);
        //Para lo de confirmVenta
        vistaConfirmD.lblTotal.setText("0.0");
        vistaConfirmD.txtPago.setText("0.0");
        vistaConfirmD.lblCambio.setText("0.0");
        vistaConfirmD.txtPago.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();

                    if (!((c >= '0') && (c <= '9') ||    
                       (c == KeyEvent.VK_BACK_SPACE) ||
                       (c == KeyEvent.VK_PERIOD) ||
                       (c == KeyEvent.VK_DELETE))) {
                            vistaConfirmD.txtPago.setEditable(false);
                    }
                    else{
                        vistaConfirmD.txtPago.setEditable(true);                        
                    }

                }
            });        
        vistaConfirmD.txtPago.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                setPago(Float.parseFloat(vistaConfirmD.txtPago.getText()));
                if (getPago()<getTotal()) {
                    vistaConfirmD.lblCambio.setText("0.0");
                }else{
                    vistaConfirmD.lblCambio.setText(String.format("%.2f", (Float.parseFloat(vistaConfirmD.txtPago.getText())-Float.parseFloat(vistaConfirmD.lblTotal.getText()))));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                System.out.println("aasd");
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
            }
        });
        
        //para el aceptar de Success
        genSucces.panelAceptar.addMouseListener(this);
        
        
    }
    // este metodo verifica si está algun elemento de la lista seleccionada
    public boolean quitarV(int pos){
        if (pos != -1) {
            return true;
        }else
            return false;
    }
    
    //este metodo actualiza la lista
    public void ActualizarLista(){
        listaModel.clear();
        for (int i = 0;  i<cant.size() ; i++) {
            String Lista = ""+cant.get(i)+"X  "+nombre.get(i)+"";
            listaModel.addElement(Lista);
            
        }
        
    }
    
    //Este metodo Actualiza los precios
    public void ActualizarPrecios(){
        vista.tablaVentas.setModel(modelo.mTabla());
        setSubTotal(0);
        setTotal(0);
        setIva(modelo.obtenerIVA());
        setDescuento(0);
        if (getPuntos()>0 || getPuntos()<51) {
            setDescuento(10);
        }
         for (int i = 0;  i<cant.size() ; i++) {
             setSubTotal((getSubTotal()+(float)precio.get(i)));
        }
         setIva(getSubTotal()*(getIva()/100));
         setSubTotal(subTotal+getIva());
         if (getPuntos()==0) {
            setDescuento(0);
            setTotal((getSubTotal()+getIva()));
        }else{
             setDescuento(getSubTotal()*(getDescuento()/100));
             setTotal(((getSubTotal()+getIva())-getDescuento()));
         }
         
         
         vista.lblSubTotal.setText(String.valueOf(getSubTotal()));
         vista.lblDescuento.setText(String.valueOf(getDescuento()));
         vista.lblIVA.setText(String.valueOf(getIva()));
         vista.lblx.setText(String.format("%.2f", getTotal()));
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
        if (vista.panelBack == e.getSource()) { //para el caso de que quiera salir de vender dulces
            vista.dispose();
        }else if (vista.panelProceder == e.getSource()) {//cuando registra todos los productos y quiere continuar al pago
            if (getTotal()!=0) {
                ConEmpConfirmVentaDulces conConfirm = new ConEmpConfirmVentaDulces(vistaConfirmD, modelo, getTotal());
                conConfirm.iniciarVista();
            }else{
                GenAlert vistaAlert = new GenAlert();
                ConAlert alert = new ConAlert(vistaAlert,"Alerta", "No Hay nada que vender");
                alert.iniciarVista();
            }
            
        }else if (vistaConfirmD.panelConfirm==e.getSource()) { //cuando los datos del panel confirm esten correctos etc
            
            if (!"".equals(vistaConfirmD.txtPago.getText())) {
                setPago(Float.parseFloat(vistaConfirmD.txtPago.getText()));
            if (getPago() < getTotal()) { //si el dinero pagado es menor al total de la compra
                GenAlert vistaAlert = new GenAlert();
                controlador.ConAlert alert = new controlador.ConAlert(vistaAlert, "Error.", "No pago el dinero suficiente");
                alert.iniciarVista();
            }else{
                if (modelo.insertarVenta(getSubTotal(), getIva(), getTotal(), getIdEmp(), 1, nombre)) { //si se registró la venta
                    ConSucces conSuccess = new ConSucces(genSucces, "¡Éxito!", "¡Se ha concretado la venta!");
                    conSuccess.iniciarVista();
                }
            }
            }else{
                GenAlert vistaAlert = new GenAlert();
                controlador.ConAlert alert = new controlador.ConAlert(vistaAlert, "Por favor,", "Ingrese el pago");
                alert.iniciarVista();
                
            }
        }else if (genSucces.panelAceptar==e.getSource()) {
            vistaConfirmD.dispose();
            cant.clear();
            precio.clear();
            nombre.clear();
            ActualizarLista();
            ActualizarPrecios();
            
        } else if (vistaConfirmD.panelCanel==e.getSource()) {//cuando le de click a regresar
            vistaConfirmD.dispose();
        }else if (vista.panelCancelarVenta == e.getSource()) {//para cancela la venta y limpiar todos los campos
            cant.clear();
            precio.clear();
            nombre.clear();
            ActualizarLista();
            ActualizarPrecios();
            
        }else if (vista.panelDelete == e.getSource()) { //para eliminar el campo seleccionado de la tabla
            int pos = vista.listaProductos.getSelectedIndex();
            if (quitarV(pos)) {
                 cant.remove(pos);
                 nombre.remove(pos);
                 precio.remove(pos);
                 ActualizarLista();
                 ActualizarPrecios();
                 resetColor(vista.panelDelete);
             }else{
                GenAlert vistaAlert = new GenAlert();
                ConAlert alert = new ConAlert(vistaAlert,"Alerta", "No hay registro seleccionado");
                alert.iniciarVista();
             }
            
        }else if (vista.panelAdd == e.getSource()) { //para agregar un producto nuevo a la lista y a la venta
            
            ConEmpOpcVentaDulces conVen = new ConEmpOpcVentaDulces(vistaOPC,modPro,vistaPro);
            
            conVen.iniciarVista();
            //esto es para saber si esta eligiendo producto o combos
        }else if (vistaPro.panelAceptar==e.getSource()) { //para los botones de la ventana add
            
            //aqui se asigan valores
            int cantidad = (int)vistaPro.sCantidad.getValue();
            String nombrePro = String.valueOf(vistaPro.comboProducto.getSelectedItem());
            
            if (cantidad == 0){
               GenAlert vistaAlert = new GenAlert();
                ConAlert alert = new ConAlert(vistaAlert, "Por favor", "rellene todos los campos");
                alert.iniciarVista();
            
            }else{
                if (getOpc() == 1) { //con esto obtiene los productos que hay
                    cant.add((int)vistaPro.sCantidad.getValue());
                    nombre.add(String.valueOf(vistaPro.comboProducto.getSelectedItem()));
                    int p =nombre.size();
                    precio.add((float)modelo.obtenerPrecioCom((String) nombre.get(p-1)));

                    ActualizarLista();
                    ActualizarPrecios();
                    resetColor(vistaPro.panelAceptar);
                    resetColor(vistaPro.panelCancelar);
                    vistaPro.dispose();
                    vistaOPC.dispose();
                }else if (getOpc() == 2) { //con esto obtienes todos los combos que hay
                    
                    cant.add((int)vistaPro.sCantidad.getValue());
                    nombre.add(String.valueOf(vistaPro.comboProducto.getSelectedItem()));
                    int p =nombre.size();
                    precio.add((float)modelo.obtenerPrecioPro((String) nombre.get(p-1)));

                    ActualizarLista();
                    ActualizarPrecios();
                    resetColor(vistaPro.panelAceptar);
                    resetColor(vistaPro.panelCancelar);
                    vistaPro.dispose();
                    vistaOPC.dispose();
                }
            

            }
            
        }else if (vistaPro.panelCancelar ==e.getSource()) {//para los botones de la ventana add
            resetColor(vistaPro.panelAceptar);
            resetColor(vistaPro.panelCancelar);
            vistaPro.dispose();
            vistaOPC.dispose();
        }
        else if (vistaOPC.panelCombo==e.getSource()) {
            setOpc(1);
        }
        else if (vistaOPC.panelProductos==e.getSource()) {
            setOpc(2);
            
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
        if (vista.panelBack == e.getSource()) {
            setColor(vista.panelBack);
        }else if (vista.panelProceder == e.getSource()) {
            setColorAceptar(vista.panelProceder);
        }else if (vista.panelCancelarVenta == e.getSource()) {
            setColorCancelar(vista.panelCancelarVenta);
        }else if (vista.panelDelete == e.getSource()) {
            setColorCancelar(vista.panelDelete);
        }else if (vista.panelAdd == e.getSource()) {
            setColorAceptar(vista.panelAdd);
        }else if (vistaPro.panelCancelar ==e.getSource()) {//para los botones de la ventana add
            setColorCancelar(vistaPro.panelCancelar);
        }
        else if (vistaPro.panelAceptar ==e.getSource()) {//para los botones de la ventana add
            setColorAceptar(vistaPro.panelAceptar);
        }
        else if (vistaConfirmD.panelConfirm ==e.getSource()) {//para los botones de la ventana add
            setColorAceptar(vistaConfirmD.panelConfirm);
        }
        else if (vistaConfirmD.panelCanel ==e.getSource()) {//para los botones de la ventana add
            setColorCancelar(vistaConfirmD.panelCanel);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (vista.panelBack == e.getSource()) {
            resetColorSalir(vista.panelBack);
        }else if (vista.panelProceder == e.getSource()) {
            resetColor(vista.panelProceder);
        }else if (vista.panelCancelarVenta == e.getSource()) {
            resetColor(vista.panelCancelarVenta);
        }else if (vista.panelDelete == e.getSource()) {
            resetColor(vista.panelDelete);
        }else if (vista.panelAdd == e.getSource()) {
            resetColor(vista.panelAdd);
        }else if (vistaPro.panelCancelar ==e.getSource()) {//para los botones de la ventana add
            resetColor(vistaPro.panelCancelar);
        }
        else if (vistaPro.panelAceptar ==e.getSource()) {//para los botones de la ventana add
            resetColor(vistaPro.panelAceptar);
        }
        else if (vistaConfirmD.panelConfirm ==e.getSource()) {//para los botones de la ventana add
            resetColor(vistaConfirmD.panelConfirm);
        }
        else if (vistaConfirmD.panelCanel ==e.getSource()) {//para los botones de la ventana add
            resetColor(vistaConfirmD.panelCanel);
        }
    }

    /**
     * @return the idEmp
     */
    public int getIdEmp() {
        return idEmp;
    }

    /**
     * @param idEmp the idEmp to set
     */
    public void setIdEmp(int idEmp) {
        this.idEmp = idEmp;
    }

    /**
     * @return the subTotal
     */
    public float getSubTotal() {
        return subTotal;
    }

    /**
     * @param subTotal the subTotal to set
     */
    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }

    /**
     * @return the iva
     */
    public float getIva() {
        return iva;
    }

    /**
     * @param iva the iva to set
     */
    public void setIva(float iva) {
        this.iva = iva;
    }

    /**
     * @return the descuento
     */
    public float getDescuento() {
        return descuento;
    }

    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    /**
     * @return the total
     */
    public float getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(float total) {
        this.total = total;
    }

    /**
     * @return the idCliente
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * @param idCliente the idCliente to set
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
    public int getOpc() {
        return opc;
    }

    public void setOpc(int opc) {
        this.opc = opc;
    }

    /**
     * @return the puntos
     */
    public int getPuntos() {
        return puntos;
    }

    /**
     * @param puntos the puntos to set
     */
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    /**
     * @return the pago
     */
    public float getPago() {
        return pago;
    }

    /**
     * @param pago the pago to set
     */
    public void setPago(float pago) {
        this.pago = pago;
    }
}
