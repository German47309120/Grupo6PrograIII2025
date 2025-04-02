/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Operaciones;

import Modelo.HojaCalculo;

/**
 *
 * @author abics
 */
public class Modificacion {

    private HojaCalculo hoja;

    public Modificacion(HojaCalculo hoja) {
        this.hoja = hoja;
    }

    // Modificar el valor de una celda específica
    public void modificarValor(int fila, int columna, String nuevoValor) {
        hoja.establecerValor(fila, columna, nuevoValor);
        System.out.println("Celda [" + fila + "," + columna + "] modificada con el valor: " + nuevoValor);
    }

    // Vaciar (borrar) el valor de una celda
    public void vaciarCelda(int fila, int columna) {
        hoja.establecerValor(fila, columna, "");
        System.out.println("Celda [" + fila + "," + columna + "] ha sido vaciada");
    }

    // Reemplazar un valor específico por otro en toda la hoja
    public void reemplazarValor(String valorBuscado, String nuevoValor, int numFilas, int numColumnas) {
        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {
                String actual = hoja.obtenerValor(i, j);
                if (actual != null && actual.equals(valorBuscado)) {
                    hoja.establecerValor(i, j, nuevoValor);
                    System.out.println("Valor en [" + i + "," + j + "] reemplazado");
                }
            }
        }
    }
}

