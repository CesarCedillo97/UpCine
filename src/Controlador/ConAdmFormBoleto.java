/*
    
 */
package Controlador;
import Modelo.ModAdmFormBoletos;
import controlador.ControladorPrincipal;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import Vista.AdmFormBoleto;
import Vista.GenSucces;
import javax.swing.JFrame;
import vista.GenAlert;
import controlador.ConSucces;
import controlador.ConAlert;
/**
 *
 * @author Jesus
 */
public class ConAdmFormBoleto extends ControladorPrincipal implements MouseListener {
    ModAdmFormBoletos modFormBoletos;
    AdmFormBoleto admFormBoleto;
    GenAlert genAlert = new GenAlert();
    GenSucces genSuccess = new GenSucces();
    int opcion;
    
    private String id, descripcion,precio;

    public ConAdmFormBoleto(ModAdmFormBoletos modFormBoletos,AdmFormBoleto admFormBoleto){
        this.admFormBoleto= admFormBoleto;
        this.modFormBoletos = modFormBoletos;
    }
    
    @Override
    public void iniciarVista()
    {
        admFormBoleto.setTitle("UpCine");
        admFormBoleto.pack();
        admFormBoleto.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        admFormBoleto.setLocationRelativeTo(null);
        admFormBoleto.setVisible(true);
        admFormBoleto.panelAdd.addMouseListener(this);
        admFormBoleto.panelBack.addMouseListener(this);
        genSuccess.panelAceptar.addMouseListener((MouseListener) this);
    }
    @Override
    public void mouseClicked(MouseEvent me) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(admFormBoleto.panelAdd == e.getSource()){
            if(!"".equals(admFormBoleto.txtPrecio.getText())){
                if(modFormBoletos.modificarBoletos(admFormBoleto.txtDescripcion.getText(), admFormBoleto.txtPrecio.getText())){
                    ConSucces success = new ConSucces(genSuccess, "¡Éxito", "Se ha modificado correctamente");
                    genSuccess.panelAceptar.addMouseListener(this);
                    success.iniciarVista();
                }
                else{
                    ConAlert alert = new ConAlert(genAlert, "No se ha modificado correctamente");
                    alert.iniciarVista();
                }
            }
        }
        else if(admFormBoleto.panelBack == e.getSource()){
            admFormBoleto.dispose();
        }
        else if(genSuccess.panelAceptar == e.getSource()){
            admFormBoleto.dispose();
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(admFormBoleto.panelAdd == e.getSource()){
            setColorAceptar(admFormBoleto.panelAdd);
        }
        else if(admFormBoleto.panelBack == e.getSource()){
            setColorCancelar(admFormBoleto.panelBack);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(admFormBoleto.panelAdd == e.getSource()){
            resetColor(admFormBoleto.panelAdd);
        }
        else if(admFormBoleto.panelBack == e.getSource()){
            resetColor(admFormBoleto.panelBack);
        }
    }
    
}
