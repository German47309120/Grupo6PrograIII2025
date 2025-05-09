/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pruebas;
/**
 *
 * @author abics
 */
import Estructura.arbolAVL;

public class TestAVL {
    public static void main(String[] args) {
        arbolAVL arbol = new arbolAVL();

        System.out.println(" Insertando nodos...");
        arbol.insertar(30);
        arbol.insertar(20);
        arbol.insertar(40);
        arbol.insertar(10);
        arbol.insertar(25);
        arbol.insertar(35);
        arbol.insertar(50);

        System.out.println("\n Arbol en inorden:");
        arbol.mostrar();

        System.out.println("\n️ Eliminando nodo 20...");
        arbol.eliminar(20);

        System.out.println("\n Arbol actualizado:");
        arbol.mostrar();

        System.out.println("\n Guardando en la base de datos...");
        arbol.guardarEnBD();
    }
}
