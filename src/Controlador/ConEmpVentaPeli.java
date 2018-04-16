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
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import modelo.ModEmpVentaPeli;
import Vista.EmpSelectAsientos;
/**
 *
 * @author Cesar Cedillo
 */
public class ConEmpVentaPeli extends ControladorPrincipal implements MouseListener,FocusListener{
    EmpVentaBoleto vista;
    ModEmpVentaPeli modelo;
    private int id;
    private String seleccion;
    private String[][] listPelis;
    private String[][] listPelis2;
    private int sala;
    ButtonGroup group = new ButtonGroup();
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
        vista.panelNext.addMouseListener(this);
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) vista.txtNormal.getEditor();
        editor.getTextField().addFocusListener(this);
        JSpinner.DefaultEditor editor2 = (JSpinner.DefaultEditor) vista.txtMayor.getEditor();
        editor2.getTextField().addFocusListener(this);
        
        JSpinner.DefaultEditor editor3 = (JSpinner.DefaultEditor) vista.txtEstud.getEditor();
        editor3.getTextField().addFocusListener(this);
        
        listPelis = modelo.consultarPeliculas();
        int filas = 0; 
        for(int x =0; x < listPelis.length; x++){
            if(Integer.parseInt(listPelis[x][0]) > (x > 0 ? Integer.parseInt(listPelis[x-1][0]): x)){
                filas++;
            }
        }
        listPelis2 = new String[filas][3];
        int j=0;
        int counter = 0;
        for (int x =0; x < listPelis.length; x++){
            if(Integer.parseInt(listPelis[x][0]) > (x > 0 ? Integer.parseInt(listPelis[x-1][0]): x)){
                JPanel panel = new JPanel(new BorderLayout());
                JPanel panelHoras = new JPanel();
                j = counter*120;
                panel.add(new JLabel(new ImageIcon(getClass().getResource("/movieImages/"+listPelis[x][6]))),BorderLayout.WEST);
                JLabel label = new JLabel(listPelis[x][1]+" "+listPelis[x][4]+" ("+listPelis[x][2]+") Sub: "+listPelis[x][3]);
                label.setFont(new Font("Arial", Font.PLAIN, 18));
                panel.add(label,BorderLayout.NORTH);
                panel.addMouseListener(this);
                listPelis2[counter][0] = listPelis[x][0];
                listPelis2[counter][1] = String.valueOf(j);
                listPelis2[counter][2] = String.valueOf(j+120);
                panel.add(panelHoras);
                panel.setBounds(5,j,530,120);
                vista.panelPelis.add(panel);
                counter++;
            }            
        }
        JPanel panelBoletos = new JPanel();
        panelBoletos.setBounds(50, 50, 360, 150);
        vista.add(panelBoletos);
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
        else if(vista.panelNext == e.getSource()){
            vista.dispose();
            EmpSelectAsientos visAsientos = new EmpSelectAsientos();
            int[][] asientos = modelo.obtenerAsientos(sala);
            boolean seatsBool;
            for (int i = 0; i < asientos.length; i++)
            {
                for (int j = 0; j < asientos[0].length; j++)
                {
                    if(asientos[i][j] == 1){
                        
                    }
                    else if(asientos[i][j]==0){
                        
                    }
                }
            }
            visAsientos.setLocationRelativeTo(null);
            visAsientos.setVisible(true);
        }
        else{
            Point p1 = new Point();
            Point p2 = new Point();
            boolean sw = false;
            vista.comboHorario.removeAllItems();
            for (int i = 0; i < listPelis2.length; i++){
                p1.x = 5;
                p1.y = Integer.parseInt(listPelis2[i][1]);
                p2.x = 5; 
                p2.y = Integer.parseInt(listPelis2[i][2]);
                if(vista.panelPelis.getMousePosition().getY() > p1.y && vista.panelPelis.getMousePosition().y < p2.y){
                    for (String[] listPeli : listPelis)
                    {
                        if (listPeli[0].equals(listPelis2[i][0]))
                        {
                            if(sw != true){
                                vista.txtPelicula.setText(listPeli[1]);   
                                sala = Integer.parseInt(listPeli[7]);
                            }
                            vista.comboHorario.addItem(listPeli[5]);
                        }
                    }
                }
                if(sw == true)
                    break;
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
    @Override
    public void focusGained(FocusEvent e)
    {
        
    }

    @Override
    public void focusLost(FocusEvent e)
    {
        float IVA = modelo.obtenerIVA();
        vista.txtNormal.getValue();
        vista.txtEstud.getValue();
        vista.txtMayor.getValue();
        float subT = Float.parseFloat(String.valueOf(vista.txtNormal.getValue())) * 40;
        subT += Float.parseFloat(String.valueOf(vista.txtMayor.getValue()))*25;
        subT += Float.parseFloat(String.valueOf(vista.txtEstud.getValue()))*30; 
        vista.txtSubtotal1.setText(""+subT);
        vista.txtIva.setText(""+(subT*(IVA/100)));
        vista.txtTotal.setText(""+(subT + Float.parseFloat(vista.txtIva.getText())));
    }
    
}
