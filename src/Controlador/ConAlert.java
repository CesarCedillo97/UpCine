//controlador de vista que funciona como un dialogo de alerta
package controlador;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import vista.GenAlert;

/**
 *
 * @author Cesar Cedillo
 */
public class ConAlert extends ControladorPrincipal implements MouseListener, KeyListener{
    GenAlert vistaAlert;
    String Mensaje;
    String Mensaje2;
    //Constructor con dos mensajes por si el mensaje es muy largo
    public ConAlert(GenAlert VistaAlert, String mensaje, String mensaje2){
        this.vistaAlert = VistaAlert;
        this.Mensaje = mensaje;
        this.Mensaje2 = mensaje2;
    }
    //constructor con un solo mensaje
    public ConAlert(GenAlert VistaAlert, String mensaje){
        this.vistaAlert = VistaAlert;
        this.Mensaje = mensaje;
    }
    
    public ConAlert(){
        this.vistaAlert = null;
        this.Mensaje = "";
        this.Mensaje2 = "";
    }

    @Override
    public void iniciarVista() {
        vistaAlert.setTitle("Aviso");
        vistaAlert.pack();
        vistaAlert.setVisible(true);
        vistaAlert.setLocationRelativeTo(null);
        vistaAlert.panelAceptar.addMouseListener((MouseListener) this);
        vistaAlert.addKeyListener(this);
        vistaAlert.lblMensaje.setText(Mensaje);
        vistaAlert.lblMensaje2.setText(Mensaje2);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (vistaAlert.panelAceptar== e.getSource()) {
           this.Mensaje = "";
           this.Mensaje2 = "";
           vistaAlert.dispose();
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
        if (vistaAlert.panelAceptar== e.getSource()) {
            setColor(vistaAlert.panelAceptar);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (vistaAlert.panelAceptar== e.getSource()) {
            resetColor(vistaAlert.panelAceptar);
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(KeyEvent.VK_ENTER == e.getKeyCode()){
            vistaAlert.dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        
    }
    
}
