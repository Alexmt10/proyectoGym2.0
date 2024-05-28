package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import gymachaca.ComprarDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class PruebaConsumirAlimentos {

	
	  private ComprarDao comprarDao;
	    private Connection connection;

	    @BeforeEach
	    void setUp() throws SQLException {
	        comprarDao = new ComprarDao();
	        connection = comprarDao.conectar();
	    }
	    
	    @Test
	    void testConsumirAlimentos() {
	       
	        try {
	            comprarDao.consumirAlimento(connection, "12345678A", "proteina", 5);
	            
	            
	            String query = "SELECT cantidad FROM Comprar WHERE dniCliente = ? AND nombrealimento = ?";
	            PreparedStatement ps = connection.prepareStatement(query);
	            ps.setString(1, "12345678A");
	            ps.setString(2, "proteina");
	            ResultSet rs = ps.executeQuery();
	            rs.next();
	            int cantidadRestante = rs.getInt("cantidad");
	            assertEquals(5, cantidadRestante, "La cantidad restante de alimentos debería ser 5.");


	        } catch (SQLException e) {
	            fail("Se lanzó una excepción SQLException: " + e.getMessage());
	        }
	    }
	}


