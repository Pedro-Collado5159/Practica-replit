package aplicacion; 
import java.io.FileWriter; 
import java.io.IOException; 
import java.io.PrintWriter; 
import java.util.List; 
import java.io.BufferedReader; 
import java.io.FileReader; 
import java.util.ArrayList;  
import dominio.Alumno;
import excepcion.*;
 
public class GestorNotas { 
 
    public void guardarAlumnos(List<Alumno> alumnos, String nombreFichero)  
            throws ErrorFicheroNotasException { 
                 
        // Uso de try-with-resources para PrintWriter y FileWriter 
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreFichero))) { 
             
            for (Alumno a : alumnos) { 
                // Formato de salida: "Nombre,Nota" 
                pw.println(a.getNombre() + "," + a.getNota()); 
            } 
             
        } catch (IOException e) { 
            // Encapsulación de la IOException en nuestra excepción personalizada 
            throw new ErrorFicheroNotasException( 
                "Error de I/O al guardar el fichero: " + nombreFichero, e); 
        } 
    } 


    public List<Alumno> cargarAlumnos(String nombreFichero)  
        throws ErrorFicheroNotasException { 
             
    List<Alumno> alumnos = new ArrayList<>(); 
    String linea = null; 
     
    // Uso de try-with-resources para BufferedReader y FileReader 
    try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) { 
         
        while ((linea = br.readLine()) != null) { 
             
            // Bloque interno para manejar NumberFormatException (Excepción No Verificada) 
            try { 
                String[] partes = linea.split(","); 
                // Comprobación mínima de formato 
                if (partes.length < 2) { 
                     throw new IllegalArgumentException("Línea sin separador de datos (',')."); 
                } 
 
                String nombre = partes[0].trim(); 
                double nota = Double.parseDouble(partes[1].trim());  
                 
                alumnos.add(new Alumno(nombre, nota)); 
                 
            } catch (IllegalArgumentException e) { 
                // El error de formato se MANEJA internamente y se registra. No se propaga. 
                System.err.println("ERROR de formato en la línea: '" + linea + "'. Se ignorará. Causa: " + e.getMessage()); 
            } 
        } 
        return alumnos; 
         
    } catch (IOException e) { 
        // El error de I/O se ENCAPSULA y se PROPAGA al método llamante. 
        throw new ErrorFicheroNotasException( 
            "Error de I/O al cargar el fichero: " + nombreFichero, e); 
    } 
}
} 