/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.excelgrupo6;

import Modelo.HojaCalculo;
import Operaciones.Modificacion;
import java.util.Scanner;

/**
 *
 * @author s u s
 */
public class ExcelGrupo6 {

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        
        // Solicitar dimensiones de la hoja de cálculo
        System.out.println("Bienvenido a la Hoja de Calculo");
        System.out.print("Ingrese el numero de filas: ");
        int numFilas = scanner.nextInt();
        System.out.print("Ingrese el numero de columnas: ");
        int numColumnas = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        // Crear la hoja de cálculo con las dimensiones especificadas
        HojaCalculo hoja = new HojaCalculo();
        hoja.crearEstructura(numFilas, numColumnas);
        
        Modificacion modificador = new Modificacion(hoja); //para modificar datos

        
        boolean salir = false;
        int filaActual = 0;
        int columnaActual = 0;
        
        while (!salir) {
            System.out.println("\n--- Hoja de Calculo ---");
            hoja.mostrarHoja(numFilas, numColumnas);
            System.out.println("\nPosicion actual: Fila " + filaActual + ", Columna " + columnaActual);
            System.out.println("Valor actual: " + (hoja.obtenerValor(filaActual, columnaActual) != null ? 
                                                  hoja.obtenerValor(filaActual, columnaActual) : "vacio"));
            
            System.out.println("\nOpciones:");
            System.out.println("1. Ingresar valor en la posicion actual");
            System.out.println("2. Mover a la celda de abajo");
            System.out.println("3. Mover a la celda de la derecha");
            System.out.println("4. Cambiar a una posicion especifica");
            System.out.println("5. Limpiar celda actual");
            System.out.println("6. Reemplazar un valor de la hoja");
            System.out.println("7. Cambiar tamaño de la hoja (filas y columnas)");

            System.out.println("8. Salir");
            System.out.print("Seleccione una opcion: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            
            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el valor para la celda: ");
                    String valor = scanner.nextLine();
                    hoja.establecerValor(filaActual, columnaActual, valor);
                    break;
                case 2:
                    if (filaActual < numFilas - 1) {
                        filaActual++;
                    } else {
                        System.out.println("Ya se encuentra en la ultima fila.");
                    }
                    break;
                case 3:
                    if (columnaActual < numColumnas - 1) {
                        columnaActual++;
                    } else {
                        System.out.println("Ya se encuentra en la ultima columna.");
                    }
                    break;
                case 4:
                    System.out.print("Ingrese el numero de fila: ");
                    int nuevaFila = scanner.nextInt();
                    System.out.print("Ingrese el numero de columna: ");
                    int nuevaColumna = scanner.nextInt();
                    scanner.nextLine(); // Consumir el salto de línea
                    
                    if (nuevaFila >= 0 && nuevaFila < numFilas && nuevaColumna >= 0 && nuevaColumna < numColumnas) {
                        filaActual = nuevaFila;
                        columnaActual = nuevaColumna;
                    } else {
                        System.out.println("Posicion fuera de rango.");
                    }
                    break;
                case 5:modificador.vaciarCelda(filaActual, columnaActual);
                
                    break;
                case 6:
                     System.out.print("Ingrese el valor que desea reemplazar: ");
                     String valorBuscado = scanner.nextLine();
                     System.out.print("Ingrese el nuevo valor: ");
                     String nuevoValor = scanner.nextLine();
                     modificador.reemplazarValor(valorBuscado, nuevoValor, numFilas, numColumnas);
                     break;
                case 7:
                    System.out.print("Ingrese el nuevo numero de filas: ");
                    int nuevasFilas = scanner.nextInt();
                    System.out.print("Ingrese el nuevo numero de columnas: ");
                    int nuevasColumnas = scanner.nextInt();
                    scanner.nextLine(); // Limpiar buffer

                    HojaCalculo nuevaHoja = new HojaCalculo();
                    nuevaHoja.crearEstructura(nuevasFilas, nuevasColumnas);

                    for (int i = 0; i < Math.min(numFilas, nuevasFilas); i++) {
                    for (int j = 0; j < Math.min(numColumnas, nuevasColumnas); j++) {
                    String valorAntiguo = hoja.obtenerValor(i, j);
                    nuevaHoja.establecerValor(i, j, valorAntiguo);
            }
        }

        hoja = nuevaHoja;
        modificador = new Modificacion(hoja);
        numFilas = nuevasFilas;
        numColumnas = nuevasColumnas;
        filaActual = 0;
        columnaActual = 0;

        System.out.println("Se ha actualizado la hoja ");
        break;     
                     
                case 8:
                     salir = true;
                    break;
 
                default:
                    System.out.println("Opcion no valida.");
            }
        }
        
        System.out.println("Gracias por usar la Hoja de Calculo!");
        scanner.close(); 
        
    }
}
