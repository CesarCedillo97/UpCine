/*
    Controlador para dar de alta salas y modificarlas
    asi como sus respectivos asientos
 */
package controlador;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import vista.AdmFormSalas;
import vista.AdmFormAsientos;
import modelo.ModAdmFormSalas;
import Vista.GenSucces;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import vista.GenAlert;
/**
 *
 * @author adria
 */
public class ConAdmFormSalas extends ControladorPrincipal implements MouseListener, KeyListener
{
    AdmFormSalas formSalas;
    ModAdmFormSalas modFormSalas;
    AdmFormAsientos formAsientos = new AdmFormAsientos();
    GenSucces genSucess = new GenSucces();
    GenAlert genAlert = new GenAlert();
    int opcion;
    JLabel[][] arregloAsientos;
    boolean[][] seatsBool;
    
    private int filas, columnas, estado, numAsientos = 0, id;
    private Object tipo;
    public ConAdmFormSalas(AdmFormSalas formSalas, ModAdmFormSalas modFormSalas, int opcion)
    {
        this.formSalas = formSalas;
        this.modFormSalas = modFormSalas;
        this.opcion = opcion;
    }
    
    @Override
    public void iniciarVista()
    {
        formSalas.setTitle("UpCine");
        formSalas.pack();
        formSalas.title.setText((opcion==1?"Agregar ":"Modificar")+" Sala");
        formSalas.setLocationRelativeTo(null);
        formSalas.setVisible(true);
        formSalas.txtCols.addKeyListener(this);
        formSalas.txtEstatus.addKeyListener(this);
        formSalas.panelSig.addMouseListener((MouseListener)this);
        genSucess.panelAceptar.addMouseListener((MouseListener)this);
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if(formSalas.panelSig == e.getSource()){
           clickSiguiente();
        }
       
        else if(formAsientos.panelAdd == e.getSource()){
            //asignaciones de variables
            id = "".equals(formSalas.txtId.getText())?-1:Integer.parseInt(formSalas.txtId.getText());
            System.out.println("id "+id);
            filas = Integer.parseInt(formSalas.txtFilas.getText());
            columnas = Integer.parseInt(formSalas.txtCols.getText());
            tipo = String.valueOf(formSalas.txtTipo.getSelectedItem());
            estado = formSalas.txtEstatus.getSelectedIndex();
            if(opcion == 1 && modFormSalas.insertarSalas(filas, columnas, numAsientos, String.valueOf(tipo), estado, seatsBool)){
                ConSucces conSuccess = new ConSucces(genSucess,"¡Éxito!", "Se ha insertado de manera correcta");
                genSucess.addKeyListener(this);
                conSuccess.iniciarVista();
            }
            else if(opcion == 2 && modFormSalas.eliminarAsientos(id) && modFormSalas.modificarSala(id, filas, columnas, numAsientos, String.valueOf(tipo), estado, seatsBool)){
                ConSucces conSuccess = new ConSucces(genSucess, "¡Éxito!","Se ha modificado de manera correcta");
                genSucess.addKeyListener(this);
                conSuccess.iniciarVista();
            }
            else{
                ConAlert conAlert = new ConAlert(genAlert, "No se ha podido insertar, intente de nuevo");
                genAlert.addKeyListener(this);
                conAlert.iniciarVista();
            }
        }
        else if(formAsientos.panelBack == e.getSource()){
            formAsientos.dispose();
        }
        else if(genSucess.panelAceptar == e.getSource()){
            formAsientos.dispose();
            formSalas.dispose();
            genSucess.dispose();
        }
        else{//para saber en que asiento se hizo click
            for (int i = 0; i < filas; i++)
            {
                for (int j = 0; j < columnas; j++)
                {
                    if(arregloAsientos[i][j] == e.getSource()){
                        if(seatsBool[i][j] == false){
                            arregloAsientos[i][j].setIcon(new ImageIcon(getClass().getResource("/iconos/asientoAzul.png")));
                            seatsBool[i][j] = true;
                            numAsientos++;
                        }
                        else{
                            arregloAsientos[i][j].setIcon(new ImageIcon(getClass().getResource("/iconos/asientoRojo.png")));
                            seatsBool[i][j] = false;
                            numAsientos--;
                        }
                    }
                }
            }
        }
    }
    
    public boolean clickSiguiente(){
        if(!"".equals(formSalas.txtFilas.getText()) && !("".equals(formSalas.txtCols.getText()))){
            boolean boolFilas = validacionNum(formSalas.txtFilas.getText(),0);//se verifica que se ingresaron numeros enteros
            boolean boolCols = validacionNum(formSalas.txtCols.getText(),0);
            if(boolFilas && boolCols){
                id = "".equals(formSalas.txtId.getText())?-1:Integer.parseInt(formSalas.txtId.getText());
                filas = Integer.parseInt(formSalas.txtFilas.getText());
                columnas = Integer.parseInt(formSalas.txtCols.getText());
                formSalas.dispose();
                //se preparan para iniciar la vista de asientos
                formAsientos.panelAdd.addMouseListener((MouseListener) this);
                formAsientos.panelBack.addMouseListener((MouseListener) this);
                formAsientos.setSize(700, 700);
                formAsientos.setLocationRelativeTo(null);
                formAsientos.title.setText("Seleccionar asientos");
                arregloAsientos = new JLabel[filas][columnas]; //esta variable almacena todos los labels que se imprimiran
                seatsBool = new boolean[filas][columnas]; //esta variable funciona para saber si esta activo o no
                formAsientos.panelAsientos.setLayout(new GridLayout(filas,columnas));
                formAsientos.panelAsientos.setSize(300, 300);
                if(opcion == 2){
                    //se obtienen los asientos de la sala en caso de que se deseen modificar
                seatsBool = modFormSalas.consultarAsientos(id, filas, columnas);
                    if(seatsBool != null){
                        pintarAsientos();//se pintan los asientos
                    }
                    else{
                        System.out.println("No se pudo realizar la consulta");
                    }          
                }
                else if(opcion == 1){
                    pintarAsientos(); 
                    numAsientos = filas * columnas;
                }
            formAsientos.setVisible(true);
                return true;
            }
            else{
                String msg2 = "";
                if(!boolCols && !boolFilas) msg2 = "Filas y columnas";
                else if(!boolCols)   msg2 = "Columnas";
                else if(!boolFilas) msg2= "Filas"; 
                ConAlert conAlert = new ConAlert(genAlert,"Se introdujeron valores no numericos en: ", msg2);
                conAlert.iniciarVista();
                return false;
            }
        }
        else{
            ConAlert conAlert = new ConAlert(genAlert, "Rellene todos los campos para proseguir");
            genAlert.addKeyListener(this);
            conAlert.iniciarVista();
            return false;
        }
    }
    
    public void pintarAsientos(){
        //pinta los asientos en el panel
        for (int i = filas-1; i >= 0 ; i--)
        {
            for (int j = 0; j < columnas; j++)
            {
                if(seatsBool[i][j] && opcion == 2){
                    arregloAsientos[i][j] = new JLabel(getLetra(i+1)+"-"+j,new ImageIcon(getClass().getResource("/iconos/asientoAzul.png")),0);
                    numAsientos+=1;
                }
                else if(!seatsBool[i][j] && opcion == 1){
                    arregloAsientos[i][j] = new JLabel(getLetra(i+1)+"-"+j,new ImageIcon(getClass().getResource("/iconos/asientoAzul.png")),0);
                    seatsBool[i][j] = true;
                }
                else{
                    arregloAsientos[i][j] = new JLabel(getLetra(i+1)+"-"+j,new ImageIcon(getClass().getResource("/iconos/asientoRojo.png")),0);
                }

                arregloAsientos[i][j].setHorizontalTextPosition(JLabel.CENTER);
                arregloAsientos[i][j].setVerticalTextPosition(JLabel.BOTTOM);
                formAsientos.panelAsientos.add(arregloAsientos[i][j]);
                arregloAsientos[i][j].addMouseListener((MouseListener)this);
            }
        }
    }
    //función que convierte de int a char 
    private String getLetra(int i) {
        return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
    }
    @Override
    public void mousePressed(MouseEvent e)
    {
        
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        if(formSalas.panelSig == e.getSource()){
            setColor(formSalas.panelSig);
        }
        else if(formAsientos.panelAdd == e.getSource()){
            setColorAceptar(formAsientos.panelAdd);
        }
        else if(formAsientos.panelBack == e.getSource()){
            setColorCancelar(formAsientos.panelBack);
        }
        else if(genSucess.panelAceptar == e.getSource()){
            setColorAceptar(genSucess.panelAceptar);
        }
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        if(formSalas.panelSig == e.getSource()){
            resetColor(formSalas.panelSig);
        }
        else if(formAsientos.panelAdd == e.getSource()){
            resetColor(formAsientos.panelAdd);
        }
        else if(formAsientos.panelBack == e.getSource()){
            resetColor(formAsientos.panelBack);
        }
        else if(genSucess.panelAceptar == e.getSource()){
            resetColor(genSucess.panelAceptar);
        }
        
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(KeyEvent.VK_ENTER == e.getKeyCode() && genAlert == e.getSource()){
            genAlert.dispose();
        }
        else if(KeyEvent.VK_ENTER == e.getKeyCode() && genSucess == e.getSource()){
            formAsientos.dispose();
            formSalas.dispose();
            genSucess.dispose();
        }
        else if(KeyEvent.VK_ENTER == e.getKeyCode() && (formSalas.txtCols == e.getSource()||formSalas.txtEstatus == e.getSource())){
            clickSiguiente();
            
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        
    }
}
