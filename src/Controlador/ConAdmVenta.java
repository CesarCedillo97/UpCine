/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import controlador.ControladorPrincipal;
import Modelo.ModAdmVentas;
import Vista.AdmFormVentas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Jesus
 */
public class ConAdmVenta extends ControladorPrincipal implements ActionListener, MouseListener {

    ModAdmVentas modAdmVentas;
    AdmFormVentas admFormVentas;

    public ConAdmVenta(ModAdmVentas modAdmVentas, AdmFormVentas admFormVentas) {
        this.modAdmVentas = modAdmVentas;
        this.admFormVentas = admFormVentas;
    }
    
    @Override
     public void iniciarVista()
    {
        admFormVentas.setTitle("UpCine");
        admFormVentas.pack();
        admFormVentas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        admFormVentas.setLocationRelativeTo(null);
        admFormVentas.setVisible(true);
        admFormVentas.btnBuscar.addActionListener(this);
        admFormVentas.txtFechaInicio.addActionListener(this);
        admFormVentas.txtFechaFin.addActionListener(this);
        admFormVentas.panelBack.addMouseListener(this);
        admFormVentas.tabla.setModel(modAdmVentas.cargarTabla());
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(admFormVentas.btnBuscar == e.getSource()){
            String fechaInic = admFormVentas.txtFechaInicio.getEditor().getText();
            String fechaFin = admFormVentas.txtFechaFin.getEditor().getText();
            String nomEmpleado = admFormVentas.txtEmpleado.getText();
            int tipoVenta = -1;
            switch (admFormVentas.comboTipo.getSelectedIndex()) {
                case 0:
                    tipoVenta = 1;
                    break;
                case 1:
                    tipoVenta = 2;
                    break;
                case 2:
                    tipoVenta = 3;
                    break;
            }
            DefaultTableModel dtm = modAdmVentas.BuscarVenta(fechaInic, fechaFin, nomEmpleado, tipoVenta);
            if(dtm != null)
            {   
                System.out.println(tipoVenta);
                admFormVentas.tabla.setModel(dtm);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if(admFormVentas.panelBack == me.getSource()){
            admFormVentas.dispose();
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        if(admFormVentas.panelBack == me.getSource()){
            setColor(admFormVentas.panelBack);
        }
    }

    @Override
    public void mouseExited(MouseEvent me) {
        if(admFormVentas.panelBack == me.getSource()){
            resetColorSalir(admFormVentas.panelBack);
        }
    }
}
