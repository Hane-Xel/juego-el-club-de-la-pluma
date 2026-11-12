package jugadores;

import personajes.Aves;

// vincula al personaje con el jugador que lo eligio
public class PlayerOne {
	private Aves personaje;
	private static final float VELOCIDAD_MOVIMIENTO = 5f;
	
	public PlayerOne(Aves personaje) {
		this.personaje = personaje; // recibe el personaje del jugador
	}
	
	public Aves getPersonaje() {
		return personaje;
	}
	
	public void setPersonaje(Aves personaje) {
		this.personaje = personaje;
	}
	
	public float getVelocidadMovimiento() {
		return VELOCIDAD_MOVIMIENTO;
	}
}