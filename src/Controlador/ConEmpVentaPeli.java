/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import Vista.EmpVentaBoleto;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import vista.GenAlert;
import Vista.GenSucces;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import vista.GenConfirm;
import modelo.ModEmpVentaPeli;

/**
 *
 * @author Cesar Cedillo
 */
public class ConEmpVentaPeli extends ControladorPrincipal implements MouseListener{
    EmpVentaBoleto vista;
    ModEmpVentaPeli modelo;
    private int id;
    private String[][] listPelis;

    public ConEmpVentaPeli(ModEmpVentaPeli modelo, EmpVentaBoleto vista) {
        this.vista = vista;
        this.modelo = modelo;
    }
    

    @Override
    public void iniciarVista() {
        vista.setTitle("UpCine");
        vista.pack();
        vista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        vista.panelBack.addMouseListener(this);
        listPelis = modelo.consultarPeliculas();
        int j=0;
        for (int x =0; x < listPelis.length; x++){
            JPanel panel = new JPanel(new GridLayout(1, 2));
            j = (x*120);
            panel.add(new JLabel(new ImageIcon(getClass().getResource("/movieImages/"+listPelis[x][6]))));
            panel.add(new JPanel().add(new JLabel("6:30")));
            panel.setBounds(5,j,530,120);
            vista.panelPelis.add(panel);
            
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (vista.panelBack == e.getSource()) {
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
        if (vista.panelBack == e.getSource()) {
            setColor(vista.panelBack);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (vista.panelBack == e.getSource()) {
            resetColorSalir(vista.panelBack);
        }
    }
    
}
