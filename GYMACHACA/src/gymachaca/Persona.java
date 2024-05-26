package gymachaca;

/**
 * La clase Persona representa a una persona con información básica como DNI, nombre, apellido, teléfono y dirección.
 * 
 * 
 * @author Alejandro Molina
 */
public class Persona {
	
	protected String dni;
	protected String nombre;		
	protected String apellido;
	protected String telefono;
	protected String direccion;
	
	
	
	/**
     * Crea una nueva instancia de Persona con el DNI especificado.
     * 
     * @param dni El Documento Nacional de Identidad de la persona
     */
	public Persona(String dni) {
		
		this.dni = dni;
	}
	  /**
     * Crea una nueva instancia de Persona con todos los atributos especificados.
     * 
     * @param dni El Documento Nacional de Identidad de la persona
     * @param nombre El nombre de la persona
     * @param apellido El apellido de la persona
     * @param telefono El número de teléfono de la persona
     * @param direccion La dirección de la persona
     */
	public Persona(String dni, String nombre, String apellido, String telefono, String direccion) {
		
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.direccion = direccion;
		
		
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	@Override
	public String toString() {
		return "Persona [dni=" + dni + ", nombre=" + nombre + ", apellido=" + apellido + ", telefono=" + telefono
				+ ", direccion=" + direccion + "]";
	}
	
	
	

}
