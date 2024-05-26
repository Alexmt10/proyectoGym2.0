package gymachaca;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * La clase Main es el punto de entrada de la aplicación y contiene 
 * el método principal para interactuar con el sistema de gestión de gimnasio.
 * 
 * @autor Alejandro Molina
 */

public class Main {
	
	  /**
     * El método principal que ejecuta el menú de opciones para interactuar
     *  con el sistema de gestión de gimnasio.
     * 
     */
	public static void main(String[] args) {
		PersonaDAO bd = new PersonaDAO();
		ComprarDao bd1 = new ComprarDao();
		EjerciciosDAO bd2 = new EjerciciosDAO();
	
		var sc = new Scanner(System.in);
		ArrayList<Persona> listapersonas = new ArrayList<Persona>();
		ArrayList<Alimentos> listaAlimentos = new ArrayList<Alimentos>();
		
		
		  // Creación de ejemplos de personas
		    Persona persona1 = new Cliente("88890878J", "Juan", "González", "123456789", "Calle A, 123", 0, null, 0);
	        Persona persona2 = new Persona("222222222", "María", "López", "987654321", "Calle B, 456");
	        Persona persona3 = new Persona("31232312P", "Carlos", "Martínez", "555555555", "Calle C, 789");
	        listapersonas.add(persona1);
	        listapersonas.add(persona2);
	        listapersonas.add(persona3);
	        
	        

		int opcion;
	        do {
	        	
	        	System.out.println("--------------------------------------------");
	        	System.out.println("1. Para crear persona (Cliente o Monitor)");	        	
	        	System.out.println("2. Para que te muestre la informacion del cliente seleccionado");
	        	System.out.println("3. Para que te muestre la informacion del monitor seleccionado");
	        	System.out.println("4. Para modificar la informacion de del cliente ");
	        	System.out.println("5. Para modificar la informacion de del monitor ");
	        	System.out.println("6. Para eliminar un cliente ");
	        	System.out.println("7. Para eliminar un monitor");
	        	System.out.println("8. Para realizar una compra |");
	        	System.out.println("9. Para ingerir alimentos o suplementos para ganar estamina");
	        	System.out.println("10. Para que un cliente realice un ejercicio");
	        	
	        	 opcion = sc.nextInt();

				switch (opcion) {
				case 1:
				bd.crearPersona();
					break;
				case 2:
				bd.leerCliente();
				break;
				case 3:
				bd.leerMonitor();
				break;	
				case 4:
				bd.modificarCliente();
					break;
				case 5:
				bd.modificarMonitor();
					break;
				case 6:
				bd.elemininarCliente();
				break;
				case 7:
				bd.elemininarMonitor();
				break;
				case 8:
				bd1.comprar();
				break;
				case 9:
				bd1.consumirAlimentos();
				break;
				case 10:
				bd2.realizarActividad();
				break;
				
				}
	        }while (opcion != 11);
	
	
	}
	
		

	}


