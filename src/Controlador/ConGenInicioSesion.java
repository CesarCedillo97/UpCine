/*
    Controlador de pantalla para el inicio de sesión
 */
package controlador;
import modelo.ModGenInicioSesion;
import vista.GenInicioSesion;

import Modelo.ModAdmMenu;
import vista.AdmMenu;
import Modelo.ModEmpOpcVender;
import vista.EmpOpcVender;

import vista.GenAlert;
import java.awt.Cursor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

/**
 *
 * @author adria
 */
public class ConGenInicioSesion extends ControladorPrincipal implements ActionListener,MouseListener, KeyListener
{

    ModGenInicioSesion modInicio;
    GenInicioSesion visInicio;
    
    int falloInicio;
    
    public ConGenInicioSesion(ModGenInicioSesion modInicio, GenInicioSesion visInicio)
    {
        this.modInicio = modInicio;
        this.visInicio = visInicio;
    }
    
    @Override
    public void iniciarVista()
    {
        visInicio.setTitle("Inicio de sesión");
        visInicio.pack();
        visInicio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        visInicio.setLocationRelativeTo(null);
        visInicio.setVisible(true);
        visInicio.btnLogin.addMouseListener((MouseListener) this);
        visInicio.btnSalir.addMouseListener((MouseListener) this);
        visInicio.txtContra.addKeyListener(this);
        visInicio.btnLogin.addKeyListener(this);
        visInicio.btnSalir.addKeyListener(this);
    }
    
    public void verificarInicio(){
        int idEmpleado = -1;
        int tipoEmpleado;
        String nombre = "";
        if(falloInicio < 5){ //verifica si no se ha fallado en el inicio de sesion más de 5 veces
            idEmpleado = modInicio.consultarInicio(visInicio.txtUsuario.getText(), visInicio.txtContra.getText()); //checa si el inicio es correcto y asigna el id a IdEmpleado si lo encuentra, de lo contrario, asigna un -1
            nombre = modInicio.VerificarNombre(idEmpleado); //obtiene el nombre del empleado a partir del ID 
        }
        if(idEmpleado != -1){   //si se encontró una coincidencia en el inicio de sesion
            falloInicio = 0;    
            tipoEmpleado = modInicio.verificarTipoAcceso(idEmpleado); //checa si es admin o empleado comun
            visInicio.dispose(); 
            if(tipoEmpleado == 1){  //Si es admin
                ModAdmMenu modMenu = new ModAdmMenu();
                AdmMenu visMenu = new AdmMenu();
                ConAdmMenu conMenu = new ConAdmMenu(modMenu, visMenu, idEmpleado);
                conMenu.iniciarVista();
            }
            else if(tipoEmpleado==2){   //si es empleado
                ModEmpOpcVender modOpcVender = new ModEmpOpcVender();
                EmpOpcVender visOpcVender = new EmpOpcVender();
                ConEmpOpcVender ConOpcVende = new ConEmpOpcVender(modOpcVender, visOpcVender, nombre,idEmpleado);
                ConOpcVende.iniciarVista();
            }

        }
        else{   //si no encuentra una coincidencia
            falloInicio++; 
            if(falloInicio >=5){ //no permite iniciar sesión despues de 5 fallas en el inicio de sesión
                GenAlert visAlert = new GenAlert();
                ConAlert alert = new ConAlert(visAlert, "Sistema temporalmente bloqueado, ","favor de contactar al administrador.");
                alert.iniciarVista();
            }
            else{
                String mensaje = ("Usuario y/o contraseña incorrectos, \nquedan "+(5-falloInicio)+" intentos");
                GenAlert visAlert = new GenAlert();
                ConAlert alert = new ConAlert(visAlert, mensaje);
                alert.iniciarVista();
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if(visInicio.btnLogin == e.getSource() && falloInicio < 5){ //mandar llamar la función verificarInicio al dar click en el boton iniciar sesion
            verificarInicio();
        }else if (visInicio.btnSalir == e.getSource()) {
            System.exit(0);
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        visInicio.setCursor(Cursor.HAND_CURSOR);
        if (visInicio.btnLogin == e.getSource()) {
            visInicio.btnLogin.setBackground(new java.awt.Color(115, 163, 239));
        }else if (visInicio.btnSalir == e.getSource()) {
            visInicio.btnSalir.setBackground(new java.awt.Color(115, 163, 239));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        visInicio.setCursor(Cursor.DEFAULT_CURSOR);
        if (visInicio.btnLogin == e.getSource()) {
            visInicio.btnLogin.setBackground(new java.awt.Color(240,240,240));
        }else if (visInicio.btnSalir == e.getSource()) {
            visInicio.btnSalir.setBackground(new java.awt.Color(240,240,240));
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(KeyEvent.VK_ENTER == e.getKeyCode() && (visInicio.btnLogin == e.getSource() || visInicio.txtContra == e.getSource())){
            verificarInicio();
        }
        else if(visInicio.btnSalir == e.getSource()){
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        
    }

    
}
