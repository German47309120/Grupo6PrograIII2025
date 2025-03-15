/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author abics
 */
public class Fila {
    // se crean atributos partiendo de la misma clase 
     Fila filaAbajo;
     Celda primeraCelda;

    public Fila() { //Se inicia el constructor con los datos en 0
        this.filaAbajo = null;
        this.primeraCelda = null;
    }

    public void setFilaAbajo(Fila fila) {
        this.filaAbajo = fila;
    }

    public Fila getFilaAbajo() {
        return filaAbajo;
    }

    public void agregarCelda(String valor) {
        Celda nuevaCelda = new Celda(valor); //Crea un nuevo valor
        if (primeraCelda == null) {
            primeraCelda = nuevaCelda;
        } else {
            Celda actual = primeraCelda;
            while (actual.getDerecha() != null) {
                actual = actual.getDerecha();
            }
            actual.setDerecha(nuevaCelda);
        }
    }

    public Celda getPrimeraCelda() {
        return primeraCelda;
    }


}
