/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.excelgrupo6;
/**
 *
 * @author abics
 */
//import de las conexiones

import Conexion.ConexionBD;
import Modelo.HojaCalculo;
import Operaciones.Calculadora;
import Operaciones.Modificacion;

import Estructura.arbolAVL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.Scanner;

public class ExcelGrupo6 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- SISTEMA GRUPO 6 ---");
            System.out.println("1. Hoja de Calculo");
            System.out.println("2. Arbol AVL");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opcion: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar salto de línea

            switch (opcion) {
                case 1:
                    ejecutarHojaCalculo(scanner);
                    break;
                case 2:
                    arbolAVL arbol = new arbolAVL();
                    subMenuAVL(scanner, arbol);
                    break;
                case 3:
                    salir = true;
                    System.out.println(" Gracias por usar el sistema");
                    break;
                default:
                    System.out.println(" Opcion invalida");
            }
        }

        scanner.close();
    }

    public static void ejecutarHojaCalculo(Scanner scanner) {
        System.out.print("Ingrese el numero de filas: ");
        int numFilas = scanner.nextInt();
        System.out.print("Ingrese el numero de columnas: ");
        int numColumnas = scanner.nextInt();
        scanner.nextLine(); // limpiar salto

        HojaCalculo hoja = new HojaCalculo();
        hoja.crearEstructura(numFilas, numColumnas);
        Modificacion modificador = new Modificacion(hoja);
        Calculadora operaciones = new Calculadora(hoja);
        int filaActual = 0;
        int columnaActual = 0;
        boolean salirHoja = false;

        while (!salirHoja) {
            System.out.println("\n--- Hoja de Calculo ---");
            hoja.mostrarHoja(numFilas, numColumnas);
            System.out.println("Posicion actual: Fila " + (filaActual + 1) + ", Columna " + (columnaActual + 1));
            System.out.println("Valor actual: " + (hoja.obtenerValor(filaActual, columnaActual) != null
                    ? hoja.obtenerValor(filaActual, columnaActual) : "vacio"));

            System.out.println("\nOpciones:");
            System.out.println("1. Ingresar valor en la posicion actual");
            System.out.println("2. Mover a la celda de abajo");
            System.out.println("3. Mover a la celda de arriba");
            System.out.println("4. Mover a la celda de la derecha");
            System.out.println("5. Mover a la celda de la izquierda");
            System.out.println("6. Cambiar a una posición especifica");
            System.out.println("7. Realizar operaciones");
            System.out.println("8. Limpiar celda actual");
            System.out.println("9. Reemplazar un valor de la hoja");
            System.out.println("10. Cambiar dimensiones de la hoja");
            System.out.println("11. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

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
                    if (filaActual > 0) {
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
                    if (columnaActual > 0) {
                        columnaActual--;
                    } else {
                        System.out.println("Ya se encuentra en la primera columna");
                    }
                    break;
                case 6:
                    System.out.print("Ingrese la celda destino (Ej: A1): ");
                    String entrada = scanner.nextLine().toUpperCase();
                    if (entrada.length() >= 2 && Character.isLetter(entrada.charAt(0))) {
                        char letraColumna = entrada.charAt(0);
                        try {
                            int fila = Integer.parseInt(entrada.substring(1)) - 1;
                            int columna = letraColumna - 'A';
                            if (fila >= 0 && fila < numFilas && columna >= 0 && columna < numColumnas) {
                                filaActual = fila;
                                columnaActual = columna;
                            } else {
                                System.out.println("La celda esta fuera de rango");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Formato invalido. Ej: A1, B2...");
                        }
                    } else {
                        System.out.println("Formato invalido. Ej: A1, B2...");
                    }
                    break;
                case 7:
                    operaciones.realizarOperacion();
                    break;
                case 8:
                    System.out.print("¿Esta seguro que desea limpiar esta celda? (s/n): ");
                    if (scanner.nextLine().equalsIgnoreCase("s")) {
                        modificador.vaciarCelda(filaActual, columnaActual);
                    }
                    break;
                case 9:
                    System.out.print("Valor a reemplazar: ");
                    String buscado = scanner.nextLine();
                    System.out.print("Nuevo valor: ");
                    String nuevo = scanner.nextLine();
                    System.out.print("¿Confirmar reemplazo? (s/n): ");
                    if (scanner.nextLine().equalsIgnoreCase("s")) {
                        modificador.reemplazarValor(buscado, nuevo, numFilas, numColumnas);
                    }
                    break;
                case 10:
                    System.out.print("¿Desea cambiar dimensiones? (s/n): ");
                    if (scanner.nextLine().equalsIgnoreCase("s")) {
                        System.out.print("Nuevas filas: ");
                        int nuevasFilas = scanner.nextInt();
                        System.out.print("Nuevas columnas: ");
                        int nuevasColumnas = scanner.nextInt();
                        scanner.nextLine();
                        HojaCalculo nuevaHoja = new HojaCalculo();
                        nuevaHoja.crearEstructura(nuevasFilas, nuevasColumnas);
                        for (int i = 0; i < Math.min(numFilas, nuevasFilas); i++) {
                            for (int j = 0; j < Math.min(numColumnas, nuevasColumnas); j++) {
                                String val = hoja.obtenerValor(i, j);
                                nuevaHoja.establecerValor(i, j, val);
                            }
                        }
                        hoja = nuevaHoja;
                        modificador = new Modificacion(hoja);
                        operaciones = new Calculadora(hoja);
                        numFilas = nuevasFilas;
                        numColumnas = nuevasColumnas;
                        filaActual = 0;
                        columnaActual = 0;
                        System.out.println("Area actualizada con exito.");
                    }
                    break;
                case 11:
                    salirHoja = true;
                    break;
                default:
                    System.out.println("Opcion invalida");
            }

        }

    }

    public static void subMenuAVL(Scanner scanner, arbolAVL arbol) {
        String opcion;
        do {
            System.out.println("\n----- SubMenu AVL -----");
            System.out.println("a. Insertar nodo");
            System.out.println("b. Eliminar nodo");
            System.out.println("c. Guardar AVL");
            System.out.println("d. Volver al menu principal");

            System.out.print("Seleccione una opcion: ");
            opcion = scanner.next();

            switch (opcion.toLowerCase()) {
                case "a":
                    System.out.print("Ingrese un numero a insertar: ");
                    if (scanner.hasNextInt()) {
                        int valorInsertar = scanner.nextInt();
                        arbol.insertar(valorInsertar);
                        arbol.mostrar();
                    } else {
                        System.out.println(" Valor no valido");
                        scanner.next();
                    }
                    break;

                case "b":
                    System.out.print("Ingrese un numero a eliminar: ");
                    if (scanner.hasNextInt()) {
                        int valorEliminar = scanner.nextInt();
                        arbol.eliminar(valorEliminar);
                        eliminarNodoDeBD(valorEliminar);
                        arbol.mostrar();
                    } else {
                        System.out.println(" Valor no valido");
                        scanner.next();
                    }
                    break;

                case "c":
                    arbol.guardarEnBD();
                    break;

                
                case "d":
                    System.out.println("Regresando al menu principal...");
                    break;

                default:
                    System.out.println("Opcion invalida");
            }

        } while (!opcion.equalsIgnoreCase("e"));
    }

    public static void eliminarNodoDeBD(int valor) {
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "DELETE FROM arbolAVL WHERE valor = ? AND idTipoArbol = 2";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, valor);
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Nodo " + valor + " eliminado también de la base de datos");
            } else {
                System.out.println(" Nodo no encontrado en la base de datos");
            }
        } catch (SQLException e) {
            System.out.println(" Error al eliminar de la base de datos:");
            System.out.println(e.getMessage());
        }
    }

    public static void guardarNodoEnBD(int valor) {
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "INSERT INTO arbolAVL (valor, idTipoArbol) VALUES (?, 2)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, valor);
            stmt.executeUpdate();
            System.out.println("Nodo " + valor + " guardado en BD");
        } catch (SQLException e) {
            System.out.println("Error al guardar en la base de datos:");
            System.out.println(e.getMessage());
        }
    }

}
