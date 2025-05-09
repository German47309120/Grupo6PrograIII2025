/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructura;

/**
 *
 * @author abics
 */
import Conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class arbolAVL {

    private nodoAVL raiz;

    public void insertar(int valor) {
        if (buscar(raiz, valor)) {
            System.out.println("Ese valor ya existe en el arbol ");
        } else {
            raiz = insertarRec(raiz, valor);
            System.out.println("Valor insertado correctamente ");
        }
    }

    public void eliminar(int valor) {
        if (!buscar(raiz, valor)) {
            System.out.println("Ese valor no existe en el arbol ");
        } else {
            raiz = eliminarRec(raiz, valor);
            System.out.println("Valor eliminado correctamente ");
        }
    }

    public void mostrar() {
        System.out.println("Inorden:");
        inorden(raiz);
        System.out.println("\nPreorden:");
        preorden(raiz);
        System.out.println("\nPostorden:");
        postorden(raiz);
    }

    private void preorden(nodoAVL nodo) {
        if (nodo != null) {
            System.out.print(nodo.valor + " ");
            preorden(nodo.izquierda);
            preorden(nodo.derecha);
        }
    }

    private void postorden(nodoAVL nodo) {
        if (nodo != null) {
            postorden(nodo.izquierda);
            postorden(nodo.derecha);
            System.out.print(nodo.valor + " ");
        }
    }

    public void guardarEnBD() {
        guardarRecursivo(raiz);
    }

    // --- Métodos auxiliares privados ---
    private boolean buscar(nodoAVL nodo, int valor) {
        if (nodo == null) {
            return false;
        }
        if (valor == nodo.valor) {
            return true;
        }
        return valor < nodo.valor ? buscar(nodo.izquierda, valor) : buscar(nodo.derecha, valor);
    }

    private void inorden(nodoAVL nodo) {
        if (nodo != null) {
            inorden(nodo.izquierda);
            System.out.print(nodo.valor + " ");
            inorden(nodo.derecha);
        }
    }

    private void guardarRecursivo(nodoAVL nodo) {
        if (nodo != null) {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "INSERT INTO arbolAVL (valor, idTipoArbol) VALUES (?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, nodo.valor);
                stmt.setInt(2, 2); // tipo 2 = AVL
                stmt.executeUpdate();
                System.out.println("Nodo " + nodo.valor + " guardado en BD ");
            } catch (Exception e) {
                System.out.println("Error guardando nodo " + nodo.valor + ": " + e.getMessage());
            }
            guardarRecursivo(nodo.izquierda);
            guardarRecursivo(nodo.derecha);
        }
    }

    // --- Inserción, eliminación y rotaciones ---
    private nodoAVL insertarRec(nodoAVL nodo, int valor) {
        if (nodo == null) {
            return new nodoAVL(valor);
        }

        if (valor < nodo.valor) {
            nodo.izquierda = insertarRec(nodo.izquierda, valor);
        } else if (valor > nodo.valor) {
            nodo.derecha = insertarRec(nodo.derecha, valor);
        } else {
            return nodo;
        }

        actualizarAltura(nodo);
        return balancear(nodo);
    }

    private nodoAVL eliminarRec(nodoAVL nodo, int valor) {
        if (nodo == null) {
            return null;
        }

        if (valor < nodo.valor) {
            nodo.izquierda = eliminarRec(nodo.izquierda, valor);
        } else if (valor > nodo.valor) {
            nodo.derecha = eliminarRec(nodo.derecha, valor);
        } else {
            if (nodo.izquierda == null || nodo.derecha == null) {
                nodo = (nodo.izquierda != null) ? nodo.izquierda : nodo.derecha;
            } else {
                nodoAVL sucesor = getMin(nodo.derecha);
                nodo.valor = sucesor.valor;
                nodo.derecha = eliminarRec(nodo.derecha, sucesor.valor);
            }
        }

        if (nodo != null) {
            actualizarAltura(nodo);
            nodo = balancear(nodo);
        }

        return nodo;
    }

    private nodoAVL getMin(nodoAVL nodo) {
        while (nodo.izquierda != null) {
            nodo = nodo.izquierda;
        }
        return nodo;
    }

    private void actualizarAltura(nodoAVL nodo) {
        int altIzq = (nodo.izquierda == null) ? 0 : nodo.izquierda.altura;
        int altDer = (nodo.derecha == null) ? 0 : nodo.derecha.altura;
        nodo.altura = 1 + Math.max(altIzq, altDer);
    }

    private int getBalance(nodoAVL nodo) {
        int altIzq = (nodo.izquierda == null) ? 0 : nodo.izquierda.altura;
        int altDer = (nodo.derecha == null) ? 0 : nodo.derecha.altura;
        return altIzq - altDer;
    }

    private nodoAVL balancear(nodoAVL nodo) {
        int balance = getBalance(nodo);

        if (balance > 1) {
            if (getBalance(nodo.izquierda) < 0) {
                nodo.izquierda = rotacionIzquierda(nodo.izquierda);
            }
            return rotacionDerecha(nodo);
        }

        if (balance < -1) {
            if (getBalance(nodo.derecha) > 0) {
                nodo.derecha = rotacionDerecha(nodo.derecha);
            }
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    private nodoAVL rotacionDerecha(nodoAVL y) {
        nodoAVL x = y.izquierda;
        nodoAVL T2 = x.derecha;

        x.derecha = y;
        y.izquierda = T2;

        actualizarAltura(y);
        actualizarAltura(x);

        return x;
    }

    private nodoAVL rotacionIzquierda(nodoAVL x) {
        nodoAVL y = x.derecha;
        nodoAVL T2 = y.izquierda;

        y.izquierda = x;
        x.derecha = T2;

        actualizarAltura(x);
        actualizarAltura(y);

        return y;
    }
}
