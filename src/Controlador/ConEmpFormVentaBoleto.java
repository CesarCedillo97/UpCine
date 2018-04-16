/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.EmpFormVentaBoleto;
import controlador.ControladorPrincipal;
import javax.swing.SpinnerNumberModel;
import Modelo.ModEmpFormVentaBoleto;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Cesar Cedillo
 */
public class ConEmpFormVentaBoleto extends ControladorPrincipal  {
    EmpFormVentaBoleto vista;
    ModEmpFormVentaBoleto modelo;

    public ConEmpFormVentaBoleto(EmpFormVentaBoleto vista, ModEmpFormVentaBoleto modelo) {
        this.vista = vista;
        this.modelo = modelo;
    }

    @Override
    public void iniciarVista() {
        
        vista.setTitle("Agregar Boleto");
        vista.pack();
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        
        //Para el combo de peliculas
        String[] productos = modelo.obtenerPeliculas();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (String pro : productos){
            model.addElement(pro);
        }
        vista.comboPeli.setModel(model);
        
        //para el combo de horarios
        DefaultComboBoxModel modeloHorarios = new DefaultComboBoxModel();
        vista.comboHorario.setModel(modeloHorarios);
        
        //Para el spinner
        SpinnerNumberModel modeloSpinner = new SpinnerNumberModel( 
        new Integer(0), // Dato visualizado al inicio en el spinner 
        new Integer(0), // Límite inferior 
        new Integer(10), // Límite superior 
        new Integer(1) // incremento-decremento 
        ); 
        vista.SpinnerCant.setModel(modeloSpinner);
    }


    
    
}
