/*
    Clase semiabastracta que se implementa en todas los controladores,
    la utilizamos para hacer que todos los controladores implementaran la clase iniciar vista
    ademas de ser de mucha utilidad las funciones pues es más facil acceder a ellas 
 */
package controlador;

import java.awt.Color;
import javax.swing.JPanel;
/**
 *
 * @author adria
 */
public abstract class ControladorPrincipal 
{
    public abstract void iniciarVista();//para inciar la vista
    
    
    //TODO ESTO ES PARA DARLE COLOR A LOS PANELES CUANDO PASAS SOBRE ELLOS:
    public void setColor(JPanel panel){
        panel.setBackground(new java.awt.Color(115, 163, 239));
    }
    
    public void resetColor(JPanel panel){
        panel.setBackground(new java.awt.Color(240,240,240));
    }
    
    public void resetColorSalir(JPanel panel){
        panel.setBackground(new java.awt.Color(25,116,232));
    }
    
    public void setColorDisabled(JPanel panel){
        panel.setBackground(new java.awt.Color(25,116,232));
    }
    
    public void setColorAceptar(JPanel panel){
        panel.setBackground(new Color(149,208,151));
    }
    
    public void setColorCancelar(JPanel panel){
        panel.setBackground(new Color(247,143,143));
    }
    public void setColorSuccess(JPanel panel){
        panel.setBackground(new Color(100,247,14));
    }
    //función para validar numeros
    //cuando recibe un 1 se puede ingresar un punto, 0 cuando no
    public boolean validacionNum(String txt, int opcion){
        if(txt.indexOf('.') < 0){
            try{
                Integer.parseInt(txt);
                return true;
            }
            catch(NumberFormatException e){
                return false;
            }
        }
        else if(opcion == 1){
            String[] txts = txt.split(".");
            try{
                System.out.println(txts[0]);
                System.out.println(txts[1]);
                Integer.parseInt(txts[0]);
                Integer.parseInt(txts[1]);
                return true;
            }
            catch(NumberFormatException e){
                return false;
            }
        }
        return false;
    }
}
