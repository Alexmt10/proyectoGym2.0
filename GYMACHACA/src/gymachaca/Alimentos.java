package gymachaca;

public class Alimentos {

	protected int codAlimento;
	protected String nombre;
	protected double precioporunidad;
	protected int calorias;
	protected int cantidad_disponible;
	protected int estaminaprod;
	protected int catidadCliente ;
		
	
	
	



	public Alimentos(int codAlimento, String nombre, int catidadCliente) {
		
		this.codAlimento = codAlimento;
		this.nombre = nombre;
		this.catidadCliente = catidadCliente;
	}


	public Alimentos(int codAlimento, String nombre, int calorias, int estaminaprod, int catidadCliente) {
		
		this.codAlimento = codAlimento;
		this.nombre = nombre;
		this.calorias = calorias;
		this.estaminaprod = estaminaprod;
		this.catidadCliente = catidadCliente;
	}


	public Alimentos(int codAlimento, String nombre, double precioporunidad, int calorias, int cantidad_disponible,
			int estaminaprod) {
		
		this.codAlimento = codAlimento;
		this.nombre = nombre;
		this.precioporunidad = precioporunidad;
		this.calorias = calorias;
		this.cantidad_disponible = cantidad_disponible;
		this.estaminaprod = estaminaprod;
	}


	public int getCodAlimento() {
		return codAlimento;
	}


	public void setCodAlimento(int codAlimento) {
		this.codAlimento = codAlimento;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public double getPrecioporunidad() {
		return precioporunidad;
	}


	public void setPrecioporunidad(double precioporunidad) {
		this.precioporunidad = precioporunidad;
	}


	public int getCalorias() {
		return calorias;
	}


	public void setCalorias(int calorias) {
		this.calorias = calorias;
	}


	public int getCantidad_disponible() {
		return cantidad_disponible;
	}


	public void setCantidad_disponible(int cantidad_disponible) {
		this.cantidad_disponible = cantidad_disponible;
	}

	

	public int getEstaminaprod() {
		return estaminaprod;
	}


	public void setEstaminaprod(int estaminaprod) {
		this.estaminaprod = estaminaprod;
	}


	@Override
	public String toString() {
		return "Alimentos [codAlimento=" + codAlimento + ", nombre=" + nombre + ", precioporunidad=" + precioporunidad
				+ ", calorias=" + calorias + ", cantidad_disponible=" + cantidad_disponible + ", estaminaprod="
				+ estaminaprod + "]";
	}
	
	
	
	
}
