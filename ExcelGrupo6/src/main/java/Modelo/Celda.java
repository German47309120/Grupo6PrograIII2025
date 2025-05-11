/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author German Hernández
 */
//Se declara los valores para usar el movimiento en clase celda
//Celda hace referencia a los nodos de la lista enlasada

public class Celda {

     String valor;
     Celda derecha;
     Celda abajo;
     Celda arriba;
     Celda izquierda;
     

    public Celda(String valor) {
        this.valor = valor;        
        this.derecha = null;
        this.abajo = null;
        this.arriba = null;
        this.izquierda = null;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Celda getDerecha() {
        return derecha;
    }

    public void setDerecha(Celda derecha) {
        this.derecha = derecha;
    }

    public Celda getAbajo() {
        return abajo;
    }

    public void setAbajo(Celda abajo) {
        this.abajo = abajo;
    }
       
    public Celda getArriba() {
        return arriba;
    }

    public void setArriba(Celda arriba) {
        this.arriba = arriba;
    }

    public Celda getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(Celda izquirda) {
        this.izquierda = izquirda;
    }

}