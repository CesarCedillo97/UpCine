/*
    Controlador para agregar y modificar funciones
 */
package controlador;

import modelo.ModAdmFormFunciones;
import vista.AdmFormFunciones;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import Vista.GenSucces;
import vista.GenAlert;
/**
 *
 * @author Cesar Cedillo
 */
public class ConAdmFormFunciones extends ControladorPrincipal implements ActionListener, MouseListener, ChangeListener{
    private ModAdmFormFunciones modelo;
    private AdmFormFunciones vista;
    private int opcion;
    private String fechaIni, fechaFin, horaIni, horaFin;
    private int idPeli = -1,  idSala = -1, idFuncion = -1, estatus = -1;
    GenSucces genSuccess = new GenSucces();
    GenAlert genAlert = new GenAlert();

    
    private String[][] pelis;
    private String[] salas ;
    public ConAdmFormFunciones(ModAdmFormFunciones modelo, AdmFormFunciones vista, int opcion){
        this.modelo = modelo;
        this.vista =  vista;
        this.opcion = opcion;        
    }
    
    @Override
    public void iniciarVista(){
        vista.setTitle("UpCine");
        vista.title.setText((opcion == 1? "Agregar ": "Modificar ")+"Funciones");
        vista.pack();
        vista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vista.setLocationRelativeTo(null);
        if(opcion != 2){
            cargarCombos();
        }
        setValueTxtHoraFin();
        vista.txtHoraInicio.addChangeListener(this);
        vista.txtComboPeli.addActionListener(this);
        vista.panelAdd.addMouseListener((MouseListener) this);
        vista.panelBack.addMouseListener((MouseListener) this);
        genSuccess.panelAceptar.addMouseListener((MouseListener) this);
        vista.setVisible(true);
    }
    //Carga los datos a los comboBox 
    public void cargarCombos(){
        pelis = modelo.obtenerPeliculas();
        salas = modelo.obtenerSalas();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (String[] peli : pelis){
            model.addElement(peli[0]);
        }
        vista.txtComboPeli.setModel(model);
        if(salas != null)
            vista.txtComboSala.setModel(new javax.swing.DefaultComboBoxModel(salas));
    }
    //Calcula la hora de terminación de la pelicula y lo asigna a txtHoraFin
    public String setValueTxtHoraFin(){
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        String[] arrayHora = vista.txtHoraInicio.getValue().toString().split(" ");
        String hora = arrayHora[3];
        try
        {
            Date dt = df.parse(hora);
            int selectedIndex = vista.txtComboPeli.getSelectedIndex();
            int duracion = Integer.parseInt(pelis[selectedIndex][1]);
            dt.setTime(dt.getTime()+ (duracion * 60000)); //60000ms = 1 minuto
            vista.txtHorafin.setValue(dt);
            String[] arrayHoraFinal = String.valueOf(dt).split(" ");
            return arrayHoraFinal[3];
        } catch (ParseException ex)
        {
            Logger.getLogger(ConAdmFormFunciones.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(vista.txtComboPeli == e.getSource()){
            setValueTxtHoraFin();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(vista.panelAdd == e.getSource()){
            //realiza asignaciones a las variables
            idFuncion = "".equals(vista.txtId.getText())?0:Integer.parseInt(vista.txtId.getText());
            fechaIni = String.valueOf(vista.txtFechaIni.getDate());
            fechaFin = String.valueOf(vista.txtFechaFin.getDate());
            String[] arrayHora = vista.txtHoraInicio.getValue().toString().split(" ");
            horaIni = arrayHora[3];
            horaFin = setValueTxtHoraFin();
            fechaIni = new SimpleDateFormat("yyyy-MM-dd").format(vista.txtFechaIni.getDate());
            fechaFin = new SimpleDateFormat("yyyy-MM-dd").format(vista.txtFechaFin.getDate());
            idSala = Integer.parseInt(String.valueOf(vista.txtComboSala.getSelectedItem()));
            idPeli = Integer.parseInt(pelis[vista.txtComboPeli.getSelectedIndex()][2]);
            estatus = vista.txtEstatus.getSelectedIndex();
            
            if(opcion == 1){ //se inserta
                if(modelo.funcionesInsertar(fechaIni, fechaFin, horaIni, horaFin, estatus, idPeli, idSala)){
                    ConSucces conSuccess = new ConSucces(genSuccess, "¡Éxito!", "Se ha insertado de manera correcta");
                    conSuccess.iniciarVista();
                }
                else{ //fallo en la inserción
                    ConAlert conAlert = new ConAlert(genAlert, "No se ha podido insertar el registro");
                    conAlert.iniciarVista();
                }
            }
            else if(opcion == 2){   //modificacion
                if(modelo.funcionesActualizar(idFuncion, fechaIni, fechaFin, horaIni, horaFin, estatus, idPeli, idSala)){
                    ConSucces conSuccess = new ConSucces(genSuccess, "¡Éxito!", "Se ha modiificado de manera correcta");
                    conSuccess.iniciarVista();
                }
                else{   //fallo en modificacion
                    ConAlert conAlert = new ConAlert(genAlert, "No se ha podido modificar el registro");
                    conAlert.iniciarVista();
                }
            }
        }
        else if(vista.panelBack == e.getSource()){
            vista.dispose();
        }
        else if(genSuccess.panelAceptar == e.getSource()){
            vista.dispose();
            genSuccess.dispose();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
         
    }

    @Override
    public void mouseReleased(MouseEvent e) {
         
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(vista.panelAdd == e.getSource()){
            setColorAceptar(vista.panelAdd);
        }
        else if(vista.panelBack == e.getSource()){
            setColorCancelar(vista.panelBack);
        }
        else if(genSuccess.panelAceptar == e.getSource()){
            setColorAceptar(genSuccess.panelAceptar);
        }
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
         if(vista.panelAdd == e.getSource()){
            resetColor(vista.panelAdd);
        }
        else if(vista.panelBack == e.getSource()){
            resetColor(vista.panelBack);
        }
        else if(genSuccess.panelAceptar == e.getSource()){
            resetColor(genSuccess.panelAceptar);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        if(vista.txtHoraInicio == e.getSource()){
            setValueTxtHoraFin();
        }
    }

    public String[][] getPelis()
    {
        return pelis;
    }

    public void setPelis(String[][] pelis)
    {
        this.pelis = pelis;
    }  
}
