package gymachaca;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class PersonaDAO {

	private Connection conexion;

	private final String USUARIO = "root";
	private final String PASSWORD = "Alex22m22";
	private final String MAQUINA = "localhost";
	private final String BD = "Gymachaca";

	public PersonaDAO() {
		conexion = conectar();
	}

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

	public void crearPersona() {

		Connection connection = conectar();

		if (connection != null) {
			try {
				var sc = new Scanner(System.in);

				System.out.println("vamos a empezar a crear a la persona y despues (cliente o monitor) :)  ");
				System.out.println("Introduce el DNI:");
				String dni = sc.nextLine();
				
				 if(dni.length()!= 9) { System.out.println("Has introducido mal el dni");
				  return; }
				 
				PreparedStatement verificaciondni = connection
						.prepareStatement("SELECT COUNT(*) FROM persona WHERE dni = ?");
				verificaciondni.setString(1, dni);
				ResultSet resultadodni = verificaciondni.executeQuery();
				resultadodni.next();
				int personasconmismodni = resultadodni.getInt(1);

				if (personasconmismodni > 0) {
					System.out.println("Ya existe una persona con el DNI proporcionado. No se puede crear.");
					return;
				}

				System.out.println("Introduce el nombre:");
				String nombre = sc.nextLine();
				System.out.println("Introduce el apellido:");
				String apellido = sc.nextLine();
				System.out.println("Introduce el teléfono:");
				String telefono = sc.next();

				PreparedStatement verificaciontelefono = connection
						.prepareStatement("SELECT COUNT(*) FROM persona WHERE telefono = ?");

				verificaciontelefono.setString(1, telefono);
				ResultSet resultado = verificaciontelefono.executeQuery();
				resultado.next();
				int mismotelefono = resultado.getInt(1);

				if (mismotelefono > 0) {
					System.out.println(
							"Ya existe una persona con el numero de telefono proporcionado. No se puede crear.");
					return;
				}
				System.out.println("Introduce la dirección:");
				sc.nextLine();
				String direccion = sc.nextLine();
				String sql = "INSERT INTO persona (dni, nombre, apellido, telefono, direccion) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, dni);
				statement.setString(2, nombre);
				statement.setString(3, apellido);
				statement.setString(4, telefono);
				statement.setString(5, direccion);
				Persona nuevaPersona = null;
				statement.executeUpdate();
				System.out.println("Se ha creado la persona correctamente");
				System.out.println("Para finalizar la creacion de la persona necesitamos saber si quieres entrar como monitor o como cliente");
				System.out.println("Que quieres ser un cliente o un monitor");
				String elegirpersona = sc.next();
				if (elegirpersona.equalsIgnoreCase("cliente")) {
					System.out.println("Has elegido ser un Cliente procedemos a crerlo");
					System.out.println("Introduce la estamina que vas a tener para realizar los ejercicios");
					int estamina = sc.nextInt();
					if (estamina > 100) {
						System.out.println(
								"Te has motivado demasiado no tienes tanta energia te vamos a asignar 100 de estamina");
						estamina = Cliente.ESTAMINA_DEF;
					}
					System.out.println("Introduce tu saldo que vas a tener para comprer en nuestra nueva tienda");
					double saldo = sc.nextDouble();
					if (saldo > 200) {
						System.out.println(
								"No tienes tanto dinero para gastarte y te vamops a asignar 200 euritos que esta muy bien no te quejes");
						saldo = Cliente.CARTERA_DEF;
					}
					System.out.println("Por ultimo introduce el bono que quieres estan. (ORO 30€, PLATA 20€, BRONCE 10€");
					String bono = sc.next().toUpperCase();
					if (bono.equalsIgnoreCase("ORO")) {
						System.out.println("Ha escogido el bono oro se ha incrementado 50 puntos su estamina");
						estamina += 50;
						saldo -= 30;
					} else if (bono.equalsIgnoreCase("PLATA")) {
						System.out.println("Ha escogido el bono plata se ha incrementado 35 puntos su estamina");
						estamina += 35;
						saldo -= 20;
					} else if (bono.equalsIgnoreCase("BRONCE")) {
						System.out.println("Ha escogido el bono bronce se ha incrementado 20 puntos su estamina");
						estamina += 20;
						saldo -= 10;
					} else {
						System.out.println(
								"Has introducido un bono que no existe, te asignaremos el de bronce y te aumentaremos en 20 tu estamina");
						estamina += 20;
						saldo -= 10;
					}
					String sql2 = "INSERT INTO Cliente (dniCliente, estamina, bono, cartera) VALUES (?, ?, ?, ?)";
					PreparedStatement statement2 = connection.prepareStatement(sql2);
					statement2.setString(1, dni);
					statement2.setInt(2, estamina);
					statement2.setString(3, bono);
					statement2.setDouble(4, saldo);

					statement2.executeUpdate();
					System.out.println("Se ha creado El cliente correctamente");

					

				} else if (elegirpersona.equalsIgnoreCase("monitor")) {
					System.out.println("Has elegido crear un monitor ");
					System.out.println(	"Introduce el tipo de clase que quieres realizar: BODYPUM, BOXEO, FUNCIONAL, OVERUP");
					String clase = sc.next().toUpperCase();
					Tipo tipo = null;
					switch (clase) {
				    case "BODYPUM":
				        System.out.println("Se le han asignado las clases de bodypUm");
				        tipo = Tipo.BODYPAM;
				        break;
				    case "BOXEO":
				        System.out.println("Se le han asignado las clases de boxeo");
				        tipo = Tipo.BOXEO;
				        break;
				    case "FUNCIONAL":
				        System.out.println("Se le han asignado las clases de funcional");
				        tipo = Tipo.FUNCIONAL;
				        break;
				    case "OVERUP":
				        System.out.println("Se le han asignado las clases de overup");
				        tipo = Tipo.OVERUP;
				        break;
				    default:
				        System.out.println("No se ha encontrado esa clase, por lo tanto te vamos a asignar la clase de bodypum");
				        tipo = Tipo.BODYPAM;
				        break;
				}
					String sql2 = "INSERT INTO Monitor (dniMonitor , tipo) VALUES (?, ?)";
					PreparedStatement statement2 = connection.prepareStatement(sql2);
					statement2.setString(1, dni);
					statement2.setString(2, tipo.name());
					statement2.executeUpdate();
					System.out.println("Se ha creado El Monitor correctamente");
					
					String sql3 = "INSERT INTO Cardio (dniMonitor, tipo, estaminaCardio) VALUES (?, ?, ?)";
			        PreparedStatement statement3 = connection.prepareStatement(sql3);
			        statement3.setString(1, dni);
			        statement3.setString(2, tipo.name());
			        statement3.setInt(3, 0); 
			        statement3.executeUpdate();
			        System.out.println("Se ha asignado el monitor a la clase de cardio en la tabla Cardio");

				} else

					connection.close();

			} catch (SQLException ex) {
				System.out.println("Error al insertar");
			}
		}
	}

	public Cliente leerCliente() {
		var sc = new Scanner(System.in);
		Cliente cliente = null;

		System.out.println("Introduce el dni del cliente que quieras ver la informacion");
		String dni = sc.next();
		String sql = "SELECT p.dni, p.nombre, p.apellido, p.telefono, p.direccion, c.estamina, c.bono, c.cartera FROM Persona p JOIN Cliente c ON p.dni = c.dniCliente WHERE p.dni = ?";

		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, dni);
			ResultSet rs = sentencia.executeQuery();

			if (rs.next()) {
				String nombre = rs.getString("nombre");
				String apellido = rs.getString("apellido");
				String telefono = rs.getString("telefono");
				String direccion = rs.getString("direccion");
				int estamina = rs.getInt("estamina");
				Bono bono = Bono.valueOf(rs.getString("bono"));
				double cartera = rs.getDouble("cartera");
				cliente = new Cliente(dni, nombre, apellido, telefono, direccion, estamina, bono, cartera);

				System.out.println(cliente.toString());
			} else {
				System.out.println("No existe ningún cliente con el DNI proporcionado.");
			}
		} catch (SQLException e) {
			System.out.println("Error al consultar un cliente");
		}
		return cliente;
	}

	public Monitor leerMonitor() {
		var sc = new Scanner(System.in);
		Monitor monitor = null;

		System.out.println("Introduce el DNI del monitor:");
		String dni = sc.next();
		String sql = "SELECT p.dni, p.nombre, p.apellido, p.telefono, p.direccion, m.tipo FROM Persona p\r\n"
				+ "JOIN Monitor m ON p.dni = m.dniMonitor WHERE p.dni = ?";

		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, dni);
			ResultSet rs = sentencia.executeQuery();

			if (rs.next()) {
				String nombre = rs.getString("nombre");
				String apellido = rs.getString("apellido");
				String telefono = rs.getString("telefono");
				String direccion = rs.getString("direccion");
				Tipo tipo = Tipo.valueOf(rs.getString("tipo"));
				monitor = new Monitor(dni, nombre, apellido, telefono, direccion, tipo);
				System.out.println(monitor.toString());
			} else {
				System.out.println("No existe ningún cliente con el DNI proporcionado.");
			}
		} catch (SQLException e) {
			System.out.println("Error al consultar un monitor");
		}
		return monitor;
	}

	public void elemininarCliente() {
		var sc = new Scanner(System.in);

		System.out.println("Introduce el DNI del cliente:");
		String dni = sc.next();
		String sqlCliente = "DELETE FROM Cliente WHERE dniCliente = ?";
		String sqlPersona = "DELETE FROM Persona WHERE dni = ?";
		try {
			PreparedStatement sentenciaCliente = conexion.prepareStatement(sqlCliente);
			sentenciaCliente.setString(1, dni);
			sentenciaCliente.executeUpdate();

			PreparedStatement sentenciaPersona = conexion.prepareStatement(sqlPersona);
			sentenciaPersona.setString(1, dni);
			sentenciaPersona.executeUpdate();

			System.out.println("Cliente eliminado correctamente.");
		} catch (SQLException e) {
			System.out.println("Error al eliminar al cliente");
		}
	}

	public void elemininarMonitor() {
		var sc = new Scanner(System.in);

		System.out.println("Introduce el DNI del monitor:");
		String dni = sc.next();
		String sqlMonitor = "DELETE FROM monitor WHERE dniMonitor = ?";
		String sqlPersona = "DELETE FROM Persona WHERE dni = ?";
		try {
			PreparedStatement sentenciaCliente = conexion.prepareStatement(sqlMonitor);
			sentenciaCliente.setString(1, dni);
			sentenciaCliente.executeUpdate();

			PreparedStatement sentenciaPersona = conexion.prepareStatement(sqlPersona);
			sentenciaPersona.setString(1, dni);
			sentenciaPersona.executeUpdate();

			System.out.println("monitor eliminado correctamente.");
		} catch (SQLException e) {
			System.out.println("Error al eliminar al cliente");
		}
	}

	public void modificarCliente() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce el DNI del cliente que deseas modificar:");
		String dni = sc.next();

		String sql = "SELECT p.dni, p.nombre, p.apellido, p.telefono, p.direccion, c.estamina, c.bono, c.cartera FROM Persona p JOIN Cliente c ON p.dni = c.dniCliente WHERE p.dni = ?";
		try {
			PreparedStatement consultaStmt = conexion.prepareStatement(sql);
			consultaStmt.setString(1, dni);
			ResultSet resultado = consultaStmt.executeQuery();

			if (resultado.next()) {

				System.out.println("Introduce la nueva información del cliente:");
				System.out.print("Nombre: ");
				String nuevoNombre = sc.next();
				System.out.print("Apellido: ");
				String nuevoApellido = sc.next();
				System.out.print("Teléfono: ");
				String nuevoTelefono = sc.next();
				System.out.print("Dirección: ");
				String nuevaDireccion = sc.next();
				System.out.print("Estamina: ");
				int nuevaEstamina = sc.nextInt();
				if (nuevaEstamina > 100) {
					System.out.println(
							"Te has motivado demasiado no tienes tanta energia te vamos a asignar 100 de estamina");
					nuevaEstamina = Cliente.ESTAMINA_DEF;
				}
				System.out.print("Cartera: ");
				double nuevaCartera = sc.nextDouble();
				if (nuevaCartera > 200) {
					System.out.println(
							"No tienes tanto dinero para gastarte y te vamops a asignar 200 euritos que esta muy bien no te quejes");
					nuevaCartera = Cliente.CARTERA_DEF;
				}
				System.out.print("Bono (ORO, PLATA, BRONCE): ");
				String nuevoBono = sc.next();
				if (nuevoBono.equalsIgnoreCase("ORO")) {
					System.out.println("Ha escogido el bono oro se ha incrementado 50 puntos su estamina");
					nuevaEstamina += 50;
					nuevaCartera -= 30;
				} else if (nuevoBono.equalsIgnoreCase("PLATA")) {
					System.out.println("Ha escogido el bono plata se ha incrementado 35 puntos su estamina");
					nuevaEstamina += 35;
					nuevaCartera -= 20;
				} else if (nuevoBono.equalsIgnoreCase("BRONCE")) {
					System.out.println("Ha escogido el bono bronce se ha incrementado 20 puntos su estamina");
					nuevaEstamina += 20;
					nuevaCartera -= 10;
				} else {
					System.out.println(
							"Has introducido un bono que no existe, te asignaremos el de bronce y te aumentaremos en 20 tu estamina");
					nuevaEstamina += 20;
					nuevaCartera -= 10;
				}

				String actualizar = "UPDATE Persona p JOIN Cliente c ON p.dni = c.dniCliente SET p.nombre = ?, p.apellido = ?, p.telefono = ?, p.direccion = ?, c.estamina = ?, c.bono = ?, c.cartera = ? WHERE p.dni = ?";
				PreparedStatement actualizarStmt = conexion.prepareStatement(actualizar);
				actualizarStmt.setString(1, nuevoNombre);
				actualizarStmt.setString(2, nuevoApellido);
				actualizarStmt.setString(3, nuevoTelefono);
				actualizarStmt.setString(4, nuevaDireccion);
				actualizarStmt.setInt(5, nuevaEstamina);
				actualizarStmt.setString(6, nuevoBono);
				actualizarStmt.setDouble(7, nuevaCartera);
				actualizarStmt.setString(8, dni);
				int filasActualizadas = actualizarStmt.executeUpdate();

				if (filasActualizadas > 0) {
					System.out.println("Información del cliente actualizada correctamente.");
				} else {
					System.out.println("No se pudo actualizar la información del cliente.");
				}
			} else {
				System.out.println("No existe ningún cliente con el DNI proporcionado.");
			}
		} catch (SQLException e) {
			System.out.println("Error al modificar el cliente: " + e.getMessage());
		}
	}
	public void modificarMonitor() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el DNI del monitor que deseas modificar:");
        String dni = sc.nextLine();

     
    	String sql = "SELECT p.dni, p.nombre, p.apellido, p.telefono, p.direccion, m.tipo FROM Persona p\r\n"
				+ "JOIN Monitor m ON p.dni = m.dniMonitor WHERE p.dni = ?";
        try {
            PreparedStatement consultaStmt = conexion.prepareStatement(sql);
            consultaStmt.setString(1, dni);
            ResultSet resultado = consultaStmt.executeQuery();

            if (resultado.next()) {

              
                System.out.println("Introduce la nueva información del monitor:");
                System.out.print("Nombre: ");
                String nuevoNombre = sc.nextLine();
                System.out.print("Apellido: ");
                String nuevoApellido = sc.nextLine();
                System.out.print("Teléfono: ");
                String nuevoTelefono = sc.nextLine();
                System.out.print("Dirección: ");
                String nuevaDireccion = sc.nextLine();
                System.out.print("Tipo (BODYPAM, BOXEO, FUNCIONAL, OVERUP): ");
                String clase = sc.next().toUpperCase();
				Tipo tipo = null;
				switch (clase) {
			    case "BODYPUM":
			        System.out.println("Se le han asignado las clases de bodypUm");
			        tipo = Tipo.BODYPAM;
			        break;
			    case "BOXEO":
			        System.out.println("Se le han asignado las clases de boxeo");
			        tipo = Tipo.BOXEO;
			        break;
			    case "FUNCIONAL":
			        System.out.println("Se le han asignado las clases de funcional");
			        tipo = Tipo.FUNCIONAL;
			        break;
			    case "OVERUP":
			        System.out.println("Se le han asignado las clases de overup");
			        tipo = Tipo.OVERUP;
			        break;
			    default:
			        System.out.println("No se ha encontrado esa clase, por lo tanto te vamos a asignar la clase de bodypum");
			        tipo = Tipo.BODYPAM; 
			        break;
			}

            
                String actualizar = "UPDATE Persona p JOIN Monitor m ON p.dni = m.dniMonitor SET p.nombre = ?, p.apellido = ?, p.telefono = ?, p.direccion = ?, m.tipo = ? WHERE p.dni = ?";
                PreparedStatement actualizarStmt = conexion.prepareStatement(actualizar);
                actualizarStmt.setString(1, nuevoNombre);
                actualizarStmt.setString(2, nuevoApellido);
                actualizarStmt.setString(3, nuevoTelefono);
                actualizarStmt.setString(4, nuevaDireccion);
                actualizarStmt.setString(5, tipo.name());
                actualizarStmt.setString(6, dni);
                int filasActualizadas = actualizarStmt.executeUpdate();

                if (filasActualizadas > 0) {
                    System.out.println("Información del monitor actualizada correctamente.");
                } else {
                    System.out.println("No se pudo actualizar la información del monitor.");
                }
            } else {
                System.out.println("No existe ningún monitor con el DNI proporcionado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar el monitor: " + e.getMessage());
        }
    }
}

