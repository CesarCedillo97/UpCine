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

/**
 *
 * @author Cesar Cedillo
 */
public class ConEmpConfirmVentaDulces extends ControladorPrincipal implements MouseListener{
    EmpConfirmVentaDulces vista;
    ModEmpVentaDulces modelo;

    public ConEmpConfirmVentaDulces(EmpConfirmVentaDulces vista, ModEmpVentaDulces modelo, float total, ArrayList productos, int idEmp) {
        this.vista = vista;
        this.modelo = modelo;
    }
    
    

    @Override
    public void iniciarVista() {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
