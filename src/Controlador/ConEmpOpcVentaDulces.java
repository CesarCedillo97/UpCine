/*
 */
package Controlador;

import controlador.ControladorPrincipal;
import Vista.EmpOpcVentaDulces;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import Vista.AdmAddProduct;
import Modelo.ModAdmFormCombos;

/**
 *
 * @author Cesar Cedillo
 */
public class ConEmpOpcVentaDulces extends ControladorPrincipal implements MouseListener{
    EmpOpcVentaDulces vista;
    ModAdmFormCombos modPro;
    AdmAddProduct vistaPro;

    public ConEmpOpcVentaDulces(EmpOpcVentaDulces vista, ModAdmFormCombos modPro, AdmAddProduct vistaPro) {
        this.vista = vista;
        this.modPro = modPro;
        this.vistaPro = vistaPro;
    }

    


    @Override
    public void iniciarVista() {
        vista.setTitle("Venta de dulces");
        vista.pack();
        vista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        vista.panelCombo.addMouseListener(this);
        vista.panelProductos.addMouseListener(this);
        vistaPro.panelAceptar.addMouseListener(this);
        vistaPro.panelCancelar.addMouseListener(this);
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (vista.panelCombo == e.getSource()) {
            ConAdmAddProduct conPro= new ConAdmAddProduct(this.vistaPro, this.modPro, 1);
            
            conPro.iniciarVista();
        }else if (vista.panelProductos == e.getSource()) {
            ConAdmAddProduct conPro= new ConAdmAddProduct(this.vistaPro, this.modPro,2);
            conPro.iniciarVista();
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
        if (vista.panelCombo == e.getSource()) {
            setColor(vista.panelCombo);
        }else if (vista.panelProductos == e.getSource()) {
            setColor(vista.panelProductos);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (vista.panelCombo == e.getSource()) {
            resetColor(vista.panelCombo);
        }else if (vista.panelProductos == e.getSource()) {
            resetColor(vista.panelProductos);
        }
    }
    
}
