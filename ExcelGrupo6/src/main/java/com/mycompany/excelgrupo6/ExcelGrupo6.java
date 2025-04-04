/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.excelgrupo6;

import Modelo.HojaCalculo;
import Operaciones.Calculadora;
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
        Calculadora operaciones = new Calculadora(hoja);
        
        boolean salir = false;
        int filaActual = 0; 
        int columnaActual = 0;
        
        while (!salir) {
            System.out.println("\n--- Hoja de Calculo ---");
            hoja.mostrarHoja(numFilas, numColumnas);
            System.out.println("\nPosicion actual: Fila " + (filaActual+1) + ", Columna " + (columnaActual+1));
            System.out.println("Valor actual: " + (hoja.obtenerValor(filaActual, columnaActual) != null ? 
                                                  hoja.obtenerValor(filaActual, columnaActual) : "vacio"));
            
            System.out.println("\nOpciones:");
            System.out.println("1. Ingresar valor en la posicion actual");
            System.out.println("2. Mover a la celda de abajo");
            System.out.println("3. Mover a la celda de arriba");
            System.out.println("4. Mover a la celda de la derecha");
            System.out.println("5. Mover a la celda de la Izquierda");
            System.out.println("6. Cambiar a una posicion especifica");
            System.out.println("7. Realizar operaciones");
            System.out.println("8. Limpiar celda actual");
            System.out.println("9. Reemplazar un valor de la hoja");
            System.out.println("10. Cambiar dimensiones de la hoja (filas y columnas)");

            System.out.println("11. Salir");
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
                        System.out.println("Ya se encuentra en la ultima fila");
                    }
                    break;
                case 3:  
                    if (filaActual < numFilas - 1) {
                        filaActual--;
                    } else {
                        System.out.println("Ya se encuentra en la primera fila");
                    }
                    break;
                case 4:
                    if (columnaActual < numColumnas - 1) {
                        columnaActual++;
                    } else {
                        System.out.println("Ya se encuentra en la ultima columna");
                    }
                    break;
                case 5:
                    if (columnaActual < numColumnas - 1) {
                        columnaActual--;
                    } else {
                        System.out.println("Ya se encuentra en la primera columna");
                    }
                    break;
                    
                case 6:
                    System.out.print("Ingrese la celda a la que desea ir (ej: A1): ");
                    String entrada = scanner.nextLine().toUpperCase();

                    if (entrada.length() >= 2 && Character.isLetter(entrada.charAt(0))) {
                    char letraColumna = entrada.charAt(0);
                    int fila;

                    try {
                    fila = Integer.parseInt(entrada.substring(1)) - 1;
                    int columna = letraColumna - 'A';

                    if (fila >= 0 && fila < numFilas && columna >= 0 && columna < numColumnas) {
                    filaActual = fila;
                    columnaActual = columna;
                    } else {
                    System.out.println("La celda esta fuera de rango");
                    }
                    } catch (NumberFormatException e) {
                    System.out.println("Formato incorrecto Ejemplo valido: A1, B2, C3...");
                    }
                    } else {
                    System.out.println("Formato invalido Ejemplo valido: A1, B2, C3...");
                    }              
                    break;
                case 7:
                    operaciones.realizarOperacion();
                    break;
                case 8:
                        System.out.print("¿Esta seguro que desea limpiar esta celda? (s/n): ");
                        String confirmacion = scanner.nextLine();
                        if (confirmacion.equalsIgnoreCase("s")) {
                        modificador.vaciarCelda(filaActual, columnaActual);
                        } else {
                        System.out.println("La celda no fue modificada");
                        }                
                    break;
                case 9:
                     System.out.print("Ingrese el valor que desea reemplazar: ");
                    String valorBuscado = scanner.nextLine();

                    System.out.print("Ingrese el nuevo valor: ");
                    String nuevoValor = scanner.nextLine();

                    System.out.print("¿Esta seguro que desea reemplazar todas las ocurrencias de \"" + valorBuscado + "\" por \"" + nuevoValor + "\"? (s/n): ");
                    String confirmacionReemplazo = scanner.nextLine();

                    if (confirmacionReemplazo.equalsIgnoreCase("s")) {
                    modificador.reemplazarValor(valorBuscado, nuevoValor, numFilas, numColumnas);
                     } else {
                    System.out.println("No se realizo ningun cambio.");
                    }
                    break;
                case 10:
                    System.out.print("Esta accion cambiara las dimensiones de la hoja y podria eliminar datos fuera del nuevo area. ¿Desea continuar? (s/n): ");
                    String confirmarDimension = scanner.nextLine();

                    if (confirmarDimension.equalsIgnoreCase("s")) {
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
                    operaciones = new Calculadora(hoja);
                    numFilas = nuevasFilas;
                    numColumnas = nuevasColumnas;
                    filaActual = 0;
                    columnaActual = 0;

                    System.out.println("Se ha actualizado el area de la hoja conservando los datos posibles");
                    } else {
                    System.out.println("No se realizaron cambios en las dimensiones");
                    }
                    break;
                case 11:
                     salir = true;
                    break;
 
                default:
                    System.out.println("Opcion no valida");
            }
        }
        
        System.out.println("Gracias por usar la Hoja de Calculo!");
        scanner.close(); 
        
    }
}
