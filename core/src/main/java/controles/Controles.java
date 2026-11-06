package controles;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import personajes.Aves;

public class Controles implements InputProcessor {
	
	public enum inputAction {
		movIzquierda, 
		movDerecha,
		movArriba,
		movAbajo,
		salto,
		bloqueo,
		ataque,
		especial
	}

	private Aves personaje1;
	private Aves personaje2;
	
	// Teclas configurables
	private int j1Arriba = Input.Keys.W, j1Abajo = Input.Keys.S, j1Izquierda = Input.Keys.A, j1Derecha = Input.Keys.D;
	private int j1Ataque = Input.Keys.U, j1Bloqueo = Input.Keys.Y, j1Especial = Input.Keys.I;
	
	private int j2Arriba = Input.Keys.UP, j2Abajo = Input.Keys.DOWN, j2Izquierda = Input.Keys.LEFT, j2Derecha = Input.Keys.RIGHT;
	private int j2Ataque = Input.Keys.NUMPAD_2, j2Bloqueo = Input.Keys.NUMPAD_1, j2Especial = Input.Keys.NUMPAD_3;
	
	// Estados [izq, der, arriba, abajo]
	private boolean[] movJ1 = new boolean[4];
	private boolean[] movJ2 = new boolean[4];
	
	// Acciones [ataque, bloqueo, especial]
	private boolean[] accJ1 = new boolean[3];
	private boolean[] accJ2 = new boolean[3];
	
	public Controles(Aves personaje1, Aves personaje2) {
		this.personaje1 = personaje1;
		this.personaje2 = personaje2;
	}

	public void actualizar(float delta) {
		if (personaje1 != null) {
			personaje1.procesarMovimiento(movJ1, delta);
			// Pasamos el estado del movimiento junto con las acciones
			personaje1.procesarAcciones(accJ1, movJ1);
		}
		if (personaje2 != null) {
			personaje2.procesarMovimiento(movJ2, delta);
			personaje2.procesarAcciones(accJ2, movJ2);
		}
	}

	public void reconfigurarTecla(int jugador, inputAction accion, int nuevaTecla) {
		if (jugador == 1) {
			switch (accion) {
				case movIzquierda: j1Izquierda = nuevaTecla; break;
				case movDerecha:   j1Derecha = nuevaTecla; break;
				case movArriba:    j1Arriba = nuevaTecla; break;
				case movAbajo:     j1Abajo = nuevaTecla; break;
				case ataque:       j1Ataque = nuevaTecla; break;
				case bloqueo:      j1Bloqueo = nuevaTecla; break;
				case especial:     j1Especial = nuevaTecla; break;
			}
		} else if (jugador == 2) {
			switch (accion) {
				case movIzquierda: j2Izquierda = nuevaTecla; break;
				case movDerecha:   j2Derecha = nuevaTecla; break;
				case movArriba:    j2Arriba = nuevaTecla; break;
				case movAbajo:     j2Abajo = nuevaTecla; break;
				case ataque:       j2Ataque = nuevaTecla; break;
				case bloqueo:      j2Bloqueo = nuevaTecla; break;
				case especial:     j2Especial = nuevaTecla; break;
			}
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == j1Izquierda) movJ1[0] = true;
		if (keycode == j1Derecha)   movJ1[1] = true;
		if (keycode == j1Arriba)    movJ1[2] = true;
		if (keycode == j1Abajo)     movJ1[3] = true;
		if (keycode == j1Ataque)   accJ1[0] = true;
		if (keycode == j1Bloqueo)  accJ1[1] = true;
		if (keycode == j1Especial) accJ1[2] = true;

		if (keycode == j2Izquierda) movJ2[0] = true;
		if (keycode == j2Derecha)   movJ2[1] = true;
		if (keycode == j2Arriba)    movJ2[2] = true;
		if (keycode == j2Abajo)     movJ2[3] = true;
		if (keycode == j2Ataque)   accJ2[0] = true;
		if (keycode == j2Bloqueo)  accJ2[1] = true;
		if (keycode == j2Especial) accJ2[2] = true;

		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == j1Izquierda) movJ1[0] = false;
		if (keycode == j1Derecha)   movJ1[1] = false;
		if (keycode == j1Arriba)    movJ1[2] = false;
		if (keycode == j1Abajo)     movJ1[3] = false;
		if (keycode == j1Ataque)   accJ1[0] = false;
		if (keycode == j1Bloqueo)  accJ1[1] = false;
		if (keycode == j1Especial) accJ1[2] = false;

		if (keycode == j2Izquierda) movJ2[0] = false;
		if (keycode == j2Derecha)   movJ2[1] = false;
		if (keycode == j2Arriba)    movJ2[2] = false;
		if (keycode == j2Abajo)     movJ2[3] = false;
		if (keycode == j2Ataque)   accJ2[0] = false;
		if (keycode == j2Bloqueo)  accJ2[1] = false;
		if (keycode == j2Especial) accJ2[2] = false;

		return true;
	}

	public void setPersonaje1(Aves p1) { this.personaje1 = p1; }
	public void setPersonaje2(Aves p2) { this.personaje2 = p2; }
	public Aves getPersonaje1() { return personaje1; }
	public Aves getPersonaje2() { return personaje2; }

	@Override public boolean keyTyped(char character) { return false; }
	@Override public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
	@Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
	@Override public boolean touchCancelled(int screenX, int screenY, int pointer, int button) { return false; }
	@Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
	@Override public boolean mouseMoved(int screenX, int screenY) { return false; }
	@Override public boolean scrolled(float amountX, float amountY) { return false; }
}