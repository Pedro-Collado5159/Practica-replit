package aplicacion; 
import dominio.Alumno; 
import excepcion.ErrorFicheroNotasException; 
import java.io.FileWriter; 
import java.util.Arrays; 
import java.util.List; 
import java.io.PrintWriter;  
import java.io.IOException; 

 
public class Main { 
    public static void main(String[] args) { 
        String FICHERO_PRUEBA = "notas_test.txt"; 
        GestorNotas gestor = new GestorNotas(); 
         
        List<Alumno> listaInicial = Arrays.asList( 
            new Alumno("Juan", 7.5), 
            new Alumno("Ana", 9.25), 
            new Alumno("Roberto", 3.0) 
        ); 
 
        // --- 1. Escritura e Inserción de un Error de Formato --- 
        try { 
            gestor.guardarAlumnos(listaInicial, FICHERO_PRUEBA); 
            System.out.println(" Datos iniciales escritos."); 
             
            // Simulación de un error de formato añadido al fichero (para probar el catch interno) 
            try (PrintWriter pw = new PrintWriter(new FileWriter(FICHERO_PRUEBA, true))) { 
                 pw.println("Maria Lopez,nota_mala_aqui");  
            } 
             
        } catch (ErrorFicheroNotasException | IOException e) { 
            System.err.println("\n*** ERROR CRÍTICO AL INICIALIZAR DATOS. ***"); 
            System.err.println(e.getMessage()); 
            return; 
        } 
 
        // --- 2. Lectura y Captura de Errores Propagados --- 
        try { 
            System.out.println("\n--- Intentando cargar los alumnos ---"); 
            List<Alumno> listaCargada = gestor.cargarAlumnos(FICHERO_PRUEBA); 
             
            System.out.println("\n--- Alumnos cargados correctamente: ---"); 
listaCargada.forEach(System.out::println); 
} catch (ErrorFicheroNotasException e) { 
// Captura final de la excepción personalizada 
System.err.println("\n*** ERROR EN EL PROCESO DE CARGA DE DATOS. ***"); 
System.err.println("Causa principal: " + e.getMessage()); 
// Imprimir la causa original de la I/O 
System.err.println("Detalles del error original: " + e.getCause().getMessage());  
} 
} 
}