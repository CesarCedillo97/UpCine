/*
    Controlador de pantalla que funciona como un dialogo de "Success"
 */
package controlador;

import Vista.GenSucces;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 *
 * @author Cesar Cedillo
 */
public class ConSucces extends ControladorPrincipal implements MouseListener{
    GenSucces vista;
    String mensaje,header;

    public ConSucces(GenSucces vista, String header, String mensaje) {
        this.vista = vista;
        this.mensaje = mensaje;
        this.header = header;
    }
    

    @Override
    public void iniciarVista() {
        vista.setTitle("¡Hecho!");
        vista.pack();
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
        vista.lblMensaje.setText(mensaje);
        vista.lblHeader.setText(header);
        vista.panelAceptar.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (vista.panelAceptar== e.getSource()) {
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
        if (vista.panelAceptar== e.getSource()) {
            setColorAceptar(vista.panelAceptar);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (vista.panelAceptar== e.getSource()) {
            resetColor(vista.panelAceptar);
        }
    }

}
