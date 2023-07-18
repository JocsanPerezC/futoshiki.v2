package interfaces;

import controlador.controlador;
import logica.FuncionesListaEnlazada;
import logica.Cuadro;
import logica.JUEGO;
import logica.CargarPartida;
import logica.GuardarPartida;
import logica.Partida;
import logica.XML;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.xml.transform.TransformerException;

public class Jugar extends javax.swing.JFrame {
    
    private controlador miControlador = new controlador();
          
    private Partida partidaActual;
         
    FuncionesListaEnlazada<String> listaJugadasHechas;//para guardar las jugadas hechas 
    
    private static ArrayList<JLabel> listaCuadros;//para guardar los cuadros utilizados, es para poder utilizar/modificar los cuadros
    private static ArrayList<JLabel> listaCuadrosRehacer;//para guardar los cuadros utilizados, es para poder utilizar/modificar los cuadros
    ArrayList<String> ListaJugadasPosi; //lista de posibles jugadas
    private static ArrayList<Cuadro> listaMatrizCuadros;//para guardar las jugadas hechas, para luego utilizar/modificar la matriz
    private static ArrayList<Cuadro> listaMatrizCuadrosRehacer;
   
    private JButton botonesNumeros[] = new JButton[9];
    private JButton botonesPrincipales[] = new JButton[10];
   
    private static JLabel matrizCuadros[][] = new JLabel[9][9];
    private static JLabel matrizLabels[][][] = new JLabel[9][9][2];
       
    int numeroJugada; //para guardar el numero de los botones 
    boolean posibleJugada; //para el boton de posibles jugadas
    
    private Timer t; //para el cronometro
    private int horas, min, sec, cs; //para el cronometro
    
    public Jugar(boolean indicadorCargarPartida) 
    {
        initComponents();
        setLocationRelativeTo(null);
             
        if(indicadorCargarPartida)
        {          
            nombreJugador.setText(CargarPartida.getNombre());
            partidaActual = JUEGO.claseJuego.getPartidaActual();
            horas = Integer.parseInt(CargarPartida.getHoras());
            min = Integer.parseInt(CargarPartida.getMinutos());
            sec = Integer.parseInt(CargarPartida.getSegundos());
            cs = Integer.parseInt(CargarPartida.getMilisegundos());
            JUEGO.claseJuego.setTiempo(CargarPartida.getTiempo());
            JUEGO.claseJuego.setPosicion(CargarPartida.getPosicionPanel());
            t = new Timer(10, acciones);        
        }       
        else
        {
            partidaActual = JUEGO.claseJuego.getPartida();
            //partidaActual = miControlador.getPartida();
        } 
        
        labelNivel.setText("NIVEL " + JUEGO.claseJuego.getPartidaActual().getNivel());
        
        botonesNumeros[0] = boton1; botonesNumeros[1] = boton2; botonesNumeros[2] = boton3;
        botonesNumeros[3] = boton4; botonesNumeros[4] = boton5; botonesNumeros[5] = boton6;
        botonesNumeros[6] = boton7; botonesNumeros[7] = boton8; botonesNumeros[8] = boton9;
        
        botonesPrincipales[0] = iniciarJuegobot; botonesPrincipales[1] = borrarJugadaBot;
        botonesPrincipales[2] = rehacerBoton; botonesPrincipales[3] = top10Boton;
        botonesPrincipales[4] = posibleJuagdaBot; botonesPrincipales[5] = salirJuegoBoton;
        botonesPrincipales[6] = cargarJuegoBoton; botonesPrincipales[7] = borrarJuegoBoton;
        botonesPrincipales[8] = terminaJuegoBoton; botonesPrincipales[9] = guardarJuegoBoton;
        
        for(JButton boton: botonesNumeros)
        {   
            boton.setEnabled(false);
            boton.setSize(58, 50);
        }
             
        terminaJuegoBoton.setEnabled(false);
        posibleJuagdaBot.setEnabled(false);
        rehacerBoton.setEnabled(false);
        
        JUEGO.claseJuego.agregarPartida();
        
        // Se inicia la matriz de los cuadros       
        matrizCuadros[0][0] = cuadro00; matrizCuadros[0][1] = cuadro01; matrizCuadros[0][2] = cuadro02;
        matrizCuadros[0][3] = cuadro03; matrizCuadros[0][4] = cuadro04; matrizCuadros[0][5] = cuadro05;
        matrizCuadros[0][6] = cuadro06; matrizCuadros[0][7] = cuadro07; matrizCuadros[0][8] = cuadro08;
        matrizCuadros[1][0] = cuadro10; matrizCuadros[1][1] = cuadro11; matrizCuadros[1][2] = cuadro12;  
        matrizCuadros[1][3] = cuadro13; matrizCuadros[1][4] = cuadro14; matrizCuadros[1][5] = cuadro15;
        matrizCuadros[1][6] = cuadro16; matrizCuadros[1][7] = cuadro17; matrizCuadros[1][8] = cuadro18;
        matrizCuadros[2][0] = cuadro20; matrizCuadros[2][1] = cuadro21; matrizCuadros[2][2] = cuadro22; 
        matrizCuadros[2][3] = cuadro23; matrizCuadros[2][4] = cuadro24; matrizCuadros[2][5] = cuadro25;
        matrizCuadros[2][6] = cuadro26; matrizCuadros[2][7] = cuadro27; matrizCuadros[2][8] = cuadro28;
        matrizCuadros[3][0] = cuadro30; matrizCuadros[3][1] = cuadro31; matrizCuadros[3][2] = cuadro32;
        matrizCuadros[3][3] = cuadro33; matrizCuadros[3][4] = cuadro34; matrizCuadros[3][5] = cuadro35;
        matrizCuadros[3][6] = cuadro36; matrizCuadros[3][7] = cuadro37; matrizCuadros[3][8] = cuadro38;
        matrizCuadros[4][0] = cuadro40; matrizCuadros[4][1] = cuadro41; matrizCuadros[4][2] = cuadro42; 
        matrizCuadros[4][3] = cuadro43; matrizCuadros[4][4] = cuadro44; matrizCuadros[4][5] = cuadro45;
        matrizCuadros[4][6] = cuadro46; matrizCuadros[4][7] = cuadro47; matrizCuadros[4][8] = cuadro48;
        matrizCuadros[5][0] = cuadro50; matrizCuadros[5][1] = cuadro51; matrizCuadros[5][2] = cuadro52;
        matrizCuadros[5][3] = cuadro53; matrizCuadros[5][4] = cuadro54; matrizCuadros[5][5] = cuadro55;
        matrizCuadros[5][6] = cuadro56; matrizCuadros[5][7] = cuadro57; matrizCuadros[5][8] = cuadro58;
        matrizCuadros[6][0] = cuadro60; matrizCuadros[6][1] = cuadro61; matrizCuadros[6][2] = cuadro62;
        matrizCuadros[6][3] = cuadro63; matrizCuadros[6][4] = cuadro64; matrizCuadros[6][5] = cuadro65;
        matrizCuadros[6][6] = cuadro66; matrizCuadros[6][7] = cuadro67; matrizCuadros[6][8] = cuadro68;
        matrizCuadros[7][0] = cuadro70; matrizCuadros[7][1] = cuadro71; matrizCuadros[7][2] = cuadro72;
        matrizCuadros[7][3] = cuadro73; matrizCuadros[7][4] = cuadro74; matrizCuadros[7][5] = cuadro75;
        matrizCuadros[7][6] = cuadro76; matrizCuadros[7][7] = cuadro77; matrizCuadros[7][8] = cuadro78;
        matrizCuadros[8][0] = cuadro80; matrizCuadros[8][1] = cuadro81; matrizCuadros[8][2] = cuadro82;
        matrizCuadros[8][3] = cuadro83; matrizCuadros[8][4] = cuadro84; matrizCuadros[8][5] = cuadro85;
        matrizCuadros[8][6] = cuadro86; matrizCuadros[8][7] = cuadro87; matrizCuadros[8][8] = cuadro88;
        
        // Se inicia la matriz de los labels        
        matrizLabels[0][0][0] = label00D; matrizLabels[0][0][1] = label00A; matrizLabels[0][1][0] = label01D;
        matrizLabels[0][1][1] = label01A; matrizLabels[0][2][0] = label02D; matrizLabels[0][2][1] = label02A;
        matrizLabels[0][3][0] = label03D; matrizLabels[0][3][1] = label03A; matrizLabels[0][4][0] = label04D;
        matrizLabels[0][4][1] = label04A; matrizLabels[0][5][0] = label05D; matrizLabels[0][5][1] = label05A;
        matrizLabels[0][6][0] = label06D; matrizLabels[0][6][1] = label06A; matrizLabels[0][7][0] = label07D;
        matrizLabels[0][7][1] = label07A; matrizLabels[0][8][0] = null; matrizLabels[0][8][1] = label08A;        
        matrizLabels[1][0][0] = label10D; matrizLabels[1][0][1] = label10A; matrizLabels[1][1][0] = label11D; 
        matrizLabels[1][1][1] = label11A; matrizLabels[1][2][0] = label12D; matrizLabels[1][2][1] = label12A; 
        matrizLabels[1][3][0] = label13D; matrizLabels[1][3][1] = label13A; matrizLabels[1][4][0] = label14D; 
        matrizLabels[1][4][1] = label14A; matrizLabels[1][5][0] = label15D; matrizLabels[1][5][1] = label15A;
        matrizLabels[1][6][0] = label16D; matrizLabels[1][6][1] = label16A; matrizLabels[1][7][0] = label17D;
        matrizLabels[1][7][1] = label17A; matrizLabels[1][8][0] = null; matrizLabels[1][8][1] = label18A;
        matrizLabels[2][0][0] = label20D; matrizLabels[2][0][1] = label20A; matrizLabels[2][1][0] = label21D; 
        matrizLabels[2][1][1] = label21A; matrizLabels[2][2][0] = label22D; matrizLabels[2][2][1] = label22A; 
        matrizLabels[2][3][0] = label23D; matrizLabels[2][3][1] = label23A; matrizLabels[2][4][0] = label24D; 
        matrizLabels[2][4][1] = label24A; matrizLabels[2][5][0] = label25D; matrizLabels[2][5][1] = label25A;
        matrizLabels[2][6][0] = label26D; matrizLabels[2][6][1] = label26A; matrizLabels[2][7][0] = label27D;
        matrizLabels[2][7][1] = label27A; matrizLabels[2][8][0] = null; matrizLabels[2][8][1] = label28A;
        matrizLabels[3][0][0] = label30D; matrizLabels[3][0][1] = label30A; matrizLabels[3][1][0] = label31D;
        matrizLabels[3][1][1] = label31A; matrizLabels[3][2][0] = label32D; matrizLabels[3][2][1] = label32A;
        matrizLabels[3][3][0] = label33D; matrizLabels[3][3][1] = label33A; matrizLabels[3][4][0] = label34D;
        matrizLabels[3][4][1] = label34A; matrizLabels[3][5][0] = label35D; matrizLabels[3][5][1] = label35A;
        matrizLabels[3][6][0] = label36D; matrizLabels[3][6][1] = label36A; matrizLabels[3][7][0] = label37D;
        matrizLabels[3][7][1] = label37A; matrizLabels[3][8][0] = null; matrizLabels[3][8][1] = label38A;
        matrizLabels[4][0][0] = label40D; matrizLabels[4][0][1] = label40A; matrizLabels[4][1][0] = label41D; 
        matrizLabels[4][1][1] = label41A; matrizLabels[4][2][0] = label42D; matrizLabels[4][2][1] = label42A; 
        matrizLabels[4][3][0] = label43D; matrizLabels[4][3][1] = label43A; matrizLabels[4][4][0] = label44D; 
        matrizLabels[4][4][1] = label44A; matrizLabels[4][5][0] = label45D; matrizLabels[4][5][1] = label45A;
        matrizLabels[4][6][0] = label46D; matrizLabels[4][6][1] = label46A; matrizLabels[4][7][0] = label47D;
        matrizLabels[4][7][1] = label47A; matrizLabels[4][8][0] = null; matrizLabels[4][8][1] = label48A;
        matrizLabels[5][0][0] = label50D; matrizLabels[5][0][1] = label50A; matrizLabels[5][1][0] = label51D;
        matrizLabels[5][1][1] = label51A; matrizLabels[5][2][0] = label52D; matrizLabels[5][2][1] = label52A;
        matrizLabels[5][3][0] = label53D; matrizLabels[5][3][1] = label53A; matrizLabels[5][4][0] = label54D;
        matrizLabels[5][4][1] = label54A; matrizLabels[5][5][0] = label55D; matrizLabels[5][5][1] = label55A;
        matrizLabels[5][6][0] = label56D; matrizLabels[5][6][1] = label56A; matrizLabels[5][7][0] = label57D;
        matrizLabels[5][7][1] = label57A; matrizLabels[5][8][0] = null; matrizLabels[5][8][1] = label58A;
        matrizLabels[6][0][0] = label60D; matrizLabels[6][0][1] = label60A; matrizLabels[6][1][0] = label61D;
        matrizLabels[6][1][1] = label61A; matrizLabels[6][2][0] = label62D; matrizLabels[6][2][1] = label62A;
        matrizLabels[6][3][0] = label63D; matrizLabels[6][3][1] = label63A; matrizLabels[6][4][0] = label64D;
        matrizLabels[6][4][1] = label64A; matrizLabels[6][5][0] = label65D; matrizLabels[6][5][1] = label65A;
        matrizLabels[6][6][0] = label66D; matrizLabels[6][6][1] = label66A; matrizLabels[6][7][0] = label67D;
        matrizLabels[6][7][1] = label67A; matrizLabels[6][8][0] = null; matrizLabels[6][8][1] = label68A;
        matrizLabels[7][0][0] = label70D; matrizLabels[7][0][1] = label70A; matrizLabels[7][1][0] = label71D;
        matrizLabels[7][1][1] = label71A; matrizLabels[7][2][0] = label72D; matrizLabels[7][2][1] = label72A;
        matrizLabels[7][3][0] = label73D; matrizLabels[7][3][1] = label73A; matrizLabels[7][4][0] = label74D;
        matrizLabels[7][4][1] = label74A; matrizLabels[7][5][0] = label75D; matrizLabels[7][5][1] = label75A;
        matrizLabels[7][6][0] = label76D; matrizLabels[7][6][1] = label76A; matrizLabels[7][7][0] = label77D;
        matrizLabels[7][7][1] = label77A; matrizLabels[7][8][0] = null; matrizLabels[7][8][1] = label78A;
        matrizLabels[8][0][0] = label80D; matrizLabels[8][0][1] = null; matrizLabels[8][1][0] = label81D;
        matrizLabels[8][1][1] = null; matrizLabels[8][2][0] = label82D; matrizLabels[8][2][1] = null;
        matrizLabels[8][3][0] = label83D; matrizLabels[8][3][1] = null; matrizLabels[8][4][0] = label84D;
        matrizLabels[8][4][1] = null; matrizLabels[8][5][0] = label85D; matrizLabels[8][5][1] = null;
        matrizLabels[8][6][0] = label86D; matrizLabels[8][6][1] = null; matrizLabels[8][7][0] = label87D;
        matrizLabels[8][7][1] = null; matrizLabels[8][8][0] = null; matrizLabels[8][8][1] = null;
        
        if(JUEGO.claseJuego.getTamañoCuadricula() == 5){ // Si la cuadricula es de 5x5            
            jPanel5.setLocation(415, 150);
            jPanel5.setSize(355, 305);
            PanelPrincipal.add(jPanel5);
            boton9.setContentAreaFilled(false);
            boton9.setText("");
            boton8.setContentAreaFilled(false);
            boton8.setText("");
            boton7.setContentAreaFilled(false);
            boton7.setText("");
            boton6.setContentAreaFilled(false);
            boton6.setText("");
        }
        else if(JUEGO.claseJuego.getTamañoCuadricula() == 6){ // Si la cuadricula es de 6x6
          
            jPanel5.setLocation(370, 140);
            jPanel5.setSize(430, 370);
            PanelPrincipal.add(jPanel5);
            boton9.setContentAreaFilled(false);
            boton9.setText("");
            boton8.setContentAreaFilled(false);
            boton8.setText("");
            boton7.setContentAreaFilled(false);
            boton7.setText("");
        }
        else if(JUEGO.claseJuego.getTamañoCuadricula() == 7){ // Si la cuadricula es de 7x7
          
            jPanel5.setLocation(360, 130);
            jPanel5.setSize(505, 433);
            PanelPrincipal.add(jPanel5);
            boton9.setContentAreaFilled(false);
            boton9.setText("");
            boton8.setContentAreaFilled(false);
            boton8.setText("");
        }
        else if(JUEGO.claseJuego.getTamañoCuadricula() == 8){ // Si la cuadricula es de 8x8
          
            jPanel5.setLocation(340, 115);
            jPanel5.setSize(579, 495);
            PanelPrincipal.add(jPanel5);
            boton9.setContentAreaFilled(false);
            boton9.setText("");
        }
        else if(JUEGO.claseJuego.getTamañoCuadricula() == 9){ // Si la cuadricula es de 9x9
          
            jPanel5.setLocation(290, 80);
            jPanel5.setSize(653, 557);
            PanelPrincipal.add(jPanel5);
        }
        
        setConstantes();
        
        posibleJugada = false;
        numeroJugada = 0;  
        ListaJugadasPosi = new ArrayList(); 
        
        listaJugadasHechas = new FuncionesListaEnlazada<>();
        
        listaCuadros = new ArrayList();
        listaCuadrosRehacer = new ArrayList();
        
        listaMatrizCuadros = new ArrayList();
        listaMatrizCuadrosRehacer = new ArrayList();
                   
        //----------- Configura el tiempo/cronometro --------           
        if(!indicadorCargarPartida)
        {
            if (JUEGO.claseJuego.getTiempo().equals("No"))//NO quiere tiempo
            {          
                etiquetaTiempo.setEnabled(false);
                t = null;
            }
            else if(JUEGO.claseJuego.getTiempo().equals("Timer"))//quiere temporizador
            {
                if(JUEGO.claseJuego.getHoras().equals(""))//por si el usuario no da horas
                {
                    horas = 0;
                }
                else
                {
                    horas = Integer.parseInt(JUEGO.claseJuego.getHoras());
                }
                cs = 100;
                sec = Integer.parseInt(JUEGO.claseJuego.getSec());
                min = Integer.parseInt(JUEGO.claseJuego.getMin());
                t = new Timer(10, acciones);
            }
          
            else // Tiempo normal
            {
                min = 0;
                cs = 0;
                sec = 0;
                horas = 0;
                t = new Timer(10, acciones);
            }
        }
        
        //----------- Configura la posisicion de las casillas ------        
       if(JUEGO.claseJuego.getPosicion().equals("Izquierda"))
        {
           
            iniciarJuegobot.setLocation(970, 180);
            borrarJugadaBot.setLocation(970, 240);
            rehacerBoton.setLocation(970, 300);
            top10Boton.setLocation(970, 360);
            posibleJuagdaBot.setLocation(970, 420);

            PanelPrincipal.add(iniciarJuegobot);
            PanelPrincipal.add(borrarJugadaBot);
            PanelPrincipal.add(rehacerBoton);
            PanelPrincipal.add(top10Boton);
            PanelPrincipal.add(posibleJuagdaBot);

            boton1.setLocation(75, 170);
            boton2.setLocation(150, 170);
            boton3.setLocation(75, 225);
            boton4.setLocation(150, 225);
            boton5.setLocation(75, 285);
            boton6.setLocation(150, 285);
            boton7.setLocation(75, 345);
            boton8.setLocation(150, 345);
            boton9.setLocation(110, 405);

            PanelPrincipal.add(boton1);
            PanelPrincipal.add(boton2);
            PanelPrincipal.add(boton3);
            PanelPrincipal.add(boton4);
            PanelPrincipal.add(boton5);
            PanelPrincipal.add(boton6);
            PanelPrincipal.add(boton7);
            PanelPrincipal.add(boton8);
            PanelPrincipal.add(boton9);
            
            cargarJuegoBoton.setLocation(970, 580);
            salirJuegoBoton.setLocation(970, 520);
            guardarJuegoBoton.setLocation(65, 470);
            terminaJuegoBoton.setLocation(65, 525);
            borrarJuegoBoton.setLocation(65, 585);
            PanelPrincipal.add(cargarJuegoBoton);
            PanelPrincipal.add(salirJuegoBoton);
            PanelPrincipal.add(guardarJuegoBoton);
            PanelPrincipal.add(terminaJuegoBoton);
            PanelPrincipal.add(borrarJuegoBoton);
            
            PanelPrincipal.setSize(931, 636);
            PanelPrincipal.setLocation(0, 0);

        }             
        
     }
    
    public void cambiarColorBotonNum(JButton pBoton)
    {        
        numeroJugada = miControlador.cambiarColorBotonNum(pBoton, botonesNumeros, t);
    }
    
    
    public void setBordeRojo(JLabel label)
    {      
        miControlador.setBordeRojo(label);      
    }
    
    public void setBordeNegro(JLabel label)
    {     
       miControlador.setBordeNegro(label);
    }
    
    public void setConstantes()
    { 
        miControlador.setConstantes(matrizCuadros, matrizLabels);
    }
    /**
     * Metodo para el timer/tiempo del juego
     */
    private ActionListener acciones = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            if (JUEGO.claseJuego.getTiempo().equals("Timer"))//temporizador
            { 
                --cs; 
                if(cs == 0)
                {                
                    if((horas == 0) && (min == 0) && (sec == 0) && (cs == 0))//punto de salida
                    {
                        actualizarLabel();
                        t.stop();
                        JOptionPane.showMessageDialog(null, "Se ha quedado sin tiempo");
                        return;
                    }
                    if(sec >= 1)//cuando es 0, no se le quita
                    { 
                        --sec;
                    }                   
                    cs = 100; 
                }
                if(sec == 0) 
                {
                    if(min >= 1)//cuando es 0, no se le quita
                    {
                        sec = 60;  
                        --min;
                    }
                    else//significa que no hay mas minutos que restar
                    {
                        sec = 0;
                    }                 
                }
                if(min == 0)
                {   
                    if(horas >= 1)//cuando es 0, no se le quita
                    {
                        --horas;
                        min = 60;
                    }   
                }
                actualizarLabel();
            }
            else//Cronometro Normal
            {
                ++cs; 
                if(cs==100){
                    cs = 0;
                    ++sec;
                }
                if(sec==60) 
                {
                    sec = 0;
                    ++min;
                }
                if(min==60)
                {
                    min = 0;
                    ++horas;
                }
                actualizarLabel();  
            }
        }       
    };
    
    private void actualizarLabel() 
    {
       miControlador.actualizarLabel(horas, min, sec, cs, etiquetaTiempo);
    }
   
    public void ganoPartida()
    {
        miControlador.ganoPartida(matrizCuadros, this, nombreJugador, horas, min, sec);            
    }
    
    public void guardarNombreTiempo()
    {
        miControlador.guardarNombreTiempo(nombreJugador, horas, min, sec);
    }
    
    private void escribirNumeroCuadro(int fila, int columna)
    {     
        miControlador.escribirNumeroCuadro(
        fila, columna, posibleJugada, ListaJugadasPosi, 
        numeroJugada, matrizCuadros, listaJugadasHechas, 
        listaMatrizCuadros, botonesNumeros, botonesPrincipales, 
        listaCuadros, this, nombreJugador, 
        horas, min, sec);
        
        posibleJugada = false;
        numeroJugada = 0;
 
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        PanelPrincipal = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        cuadro10 = new javax.swing.JLabel();
        cuadro20 = new javax.swing.JLabel();
        cuadro25 = new javax.swing.JLabel();
        cuadro42 = new javax.swing.JLabel();
        cuadro01 = new javax.swing.JLabel();
        cuadro11 = new javax.swing.JLabel();
        cuadro00 = new javax.swing.JLabel();
        cuadro21 = new javax.swing.JLabel();
        cuadro26 = new javax.swing.JLabel();
        cuadro36 = new javax.swing.JLabel();
        cuadro30 = new javax.swing.JLabel();
        cuadro35 = new javax.swing.JLabel();
        cuadro12 = new javax.swing.JLabel();
        cuadro32 = new javax.swing.JLabel();
        cuadro03 = new javax.swing.JLabel();
        cuadro15 = new javax.swing.JLabel();
        cuadro23 = new javax.swing.JLabel();
        cuadro02 = new javax.swing.JLabel();
        cuadro37 = new javax.swing.JLabel();
        cuadro04 = new javax.swing.JLabel();
        cuadro18 = new javax.swing.JLabel();
        cuadro24 = new javax.swing.JLabel();
        cuadro31 = new javax.swing.JLabel();
        cuadro33 = new javax.swing.JLabel();
        label00D = new javax.swing.JLabel();
        label03D = new javax.swing.JLabel();
        label01D = new javax.swing.JLabel();
        label12D = new javax.swing.JLabel();
        label15D = new javax.swing.JLabel();
        label10D = new javax.swing.JLabel();
        label11D = new javax.swing.JLabel();
        label13D = new javax.swing.JLabel();
        label31D = new javax.swing.JLabel();
        label20D = new javax.swing.JLabel();
        label30D = new javax.swing.JLabel();
        label36D = new javax.swing.JLabel();
        label14D = new javax.swing.JLabel();
        label21D = new javax.swing.JLabel();
        label22D = new javax.swing.JLabel();
        label01A = new javax.swing.JLabel();
        label03A = new javax.swing.JLabel();
        label00A = new javax.swing.JLabel();
        label28A = new javax.swing.JLabel();
        label06A = new javax.swing.JLabel();
        label10A = new javax.swing.JLabel();
        label11A = new javax.swing.JLabel();
        label24A = new javax.swing.JLabel();
        label23A = new javax.swing.JLabel();
        label27A = new javax.swing.JLabel();
        label41A = new javax.swing.JLabel();
        label42A = new javax.swing.JLabel();
        label52A = new javax.swing.JLabel();
        label20A = new javax.swing.JLabel();
        label22A = new javax.swing.JLabel();
        label40A = new javax.swing.JLabel();
        label02D = new javax.swing.JLabel();
        cuadro07 = new javax.swing.JLabel();
        label04D = new javax.swing.JLabel();
        cuadro05 = new javax.swing.JLabel();
        label05D = new javax.swing.JLabel();
        cuadro06 = new javax.swing.JLabel();
        label06D = new javax.swing.JLabel();
        cuadro13 = new javax.swing.JLabel();
        label04A = new javax.swing.JLabel();
        cuadro14 = new javax.swing.JLabel();
        label05A = new javax.swing.JLabel();
        cuadro16 = new javax.swing.JLabel();
        label07A = new javax.swing.JLabel();
        label16D = new javax.swing.JLabel();
        label16A = new javax.swing.JLabel();
        label15A = new javax.swing.JLabel();
        cuadro28 = new javax.swing.JLabel();
        label26A = new javax.swing.JLabel();
        label33A = new javax.swing.JLabel();
        cuadro34 = new javax.swing.JLabel();
        label23D = new javax.swing.JLabel();
        label24D = new javax.swing.JLabel();
        label25D = new javax.swing.JLabel();
        label26D = new javax.swing.JLabel();
        cuadro51 = new javax.swing.JLabel();
        cuadro80 = new javax.swing.JLabel();
        cuadro50 = new javax.swing.JLabel();
        label50A = new javax.swing.JLabel();
        label08A = new javax.swing.JLabel();
        label04A6 = new javax.swing.JLabel();
        cuadro22 = new javax.swing.JLabel();
        label30A = new javax.swing.JLabel();
        label54A = new javax.swing.JLabel();
        label55A = new javax.swing.JLabel();
        label70A = new javax.swing.JLabel();
        label34D = new javax.swing.JLabel();
        label32D = new javax.swing.JLabel();
        label33D = new javax.swing.JLabel();
        label35D = new javax.swing.JLabel();
        cuadro40 = new javax.swing.JLabel();
        label31A = new javax.swing.JLabel();
        label32A = new javax.swing.JLabel();
        label34A = new javax.swing.JLabel();
        label37A = new javax.swing.JLabel();
        label36A = new javax.swing.JLabel();
        label76A = new javax.swing.JLabel();
        label35A = new javax.swing.JLabel();
        cuadro41 = new javax.swing.JLabel();
        cuadro43 = new javax.swing.JLabel();
        cuadro44 = new javax.swing.JLabel();
        cuadro46 = new javax.swing.JLabel();
        cuadro45 = new javax.swing.JLabel();
        cuadro47 = new javax.swing.JLabel();
        label50D = new javax.swing.JLabel();
        label44D = new javax.swing.JLabel();
        label43D = new javax.swing.JLabel();
        label46D = new javax.swing.JLabel();
        label51D = new javax.swing.JLabel();
        label47D = new javax.swing.JLabel();
        label53D = new javax.swing.JLabel();
        label51A = new javax.swing.JLabel();
        label43A = new javax.swing.JLabel();
        label44A = new javax.swing.JLabel();
        label45A = new javax.swing.JLabel();
        label47A = new javax.swing.JLabel();
        label48A = new javax.swing.JLabel();
        label46A = new javax.swing.JLabel();
        cuadro53 = new javax.swing.JLabel();
        cuadro52 = new javax.swing.JLabel();
        cuadro54 = new javax.swing.JLabel();
        cuadro55 = new javax.swing.JLabel();
        cuadro56 = new javax.swing.JLabel();
        cuadro57 = new javax.swing.JLabel();
        cuadro58 = new javax.swing.JLabel();
        label70D = new javax.swing.JLabel();
        label61D = new javax.swing.JLabel();
        label62D = new javax.swing.JLabel();
        label63D = new javax.swing.JLabel();
        label64D = new javax.swing.JLabel();
        label65D = new javax.swing.JLabel();
        label56D = new javax.swing.JLabel();
        label53A = new javax.swing.JLabel();
        label56A = new javax.swing.JLabel();
        label65A = new javax.swing.JLabel();
        label57A = new javax.swing.JLabel();
        label58A = new javax.swing.JLabel();
        label78A = new javax.swing.JLabel();
        label75A = new javax.swing.JLabel();
        cuadro63 = new javax.swing.JLabel();
        cuadro62 = new javax.swing.JLabel();
        cuadro61 = new javax.swing.JLabel();
        cuadro65 = new javax.swing.JLabel();
        cuadro72 = new javax.swing.JLabel();
        cuadro64 = new javax.swing.JLabel();
        cuadro74 = new javax.swing.JLabel();
        label60D = new javax.swing.JLabel();
        label66D = new javax.swing.JLabel();
        label71D = new javax.swing.JLabel();
        label72D = new javax.swing.JLabel();
        label67D = new javax.swing.JLabel();
        label73D = new javax.swing.JLabel();
        label77D = new javax.swing.JLabel();
        label60A = new javax.swing.JLabel();
        label61A = new javax.swing.JLabel();
        label62A = new javax.swing.JLabel();
        label63A = new javax.swing.JLabel();
        label66A = new javax.swing.JLabel();
        label68A = new javax.swing.JLabel();
        label80D = new javax.swing.JLabel();
        cuadro71 = new javax.swing.JLabel();
        cuadro70 = new javax.swing.JLabel();
        cuadro67 = new javax.swing.JLabel();
        cuadro66 = new javax.swing.JLabel();
        label21A = new javax.swing.JLabel();
        label12A = new javax.swing.JLabel();
        label14A = new javax.swing.JLabel();
        label17A = new javax.swing.JLabel();
        label18A = new javax.swing.JLabel();
        cuadro86 = new javax.swing.JLabel();
        cuadro68 = new javax.swing.JLabel();
        cuadro78 = new javax.swing.JLabel();
        label84D = new javax.swing.JLabel();
        label82D = new javax.swing.JLabel();
        label81D = new javax.swing.JLabel();
        label83D = new javax.swing.JLabel();
        label85D = new javax.swing.JLabel();
        label86D = new javax.swing.JLabel();
        cuadro60 = new javax.swing.JLabel();
        cuadro82 = new javax.swing.JLabel();
        cuadro81 = new javax.swing.JLabel();
        cuadro83 = new javax.swing.JLabel();
        cuadro85 = new javax.swing.JLabel();
        cuadro84 = new javax.swing.JLabel();
        cuadro87 = new javax.swing.JLabel();
        cuadro88 = new javax.swing.JLabel();
        label71A = new javax.swing.JLabel();
        label64A = new javax.swing.JLabel();
        label73A = new javax.swing.JLabel();
        label72A = new javax.swing.JLabel();
        label40D = new javax.swing.JLabel();
        label41D = new javax.swing.JLabel();
        label42D = new javax.swing.JLabel();
        label45D = new javax.swing.JLabel();
        label52D = new javax.swing.JLabel();
        label54D = new javax.swing.JLabel();
        label74D = new javax.swing.JLabel();
        cuadro17 = new javax.swing.JLabel();
        label07D = new javax.swing.JLabel();
        cuadro27 = new javax.swing.JLabel();
        cuadro38 = new javax.swing.JLabel();
        cuadro48 = new javax.swing.JLabel();
        cuadro76 = new javax.swing.JLabel();
        cuadro08 = new javax.swing.JLabel();
        cuadro77 = new javax.swing.JLabel();
        cuadro75 = new javax.swing.JLabel();
        cuadro73 = new javax.swing.JLabel();
        label17D = new javax.swing.JLabel();
        label27D = new javax.swing.JLabel();
        label37D = new javax.swing.JLabel();
        label55D = new javax.swing.JLabel();
        label57D = new javax.swing.JLabel();
        label87D = new javax.swing.JLabel();
        label76D = new javax.swing.JLabel();
        label75D = new javax.swing.JLabel();
        label02A = new javax.swing.JLabel();
        label13A = new javax.swing.JLabel();
        label25A = new javax.swing.JLabel();
        label38A = new javax.swing.JLabel();
        label77A = new javax.swing.JLabel();
        label67A = new javax.swing.JLabel();
        label74A = new javax.swing.JLabel();
        labelNivel = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        nombreJugador = new javax.swing.JTextPane();
        terminaJuegoBoton = new javax.swing.JButton();
        iniciarJuegobot = new javax.swing.JButton();
        guardarJuegoBoton = new javax.swing.JButton();
        borrarJuegoBoton = new javax.swing.JButton();
        rehacerBoton = new javax.swing.JButton();
        top10Boton = new javax.swing.JButton();
        borrarJugadaBot = new javax.swing.JButton();
        cargarJuegoBoton = new javax.swing.JButton();
        boton2 = new javax.swing.JButton();
        boton3 = new javax.swing.JButton();
        boton4 = new javax.swing.JButton();
        boton5 = new javax.swing.JButton();
        boton1 = new javax.swing.JButton();
        etiquetaTiempo = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        salirJuegoBoton = new javax.swing.JButton();
        posibleJuagdaBot = new javax.swing.JButton();
        boton6 = new javax.swing.JButton();
        boton7 = new javax.swing.JButton();
        boton8 = new javax.swing.JButton();
        boton9 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        PanelPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        PanelPrincipal.setForeground(new java.awt.Color(255, 51, 0));
        PanelPrincipal.setMaximumSize(new java.awt.Dimension(936, 636));
        PanelPrincipal.setMinimumSize(new java.awt.Dimension(936, 636));
        PanelPrincipal.setPreferredSize(new java.awt.Dimension(936, 636));

        jPanel3.setBackground(new java.awt.Color(255, 51, 0));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Algerian", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("FUTOSHIKI");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));

        cuadro10.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro10.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro10MouseClicked(evt);
            }
        });

        cuadro20.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro20.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro20MouseClicked(evt);
            }
        });

        cuadro25.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro25.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro25MouseClicked(evt);
            }
        });

        cuadro42.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro42.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro42.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro42MouseClicked(evt);
            }
        });

        cuadro01.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro01.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro01.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro01.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro01MouseClicked(evt);
            }
        });

        cuadro11.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro11.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro11MouseClicked(evt);
            }
        });

        cuadro00.setBackground(new java.awt.Color(0, 0, 0));
        cuadro00.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro00.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro00.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro00.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro00MouseClicked(evt);
            }
        });

        cuadro21.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro21.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro21MouseClicked(evt);
            }
        });

        cuadro26.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro26.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro26MouseClicked(evt);
            }
        });

        cuadro36.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro36.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro36MouseClicked(evt);
            }
        });

        cuadro30.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro30.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro30MouseClicked(evt);
            }
        });

        cuadro35.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro35.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro35.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro35MouseClicked(evt);
            }
        });

        cuadro12.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro12.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro12MouseClicked(evt);
            }
        });

        cuadro32.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro32.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro32MouseClicked(evt);
            }
        });

        cuadro03.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro03.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro03.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro03.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro03MouseClicked(evt);
            }
        });

        cuadro15.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro15.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro15MouseClicked(evt);
            }
        });

        cuadro23.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro23.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro23MouseClicked(evt);
            }
        });

        cuadro02.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro02.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro02.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro02.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro02MouseClicked(evt);
            }
        });

        cuadro37.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro37.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro37MouseClicked(evt);
            }
        });

        cuadro04.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro04.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro04.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro04.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro04MouseClicked(evt);
            }
        });

        cuadro18.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro18.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro18MouseClicked(evt);
            }
        });

        cuadro24.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro24.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro24MouseClicked(evt);
            }
        });

        cuadro31.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro31.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro31MouseClicked(evt);
            }
        });

        cuadro33.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro33.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro33MouseClicked(evt);
            }
        });

        label00D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label03D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label01D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label12D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label15D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label10D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label11D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label13D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label31D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label20D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label30D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label36D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label14D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label21D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label22D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label01A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label01A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label01A.setAlignmentY(0.0F);

        label03A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label03A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label00A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label00A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label28A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label28A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label28A.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        label06A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label06A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label06A.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        label10A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label10A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label11A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label11A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label24A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label24A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label23A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label23A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label27A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label27A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label41A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label41A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label42A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label42A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label52A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label52A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label20A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label20A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label22A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label22A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label40A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label40A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label02D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        cuadro07.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro07.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro07.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro07.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro07MouseClicked(evt);
            }
        });

        label04D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        cuadro05.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro05.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro05.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro05.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro05MouseClicked(evt);
            }
        });

        label05D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        cuadro06.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro06.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro06.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro06.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro06MouseClicked(evt);
            }
        });

        label06D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        cuadro13.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro13.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro13MouseClicked(evt);
            }
        });

        label04A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label04A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        cuadro14.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro14.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro14MouseClicked(evt);
            }
        });

        label05A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label05A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        cuadro16.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro16.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro16MouseClicked(evt);
            }
        });

        label07A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label07A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label07A.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        label16D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label16A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label16A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label15A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label15A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        cuadro28.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro28.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro28MouseClicked(evt);
            }
        });

        label26A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label26A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label33A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label33A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        cuadro34.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro34.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro34MouseClicked(evt);
            }
        });

        label23D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label24D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label25D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label26D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        cuadro51.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro51.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro51.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro51MouseClicked(evt);
            }
        });

        cuadro80.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro80.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro80.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro80.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro80MouseClicked(evt);
            }
        });

        cuadro50.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro50.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro50.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro50MouseClicked(evt);
            }
        });

        label50A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label50A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label50A.setAlignmentY(0.0F);

        label08A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label08A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label08A.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        label04A6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label04A6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label04A6.setText("   ");
        label04A6.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        cuadro22.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro22.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro22MouseClicked(evt);
            }
        });

        label30A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label30A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label30A.setAlignmentY(0.0F);

        label54A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label54A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label54A.setAlignmentY(0.0F);

        label55A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label55A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label55A.setAlignmentY(0.0F);

        label70A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label70A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label70A.setAlignmentY(0.0F);

        label34D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label32D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label33D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label35D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        cuadro40.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro40.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro40.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro40MouseClicked(evt);
            }
        });

        label31A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label31A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label31A.setAlignmentY(0.0F);

        label32A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label32A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label32A.setAlignmentY(0.0F);

        label34A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label34A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label34A.setAlignmentY(0.0F);

        label37A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label37A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label37A.setAlignmentY(0.0F);

        label36A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label36A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label36A.setAlignmentY(0.0F);

        label76A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label76A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label76A.setAlignmentY(0.0F);

        label35A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label35A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label35A.setAlignmentY(0.0F);

        cuadro41.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro41.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro41.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro41MouseClicked(evt);
            }
        });

        cuadro43.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro43.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro43MouseClicked(evt);
            }
        });

        cuadro44.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro44.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro44.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro44MouseClicked(evt);
            }
        });

        cuadro46.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro46.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro46.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro46MouseClicked(evt);
            }
        });

        cuadro45.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro45.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro45.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro45MouseClicked(evt);
            }
        });

        cuadro47.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro47.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro47.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro47MouseClicked(evt);
            }
        });

        label50D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label44D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label43D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label46D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label51D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label47D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label53D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label51A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label51A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label51A.setAlignmentY(0.0F);

        label43A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label43A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label43A.setAlignmentY(0.0F);

        label44A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label44A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label44A.setAlignmentY(0.0F);

        label45A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label45A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label45A.setAlignmentY(0.0F);

        label47A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label47A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label47A.setAlignmentY(0.0F);

        label48A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label48A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label48A.setAlignmentY(0.0F);

        label46A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label46A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label46A.setAlignmentY(0.0F);

        cuadro53.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro53.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro53.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro53MouseClicked(evt);
            }
        });

        cuadro52.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro52.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro52.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro52MouseClicked(evt);
            }
        });

        cuadro54.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro54.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro54.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro54MouseClicked(evt);
            }
        });

        cuadro55.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro55.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro55.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro55MouseClicked(evt);
            }
        });

        cuadro56.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro56.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro56.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro56MouseClicked(evt);
            }
        });

        cuadro57.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro57.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro57.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro57.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro57MouseClicked(evt);
            }
        });

        cuadro58.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro58.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro58.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro58MouseClicked(evt);
            }
        });

        label70D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label61D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label62D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label63D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label64D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label65D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label56D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label53A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label53A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label53A.setAlignmentY(0.0F);

        label56A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label56A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label56A.setAlignmentY(0.0F);

        label65A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label65A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label65A.setAlignmentY(0.0F);

        label57A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label57A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label57A.setAlignmentY(0.0F);

        label58A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label58A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label58A.setAlignmentY(0.0F);

        label78A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label78A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label78A.setAlignmentY(0.0F);

        label75A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label75A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label75A.setAlignmentY(0.0F);

        cuadro63.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro63.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro63.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro63.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro63MouseClicked(evt);
            }
        });

        cuadro62.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro62.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro62.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro62.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro62MouseClicked(evt);
            }
        });

        cuadro61.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro61.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro61.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro61.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro61MouseClicked(evt);
            }
        });

        cuadro65.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro65.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro65.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro65.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro65MouseClicked(evt);
            }
        });

        cuadro72.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro72.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro72.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro72.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro72MouseClicked(evt);
            }
        });

        cuadro64.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro64.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro64.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro64MouseClicked(evt);
            }
        });

        cuadro74.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro74.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro74.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro74.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro74MouseClicked(evt);
            }
        });

        label60D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label66D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label71D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label72D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label67D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label73D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label77D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label60A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label60A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label60A.setAlignmentY(0.0F);

        label61A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label61A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label61A.setAlignmentY(0.0F);

        label62A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label62A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label62A.setAlignmentY(0.0F);

        label63A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label63A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label63A.setAlignmentY(0.0F);

        label66A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label66A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label66A.setAlignmentY(0.0F);

        label68A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label68A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label68A.setAlignmentY(0.0F);

        label80D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        cuadro71.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro71.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro71.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro71.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro71MouseClicked(evt);
            }
        });

        cuadro70.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro70.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro70.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro70.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro70MouseClicked(evt);
            }
        });

        cuadro67.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro67.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro67.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro67.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro67MouseClicked(evt);
            }
        });

        cuadro66.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro66.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro66.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro66.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro66MouseClicked(evt);
            }
        });

        label21A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label21A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label12A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label12A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label14A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label14A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label17A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label17A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label18A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label18A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        cuadro86.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro86.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro86.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro86.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro86MouseClicked(evt);
            }
        });

        cuadro68.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro68.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro68.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro68.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro68MouseClicked(evt);
            }
        });

        cuadro78.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro78.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro78.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro78.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro78MouseClicked(evt);
            }
        });

        label84D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label82D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label81D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label83D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label85D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label86D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        cuadro60.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro60.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro60.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro60MouseClicked(evt);
            }
        });

        cuadro82.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro82.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro82.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro82.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro82MouseClicked(evt);
            }
        });

        cuadro81.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro81.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro81.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro81.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro81MouseClicked(evt);
            }
        });

        cuadro83.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro83.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro83.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro83.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro83MouseClicked(evt);
            }
        });

        cuadro85.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro85.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro85.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro85.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro85MouseClicked(evt);
            }
        });

        cuadro84.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro84.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro84.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro84.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro84MouseClicked(evt);
            }
        });

        cuadro87.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro87.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro87.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro87.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro87MouseClicked(evt);
            }
        });

        cuadro88.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro88.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro88.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro88.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro88MouseClicked(evt);
            }
        });

        label71A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label71A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label71A.setAlignmentY(0.0F);

        label64A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label64A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label64A.setAlignmentY(0.0F);

        label73A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label73A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label73A.setAlignmentY(0.0F);

        label72A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label72A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label72A.setAlignmentY(0.0F);

        label40D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label41D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label42D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label45D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label52D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label54D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label74D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        cuadro17.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro17.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro17MouseClicked(evt);
            }
        });

        label07D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        cuadro27.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro27.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro27MouseClicked(evt);
            }
        });

        cuadro38.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro38.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro38MouseClicked(evt);
            }
        });

        cuadro48.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro48.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro48.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro48MouseClicked(evt);
            }
        });

        cuadro76.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro76.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro76.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro76.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro76MouseClicked(evt);
            }
        });

        cuadro08.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro08.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro08.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro08.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro08MouseClicked(evt);
            }
        });

        cuadro77.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro77.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro77.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro77.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro77MouseClicked(evt);
            }
        });

        cuadro75.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro75.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro75.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro75.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro75MouseClicked(evt);
            }
        });

        cuadro73.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cuadro73.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cuadro73.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        cuadro73.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuadro73MouseClicked(evt);
            }
        });

        label17D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label27D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label37D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label55D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label57D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label87D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label76D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label75D.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N

        label02A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label02A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label13A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label13A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label25A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label25A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label38A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label38A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label77A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label77A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label67A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label67A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        label74A.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label74A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label74A.setAlignmentY(0.0F);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(label03A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label20A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                                        .addGap(1, 1, 1)
                                                        .addComponent(cuadro00, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addComponent(label30A, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(cuadro40, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(label50A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cuadro50, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cuadro60, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(label00A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(label60D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(label61A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                    .addComponent(cuadro61, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(label61D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGap(0, 73, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                    .addGap(1, 1, 1)
                                                    .addComponent(cuadro10, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(label10A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cuadro20, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cuadro30, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                    .addGap(0, 2, Short.MAX_VALUE)
                                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                            .addComponent(label00D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(cuadro01, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(label01D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(cuadro02, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(label02D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                                                    .addComponent(label10D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(label11A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(cuadro11, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                    .addGap(1, 1, 1))
                                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                                    .addComponent(label30D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(label21A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(cuadro31, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(label31A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(label41A, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                                            .addComponent(label40D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                            .addComponent(cuadro41, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                    .addGap(3, 3, 3))
                                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                                    .addGap(4, 4, 4)
                                                                    .addComponent(label50D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addGap(2, 2, 2)
                                                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(label01A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(cuadro51, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(label51A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                                    .addGap(3, 3, 3)
                                                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                                            .addComponent(label11D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(label12A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                                                    .addComponent(cuadro12, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                    .addGap(9, 9, 9)
                                                                                    .addComponent(label12D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addComponent(label02A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                                            .addGap(22, 22, 22)
                                                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(label22A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                                                    .addComponent(cuadro22, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                    .addComponent(label22D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                                    .addComponent(label51D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                    .addComponent(cuadro52, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                            .addComponent(label20D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(cuadro21, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(label21D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addGap(51, 51, 51))
                                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                            .addComponent(label31D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(cuadro32, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(label32D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                    .addGap(75, 75, 75)
                                                    .addComponent(label41D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                            .addGap(1, 1, 1)
                                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                                    .addGap(52, 52, 52)
                                                                    .addComponent(label52D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addComponent(label42A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(label52A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                                    .addGap(6, 6, 6)
                                                                    .addComponent(cuadro62, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                    .addComponent(label62D))
                                                                .addComponent(label62A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                                    .addGap(6, 6, 6)
                                                                    .addComponent(cuadro72, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                    .addComponent(label72D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addComponent(label72A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                                    .addGap(6, 6, 6)
                                                                    .addComponent(cuadro82, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                    .addComponent(label82D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(label32A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                                    .addGap(6, 6, 6)
                                                                    .addComponent(cuadro42, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(label42D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(cuadro13, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(label13A, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(cuadro23, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(label33A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(cuadro03, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cuadro33, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(label23A, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(cuadro43, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label43A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cuadro53, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label53A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cuadro63, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cuadro73, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label63A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cuadro83, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label73A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label40A, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(cuadro80, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(label80D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cuadro81, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(label81D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                                .addComponent(label70A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(label71A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                                .addComponent(cuadro70, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(label70D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cuadro71, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(label71D)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(label03D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label13D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label23D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label33D, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(label43D, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(label53D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label83D, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                                        .addComponent(label64A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(label65A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                                        .addComponent(cuadro64, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(label64D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(cuadro65, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                                        .addComponent(label65D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(cuadro66, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addComponent(label66A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(label66D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cuadro67, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(label76D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cuadro77, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(label67A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(label77A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(label77D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cuadro78, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(label68A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                                        .addComponent(label67D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(cuadro68, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                                        .addComponent(label78A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(1, 1, 1))))))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                                        .addComponent(cuadro04, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(label04D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(cuadro05, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(label05D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(cuadro06, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                                                        .addGap(51, 51, 51)
                                                                        .addComponent(label24D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                    .addComponent(label24A, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                                                        .addComponent(cuadro34, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(label34D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                                                        .addComponent(cuadro25, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(label25D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                    .addComponent(label25A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                                                        .addComponent(cuadro35, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(label35D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                                                .addComponent(cuadro54, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(label54D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                                            .addComponent(cuadro44, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                            .addComponent(label44D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                            .addGap(5, 5, 5))
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                                                            .addComponent(label44A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                            .addGap(27, 27, 27)))
                                                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                                                        .addComponent(label54A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(27, 27, 27)))
                                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                                                        .addComponent(cuadro45, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(label45D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                            .addComponent(label55A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                            .addComponent(label45A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                            .addComponent(label35A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                                                .addComponent(cuadro55, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(label55D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                        .addGap(0, 0, Short.MAX_VALUE)))))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(cuadro36, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(label26A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(cuadro26, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                .addComponent(label36A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(cuadro46, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(label46A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(cuadro56, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(label56A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                    .addComponent(label34A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                                        .addComponent(label06D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                                .addComponent(label07A, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(label08A, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                                .addComponent(cuadro07, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(label07D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(cuadro08, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                            .addComponent(label26D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(label36D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                                .addComponent(cuadro37, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(label37D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(cuadro38, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                                .addComponent(label27A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(label28A, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                                .addComponent(cuadro27, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(label27D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(cuadro28, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                            .addComponent(label37A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(label47A, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                                    .addComponent(label56D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                    .addComponent(cuadro57, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                                    .addComponent(label46D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                    .addComponent(cuadro47, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                            .addComponent(label57A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGap(6, 6, 6)
                                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                .addComponent(label38A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                                    .addComponent(label47D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                    .addComponent(cuadro48, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addComponent(label48A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                                    .addComponent(label57D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                    .addComponent(cuadro58, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                            .addComponent(label58A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(cuadro14, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(label14A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(label14D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(label15A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                                        .addComponent(cuadro15, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(label15D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(5, 5, 5)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(cuadro16, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(label16A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(label16D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(label17A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(cuadro17, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(label17D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(label18A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(cuadro18, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(label04A, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(29, 29, 29)
                                                .addComponent(label05A, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(29, 29, 29)
                                                .addComponent(label06A, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(cuadro24, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addComponent(label63D)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(label73D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                                .addComponent(label74A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(28, 28, 28)
                                                .addComponent(label75A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(cuadro74, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(label74D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cuadro75, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(label75D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(cuadro84, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(label84D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cuadro85, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(label85D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cuadro76, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label76A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(cuadro86, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(label86D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cuadro87, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(label87D, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cuadro88, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addComponent(label60A, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80)
                .addComponent(label04A6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label02D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label03D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cuadro03, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(label00D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label01D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cuadro00, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cuadro01, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cuadro02, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cuadro05, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label04D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cuadro04, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cuadro06, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label05D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(label06D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label01A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(label03A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label02A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(label00A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(label04A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label05A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label06A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label08A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label07A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cuadro07, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label07D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cuadro08, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(label14A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label13A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label12A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label11A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label10A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(4, 4, 4)
                                        .addComponent(cuadro24, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(label24A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label25A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label26A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(50, 50, 50)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(label30A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label31A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label32A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label33A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label34A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label35A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(label27A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(label15A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(label16A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(label17A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(label18A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGap(4, 4, 4)
                                                        .addComponent(cuadro28, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                                        .addGap(17, 17, 17)
                                                        .addComponent(label27D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                                                .addComponent(label28A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(cuadro38, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label37D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(3, 3, 3)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(label37A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label36A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label38A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(1, 1, 1))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(label12D, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(label04A6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(label10D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(2, 2, 2))))
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(label13D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cuadro11, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cuadro10, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cuadro12, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cuadro13, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cuadro14, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(label14D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label11D, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(label15D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cuadro15, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cuadro16, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label16D, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cuadro17, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label17D, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cuadro18, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(19, 19, 19)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label26D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addComponent(cuadro22, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cuadro31, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(label24D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(label25D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(cuadro25, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(cuadro26, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addComponent(cuadro27, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(label23D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(cuadro23, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                                        .addGap(2, 2, 2)
                                                        .addComponent(label22D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addComponent(label20D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(label21D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(cuadro21, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGap(2, 2, 2)))
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                                        .addGap(23, 23, 23)
                                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(label31D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(label30D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                    .addComponent(label32D, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                    .addGap(24, 24, 24)
                                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(label33D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(cuadro34, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(cuadro33, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(cuadro35, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(label34D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(cuadro36, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(cuadro37, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(label35D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(label36D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(label22A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                    .addComponent(cuadro20, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(label20A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(label21A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(label23A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(cuadro30, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                                    .addGap(7, 7, 7)
                                                    .addComponent(cuadro32, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cuadro40, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cuadro41, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cuadro42, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cuadro43, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cuadro44, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cuadro45, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cuadro46, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cuadro47, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cuadro48, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label41D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label42D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label44D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label46D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label47D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(label40D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label43D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label45D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(label40A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label41A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(label42A, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label43A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label44A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(label45A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label46A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(label47A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label48A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label51D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label52D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(label55D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(label57D, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(cuadro50, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cuadro51, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cuadro53, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cuadro55, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cuadro56, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cuadro57, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cuadro58, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(label50D, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(label56D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(cuadro52, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(label50A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label51A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label52A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label53A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label54A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label55A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label56A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label57A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label58A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(label54D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cuadro65, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cuadro66, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cuadro64, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cuadro63, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label62D, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label66D, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(7, 7, 7))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(label60D, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cuadro61, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label61D, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cuadro62, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(7, 7, 7)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(label63D)
                                            .addComponent(label64D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(cuadro60, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cuadro67, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cuadro68, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label65D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label67D, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(2, 2, 2)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(label61A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label62A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label63A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label64A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label65A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label66A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label67A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label68A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(label70D, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cuadro71, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(cuadro72, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label72D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(label71D, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(label60A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cuadro70, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(label73D, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cuadro73, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cuadro74, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label74D, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(label76D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(label75D, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cuadro75, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cuadro76, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cuadro77, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(cuadro78, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label77D, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(3, 3, 3)))))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(label77A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label78A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cuadro87, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cuadro88, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label87D, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(label70A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label71A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label72A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label73A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label74A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label75A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cuadro80, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(cuadro81, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(label80D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(label81D, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(cuadro82, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cuadro83, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label82D, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cuadro84, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label84D, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cuadro85, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(label83D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(label76A, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(label86D, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cuadro86, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(label85D, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label53D, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cuadro54, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        labelNivel.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        labelNivel.setForeground(new java.awt.Color(255, 0, 0));
        labelNivel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelNivel.setText("NIVEL FÁCIL");

        jLabel66.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(255, 51, 0));
        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel66.setText("Nombre del Jugador");

        nombreJugador.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                nombreJugadorInputMethodTextChanged(evt);
            }
        });
        jScrollPane2.setViewportView(nombreJugador);

        terminaJuegoBoton.setBackground(new java.awt.Color(204, 0, 204));
        terminaJuegoBoton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        terminaJuegoBoton.setForeground(new java.awt.Color(255, 255, 255));
        terminaJuegoBoton.setText("TERMINAR JUEGO");
        terminaJuegoBoton.setBorder(null);
        terminaJuegoBoton.setFocusPainted(false);
        terminaJuegoBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                terminaJuegoBotonActionPerformed(evt);
            }
        });

        iniciarJuegobot.setBackground(new java.awt.Color(0, 153, 0));
        iniciarJuegobot.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        iniciarJuegobot.setForeground(new java.awt.Color(255, 255, 255));
        iniciarJuegobot.setText("INICIAR JUEGO");
        iniciarJuegobot.setBorder(null);
        iniciarJuegobot.setFocusPainted(false);
        iniciarJuegobot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iniciarJuegobotActionPerformed(evt);
            }
        });

        guardarJuegoBoton.setBackground(new java.awt.Color(153, 153, 153));
        guardarJuegoBoton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        guardarJuegoBoton.setForeground(new java.awt.Color(255, 255, 255));
        guardarJuegoBoton.setText("Guardar Juego");
        guardarJuegoBoton.setBorder(null);
        guardarJuegoBoton.setFocusPainted(false);
        guardarJuegoBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarJuegoBotonActionPerformed(evt);
            }
        });

        borrarJuegoBoton.setBackground(new java.awt.Color(51, 153, 255));
        borrarJuegoBoton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        borrarJuegoBoton.setForeground(new java.awt.Color(255, 255, 255));
        borrarJuegoBoton.setText("BORRAR JUEGO");
        borrarJuegoBoton.setBorder(null);
        borrarJuegoBoton.setFocusPainted(false);
        borrarJuegoBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarJuegoBotonActionPerformed(evt);
            }
        });

        rehacerBoton.setBackground(new java.awt.Color(51, 204, 255));
        rehacerBoton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        rehacerBoton.setForeground(new java.awt.Color(255, 255, 255));
        rehacerBoton.setText("REHACER JUGADA");
        rehacerBoton.setBorder(null);
        rehacerBoton.setFocusPainted(false);
        rehacerBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rehacerBotonActionPerformed(evt);
            }
        });

        top10Boton.setBackground(new java.awt.Color(255, 204, 0));
        top10Boton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        top10Boton.setForeground(new java.awt.Color(255, 255, 255));
        top10Boton.setText("TOP 10");
        top10Boton.setBorder(null);
        top10Boton.setFocusPainted(false);
        top10Boton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                top10BotonActionPerformed(evt);
            }
        });

        borrarJugadaBot.setBackground(new java.awt.Color(255, 51, 51));
        borrarJugadaBot.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        borrarJugadaBot.setForeground(new java.awt.Color(255, 255, 255));
        borrarJugadaBot.setText("BORRAR JUGADA");
        borrarJugadaBot.setBorder(null);
        borrarJugadaBot.setFocusPainted(false);
        borrarJugadaBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarJugadaBotActionPerformed(evt);
            }
        });

        cargarJuegoBoton.setBackground(new java.awt.Color(153, 153, 153));
        cargarJuegoBoton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cargarJuegoBoton.setForeground(new java.awt.Color(255, 255, 255));
        cargarJuegoBoton.setText("Cargar Juego");
        cargarJuegoBoton.setBorder(null);
        cargarJuegoBoton.setFocusPainted(false);
        cargarJuegoBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargarJuegoBotonActionPerformed(evt);
            }
        });

        boton2.setBackground(new java.awt.Color(204, 204, 204));
        boton2.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        boton2.setText("2");
        boton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton2ActionPerformed(evt);
            }
        });

        boton3.setBackground(new java.awt.Color(204, 204, 204));
        boton3.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        boton3.setText("3");
        boton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton3ActionPerformed(evt);
            }
        });

        boton4.setBackground(new java.awt.Color(204, 204, 204));
        boton4.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        boton4.setText("4");
        boton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton4ActionPerformed(evt);
            }
        });

        boton5.setBackground(new java.awt.Color(204, 204, 204));
        boton5.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        boton5.setText("5");
        boton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton5ActionPerformed(evt);
            }
        });

        boton1.setBackground(new java.awt.Color(204, 204, 204));
        boton1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        boton1.setText("1");
        boton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton1ActionPerformed(evt);
            }
        });

        etiquetaTiempo.setFont(new java.awt.Font("Lucida Sans", 0, 18)); // NOI18N
        etiquetaTiempo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaTiempo.setText("00:00:00:00");

        jLabel3.setFont(new java.awt.Font("Sitka Text", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Tiempo:");

        salirJuegoBoton.setBackground(new java.awt.Color(153, 153, 153));
        salirJuegoBoton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        salirJuegoBoton.setForeground(new java.awt.Color(255, 255, 255));
        salirJuegoBoton.setText("Salir");
        salirJuegoBoton.setBorder(null);
        salirJuegoBoton.setFocusPainted(false);
        salirJuegoBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirJuegoBotonActionPerformed(evt);
            }
        });

        posibleJuagdaBot.setBackground(new java.awt.Color(102, 255, 255));
        posibleJuagdaBot.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        posibleJuagdaBot.setForeground(new java.awt.Color(255, 255, 255));
        posibleJuagdaBot.setText("POSIBLES JUGADAS");
        posibleJuagdaBot.setBorder(null);
        posibleJuagdaBot.setFocusPainted(false);
        posibleJuagdaBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                posibleJuagdaBotActionPerformed(evt);
            }
        });

        boton6.setBackground(new java.awt.Color(204, 204, 204));
        boton6.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        boton6.setText("6");
        boton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton6ActionPerformed(evt);
            }
        });

        boton7.setBackground(new java.awt.Color(204, 204, 204));
        boton7.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        boton7.setText("7");
        boton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton7ActionPerformed(evt);
            }
        });

        boton8.setBackground(new java.awt.Color(204, 204, 204));
        boton8.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        boton8.setText("8");
        boton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton8ActionPerformed(evt);
            }
        });

        boton9.setBackground(new java.awt.Color(204, 204, 204));
        boton9.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        boton9.setText("9");
        boton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelPrincipalLayout = new javax.swing.GroupLayout(PanelPrincipal);
        PanelPrincipal.setLayout(PanelPrincipalLayout);
        PanelPrincipalLayout.setHorizontalGroup(
            PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPrincipalLayout.createSequentialGroup()
                .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPrincipalLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PanelPrincipalLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(posibleJuagdaBot, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                            .addComponent(top10Boton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rehacerBoton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(borrarJugadaBot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(iniciarJuegobot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(salirJuegoBoton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cargarJuegoBoton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PanelPrincipalLayout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelPrincipalLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PanelPrincipalLayout.createSequentialGroup()
                                        .addComponent(boton1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(boton2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(PanelPrincipalLayout.createSequentialGroup()
                                        .addComponent(boton3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(boton4, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(PanelPrincipalLayout.createSequentialGroup()
                                        .addComponent(boton5, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(boton6, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(PanelPrincipalLayout.createSequentialGroup()
                                        .addComponent(boton7, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(boton8, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(PanelPrincipalLayout.createSequentialGroup()
                                        .addGap(39, 39, 39)
                                        .addComponent(boton9, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(borrarJuegoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(terminaJuegoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(guardarJuegoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(PanelPrincipalLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(15, Short.MAX_VALUE))
                    .addGroup(PanelPrincipalLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(etiquetaTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))))
        );
        PanelPrincipalLayout.setVerticalGroup(
            PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPrincipalLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(iniciarJuegobot, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(borrarJugadaBot, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rehacerBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(top10Boton, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(posibleJuagdaBot, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(salirJuegoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cargarJuegoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(PanelPrincipalLayout.createSequentialGroup()
                .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(PanelPrincipalLayout.createSequentialGroup()
                        .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelPrincipalLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelPrincipalLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(labelNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelPrincipalLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(etiquetaTiempo)
                        .addGap(26, 26, 26)
                        .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(boton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boton2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(boton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boton4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(boton5)
                            .addComponent(boton6))
                        .addGap(18, 18, 18)
                        .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(boton7)
                            .addComponent(boton8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(boton9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(borrarJuegoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(terminaJuegoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(guardarJuegoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(PanelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 1148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton1ActionPerformed
          cambiarColorBotonNum(boton1);
    }//GEN-LAST:event_boton1ActionPerformed

    private void boton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton5ActionPerformed
        cambiarColorBotonNum(boton5);
    }//GEN-LAST:event_boton5ActionPerformed

    private void boton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton4ActionPerformed
        cambiarColorBotonNum(boton4);
    }//GEN-LAST:event_boton4ActionPerformed

    private void boton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton3ActionPerformed
        cambiarColorBotonNum(boton3);
    }//GEN-LAST:event_boton3ActionPerformed

    private void boton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton2ActionPerformed
        cambiarColorBotonNum(boton2);
    }//GEN-LAST:event_boton2ActionPerformed

    private void borrarJugadaBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrarJugadaBotActionPerformed
        miControlador.borrarJugadaBoton(listaJugadasHechas, rehacerBoton, listaMatrizCuadros, 
                listaMatrizCuadrosRehacer, listaCuadros, listaCuadrosRehacer);
    }//GEN-LAST:event_borrarJugadaBotActionPerformed

    private void top10BotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_top10BotonActionPerformed
        miControlador.top10Boton(t);       
    }//GEN-LAST:event_top10BotonActionPerformed

    private void rehacerBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rehacerBotonActionPerformed
        miControlador.rehacerBoton(rehacerBoton, listaMatrizCuadrosRehacer, listaCuadrosRehacer);
    }//GEN-LAST:event_rehacerBotonActionPerformed

    private void borrarJuegoBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrarJuegoBotonActionPerformed
        miControlador.borrarJuegoBoton(t, matrizCuadros, matrizLabels);
    }//GEN-LAST:event_borrarJuegoBotonActionPerformed

    private void iniciarJuegobotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iniciarJuegobotActionPerformed
        
        miControlador.iniciarJuegoboton(nombreJugador, botonesPrincipales, botonesNumeros, t);
    }//GEN-LAST:event_iniciarJuegobotActionPerformed

    private void terminaJuegoBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_terminaJuegoBotonActionPerformed
        miControlador.terminaJuegoBoton(t, this);
    }//GEN-LAST:event_terminaJuegoBotonActionPerformed

    private void cuadro33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro33MouseClicked
        escribirNumeroCuadro(3,3);
    }//GEN-LAST:event_cuadro33MouseClicked

    private void cuadro31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro31MouseClicked
        escribirNumeroCuadro(3,1);
    }//GEN-LAST:event_cuadro31MouseClicked

    private void cuadro24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro24MouseClicked
        escribirNumeroCuadro(2,4);
    }//GEN-LAST:event_cuadro24MouseClicked

    private void cuadro18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro18MouseClicked
        escribirNumeroCuadro(1,8);
    }//GEN-LAST:event_cuadro18MouseClicked

    private void cuadro04MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro04MouseClicked
        escribirNumeroCuadro(0,4);
    }//GEN-LAST:event_cuadro04MouseClicked

    private void cuadro37MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro37MouseClicked
        escribirNumeroCuadro(3,7);
    }//GEN-LAST:event_cuadro37MouseClicked

    private void cuadro02MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro02MouseClicked
        escribirNumeroCuadro(0,2);
    }//GEN-LAST:event_cuadro02MouseClicked

    private void cuadro23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro23MouseClicked
        escribirNumeroCuadro(2,3);
    }//GEN-LAST:event_cuadro23MouseClicked

    private void cuadro15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro15MouseClicked
        escribirNumeroCuadro(1,5);
    }//GEN-LAST:event_cuadro15MouseClicked

    private void cuadro03MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro03MouseClicked
        escribirNumeroCuadro(0,3);
    }//GEN-LAST:event_cuadro03MouseClicked

    private void cuadro32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro32MouseClicked
        escribirNumeroCuadro(3,2);
    }//GEN-LAST:event_cuadro32MouseClicked

    private void cuadro12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro12MouseClicked
        escribirNumeroCuadro(1,2);
    }//GEN-LAST:event_cuadro12MouseClicked

    private void cuadro35MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro35MouseClicked
        escribirNumeroCuadro(3,5);
    }//GEN-LAST:event_cuadro35MouseClicked

    private void cuadro30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro30MouseClicked
        escribirNumeroCuadro(3,0);
    }//GEN-LAST:event_cuadro30MouseClicked

    private void cuadro36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro36MouseClicked
        escribirNumeroCuadro(3,6);
    }//GEN-LAST:event_cuadro36MouseClicked

    private void cuadro26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro26MouseClicked
        escribirNumeroCuadro(2,6);
    }//GEN-LAST:event_cuadro26MouseClicked

    private void cuadro21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro21MouseClicked
        escribirNumeroCuadro(2,1);
    }//GEN-LAST:event_cuadro21MouseClicked

    private void cuadro00MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro00MouseClicked
        escribirNumeroCuadro(0,0);      
    }//GEN-LAST:event_cuadro00MouseClicked

    private void cuadro11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro11MouseClicked
        escribirNumeroCuadro(1,1);
    }//GEN-LAST:event_cuadro11MouseClicked

    private void cuadro01MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro01MouseClicked
        escribirNumeroCuadro(0,1);
    }//GEN-LAST:event_cuadro01MouseClicked

    private void cuadro42MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro42MouseClicked
        escribirNumeroCuadro(4,2);
    }//GEN-LAST:event_cuadro42MouseClicked

    private void cuadro25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro25MouseClicked
        escribirNumeroCuadro(2,5);
    }//GEN-LAST:event_cuadro25MouseClicked

    private void cuadro20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro20MouseClicked
        escribirNumeroCuadro(2,0);
    }//GEN-LAST:event_cuadro20MouseClicked

    private void cargarJuegoBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargarJuegoBotonActionPerformed
        miControlador.cargarJuegoBoton(this);
    }//GEN-LAST:event_cargarJuegoBotonActionPerformed

    private void guardarJuegoBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarJuegoBotonActionPerformed
        miControlador.guardarJuegoBoton(horas, min, sec, cs, partidaActual, nombreJugador);
    }//GEN-LAST:event_guardarJuegoBotonActionPerformed

    private void nombreJugadorInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_nombreJugadorInputMethodTextChanged

    }//GEN-LAST:event_nombreJugadorInputMethodTextChanged

    private void salirJuegoBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirJuegoBotonActionPerformed
        miControlador.salirJuegoBoton(this);
    }//GEN-LAST:event_salirJuegoBotonActionPerformed

    private void cuadro07MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro07MouseClicked
        escribirNumeroCuadro(0,7);
    }//GEN-LAST:event_cuadro07MouseClicked

    private void cuadro05MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro05MouseClicked
        escribirNumeroCuadro(0,5);
    }//GEN-LAST:event_cuadro05MouseClicked

    private void cuadro06MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro06MouseClicked
        escribirNumeroCuadro(0,6);
    }//GEN-LAST:event_cuadro06MouseClicked

    private void cuadro10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro10MouseClicked
        escribirNumeroCuadro(1,0);
    }//GEN-LAST:event_cuadro10MouseClicked

    private void cuadro13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro13MouseClicked
        escribirNumeroCuadro(1,3);
    }//GEN-LAST:event_cuadro13MouseClicked

    private void cuadro14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro14MouseClicked
       escribirNumeroCuadro(1,4);
    }//GEN-LAST:event_cuadro14MouseClicked

    private void cuadro16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro16MouseClicked
        escribirNumeroCuadro(1,6);
    }//GEN-LAST:event_cuadro16MouseClicked

    private void cuadro28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro28MouseClicked
        escribirNumeroCuadro(2,8);
    }//GEN-LAST:event_cuadro28MouseClicked

    private void cuadro34MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro34MouseClicked
        escribirNumeroCuadro(3,4);
    }//GEN-LAST:event_cuadro34MouseClicked

    private void cuadro51MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro51MouseClicked
       escribirNumeroCuadro(5,1);
    }//GEN-LAST:event_cuadro51MouseClicked

    private void cuadro80MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro80MouseClicked
        escribirNumeroCuadro(0,8);
    }//GEN-LAST:event_cuadro80MouseClicked

    private void cuadro50MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro50MouseClicked
        escribirNumeroCuadro(5,0);
    }//GEN-LAST:event_cuadro50MouseClicked

    private void cuadro22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro22MouseClicked
        escribirNumeroCuadro(2,2);
    }//GEN-LAST:event_cuadro22MouseClicked

    private void cuadro40MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro40MouseClicked
        escribirNumeroCuadro(4,0);
    }//GEN-LAST:event_cuadro40MouseClicked

    private void cuadro41MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro41MouseClicked
        escribirNumeroCuadro(4,1);
    }//GEN-LAST:event_cuadro41MouseClicked

    private void cuadro43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro43MouseClicked
        escribirNumeroCuadro(4,3);
    }//GEN-LAST:event_cuadro43MouseClicked

    private void cuadro44MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro44MouseClicked
        escribirNumeroCuadro(4,4);
    }//GEN-LAST:event_cuadro44MouseClicked

    private void cuadro46MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro46MouseClicked
        escribirNumeroCuadro(4,6);
    }//GEN-LAST:event_cuadro46MouseClicked

    private void cuadro45MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro45MouseClicked
        escribirNumeroCuadro(4,5);
    }//GEN-LAST:event_cuadro45MouseClicked

    private void cuadro47MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro47MouseClicked
        escribirNumeroCuadro(4,7);
    }//GEN-LAST:event_cuadro47MouseClicked

    private void cuadro53MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro53MouseClicked
        escribirNumeroCuadro(5,3);
    }//GEN-LAST:event_cuadro53MouseClicked

    private void cuadro52MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro52MouseClicked
        escribirNumeroCuadro(5,2);
    }//GEN-LAST:event_cuadro52MouseClicked

    private void cuadro54MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro54MouseClicked
        escribirNumeroCuadro(5,4);
    }//GEN-LAST:event_cuadro54MouseClicked

    private void cuadro55MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro55MouseClicked
        escribirNumeroCuadro(5,5);
    }//GEN-LAST:event_cuadro55MouseClicked

    private void cuadro56MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro56MouseClicked
        escribirNumeroCuadro(5,6);
    }//GEN-LAST:event_cuadro56MouseClicked

    private void cuadro57MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro57MouseClicked
        escribirNumeroCuadro(5,7);
    }//GEN-LAST:event_cuadro57MouseClicked

    private void cuadro58MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro58MouseClicked
        escribirNumeroCuadro(5,8);
    }//GEN-LAST:event_cuadro58MouseClicked

    private void cuadro63MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro63MouseClicked
        escribirNumeroCuadro(6,3);
    }//GEN-LAST:event_cuadro63MouseClicked

    private void cuadro62MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro62MouseClicked
        escribirNumeroCuadro(6,2);
    }//GEN-LAST:event_cuadro62MouseClicked

    private void cuadro61MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro61MouseClicked
       escribirNumeroCuadro(6,1);
    }//GEN-LAST:event_cuadro61MouseClicked

    private void cuadro65MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro65MouseClicked
        escribirNumeroCuadro(6,5);
    }//GEN-LAST:event_cuadro65MouseClicked

    private void cuadro72MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro72MouseClicked
        escribirNumeroCuadro(7,2);
    }//GEN-LAST:event_cuadro72MouseClicked

    private void cuadro64MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro64MouseClicked
        escribirNumeroCuadro(6,4);
    }//GEN-LAST:event_cuadro64MouseClicked

    private void cuadro74MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro74MouseClicked
        escribirNumeroCuadro(7,4);
    }//GEN-LAST:event_cuadro74MouseClicked

    private void cuadro71MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro71MouseClicked
        escribirNumeroCuadro(7,1);
    }//GEN-LAST:event_cuadro71MouseClicked

    private void cuadro70MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro70MouseClicked
        escribirNumeroCuadro(7,0);
    }//GEN-LAST:event_cuadro70MouseClicked

    private void cuadro67MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro67MouseClicked
         escribirNumeroCuadro(6,7);
    }//GEN-LAST:event_cuadro67MouseClicked

    private void cuadro66MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro66MouseClicked
        escribirNumeroCuadro(6,6);
    }//GEN-LAST:event_cuadro66MouseClicked

    private void cuadro86MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro86MouseClicked
        escribirNumeroCuadro(8,6);
    }//GEN-LAST:event_cuadro86MouseClicked

    private void cuadro68MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro68MouseClicked
        escribirNumeroCuadro(6,8);
    }//GEN-LAST:event_cuadro68MouseClicked

    private void cuadro78MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro78MouseClicked
        escribirNumeroCuadro(7,8);
    }//GEN-LAST:event_cuadro78MouseClicked

    private void cuadro60MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro60MouseClicked
        escribirNumeroCuadro(6,0);
    }//GEN-LAST:event_cuadro60MouseClicked

    private void cuadro82MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro82MouseClicked
        escribirNumeroCuadro(8,2);
    }//GEN-LAST:event_cuadro82MouseClicked

    private void cuadro81MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro81MouseClicked
        escribirNumeroCuadro(8,1);
    }//GEN-LAST:event_cuadro81MouseClicked

    private void cuadro83MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro83MouseClicked
        escribirNumeroCuadro(8,3);
    }//GEN-LAST:event_cuadro83MouseClicked

    private void cuadro85MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro85MouseClicked
        escribirNumeroCuadro(8,5);
    }//GEN-LAST:event_cuadro85MouseClicked

    private void cuadro84MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro84MouseClicked
        escribirNumeroCuadro(8,4);
    }//GEN-LAST:event_cuadro84MouseClicked

    private void cuadro87MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro87MouseClicked
        escribirNumeroCuadro(8,7);
    }//GEN-LAST:event_cuadro87MouseClicked

    private void cuadro88MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro88MouseClicked
        escribirNumeroCuadro(8,8);
    }//GEN-LAST:event_cuadro88MouseClicked

    private void cuadro17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro17MouseClicked
        escribirNumeroCuadro(1,7);
    }//GEN-LAST:event_cuadro17MouseClicked

    private void cuadro27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro27MouseClicked
        escribirNumeroCuadro(2,7);
    }//GEN-LAST:event_cuadro27MouseClicked

    private void cuadro38MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro38MouseClicked
        escribirNumeroCuadro(3,8);
    }//GEN-LAST:event_cuadro38MouseClicked

    private void cuadro48MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro48MouseClicked
        escribirNumeroCuadro(4,8);
    }//GEN-LAST:event_cuadro48MouseClicked

    private void cuadro76MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro76MouseClicked
        escribirNumeroCuadro(7,6);
    }//GEN-LAST:event_cuadro76MouseClicked

    private void cuadro08MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro08MouseClicked
        escribirNumeroCuadro(0,8);
    }//GEN-LAST:event_cuadro08MouseClicked

    private void cuadro77MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro77MouseClicked
        escribirNumeroCuadro(7,7);
    }//GEN-LAST:event_cuadro77MouseClicked

    private void cuadro75MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro75MouseClicked
        escribirNumeroCuadro(7,5);
    }//GEN-LAST:event_cuadro75MouseClicked

    private void cuadro73MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuadro73MouseClicked
        escribirNumeroCuadro(7,3);
    }//GEN-LAST:event_cuadro73MouseClicked

    private void boton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton6ActionPerformed
        cambiarColorBotonNum(boton6);
    }//GEN-LAST:event_boton6ActionPerformed

    private void boton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton7ActionPerformed
        cambiarColorBotonNum(boton7);
    }//GEN-LAST:event_boton7ActionPerformed

    private void boton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton8ActionPerformed
        cambiarColorBotonNum(boton8);
    }//GEN-LAST:event_boton8ActionPerformed

    private void boton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton9ActionPerformed
        cambiarColorBotonNum(boton9);
    }//GEN-LAST:event_boton9ActionPerformed

    private void posibleJuagdaBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_posibleJuagdaBotActionPerformed
        posibleJugada = miControlador.posibleJuagdaBoton(botonesNumeros, botonesPrincipales);
    }//GEN-LAST:event_posibleJuagdaBotActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Jugar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Jugar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Jugar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Jugar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            public void run() 
            {               
                new Jugar(false).setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelPrincipal;
    private javax.swing.JButton borrarJuegoBoton;
    private javax.swing.JButton borrarJugadaBot;
    private javax.swing.JButton boton1;
    private javax.swing.JButton boton2;
    private javax.swing.JButton boton3;
    private javax.swing.JButton boton4;
    private javax.swing.JButton boton5;
    private javax.swing.JButton boton6;
    private javax.swing.JButton boton7;
    private javax.swing.JButton boton8;
    private javax.swing.JButton boton9;
    private javax.swing.JButton cargarJuegoBoton;
    private javax.swing.JLabel cuadro00;
    private javax.swing.JLabel cuadro01;
    private javax.swing.JLabel cuadro02;
    private javax.swing.JLabel cuadro03;
    private javax.swing.JLabel cuadro04;
    private javax.swing.JLabel cuadro05;
    private javax.swing.JLabel cuadro06;
    private javax.swing.JLabel cuadro07;
    private javax.swing.JLabel cuadro08;
    private javax.swing.JLabel cuadro10;
    private javax.swing.JLabel cuadro11;
    private javax.swing.JLabel cuadro12;
    private javax.swing.JLabel cuadro13;
    private javax.swing.JLabel cuadro14;
    private javax.swing.JLabel cuadro15;
    private javax.swing.JLabel cuadro16;
    private javax.swing.JLabel cuadro17;
    private javax.swing.JLabel cuadro18;
    private javax.swing.JLabel cuadro20;
    private javax.swing.JLabel cuadro21;
    private javax.swing.JLabel cuadro22;
    private javax.swing.JLabel cuadro23;
    private javax.swing.JLabel cuadro24;
    private javax.swing.JLabel cuadro25;
    private javax.swing.JLabel cuadro26;
    private javax.swing.JLabel cuadro27;
    private javax.swing.JLabel cuadro28;
    private javax.swing.JLabel cuadro30;
    private javax.swing.JLabel cuadro31;
    private javax.swing.JLabel cuadro32;
    private javax.swing.JLabel cuadro33;
    private javax.swing.JLabel cuadro34;
    private javax.swing.JLabel cuadro35;
    private javax.swing.JLabel cuadro36;
    private javax.swing.JLabel cuadro37;
    private javax.swing.JLabel cuadro38;
    private javax.swing.JLabel cuadro40;
    private javax.swing.JLabel cuadro41;
    private javax.swing.JLabel cuadro42;
    private javax.swing.JLabel cuadro43;
    private javax.swing.JLabel cuadro44;
    private javax.swing.JLabel cuadro45;
    private javax.swing.JLabel cuadro46;
    private javax.swing.JLabel cuadro47;
    private javax.swing.JLabel cuadro48;
    private javax.swing.JLabel cuadro50;
    private javax.swing.JLabel cuadro51;
    private javax.swing.JLabel cuadro52;
    private javax.swing.JLabel cuadro53;
    private javax.swing.JLabel cuadro54;
    private javax.swing.JLabel cuadro55;
    private javax.swing.JLabel cuadro56;
    private javax.swing.JLabel cuadro57;
    private javax.swing.JLabel cuadro58;
    private javax.swing.JLabel cuadro60;
    private javax.swing.JLabel cuadro61;
    private javax.swing.JLabel cuadro62;
    private javax.swing.JLabel cuadro63;
    private javax.swing.JLabel cuadro64;
    private javax.swing.JLabel cuadro65;
    private javax.swing.JLabel cuadro66;
    private javax.swing.JLabel cuadro67;
    private javax.swing.JLabel cuadro68;
    private javax.swing.JLabel cuadro70;
    private javax.swing.JLabel cuadro71;
    private javax.swing.JLabel cuadro72;
    private javax.swing.JLabel cuadro73;
    private javax.swing.JLabel cuadro74;
    private javax.swing.JLabel cuadro75;
    private javax.swing.JLabel cuadro76;
    private javax.swing.JLabel cuadro77;
    private javax.swing.JLabel cuadro78;
    private javax.swing.JLabel cuadro80;
    private javax.swing.JLabel cuadro81;
    private javax.swing.JLabel cuadro82;
    private javax.swing.JLabel cuadro83;
    private javax.swing.JLabel cuadro84;
    private javax.swing.JLabel cuadro85;
    private javax.swing.JLabel cuadro86;
    private javax.swing.JLabel cuadro87;
    private javax.swing.JLabel cuadro88;
    private javax.swing.JLabel etiquetaTiempo;
    private javax.swing.JButton guardarJuegoBoton;
    private javax.swing.JButton iniciarJuegobot;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel label00A;
    private javax.swing.JLabel label00D;
    private javax.swing.JLabel label01A;
    private javax.swing.JLabel label01D;
    private javax.swing.JLabel label02A;
    private javax.swing.JLabel label02D;
    private javax.swing.JLabel label03A;
    private javax.swing.JLabel label03D;
    private javax.swing.JLabel label04A;
    private javax.swing.JLabel label04A6;
    private javax.swing.JLabel label04D;
    private javax.swing.JLabel label05A;
    private javax.swing.JLabel label05D;
    private javax.swing.JLabel label06A;
    private javax.swing.JLabel label06D;
    private javax.swing.JLabel label07A;
    private javax.swing.JLabel label07D;
    private javax.swing.JLabel label08A;
    private javax.swing.JLabel label10A;
    private javax.swing.JLabel label10D;
    private javax.swing.JLabel label11A;
    private javax.swing.JLabel label11D;
    private javax.swing.JLabel label12A;
    private javax.swing.JLabel label12D;
    private javax.swing.JLabel label13A;
    private javax.swing.JLabel label13D;
    private javax.swing.JLabel label14A;
    private javax.swing.JLabel label14D;
    private javax.swing.JLabel label15A;
    private javax.swing.JLabel label15D;
    private javax.swing.JLabel label16A;
    private javax.swing.JLabel label16D;
    private javax.swing.JLabel label17A;
    private javax.swing.JLabel label17D;
    private javax.swing.JLabel label18A;
    private javax.swing.JLabel label20A;
    private javax.swing.JLabel label20D;
    private javax.swing.JLabel label21A;
    private javax.swing.JLabel label21D;
    private javax.swing.JLabel label22A;
    private javax.swing.JLabel label22D;
    private javax.swing.JLabel label23A;
    private javax.swing.JLabel label23D;
    private javax.swing.JLabel label24A;
    private javax.swing.JLabel label24D;
    private javax.swing.JLabel label25A;
    private javax.swing.JLabel label25D;
    private javax.swing.JLabel label26A;
    private javax.swing.JLabel label26D;
    private javax.swing.JLabel label27A;
    private javax.swing.JLabel label27D;
    private javax.swing.JLabel label28A;
    private javax.swing.JLabel label30A;
    private javax.swing.JLabel label30D;
    private javax.swing.JLabel label31A;
    private javax.swing.JLabel label31D;
    private javax.swing.JLabel label32A;
    private javax.swing.JLabel label32D;
    private javax.swing.JLabel label33A;
    private javax.swing.JLabel label33D;
    private javax.swing.JLabel label34A;
    private javax.swing.JLabel label34D;
    private javax.swing.JLabel label35A;
    private javax.swing.JLabel label35D;
    private javax.swing.JLabel label36A;
    private javax.swing.JLabel label36D;
    private javax.swing.JLabel label37A;
    private javax.swing.JLabel label37D;
    private javax.swing.JLabel label38A;
    private javax.swing.JLabel label40A;
    private javax.swing.JLabel label40D;
    private javax.swing.JLabel label41A;
    private javax.swing.JLabel label41D;
    private javax.swing.JLabel label42A;
    private javax.swing.JLabel label42D;
    private javax.swing.JLabel label43A;
    private javax.swing.JLabel label43D;
    private javax.swing.JLabel label44A;
    private javax.swing.JLabel label44D;
    private javax.swing.JLabel label45A;
    private javax.swing.JLabel label45D;
    private javax.swing.JLabel label46A;
    private javax.swing.JLabel label46D;
    private javax.swing.JLabel label47A;
    private javax.swing.JLabel label47D;
    private javax.swing.JLabel label48A;
    private javax.swing.JLabel label50A;
    private javax.swing.JLabel label50D;
    private javax.swing.JLabel label51A;
    private javax.swing.JLabel label51D;
    private javax.swing.JLabel label52A;
    private javax.swing.JLabel label52D;
    private javax.swing.JLabel label53A;
    private javax.swing.JLabel label53D;
    private javax.swing.JLabel label54A;
    private javax.swing.JLabel label54D;
    private javax.swing.JLabel label55A;
    private javax.swing.JLabel label55D;
    private javax.swing.JLabel label56A;
    private javax.swing.JLabel label56D;
    private javax.swing.JLabel label57A;
    private javax.swing.JLabel label57D;
    private javax.swing.JLabel label58A;
    private javax.swing.JLabel label60A;
    private javax.swing.JLabel label60D;
    private javax.swing.JLabel label61A;
    private javax.swing.JLabel label61D;
    private javax.swing.JLabel label62A;
    private javax.swing.JLabel label62D;
    private javax.swing.JLabel label63A;
    private javax.swing.JLabel label63D;
    private javax.swing.JLabel label64A;
    private javax.swing.JLabel label64D;
    private javax.swing.JLabel label65A;
    private javax.swing.JLabel label65D;
    private javax.swing.JLabel label66A;
    private javax.swing.JLabel label66D;
    private javax.swing.JLabel label67A;
    private javax.swing.JLabel label67D;
    private javax.swing.JLabel label68A;
    private javax.swing.JLabel label70A;
    private javax.swing.JLabel label70D;
    private javax.swing.JLabel label71A;
    private javax.swing.JLabel label71D;
    private javax.swing.JLabel label72A;
    private javax.swing.JLabel label72D;
    private javax.swing.JLabel label73A;
    private javax.swing.JLabel label73D;
    private javax.swing.JLabel label74A;
    private javax.swing.JLabel label74D;
    private javax.swing.JLabel label75A;
    private javax.swing.JLabel label75D;
    private javax.swing.JLabel label76A;
    private javax.swing.JLabel label76D;
    private javax.swing.JLabel label77A;
    private javax.swing.JLabel label77D;
    private javax.swing.JLabel label78A;
    private javax.swing.JLabel label80D;
    private javax.swing.JLabel label81D;
    private javax.swing.JLabel label82D;
    private javax.swing.JLabel label83D;
    private javax.swing.JLabel label84D;
    private javax.swing.JLabel label85D;
    private javax.swing.JLabel label86D;
    private javax.swing.JLabel label87D;
    private javax.swing.JLabel labelNivel;
    private javax.swing.JTextPane nombreJugador;
    private javax.swing.JButton posibleJuagdaBot;
    private javax.swing.JButton rehacerBoton;
    private javax.swing.JButton salirJuegoBoton;
    private javax.swing.JButton terminaJuegoBoton;
    private javax.swing.JButton top10Boton;
    // End of variables declaration//GEN-END:variables
}
