package gymachaca;

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
 * La clase ComprarDao gestiona las operaciones relacionadas con las compras y el consumo de alimentos
 * en la base de datos del gimnasio.
 * 
 * <p>Incluye métodos para conectar con la base de datos, realizar compras y consumir alimentos.</p>
 * 
 * @autor Alejandro Molina
 */
public class ComprarDao {

	private Connection conexion;

	private final String USUARIO = "root";
	private final String PASSWORD = "Alex22m22";
	private final String MAQUINA = "localhost";
	private final String BD = "Gymachaca";

	
    /**
     * Constructor que establece la conexión con la base de datos.
     */
	public ComprarDao() {
		conexion = conectar();
	}


    /**
     * Establece la conexión con la base de datos.
     * 
     * @return la conexión a la base de datos, o null si no se pudo conectar.
     */
	public Connection conectar() {
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
     * Realiza una compra de alimentos para un cliente en la base de datos.
     * 
     * <p>Solicita el DNI del cliente, verifica su existencia, muestra los productos disponibles,
     * permite seleccionar y comprar productos y actualiza la base de datos en consecuencia.</p>
     */
	public void comprar() {
		Connection connection = conectar();
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce el DNI del cliente:");
		String dniCliente = sc.nextLine();

		if (connection != null) {

			try {
				PreparedStatement verificaciondni = connection
						.prepareStatement("SELECT COUNT(*) FROM persona WHERE dni = ?");
				verificaciondni.setString(1, dniCliente);
				ResultSet resultadodni = verificaciondni.executeQuery();
				resultadodni.next();
				int dnipersona = resultadodni.getInt(1);

				if (dnipersona < 1) {
					System.out.println("No existe ningún cliente con ese DNI.");
					return;
				}

				while (true) {

					System.out.println("¿Desea comprar un producto? (s/n)");
					String respuesta = sc.nextLine().toLowerCase();
					if (respuesta.equals("n")) {
						System.out.println("Compra finalizada.");
						break;
					} else if (respuesta.equals("s")) {
						System.out.println(
								"Productos disponibles son:\n -proteína: 10$/unidad  \n -creatina: 9$/unidad \n -aminoacidos: 18.25$/unidad \n -testosterona: 50$/unidad \n -barritas proteicas: 4$/unidad");
						System.out.println("Introduce el nombre del producto:");
						String nombreProducto = sc.nextLine();
						int codAlimento;
						switch (nombreProducto.toLowerCase()) {
						case "proteina":
							codAlimento = 1;
							break;
						case "creatina":
							codAlimento = 2;
							break;
						case "barritas proteicas":
							codAlimento = 3;
							break;
						case "aminoacidos":
							codAlimento = 4;
							break;
						case "testosterona":
							codAlimento = 5;
							break;
						default:
							codAlimento = -1;
							break;
						}

						String query = "SELECT * FROM Alimentos WHERE nombre = ?";
						PreparedStatement ps = connection.prepareStatement(query);
						ps.setString(1, nombreProducto);
						ResultSet rs = ps.executeQuery();

						if (rs.next()) {
							int cantidadDisponible = rs.getInt("cantidad_disponible");
							double precioPorUnidad = rs.getDouble("precioporunidad");

							System.out.println("Introduce la cantidad que desea comprar:");
							int cantidad = sc.nextInt();
							sc.nextLine();

							String queryCliente = "SELECT cartera FROM Cliente WHERE dniCliente = ?";
							PreparedStatement psCliente = connection.prepareStatement(queryCliente);
							psCliente.setString(1, dniCliente);
							ResultSet rsCliente = psCliente.executeQuery();
							rsCliente.next();
							double carteraActual = rsCliente.getDouble("cartera");
							double precioTotal = precioPorUnidad * cantidad;

							if (carteraActual - precioTotal < 0) {
								System.out
										.println("No tiene suficiente saldo en su cartera para realizar esta compra.");
								continue;
							}

							if (cantidadDisponible >= cantidad) {
								String actualizarStockQuery = "UPDATE Alimentos SET cantidad_disponible = ? WHERE nombre = ?";
								PreparedStatement psActualizar = connection.prepareStatement(actualizarStockQuery);
								psActualizar.setInt(1, cantidadDisponible - cantidad);
								psActualizar.setString(2, nombreProducto);
								psActualizar.executeUpdate();

								String actualizarCarteraQuery = "UPDATE Cliente SET cartera = cartera - ? WHERE dniCliente = ?";
								PreparedStatement psCartera = connection.prepareStatement(actualizarCarteraQuery);
								psCartera.setDouble(1, precioTotal);
								psCartera.setString(2, dniCliente);
								psCartera.executeUpdate();

								String queryInsertCompra = "INSERT INTO Comprar (dniCliente, codAlimento, cantidad, nombrealimento) VALUES (?, ?, ?, ?)";
								PreparedStatement psInsertCompra = connection.prepareStatement(queryInsertCompra,
										Statement.RETURN_GENERATED_KEYS);
								psInsertCompra.setString(1, dniCliente);
								psInsertCompra.setInt(2, codAlimento);
								psInsertCompra.setInt(3, cantidad);
								psInsertCompra.setString(4, nombreProducto);
								psInsertCompra.executeUpdate();

								ResultSet generatedKeys = psInsertCompra.getGeneratedKeys();

								if (generatedKeys.next()) {
									int idCompra = generatedKeys.getInt(1);
									System.out.println("Compra realizada (ID: " + idCompra + "): " + cantidad
											+ " unidades de " + nombreProducto);
								}
							} else {
								System.out.println("No hay suficiente cantidad de " + nombreProducto + " en stock.");
							}
						} else {
							System.out.println("El producto con nombre " + nombreProducto + " no existe.");
						}
					} else {
						System.out.println("Respuesta inválida. Por favor, responda con 's' o 'n'.");
					}
				}
				connection.close();
			} catch (SQLException e) {
				System.out.println("Error al ejecutar la compra.");
			}
		} else {
			System.out.println("No se pudo establecer conexión con la base de datos.");
		}
	}

	 /**
     * Permite a un cliente consumir alimentos previamente comprados.
     * 
     * <p>Solicita el DNI del cliente, muestra los alimentos disponibles para el cliente,
     * permite seleccionar y consumir alimentos y actualiza la base de datos en consecuencia.</p>
     */
	public void consumirAlimentos() {
		Connection connection = conectar();
		Scanner scanner = new Scanner(System.in);

		System.out.println("Introduce el DNI del cliente:");
		String dniCliente = scanner.nextLine();

		try {

			PreparedStatement verificarCliente = connection
					.prepareStatement("SELECT COUNT(*) FROM Cliente WHERE dniCliente = ?");
			verificarCliente.setString(1, dniCliente);
			ResultSet resultadoCliente = verificarCliente.executeQuery();
			resultadoCliente.next();
			int cantidadClientes = resultadoCliente.getInt(1);

			if (cantidadClientes < 1) {
				System.out.println("No existe ningún cliente con ese DNI.");
				return;
			}

			System.out.println("Alimentos comprados por el cliente:");
			mostrarAlimentosComprados(connection, dniCliente);

			System.out.println("Ingrese el nombre del alimento que desea consumir:");
			String nombreAlimento = scanner.nextLine();
			System.out.println("Ingrese la cantidad que desea consumir:");
			int cantidadConsumir = scanner.nextInt();
			scanner.nextLine();

			if (verificarAlimentoDisponible(connection, dniCliente, nombreAlimento, cantidadConsumir)) {

				consumirAlimento(connection, dniCliente, nombreAlimento, cantidadConsumir);
			}
		} catch (SQLException e) {
			System.out.println("Error al consumir alimentos: " + e.getMessage());
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					System.out.println("Error al cerrar la conexión: " + e.getMessage());
				}
			}
		}
	}
	   /**
     * Muestra los alimentos comprados por un cliente.
     * 
     * @param connection la conexión a la base de datos
     * @param dniCliente el DNI del cliente
     * @throws SQLException si ocurre un error al acceder a la base de datos
     */
	public void mostrarAlimentosComprados(Connection connection, String dniCliente) throws SQLException {
		String query = "SELECT nombreAlimento, SUM(cantidad) AS cantidad_total FROM Comprar WHERE dniCliente = ? GROUP BY nombreAlimento";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, dniCliente);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String nombreAlimento = rs.getString("nombreAlimento");
				int cantidadTotal = rs.getInt("cantidad_total");
				System.out.println("- " + nombreAlimento + ": " + cantidadTotal);
			}
		}
	}

    /**
     * Verifica si el cliente tiene suficiente cantidad de un alimento específico para consumir.
     * 
     * @param connection la conexión a la base de datos
     * @param dniCliente el DNI del cliente
     * @param nombreAlimento el nombre del alimento
     * @param cantidadConsumir la cantidad que se desea consumir
     * @return true si el cliente tiene suficiente cantidad del alimento, false en caso contrario
     * @throws SQLException si ocurre un error al acceder a la base de datos
     */
	public boolean verificarAlimentoDisponible(Connection connection, String dniCliente, String nombreAlimento,
			int cantidadConsumir) throws SQLException {
		String query = "SELECT cantidad FROM Comprar WHERE dniCliente = ? AND nombreAlimento = ?";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, dniCliente);
			ps.setString(2, nombreAlimento);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int cantidadComprada = rs.getInt("cantidad");
				if (cantidadComprada >= cantidadConsumir) {
					return true;
				} else {
					System.out.println("No tiene suficiente cantidad de " + nombreAlimento + " para consumir.");
				}
			} else {
				System.out.println("No ha comprado " + nombreAlimento + ".");
			}
		}
		return false;
	}

	  /**
     * Actualiza la base de datos para reflejar el consumo de un alimento por parte de un cliente.
     * 
     * @param connection la conexión a la base de datos
     * @param dniCliente el DNI del cliente
     * @param nombreAlimento el nombre del alimento
     * @param cantidadConsumir la cantidad que se desea consumir
     * @throws SQLException si ocurre un error al acceder a la base de datos
     */
	public void consumirAlimento(Connection connection, String dniCliente, String nombreAlimento, int cantidadConsumir)
            throws SQLException {

        String actualizarCantidadQuery = "UPDATE Alimentos SET cantidad_disponible = cantidad_disponible - ? WHERE nombre = ?";
        try (PreparedStatement psActualizarCantidad = connection.prepareStatement(actualizarCantidadQuery)) {
            psActualizarCantidad.setInt(1, cantidadConsumir);
            psActualizarCantidad.setString(2, nombreAlimento);
            psActualizarCantidad.executeUpdate();
        }

        String restarCantidadCompradaQuery = "UPDATE Comprar SET cantidad = cantidad - ? WHERE dniCliente = ? AND nombrealimento = ?";
        try (PreparedStatement psRestarCantidadComprada = connection.prepareStatement(restarCantidadCompradaQuery)) {
            psRestarCantidadComprada.setInt(1, cantidadConsumir);
            psRestarCantidadComprada.setString(2, dniCliente);
            psRestarCantidadComprada.setString(3, nombreAlimento);
            psRestarCantidadComprada.executeUpdate();
        }

        actualizarEstaminaCliente(connection, dniCliente, nombreAlimento, cantidadConsumir);

        System.out.println("Se han consumido " + cantidadConsumir + " unidades de " + nombreAlimento + ".");
    }

    /**
     * Actualiza la estamina del cliente en función del alimento consumido.
     * 
     * @param connection la conexión a la base de datos
     * @param dniCliente el DNI del cliente
     * @param nombreAlimento el nombre del alimento
     * @param cantidadConsumir la cantidad que se ha consumido
     * @throws SQLException si ocurre un error al acceder a la base de datos
     */
    public void actualizarEstaminaCliente(Connection connection, String dniCliente, String nombreAlimento,
            int cantidadConsumir) throws SQLException {
        String queryEstamina = "SELECT estaminaproduct FROM Alimentos WHERE nombre = ?";
        try (PreparedStatement psEstamina = connection.prepareStatement(queryEstamina)) {
            psEstamina.setString(1, nombreAlimento);
            try (ResultSet rsEstamina = psEstamina.executeQuery()) {
                if (rsEstamina.next()) {
                    int estaminaProducto = rsEstamina.getInt("estaminaproduct");

                    String queryActualizarEstamina = "UPDATE Cliente SET estamina = estamina + ? WHERE dniCliente = ?";
                    try (PreparedStatement psActualizarEstamina = connection.prepareStatement(queryActualizarEstamina)) {
                        psActualizarEstamina.setInt(1, estaminaProducto * cantidadConsumir);
                        psActualizarEstamina.setString(2, dniCliente);
                        psActualizarEstamina.executeUpdate();
                    }
                }
            }
        }
    }
}