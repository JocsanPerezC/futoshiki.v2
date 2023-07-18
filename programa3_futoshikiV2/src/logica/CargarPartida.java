package logica;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * Este es la clase CargarPartida donde se van a crear y llamar las funciones necesarias para CargarPartida
 * @author Kendall Rodríguez Camacho 2022049438 y Jocsan Adriel Perez Coto 2022437948 
 */
public class CargarPartida 
{  
    private static String constantes[][] = new String[9][9];
    private static String desigualdades[][][] = new String[9][9][2];
    private String nivel;
    private static int tamaño;
    private static String nombre;
    private static String horas;
    private static String min;
    private static String seg;
    private static String cs;
    private static String tiempo;
    private static String panel;
    
    private Partida nuevaPartida;
  
    /**
     * Metodo constructor de CargarPartida
     */
    public CargarPartida()
    {       
        try {           
            SAXBuilder builder = new SAXBuilder();
            File xml = new File("GuardarPartida.xml"); // Se abre el documento

            Document document = builder.build(xml);
            
            Element root = document.getRootElement();
            
            List<Element> elementoNivel = root.getChildren("Nivel"); 
            
            Element nivelElement = elementoNivel.get(0);
            
            nivel = nivelElement.getValue();
            
            List<Element> elementoTamaño = root.getChildren("Tamaño"); 
            
            Element TamañoElement = elementoTamaño.get(0);
            
            tamaño = Integer.parseInt(TamañoElement.getValue());
            
            List<Element> elementoNombre = root.getChildren("Nombre"); 
            
            Element NombreElement = elementoNombre.get(0);
            
            nombre = NombreElement.getValue();
            
            List<Element> elementoPanel = root.getChildren("Panel"); 
            
            Element PanelElement = elementoPanel.get(0);
            
            panel = PanelElement.getValue();
            
            List<Element> elementoTiempo = root.getChildren("Tiempo"); 
            
            Element TiempoElement = elementoTiempo.get(0);
            
            tiempo = TiempoElement.getValue();
            
            List<Element> elementoHoras = root.getChildren("Horas"); 
            
            Element horasElement = elementoHoras.get(0);
            
            horas = horasElement.getValue();
            
            List<Element> elementoMinutos = root.getChildren("Minutos"); 
            
            Element minutosElement = elementoMinutos.get(0);
            
            min = minutosElement.getValue();
            
            List<Element> elementoSegundos = root.getChildren("Segundos"); 
            
            Element segundosElement = elementoSegundos.get(0);
            
            seg = segundosElement.getValue();
            
            List<Element> elementoMilisegundos = root.getChildren("Milisegundos"); 
            
            Element milisegundosElement = elementoMilisegundos.get(0);
            
            cs = milisegundosElement.getValue();
            
            // Se obtiene la lista de Partidas
            List<Element> listaCuadros = root.getChildren("Cuadro"); 
            
            String valor;
            int fila;
            int columna;
           
            for(int indice1 = 0; indice1 < 5; indice1 ++){ // Se inician en nulo las matrices
                 
                    for(int indice2 = 0; indice2 < 9; indice2 ++)
                    {                       
                        constantes[indice1][indice2] = null;
                        desigualdades[indice1][indice2][0] = null;
                        desigualdades[indice1][indice2][1] = null; 
                    }
                }
            
            for (int i = 0; i < listaCuadros.size(); i++) { // Se recorren las partidas
                
                Element cuadro = listaCuadros.get(i); // Se obtiene el cuadro
                List<Element> valoresPartidas = cuadro.getChildren(); // Se obtienen los valores
             
                // Se recorren los valores de la partida
                for (int j = 0; j < valoresPartidas.size(); j++) {
                     
                    // Se obtiene el valor, ya sea const, des o el nivel
                    Element campo = valoresPartidas.get(j);
                
                    if(campo.getName().equals("cons"))
                    {                    
                        valor = campo.getValue().substring(0, 1);
                        fila = Integer.parseInt(campo.getValue().substring(2, 3));
                        columna = Integer.parseInt(campo.getValue().substring(4, 5));
                        
                        if( !valor.equals("0") ) constantes[fila][columna] = valor;
                    }
                    else if(campo.getName().equals("des")){
                        
                        valor = campo.getValue().substring(0, 1);
                        fila = Integer.parseInt(campo.getValue().substring(2, 3));
                        columna = Integer.parseInt(campo.getValue().substring(4, 5));

                        if(valor.equals("a") || valor.equals("b")) desigualdades[fila][columna][0] = valor;
                        else if (valor.equals("y") || valor.equals("z"))
                            desigualdades[fila][columna][1] = valor;
                    }   
                }                    
            }
            
            nuevaPartida = new Partida(constantes, desigualdades, nivel, tamaño);
           
        } catch (JDOMException | IOException ex) {
          
            Logger.getLogger(XML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Funcion para retornar una nueva partida (que ha sido cargada)
     * @return nuevaPartida
     */
    public Partida cargarPartidaXML()
    {       
        return nuevaPartida;
    }
    
    /**
     * Funcion para retornar el nombre
     * @return nombre (string)
     */
    public static String getNombre()
    {     
        return nombre;
    }
    
    /**
     * Funcion para retornar el horas
     * @return horas (string)
     */
    public static String getHoras()
    {   
        return horas;
    }
    
    /**
     * Funcion para retornar los minutos
     * @return min (String)
     */
    public static String getMinutos()
    {      
        return min;
    }
    
    /**
     * Funcion para retornar los segundos
     * @return seg (seg)
     */
    public static String getSegundos()
    {       
        return seg;
    }
    
    /**
     * Funcion para retornar los milisegundos
     * @return cs (String)
     */
    public static String getMilisegundos()
    {      
        return cs;
    }
    
    /**
     * Funcion para retornar el tiempo total
     * @return tiempo (String)
     */
    public static String getTiempo()
    {       
        return tiempo;
    }
    
    /**
     * Funcion para retornar la posicion del panel/interfaz
     * @return panel (String)
     */
    public static String getPosicionPanel()
    {       
        return panel;
    }
    
    /**
     * Funcion para retornar el tamaño de la cuadricula
     * @return tamaño (int)
     */
    public static int getTamaño()
    {     
        return tamaño;
    }
}
