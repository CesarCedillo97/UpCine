/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import controlador.ControladorPrincipal;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import Modelo.ModAdmVentas;
import Vista.AdmFormVentas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
/**
 *
 * @author Jesus
 */
public class ConAdmVenta extends ControladorPrincipal implements MouseListener, ActionListener, ChangeListener {

    ModAdmVentas modAdmVentas;
    AdmFormVentas admFormVentas;

    public ConAdmVenta(ModAdmVentas modAdmVentas, AdmFormVentas admFormVentas) {
        this.modAdmVentas = modAdmVentas;
        this.admFormVentas = admFormVentas;
    }
    
    @Override
     public void iniciarVista()
    {
        admFormVentas.setTitle("UpCine");
        admFormVentas.pack();
        admFormVentas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        admFormVentas.setLocationRelativeTo(null);
        admFormVentas.setVisible(true);
        admFormVentas.btnBuscar.addActionListener(this);
        admFormVentas.txtFechaInicio.addActionListener(this);
        admFormVentas.txtFechaFin.addActionListener(this);
        admFormVentas.txtFechaFin.setEnabled(false);
        
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        
    }
    
}
