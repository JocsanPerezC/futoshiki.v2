package logica;

/**
 * Clase ExcepcionArchivo, contiene un mensaje de error personalizado
 * @author Kendall Rodríguez Camacho 2022049438 y Jocsan Adriel Perez Coto 2022437948 
 */
public class ExcepcionArchivo extends Exception{
    
    /**
     *  Metodo constructor de ExcepcionArchivo
     * @param mensaje mensaje
     */
    public ExcepcionArchivo(String mensaje){   
        super(mensaje);
    }
    
}
