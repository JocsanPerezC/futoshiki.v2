
package logica;

/**
 * Clase FuncionesListaEnlazada, contiene el metodo constructor de FuncionesListaEnlazada y sus funciones necesarias
 * @author Kendall Rodríguez Camacho 2022049438 y Jocsan Adriel Perez Coto 2022437948 
 */
public class FuncionesListaEnlazada <T>
{
    public Nodo cabezaLista;
    public Nodo  colaLista;
    
    /**
     * Metodo constructor de FuncionesListaEnlazada
     */
    public FuncionesListaEnlazada(){
        
        cabezaLista = null;
        colaLista = null;
    }
    
    /**
     * Funcion para hacer push(agregar) un elemento a la lista enlazada
     * @param t nodo
     */
    public void push(T t){
        
        Nodo nodo = new Nodo<T>();
        nodo.dato = t;
        if(cabezaLista == null){
            
            cabezaLista = nodo;
            colaLista = nodo;
        }
        else{
            
            nodo.predecesor = colaLista;
            colaLista.sucesor = nodo;
            colaLista = nodo;
        }
    }

    /**
     * Funcion que retorna y elimina la cola de la lista
     * @return nodo
     */
    public Nodo pop(){
        
        Nodo nodo = cabezaLista; 
        Nodo aux;
        
        if(nodo == null) return null;
        
        while(nodo.sucesor != null){ // 2, 4, 5
            
            nodo = nodo.sucesor;
            
        }
        
        aux = nodo.predecesor;
        if(aux == null){
            cabezaLista = null;
            return nodo;
        }
        aux.sucesor = null;
        return nodo;
    }
     
    /**
     * Funcion que retorna true si la lista es vacía, false de lo contrario
     * @return true (vacia) /false (no vacia)
     */
    public boolean empty(){
       
        if(cabezaLista == null) return true;
        
        return false;
    }
}
