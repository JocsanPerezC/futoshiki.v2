package controlador;

import interfaces.Jugar;

import logica.Cuadro;
import logica.FuncionesListaEnlazada;
import logica.GuardarPartida;
import logica.JUEGO;
import logica.Partida;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.xml.transform.TransformerException;
import javax.swing.table.DefaultTableModel;

public class controlador
{   
    public int cambiarColorBotonNum(JButton pBoton, JButton[] pBotones, Timer pTimer)
    {  
        return JUEGO.claseJuego.cambiarColorBotonNum(pBoton, pBotones, pTimer);       
    }
    
    public void setBordeRojo(JLabel label)
    {    
        JUEGO.claseJuego.setBordeRojo(label);
    }
    
    public void setBordeNegro(JLabel label)
    {       
        JUEGO.claseJuego.setBordeNegro(label);
    }
    
    public void setConstantes(JLabel[][] matrizCuadros, JLabel matrizLabels[][][])
    {     
        JUEGO.claseJuego.setConstantes(matrizCuadros, matrizLabels);
    }
    
    public void actualizarLabel(int horas, int min, int sec, int cs, JLabel etiquetaTiempo) 
    {
       JUEGO.claseJuego.actualizarLabel(horas, min, sec, cs, etiquetaTiempo);
    }
    
    public void ganoPartida(JLabel matrizCuadros[][],
            Jugar partida, JTextPane nombreJugador, int horas, int min, int sec)
    {
        JUEGO.claseJuego.ganoPartida(matrizCuadros, nombreJugador, horas, min, sec, partida);
    }
    
    public void guardarNombreTiempo(JTextPane nombreJugador, int horas, int min, int sec)
    {
        JUEGO.claseJuego.guardarNombreTiempo(nombreJugador, horas, min, sec);
    }
    
    public void escribirNumeroCuadro(int fila, int columna, boolean posibleJugada,
            ArrayList<String> ListaJugadasPosi, int numeroJugada, JLabel matrizCuadros[][],
            FuncionesListaEnlazada<String> listaJugadasHechas, ArrayList<Cuadro> listaMatrizCuadros,
            JButton[] pBotonesNum, JButton[] botonesPrincipales, ArrayList<JLabel> listaCuadros,
            Jugar partida, JTextPane nombreJugador, int horas, int min, int sec)
    {         
        JUEGO.claseJuego.escribirNumeroCuadro(fila, columna, posibleJugada, ListaJugadasPosi, 
                numeroJugada, matrizCuadros, listaJugadasHechas, listaMatrizCuadros, pBotonesNum, 
                botonesPrincipales, listaCuadros, partida, nombreJugador, horas, min, sec);
    }
    
    public void borrarJugadaBoton(FuncionesListaEnlazada<String> listaJugadasHechas,
            JButton rehacerBoton, ArrayList<Cuadro> listaMatrizCuadros, ArrayList<Cuadro>
            listaMatrizCuadrosRehacer, ArrayList<JLabel> listaCuadros, 
            ArrayList<JLabel> listaCuadrosRehacer) 
    {                                                
        JUEGO.claseJuego.borrarJugadaBoton(listaJugadasHechas, rehacerBoton, listaMatrizCuadros,
                listaMatrizCuadrosRehacer, listaCuadros, listaCuadrosRehacer);
    }  
    
    public void top10Boton(Timer t) 
    {                                           
        JUEGO.claseJuego.top10Boton(t);     
    }   
    
    public void listarTop10(ArrayList<String> PersonasMasInfo, ArrayList<Integer> MejoresTiempos, DefaultTableModel dificilAux, DefaultTableModel intermedioAux, DefaultTableModel facilAux)
    {
        JUEGO.claseJuego.listaTOP10(PersonasMasInfo, MejoresTiempos, dificilAux, intermedioAux, facilAux);
    }
    
    public void rehacerBoton(JButton rehacerBoton, ArrayList<Cuadro>
            listaMatrizCuadrosRehacer, ArrayList<JLabel> listaCuadrosRehacer)
    {                                                   
        JUEGO.claseJuego.rehacerBoton(rehacerBoton, listaMatrizCuadrosRehacer, listaCuadrosRehacer);
    }      
                
    public void borrarJuegoBoton(Timer t, JLabel[][] matrizCuadros, JLabel matrizLabels[][][])
    {                                                        
        JUEGO.claseJuego.borrarJuegoBoton(t, matrizCuadros, matrizLabels);
    }     
    
    public void iniciarJuegoboton(JTextPane nombreJugador, JButton[] botonesPrincipales, JButton[]
            botonesNum, Timer t) 
    {                                                      
        JUEGO.claseJuego.iniciarJuegoboton(nombreJugador, botonesPrincipales, botonesNum, t);
    }  
    
    public void terminaJuegoBoton(Timer t, Jugar ventana) 
    {                                                        
        JUEGO.claseJuego.terminaJuegoBoton(t, ventana);
    }     
    
    public void cargarJuegoBoton(Jugar ventana) 
    {                                                         
       JUEGO.claseJuego.cargarJuegoBoton(ventana);
    }    
    
    public void guardarJuegoBoton(int horas, int min, int sec, int cs, Partida partidaActual,
            JTextPane nombreJugador)
    {                                                         
        JUEGO.claseJuego.guardarJuegoBoton(horas, min, sec, cs, partidaActual, nombreJugador);
    }       
    
    public void salirJuegoBoton(Jugar ventana) 
    {                                                
        JUEGO.claseJuego.salirJuegoBoton(ventana);
    }     
    
    public boolean posibleJuagdaBoton(JButton[] pBotonesNum, JButton[] botonesPrincipales) {                                                 
        
        return JUEGO.claseJuego.posibleJuagdaBoton(pBotonesNum, botonesPrincipales);
    }   
    
    public void setDificultad(String dificultad)
    {
        JUEGO.claseJuego.setDificultad(dificultad);
    }
    
    public void setTimer(String timer)
    {
        JUEGO.claseJuego.setTiempo(timer);
    }
    
    public void setPosicion(String posicion)
    {
        JUEGO.claseJuego.setPosicion(posicion);
    }
    
    public void setTamaño(int tamaño)
    {
        JUEGO.claseJuego.setTamañoCuadricula(tamaño);
    }
    
    public void setTiempo(String horas, String min, String sec)
    {
        JUEGO.claseJuego.setTiempo(horas, min, sec);
    }
    
    public void abreManual()
    {
        JUEGO.claseJuego.abrirManual();
    }
}
