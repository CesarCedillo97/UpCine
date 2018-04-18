/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import Vista.AdmOpcProductos;
import controlador.ConAdmABC;
import controlador.ControladorPrincipal;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import vista.AdmABC;
import modelo.ModAdmABC;
/**
 *
 * @author Cesar Cedillo
 */
public class ConAdmOpcProductos extends ControladorPrincipal implements MouseListener {
    AdmOpcProductos vista;
    int idEmp;
    AdmABC vistaABC = new AdmABC();
    ModAdmABC modABC = new ModAdmABC();
    

    public ConAdmOpcProductos(AdmOpcProductos vista, int idEmp) {
        this.idEmp = idEmp;
        this.vista = vista;
    }
    

    @Override
    public void iniciarVista() {
        vista.setTitle("Opcion productos");
        vista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        vista.panelBack.addMouseListener(this);
        vista.panelCombos.addMouseListener(this);
        vista.panelProductos.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (vista.panelBack==e.getSource()) {
            vista.dispose();
        }
        else if (vista.panelCombos== e.getSource()) {
            ConAdmABC conAdmABC = new controlador.ConAdmABC(modABC, vistaABC, this.idEmp, 9);
            conAdmABC.iniciarVista();
            vista.dispose();
        }
        else if (vista.panelProductos==e.getSource()) {
            ConAdmABC conAdmABC = new controlador.ConAdmABC(modABC, vistaABC, this.idEmp, 4);
            conAdmABC.iniciarVista();
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
        if (vista.panelBack==e.getSource()) {
            setColor(vista.panelBack);
            
        }else if (vista.panelCombos== e.getSource()) {
            setColor(vista.panelCombos);
        }else if (vista.panelProductos==e.getSource()) {
            setColor(vista.panelProductos);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (vista.panelBack==e.getSource()) {
            resetColorSalir(vista.panelBack);
        }else if (vista.panelCombos== e.getSource()) {
            resetColor(vista.panelCombos);
        }else if (vista.panelProductos==e.getSource()) {
            resetColor(vista.panelProductos);
        }
    }
    
}
