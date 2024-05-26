package gymachaca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La clase EjerciciosDAO gestiona las actividades de ejercicio realizadas por los clientes
 * en la base de datos del gimnasio.
 * 
 * <p>Incluye métodos para conectar con la base de datos y realizar actividades de pesas y cardio.</p>
 * 
 * @autor Alejandro Molina
 */
public class EjerciciosDAO {

	private Connection conexion;

	private final String USUARIO = "root";
	private final String PASSWORD = "Alex22m22";
	private final String MAQUINA = "localhost";
	private final String BD = "Gymachaca";

	  /**
     * Constructor que establece la conexión con la base de datos.
     */
    public EjerciciosDAO() {
        conexion = conectar();
    }

    /**
     * Establece la conexión con la base de datos.
     * 
     * @return la conexión a la base de datos, o null si no se pudo conectar.
     */
	private Connection conectar() {
		Connection con = null;
		String url = "jdbc:mysql://" + MAQUINA + "/" + BD;
		try {
			con = DriverManager.getConnection(url, USUARIO, PASSWORD);
		} catch (SQLException e) {
			System.out.println("Error al conectar al SGBD");
		}
		return con;
	}

    /**
     * Permite a un cliente realizar una actividad de cardio o de pesas.
     * 
     * <p>Solicita el DNI del cliente, verifica su existencia, permite seleccionar entre actividades de pesas y cardio,
     * actualiza la estamina del cliente y verifica la disponibilidad de monitores para las actividades de cardio.</p>
     * 
     * @param connection la conexión a la base de datos
     * @param sc el scanner para entrada de datos
     * @param dniCliente el DNI del cliente
     * @throws SQLException si ocurre un error al actualizar la base de datos
     */
	public void realizarActividad() {
		Connection connection = conectar();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Introduce tu DNI:");
		String dniCliente = sc.nextLine();
		
		
		if (connection != null) {
			
			try {
				   
				PreparedStatement verificarDni = connection .prepareStatement("SELECT COUNT(*) FROM Cliente WHERE dniCliente = ?");
				verificarDni.setString(1, dniCliente);
				ResultSet resultadoDni = verificarDni.executeQuery();
				resultadoDni.next();
				int cantidadDni = resultadoDni.getInt(1);

				if (cantidadDni < 1) {
					System.out.println("No existe ningún cliente con ese DNI.");
					return;
				}

				System.out.println("¿Qué tipo de actividad deseas realizar? (pesas / cardio)");
				String tipoActividad = sc.nextLine().toLowerCase();

				if (tipoActividad.equals("pesas")) {
					System.out.println(
							"Elige el ejercicio de pesas que quieres realizar: (1. Press Banca, 2. Sentadilla, 3. Peso Muerto)");
					int opcionEjercicio = sc.nextInt();
					sc.nextLine(); 

					int estaminaRequerida = 0;
					int pesoRequerido = 0;

					switch (opcionEjercicio) {
					case 1:
						estaminaRequerida = 10;
						System.out.println("¿Cuánto peso vas a levantar en Press Banca?");
						pesoRequerido = sc.nextInt();
						if (pesoRequerido > 130) {
							System.out.println("No te flipes, máximo puedes levantar 130kg.");
							pesoRequerido = 100;
						}
						break;
					case 2:
						estaminaRequerida = 20;
						System.out.println("¿Cuánto peso vas a levantar en Sentadilla?");
						pesoRequerido = sc.nextInt();
						if (pesoRequerido > 200) {
							System.out.println("No te flipes, máximo puedes levantar 200kg.");
							pesoRequerido = 150;
						}
						break;
					case 3:
						estaminaRequerida = 15;
						System.out.println("¿Cuánto peso vas a levantar en Peso Muerto?");
						pesoRequerido = sc.nextInt();
						if (pesoRequerido > 200) {
							System.out.println("No te flipes, máximo puedes levantar 200kg.");
							pesoRequerido = 170;
						}
						break;
					default:
						System.out.println("Opción no válida.");
						return;
					}

					String actualizarEstaminaQuery = "UPDATE Cliente SET estamina = estamina - ? WHERE dniCliente = ?";
					PreparedStatement psActualizarEstamina = connection.prepareStatement(actualizarEstaminaQuery);
					psActualizarEstamina.setInt(1, estaminaRequerida);
					psActualizarEstamina.setString(2, dniCliente);
					psActualizarEstamina.executeUpdate();

					System.out.println("Realizando ejercicio de pesas...");
					
				

				} else if (tipoActividad.equals("cardio")) {
				   

				       
					System.out.println("Elige el tipo de cardio que quieres realizar: (BODYPAM, BOXEO, FUNCIONAL, OVERUP)");
					String tipoCardio = sc.nextLine().toUpperCase(); 

					int estaminaCardio = 0;

					switch (tipoCardio) {
					    case "BODYPAM":
					        estaminaCardio = 5;
					        break;
					    case "BOXEO":
					        estaminaCardio = 7;
					        break;
					    case "FUNCIONAL":
					        estaminaCardio = 10;
					        break;
					    case "OVERUP":
					        estaminaCardio = 8;
					        break;
					    default:
					        System.out.println("Opción no válida.");
					        return;
					}
					try {
					  
					    PreparedStatement verificarMonitor = connection.prepareStatement("SELECT COUNT(*) FROM Monitor WHERE tipo = ?");
					    verificarMonitor.setString(1, tipoCardio);
					    ResultSet resultadoMonitor = verificarMonitor.executeQuery();
					    resultadoMonitor.next();
					    int cantidadMonitores = resultadoMonitor.getInt(1);

					    if (cantidadMonitores == 0) {
					        System.out.println("Lo siento, no hay ningún monitor asignado a la clase de cardio seleccionada.");
					        return; 
					    }
				        String actualizarEstaminaQuery = "UPDATE Cliente SET estamina = estamina - ? WHERE dniCliente = ?";
				        PreparedStatement psActualizarEstamina = connection.prepareStatement(actualizarEstaminaQuery);
				        psActualizarEstamina.setInt(1, estaminaCardio);
				        psActualizarEstamina.setString(2, dniCliente);
				        psActualizarEstamina.executeUpdate();

				        System.out.println("Realizando ejercicio de cardio...");
				    } catch (SQLException e) {
				        System.out.println("Error al verificar la disponibilidad del monitor.");
				        return;
				    }
				}else {
					System.out.println("Tipo de actividad no válido.");
					return;
				}
				             
				connection.close();
			} catch (SQLException e) {
				System.out.println("Error al realizar la actividad.");
			}
		} else {
			System.out.println("No se pudo establecer conexión con la base de datos.");
		}
	}
}
