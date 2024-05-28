package gymachaca;

import java.util.ArrayList;

/**
 * La clase Cliente representa a un cliente que extiende de la clase Persona 
 * e incluye atributos adicionales como estamina, bono, cartera y lista de compra.
 * 
 * @autor Alejandro Molina
 */
public class Cliente extends Persona {
	
	protected static final int ESTAMINA_DEF = 100;
	protected static final double CARTERA_DEF = 200;
	

	protected int stamina;
	protected Bono bono;
	protected double cartera;
	protected ArrayList<Alimentos>listadecompra;
	
	
	

    /**
     * Crea una nueva instancia de Cliente con el DNI especificado.
     * 
     * @param dni El Documento Nacional de Identidad del cliente
     */
	public Cliente(String dni) {
		super(dni);
		this.listadecompra = listadecompra;
	}

	
	
	public Cliente(String dni, String nombre, String apellido, String telefono, String direccion, int stamina, Bono bono,
			double cartera) {
		super(dni, nombre, apellido, telefono, direccion);
		this.stamina = stamina;
		this.bono = bono;
		this.cartera = cartera;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public Bono getBono() {
		return bono;
	}

	public void setBono(Bono bono) {
		this.bono = bono;
	}

	public double getCartera() {
		return cartera;
	}

	public void setCartera(double cartera) {
		this.cartera = cartera;
	}

	public static int getEstaminaDef() {
		return ESTAMINA_DEF;
	}

	public static double getCarteraDef() {
		return CARTERA_DEF;
	}
	
	
	public void agregarCompra(Alimentos alimento) {
		listadecompra.add(alimento);
    }
	

	

	@Override
	public String toString() {
	    return "Cliente" +
	            "\nDNI: " + dni +
	            "\nNombre: " + nombre +
	            "\nApellido: " + apellido +
	            "\nTeléfono: " + telefono +
	            "\nDirección: " + direccion +
	            "\nStamina: " + stamina +
	            "\nBono: " + bono +
	            "\nCartera: " + cartera;
	}


	
}
