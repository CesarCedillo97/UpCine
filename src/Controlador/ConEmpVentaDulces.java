/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import modelo.ModEmpVentaDulces;
import vista.EmpVentaDulces;
/**
 *
 * @author Cesar Cedillo
 */
public class ConEmpVentaDulces extends ControladorPrincipal implements MouseListener{
    ModEmpVentaDulces modelo;
    EmpVentaDulces vista;
     
    DefaultListModel listaModel = new DefaultListModel();
    
    private int idEmp;
    private float subTotal;
    private float iva;
    private float descuento;
    private float total;
    private int idCliente;

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
        vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        vista.panelBack.addMouseListener(this);
        
        //Para poner os valores en cero
        vista.labelTotal.setText(String.valueOf(0.0f));
        vista.lblDescuento.setText(String.valueOf(0.0f));
        vista.lblSubTotal.setText(String.valueOf(0.0f));
        vista.lblIVA.setText(String.valueOf(0.0f));
        
        //para los paneles
        vista.panelAceptar.addMouseListener(this);
        vista.panelAdd.addMouseListener(this);
        vista.panelCancelar.addMouseListener(this);
        vista.panelDelete.addMouseListener(this);
        
        //para la lista
        vista.listaProductos.setModel(listaModel);
        
        //para la tabla
        
        
        
    }
    
    public boolean quitarV(int pos){
        if (pos != -1) {
            return true;
        }else
            return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (vista.panelBack == e.getSource()) {
            vista.dispose();
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
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (vista.panelBack == e.getSource()) {
            resetColorSalir(vista.panelBack);
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
}
