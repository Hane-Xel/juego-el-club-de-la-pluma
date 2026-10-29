package jugadores;

import personajes.Aves;

public class PlayerOne {
	private Aves personaje;
	private static final float VELOCIDAD_MOVIMIENTO = 5f;
	private int victorias = 0;
	
	public PlayerOne(Aves personaje) {
		this.personaje = personaje;
	}
	
	/**
	 * Actualiza el estado interno del jugador (si requiere lógica propia por frame)
	 */
	public void actualizar(float delta) {
		// La lectura de controles se maneja directamente en la clase Controles
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

	public int getVictorias() {
		return victorias;
	}

	public void sumarVictoria() {
		this.victorias++;
	}
}