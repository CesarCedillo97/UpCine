/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import Vista.EmpConfirmVentaDulces;
import modelo.ModEmpVentaDulces;
import controlador.ControladorPrincipal;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author Cesar Cedillo
 */
public class ConEmpConfirmVentaDulces extends ControladorPrincipal implements MouseListener{
    EmpConfirmVentaDulces vista;
    ModEmpVentaDulces modelo;
    float Total,Cambio,Pago;
    ArrayList nombre;
    int idEmp;

    public ConEmpConfirmVentaDulces(EmpConfirmVentaDulces vista, ModEmpVentaDulces modelo, float Total, ArrayList nombre, int idEmp) {
        this.vista = vista;
        this.modelo = modelo;
        this.Total = Total;
        this.nombre = nombre;
        this.idEmp = idEmp;
    }
    
    
    @Override
    public void iniciarVista() {
        vista.setTitle("Confirmar");
        vista.pack();
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        vista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        vista.lblCambio.setText("0");
        vista.txtPago.setText("");
        vista.lblTotal.setText(String.valueOf(this.Total));
        
        vista.panelCanel.addMouseListener(this);
        vista.panelConfirm.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (vista.panelConfirm==e.getSource()) {
            
        }else if (vista.panelCanel==e.getSource()) {
            vista.dispose();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (vista.panelConfirm==e.getSource()) {
            setColor(vista.panelConfirm);
        }else if (vista.panelCanel==e.getSource()) {
            setColorCancelar(vista.panelCanel);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (vista.panelConfirm==e.getSource()) {
            resetColor(vista.panelConfirm);
        }else if (vista.panelCanel==e.getSource()) {
            resetColor(vista.panelCanel);
        }
    }
    
}
