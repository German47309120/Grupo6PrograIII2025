/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.excelgrupo6;

/**
 *
 * @author German Hernández
 */
// Se declaran las variables de movimientos de la celda
public class Celda {
    private String nombre;  
    private Double valor;   
    private Celda derecha;  
    private Celda izquierda;
    private Celda abajo;
    private Celda arriba;
    
//Funcion que permite autorefeciar atributos de una clase
    public Celda(String nombre, Double valor) {
        this.nombre = nombre;
        this.valor = valor;
        this.derecha = null;
        this.izquierda = null;
        this.abajo = null;
        this.arriba = null;
    }

    // Metodos nos permiten acceder y modificar los atributos de la celda
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Celda getDerecha() {
        return derecha;
    }

    public void setDerecha(Celda derecha) {
        this.derecha = derecha;
    }

    public Celda getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(Celda izquierda) {
        this.izquierda = izquierda;
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

    // Devuelve true si la celda no tiene un valor asignado y false si tiene un valor numerico
    public boolean esVacia() {
        return valor == null;
    }
// nos permite agregar un valor si en caso esta vacia y si tiene un valor lo incrementa
    public void incrementarValor(double cantidad) {
        if (valor == null) {
            valor = cantidad;
        } else {
            valor += cantidad;
        }
    }
// nos permite restar cantidad y si en caso esta vacia se le agrega -cantidad
    public void decrementarValor(double cantidad) {
        if (valor == null) {
            valor = -cantidad;
        } else {
            valor -= cantidad;
        }
    }

    public void multiplicarValor(double factor) {
        if (valor != null) {
            valor *= factor;
        }
    }

    public void dividirValor(double divisor) {
        if (valor != null && divisor != 0) {
            valor /= divisor;
        }
    }
// nos muestra el resultado de la celda en TEXTO y si esta vacia muestra VACIA
    @Override
    public String toString() {
        return nombre + ": " + (valor == null ? "VACÍA" : valor);
    }
}




