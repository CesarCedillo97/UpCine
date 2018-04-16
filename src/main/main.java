
package main;
import modelo.ModGenInicioSesion;
import vista.GenInicioSesion;
import controlador.ConGenInicioSesion;
/**
 *
 * @author adria
 */
public class main
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        ModGenInicioSesion modelo = new ModGenInicioSesion();
        GenInicioSesion vista = new GenInicioSesion();
        ConGenInicioSesion control = new ConGenInicioSesion(modelo, vista);
        control.iniciarVista();
    }
    
}
