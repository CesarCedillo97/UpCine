/*
    
 */
package Controlador;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import Modelo.ModAdmPrecio;
import Vista.AdmFormBoleto;
import Vista.GenSucces;
import javax.swing.JFrame;
import vista.GenAlert;
/**
 *
 * @author Jesus
 */
public class ConAdmFormBoleto extends ControladorPrincipal implements MouseListener {
    ModAdmPrecio modAdmPrecio;
    AdmFormBoleto admFormBoleto;
    GenAlert genAlert = new GenAlert();
    GenSucces genSuccess = new GenSucces();
    int opcion;
    
    private String id, descripcion,precio;

    public ConAdmFormBoleto(ModAdmPrecio modAdmPrecio,AdmFormBoleto admFormBoleto, int opcion){
        this.admFormBoleto= admFormBoleto;
        this.modAdmPrecio = modAdmPrecio;
        this.opcion = opcion;
    
    }
    public void iniciarVista()
    {
        admFormBoleto.setTitle("UpCine");
        admFormBoleto.title.setText((opcion == 1?"Agregar ": "Modificar ")+"Precio");
        admFormBoleto.pack();
        admFormBoleto.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        admFormBoleto.setLocationRelativeTo(null);
        admFormBoleto.setVisible(true);
        genSuccess.panelAceptar.addMouseListener((MouseListener) this);
    }
    @Override
    public void mouseClicked(MouseEvent me) {
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
