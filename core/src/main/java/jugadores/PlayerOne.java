package jugadores;

import personajes.Aves;
import controles.Controles;

public class PlayerOne {
	private Aves personaje;
	private Controles controles;
	private static final float VELOCIDAD_MOVIMIENTO = 5f;
	
	public PlayerOne(Aves personaje, Controles controles) {
		this.personaje = personaje;
		this.controles = controles;
	}
	
	/**
	 * Actualiza el estado del jugador procesando los controles
	 */
	public void actualizar() {
		// Procesar movimiento
		boolean[] movimientos = controles.obtenerMovimientoJugador1();
		personaje.procesarMovimiento(movimientos, VELOCIDAD_MOVIMIENTO);
		
		// Procesar acciones de combate
		boolean[] acciones = controles.obtenerAccionesJugador1();
		personaje.procesarAcciones(acciones);
	}
	
	/**
	 * Obtiene el personaje del jugador
	 * @return el personaje
	 */
	public Aves getPersonaje() {
		return personaje;
	}
	
	/**
	 * Establece el personaje del jugador
	 * @param personaje el nuevo personaje
	 */
	public void setPersonaje(Aves personaje) {
		this.personaje = personaje;
	}
	
	/**
	 * Obtiene los controles del jugador
	 * @return los controles
	 */
	public Controles getControles() {
		return controles;
	}
	
	/**
	 * Establece los controles del jugador
	 * @param controles los nuevos controles
	 */
	public void setControles(Controles controles) {
		this.controles = controles;
	}
	
	/**
	 * Obtiene la velocidad de movimiento del jugador
	 * @return la velocidad de movimiento
	 */
	public float getVelocidadMovimiento() {
		return VELOCIDAD_MOVIMIENTO;
	}
}
