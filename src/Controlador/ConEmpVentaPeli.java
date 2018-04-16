/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import vista.EmpVentaBoleto;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import vista.GenAlert;
import Vista.GenSucces;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.plaf.FontUIResource;
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
    
    public ConEmpVentaPeli(ModEmpVentaPeli modelo, EmpVentaBoleto vista,int idEmp) {
        this.vista = vista;
        this.modelo = modelo;
        this.id = idEmp;
    }
    

    @Override
    public void iniciarVista() {
        vista.setTitle("UpCine");
        vista.pack();
        vista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vista.setLocationRelativeTo(null);
        vista.pack();
        vista.panelBack.addMouseListener(this);
        listPelis = modelo.consultarPeliculas();
        int j=0;
        ButtonGroup group = new ButtonGroup();
        int counter = 0;
        for (int x =0; x < listPelis.length; x++){
            if(Integer.parseInt(listPelis[x][0]) > x){
                JPanel panel = new JPanel(new BorderLayout());
                JPanel panelHoras = new JPanel();
                j = counter*120;
                panel.add(new JLabel(new ImageIcon(getClass().getResource("/movieImages/"+listPelis[x][6]))),BorderLayout.WEST);
                JLabel label = new JLabel(listPelis[x][1]+" "+listPelis[x][4]+" ("+listPelis[x][2]+") Sub: "+listPelis[x][3]);
                label.setFont(new Font("Arial", Font.PLAIN, 18));
                panel.add(label,BorderLayout.NORTH);
                
                for (String[] listPeli : listPelis)
                {
                    if (listPelis[x][0].equals(listPeli[0]))   
                    {
                        JRadioButton radio = new JRadioButton(listPeli[5]);
                        radio.addMouseListener(this);
                        group.add(radio);
                        panelHoras.add(radio,BorderLayout.CENTER);
                    }
                }
                panel.add(panelHoras);
                panel.setBounds(5,j,530,120);
                vista.panelPelis.add(panel);
                counter++;
            }            
        }
        vista.setVisible(true);
    }
    
    public boolean esIdRepetido(String id, String[][] lista, int x){
        for (int i = 0; i < x; i++){
            return lista[i][0].equals(id);
        }
        return false;
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
