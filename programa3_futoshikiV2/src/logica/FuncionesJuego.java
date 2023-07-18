package logica;

import interfaces.Jugar;
import interfaces.MenuPrincipal;
import interfaces.Top10;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.xml.transform.TransformerException;

/**
 * Este es la clase FuncionesJuego donde se van a crear y llamar las funciones necesarias para que funcione el juego
 * @author Kendall Rodríguez Camacho 2022049438 y Jocsan Adriel Perez Coto 2022437948 
 */
public class FuncionesJuego 
{
    private static Partida partidaActual;
    
    private static XML xml = new XML();
    
    private static ArrayList<Partida> listaPartidas = xml.getPartidas();
    
    private int tamañoCuadricula = 5;
     
    private static int indicadorNivel = 0;
    
    public Cuadro listaCuadros[][] = new Cuadro[9][9];
    
    public String dificultad, tiempo, posicion, horas, min, sec;
    
    /**
     * Metodo constructor de FuncionesJuego
     */
    public FuncionesJuego()
    {       
        dificultad = "";
        tiempo = "";
        posicion = "";   
        
        horas = "";
        min = "";
        sec = ""; 
        
        
    }
    
    /**
     * Funcion para obtener la lista de cuadros
     * @return lista de cuadros
     */
    public Cuadro [][] getListaCuadros(){        
        return listaCuadros;
    }
    
    /**
     * Funcion para cambiar los colores de los botones, coloca un boton diferente
     * @param pBoton (JButton)
     * @param pBotones (JButton[])
     * @param pTimer
     * @return NumeroJugada
     */ 
    public int cambiarColorBotonNum(JButton pBoton, JButton[] pBotones, Timer pTimer){
        
        int contador = 0;
        int numeroJugada = 0;
        
        for(JButton boton: pBotones){
            
            contador ++;
            
            if(boton == pBoton){
                
                boton.setBackground(Color.green);
                numeroJugada = contador;
                continue;
            }
            
            boton.setBackground(Color.lightGray);
            
        }
        
        pTimer.start();
        
        return numeroJugada;
        
    }
    
    /**
     * Funcion para cambiar el borde de un cuadro de color rojo
     * @param label (JLabel)
    */    
    public void setBordeRojo(JLabel label){
        
        label.setBorder(BorderFactory.createMatteBorder(
                                   2, 2, 2, 2, Color.red));
        
    }
    
    /**
     * Funcion para cambiar el borde de un cuadro de color negro
     * @param label (JLabel)
    */   
    public void setBordeNegro(JLabel label){
        
        label.setBorder(BorderFactory.createMatteBorder(
                                   2, 2, 2, 2, Color.black));
        
    }
    
    /**
     * Funcion para ingresar las constantes en la cuadricula
     * @param matrizCuadros (JLabel[][])
     * * @param matrizLabels (JLabel[][][])
    */  
    public void setConstantes(JLabel matrizCuadros[][], JLabel matrizLabels[][][]){
      
        int num;
        String simbolo;
        for(int i = 0; i < 9; i ++){
            
            for(int e = 0; e < 9; e ++){
                num = getListaCuadros()[i][e].getNum();
                if(num == 0) matrizCuadros[i][e].setText("");
                else matrizCuadros[i][e].setText(Integer.toString(num));
                simbolo = getListaCuadros()[i][e].getSimbolo1();
             
                if(simbolo != null &&  matrizLabels[i][e][0] != null)
                    matrizLabels[i][e][0].setText(simbolo);
               
                simbolo = getListaCuadros()[i][e].getSimbolo2();
                if(simbolo != null && matrizLabels[i][e][1] != null) 
                    matrizLabels[i][e][1].setText(simbolo);
            }
        }
    }
    
    /**
     * Funcion para actualizar labels
     * @param horas (int)
     * @param min (int)
     * @param sec (int)
     * @param cs (int)
     * @param etiquetaTiempo (JLabel)
    */   
    public void actualizarLabel(int horas, int min, int sec, int cs, JLabel etiquetaTiempo) 
    {
        String tiempo = (horas <= 9?"0":"")+ horas + ":"+ (min<=9?"0":"")+ min+":"+(sec<=9?"0":"")+sec+":"+(cs<=9?"0":"")+cs;
        etiquetaTiempo.setText(tiempo);
    }
    
    /**
     * Metodo para verificar si ganó la partida
     * @param matrizCuadros JLabel[][])
     * @param nombreJugador (JLabel)
     * @param horas (int)
     * @param min (int)
     * @param sec (int)
     * @param partida (Jugar)
    */   
    public void ganoPartida(JLabel matrizCuadros[][], JTextPane nombreJugador, int horas, int min, int sec,
            Jugar partida)
    {
        for (int i = 0; i < matrizCuadros.length; i++)
        {   
            for (int x = 0; x < matrizCuadros[i].length; x++)
            {
                if(matrizCuadros[i][x].getText().equals("") && (i < tamañoCuadricula && x  < tamañoCuadricula))
                {
                    return;
                }
            }
        }
        //significa que todos los cuadros han sido llenados
        JOptionPane.showMessageDialog(null, "Felicidades, Ha ganado la partida");
        guardarNombreTiempo(nombreJugador, horas, min, sec);
        
        if(getIndicadorNivel() > 0)
        {       
            setIndicadorNivel();
            iniciarNuevaPartida();
            return;
        }
           
        partida.dispose();
        MenuPrincipal nuevoMenu = new MenuPrincipal();
        nuevoMenu.setVisible(true);     
    }
    
    public static void iniciarNuevaPartida()
    {       
        Jugar nuevo = new Jugar(false);     
        nuevo.setVisible(true);
    }
    
    /**
     * Metodo para guardar el tiempo en un archivo .dat
     * @param nombreJugador (JTextPane)
     * @param horas (int)
     * @param min (int)
     * @param sec (int)
    */  
    public void guardarNombreTiempo(JTextPane nombreJugador, int horas, int min, int sec)
    {
        if(JUEGO.claseJuego.getTiempo().equals("Timer"))
        {
            return;
        }
        
        if (JUEGO.claseJuego.getDificultad().equals("Dificil"))
        {
            File archivoD = new File("TiemposDificil.dat");

            JUEGO.claseJuego.crearArchivoTop10(archivoD);//si esta creado, no se vuelve a crear

            String tiempoTotal = String.valueOf(horas) + String.valueOf(min) + String.valueOf(sec);    

            JUEGO.claseJuego.guardarArchivoTop10(archivoD, nombreJugador.getText(), tiempoTotal, horas, min, sec);//guarda el nombre y tiempo 

        }
        if (JUEGO.claseJuego.getDificultad().equals("Intermedio"))
        {
            File archivoD = new File("TiemposIntermedio.dat");

            JUEGO.claseJuego.crearArchivoTop10(archivoD);//si esta creado, no se vuelve a crear

            String tiempoTotal = String.valueOf(horas) + String.valueOf(min) + String.valueOf(sec);    

            JUEGO.claseJuego.guardarArchivoTop10(archivoD, nombreJugador.getText(), tiempoTotal, horas, min, sec);//guarda el nombre y tiempo 

        }
        if (JUEGO.claseJuego.getDificultad().equals("Facil"))
        {
            File archivoD = new File("TiemposFacil.dat");

            JUEGO.claseJuego.crearArchivoTop10(archivoD);//si esta creado, no se vuelve a crear

            String tiempoTotal = String.valueOf(horas) + String.valueOf(min) + String.valueOf(sec);    

            JUEGO.claseJuego.guardarArchivoTop10(archivoD, nombreJugador.getText(), tiempoTotal, horas, min, sec);//guarda el nombre y tiempo 

        }
    }

    /**
     * Metodo para escribir un numero en un cuadro
     * @param fila
     * @param columna
     * @param posibleJugada
     * @param ListaJugadasPosi
     * @param numeroJugada
     * @param matrizCuadros
     * @param listaJugadasHechas
     * @param listaMatrizCuadros
     * @param pBotonesNum
     * @param botonesPrincipales
     * @param pListaCuadros
     * @param partida
     * @param nombreJugador
     * @param horas
     * @param min
     * @param sec 
     */
    public void escribirNumeroCuadro(int fila, int columna, boolean posibleJugada,
            ArrayList<String> ListaJugadasPosi, int numeroJugada, JLabel matrizCuadros[][],
            FuncionesListaEnlazada<String> listaJugadasHechas, ArrayList<Cuadro> listaMatrizCuadros,
            JButton[] pBotonesNum, JButton[] botonesPrincipales, ArrayList<JLabel> pListaCuadros,
            Jugar partida, JTextPane nombreJugador, int horas, int min, 
            int sec){
        
        if (posibleJugada == true)//el boton de posible jugada esta activo
        {
            int numero = JUEGO.claseJuego.getTamañoCuadricula();
            
            JUEGO.claseJuego.verificaJugadas(ListaJugadasPosi, numero, fila, columna);
            
            ListaJugadasPosi.clear();
            
            botonesPrincipales[7].setEnabled(true);
            botonesPrincipales[1].setEnabled(true);
            botonesPrincipales[6].setEnabled(true);
            botonesPrincipales[9].setEnabled(true);
            botonesPrincipales[2].setEnabled(true);
            
            for(JButton boton: pBotonesNum){
                
                boton.setEnabled(true);
            }
            
            posibleJugada = false;
                       
            return;
        }
         
        if(numeroJugada == 0)//para que no de error antes de presionar los botones
        {
            return;
        }
           
        if(matrizCuadros[fila][columna].getText().equals(""))
        {   
            for(JButton boton: pBotonesNum){
                        
                boton.setBackground(Color.lightGray);
            }
            
            if(JUEGO.claseJuego.verificaColumna(numeroJugada, columna) == true)//numero, columna
            {
                if(JUEGO.claseJuego.verificaFila(numeroJugada, fila) == true)//numero, fila
                {
                    if(! JUEGO.claseJuego.verificaDesigualdad2(numeroJugada, fila, columna, true)){

                        setBordeRojo(matrizCuadros[fila][columna]);
                        JOptionPane.showMessageDialog(null, "La desigualdad no se cumple");
                        setBordeNegro(matrizCuadros[fila][columna]);
                        return;
                    }

                    if(! JUEGO.claseJuego.verificaDesigualdad2(numeroJugada, fila, columna, false)){

                        setBordeRojo(matrizCuadros[fila][columna]);
                        JOptionPane.showMessageDialog(null, "La desigualdad no se cumple");
                        setBordeNegro(matrizCuadros[fila][columna]);
                        return;
                    }

                    JUEGO.claseJuego.insertarNum(numeroJugada, fila, columna);

                    String num = String.valueOf(numeroJugada);
                    matrizCuadros[fila][columna].setText(num);

                    pListaCuadros.add(matrizCuadros[fila][columna]);//se guarda el cuadro

                    listaJugadasHechas.push(num);//se guarda el numero que fue jugado

                    Cuadro cuadrito = new Cuadro(numeroJugada, "", "", fila, columna);//guarda el numero, la fila y la columna
                    listaMatrizCuadros.add(cuadrito);
                    
                    ganoPartida(matrizCuadros, nombreJugador, horas, min, sec, partida);
                    return;
                }
                setBordeRojo(matrizCuadros[fila][columna]);
                JOptionPane.showMessageDialog(null, "El número ya fue escrito en la fila");
                setBordeNegro(matrizCuadros[fila][columna]);
                return;
            }
            setBordeRojo(matrizCuadros[fila][columna]);
            JOptionPane.showMessageDialog(null, "El número ya fue escrito en la fila");
            setBordeNegro(matrizCuadros[fila][columna]);

        }
    }
    
    /**
     * Metodo para borrar una jugada
     * @param listaJugadasHechas
     * @param rehacerBoton
     * @param listaMatrizCuadros
     * @param listaMatrizCuadrosRehacer
     * @param listaCuadros
     * @param listaCuadrosRehacer 
     */    
    public void borrarJugadaBoton(FuncionesListaEnlazada<String> listaJugadasHechas,
            JButton rehacerBoton, ArrayList<Cuadro> listaMatrizCuadros, ArrayList<Cuadro>
            listaMatrizCuadrosRehacer, ArrayList<JLabel> listaCuadros, 
            ArrayList<JLabel> listaCuadrosRehacer) {                                                

        if(listaJugadasHechas.empty() == true)//por si esta vacia la lista
        {
            JOptionPane.showMessageDialog(null, "No hay jugadas por borrar");
            return;
        }
        
        rehacerBoton.setEnabled(true);
       
        //Internamente en la matriz del juego
        //---------------------Elimina dentro de la matriz interna-------------------------
        int ultimo1 = listaMatrizCuadros.size() - 1; //obtiene el ultimo del indice

        int fila = listaMatrizCuadros.get(ultimo1).getFila();
        int columna = listaMatrizCuadros.get(ultimo1).getColumna();

        listaMatrizCuadrosRehacer.add(listaMatrizCuadros.get(ultimo1)); //para luego rehacerlo

        listaMatrizCuadros.remove(ultimo1);//elimina el ultimo cuadro utilizado

        JUEGO.claseJuego.insertarNum(0, fila, columna);//para hacer la matriz de nuevo en 0

        //Externamente en la matriz del juego
        //--------------------Elimina y hace push de la lista de los cuadros utilizados------

        int ultimo2 = listaCuadros.size() - 1; //obtiene el ultimo del indice

        listaCuadros.get(ultimo2).setText("");//pone el cuadro en 0

        listaCuadrosRehacer.add(listaCuadros.get(ultimo2));//se agrega para rehacerlo

        listaCuadros.remove(ultimo2);

        listaJugadasHechas.pop();
    } 
    
    /**
     * Metodo para mostrar el top 10
     * @param t 
     */
    public void top10Boton(Timer t) 
    {            
        t.stop();
        Top10 top10 = new Top10();            
        top10.setVisible(true);       
    }   
    
    /**
     * Metodo para llamar las funciones de listar los mejores top 10 
     * @param PersonasMasInfo lista que va a contener la informacion de las personas
     * @param MejoresTiempos lista que contiene los mejores tiempos de las personas
     * @param dificilAux tabla del top 10 difiicl
     * @param intermedioAux tabla del top 10 intermedio
     * @param facilAux  tabla del top 10 facil
     */
    public void listaTOP10(ArrayList<String> PersonasMasInfo, ArrayList<Integer> MejoresTiempos, 
            DefaultTableModel dificilAux, DefaultTableModel intermedioAux, DefaultTableModel facilAux)
    {
        listarDificil(PersonasMasInfo, MejoresTiempos, dificilAux);
        listarIntermedio(PersonasMasInfo, MejoresTiempos, intermedioAux);
        listarFacil(PersonasMasInfo, MejoresTiempos, facilAux);       
    }

    /**
     * Funcion para crear un top 10 de los mejores tiempos en dificultad diicil
     * @param PersonasMasInfo (ArrayList)
     * @param MejoresTiempos (ArrayList)
     * @param dificilAux (tabla)
     */
    public void listarDificil(ArrayList<String> PersonasMasInfo, ArrayList<Integer> MejoresTiempos, DefaultTableModel dificilAux )
    {             
        File archivoD = new File("TiemposDificil.dat");
             
        PersonasMasInfo = JUEGO.claseJuego.leerArchivoTop10(archivoD, PersonasMasInfo);//se crea una lista (a base del archivo guardado) con todos los tiempos y el nombre de las personas

        MejoresTiempos = JUEGO.claseJuego.obtenerMejorTop10(MejoresTiempos, PersonasMasInfo);//se crea una lista con los mejores tiempos ordenados
              
        dificilAux.setRowCount(0);
        String [] info = new String [2];
        
        String tiempoTotal;
        
        int posicion = 1;
        for (Integer mejor: MejoresTiempos)//lista con los mejores tiempos oirdenados del menor al mayor
        {
            int tiempoTotalLista = 1;

            for(int g = 1; g <= PersonasMasInfo.size(); g++)//para hacer el ciclo en la lista con el elemento de arriba, se busca ese elemento en las posiciones de la lista de personas
            {
                String indiceT = PersonasMasInfo.get(tiempoTotalLista);//obtengo el elemento que esta en indice del tiempo en la lista de toda la informacion
                indiceT = indiceT.replaceAll("^0+", "");//se quitan los 0 ya que daba un problema

                if(String.valueOf(mejor).equals(indiceT))//significa que coinciden los tiempos del mejor con el que se busca en la lista de personas
                {
                    //horas, minutos, segundos
                    tiempoTotal =  PersonasMasInfo.get(tiempoTotalLista + 1) + " : " + PersonasMasInfo.get(tiempoTotalLista + 2) + " : " +  PersonasMasInfo.get(tiempoTotalLista + 3);

                    String nombreP = "" + posicion + "- ";//para la impresion en la tabla
                    nombreP += PersonasMasInfo.get(tiempoTotalLista - 1);//nombre en la lista
      
                    info[0] = nombreP;
                    info[1] = tiempoTotal; 

                    dificilAux.addRow(info);
                    
                    posicion ++;
                    break;//vuelve a buscar con el siguiente mejor tiempo
                }
                tiempoTotalLista ++;
                tiempoTotalLista ++;
                tiempoTotalLista ++;
                tiempoTotalLista ++;
                tiempoTotalLista ++;
            }
        }
        MejoresTiempos.clear();
        PersonasMasInfo.clear();
        
    }
    
    /**
     * Funcion para crear un top 10 de los mejores tiempos en dificultad Intermedio
     * @param PersonasMasInfo (ArrayList)
     * @param MejoresTiempos (ArrayList)
     * @param intermedioAux (tabla)
     */
    public void listarIntermedio(ArrayList<String> PersonasMasInfo, ArrayList<Integer> MejoresTiempos, DefaultTableModel intermedioAux)
    {
        File archivoD = new File("TiemposIntermedio.dat");
             
        PersonasMasInfo = JUEGO.claseJuego.leerArchivoTop10(archivoD, PersonasMasInfo);//se crea una lista (a base del archivo guardado) con todos los tiempos y el nombre de las personas

        MejoresTiempos = JUEGO.claseJuego.obtenerMejorTop10(MejoresTiempos, PersonasMasInfo);//se crea una lista con los mejores tiempos ordenados
              
        intermedioAux.setRowCount(0);
        String [] info = new String [2];
        
        String tiempoTotal;
        
        int posicion = 1;
        for (Integer mejor: MejoresTiempos)//lista con los mejores tiempos oirdenados del menor al mayor
        {
            int tiempoTotalLista = 1;

            for(int g = 1; g <= PersonasMasInfo.size(); g++)//para hacer el ciclo en la lista con el elemento de arriba, se busca ese elemento en las posiciones de la lista de personas
            {
                String indiceT = PersonasMasInfo.get(tiempoTotalLista);//obtengo el elemento que esta en indice del tiempo en la lista de toda la informacion
                indiceT = indiceT.replaceAll("^0+", "");//se quitan los 0 ya que daba un problema

                if(String.valueOf(mejor).equals(indiceT))//significa que coinciden los tiempos del mejor con el que se busca en la lista de personas
                {
                    //horas, minutos, segundos
                    tiempoTotal =  PersonasMasInfo.get(tiempoTotalLista + 1) + " : " + PersonasMasInfo.get(tiempoTotalLista + 2) + " : " +  PersonasMasInfo.get(tiempoTotalLista + 3);

                    String nombreP = "" + posicion + "- ";//para la impresion en la tabla
                    nombreP += PersonasMasInfo.get(tiempoTotalLista - 1);//nombre en la lista

                    info[0] = nombreP;
                    info[1] = tiempoTotal; 

                    intermedioAux.addRow(info);
                    
                    posicion ++;
                    break;//vuelve a buscar con el siguiente mejor tiempo
                }
                tiempoTotalLista ++;
                tiempoTotalLista ++;
                tiempoTotalLista ++;
                tiempoTotalLista ++;
                tiempoTotalLista ++;
            }
        }  
        MejoresTiempos.clear();
        PersonasMasInfo.clear();
    }
    
    /**
     * Funcion para crear un top 10 de los mejores tiempos en dificultad Facil
     * @param PersonasMasInfo (ArrayList)
     * @param MejoresTiempos (ArrayList)
     * @param facilAux (tabla)
     */
    public void listarFacil(ArrayList<String> PersonasMasInfo, ArrayList<Integer> MejoresTiempos, DefaultTableModel facilAux)
    {
        File archivoD = new File("TiemposFacil.dat");
             
        PersonasMasInfo = JUEGO.claseJuego.leerArchivoTop10(archivoD, PersonasMasInfo);//se crea una lista (a base del archivo guardado) con todos los tiempos y el nombre de las personas

        MejoresTiempos = JUEGO.claseJuego.obtenerMejorTop10(MejoresTiempos, PersonasMasInfo);//se crea una lista con los mejores tiempos ordenados
              
        facilAux.setRowCount(0);
        String [] info = new String [2];
        
        String tiempoTotal;
        
        int posicion = 1;
        for (Integer mejor: MejoresTiempos)//lista con los mejores tiempos oirdenados del menor al mayor
        {
            int tiempoTotalLista = 1;

            for(int g = 1; g <= PersonasMasInfo.size(); g++)//para hacer el ciclo en la lista con el elemento de arriba, se busca ese elemento en las posiciones de la lista de personas
            {
                String indiceT = PersonasMasInfo.get(tiempoTotalLista);//obtengo el elemento que esta en indice del tiempo en la lista de toda la informacion
                indiceT = indiceT.replaceAll("^0+", "");//se quitan los 0 ya que daba un problema

                if(String.valueOf(mejor).equals(indiceT))//significa que coinciden los tiempos del mejor con el que se busca en la lista de personas
                {
                    //horas, minutos, segundos
                    tiempoTotal =  PersonasMasInfo.get(tiempoTotalLista + 1) + " : " + PersonasMasInfo.get(tiempoTotalLista + 2) + " : " +  PersonasMasInfo.get(tiempoTotalLista + 3);
                    
                    String nombreP = "" + posicion + "- ";//para la impresion en la tabla
                    nombreP += PersonasMasInfo.get(tiempoTotalLista - 1);//nombre en la lista


                    info[0] = nombreP;
                    info[1] = tiempoTotal; 

                    facilAux.addRow(info);
                    
                    posicion ++;
                    break;//vuelve a buscar con el siguiente mejor tiempo
                }
                tiempoTotalLista ++;
                tiempoTotalLista ++;
                tiempoTotalLista ++;
                tiempoTotalLista ++;
                tiempoTotalLista ++;
            }
        } 
        MejoresTiempos.clear();
        PersonasMasInfo.clear();
    }
      
    /**
     * Metodo para rehacer una jugada
    */  
    public void rehacerBoton(JButton rehacerBoton, ArrayList<Cuadro>
            listaMatrizCuadrosRehacer, ArrayList<JLabel> listaCuadrosRehacer) 
    {                                             
        rehacerBoton.setEnabled(false);

        //--------------- Internamente dentro de la matriz del juego
        int ultimo1 = listaMatrizCuadrosRehacer.size() - 1; //obtiene el ultimo del indice

        int num = listaMatrizCuadrosRehacer.get(ultimo1).getNum();
        int fila = listaMatrizCuadrosRehacer.get(ultimo1).getFila();
        int columna = listaMatrizCuadrosRehacer.get(ultimo1).getColumna();

        JUEGO.claseJuego.insertarNum(num, fila, columna);//para hacer la matriz de nuevo en 0

        //------------ Externamente en la matriz del juego

        int ultimo2 = listaCuadrosRehacer.size() - 1; //obtiene el ultimo del indice

        String numero = String.valueOf(num);//utiliza el numero de la matriz interna para luego ponerlo en la externa

        listaCuadrosRehacer.get(ultimo2).setText(numero);//pone el cuadro en 0

        listaCuadrosRehacer.remove(ultimo2);

    }      
    
    /**
     * Metodo para borrar el juego actual
    */   
    public void borrarJuegoBoton(Timer t, JLabel[][] matrizCuadros, JLabel matrizLabels[][][]) {                                                 
        t.stop(); 
        int resp = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea borrar el juego?", "Alerta!",
        JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        
        if(JOptionPane.YES_OPTION == resp)
        {
            JUEGO.claseJuego.agregarPartida();
            setConstantes(matrizCuadros, matrizLabels);    
        }
        t.start();
    }     
    
    /**
     * Metodo para terminar el juego
     * @param t (Timer)
     * @param ventana (Jugar)
    */
    public void terminaJuegoBoton(Timer t, Jugar ventana) {                                                  
        t.stop();
        int resp = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea terminar el juego?", "Alerta!",
            JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        if(JOptionPane.YES_OPTION == resp)
        {                
            ventana.dispose();
            iniciarNuevaPartida();          
        }
        t.start();
    }     
    
    /**
     * Metodo para cargar un juego desde un archivo
     * @param ventana (Jugar)
    */    
    public void cargarJuegoBoton(Jugar ventana) 
    {                                                        
        CargarNuevaPartida();       
        ventana.dispose();
    }      
    
    /**
     * Metodo para guardar una partida en un archivo
     * @param horas (int)
     * @param min (int)
     * @param sec (int)
     * @param cs (int)
     * @param partidaActual (Partida)
     * @param nombreJugador 
    */
    public void guardarJuegoBoton(int horas, int min, int sec, int cs, Partida partidaActual,
            JTextPane nombreJugador) {                                                  
        try {
     
            String nivel = partidaActual.getNivel();
            GuardarPartida nuevo = new GuardarPartida(JUEGO.claseJuego.getListaCuadros(), nivel,
            horas, min, sec, cs, nombreJugador.getText(), JUEGO.claseJuego.getTiempo(), 
            JUEGO.claseJuego.getPosicion(), JUEGO.claseJuego.getTamañoCuadricula());
            
            JOptionPane.showMessageDialog(null, "Se ha guardado la partida");
        } catch (TransformerException ex) 
        {         
            Logger.getLogger(Jugar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }     
    
    /**
     * Metodo para iniciar el juego
     * @param nombreJugador
     * @param botonesPrincipales
     * @param botonesNum
     * @param t 
     */
    public void iniciarJuegoboton(JTextPane nombreJugador, JButton[] botonesPrincipales, JButton[]
            botonesNum, Timer t) {                                                
        
        if(nombreJugador.getText().equals("")){
            JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR SU NOMBRE");
            return;
        }
        botonesPrincipales[8].setEnabled(true);
        
        
        botonesNum[0].setEnabled(true);
        botonesNum[1].setEnabled(true);
        botonesNum[2].setEnabled(true);
        botonesNum[3].setEnabled(true);
        botonesNum[4].setEnabled(true);
       
        switch(JUEGO.claseJuego.getTamañoCuadricula()){
            
            case 9: botonesNum[8].setEnabled(true);
            
            case 8: botonesNum[7].setEnabled(true);
            
            case 7: botonesNum[6].setEnabled(true);
            
            case 6: botonesNum[5].setEnabled(true);
            break;
            
        }
        
        nombreJugador.setEnabled(false);
        botonesPrincipales[0].setEnabled(false);
        botonesPrincipales[1].setEnabled(true);
        botonesPrincipales[4].setEnabled(true);
        botonesPrincipales[6].setEnabled(false);
        
        if (t != null)//significa que no quiere cronometro y que quiere cronometro normal
        {
            t.start();
            
        }
        //else: no quiere cronometro
        //el otro caso se pone temporizador
    }    
    
    /**
     * Metodo para salir del juego
     * @param ventana (Jugar)
    */   
    public void salirJuegoBoton(Jugar ventana) {                                                
        // TODO add your handling code here:
        
        int resp = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea salirse?", "Alerta!",
            JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        if(JOptionPane.YES_OPTION == resp)
        {                
            
            MenuPrincipal nuevo = new MenuPrincipal();
            ventana.dispose();
            nuevo.setVisible(true);
        }
      
    }     
    
    /**
     * Metodo para mostrar las posibles jugadas de un cuadro
     * @param pBotonesNum
     * @param botonesPrincipales
     * @return 
     */
    public boolean posibleJuagdaBoton(JButton[] pBotonesNum, JButton[] botonesPrincipales) 
    {                                                         
        botonesPrincipales[7].setEnabled(false);
        botonesPrincipales[1].setEnabled(false);
        botonesPrincipales[6].setEnabled(false);
        botonesPrincipales[9].setEnabled(false);
        botonesPrincipales[0].setEnabled(false);
        botonesPrincipales[2].setEnabled(false);
        
        for(JButton botonNum: pBotonesNum)
        {           
            botonNum.setEnabled(false);
        }   
        return true;      
    }   
           
    /**
     * Funcion para agregar una partida
     */
    public void agregarPartida()
    {       
        partidaActual = getPartidaActual();
        int constante;
        String desigualdad1;
        String desigualdad2;
        
        for(int filas = 0; filas < 9; filas++)
        {            
            for(int columnas = 0; columnas < 9; columnas++)
            {               
                // Se obtiene la constante de la partida, si no es nula se agrega, si no se agrega 0
                if(partidaActual.getConstantes()[filas][columnas] != null)
                constante = Integer.parseInt(partidaActual.getConstantes()[filas][columnas]);
                else constante = 0;
                // Se agrega la desigualdad1, que son los < y >
                desigualdad1 = partidaActual.getDesigualdades()[filas][columnas][0];

                if(desigualdad1 != null && (desigualdad1.equals(">") || desigualdad1.equals
                        ("a"))) desigualdad1 = ">";
                else if(desigualdad1 != null) desigualdad1 = "<";
                // Se agrega la desigualdad1, que son los v y ^
                desigualdad2 = partidaActual.getDesigualdades()[filas][columnas][1];
                if(desigualdad2 != null && desigualdad2.equals("v")) desigualdad2 = "v";
                else if(desigualdad2 != null) desigualdad2 = "^";
                // Se crea un cuadro
                Cuadro cuadro = new Cuadro(constante, desigualdad1, desigualdad2, filas, columnas);
                // Se agrega el cuadro a la lista de cuadros
               
                listaCuadros[filas][columnas] = cuadro;   
            }      
        }
    }
    
    /**
     * Funcion para insertar un numero dentro de un cuadro que esta en la lista de cuadros
     * @param num (int)
     * @param fil (int)
     * @param colum (int)
    */
    public void insertarNum(int num, int fil, int colum)
    {        
        listaCuadros[fil][colum].setNum(num);         
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //--Funciones para verificar los numeros que se ingresen en los cuadros--//
    ///////////////////////////////////////////////////////////////////////////   
    /**
     * Funcion para verificar si el numero fue escrito en la fila
     * @param num (int)
     * @param colum (int)
     * @return true (No se ha escrito)/ false (fue escrito)
     */
    public boolean verificaColumna(int num, int colum)
    {
        for(int filas = 0; filas < 5; filas++)
        { 
            if(listaCuadros[filas][colum].getNum() == num)//El numero ya fue escrito en esa columna
            {   
                return false;
            }               
        }
        return true;//el numero se encuentra una vez en la columna
    }
    
    /**
     * Funcion para verificar si el numero fue escrito en la columna
     * @param num (int)
     * @param fil (int)
     * @return true (No se ha escrito)/ false (fue escrito)
     */
    public boolean verificaFila(int num, int fil)
    {   
        for(int columnas = 0; columnas < 5; columnas++)
        {   
            if(listaCuadros[fil][columnas].getNum() == num)//El numero ya fue escrito en esa fila
            {    
                return false;
            }               
        }
        return true;//el numero se encuentra una vez en la fila
    }
      
    /**
     * Método que valida la desigualdad de la izquierda y derecha   
     * @param num (int) numero a verificar
     * @param fila (int)
     * @param columna (int)
     * @param simbolo (int) simbolo para verirficar
     * @return true(no hay desigualdad)/ false (si hay desigualdad)
     */
    public boolean verificaDesigualdadX(int num, int fila, int columna, String simbolo)
    {    
        switch(simbolo)
        {           
            case ">":
                 
               
                // Valido el cuadro de la derecha
                if(listaCuadros[fila][columna + 1].getNum() != 0 && 
                        listaCuadros[fila][columna + 1].getNum() >= num){
                    
                 
                    return false; 
                    
                }
                
                break;
               
            case "<":
                 // Valido el cuadro de la izquierda
                if(listaCuadros[fila][columna + 1].getNum() != 0 && 
                        listaCuadros[fila][columna + 1].getNum() <= num){
                    
                    return false;
                }
        }
        return true;
    }
    
    /**
     *  Método que valida la desigualdad de arriba y abajo   
     * @param num (int) numero a verificar
     * @param fila (int)
     * @param columna (int)
     * @param simbolo (int) simbolo para verirficar
     * @return true(no hay desigualdad)/ false (si hay desigualdad) 
     */
    public boolean verificaDesigualdadY(int num, int fila, int columna, String simbolo)
    {
       
        
        switch(simbolo){
            
            case "v":
                
                
                if(listaCuadros[fila + 1][columna].getNum() != 0 && 
                        listaCuadros[fila + 1][columna].getNum() >= num){
                    
                    return false;
                }
                break;
            
            case "^":
                
                if(listaCuadros[fila + 1][columna].getNum() != 0 && 
                        listaCuadros[fila + 1][columna].getNum() <= num){
                    
                    return false;
                }
        }
        
        return true;
    }
    

    /**
     * Funcion que Llama a los metodos de verificarX y verificarY, Recibe un indicador para ver si valida el símbolo 1 o el simbolo 2
     * 0 para el simbolo 1 y 1 para el simbolo 2
     * @param num (int) numero a verificar
     * @param fila (int)
     * @param columna (int)
     * @param indicador true/false
     * @return true(no hay desigualdad)/ false (si hay desigualdad) 
     */
    public boolean verificaDesigualdad2(int num, int fila, int columna, boolean indicador)
    {
        
        String simbolo;
                
        // Si el cuadro tiene un simbolo
        
        if(indicador) simbolo = listaCuadros[fila][columna].getSimbolo2();
        else simbolo = listaCuadros[fila][columna].getSimbolo1();
        
        if(simbolo != null){
            
            // Valida izquierda o derecha
            
            if(simbolo.equals(">") || simbolo.equals("<")){
                
                if(verificaDesigualdadX(num, fila, columna, simbolo) == false) return false;
                
            }
            
            // Valida arriba o abajo
            
            else{
              
                if(verificaDesigualdadY(num, fila, columna, simbolo) == false) return false;
            
            }
            
        }
        
        // Valida el cuadro de la izquierda con el actual, manda el numero al cuadro actual
        
        if(columna > 0) simbolo = listaCuadros[fila][columna - 1].getSimbolo1();
        else simbolo = null;
         
        if(simbolo != null && columna - 1 >= 0){
            
            listaCuadros[fila][columna].setNum(num);
           
            num = listaCuadros[fila][columna - 1].getNum();
            
            if(num != 0 && verificaDesigualdadX(num, fila, columna - 1, simbolo) == false){
                
                listaCuadros[fila][columna].setNum(0);
                return false;
            }
         
        }
        
        // Valida el cuadro de arriba con el actual
        
        if(fila > 0) simbolo = listaCuadros[fila - 1][columna].getSimbolo2();
        else simbolo = null;
        
        if(simbolo != null && fila - 1 >= 0){
            
            listaCuadros[fila][columna].setNum(num);
            
            num = listaCuadros[fila - 1][columna].getNum();
            
            if(num != 0 && verificaDesigualdadY(num, fila - 1, columna, simbolo) == false){
                
                listaCuadros[fila][columna].setNum(num);
                return false;
            }
        }
        
        return true;
    }
  
    ////////////////////////////////////////////////////
    //----Funciones para la configuracion del juego---//
    ////////////////////////////////////////////////////  
    /**
     * Funcion para establecer el tipo de dificultad 
     * @param pDificultad (string)
     */
    public void setDificultad(String pDificultad)
    {
        dificultad = pDificultad;
    }
        
    /**
     * Funcion para establecer el tipo de tiempo
     * @param pTiempo (string)
     */
    public void setTiempo(String pTiempo)
    {
        tiempo = pTiempo;
    }
    
    /**
     * Funcion para establecer el tipo de posicion
     * @param pPosicion (string)
     */
    public void setPosicion(String pPosicion)
    {
        posicion = pPosicion;
    }
    
    /**
     * Funcion para establecer el tamaño de la cuadricula
     * @param pTamaño (int)
     */
    public void setTamañoCuadricula(int pTamaño){
        
        tamañoCuadricula = pTamaño;
    }
    
    /**
     * Funcion para obtener el tipo de dificultad
     * @return (string)
     */
    public String getDificultad()
    {
        return dificultad;
    }   
    
    /**
     * Funcion para obtener el tipo de Tiempo
     * @return (string)
     */
    public String getTiempo()
    {
        return tiempo;
    }
    
    /**
     * Funcion para obtener el tipo de Posicion
     * @return (string)
     */
    public String getPosicion()
    {
        return posicion;
    }
    
     /**
     * Funcion para obtener el tamaño de la cuadricula
     * @return tamnaño de la cuadricula (int)
     */
    public int getTamañoCuadricula(){
        
        return tamañoCuadricula;
    }    

    /**
     * Funcion para establecer el tiempo/cronometro del usuario
     * @param pHoras (string)
     * @param pMin (string)
     * @param pSec (string)
     */
    public void setTiempo(String pHoras, String pMin, String pSec)
    {
        horas = pHoras;
        min = pMin;
        sec = pSec;
    }
       
    /**
     * Funcion para obtener las horas
     * @return (string)
     */
    public String getHoras()
    {
        return horas;
    }
    
    /**
     * Funcion para obtener los minutos
     * @return (string)
     */
    public String getMin()
    {
        return min;
    }
    
    /**
     * Funcion para obtener los segundos
     * @return (string)
     */
    public String getSec()
    {
        return sec;
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //----Funciones para la creacion de archivos y encontrar los mejores 10 tiempos de cada dificultad--//   
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Funcion para crear un archivo con el nombre que se elija predeterminadamente antes
     * @param archivo (File)
     */
    public void crearArchivoTop10(File archivo)
    {     
        try{     
                if(archivo.exists() && !archivo.isDirectory())// si existe entonces no hace nada     
                {
                    return; 
                }
                if (archivo.createNewFile())//crea un archivo
                {
                    System.out.println("El fichero se ha creado correctamente");
                }               
                else//en caso de error
                {
                    System.out.println("No ha podido ser creado el fichero");
                }
                
            }catch (IOException e) 
            {          //en caso de error
                  
            }
    }
    
    /**
     * Funcion para guardar en un archivo existente los datos obtenidos del juego del usuario
     * @param archivo (File) nombre del archivo
     * @param nombre (String) nombre del usuario
     * @param TiempoTotal (String) tiempo total obtenido del usuario
     * @param horas (int) horas del usuario
     * @param min (int) minutos del usuario
     * @param sec (int) segundos del usuario
     */
    public void guardarArchivoTop10(File archivo, String nombre, String TiempoTotal, int horas, int min, int sec)//Este guardar archivo es para las partidas
    {
    try{                  
            FileWriter escribir = new FileWriter(archivo, true);//se utiliza el import de FileWriter para escribir en el archivo, se le manda el archivo
           
            escribir.write(nombre);
            escribir.write("\n");
            escribir.write(TiempoTotal);
            escribir.write("\n");
            escribir.write(String.valueOf(horas));
            escribir.write("\n");
            escribir.write(String.valueOf(min));
            escribir.write("\n");
            escribir.write(String.valueOf(sec));
            escribir.write("\n");
            escribir.close();//se cierra
            
            System.out.println("Se ha escrito en el archivo");
        }
        catch(IOException e){
            
            //imprime el error si no existe un archivo donde escribir
            System.out.println("NO SE PUEDE ESCRIBIR EN EL ARCHIVO");
        }    
    }
    
    /**
     * Funcion para crear una lista con la informacion de un archivo
     * @param archivo1 (File) archivo a buscar
     * @param lista (ArrayList) lista vacia para modificar
     * @return lista(ArrayLista) lista modificada
     */
    public ArrayList<String> leerArchivoTop10(File archivo1, ArrayList<String> lista)
    {
        try
        {                                  
            if(archivo1.exists() && !archivo1.isDirectory())//busca que exista
            {      
                System.out.println("EL ARCHIVO SE ABRIÓ CORRECTAMENTE");
                                           
                Scanner obj = new Scanner(archivo1);//si existe, este objeto lee todo lo que tenga
 
                while (obj.hasNextLine())//lo lee por lineas
                {                   
                    lista.add(obj.nextLine());
                } 
            }
                
        }catch(Exception e)
        {
            //imprime el error si no existe un archivo donde escribir
            System.out.println("NO SE PUEDE ESCRIBIR EN EL ARCHIVO...");
        }           
        return lista;//devuelve la lista
    }
    
    /**
     * Funcion para crear una lista con los mejores tiempos de otra lista
     * @param nuevaLista (ArrayList) lista vacia para modificar
     * @param lista (ArrayList) lista con la informacion
     * @return nuevaLista (ArrayList) lista modificada
     */
    public  ArrayList<Integer> obtenerMejorTop10(ArrayList<Integer> nuevaLista, ArrayList<String> lista)
    {       
        int i = 0;
        for (String elemento: lista)
        {       
            if(i == 1)
            {
                nuevaLista.add(Integer.parseInt(elemento)); //tiempo Total
            }
            if(i == 4)//para hacer el ciclo
            {
                i = 0;
            }
            else
            {
                i++;
            }
        }
              
        Collections.sort(nuevaLista);//ordena los elementos de menor a mayor
               
        return nuevaLista;
    }
    
    /**
     * Funcion para el boton de posibles jugadas, determina si un numero puede ser usado en una casilla
     * @param ListaJugadasPosi
     * @param numero
     * @param fila
     * @param columna 
     */
    public void verificaJugadas(ArrayList ListaJugadasPosi, int numero, int fila, int columna)
    {   
        for (int i = 1; i <= numero; i++)//verifica todos los numeros, numero seria la cuadricula
        {
            if(verificaColumna(i, columna) == true)//true significa que no ha sido escrito
            {
                if(verificaFila(i, fila) == true)//true significa que no ha sido escrito
                {
                        if(JUEGO.claseJuego.verificaDesigualdad2(i, fila, columna, true))
                        {
                            if(JUEGO.claseJuego.verificaDesigualdad2(i, fila, columna, false))
                            {
                                ListaJugadasPosi.add(String.valueOf(i));//se puede escribir en la fila y columna
                            }
                        } 
                }
            }
        }
        
        if (ListaJugadasPosi.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "No hay jugadas posibles en este cuadro");
            return;
        }
        
        String jugadas = "";
        for (Object num: ListaJugadasPosi)
        {
            jugadas += num + " ";
        }

        JOptionPane.showMessageDialog(null, "Posibles jugadas en este cuadro: " + jugadas);        
    }  
    
    /**
     * Abre el manual de usuario
     */
    public void abrirManual()
    {
        try
        {
            File file = new File("C:\\Users\\Admin\\OneDrive - Estudiantes ITCR\\Escritorio\\POO\\Proyecto #3\\Manual de Usuario.pdf");
            if(!Desktop.isDesktopSupported())
            {
                System.out.println("No se pudo abrir");
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            if(file.exists())
                desktop.open(file);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
 
    /**
     * Funcion para obtener la partida actual
     * @return (la partida actual)
     */
    public static Partida getPartidaActual()
    {            
        return partidaActual;
    }
    
    /**
     * Funcion que obtiene el indicador de nivel
     * @return (int)
     */
    public static int getIndicadorNivel()
    {    
        return indicadorNivel;
    }
    
    /**
     * Funcioon para sumar 1 al indicador de nivel
     */
    public static void setIndicadorNivel()
    {    
        indicadorNivel ++;
    }
    
    /**
     * Funcion para reiniciar al indicador de nivel
     */
    public static void reiniciarIndicadorNivel()
    {       
        indicadorNivel = 1;
    }

    /**
     * Método que retorna una partida que esté activa    
     * @return (partida activa)
     */
    public static Partida getPartida()
    {              
        //return miControlador.getPartida(indicadorNivel, partidaActual);
        
        if(indicadorNivel == 2)
        {
            JUEGO.claseJuego.setDificultad("Intermedio");
        } 
        else if(indicadorNivel >= 3)
        {
            JUEGO.claseJuego.setDificultad("Dificil");
        } 
                    
        if(JUEGO.claseJuego.getDificultad().equals(""))
        {
            JUEGO.claseJuego.setDificultad("Facil");
        } 
        
        while(true)
        {                
            for(Partida partida: listaPartidas)
            {             
                if(partida.getActivo() && JUEGO.claseJuego.getDificultad().
                        equals(partida.getNivel()) && JUEGO.claseJuego.getTamañoCuadricula()
                        == partida.getTamaño()){ // Si está activa la retorna

                    partida.setActivo(false);
                    partidaActual = partida;
                    
                    return listaPartidas.get(2);
                }
            }      
            for(Partida partida: listaPartidas){ // Si todas están inactivas las reinicia 

                partida.setActivo(true);
            }
        }
    }
    
    /**
     * Funcion para cargar una nueva partida
     */
    public static void CargarNuevaPartida()
    {       
        CargarPartida nuevo = new CargarPartida();
        
        partidaActual = nuevo.cargarPartidaXML();
        
        JUEGO.claseJuego.setTamañoCuadricula(CargarPartida.getTamaño());
       
        Jugar partida = new Jugar(true);     
        partida.setVisible(true);      
    }
}
