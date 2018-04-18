/*
    Controlador que funciona de auxiliar para todos los modulos, aquí se muestran los datos 
    de todos los modulos.
 */
package controlador;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import modelo.ModAdmABC;
import vista.AdmABC;
import vista.AdmFormEmpleados;
import modelo.ModAdmFormEmpleados;
import vista.AdmMenu;
import Modelo.ModAdmMenu;
import vista.AdmFormPeliculas;
import modelo.ModAdmFormPeliculas;
import vista.AdmFormSalas;
import modelo.ModAdmFormSalas;
import vista.GenConfirm;
import modelo.ModAdmFormFunciones;
import vista.AdmFormFunciones;
import modelo.ModAdmFormProveedores;
import vista.AdmFormProveedores;
import modelo.ModAdmFormProductos;
import vista.AdmFormProductos;
import modelo.ModAdmFormCompras;
import vista.AdmFormCompras;
import Vista.AdmOpcProductos;
import Controlador.ConAdmOpcProductos;
import Vista.AdmFormCombos;
import Modelo.ModAdmFormCombos;
import Controlador.ConAdmFormCombos;




/**
 *
 * @author adria
 */
public class ConAdmABC extends ControladorPrincipal implements MouseListener, WindowListener
{
    ModAdmABC modAdmABC;
    AdmABC visAdmABC;
    int opcion; 
    int idEmp;
    int fila = -1;
    GenConfirm genConfirm = new GenConfirm();
    ConConfirm conConfirm = new ConConfirm(genConfirm, "¡Advertencia!", "¿Seguro que desea eliminar el registro?");
    
    public ConAdmABC(ModAdmABC modAdmABC, AdmABC visAdmABC, int idEmp, int opcion)
    {
        this.modAdmABC = modAdmABC;
        this.visAdmABC = visAdmABC;
        this.opcion = opcion;
        this.idEmp = idEmp;
    }
    
    @Override
    public void iniciarVista()
    {
        //DEPENDIENDO DE EL PANEL QUE SE HAYA SELECCIONADO
        //CARGA UN MODELO DE TABLA DIFERENTE Y SE LE AGREGAR UN TITULO DIFERENTE
        switch(opcion){
            case 1:
                visAdmABC.lblMenu.setText("Empleados");
                visAdmABC.tabla.setModel(modAdmABC.consulta(1));
                break;
            case 2:
                visAdmABC.lblMenu.setText("Funciones");
                visAdmABC.tabla.setModel(modAdmABC.consulta(2));
                visAdmABC.tabla.removeColumn(visAdmABC.tabla.getColumnModel().getColumn(2));
                break;
            case 3:
                visAdmABC.lblMenu.setText("Productos");
                visAdmABC.tabla.setModel(modAdmABC.consulta(3));
                break;
            case 4: 
                visAdmABC.lblMenu.setText("Productos");
                visAdmABC.tabla.setModel(modAdmABC.consulta(4));
                break;
            case 5:
                visAdmABC.lblMenu.setText("Precios");
                visAdmABC.tabla.setModel(modAdmABC.consulta(5));
                break;
            case 6: 
                visAdmABC.lblMenu.setText("Sala y asientos");
                visAdmABC.tabla.setModel(modAdmABC.consulta(6));
                break;
            case 7:
                visAdmABC.lblMenu.setText("Proveedores");
                visAdmABC.tabla.setModel(modAdmABC.consulta(7));
                break;
            case 8: 
                visAdmABC.lblMenu.setText("Compras");
                visAdmABC.tabla.setModel(modAdmABC.consulta(8));
                break;
            case 9:
                visAdmABC.lblMenu.setText("Combos");
                visAdmABC.tabla.setModel(modAdmABC.consulta(9));
                break;
            case 10:
                visAdmABC.lblMenu.setText("Ventas");
                visAdmABC.tabla.setModel(modAdmABC.consulta(10));
                break;
        }
       
        resizeColumnWidth(visAdmABC.tabla);
        visAdmABC.setTitle("UpCine");
        visAdmABC.pack();
        visAdmABC.setLocationRelativeTo(null);
        visAdmABC.setVisible(true);
        visAdmABC.addWindowListener(this);
        
        genConfirm.panelAceptar.addMouseListener((MouseListener)this);
        genConfirm.panelCancelar.addMouseListener((MouseListener)this);
        this.visAdmABC.panelBack.addMouseListener((MouseListener) this);
        this.visAdmABC.panelAdd.addMouseListener((MouseListener) this);
        this.visAdmABC.panelEli.addMouseListener((MouseListener) this);
        this.visAdmABC.panelModi.addMouseListener((MouseListener) this);
        this.visAdmABC.tabla.addMouseListener((MouseListener) this);   
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
        if (visAdmABC.panelBack == e.getSource()) {//ES PARA REGRESAR A LA VENTANA ANTERIOR
            if (this.opcion==4 || this.opcion == 9) {//ES PARA REGRESAR A LA VENTANA DE OPCION DE PRODUCTOS
            AdmOpcProductos vistaPro = new AdmOpcProductos();
            ConAdmOpcProductos controlPro = new ConAdmOpcProductos(vistaPro, idEmp);
            visAdmABC.dispose();
            controlPro.iniciarVista();
            visAdmABC.dispose();
            }else{ //ES PARA REGRESAR A EL MENU DEL ADMINISTRADOR
            AdmMenu menuVista = new AdmMenu();
            ModAdmMenu menuMod = new ModAdmMenu();
            ConAdmMenu menuControl = new ConAdmMenu(menuMod, menuVista, idEmp);
            visAdmABC.dispose();
            menuControl.iniciarVista();
            visAdmABC.dispose();
            }
            
        }
        else if(visAdmABC.tabla == e.getSource()){ //ESTO ES PARA CAMBIAR EL COLOR DE LOS ICONOS CUANDO SE HAYA SELECCIONADO UN REGISTRO DE LA TABLA
            this.fila = visAdmABC.tabla.rowAtPoint(e.getPoint());
            visAdmABC.lblModi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/icons8_Edit_File_48px.png")));
            visAdmABC.txtModi.setForeground(new Color(25,116,232));
            visAdmABC.lblElim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/icons8_Delete_Document_52px.png")));
            visAdmABC.txtElim.setForeground(new Color(25,116,232));
        }
        else if(visAdmABC.panelAdd == e.getSource()){ //PARA AGREGRA UN NUEVO REGISTRO SEGUN EL PANEL QUE SE HAYA SELECCIONADO
            switch(opcion){
                case 1: //empleados
                    ModAdmFormEmpleados modAdmFormEmp = new ModAdmFormEmpleados();
                    AdmFormEmpleados formEmp = new AdmFormEmpleados();
                    ConAdmFormEmpleados controlador = new ConAdmFormEmpleados(modAdmFormEmp,formEmp, 1);//1 para agregar
                    controlador.iniciarVista();
                    break;
                case 2: //funciones
                    ModAdmFormFunciones modAdmFormFunciones = new ModAdmFormFunciones();
                    AdmFormFunciones formFunciones = new AdmFormFunciones();
                    ConAdmFormFunciones conFormFunciones = new ConAdmFormFunciones(modAdmFormFunciones, formFunciones, 1);
                    conFormFunciones.iniciarVista();
                    break;
                case 3: //peliculas
                    ModAdmFormPeliculas modFormPel = new ModAdmFormPeliculas();
                    AdmFormPeliculas formPelis = new AdmFormPeliculas();
                    ConAdmFormPeliculas conFormPelis = new ConAdmFormPeliculas(formPelis, modFormPel,1);
                    conFormPelis.iniciarVista();
                    break;
                case 4: //productos
                    ModAdmFormProductos modFormProd = new ModAdmFormProductos();
                    AdmFormProductos formProd = new AdmFormProductos();
                    ConAdmFormProductos conFormProd = new ConAdmFormProductos(formProd, modFormProd, 1);
                    conFormProd.iniciarVista();
                    break;
                case 5: //precios
                    break;
                case 6: //salas y asientos
                    ModAdmFormSalas modFormSalas = new ModAdmFormSalas();
                    AdmFormSalas formSalas = new AdmFormSalas();
                    ConAdmFormSalas conFormSalas = new ConAdmFormSalas(formSalas, modFormSalas, 1);
                    conFormSalas.iniciarVista();
                    break;
                case 7:
                    ModAdmFormProveedores modFormProv = new ModAdmFormProveedores();
                    AdmFormProveedores formProv = new AdmFormProveedores();
                    ConAdmFormProveedores conFormProv = new ConAdmFormProveedores(modFormProv, formProv, 1);
                    conFormProv.iniciarVista();
                    break;
                case 8:
                    ModAdmFormCompras modFormCompras = new ModAdmFormCompras();
                    AdmFormCompras formCompras = new AdmFormCompras();
                    ConAdmFormCompras conFormCompras = new ConAdmFormCompras(modFormCompras, formCompras, 1, idEmp);
                    conFormCompras.iniciarVista();
                    break;      
                case 9:
                    ModAdmFormCombos modFormPro = new ModAdmFormCombos();
                    AdmFormCombos formCombos = new AdmFormCombos();
                    ConAdmFormCombos conFormcombos = new ConAdmFormCombos(formCombos, modFormPro);
                    conFormcombos.iniciarVista();
                    break; 
                case 10:
                    
                    break; 
            }
        }
        
        else if(visAdmABC.panelEli == e.getSource() && fila != -1){//PARA ABRIR UNA VENTANA Y PREGUNTAR SI ESTÁS SEGURO DE ELIMINAR
            conConfirm.iniciarVista();
        }
        
        else if(visAdmABC.panelModi == e.getSource() && fila != -1){ //PARA MODIFICAR UN NUEVO REGISTRO SEGUN EL PANEL QUE SE HAYA SELECCIONADO
            switch(opcion){
                case 1: //empleado                
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    try
                    {
                         date = df.parse(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 7)));
                    } catch (ParseException ex){
                        Logger.getLogger(ConAdmABC.class.getName()).log(Level.SEVERE, null, ex);
                    }   
                    ModAdmFormEmpleados modelo = new ModAdmFormEmpleados();
                    AdmFormEmpleados vista = new AdmFormEmpleados();
                    ConAdmFormEmpleados controlador = new ConAdmFormEmpleados(modelo, vista, 2);//2 para modificar
                    vista.txtId.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 0)));
                    vista.txtUsuario.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 1)));
                    vista.txtContra.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 2)));
                    vista.txtNombre.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 3)));
                    vista.txtTelefono.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 4)));
                    vista.txtDireccion.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 5)));
                    vista.txtEdad.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 6)));
                    vista.txtFecha_Inicio.setDate(date);
                    vista.txtTipo.setSelectedIndex("Administrador".equals(visAdmABC.tabla.getValueAt(this.fila, 8))?1:2);
                    controlador.iniciarVista();
                    break;
                case 2: //funciones
                    ModAdmFormFunciones modFormFunc = new ModAdmFormFunciones();
                    AdmFormFunciones formFunc = new AdmFormFunciones();
                    ConAdmFormFunciones conFormFunc = new ConAdmFormFunciones(modFormFunc, formFunc, 2);
                    
                    formFunc.txtId.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 0)));
                    conFormFunc.cargarCombos();
                    SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat dfTime = new SimpleDateFormat("HH:mm:ss");
                    Date fechaI = null;
                    Date fechaF = null;
                    Date horaI = null;
                    try
                    {
                         fechaI = df2.parse(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 3)));
                         fechaF = df2.parse(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 4)));
                         horaI = dfTime.parse(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 5)));
                    } catch(ParseException ex){
                        Logger.getLogger(ConAdmABC.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    int index = -1;
                    for (int i = 0; i < conFormFunc.getPelis().length; i++)
                    {
                        if(Integer.parseInt(conFormFunc.getPelis()[i][2]) == Integer.parseInt(String.valueOf(visAdmABC.tabla.getModel().getValueAt(this.fila, 2)))){
                            index = i;
                            break;
                        }
                    }
                    formFunc.txtComboPeli.setSelectedIndex(Integer.parseInt(conFormFunc.getPelis()[index][2])-1);
                    formFunc.txtComboSala.setSelectedItem(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 1)));
                    formFunc.txtFechaIni.setDate(fechaI);
                    formFunc.txtFechaFin.setDate(fechaF);
                    formFunc.txtHoraInicio.setValue(horaI);
                    conFormFunc.setValueTxtHoraFin();
                    formFunc.txtEstatus.setSelectedIndex("Disponible".equals(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 7)))?1:0);
                    conFormFunc.iniciarVista();
                    break;
                case 3: //peliculas
                    ModAdmFormPeliculas modFormPel = new ModAdmFormPeliculas();
                    AdmFormPeliculas formPelis = new AdmFormPeliculas();
                    ConAdmFormPeliculas conFormPelis = new ConAdmFormPeliculas(formPelis, modFormPel,2);
                    
                    formPelis.txtId.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 0)));
                    formPelis.txtNombre.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 1)));
                    formPelis.txtDirector.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 2)));
                    formPelis.txtDuracion.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 3)));
                    formPelis.txtClasificacion.setSelectedItem(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 4)));
                    formPelis.txtGeneros.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 5)));
                    formPelis.txtActores.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 6)));
                    formPelis.txtIdioma.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 7)));
                    formPelis.txtSubtitulos.setText("".equals(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 8)))?"":String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 8)));
                    formPelis.txtImagen.setText("null".equals(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 10)))?"":String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 10)));
                    formPelis.txtFormato.setSelectedItem(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 9)));
                    conFormPelis.iniciarVista();
                    break;
                case 4: //productos
                    ModAdmFormProductos modFormProd = new ModAdmFormProductos();
                    AdmFormProductos formProd = new AdmFormProductos();
                    ConAdmFormProductos conFormProd = new ConAdmFormProductos(formProd, modFormProd, 2);
                    formProd.txtId.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 0)));
                    formProd.txtDescripcion.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 1)));
                    formProd.txtCantidad.setValue(visAdmABC.tabla.getValueAt(this.fila, 2));
                    formProd.txtCosto.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 3)));
                    formProd.txtPrecioVenta.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 4)));
                    String[][] arrayprovs = modFormProd.obtenerProveedores();
                    DefaultComboBoxModel model = new DefaultComboBoxModel();
                    for (String[] arrayprov : arrayprovs){
                        model.addElement(arrayprov[1]);
                    }
                    formProd.txtProv.setModel(model);
                    formProd.txtProv.setSelectedItem(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 5))); 
                    conFormProd.iniciarVista();
                    break;
                case 5: //precios
                    break;
                case 6: //salas y asientos
                    ModAdmFormSalas modFormSalas = new ModAdmFormSalas();
                    AdmFormSalas formSalas = new AdmFormSalas();
                    ConAdmFormSalas conFormSalas = new ConAdmFormSalas(formSalas, modFormSalas, 2);
                    formSalas.txtId.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 0)));
                    formSalas.txtFilas.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 1)));
                    formSalas.txtCols.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 2)));
                    formSalas.txtEstatus.setSelectedItem(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 4)));
                    formSalas.txtTipo.setSelectedItem(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 3)));
                    conFormSalas.iniciarVista();
                    break;
                case 7:
                    ModAdmFormProveedores modFormProv = new ModAdmFormProveedores();
                    AdmFormProveedores formProv = new AdmFormProveedores();
                    ConAdmFormProveedores conFormProv = new ConAdmFormProveedores(modFormProv, formProv, 2);
                    formProv.txtId.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 0)));
                    formProv.txtEmpresa.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 1)));
                    formProv.txtResponsable.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 2)));
                    formProv.txtDireccion.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 3)));
                    formProv.txtTelefono.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 4)));
                    conFormProv.iniciarVista();
                    break;
                case 8:
                    ModAdmFormCompras modFormCompras = new ModAdmFormCompras();
                    AdmFormCompras formCompras = new AdmFormCompras();
                    ConAdmFormCompras conFormCompras = new ConAdmFormCompras(modFormCompras, formCompras, 2, idEmp);
                    formCompras.txtSubtotal.setText(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 1)));
                    formCompras.txtEstado.setSelectedItem(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 5)));
                    formCompras.listProductos.setModel(modFormCompras.obtenerProductos(Integer.parseInt(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 0)))));
                    conFormCompras.setIdCompra(Integer.parseInt(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 0))));
                    conFormCompras.iniciarVista();
                    conFormCompras.changeValues();
                    break;
                    
            }
        }
        else if(genConfirm.panelAceptar == e.getSource()){ //PARA ELIMINAR UN NUEVO REGISTRO SEGUN EL PANEL QUE SE HAYA SELECCIONADO
            int id;
            switch(opcion){
                case 1: //empleados
                    ModAdmFormEmpleados modFormEmp= new ModAdmFormEmpleados();
                    if(modFormEmp.deleteEmpleado(Integer.parseInt(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 0))))){
                        reset();
                    }
                    else{
                        System.out.println("No sé eliminó el registro");
                    }
                    genConfirm.dispose();
                    break;
                case 2: //funciones
                    ModAdmFormFunciones modFormFuncion = new ModAdmFormFunciones();
                    if(modFormFuncion.deleteFuncion(Integer.parseInt(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 0))))){
                        reset();
                    }
                    else{
                        System.out.println("No sé eliminó el registro");
                    }
                    break;
                case 3: //peliculas
                    ModAdmFormPeliculas modFormPel = new ModAdmFormPeliculas();
                    if(modFormPel.deletePelicula(Integer.parseInt(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 0))))){
                        reset();
                    }
                    else{
                        System.out.println("No sé eliminó el registro");
                    }
                    break;
                case 4: //productos
                    ModAdmFormProductos modFormProductos = new ModAdmFormProductos();
                    if(modFormProductos.eliminarProductos(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 0)))){
                        reset();
                    }
                    break;
                case 5: //precios
                    break;
                case 6: //salas y asientos
                    id = Integer.parseInt(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 0)));
                    ModAdmFormSalas modFormSalas = new ModAdmFormSalas();
                    if(modFormSalas.eliminarAsientos(id) && modFormSalas.eliminarSala(id)){
                        reset();
                    }
                    break;
                case 7:
                    ModAdmFormProveedores modFormProv = new ModAdmFormProveedores();
                    if(modFormProv.eliminarProveedores(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 0)))){
                        reset();
                    }
                    break;
                case 8:
                    ModAdmFormCompras modFormCompras = new ModAdmFormCompras();
                    if(modFormCompras.eliminarCompra(Integer.parseInt(String.valueOf(visAdmABC.tabla.getValueAt(this.fila, 0))))){
                        reset();
                    }
            }
            genConfirm.dispose();

        }
        else if(genConfirm.panelCancelar == e.getSource()){
            genConfirm.dispose();
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
        
    }
    
    public void reset(){
        System.out.println("Eliminado correctamente");
        visAdmABC.tabla.setModel(modAdmABC.consulta(opcion));
        fila = -1;
        visAdmABC.lblModi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/EditDisabled.png")));
        visAdmABC.txtModi.setForeground(new Color(128,128,128));
        visAdmABC.lblElim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/DeleteDisabled.png")));
        visAdmABC.txtElim.setForeground(new Color(128,128,128));
        resetColor(visAdmABC.panelEli);
        resizeColumnWidth(visAdmABC.tabla);
    }
    
    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 30; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            if(width > 300)
                width=300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
        if(opcion == 3)
            visAdmABC.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        else if(opcion == 2)
            visAdmABC.tabla.removeColumn(visAdmABC.tabla.getColumnModel().getColumn(2));
    }
    
    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (visAdmABC.panelBack == e.getSource()) {
            setColor(visAdmABC.panelBack);
        }
        else if (visAdmABC.panelAdd==e.getSource()) {
            setColor(visAdmABC.panelAdd);
        }
        else if (visAdmABC.panelEli==e.getSource() && this.fila != -1) {
            setColor(visAdmABC.panelEli);
        }
        else if (visAdmABC.panelModi==e.getSource() && this.fila != -1) {
            visAdmABC.setCursor(Cursor.HAND_CURSOR);
            setColor(visAdmABC.panelModi);
        }
        else if (visAdmABC.panelModi==e.getSource() && this.fila == -1){
            visAdmABC.setCursor(Cursor.DEFAULT_CURSOR);
        }
        else if(genConfirm.panelAceptar == e.getSource()){
            setColorAceptar(genConfirm.panelAceptar);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        visAdmABC.setCursor(Cursor.DEFAULT_CURSOR);
        if (visAdmABC.panelBack == e.getSource()) {
            resetColorSalir(visAdmABC.panelBack);
        }
        else if (visAdmABC.panelAdd==e.getSource()) {
            resetColor(visAdmABC.panelAdd);
        }
        else if (visAdmABC.panelEli==e.getSource() && this.fila != -1) {
            resetColor(visAdmABC.panelEli);
        }
        else if (visAdmABC.panelModi==e.getSource() && this.fila != -1) {
            resetColor(visAdmABC.panelModi);
        }
        else if(genConfirm.panelAceptar == e.getSource()){
            resetColor(genConfirm.panelAceptar);
        }
    }

    @Override
    public void windowOpened(WindowEvent e)
    {
        
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
        
    }

    @Override
    public void windowClosed(WindowEvent e)
    {
        
    }

    @Override
    public void windowIconified(WindowEvent e)
    {
        
    }

    @Override
    public void windowDeiconified(WindowEvent e)
    {
        
    }

    @Override
    public void windowActivated(WindowEvent e)
    {
        if(visAdmABC == e.getSource()){
            visAdmABC.tabla.setModel(modAdmABC.consulta(opcion));
            fila = -1;
            resizeColumnWidth(visAdmABC.tabla);
            visAdmABC.lblModi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/EditDisabled.png")));
            visAdmABC.txtModi.setForeground(new Color(128,128,128));
            visAdmABC.lblElim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/DeleteDisabled.png")));
            visAdmABC.txtElim.setForeground(new Color(128,128,128));
        }
    }

    @Override
    public void windowDeactivated(WindowEvent e)
    {
        
    }
    
}
