package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import gymachaca.ComprarDao;

class PruebaComprobarcompra {

	  private ComprarDao comprarDao;
	    private Connection connection;

	    @BeforeEach
	    void setUp() throws SQLException {
	        comprarDao = new ComprarDao();
	        connection = comprarDao.conectar();
	    }
	    @Test
	    void testVerificarAlimentoDisponible() {
	        try {
	            boolean disponible = comprarDao.verificarAlimentoDisponible(connection, "12345678A", "proteina", 5);
	            assertTrue(disponible, "El alimento debería estar disponible en la cantidad solicitada.");
	        } catch (SQLException e) {
	            fail("Se lanzó una excepción SQLException: " + e.getMessage());
	        }
	    }

}
