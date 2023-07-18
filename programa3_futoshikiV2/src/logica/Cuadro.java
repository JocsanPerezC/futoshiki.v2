package logica;

/**
 * Este es la clase Cuadro donde se van a crear y llamar las funciones necesarias para cada cuadro del juego
 * @author Kendall Rodríguez Camacho 2022049438 y Jocsan Adriel Perez Coto 2022437948 
 */
public class Cuadro 
{
    private int num;
    private String simbolo1;
    private String simbolo2;
    private int fila;
    private int columna;
       
   /**
    * Metodo constructor del Cuadro
    * @param pNum (int)
    * @param pSimbolo1 (String)
    * @param pSimbolo2 (String)
    * @param pFila (int)
    * @param pColumna (int)
    */
    public Cuadro(int pNum, String pSimbolo1, String pSimbolo2, int pFila, int pColumna)
    {
        num = pNum;
        simbolo1 = pSimbolo1;
        simbolo2 = pSimbolo2;
        
        fila = pFila;
        columna = pColumna;      
    }
    
    
    // SETTERS
    /**
     * Funcion para ingresar el numero del cuadro
     * @param pNum (int)
     */
    public void setNum(int pNum)
    {
        num = pNum;
    }
    
    /**
     * Método para crear/agregar si el simbolo de mayor o menor
     * @param pSimbolo (string)
     */
    public void setSimbolo1(String pSimbolo)
    {
        simbolo1 = pSimbolo;
    }
    
    /**
     * Método para crear/agregar si el simbolo de mayor o menor 
     * @param pSimbolo (string)
     */
    public void setSimbolo2(String pSimbolo)
    {
        simbolo2 = pSimbolo;
    }
    
    // GETTERS   
    /**
     * Método para obtener el numero del cuadro
     * @return num (int)
     */
    public int getNum()
    {
        return num;
    }
    
    /**
     * Metodo para obtener al fila del cuadro
     * @return fila (int)
     */
    public int getFila()
    {
        return fila;
    }
    
    /**
     * Metodo para obtener la columna del cuadro
     * @return columna (int)
     */
    public int getColumna()
    {
        return columna;
    }

    /**
     * Método para obtener el simbolo del cuadro
     * @return simbolo (string)
     */
    public String getSimbolo1()
    {
        return simbolo1;
    }
    
    /**
     * Metodo para obtener el simbolo del cuadro
     * @return simbolo (string)
     */
    public String getSimbolo2()
    {
        return simbolo2;
    }
    
    /**
     * 
     * @return 
     */
    public String toString()
    {
        return num + simbolo1 + simbolo2;
    }
}
