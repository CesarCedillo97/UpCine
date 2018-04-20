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
import vista.GenAlert;
import controlador.ConSucces;
import Vista.GenSucces;

/**
 *
 * @author Cesar Cedillo
 */
public class ConEmpConfirmVentaDulces extends ControladorPrincipal { 
    GenSucces genSucces = new GenSucces();
    EmpConfirmVentaDulces vista;
    ModEmpVentaDulces modelo;
    float Total;
    ArrayList nombre;
    int idEmp;

    public ConEmpConfirmVentaDulces(EmpConfirmVentaDulces vista, ModEmpVentaDulces modelo,float total) {
        this.vista = vista;
        this.modelo = modelo;
        this.Total=total;

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
        

        
    }

    
}
