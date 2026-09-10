package jugadores;

import controles.Controles;
import personajes.Aves;

public class Jugadores {
	private PlayerOne jugador1;
	private PlayerTwo jugador2;
	private Controles controles;
	
	public Jugadores(Aves personaje1, Aves personaje2, Controles controles) {
		this.controles = controles;
		this.jugador1 = new PlayerOne(personaje1, controles);
		this.jugador2 = new PlayerTwo(personaje2, controles);
	}
	
	/**
	 * Actualiza ambos jugadores procesando sus entradas
	 */
	public void actualizar() {
		jugador1.actualizar();
		jugador2.actualizar();
	}
	
	/**
	 * Obtiene el Jugador 1
	 * @return PlayerOne
	 */
	public PlayerOne getJugador1() {
		return jugador1;
	}
	
	/**
	 * Obtiene el Jugador 2
	 * @return PlayerTwo
	 */
	public PlayerTwo getJugador2() {
		return jugador2;
	}
	
	/**
	 * Obtiene los controles
	 * @return Controles
	 */
	public Controles getControles() {
		return controles;
	}
	
	/**
	 * Establece los controles
	 * @param controles los nuevos controles
	 */
	public void setControles(Controles controles) {
		this.controles = controles;
		jugador1.setControles(controles);
		jugador2.setControles(controles);
	}
	
	/**
	 * Cambia el personaje del Jugador 1
	 * @param nuevoPersonaje el nuevo personaje
	 */
	public void cambiarPersonajeJugador1(Aves nuevoPersonaje) {
		jugador1.setPersonaje(nuevoPersonaje);
	}
	
	/**
	 * Cambia el personaje del Jugador 2
	 * @param nuevoPersonaje el nuevo personaje
	 */
	public void cambiarPersonajeJugador2(Aves nuevoPersonaje) {
		jugador2.setPersonaje(nuevoPersonaje);
	}
}
