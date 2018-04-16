/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import Vista.GenSucces;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import vista.GenConfirm;

/**
 *
 * @author Cesar Cedillo
 */
public class ConConfirm extends ControladorPrincipal implements MouseListener,KeyListener {
    GenConfirm vistaSucces;
    String header, mensaje;
    
    public ConConfirm(GenConfirm VistaSucces, String header, String mensaje){
        this.vistaSucces = VistaSucces;
        this.header = header;
        this.mensaje = mensaje;
    }

    ConConfirm(GenSucces genSuccess, String enhorabuena, String se_ha_insertado_el_registro_de_manera_exi) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void iniciarVista() {
        vistaSucces.setTitle("Aviso");
        vistaSucces.pack();
        vistaSucces.setLocationRelativeTo(null);
        vistaSucces.setVisible(true);
        vistaSucces.lblMensaje.setText(mensaje);
        vistaSucces.addKeyListener(this);
        vistaSucces.headerSucces.setText(header);
        vistaSucces.panelCancelar.addMouseListener((MouseListener) this);
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (vistaSucces.panelCancelar == e.getSource()) {
            vistaSucces.dispose();
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
        if (vistaSucces.panelCancelar == e.getSource()) {
            setColorCancelar(vistaSucces.panelCancelar);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (vistaSucces.panelCancelar == e.getSource()) {
            resetColor(vistaSucces.panelCancelar);
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
    
    
}
