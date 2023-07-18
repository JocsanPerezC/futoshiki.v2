package logica;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * Este es la clase GuardarPartida donde se van a crear y llamar las funciones necesarias para GuardarPartida
 * @author Kendall Rodríguez Camacho 2022049438 y Jocsan Adriel Perez Coto 2022437948 
 */
public class GuardarPartida {
    
    /**
     * Metodo constructor de GuardarPartida
     * @param listaCuadros (matriz con objetos tipo cuadro)
     * @param nivel (String)
     * @param horas (int)
     * @param min (int)
     * @param sec (int)
     * @param cs (int)
     * @param nombre (String)
     * @param tiempo (String)
     * @param posicionPanel
     * @param tamaño (int)
     * @throws TransformerConfigurationException
     * @throws TransformerException 
     */
    public GuardarPartida(Cuadro listaCuadros[][], String nivel, int horas, int min, int sec
            , int cs, String nombre, String tiempo, String posicionPanel, int tamaño) 
            throws TransformerConfigurationException, TransformerException{
    String simbolo1; 
    String simbolo2;
    String nomArchivo = "GuardarPartida";
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, nomArchivo, null);
            document.setXmlVersion("1.0");
            
            // NODO RAIZ
            Element raiz = document.getDocumentElement();
            
            Element NodeNivel = document.createElement("Nivel");
            Text nivelNodo = document.createTextNode(nivel);
            NodeNivel.appendChild(nivelNodo);
            raiz.appendChild(NodeNivel);
            
            Element NodeTamaño = document.createElement("Tamaño");
            Text tamañoNodo = document.createTextNode(Integer.toString(tamaño));
            NodeTamaño.appendChild(tamañoNodo);
            raiz.appendChild(NodeTamaño);
            
            Element NodeNombre = document.createElement("Nombre");
            Text nombreNodo = document.createTextNode(nombre);
            NodeNombre.appendChild(nombreNodo);
            raiz.appendChild(NodeNombre);
            
            Element NodePanel = document.createElement("Panel");
            Text panelNodo = document.createTextNode(posicionPanel);
            NodePanel.appendChild(panelNodo);
            raiz.appendChild(NodePanel);
            
            Element NodeTiempo = document.createElement("Tiempo");
            Text tiempoNodo = document.createTextNode(tiempo);
            NodeTiempo.appendChild(tiempoNodo);
            raiz.appendChild(NodeTiempo);            
            
            Element NodeHoras = document.createElement("Horas");
            Text horasNodo = document.createTextNode(Integer.toString(horas));
            NodeHoras.appendChild(horasNodo);
            raiz.appendChild(NodeHoras);
            
            Element NodeMin = document.createElement("Minutos");
            Text minNodo = document.createTextNode(Integer.toString(min));
            NodeMin.appendChild(minNodo);
            raiz.appendChild(NodeMin);
            
            Element NodeSeg = document.createElement("Segundos");
            Text segNodo = document.createTextNode(Integer.toString(sec));
            NodeSeg.appendChild(segNodo);
            raiz.appendChild(NodeSeg);
            
            Element NodeCs = document.createElement("Milisegundos");
            Text csNodo = document.createTextNode(Integer.toString(cs));
            NodeCs.appendChild(csNodo);
            raiz.appendChild(NodeCs);         
            
            for(int i = 0 ; i < 5; i++) { // Se recorren las filas
                
                for(int e = 0; e < 5; e ++){ // Se recorren las columnas
                    
                    Element itemNode = document.createElement("Cuadro");
                    Element desNodo1 = document.createElement("des");
                    simbolo1 = listaCuadros[i][e].getSimbolo1();
                    
                    if(simbolo1 != null){
                        
                        switch(simbolo1){

                            case ">": simbolo1 = "a"; break;
                            case "<": simbolo1 = "b"; break;
                            case "v": simbolo1 = "y"; break;
                            case "^": simbolo1 = "z"; break;
                           
                        }
                        
                    } else simbolo1 = "d";
                    
                    Text nodoSimbolo1 = document.createTextNode("" +  simbolo1 + "," + 
                            listaCuadros[i][e].getFila() + "," + listaCuadros[i][e].getColumna());
                    
                    
                    desNodo1.appendChild(nodoSimbolo1);
                    
                    Element desNodo2 = document.createElement("des");
                    simbolo2 = listaCuadros[i][e].getSimbolo2();
                    
                    if(simbolo2 != null){
                        
                        switch(simbolo2){

                            case ">": simbolo2 = "a"; break;
                            case "<": simbolo2 = "b"; break;
                            case "v": simbolo2 = "y"; break;
                            case "^": simbolo2 = "z"; break;
                            
                        }
                        
                    } else simbolo2 = "d";
                   
                        
                    Text nodoSimbolo2 = document.createTextNode(simbolo2 + "," + 
                            listaCuadros[i][e].getFila() + "," + listaCuadros[i][e].getColumna());
                        
               
                    desNodo2.appendChild(nodoSimbolo2);
                    
                    Element consNodo = document.createElement("cons");
                    
                    Text nodoConstante =document.createTextNode(listaCuadros[i][e].getNum()+
                            "," + listaCuadros[i][e].getFila() + "," + listaCuadros[i][e].getColumna());
                    consNodo.appendChild(nodoConstante);
                    
                    itemNode.appendChild(desNodo1);
                    itemNode.appendChild(desNodo2);
                    itemNode.appendChild(consNodo);

                    raiz.appendChild(itemNode);
                }
            }
            
            // GENERA XML
            Source source = new DOMSource(document);
            
            // DONDE SE GUARDARA
            Result result = new StreamResult(new java.io.File(nomArchivo + ".xml"));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
        
        } 
        catch(ParserConfigurationException e) 
        {
            
        }
    }   
}
