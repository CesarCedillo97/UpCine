
//Controlador de venta de boletos 
package controlador;
import vista.EmpVentaBoleto;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import vista.GenAlert;
import Vista.GenSucces;
import Vista.EmpConfirmVenta;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modelo.ModEmpVentaPeli;
import Vista.EmpSelectAsientos;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 *
 * @author Cesar Cedillo
 */
public class ConEmpVentaPeli extends ControladorPrincipal implements MouseListener, Runnable{
    EmpVentaBoleto vista;
    ModEmpVentaPeli modelo;
    EmpSelectAsientos visAsientos = new EmpSelectAsientos();
    EmpConfirmVenta confirmVenta = new EmpConfirmVenta();
    private int id;
    private String[][] listPelis;
    private String[][] listPelis2;
    private int sala, funcion;
    private float IVA;
    private String hora, minutos, segundos, ampm;
    private Thread h1;
    private int filas, columnas, numAsientos;
    private JLabel[][] arregloAsientos;
    int[][] asientos;
    private int numAsientoDis;
    private int countAsientos; 

    ButtonGroup group = new ButtonGroup();
    //constructor
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
        vista.panelBack1.addMouseListener(this);
        //función que calcular los valores de total, subtotal e iva
        ChangeListener listener = (ChangeEvent e) ->{
            reasignarCantidades();
        };
        this.IVA = modelo.obtenerIVA();
        vista.txtNormal.addChangeListener(listener);
        vista.txtMayor.addChangeListener(listener);
        vista.txtEstud.addChangeListener(listener);
        listPelis = modelo.consultarPeliculas();        
        cargarPeliculas();
        JPanel panelBoletos = new JPanel();
        panelBoletos.setBounds(50, 50, 360, 150);
        vista.add(panelBoletos);
        h1 = new Thread(this);
        h1.start(); //se inicia el hilo de reloj
        vista.setVisible(true);
    }
    //funcion que cambia los valores de subtotal, iva y total
    public void reasignarCantidades(){
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
    
    //se cargan las peliculas que estan disponibles en las proximas horas del dia
    public void cargarPeliculas(){
        int filas2 = 0; 
        for(int x =0; x < listPelis.length; x++){
            if(Integer.parseInt(listPelis[x][0]) > (x > 0 ? Integer.parseInt(listPelis[x-1][0]): x)){
                filas2++;
            }
        }
        listPelis2 = new String[filas2][3];
        int j=0;
        int counter = 0;
        for (int x =0; x < listPelis.length; x++){
            if(Integer.parseInt(listPelis[x][0]) > (x > 0 ? Integer.parseInt(listPelis[x-1][0]): x)){
                JPanel panel = new JPanel(new BorderLayout());
                JPanel panelHoras = new JPanel();
                j = counter*120;
                if(listPelis[x][6] != null){
                    panel.add(new JLabel(new ImageIcon(getClass().getResource("/movieImages/"+listPelis[x][6]))),BorderLayout.WEST);
                }
                JLabel label = new JLabel(listPelis[x][1]+" "+listPelis[x][4]+" ("+listPelis[x][2]+") Sub: "+listPelis[x][3]);
                label.setFont(new Font("Arial", Font.PLAIN, 18));
                listPelis2[counter][0] = listPelis[x][0];
                listPelis2[counter][1] = String.valueOf(j);
                listPelis2[counter][2] = String.valueOf(j+120);
                panel.add(label,BorderLayout.NORTH);
                panel.add(panelHoras);
                panel.addMouseListener(this);
                panel.setBounds(5,j,530,120);
                vista.panelPelis.add(panel);
                counter++;
            }            
        }
    }
    //verifica si hay otra función de la misma pelicula
    public boolean esIdRepetido(String id, String[][] lista, int x){
        for (int i = 0; i < x; i++){
            return lista[i][0].equals(id);
        }
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override 
    public void mousePressed(MouseEvent e) {
        if (vista.panelBack == e.getSource()) {
            vista.dispose();
        }
        else if(vista.panelNext == e.getSource()){
            if(!"".equals(vista.txtPelicula.getText()) && (!"0".equals(String.valueOf(vista.txtEstud.getValue())) || (!"0".equals(String.valueOf(vista.txtMayor.getValue()))) || (!"0".equals(String.valueOf(vista.txtNormal.getValue()))))){
                //obtiene el numero total de boletos comprados 
                this.numAsientos = (Integer.parseInt(String.valueOf(vista.txtEstud.getValue())) + Integer.parseInt(String.valueOf(vista.txtMayor.getValue())) + Integer.parseInt(String.valueOf(vista.txtNormal.getValue())));
                if(numAsientos <= numAsientoDis){
                    vista.setEnabled(false);
                    visAsientos.addWindowListener(new WindowAdapter(){
                        @Override
                        public void windowClosing(WindowEvent e) {
                            vista.setEnabled(true);
                            visAsientos.dispose();
                        }
                    });

                    asientos = modelo.obtenerAsientos(sala);
                    this.filas = asientos.length;
                    this.columnas = asientos[0].length;
                    visAsientos.panelAsientos.setLayout(new GridLayout(asientos.length,asientos[0].length));
                    visAsientos.panelAdd1.addMouseListener(this);
                    visAsientos.panelBack.addMouseListener(this);
                    arregloAsientos = new JLabel[asientos.length][asientos[0].length];
                    //imprime los asientos 
                    for (int i = asientos.length -1; i >= 0; i--)
                    {
                        for (int j = 0; j < asientos[0].length; j++)
                        {
                            switch (asientos[i][j])
                            {
                                case 1:
                                    arregloAsientos[i][j] = new JLabel(getLetra(i+1)+"-"+j,new ImageIcon(getClass().getResource("/iconos/asientoAzul.png")),0);
                                    break;
                                case 0:
                                    arregloAsientos[i][j] = new JLabel(getLetra(i+1)+"-"+j,new ImageIcon(getClass().getResource("/iconos/asientoRojo.png")),0);
                                    break;
                                case -1:
                                    arregloAsientos[i][j] = new JLabel("");
                                    break;
                                default:
                                    break;
                            }
                            visAsientos.panelAsientos.add(arregloAsientos[i][j]);
                            arregloAsientos[i][j].addMouseListener(new java.awt.event.MouseAdapter(){
                                @Override
                                public void mousePressed(java.awt.event.MouseEvent evt){
                                    asientosMousePressed(evt);
                                }
                            });
                        }
                    }
                    visAsientos.setLocationRelativeTo(null);
                    visAsientos.setVisible(true);
                }
                else{
                    GenAlert genAlert = new GenAlert();
                    ConAlert alert = new ConAlert(genAlert, "El numero de asientos es mayor a los","que hay disponibles");
                    alert.iniciarVista();
                }
            }
            else{
                GenAlert genAlert = new GenAlert();
                ConAlert alert = new ConAlert(genAlert, "Rellene todos los datos para proseguir");
                alert.iniciarVista();
            }
        }
        else if(vista.panelBack == e.getSource() || vista.panelBack1 == e.getSource()){
            vista.dispose();   
        }
        else if(visAsientos.panelAdd1 == e.getSource()){
            if(countAsientos == numAsientos){
                confirmVenta.panelAdd1.addMouseListener(this);
                confirmVenta.panelBack.addMouseListener(this);
                confirmVenta.txtSubtotal.setText(vista.txtSubtotal1.getText());
                confirmVenta.txtIva.setText(vista.txtIva.getText());
                confirmVenta.txtTotal.setText(vista.txtTotal.getText());
                confirmVenta.txtPeli.setText(vista.txtPelicula.getText());
                confirmVenta.txtBoletos.setText("Normal: "+vista.txtNormal.getValue()+" Estudiante: "+vista.txtEstud.getValue()+" Mayores: "+vista.txtMayor.getValue());
                confirmVenta.setLocationRelativeTo(null);
                confirmVenta.setVisible(true);
            }
            else{
                GenAlert genAlert = new GenAlert();
                ConAlert alert = new ConAlert(genAlert, "Por favor seleccione los asientos para continuar");
                alert.iniciarVista();
            }
        }
        else if(visAsientos.panelBack == e.getSource()){
            visAsientos.dispose();
        }
        else if(confirmVenta.panelAdd1 == e.getSource()){
            realizarInsercion();
        }
        else if(confirmVenta.panelBack == e.getSource()){
            confirmVenta.dispose();
        }
        //para obtener la pelicula en la cual se dio click
        else{
            Point p1 = new Point();
            Point p2 = new Point();
            boolean sw = false;
            vista.comboHorario.removeAllItems();
            for (String[] listPelis21 : listPelis2){
                p1.x = 5;
                p1.y = Integer.parseInt(listPelis21[1]);
                p2.x = 5;
                p2.y = Integer.parseInt(listPelis21[2]);
                if (vista.panelPelis.getMousePosition().getY() > p1.y && vista.panelPelis.getMousePosition().y < p2.y)
                {
                    for (String[] listPeli : listPelis)
                    {
                        if (listPeli[0].equals(listPelis21[0]))
                        {
                            if(sw != true){
                                vista.txtPelicula.setText(listPeli[1]);   
                                sala = Integer.parseInt(listPeli[7]);
                                funcion = Integer.parseInt(listPeli[8]);
                            }
                            vista.comboHorario.addItem(listPeli[5]);
                        }
                    }
                }
                if(sw == true)
                    break;
            }
            numAsientoDis = modelo.getNumAsientos(sala);
            vista.txtAsientosDisp.setText(""+numAsientoDis);
        }
    }
    //esta función marca los asientos que fueron seleccionados 
    public void asientosMousePressed(java.awt.event.MouseEvent e){
        
        for (int i = 0; i < filas; i++)
            {
                for (int j = 0; j < columnas; j++)
                {
                    if(arregloAsientos[i][j] == e.getSource()){
                        if(asientos[i][j] == 0 && countAsientos < numAsientos){
                            arregloAsientos[i][j].setIcon(new ImageIcon(getClass().getResource("/iconos/asientoVerde.png")));
                            asientos[i][j] = 2;
                            countAsientos++;
                        }
                        else if(asientos[i][j] == 2){
                            arregloAsientos[i][j].setIcon(new ImageIcon(getClass().getResource("/iconos/asientoRojo.png")));
                            asientos[i][j] = 0;
                            countAsientos--;
                        }
                    }
                }
            }
    }
    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (vista.panelBack == e.getSource()) {
            setColor(vista.panelBack);
        }
        else if(vista.panelBack1 == e.getSource()){
            setColorCancelar(vista.panelBack1);
        }
        else if(vista.panelNext == e.getSource()){
            setColorAceptar(vista.panelNext);
        }
        else if(visAsientos.panelBack == e.getSource()){
            setColorCancelar(visAsientos.panelBack);
        }
        else if(visAsientos.panelAdd1 == e.getSource()){
            setColorAceptar(visAsientos.panelAdd1);
        }
        else if(confirmVenta.panelAdd1 == e.getSource()){
            setColorAceptar(confirmVenta.panelAdd1);
        }
        else if(confirmVenta.panelBack == e.getSource()){
            setColorCancelar(confirmVenta.panelBack);
        }
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (vista.panelBack == e.getSource()) {
            resetColorSalir(vista.panelBack);
        }
        else if(vista.panelBack1 == e.getSource()){
            resetColor(vista.panelBack1);
        }
        else if(vista.panelNext == e.getSource()){
            resetColor(vista.panelNext);
        }
        else if(visAsientos.panelBack == e.getSource()){
            resetColor(visAsientos.panelBack);
        }
        else if(visAsientos.panelAdd1 == e.getSource()){
            resetColor(visAsientos.panelAdd1);
        }
        else if(confirmVenta.panelAdd1 == e.getSource()){
            resetColor(confirmVenta.panelAdd1);
        }
        else if(confirmVenta.panelBack == e.getSource()){
            resetColor(confirmVenta.panelBack);
        }
    }

    @Override
    public void run()
    {
        Thread ct= Thread.currentThread();
        
        while(ct==h1){
            calcula();
            vista.hora.setText(hora+":"+minutos+":"+segundos+" "+ampm);
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){}
        }
    }
    
    private void calcula() {
        Calendar calendario = new GregorianCalendar();
        Date fechaHoraActual = new Date();
        
        calendario.setTime(fechaHoraActual);
        ampm= calendario.get(Calendar.AM_PM)==Calendar.AM?"AM":"PM";
        
        if(ampm.equals("PM")){
            int h=calendario.get(Calendar.HOUR_OF_DAY)-12;
            hora = h>9?""+h:"0"+h;
        }else{
            hora= calendario.get(Calendar.HOUR_OF_DAY)>9?""+calendario.get(Calendar.HOUR_OF_DAY):"0"+calendario.get(Calendar.HOUR_OF_DAY); 
        }
        minutos = calendario.get(Calendar.MINUTE)>9?""+calendario.get(Calendar.MINUTE):"0"+calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND)>9?""+calendario.get(Calendar.SECOND):"0"+calendario.get(Calendar.SECOND);
    }
    
    private String getLetra(int i) {
        return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
    }
    
    public void realizarInsercion(){
        int[][] selectedSeats = new int[countAsientos][2];
        int counter = 0;
        //obtiene los asientos que fueron
        for (int i = filas-1; i >= 0; i--)
        {
            for (int j = 0; j < columnas; j++)
            {
                if(asientos[i][j] == 2){
                    selectedSeats[counter][0] = i;
                    selectedSeats[counter][1] = j;
                    counter++;
                }
            }
        }
        if(modelo.insertarVenta(Float.parseFloat(confirmVenta.txtSubtotal.getText()), Float.parseFloat(confirmVenta.txtIva.getText()), Float.parseFloat(confirmVenta.txtTotal.getText()), this.id,-1,selectedSeats, sala, funcion)){
            GenSucces genSuccess = new GenSucces();
            ConSucces conSuccess = new ConSucces(genSuccess, "¡Éxito!", "¡Se ha concretado la venta!");
            conSuccess.iniciarVista();
            confirmVenta.dispose();
            visAsientos.dispose();
            vista.setEnabled(true);
        }
        else{
            GenAlert genAlert = new GenAlert();
            ConAlert conAlert = new ConAlert(genAlert, "No se pudo concretar la venta");
            conAlert.iniciarVista();
        }
    }

    
}
