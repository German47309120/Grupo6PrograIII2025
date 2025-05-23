/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author abics
 */
public class HojaCalculo {
  
    private Fila primeraFila;
    private Columna primeraColumna;
    
    public HojaCalculo() {
        this.primeraFila = null;
        this.primeraColumna = null;
    }
    
    // Método para crear la estructura básica de la hoja
    public void crearEstructura(int numFilas, int numColumnas) {
        // Crear primera fila y primera columna
        primeraFila = new Fila();
        primeraColumna = new Columna();
        
        // Crear el resto de filas
        Fila filaActual = primeraFila;
        for (int i = 1; i < numFilas; i++) {
            Fila nuevaFila = new Fila();
            filaActual.setFilaAbajo(nuevaFila);
            filaActual = nuevaFila;
        }
        
        // Crear el resto de columnas
        Columna columnaActual = primeraColumna;
        for (int i = 1; i < numColumnas; i++) {
            Columna nuevaColumna = new Columna();
            columnaActual.setColumnaDerecha(nuevaColumna);
            columnaActual = nuevaColumna;
        }
        
        // Inicializar todas las celdas con valor vacío
        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {
                establecerValor(i, j, "");
            }
        }
    }
    
    // Método para establecer un valor en coordenadas específicas
    public void establecerValor(int indiceFila, int indiceColumna, String valor) {
        // Obtener la fila correspondiente
        Fila filaObjetivo = obtenerFila(indiceFila);
        if (filaObjetivo == null) {
            System.out.println("Fila no encontrada: " + indiceFila);
            return;
        }
        
        // Buscar o crear la celda en la posición correcta dentro de la fila
        Celda celdaActual = filaObjetivo.getPrimeraCelda();
        int posActual = 0;
        
        // Si es la primera celda
        if (indiceColumna == 0) {
            if (celdaActual == null) {
                filaObjetivo.agregarCelda(valor);
            } else {
                celdaActual.setValor(valor);
            }
            return;
        }
        
        // Buscar la posición correcta
        while (celdaActual != null && posActual < indiceColumna - 1) {
            celdaActual = celdaActual.getDerecha();
            posActual++;
        }
        
        // Si llegamos al final y aun no alcanzamos la posición deseada
        if (celdaActual == null) {
            // Necesitamos agregar celdas hasta llegar a la posición deseada
            for (int i = 0; i <= indiceColumna; i++) {
                filaObjetivo.agregarCelda(i == indiceColumna ? valor : "");
            }
        } else if (celdaActual.getDerecha() == null && posActual == indiceColumna - 1) {
            // Estamos justo antes de la posición deseada y no hay siguiente
            Celda nuevaCelda = new Celda(valor);
            celdaActual.setDerecha(nuevaCelda);
        } else if (posActual == indiceColumna - 1) {
            // Estamos justo antes de la posición deseada
            celdaActual.getDerecha().setValor(valor);
        }
        
        // También actualizamos la estructura por columnas
        actualizarEnlacesVerticales(indiceFila, indiceColumna, valor);
    }
    
    // Método auxiliar para actualizar enlaces verticales
    private void actualizarEnlacesVerticales(int indiceFila, int indiceColumna, String valor) {
        // Obtener la columna correspondiente
        Columna columnaObjetivo = obtenerColumna(indiceColumna);
        if (columnaObjetivo == null) {
            System.out.println("Columna no encontrada: " + indiceColumna);
            return;
        }
        
        // Asegurarnos de que la columna tenga la celda correcta
        Celda celdaActual = columnaObjetivo.getPrimeraCelda();
        int posActual = 0;
        
        // Similar a la logica de filas pero para columnas
        if (indiceFila == 0) {
            if (celdaActual == null) {
                columnaObjetivo.agregarCelda(valor);
            } else {
                celdaActual.setValor(valor);
            }
            return;
        }
        
        while (celdaActual != null && posActual < indiceFila - 1) {
            celdaActual = celdaActual.getAbajo();
            posActual++;
        }
        
        if (celdaActual == null) {
            for (int i = 0; i <= indiceFila; i++) {
                columnaObjetivo.agregarCelda(i == indiceFila ? valor : "");
            }
        } else if (celdaActual.getAbajo() == null && posActual == indiceFila - 1) {
            Celda nuevaCelda = new Celda(valor);
            celdaActual.setAbajo(nuevaCelda);
        } else if (posActual == indiceFila - 1) {
            celdaActual.getAbajo().setValor(valor);
        }
    }
    
    // Método para obtener el valor en una coordenada específica
    public String obtenerValor(int indiceFila, int indiceColumna) {
        Fila filaObjetivo = obtenerFila(indiceFila);
        if (filaObjetivo == null) {
            return null;
        }
        
        Celda celdaActual = filaObjetivo.getPrimeraCelda();
        int posActual = 0;
        
        while (celdaActual != null && posActual < indiceColumna) {
            celdaActual = celdaActual.getDerecha();
            posActual++;
        }
        
        if (celdaActual == null) {
            return null;
        }
        
        return celdaActual.getValor();
    }
    
    // Método auxiliar para obtener una fila por índice
    private Fila obtenerFila(int indice) {
        if (indice < 0 || primeraFila == null) {
            return null;
        }
        
        Fila filaActual = primeraFila;
        int posActual = 0;
        
        while (filaActual != null && posActual < indice) {
            filaActual = filaActual.getFilaAbajo();
            posActual++;
        }
        
        return filaActual;
    }
    
    // Método auxiliar para obtener una columna por índice
    private Columna obtenerColumna(int indice) {
        if (indice < 0 || primeraColumna == null) {
            return null;
        }
        
        Columna columnaActual = primeraColumna;
        int posActual = 0;
        
        while (columnaActual != null && posActual < indice) {
            columnaActual = columnaActual.getColumnaDerecha();
            posActual++;
        }
        
        return columnaActual;
    }
    
    // Método para mostrar la hoja de cálculo con encabezados
    public void mostrarHoja(int numFilas, int numColumnas) {
    System.out.println("Contenido de la Hoja de Calculo:");

    // Imprimir encabezado de columnas (letras)
    System.out.print("\t");
    for (int col = 0; col < numColumnas; col++) {
        System.out.print((char) ('A' + col) + "\t");
    }
    System.out.println();

    // Imprimir cada fila con su número al inicio
    for (int i = 0; i < numFilas; i++) {
        System.out.print((i + 1) + "\t"); // número de fila
        for (int j = 0; j < numColumnas; j++) {
            String valor = obtenerValor(i, j);
            System.out.print((valor != null && !valor.isEmpty() ? valor : "-") + "\t");
        }
        System.out.println();
    }
}
    
    public void redimensionar(int nuevasFilas, int nuevasColumnas) {
    HojaCalculo nuevaHoja = new HojaCalculo();
    nuevaHoja.crearEstructura(nuevasFilas, nuevasColumnas);
    
    // Copiar datos existentes
    Fila filaActual = primeraFila;
    for (int i = 0; i < nuevasFilas && filaActual != null; i++) {
        Celda celdaActual = filaActual.getPrimeraCelda();
        for (int j = 0; j < nuevasColumnas && celdaActual != null; j++) {
            nuevaHoja.establecerValor(i, j, celdaActual.getValor());
            celdaActual = celdaActual.getDerecha();
        }
        filaActual = filaActual.getFilaAbajo();
    }
    
    // Se actualiza la estructura
    this.primeraFila = nuevaHoja.primeraFila;
    this.primeraColumna = nuevaHoja.primeraColumna;
}
    
    public Celda obtenerCelda(int indiceFila, int indiceColumna) {
    Fila filaObjetivo = obtenerFila(indiceFila);
    if (filaObjetivo == null) {
        return null;
    }
    
    Celda celdaActual = filaObjetivo.getPrimeraCelda();
    int posActual = 0;
    
    while (celdaActual != null && posActual < indiceColumna) {
        celdaActual = celdaActual.getDerecha();
        posActual++;
    }
    
    return celdaActual;
}

// Navegar a la celda de abajo desde una posicion específica
    public Celda navegarAbajo(int indiceFila, int indiceColumna) {
    Celda celdaActual = obtenerCelda(indiceFila, indiceColumna);
    if (celdaActual == null) {
        return null;
    }
    
    return celdaActual.getAbajo();
}

// Navegar a la celda de la derecha desde una posición específica
    public Celda navegarDerecha(int indiceFila, int indiceColumna) {
    Celda celdaActual = obtenerCelda(indiceFila, indiceColumna);
    if (celdaActual == null) {
        return null;
    }
    
    return celdaActual.getDerecha();
}

// Método para verificar si existe una celda abajo
    public boolean existeCeldaAbajo(int indiceFila, int indiceColumna) {
    return navegarAbajo(indiceFila, indiceColumna) != null;
}

// Método para verificar si existe una celda a la derecha
    public boolean existeCeldaDerecha(int indiceFila, int indiceColumna) {
    return navegarDerecha(indiceFila, indiceColumna) != null;
}
    
}
