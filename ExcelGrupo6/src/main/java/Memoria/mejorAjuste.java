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
public class mejorAjuste {
 public static void ejecutar(Scanner scanner) {
        int[] memoria;

        System.out.println("Seleccione el modo de ingreso de memoria:");
        System.out.println("1. Manual");
        System.out.println("2. Aleatorio");
        System.out.print("Opcion: ");
        int modo = scanner.nextInt();

        if (modo != 1 && modo != 2) {
            System.out.println("Opcion invalida");
            scanner.nextLine();
            return;
        }

        if (modo == 1) {
            System.out.print("Ingrese la cantidad de bloques de memoria: ");
            int n = scanner.nextInt();
            memoria = new int[n];

            for (int i = 0; i < n; i++) {
                System.out.print("Dimension del bloque " + (i + 1) + ": ");
                memoria[i] = scanner.nextInt();
            }
        } else {
            Random rand = new Random();
            int n = rand.nextInt(6) + 5;
            memoria = new int[n];

            for (int i = 0; i < n; i++) {
                memoria[i] = rand.nextInt(901) + 100;
            }
        }

        System.out.print("Ingrese la dimension del proceso: ");
        int proceso = scanner.nextInt();
        scanner.nextLine();

        int[] memoriaAntes = Arrays.copyOf(memoria, memoria.length);

        System.out.println("\n--- Mejor Ajuste ---");

        int mejorPunto = -1;
        for (int i = 0; i < memoria.length; i++) {
            if (memoria[i] >= proceso) {
                if (mejorPunto == -1 || memoria[i] < memoria[mejorPunto]) {
                    mejorPunto = i;
                }
            }
        }

        boolean asignado = false;
        if (mejorPunto != -1) {
            memoria[mejorPunto] -= proceso;
            asignado = true;
        }

        System.out.print("ANTES:   ");
        for (int m : memoriaAntes) {
            System.out.print("[" + m + "] ");
        }
        System.out.println();

        System.out.print("DESPUES: ");
        for (int i = 0; i < memoria.length; i++) {
            if (i == mejorPunto && asignado) {
                System.out.print("[" + memoria[i] + " <- " + proceso + "] ");
            } else {
                System.out.print("[" + memoria[i] + "] ");
            }
        }
        System.out.println();

        if (!asignado) {
            System.out.println("\nNingun bloque tiene suficiente dimension para alojar el proceso.");
        }
    }
}