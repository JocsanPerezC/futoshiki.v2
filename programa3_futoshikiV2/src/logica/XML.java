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
 * Este es la clase XML donde se van a crear y llamar las funciones necesarias para XML
 * @author Kendall Rodríguez Camacho 2022049438 y Jocsan Adriel Perez Coto 2022437948 
 */
public class XML {
    
    private static ArrayList<Partida> partidas = new ArrayList<>();
    private static String constantes[][] = new String[9][9];
    private static String desigualdades[][][] = new String[9][9][2];
    private static String nivel;
    private static int tamaño;
  
    /**
     * Metodo constructor de XML
     */
    public XML(){
        
        try {
            SAXBuilder builder = new SAXBuilder();
            File xml = new File("futoshiki2022partidas.xml"); // Se abre el documento

            Document document = builder.build(xml);
            
            Element root = document.getRootElement();
           
            // Se obtiene la lista de Partidas
            List<Element> listaPartidas = root.getChildren("partida"); 
            
            String valor;
            int fila;
            int columna;
            
            Partida nuevaPartida;
           
            for (int i = 0; i < listaPartidas.size(); i++) { // Se recorren las partidas
                
                nivel = null;
                
                for(int indice1 = 0; indice1 < 9; indice1 ++){ // Se inician en nulo las matrices
                    
                    for(int indice2 = 0; indice2 < 9; indice2 ++){
                        
                        constantes[indice1][indice2] = null;
                        desigualdades[indice1][indice2][0] = null;
                        desigualdades[indice1][indice2][1] = null;
                        
                    }
                }
                
                Element partida = listaPartidas.get(i); // Se obtiene la partida
                List<Element> valoresPartidas = partida.getChildren(); // Se obtienen los valores
             
                // Se recorren los valores de la partida
                for (int j = 0; j < valoresPartidas.size(); j++) {
                     
                    // Se obtiene el valor, ya sea const, des o el nivel
                    Element campo = valoresPartidas.get(j);
                
                    if(campo.getName().equals("nivel")) nivel = campo.getValue();
                    if(campo.getName().equals("cuadricula")) tamaño = Integer.parseInt(campo.getValue());
                    
                    if(campo.getName().equals("cons")){
                     
                        valor = campo.getValue().substring(0, 1);
                        fila = Integer.parseInt(campo.getValue().substring(2, 3));
                        columna = Integer.parseInt(campo.getValue().substring(4, 5));
                        
                        constantes[fila][columna] = valor;
                        
                    }
                    else if(campo.getName().equals("des")){
                        
                        valor = campo.getValue().substring(0, 1);
                        fila = Integer.parseInt(campo.getValue().substring(2, 3));
                        columna = Integer.parseInt(campo.getValue().substring(4, 5));
                       // System.out.println("\nVALOR ES " + valor + " " + nivel + " " + i + "\n");
                        if(valor.equals("a") || valor.equals("b")) desigualdades[fila][columna][0] = valor;
                        else desigualdades[fila][columna][1] = valor;
                       
                    }   
                  
                }
                
                nuevaPartida = new Partida(constantes, desigualdades, nivel, tamaño);
                partidas.add(nuevaPartida); // Se guarda la partida en la lista de partidas
            }
            
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(XML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Funcion para obtener las partidas
     * @return partidas (ArrayList)
     */
    public ArrayList<Partida> getPartidas()
    {          
        return partidas;
    }   
}
