/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Operaciones;


import Modelo.HojaCalculo;
import java.util.Scanner;
/**
 *
 * @author abics
 */
public class Calculadora {
    
    private HojaCalculo hoja;
    private Scanner scanner;
    
    public Calculadora(HojaCalculo hoja) {
        this.hoja = hoja;
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Realiza operaciones matemáticas entre celdas
     * @return El resultado de la operación o null si hubo error
     */
    public String realizarOperacion() {
        System.out.println("\n--- Operaciones Matematicas ---");
        System.out.println("1. Suma");
        System.out.println("2. Resta");
        System.out.println("3. Multiplicacion");
        System.out.println("4. Division");
        System.out.print("Seleccione la operacion: ");
        
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea
        
        // Solicitar primera referencia
        System.out.print("Ingrese la referencia de la primera celda (ej: A1): ");
        String ref1 = scanner.nextLine().toUpperCase();
        
        // Solicitar segunda referencia
        System.out.print("Ingrese la referencia de la segunda celda (ej: B1): ");
        String ref2 = scanner.nextLine().toUpperCase();
        
        // Convertir referencias a índices
        int[] indices1 = convertirReferenciaAIndices(ref1);
        int[] indices2 = convertirReferenciaAIndices(ref2);
        
        if (indices1 == null || indices2 == null) {
            System.out.println("Error: Referencia de celda invalida.");
            return null;
        }
        
        // Obtener valores de las celdas
        String valorStr1 = hoja.obtenerValor(indices1[0], indices1[1]);
        String valorStr2 = hoja.obtenerValor(indices2[0], indices2[1]);
        
        // Verificar que los valores no sean nulos
        if (valorStr1 == null || valorStr2 == null || valorStr1.isEmpty() || valorStr2.isEmpty()) {
            System.out.println("Nel mijo: Una o ambas celdas están vacias");
            return null;
        }
        
        // Intentar convertir los valores a números
        double valor1, valor2;
        try {
            valor1 = Double.parseDouble(valorStr1);
            valor2 = Double.parseDouble(valorStr2);
        } catch (NumberFormatException e) {
            System.out.println("Error: Los valores de las celdas deben ser numericos.");
            return null;
        }
        
        // Realizar la operación seleccionada
        double resultado = 0;
        String operador = "";
        
        switch (opcion) {
            case 1: // Suma
                resultado = valor1 + valor2;
                operador = "+";
                break;
            case 2: // Resta
                resultado = valor1 - valor2;
                operador = "-";
                break;
            case 3: // Multiplicación
                resultado = valor1 * valor2;
                operador = "*";
                break;
            case 4: // División
                if (valor2 == 0) {
                    System.out.println("Error: Division por cero.");
                    return null;
                }
                resultado = valor1 / valor2;
                operador = "/";
                break;
            default:
                System.out.println("Error: Operacion no valida.");
                return null;
        }
        
        // Formatear resultado, eliminando decimales si es un numero entero
        String resultadoStr;
        if (resultado == (int) resultado) {
            resultadoStr = String.valueOf((int) resultado);
        } else {
            resultadoStr = String.valueOf(resultado);
        }
        
        System.out.println("Operacion: " + valorStr1 + " " + operador + " " + valorStr2 + " = " + resultadoStr);
        
        // Preguntar si se desea guardar el resultado en alguna celda
        System.out.print("¿Desea guardar el resultado en una celda? (S/N): ");
        String respuesta = scanner.nextLine().toUpperCase();
        
        if (respuesta.equals("S")) {
            System.out.print("Ingrese la referencia de la celda destino (ej: C1): ");
            String refDestino = scanner.nextLine().toUpperCase();
            int[] indicesDestino = convertirReferenciaAIndices(refDestino);
            
            if (indicesDestino != null) {
                hoja.establecerValor(indicesDestino[0], indicesDestino[1], resultadoStr);
                System.out.println("Resultado guardado en la celda " + refDestino);
            } else {
                System.out.println("Error: Referencia de celda destino invalida.");
            }
        }
        
        return resultadoStr;
    }
    
    
    private int[] convertirReferenciaAIndices(String referencia) {
        try {
            // Separar letras (columnas) y números (filas)
            String letras = referencia.replaceAll("\\d", "");
            String numeros = referencia.replaceAll("\\D", "");
            
            if (letras.isEmpty() || numeros.isEmpty()) {
                return null;
            }
            
            // Convertir letras a índice de columna (A=0, B=1, etc.)
            int columna = 0;
            for (int i = 0; i < letras.length(); i++) {
                columna = columna * 26 + (letras.charAt(i) - 'A' + 1);
            }
            columna -= 1; // Ajustar para base 0
            
            // Convertir número a índice de fila (1=0, 2=1, etc.)
            int fila = Integer.parseInt(numeros) - 1;
            
            // Verificar que los índices son válidos
            if (fila < 0 || columna < 0) {
                return null;
            }
            
            return new int[] {fila, columna};
        } catch (Exception e) {
            return null;
        }
    }
    
}
