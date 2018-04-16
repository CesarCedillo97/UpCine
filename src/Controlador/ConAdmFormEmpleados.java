/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import modelo.ModAdmFormEmpleados;
import vista.AdmFormEmpleados;
import Vista.GenSucces;
import vista.GenAlert;
/**
 *
 * @author adria
 */
public class ConAdmFormEmpleados extends ControladorPrincipal implements MouseListener, KeyListener
{
    ModAdmFormEmpleados modFormEmp;
    AdmFormEmpleados visFormEmpleados;
    private String id,nombre,direccion,edad,telefono,tipo,usuario, contraseña;
    private Date fecha_ini;
    GenSucces genSuccess = new GenSucces();
    GenAlert genAlert = new GenAlert();
    ConAlert alerta = new ConAlert();
    int opcion = 0;
    
    public ConAdmFormEmpleados(ModAdmFormEmpleados modFormEmp, AdmFormEmpleados visFormEmpleados, int opcion)
    {
        this.modFormEmp = modFormEmp;
        this.visFormEmpleados = visFormEmpleados;
        this.opcion = opcion;
    }    

    @Override
    public void iniciarVista()  //se inicia la vista y se agregan los listeners a los elementos
    {   
        visFormEmpleados.setTitle("UpCine");
        visFormEmpleados.pack();
        visFormEmpleados.setLocationRelativeTo(null);
        visFormEmpleados.setVisible(true);
        genSuccess.panelAceptar.addMouseListener((MouseListener) this);
        genAlert.panelAceptar.addMouseListener((MouseListener) this);
        visFormEmpleados.panelAdd.addMouseListener( (MouseListener) this);
        visFormEmpleados.panelBack.addMouseListener( (MouseListener) this);
        visFormEmpleados.txtEdad.addKeyListener(this);
        visFormEmpleados.txtNombre.addKeyListener(this);
        visFormEmpleados.title.setText((opcion==1?"Agregar ":"Modificar")+" Empleados");
        alerta.vistaAlert = genAlert;
    }
   
    
    
    @Override
    public void mouseClicked(MouseEvent e)
    {
        if(visFormEmpleados.panelAdd == e.getSource()){ //se asignan todos los datos de los textField a las variables
            this.nombre = visFormEmpleados.txtNombre.getText();
            this.telefono = visFormEmpleados.txtTelefono.getText();
            this.direccion = visFormEmpleados.txtDireccion.getText();
            this.tipo = String.valueOf(visFormEmpleados.txtTipo.getSelectedIndex());
            this.usuario = visFormEmpleados.txtUsuario.getText();
            this.contraseña  = visFormEmpleados.txtContra.getText();
            this.edad = visFormEmpleados.txtEdad.getText();
            this.id = "".equals(visFormEmpleados.txtId.getText())? "-1":visFormEmpleados.txtId.getText();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String fechaBD = df.format(visFormEmpleados.txtFecha_Inicio.getDate()); //le da formato correcto a la fecha 
            this.fecha_ini = visFormEmpleados.txtFecha_Inicio.getDate();
            if( !"".equals(nombre) &&   //se verifica que no esten vacios
                !"".equals(telefono) &&
                !"".equals(direccion) &&    
                !"".equals(fechaBD) &&
                !"0".equals(tipo) &&
                !"".equals(edad) && 
                !"".equals(usuario) &&
                !"".equals(contraseña)){
                int userExist = modFormEmp.verificarUsuario(usuario, Integer.parseInt(id));//verifica que el usuario no este en uso
                boolean boolTelefono = validacionNum(visFormEmpleados.txtTelefono.getText(),0);//valida los numeros
                boolean boolEdad = validacionNum(visFormEmpleados.txtEdad.getText(),1); //Valida los numeros
                if(boolTelefono && boolEdad){  
                    if(userExist == 0){ //Si no esta en uso prosigue
                        //si opcion = 1 lo inserta
                        if(opcion == 1 && modFormEmp.insertarEmpleado(nombre, telefono, direccion, edad, fechaBD, tipo, usuario, contraseña)){
                            ConSucces success = new ConSucces(genSuccess, "¡Enhorabuena!","Se ha insertado el registro de manera exitosa");
                            success.iniciarVista();
                            genSuccess.addKeyListener(this);

                        }   
                        //si opcion = 2 lo modifica
                        else if(opcion == 2 && modFormEmp.modificarEmpleado(id, nombre, telefono, direccion, edad, fechaBD, tipo, usuario, nombre)){
                            ConSucces success = new ConSucces(genSuccess, "¡Enhorabuena!","Se ha modificado el registro de manera exitosa");
                            success.iniciarVista();
                            genSuccess.addKeyListener(this);
                        }
                        //Ocurrió algun error
                        else if(opcion == 2 || opcion == 1){
                            alerta.Mensaje = "La acción no se concretó de manera correcta";
                            genAlert.addKeyListener(this);
                            alerta.iniciarVista();
                        }
                    }
                    //Si el usuario ya existe
                    else if(userExist==1){
                            alerta.Mensaje = "El usuario ya esta en uso";
                            alerta.iniciarVista();
                    }
                }
                else{//si se ingresaron caracteres no numericos en edad y telefono
                    String msg2 = "";
                    if(!boolTelefono && !boolEdad) msg2 = "Edad y Telefono";
                    else if(!boolTelefono) msg2 = "Telefono";
                    else if(!boolEdad) msg2 = "Edad";
                    alerta.Mensaje = "Se ingresaron valores no numericos en: ";
                    alerta.Mensaje2 = msg2;
                    alerta.iniciarVista();
                }
                
            }
           
            else{
                ConAlert alert = new ConAlert(genAlert,"Rellene todos los campos para proseguir");
                genAlert.addKeyListener(this);
                alert.iniciarVista();
            }
            
        }
        
        else if(visFormEmpleados.panelBack== e.getSource()){
            visFormEmpleados.dispose();
        }
        else if(genSuccess.panelAceptar == e.getSource()){
            visFormEmpleados.dispose();
            genSuccess.dispose();
        }

    }
   
    @Override
    public void mousePressed(MouseEvent e)
    {
        if(visFormEmpleados.txtEdad == e.getSource()){
            visFormEmpleados.txtEdad.setEnabled(true);
            visFormEmpleados.txtEdad.setEditable(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        if(visFormEmpleados.panelAdd == e.getSource())
            setColorAceptar(visFormEmpleados.panelAdd);
        else if(visFormEmpleados.panelBack == e.getSource()){
            setColorCancelar(visFormEmpleados.panelBack);
        }
        else if(genSuccess.panelAceptar == e.getSource()){
            setColorAceptar(genSuccess.panelAceptar);
        }
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        if(visFormEmpleados.panelAdd == e.getSource())
            resetColor(visFormEmpleados.panelAdd);
        else if(visFormEmpleados.panelBack == e.getSource()){
            resetColor(visFormEmpleados.panelBack);
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
            visFormEmpleados.dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        
    }
}