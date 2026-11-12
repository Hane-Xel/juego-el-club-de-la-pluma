package jugadores;

//coordina los personajes de ambos jugadores, establece formalmente a los jugadores y le asigna los controles
import controles.Controles;
import personajes.Aves;

public class Jugadores {
	private PlayerOne jugador1;
	private PlayerTwo jugador2;
	private Controles controles;
	
	public Jugadores(Aves personaje1, Aves personaje2, Controles controles) {
		this.controles = controles;
		// recibe los dos personajes y los controles
		this.jugador1 = new PlayerOne(personaje1);
		this.jugador2 = new PlayerTwo(personaje2);
	}
	
	/**
	 * Actualiza el estado de los jugadores pasando el delta time
	 */
	public void actualizar(float delta) {
		
		if (controles != null) {
			controles.actualizar(delta); // hace que se revise cada milisegundo si hay un control presionado
		}
	}
	
	public PlayerOne getJugador1() {
		return jugador1;
	}
	
	public PlayerTwo getJugador2() {
		return jugador2;
	}
	
	public Controles getControles() {
		return controles;
	}
	
	public void setControles(Controles controles) {
		this.controles = controles;
	}
	
	public void cambiarPersonajeJugador1(Aves nuevoPersonaje) {
		jugador1.setPersonaje(nuevoPersonaje);
	}
	
	public void cambiarPersonajeJugador2(Aves nuevoPersonaje) {
		jugador2.setPersonaje(nuevoPersonaje);
	}
}