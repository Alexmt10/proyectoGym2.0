package gymachaca;

public class Monitor extends Persona{
	
	protected Tipo tipo;

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
