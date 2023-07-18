package logica;

/**
 * Este es la clase Partida donde se van a crear y llamar las funciones necesarias para Partida
 * @author Kendall Rodríguez Camacho 2022049438 y Jocsan Adriel Perez Coto 2022437948 
 */
public class Partida 
{   
    private String constantes[][] = new String[9][9];
    private String desigualdades[][][] = new String[9][9][2];
    private String nivel;
    private int tamañoCuadricula;
    private boolean activo = true;
    
    /**
     * Metodo constructor de la clase Partida
     * @param pConstantes (matriz (String))
     * @param pDesigualdades (matriz (String))
     * @param pNivel (String)
     * @param pTamaño (int)
     */
    public Partida(String pConstantes[][], String pDesigualdades[][][], String pNivel, int pTamaño){
        
        nivel = pNivel;
        
        tamañoCuadricula = pTamaño;
     
        for(int i = 0; i < 9; i ++){
            
            for(int e = 0; e < 9; e ++){
                 // En desigualdades [0] se guardan los > y <
                if(pDesigualdades[i][e][0] != null){
                    
                    switch(pDesigualdades[i][e][0]){

                        case "a": desigualdades[i][e][0] = ">"; break;
                        case "b": desigualdades[i][e][0] = "<"; break;
                       
                    }
                } else desigualdades[i][e][0] = pDesigualdades[i][e][0];
                 // En desigualdades [1] se guardan los v y ^ 
                if(pDesigualdades[i][e][1] != null){
                    
                    switch(pDesigualdades[i][e][1]){

                        case "y": desigualdades[i][e][1] = "v"; break;
                        
                        case "z": desigualdades[i][e][1] = "^"; break;
                       
                    }
                } else desigualdades[i][e][0] = pDesigualdades[i][e][0];
                
                 constantes[i][e] = pConstantes[i][e];
            }    
        }
    }
    
    /**
     * Funcion para establecer el valor activo
     * @param pActivo (boolean)
     */
    public void setActivo(boolean pActivo)
    {       
        activo = pActivo;
    }
    
    /**
     * Funcion para obtener las constantes
     * @return (matriz (String))
     */
    public String[][] getConstantes(){
               
        return constantes;
    }
    
    /**
     * Funcion para obtener las Desigualdades
     * @return (matriz (String))
     */
    public String[][][] getDesigualdades()
    {       
        return desigualdades;
    }
    
    /**
     * Funcion para obtener el nivel
     * @return nivel (String)
     */
    public String getNivel()
    {       
        return nivel;
    }
    
    /**
     * Funcion para obtener el valor activo
     * @return activo (boolean)
     */
    public boolean getActivo()
    {       
        return activo;
    }
    
    /**
     * Funcion para obtener el tamaño de la cuadricula
     * @return tamañoCuadricula (int)
     */
    public int getTamaño()
    {      
        return tamañoCuadricula;
    }
 
}
