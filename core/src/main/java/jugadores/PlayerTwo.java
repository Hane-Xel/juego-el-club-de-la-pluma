package jugadores;

import personajes.Aves;


// lo mismo que el 1
public class PlayerTwo {
	private Aves personaje;
	private static final float VELOCIDAD_MOVIMIENTO = 5f;
	
	public PlayerTwo(Aves personaje) {
		this.personaje = personaje; 
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