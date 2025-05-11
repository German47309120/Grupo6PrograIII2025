/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructura;

/**
 *
 * @author abics
 */
//import de las conexiones
import Conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class arbolAVL {

    private nodoAVL raiz;

    // Verifica si el arbol esta vacio
    public boolean estaVacio() {
        return raiz == null;
    }

    // Verifica si un valor ya existe
    public boolean contiene(int valor) {
        return obtenerRecurrente(raiz, valor);
    }

    private boolean obtenerRecurrente(nodoAVL nodo, int valor) {
        if (nodo == null) {
            return false;
        }
        if (valor == nodo.valor) {
            return true;
        }
        if (valor < nodo.valor) {
            return obtenerRecurrente(nodo.izquierda, valor);
        }
        return obtenerRecurrente(nodo.derecha, valor);
    }

    // Inserta un nodo en el arbol
    public void insertar(int valor) {
        raiz = agregarRecurrente(raiz, valor);
    }

    private nodoAVL agregarRecurrente(nodoAVL nodo, int valor) {
        if (nodo == null) {
            return new nodoAVL(valor);
        }
        if (valor < nodo.valor) {
            nodo.izquierda = agregarRecurrente(nodo.izquierda, valor);
        } else if (valor > nodo.valor) {
            nodo.derecha = agregarRecurrente(nodo.derecha, valor);
        } else {
            return nodo;
        }

        nodo.altura = 1 + Math.max(altura(nodo.izquierda), altura(nodo.derecha));
        return balancear(nodo);
    }

    // Elimina un nodo
    public void eliminar(int valor) {
        raiz = eliminarRecurrente(raiz, valor);
    }

    private nodoAVL eliminarRecurrente(nodoAVL nodo, int valor) {
        if (nodo == null) {
            return null;
        }

        if (valor < nodo.valor) {
            nodo.izquierda = eliminarRecurrente(nodo.izquierda, valor);
        } else if (valor > nodo.valor) {
            nodo.derecha = eliminarRecurrente(nodo.derecha, valor);
        } else {
            if (nodo.izquierda == null || nodo.derecha == null) {
                nodo = (nodo.izquierda != null) ? nodo.izquierda : nodo.derecha;
            } else {
                nodoAVL sucesor = minimo(nodo.derecha);
                nodo.valor = sucesor.valor;
                nodo.derecha = eliminarRecurrente(nodo.derecha, sucesor.valor);
            }
        }

        if (nodo == null) {
            return null;
        }

        nodo.altura = 1 + Math.max(altura(nodo.izquierda), altura(nodo.derecha));
        return balancear(nodo);
    }

    private nodoAVL minimo(nodoAVL nodo) {
        while (nodo.izquierda != null) {
            nodo = nodo.izquierda;
        }
        return nodo;
    }

    // Muestra recorrido inorden
    public void mostrar() {
        System.out.print("Recorrido inorden: ");
        inorden(raiz);
        System.out.println();
    }

    // Guarda el arbol completo en la BD
    public void guardarEnBD() {
        guardarRecurrente(raiz);
        System.out.println("AVL guardado en la base de datos.");
    }

    private void guardarRecurrente(nodoAVL nodo) {
        if (nodo != null) {
            guardarRecurrente(nodo.izquierda);
            almacenarBD(nodo.valor);
            guardarRecurrente(nodo.derecha);
        }
    }

    private void almacenarBD(int valor) {
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "INSERT INTO arbolAVL (valor, idTipoArbol) VALUES (?, 2)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, valor);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al guardar en la base de datos: " + e.getMessage());
        }
    }

    // Altura y balanceo
    private int altura(nodoAVL nodo) {
        return (nodo == null) ? 0 : nodo.altura;
    }

    private int valorBalance(nodoAVL nodo) {
        return (nodo == null) ? 0 : altura(nodo.izquierda) - altura(nodo.derecha);
    }

    private nodoAVL balancear(nodoAVL nodo) {
        int balance = valorBalance(nodo);

        if (balance > 1) {
            if (valorBalance(nodo.izquierda) < 0) {
                nodo.izquierda = rotarIzquierda(nodo.izquierda);
            }
            return rotarDerecha(nodo);
        }

        if (balance < -1) {
            if (valorBalance(nodo.derecha) > 0) {
                nodo.derecha = rotarDerecha(nodo.derecha);
            }
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    private nodoAVL rotarDerecha(nodoAVL y) {
        nodoAVL x = y.izquierda;
        nodoAVL T2 = x.derecha;
        x.derecha = y;
        y.izquierda = T2;

        y.altura = 1 + Math.max(altura(y.izquierda), altura(y.derecha));
        x.altura = 1 + Math.max(altura(x.izquierda), altura(x.derecha));

        return x;
    }

    private nodoAVL rotarIzquierda(nodoAVL x) {
        nodoAVL y = x.derecha;
        nodoAVL T2 = y.izquierda;
        y.izquierda = x;
        x.derecha = T2;

        x.altura = 1 + Math.max(altura(x.izquierda), altura(x.derecha));
        y.altura = 1 + Math.max(altura(y.izquierda), altura(y.derecha));

        return y;
    }

    // Mostrar en Inorden
    public void mostrarInorden() {
        System.out.print("Inorden: ");
        inorden(raiz);
        System.out.println();
    }

// Mostrar en Preorden
    public void mostrarPreorden() {
        System.out.print("Preorden: ");
        preorden(raiz);
        System.out.println();
    }

// Mostrar en Postorden
    public void mostrarPostorden() {
        System.out.print("Postorden: ");
        postorden(raiz);
        System.out.println();
    }

// Recorrido inorden (izquierda - raíz - derecha)
    private void inorden(nodoAVL nodo) {
        if (nodo != null) {
            inorden(nodo.izquierda);
            System.out.print(nodo.valor + " ");
            inorden(nodo.derecha);
        }
    }

// Recorrido preorden (raíz - izquierda - derecha)
    private void preorden(nodoAVL nodo) {
        if (nodo != null) {
            System.out.print(nodo.valor + " ");
            preorden(nodo.izquierda);
            preorden(nodo.derecha);
        }
    }

// Recorrido postorden (izquierda - derecha - raíz)
    private void postorden(nodoAVL nodo) {
        if (nodo != null) {
            postorden(nodo.izquierda);
            postorden(nodo.derecha);
            System.out.print(nodo.valor + " ");
        }
    }

}
