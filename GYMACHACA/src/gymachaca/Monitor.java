package gymachaca;

/**
 * La clase Monitor representa a un monitor que extiende de la clase Persona
 * e incluye un atributo adicional para el tipo de monitor.
 * 
 * @autor Alejandro Molina
 */
public class Monitor extends Persona{
	
	protected Tipo tipo;

	

    /**
     * Crea una nueva instancia de Monitor con todos los atributos especificados.
     * 
     * @param dni El Documento Nacional de Identidad del monitor
     * @param nombre El nombre del monitor
     * @param apellido El apellido del monitor
     * @param telefono El número de teléfono del monitor
     * @param direccion La dirección del monitor
     * @param tipo El tipo de clase que da el monitor
     */
	public Monitor(String dni, String nombre, String apellido, String telefono, String direccion, Tipo tipo) {
		super(dni, nombre, apellido, telefono, direccion);
		this.tipo = tipo;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
	    return "Monitor: \n" +
	            "Nombre: " + nombre + "\n" +
	            "Apellido: " + apellido + "\n" +
	            "Teléfono: " + telefono + "\n" +
	            "Dirección: " + direccion + "\n" +
	            "Tipo: " + tipo;
	}
	

}
