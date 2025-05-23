/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Memoria;

/**
 *
 * @author abics
 */
import java.util.*;
public class Ejecucion {
      public static void verMenu(Scanner scanner) {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- ADMINISTRACION DE MEMORIA ---");
            System.out.println("a. Primer ajuste");
            System.out.println("b. Mejor ajuste");
            System.out.println("c. Peor ajuste");
            System.out.println("d. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");

            String opcion = scanner.nextLine().toLowerCase();

            switch (opcion) {
                case "a":
                    primerAjuste.ejecutar(scanner);
                    break;
                case "b":
                    mejorAjuste.ejecutar(scanner);
                    break;
                case "c":
                    peorAjuste.ejecutar(scanner);
                    break;
                case "d":
                    volver = true;
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    public static void Menu(Scanner scanner) {
       throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
