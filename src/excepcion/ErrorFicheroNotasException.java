package excepcion; 
public class ErrorFicheroNotasException extends Exception { 
// Constructor para encapsular la causa original 
public ErrorFicheroNotasException(String mensaje, Throwable causa) { 
super(mensaje, causa); 
} 
// Constructor simple 
public ErrorFicheroNotasException(String mensaje) { 
super(mensaje); 
} 
}