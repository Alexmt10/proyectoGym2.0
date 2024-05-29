
-- CONSULTAS
SELECT p.nombre AS Cliente, p.apellido AS ClienteApellido, m.dniMonitor, m.tipo AS TipoClase 
FROM Cliente c
JOIN Persona p ON c.dniCliente = p.dni
LEFT JOIN Cardio ca ON c.dniCliente = ca.dniCliente
LEFT JOIN Monitor m ON ca.dniMonitor = m.dniMonitor;


SELECT bono, COUNT(*) AS TotalClientes
FROM Cliente
GROUP BY bono;


SELECT nombre, precioporunidad
FROM Alimentos
ORDER BY precioporunidad DESC
LIMIT 1;


SELECT p.nombre, p.apellido, SUM(a.precioporunidad * c.cantidad) AS TotalGasto
FROM Comprar c
JOIN Alimentos a ON c.codAlimento = a.codAlimento
JOIN Persona p ON c.dniCliente = p.dni
GROUP BY c.dniCliente;


SELECT c.dniCliente, p.nombre, p.apellido, COUNT(a.idInstalacion) AS NumeroDeInstalaciones
FROM Apuntarse a
JOIN Cliente c ON a.dniCliente = c.dniCliente
JOIN Persona p ON c.dniCliente = p.dni
GROUP BY c.dniCliente
HAVING COUNT(a.idInstalacion) > 1;

-- UPDATES Y DELETES
UPDATE Cliente c
JOIN Comprar co ON c.dniCliente = co.dniCliente
JOIN Alimentos a ON co.codAlimento = a.codAlimento
SET c.estamina = c.estamina + a.estaminaproduct
WHERE a.nombre = 'proteina';



UPDATE Alimentos a
JOIN Comprar c ON a.codAlimento = c.codAlimento
SET a.cantidad_disponible = a.cantidad_disponible - c.cantidad
WHERE c.codAlimento = 1; 


DELETE FROM Comprar
WHERE dniCliente = '44444444D'; 


-- VISTAS
CREATE VIEW VistaGastoTotalClientes AS
SELECT p.nombre, p.apellido, SUM(a.precioporunidad * c.cantidad) AS TotalGasto
FROM Comprar c
JOIN Alimentos a ON c.codAlimento = a.codAlimento
JOIN Persona p ON c.dniCliente = p.dni
GROUP BY c.dniCliente;

CREATE VIEW VistaClientesProteina AS
SELECT p.nombre, p.apellido, c.estamina
FROM Cliente c
JOIN Persona p ON c.dniCliente = p.dni
JOIN Comprar co ON c.dniCliente = co.dniCliente
JOIN Alimentos a ON co.codAlimento = a.codAlimento
WHERE a.nombre = 'proteina';


-- PROCEDIMIENTOS

DROP PROCEDURE IF EXISTS GastoTotalCliente;
DELIMITER $$
CREATE FUNCTION GastoTotalCliente(dniCliente VARCHAR(9))
RETURNS DECIMAL(10, 2)
BEGIN
    DECLARE total DECIMAL(10, 2);
    
    SELECT SUM(a.precioporunidad * c.cantidad) INTO total
    FROM Comprar c
    JOIN Alimentos a ON c.codAlimento = a.codAlimento
    WHERE c.dniCliente = dniCliente;
    
    RETURN total;
END$$
DELIMITER ;

call GastoTotalCliente('44444444D');

DROP PROCEDURE IF EXISTS ActualizarCarteraCliente;
DELIMITER $$
CREATE PROCEDURE ActualizarCarteraCliente(IN dniCliente VARCHAR(9),  IN cantidadGastada DECIMAL(10, 2))
BEGIN
    UPDATE Cliente
    SET cartera = cartera - cantidadGastada
    WHERE dniCliente = dniCliente;
END$$
DELIMITER ;

call ActualizarCarteraCliente('66666666F', 15);

DROP PROCEDURE IF EXISTS RegistrarCompraSimple;
DELIMITER $$

CREATE PROCEDURE RegistrarCompraSimple( IN p_dniCliente VARCHAR(9), IN p_codAlimento INT, IN p_cantidad DECIMAL(10, 2))
BEGIN
    DECLARE v_nombreAlimento VARCHAR(30);

    SELECT nombre INTO v_nombreAlimento
    FROM Alimentos
    WHERE codAlimento = p_codAlimento
    LIMIT 1;

    INSERT INTO Comprar (dniCliente, codAlimento, cantidad, nombrealimento)
    VALUES (p_dniCliente, p_codAlimento, p_cantidad, v_nombreAlimento);

    UPDATE Alimentos
    SET cantidad_disponible = cantidad_disponible - p_cantidad
    WHERE codAlimento = p_codAlimento;
    
END$$
DELIMITER ;
CALL RegistrarCompraSimple('44444444D', 1, 4);

 -- DISPARADORES
 
DROP TRIGGER IF EXISTS VerificarTelefono;
DELIMITER $$
CREATE TRIGGER VerificarTelefono BEFORE INSERT ON Persona
FOR EACH ROW
BEGIN
    IF CHAR_LENGTH(NEW.telefono) <> 9 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El número de teléfono debe tener 9 dígitos';
    END IF;
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS ActualizarCantidadAlimento;
DELIMITER $$
CREATE TRIGGER ActualizarCantidadAlimento AFTER INSERT ON Comprar
FOR EACH ROW
BEGIN
    UPDATE Alimentos
    SET cantidad_disponible = cantidad_disponible - NEW.cantidad
    WHERE codAlimento = NEW.codAlimento;
END$$
DELIMITER ;


