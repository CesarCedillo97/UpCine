/*
    Controlador para agregar y modificar peliculas
 */
package controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import vista.AdmFormPeliculas;
import modelo.ModAdmFormPeliculas;
import Vista.GenSucces;
import vista.GenAlert;
/**
 *
 * @author adria
 */
public class ConAdmFormPeliculas extends ControladorPrincipal implements MouseListener, KeyListener, ChangeListener, ActionListener, ListSelectionListener 
{
    AdmFormPeliculas formPeli;
    ModAdmFormPeliculas modFormPelis;
    GenSucces genSuccess = new GenSucces();
    GenAlert genAlert = new GenAlert();
    int opcion;
    
    private String nombre, director, actores, idioma, subtitulos, imagen, generos, duracion, id;
    private String formato, clasificacion;
    
    public ConAdmFormPeliculas(AdmFormPeliculas formPeli, ModAdmFormPeliculas modFormPelis, int opcion)
    {
        this.formPeli = formPeli;
        this.modFormPelis = modFormPelis;
        this.opcion = opcion;
        
    }
    
    @Override
    public void iniciarVista()
    {
        formPeli.setTitle("UpCine");
        formPeli.pack();
        formPeli.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        formPeli.setLocationRelativeTo(null);
        formPeli.setVisible(true);
        formPeli.title.setText((this.opcion == 1?"Agregar":"Modificar")+" peliculas");
        formPeli.panelAdd.addMouseListener((MouseListener) this);
        formPeli.panelBack.addMouseListener((MouseListener)this);
        formPeli.btnBrowse.addActionListener(this);
        formPeli.listGeneros.addListSelectionListener(this);
        genSuccess.panelAceptar.addMouseListener((MouseListener) this);
        genAlert.panelAceptar.addMouseListener((MouseListener) this);        
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if(formPeli.panelAdd == e.getSource()){
            //se realizan asignaciones
            this.nombre = formPeli.txtNombre.getText();
            this.director = formPeli.txtDirector.getText();
            this.duracion = formPeli.txtDuracion.getText();
            this.generos = ("Seleccione los generos:".equals(formPeli.txtGeneros.getText())?" ":formPeli.txtGeneros.getText());//la condición es solo para compararlo más fácil
            this.idioma = formPeli.txtIdioma.getText();
            this.subtitulos = formPeli.txtSubtitulos.getText();
            this.imagen = formPeli.txtImagen.getText();
            this.formato = String.valueOf(formPeli.txtFormato.getSelectedItem());
            this.clasificacion = String.valueOf(formPeli.txtClasificacion.getSelectedItem());
            this.actores = formPeli.txtActores.getText();
            this.id = formPeli.txtId.getText();
            if(!"".equals(nombre) &&
               !"".equals(director)&&
               !"".equals(generos) &&
               !"".equals(idioma) &&
               !"".equals(imagen)&&
               !"".equals(actores)&&
               !"".equals(duracion) &&  //se verifica que no haya nada vacio para insertar
               formPeli.txtFormato.getSelectedIndex() != 0 &&
               formPeli.txtClasificacion.getSelectedIndex() != 0){
                if(validacionNum(formPeli.txtDuracion.getText(),0)){//Inserción
                    if(opcion == 1 && modFormPelis.insertarPelicula(nombre, director, Integer.parseInt(duracion), clasificacion, generos, actores, idioma, subtitulos, formato, imagen)){
                    ConSucces success = new ConSucces(genSuccess, "¡Enhorabuena!","Se ha agregado el registro de manera exitosa");
                    success.iniciarVista();
                    genSuccess.addKeyListener(this);
                    }
                    //modificacion
                    else if(opcion == 2 && modFormPelis.modificarPeli(id, nombre, director, Integer.parseInt(duracion), clasificacion, generos, actores, idioma, subtitulos, formato, imagen)){
                        ConSucces success = new ConSucces(genSuccess, "¡Enhorabuena!","Se ha modificado el registro de manera exitosa");
                        success.iniciarVista();
                        genSuccess.addKeyListener(this);
                    }
                    else if(opcion == 2 || opcion == 1){
                        ConAlert alert = new ConAlert(genAlert,"La acción no se concretó de manera correcta");
                        genAlert.addKeyListener(this);
                        alert.iniciarVista();
                    }
                }
                else{
                    ConAlert conAlert = new ConAlert(genAlert, "Se ingresaron caracteres no numéricos en: ", "Duración");
                    conAlert.iniciarVista();
                }
                
            }
            else{
                ConAlert alert = new ConAlert(genAlert,"Rellene todos los campos para proseguir");
                genAlert.addKeyListener(this);
                alert.iniciarVista();
            }
        }
        else if(formPeli.panelBack== e.getSource()){
            formPeli.dispose();
        }
        else if(genSuccess.panelAceptar == e.getSource()){
            genSuccess.dispose();
            formPeli.dispose();
        }
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
        if(formPeli.panelAdd == e.getSource())
            setColorAceptar(formPeli.panelAdd);
        else if(formPeli.panelBack == e.getSource()){
            setColorCancelar(formPeli.panelBack);
        }
        else if(genSuccess.panelAceptar == e.getSource()){
            setColorAceptar(genSuccess.panelAceptar);
        } 
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        if(formPeli.panelAdd == e.getSource())
            resetColor(formPeli.panelAdd);
        else if(formPeli.panelBack == e.getSource()){
            resetColor(formPeli.panelBack);
        }
        else if(genSuccess.panelAceptar == e.getSource()){
            resetColor(genSuccess.panelAceptar);
        } 
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(KeyEvent.VK_ENTER == e.getKeyCode() && genSuccess == e.getSource()){
            formPeli.dispose();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(formPeli.btnBrowse == e.getSource()){
            //se abre un fileChooser
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png");
            chooser.setFileFilter(filter);
            chooser.setCurrentDirectory(new File(".//src//movieImages"));
            int returnVal = chooser.showOpenDialog(formPeli);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                    formPeli.txtImagen.setText((chooser.getSelectedFile()).getName());
            }
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e)
    {
        
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        
    }

    @Override
    public void valueChanged(ListSelectionEvent e){
        if(formPeli.listGeneros == e.getSource()){
            if(formPeli.listGeneros.getSelectedIndices().length <= 3 && formPeli.listGeneros.getSelectedIndices().length > 0){    
                formPeli.txtGeneros.setText(String.valueOf(formPeli.listGeneros.getSelectedValuesList()).replaceAll("\\[", "").replaceAll("\\]",""));
            }
            else if(formPeli.listGeneros.getSelectedIndices().length == 0){
                formPeli.txtGeneros.setText("Seleccione los generos:");
            }
            else{
                formPeli.listGeneros.removeSelectionInterval(3, 3);
            }
        }
    }

   

}
