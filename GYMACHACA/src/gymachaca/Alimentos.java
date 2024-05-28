package gymachaca;
/**
 * La clase Alimentos representa un alimento con información detallada como código de alimento, nombre, precio por unidad,
 * calorías, cantidad disponible, estamina del producto y cantidad del cliente.
 * 
 * @autor Alejandro Molina Tenderoo
 */
public class Alimentos {

	protected int codAlimento;
	protected String nombre;
	protected double precioporunidad;
	protected int calorias;
	protected int cantidad_disponible;
	protected int estaminaprod;
	protected int catidadCliente ;
		
	
	
	  /**
     * Crea una nueva instancia de Alimentos con el código del alimento, nombre y cantidad del cliente especificados.
     * 
     * @param codAlimento El código del alimento
     * @param nombre El nombre del alimento
     * @param catidadCliente La cantidad del cliente del alimento
     */
	public Alimentos(int codAlimento, String nombre, int catidadCliente) {
		
		this.codAlimento = codAlimento;
		this.nombre = nombre;
		this.catidadCliente = catidadCliente;
	}

	 /**
     * Crea una nueva instancia de Alimentos con el código del alimento, nombre, calorías, estamina del producto y cantidad del cliente especificados.
     * 
     * @param codAlimento El código del alimento
     * @param nombre El nombre del alimento
     * @param calorias Las calorías del alimento
     * @param estaminaprod La estamina del producto del alimento
     * @param catidadCliente La cantidad del cliente del alimento
     */
	public Alimentos(int codAlimento, String nombre, int calorias, int estaminaprod, int catidadCliente) {
		
		this.codAlimento = codAlimento;
		this.nombre = nombre;
		this.calorias = calorias;
		this.estaminaprod = estaminaprod;
		this.catidadCliente = catidadCliente;
	}

	   /**
     * Crea una nueva instancia de Alimentos con el código del alimento, nombre, precio por unidad, calorías, cantidad disponible y estamina del producto especificados.
     * 
     * @param codAlimento El código del alimento
     * @param nombre El nombre del alimento
     * @param precioporunidad El precio por unidad del alimento
     * @param calorias Las calorías del alimento
     * @param cantidad_disponible La cantidad disponible del alimento
     * @param estaminaprod La estamina del producto del alimento
     */
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
