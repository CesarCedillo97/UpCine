/*
    Controlador de ventana que sirve para elegir la opcion en la que estará trabajando
 */
package controlador;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import Modelo.ModEmpOpcVender;
import vista.EmpOpcVender;
import modelo.ModEmpVentaDulces;
import vista.EmpVentaDulces;
import vista.GenInicioSesion;
import modelo.ModGenInicioSesion;
import modelo.ModEmpVentaPeli;
import vista.EmpVentaBoleto;
/**
 *
 * @author adria
 */
public class ConEmpOpcVender extends ControladorPrincipal implements MouseListener
{
    ModEmpOpcVender ModOpcVender;
    EmpOpcVender VisOpcVender; 
    String nombre;
    private final int idEmp;

    public ConEmpOpcVender(ModEmpOpcVender ModOpcVender, EmpOpcVender VisOpcVender, String Nombre, int idEmp)
    {
        this.ModOpcVender = ModOpcVender;
        this.VisOpcVender = VisOpcVender;
        this.nombre = Nombre;
        this.idEmp = idEmp;
    }
    
    @Override
    public void iniciarVista()
    {
        VisOpcVender.setTitle("Selección de venta");
        VisOpcVender.pack();
        VisOpcVender.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VisOpcVender.setLocationRelativeTo(null);
        VisOpcVender.setVisible(true);
        VisOpcVender.panelBoleto.addMouseListener(this);
        VisOpcVender.panelDulces.addMouseListener(this);
        VisOpcVender.lblNombre.setText(nombre);
        VisOpcVender.panelSalir.addMouseListener(this);
    }



    @Override
    public void mouseClicked(MouseEvent e) {
         //entra a boletos 
        if(VisOpcVender.panelBoleto == e.getSource()){
            EmpVentaBoleto vista = new EmpVentaBoleto();
            ModEmpVentaPeli modelo = new ModEmpVentaPeli();
            ConEmpVentaPeli ventaBoleto = new ConEmpVentaPeli(modelo, vista, idEmp, nombre);
            VisOpcVender.dispose();
            ventaBoleto.iniciarVista();
        }
        //entra a la dulceria 
        else if(VisOpcVender.panelDulces == e.getSource()){
            ModEmpVentaDulces modVentaDulces = new ModEmpVentaDulces();
            EmpVentaDulces empVentaDulces = new EmpVentaDulces();
            ConEmpVentaDulces conVentaDulces = new ConEmpVentaDulces(modVentaDulces, empVentaDulces, idEmp);
            conVentaDulces.iniciarVista();
        }
        //cierra sesión
        else if(VisOpcVender.panelSalir == e.getSource()){
            VisOpcVender.dispose();
            ModGenInicioSesion modInicioSesion = new ModGenInicioSesion();
            GenInicioSesion visInicioSesion = new GenInicioSesion();
            ConGenInicioSesion conInicioSesion = new ConGenInicioSesion(modInicioSesion, visInicioSesion);
            conInicioSesion.iniciarVista();
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

        if(VisOpcVender.panelBoleto == e.getSource()){
            setColor(VisOpcVender.panelBoleto);
        }

        else if(VisOpcVender.panelDulces == e.getSource()){
            setColor(VisOpcVender.panelDulces);
        }

        else if(VisOpcVender.panelSalir == e.getSource()){
            setColor(VisOpcVender.panelSalir);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(VisOpcVender.panelBoleto == e.getSource()){
            resetColor(VisOpcVender.panelBoleto);
        }

        else if(VisOpcVender.panelDulces == e.getSource()){
            resetColor(VisOpcVender.panelDulces);
        }

        else if(VisOpcVender.panelSalir == e.getSource()){
            resetColor(VisOpcVender.panelSalir);
        }
    }
    
    
}
