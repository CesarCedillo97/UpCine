    /*
    Controlador para agregar y modificar proveedores
 */
package controlador;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import modelo.ModAdmFormProveedores;
import vista.AdmFormProveedores;
import vista.GenAlert;
import Vista.GenSucces;
/**
 *
 * @author adria
 */
public class ConAdmFormProveedores extends ControladorPrincipal implements MouseListener
{
    ModAdmFormProveedores modFormProveedor;
    AdmFormProveedores formProveedor;
    GenAlert genAlert = new GenAlert();
    GenSucces genSuccess = new GenSucces();
    int opcion;
    private String telefono,responsable, direccion, empresa,id;
    
    public ConAdmFormProveedores(ModAdmFormProveedores modFormProveedor, AdmFormProveedores formProveedor, int opcion)
    {
        this.modFormProveedor = modFormProveedor;
        this.formProveedor = formProveedor;
        this.opcion = opcion;
    }
        
    @Override
    public void iniciarVista()
    {
        formProveedor.setTitle("UpCine");
        formProveedor.title.setText((opcion == 1?"Agregar ": "Modificar ")+"proveedor");
        formProveedor.pack();
        formProveedor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        formProveedor.setLocationRelativeTo(null);
        formProveedor.setVisible(true);
        formProveedor.panelAdd.addMouseListener((MouseListener) this);
        formProveedor.panelBack.addMouseListener((MouseListener) this);
        genSuccess.panelAceptar.addMouseListener((MouseListener) this);
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        //asignacion de datos 
        if(formProveedor.panelAdd == e.getSource()){
            id = formProveedor.txtId.getText();
            empresa = formProveedor.txtEmpresa.getText();
            responsable = formProveedor.txtResponsable.getText();
            direccion = formProveedor.txtDireccion.getText();
            telefono = formProveedor.txtTelefono.getText();
            if(opcion == 1 && !"".equals(empresa) && !"".equals(responsable) && !"".equals(direccion) && !"".equals(telefono)){
                //si no hay vacios se manda llamar a la funcion de insertar en el modelo
                if(modFormProveedor.insertarProveedores(empresa, responsable, direccion, telefono)){
                    ConSucces success = new ConSucces(genSuccess, "¡Éxito!", "El registro se ha insertado exitosamente");
                    success.iniciarVista();
                }
                else{//Si falla al insertar
                    ConAlert conAlert = new ConAlert(genAlert,"No se ha podido agregar el registro");
                    conAlert.iniciarVista();
                }
            }
            else if(opcion == 2 &&!"".equals(empresa) && !"".equals(responsable) && !"".equals(direccion) && !"".equals(telefono) ){
                //se modifica
                if(modFormProveedor.modificarProveedores(id, empresa, responsable, direccion, telefono)){
                    ConSucces success = new ConSucces(genSuccess, "¡Éxito!", "El registro se ha modificado exitosamente");
                    success.iniciarVista();
                }
                else{
                    ConAlert conAlert = new ConAlert(genAlert,"No se ha podido modificar el registro");
                    conAlert.iniciarVista();
                }
            }
            else{
                ConAlert conAlert = new ConAlert(genAlert,"Rellene todos los campos para proseguir");
                conAlert.iniciarVista();
            }
        }
        else if(formProveedor.panelBack == e.getSource()){
            formProveedor.dispose();
        }
        else if(genSuccess.panelAceptar == e.getSource()){
            formProveedor.dispose();
            genSuccess.dispose();
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
        if(formProveedor.panelAdd == e.getSource()){
            setColorAceptar(formProveedor.panelAdd);
        }
        else if(formProveedor.panelBack == e.getSource()){
            setColorCancelar(formProveedor.panelBack);
        }
        else if(genSuccess.panelAceptar == e.getSource()){
            setColorAceptar(genSuccess.panelAceptar);
        }
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        if(formProveedor.panelAdd == e.getSource()){
            resetColor(formProveedor.panelAdd);
        }
        else if(formProveedor.panelBack == e.getSource()){
            resetColor(formProveedor.panelBack);
        }
        else if(genSuccess.panelAceptar == e.getSource()){
            resetColor(genSuccess.panelAceptar);
        }
    }
    
}
