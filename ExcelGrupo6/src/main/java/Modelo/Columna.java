/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author abics
 */
public class Columna {
  // se crean atributos partiendo de la misma clase 
     Columna columnaDerecha;
     
     Celda primeraCelda;

    public Columna() {  //Se inicia el constructor con los datos en 0
        this.columnaDerecha = null;
        
        this.primeraCelda = null;
    }

    public void setColumnaDerecha(Columna columna) {
        this.columnaDerecha = columna;
        
    }

    public Columna getColumnaDerecha() {
        return columnaDerecha;
        
    }

    public void agregarCelda(String valor) {
        Celda nuevaCelda = new Celda(valor);  //Crea un nuevo valor
        if (primeraCelda == null) {
            primeraCelda = nuevaCelda;
        } else {
            Celda actual = primeraCelda;
            while (actual.getAbajo() != null) {
                actual = actual.getAbajo();
            }
            actual.setAbajo(nuevaCelda);
        }
    }

    public Celda getPrimeraCelda() {
        return primeraCelda;
    }


    
}
