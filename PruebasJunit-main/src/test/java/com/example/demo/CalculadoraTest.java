package com.example.demo;

import com.example.Calculadora;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class CalculadoraTest {
    private Calculadora calculadora;

    
    @BeforeEach // Se hace antes de cada test
    void setUp() {
        calculadora = new Calculadora();
    }


    @Test
    @DisplayName("Suma: 2 + 3 = 5")
    void testSumar() {
        int expected = 5; // Valor esperado
        int actual = calculadora.sumar(2, 3); // Valor real obtenido
        assertEquals(expected, actual, "2 + 3 debería ser 5");
    }

    @Test
    @DisplayName("Resta: 5 - 3 = 2")
    void testRestar() {
        assertEquals(2, calculadora.restar(5, 3));
    }

    @Test
    @DisplayName("División correcta")
    void testDividir() {
        assertEquals(3, calculadora.dividir(9, 3));
    }

    @Test
    @DisplayName("División por cero lanza IllegalArgumentException")
    void testDividirEntreCero() {
        assertThrows(IllegalArgumentException.class, () -> calculadora.dividir(5, 0));
    }

    /*
    * @BeforeEach: Se ejecuta antes de cada método de prueba para configurar el entorno de prueba.
    * @Test: Indica que el método es un caso de prueba.
    * @DisplayName: Proporciona un nombre para el test.
    * assertEquals: Verifica que dos valores sean iguales.
    * assertThrows: Verifica que se lance una excepción específica.
    * 
    * Otras anotaciones útiles incluyen:
    * @BeforeAll: Se ejecuta una vez antes de todos los tests.
    * @AfterAll: Se ejecuta una vez después de todos los tests.
    * @AfterEach: Se ejecuta después de cada test.
    * @Disabled: Deshabilita un test.
    * @ParameterizedTest: Permite ejecutar un mismo test con diferentes parámetros.
    * @ValueSource: Datos para pruebas parametrizadas.
    * 
    * Otros métodos de aserción incluyen:
    * assertTrue: Verifica que una condición sea verdadera.
    * assertFalse: Verifica que una condición sea falsa.
    * assertNull: Verifica que un objeto sea nulo.
    * assertNotNull: Verifica que un objeto no sea nulo.
    * assertAll: Verifica múltiples condiciones a la vez.
    */

}
