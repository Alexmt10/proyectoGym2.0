package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import gymachaca.Bono;
import gymachaca.Cliente;

class TestModifiarCliente {

	@Test
	  void testModificarCliente() {
       
        Cliente cliente = new Cliente("123456789", "Juan", "Perez", "123456789", "Calle Falsa 123", 100, Bono.ORO, 200);
        
        
        cliente.setNombre("Pedro");
        cliente.setApellido("Gomez");
        cliente.setTelefono("987654321");
        cliente.setDireccion("Av. Principal 456");
        cliente.setStamina(150);
        cliente.setBono(Bono.PLATA);
        cliente.setCartera(300.0);
        
        
        assertEquals("Pedro", cliente.getNombre());
        assertEquals("Gomez", cliente.getApellido());
        assertEquals("987654321", cliente.getTelefono());
        assertEquals("Av. Principal 456", cliente.getDireccion());
        assertEquals(150, cliente.getStamina());
        assertEquals(Bono.PLATA, cliente.getBono());
        assertEquals(300.0, cliente.getCartera());
    }
}
