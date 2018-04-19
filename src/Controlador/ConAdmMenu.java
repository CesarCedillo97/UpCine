/*
    Controlador del menu principal del administrador, a partir de aquí se deriva a los distintos modulos
 */
package controlador;

import Controlador.ConAdmVenta;
import Vista.AdmOpcProductos;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import Modelo.ModAdmMenu;
import Modelo.ModAdmVentas;
import Vista.AdmFormVentas;
import vista.AdmMenu;
import modelo.ModAdmABC;
import modelo.ModGenInicioSesion;
import vista.AdmABC;
import vista.GenInicioSesion;
/**
 *
 * @author adria
 */
public class ConAdmMenu extends ControladorPrincipal implements MouseListener
{
    ModAdmMenu modelo;
    AdmMenu AdmMenu;
    int idEmp;
    
    public ConAdmMenu(ModAdmMenu modelo, AdmMenu AdmMenu,int idEmp)
    {
        this.modelo = modelo;
        this.AdmMenu = AdmMenu;
        this.idEmp = idEmp;
    }
    
    @Override
    public void iniciarVista()
    {
        AdmMenu.setTitle("UpCine");
        AdmMenu.pack();
        AdmMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        AdmMenu.setLocationRelativeTo(null);
        AdmMenu.setVisible(true);
        AdmMenu.nombreEmpleado.setText("ID: "+this.idEmp);
        this.AdmMenu.panelEmpleados.addMouseListener((MouseListener) this);
        this.AdmMenu.panelFunciones.addMouseListener((MouseListener) this);
        this.AdmMenu.panelPeliculas.addMouseListener((MouseListener) this);
        this.AdmMenu.panelPrecios.addMouseListener((MouseListener) this);
        this.AdmMenu.panelProductos.addMouseListener((MouseListener) this);
        this.AdmMenu.panelSalas.addMouseListener((MouseListener) this);
        this.AdmMenu.panelProveedores.addMouseListener((MouseListener) this);
        this.AdmMenu.panelCompras.addMouseListener((MouseListener) this);
        this.AdmMenu.panelCerrarSesion.addMouseListener((MouseListener) this);
        this.AdmMenu.panelVentas.addMouseListener(this);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        ModAdmABC modAdmABC = new ModAdmABC();
        AdmABC visAdmABC = new AdmABC();
        
        if(AdmMenu.lblCerrarSesion != e.getSource()){
            ConAdmABC conAdmABC = null;
            //se carga el modulo según al panel que se haya dado click
            if (AdmMenu.panelEmpleados==e.getSource()) {
                conAdmABC = new ConAdmABC(modAdmABC, visAdmABC,idEmp, 1);
            }
            else if (AdmMenu.panelFunciones==e.getSource()) {
                conAdmABC = new ConAdmABC(modAdmABC, visAdmABC, idEmp, 2);
            }
            else if (AdmMenu.panelPeliculas==e.getSource()) {
                conAdmABC = new ConAdmABC(modAdmABC, visAdmABC, idEmp, 3);
            }
            else if (AdmMenu.panelProductos==e.getSource()) {
                AdmOpcProductos vistaPro = new AdmOpcProductos();
                Controlador.ConAdmOpcProductos conP = new Controlador.ConAdmOpcProductos(vistaPro, idEmp);
                conP.iniciarVista();
            }
            else if (AdmMenu.panelPrecios==e.getSource()) {
                conAdmABC = new ConAdmABC(modAdmABC, visAdmABC, idEmp, 5);
            }
            else if (AdmMenu.panelSalas==e.getSource()) {
                conAdmABC = new ConAdmABC(modAdmABC, visAdmABC, idEmp, 6);
            }
            else if (AdmMenu.panelProveedores == e.getSource()) {
                conAdmABC = new ConAdmABC(modAdmABC, visAdmABC, idEmp, 7);
            }
            else if (AdmMenu.panelCompras == e.getSource()) {
                conAdmABC = new ConAdmABC(modAdmABC, visAdmABC, idEmp, 8);
            }
            else if (AdmMenu.panelVentas == e.getSource()) {
                ModAdmVentas modAdmVentas = new ModAdmVentas();
                AdmFormVentas visAdmVentas= new AdmFormVentas(); 
                ConAdmVenta conAdmVenta = new ConAdmVenta(modAdmVentas, visAdmVentas);
                conAdmVenta.iniciarVista();
            }
            else if(AdmMenu.panelCerrarSesion == e.getSource()){
                AdmMenu.dispose();
                ModGenInicioSesion modInicioSesion = new ModGenInicioSesion();
                GenInicioSesion visInicioSesion = new GenInicioSesion();
                ConGenInicioSesion conInicioSesion = new ConGenInicioSesion(modInicioSesion, visInicioSesion);
                conInicioSesion.iniciarVista();
            }
            if(conAdmABC != null)
            {
                AdmMenu.dispose();
                conAdmABC.iniciarVista();
                
            }
            
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
        AdmMenu.setCursor(Cursor.HAND_CURSOR);
        if (AdmMenu.panelSalas==e.getSource()) {
            setColor(AdmMenu.panelSalas);
        }
        else if (AdmMenu.panelEmpleados==e.getSource()) {
            setColor(AdmMenu.panelEmpleados);
        }
        else if (AdmMenu.panelFunciones==e.getSource()) {
            setColor(AdmMenu.panelFunciones);
        }
        else if (AdmMenu.panelPeliculas==e.getSource()) {
            setColor(AdmMenu.panelPeliculas);
        }
        else if (AdmMenu.panelPrecios==e.getSource()) {
            setColor(AdmMenu.panelPrecios);
        }
        else if (AdmMenu.panelProductos==e.getSource()) {
            setColor(AdmMenu.panelProductos);
        }
        else if (AdmMenu.panelProveedores==e.getSource()) {
            setColor(AdmMenu.panelProveedores);
        }
        else if(AdmMenu.panelCompras == e.getSource()){
            setColor(AdmMenu.panelCompras);
        }
        if (AdmMenu.panelVentas==e.getSource()) {
            setColor(AdmMenu.panelVentas);
        }
        else if (AdmMenu.panelCerrarSesion==e.getSource()) {
            setColor(AdmMenu.panelCerrarSesion);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        AdmMenu.setCursor(Cursor.DEFAULT_CURSOR);
        if (AdmMenu.panelSalas==e.getSource()) {
            resetColor(AdmMenu.panelSalas);
        }
        else if (AdmMenu.panelEmpleados==e.getSource()) {
            resetColor(AdmMenu.panelEmpleados);
        }
        else if (AdmMenu.panelFunciones==e.getSource()) {
            resetColor(AdmMenu.panelFunciones);
        }
        else if (AdmMenu.panelPeliculas==e.getSource()) {
            resetColor(AdmMenu.panelPeliculas);
        }
        else if (AdmMenu.panelPrecios==e.getSource()) {
            resetColor(AdmMenu.panelPrecios);
        }
        else if (AdmMenu.panelProductos==e.getSource()) {
            resetColor(AdmMenu.panelProductos);
        }
        else if (AdmMenu.panelProveedores==e.getSource()) {
            resetColor(AdmMenu.panelProveedores);
        }
        else if(AdmMenu.panelCompras == e.getSource()){
            resetColor(AdmMenu.panelCompras);
        }
        else if (AdmMenu.panelVentas==e.getSource()) {
            resetColor(AdmMenu.panelVentas);
        }
        else if (AdmMenu.panelCerrarSesion==e.getSource()) {
            resetColorSalir(AdmMenu.panelCerrarSesion);
        }
    }
}
