/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.excelgrupo6;

/**
 *
 * @author abics
 */
//import de las conexiones
// Importacion de clases necesarias
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

        // Menu principal
        while (!salir) {
            System.out.println("\n--- SISTEMA GRUPO 6 ---");
            System.out.println("1. Hoja de Calculo");
            System.out.println("2. Arbol AVL");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opcion: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar salto de linea

            switch (opcion) {
                case 1:
                    ejecutarHojaCalculo(scanner); // Llama al menu de hoja de calculo
                    break;
                case 2:
                    arbolAVL arbol = new arbolAVL(); // Crea el arbol AVL
                    subMenuAVL(scanner, arbol);     // Llama al submenu AVL
                    break;
                case 3:
                    salir = true;
                    System.out.println("Gracias por usar el sistema");
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        }

        scanner.close();
    }

    // Metodo que contiene el menu y funcionalidades de la hoja de calculo
    public static void ejecutarHojaCalculo(Scanner scanner) {
        System.out.print("Ingrese el numero de filas: ");
        int numFilas = scanner.nextInt();
        System.out.print("Ingrese el numero de columnas: ");
        int numColumnas = scanner.nextInt();
        scanner.nextLine(); // Limpiar salto

        HojaCalculo hoja = new HojaCalculo();
        hoja.crearEstructura(numFilas, numColumnas);
        Modificacion modificador = new Modificacion(hoja);
        Calculadora operaciones = new Calculadora(hoja);
        int filaActual = 0;
        int columnaActual = 0;
        boolean salirHoja = false;
        //
        //

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
            System.out.println("6. Cambiar a una posicion especifica");
            System.out.println("7. Realizar operaciones");
            System.out.println("8. Limpiar celda actual");
            System.out.println("9. Reemplazar un valor");
            System.out.println("10. Cambiar dimensiones");
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
                case 2: // Moverse abajo
                    if (filaActual >= numFilas - 1) {
                        System.out.print("Llmite inferior alcanzado. Desea agregar mas filas? (s/n): ");
                        if (scanner.nextLine().equalsIgnoreCase("s")) {
                            int nuevasFilas = numFilas + 1;
                            hoja.redimensionar(nuevasFilas, numColumnas);
                            numFilas = nuevasFilas;
                            System.out.println("Se agrego una nueva fila. Total: " + numFilas);
                        }
                    }
                    filaActual = Math.min(filaActual + 1, numFilas - 1);
                    break;
                case 3:
                        filaActual = Math.max(0, filaActual - 1);
                    break;
                case 4: // Moverse a la derecha
                    if (columnaActual >= numColumnas - 1) {
                        System.out.print("Limite derecho alcanzado. Desea agregar mas columnas? (s/n): ");
                        if (scanner.nextLine().equalsIgnoreCase("s")) {
                            int nuevasColumnas = numColumnas + 1;
                            hoja.redimensionar(numFilas, nuevasColumnas);
                            numColumnas = nuevasColumnas;
                            System.out.println("Se agrego una nueva columna. Total: " + numColumnas);
                        }
                    }
                    columnaActual = Math.min(columnaActual + 1, numColumnas - 1);
                    break;
                case 5: // Moverse a la izquierda
                    columnaActual = Math.max(0, columnaActual - 1);
                    break;
                case 6:
                    System.out.print("Ingrese la celda (Ej: A1): ");
                    String entrada = scanner.nextLine().toUpperCase();
                    if (entrada.length() >= 2 && Character.isLetter(entrada.charAt(0))) {
                        char letraColumna = entrada.charAt(0);
                        try {
                            int fila = Integer.parseInt(entrada.substring(1)) - 1;
                            int columna = letraColumna - 'A';
            
                            // Verificacion para ver si la posicon esta fuera de rango
                            if (fila >= numFilas || columna >= numColumnas) {
                                System.out.print("Posicion fuera de rango Desea redimensionar la hoja? (s/n): ");
                                if (scanner.nextLine().equalsIgnoreCase("s")) {
                                    // Calcular nuevas dimensiones (actual + diferencia necesaria + 1 como buffer)
                                    int nuevasFilas = Math.max(numFilas, fila + 1);
                                    int nuevasColumnas = Math.max(numColumnas, columna + 1);
                    
                                    // Redimension
                                    HojaCalculo nuevaHoja = new HojaCalculo();
                                    nuevaHoja.crearEstructura(nuevasFilas, nuevasColumnas);
                    
                                    // Copiar datos existentes
                                    for (int i = 0; i < numFilas; i++) {
                                        for (int j = 0; j < numColumnas; j++) {
                                            String val = hoja.obtenerValor(i, j);
                                            nuevaHoja.establecerValor(i, j, val);
                                        }
                                    }
                    
                                    hoja = nuevaHoja;
                                    modificador = new Modificacion(hoja);
                                    operaciones = new Calculadora(hoja);
                                    numFilas = nuevasFilas;
                                    numColumnas = nuevasColumnas;
                                    System.out.println("Hoja redimensionada a " + numFilas + " filas x " + numColumnas + " columnas");
                                } else {
                                    System.out.println("Operacion cancelada");
                                    break;
                                }
                            }
            
                            // Verificar los limites despues de posible redimension
                            if (fila >= 0 && fila < numFilas && columna >= 0 && columna < numColumnas) {
                                filaActual = fila;
                                columnaActual = columna;
                            } else {
                                System.out.println("Celda fuera de rango");
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
                    System.out.print("¿Desea limpiar esta celda? (s/n): ");
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
                        System.out.println("Area actualizada");
                    }
                    break;
                case 11:
                    salirHoja = true;
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        }
    }

    // Submenu del arbol AVL
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
                        if (arbol.contiene(valorInsertar)) {
                            System.out.println("El valor " + valorInsertar + " ya existe en el arbol");
                        } else {
                            arbol.insertar(valorInsertar);
                            arbol.mostrarInorden();
                            arbol.mostrarPreorden();
                            arbol.mostrarPostorden();
                        }
                    } else {
                        System.out.println(" Valor no valido");
                        scanner.next();
                    }
                    break;

                case "b":
                    if (arbol.estaVacio()) {
                        System.out.println(" El arbol esta vacio, no se puede eliminar");
                    } else {
                        System.out.print("Ingrese un numero a eliminar: ");
                        if (scanner.hasNextInt()) {
                            int valor = scanner.nextInt();

                            if (!arbol.contiene(valor)) {
                                System.out.println(" Ese valor no existe en el arbol");
                            } else {
                                arbol.eliminar(valor);                  // Elimina del AVL
                                eliminarNodoDeBD(valor);                // Elimina de la BD
                                System.out.println(" Valor eliminado correctamente");

                                // Mostrar los 3 recorridos
                                arbol.mostrarInorden();
                                arbol.mostrarPreorden();
                                arbol.mostrarPostorden();
                            }

                        } else {
                            System.out.println(" Entrada invalida solo se permiten numeros");
                            scanner.next(); // limpiar buffer
                        }
                    }
                    break;

                case "c":
                    if (arbol.estaVacio()) {
                        System.out.println(" El arbol esta vacio no se puede guardar");
                    } else {
                        arbol.guardarEnBD(); // Guarda en la base de datos
                        System.out.println(" Arbol guardado correctamente en la base de datos");

                        // Mostrar recorridos
                        arbol.mostrarInorden();
                        arbol.mostrarPreorden();
                        arbol.mostrarPostorden();
                    }

                    break;
                case "d":
                    System.out.println("Regresando al menu principal...");
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        } while (!opcion.equalsIgnoreCase("d"));
    }

    // Metodo para eliminar un nodo de la base de datos
    public static void eliminarNodoDeBD(int valor) {
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "DELETE FROM arbolAVL WHERE valor = ? AND idTipoArbol = 2";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, valor);
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Nodo " + valor + " eliminado de la base de datos");
            } else {
                System.out.println("Nodo no encontrado en la base de datos");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar de la base de datos:");
            System.out.println(e.getMessage());
        }
    }

    // Metodo opcional si deseas insertar nodos directamente (no usado ahora)
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
