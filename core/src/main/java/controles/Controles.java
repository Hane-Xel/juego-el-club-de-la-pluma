package controles;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

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
	
	// Mapeo de teclas para Jugador 1 (WASD)
	private static final int JUGADOR1_ARRIBA = Input.Keys.W;
	private static final int JUGADOR1_ABAJO = Input.Keys.S;
	private static final int JUGADOR1_IZQUIERDA = Input.Keys.A;
	private static final int JUGADOR1_DERECHA = Input.Keys.D;
	private static final int JUGADOR1_ATAQUE = Input.Keys.U;
	private static final int JUGADOR1_BLOQUEO = Input.Keys.Y;
	private static final int JUGADOR1_ESPECIAL = Input.Keys.I;
	
	// Mapeo de teclas para Jugador 2 (Flechas)
	private static final int JUGADOR2_ARRIBA = Input.Keys.UP;
	private static final int JUGADOR2_ABAJO = Input.Keys.DOWN;
	private static final int JUGADOR2_IZQUIERDA = Input.Keys.LEFT;
	private static final int JUGADOR2_DERECHA = Input.Keys.RIGHT;
	private static final int JUGADOR2_ATAQUE = Input.Keys.NUMPAD_2;
	private static final int JUGADOR2_BLOQUEO = Input.Keys.NUMPAD_1;
	private static final int JUGADOR2_ESPECIAL = Input.Keys.NUMPAD_3;
	
	// Arrays para rastrear el estado de las teclas presionadas
	// Movimiento: [arriba, abajo, izquierda, derecha]
	private boolean[] teclasMovimientoJugador1 = new boolean[4];
	private boolean[] teclasMovimientoJugador2 = new boolean[4];
	
	// Acciones de combate: [ataque, bloqueo, especial]
	private boolean[] teclasAccionesJugador1 = new boolean[3];
	private boolean[] teclasAccionesJugador2 = new boolean[3];
	
	/**
	 * Obtiene el estado de movimiento del Jugador 1
	 * @return array con [arriba, abajo, izquierda, derecha]
	 */
	public boolean[] obtenerMovimientoJugador1() {
		return teclasMovimientoJugador1;
	}
	
	/**
	 * Obtiene el estado de movimiento del Jugador 2
	 * @return array con [arriba, abajo, izquierda, derecha]
	 */
	public boolean[] obtenerMovimientoJugador2() {
		return teclasMovimientoJugador2;
	}
	
	/**
	 * Obtiene el estado de acciones del Jugador 1
	 * @return array con [ataque, bloqueo, especial]
	 */
	public boolean[] obtenerAccionesJugador1() {
		return teclasAccionesJugador1;
	}
	
	/**
	 * Obtiene el estado de acciones del Jugador 2
	 * @return array con [ataque, bloqueo, especial]
	 */
	public boolean[] obtenerAccionesJugador2() {
		return teclasAccionesJugador2;
	}
	
	/**
	 * Verifica si una acción está activa para el Jugador 1
	 * @param accion la acción a verificar
	 * @return true si la acción está activa
	 */
	public boolean estaActivoJugador1(inputAction accion) {
		switch(accion) {
			case movArriba:
				return teclasMovimientoJugador1[0];
			case movAbajo:
				return teclasMovimientoJugador1[1];
			case movIzquierda:
				return teclasMovimientoJugador1[2];
			case movDerecha:
				return teclasMovimientoJugador1[3];
			case ataque:
				return teclasAccionesJugador1[0];
			case bloqueo:
				return teclasAccionesJugador1[1];
			case especial:
				return teclasAccionesJugador1[2];
			default:
				return false;
		}
	}
	
	/**
	 * Verifica si una acción está activa para el Jugador 2
	 * @param accion la acción a verificar
	 * @return true si la acción está activa
	 */
	public boolean estaActivoJugador2(inputAction accion) {
		switch(accion) {
			case movArriba:
				return teclasMovimientoJugador2[0];
			case movAbajo:
				return teclasMovimientoJugador2[1];
			case movIzquierda:
				return teclasMovimientoJugador2[2];
			case movDerecha:
				return teclasMovimientoJugador2[3];
			case ataque:
				return teclasAccionesJugador2[0];
			case bloqueo:
				return teclasAccionesJugador2[1];
			case especial:
				return teclasAccionesJugador2[2];
			default:
				return false;
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		// Procesar entrada del Jugador 1 (WASD + U, Y, I)
		if (keycode == JUGADOR1_ARRIBA) {
			teclasMovimientoJugador1[0] = true;
		} else if (keycode == JUGADOR1_ABAJO) {
			teclasMovimientoJugador1[1] = true;
		} else if (keycode == JUGADOR1_IZQUIERDA) {
			teclasMovimientoJugador1[2] = true;
		} else if (keycode == JUGADOR1_DERECHA) {
			teclasMovimientoJugador1[3] = true;
		} else if (keycode == JUGADOR1_ATAQUE) {
			teclasAccionesJugador1[0] = true;
		} else if (keycode == JUGADOR1_BLOQUEO) {
			teclasAccionesJugador1[1] = true;
		} else if (keycode == JUGADOR1_ESPECIAL) {
			teclasAccionesJugador1[2] = true;
		}
		
		// Procesar entrada del Jugador 2 (Flechas + Numpad 1, 2, 3)
		if (keycode == JUGADOR2_ARRIBA) {
			teclasMovimientoJugador2[0] = true;
		} else if (keycode == JUGADOR2_ABAJO) {
			teclasMovimientoJugador2[1] = true;
		} else if (keycode == JUGADOR2_IZQUIERDA) {
			teclasMovimientoJugador2[2] = true;
		} else if (keycode == JUGADOR2_DERECHA) {
			teclasMovimientoJugador2[3] = true;
		} else if (keycode == JUGADOR2_ATAQUE) {
			teclasAccionesJugador2[0] = true;
		} else if (keycode == JUGADOR2_BLOQUEO) {
			teclasAccionesJugador2[1] = true;
		} else if (keycode == JUGADOR2_ESPECIAL) {
			teclasAccionesJugador2[2] = true;
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// Liberar tecla del Jugador 1 (WASD + U, Y, I)
		if (keycode == JUGADOR1_ARRIBA) {
			teclasMovimientoJugador1[0] = false;
		} else if (keycode == JUGADOR1_ABAJO) {
			teclasMovimientoJugador1[1] = false;
		} else if (keycode == JUGADOR1_IZQUIERDA) {
			teclasMovimientoJugador1[2] = false;
		} else if (keycode == JUGADOR1_DERECHA) {
			teclasMovimientoJugador1[3] = false;
		} else if (keycode == JUGADOR1_ATAQUE) {
			teclasAccionesJugador1[0] = false;
		} else if (keycode == JUGADOR1_BLOQUEO) {
			teclasAccionesJugador1[1] = false;
		} else if (keycode == JUGADOR1_ESPECIAL) {
			teclasAccionesJugador1[2] = false;
		}
		
		// Liberar tecla del Jugador 2 (Flechas + Numpad 1, 2, 3)
		if (keycode == JUGADOR2_ARRIBA) {
			teclasMovimientoJugador2[0] = false;
		} else if (keycode == JUGADOR2_ABAJO) {
			teclasMovimientoJugador2[1] = false;
		} else if (keycode == JUGADOR2_IZQUIERDA) {
			teclasMovimientoJugador2[2] = false;
		} else if (keycode == JUGADOR2_DERECHA) {
			teclasMovimientoJugador2[3] = false;
		} else if (keycode == JUGADOR2_ATAQUE) {
			teclasAccionesJugador2[0] = false;
		} else if (keycode == JUGADOR2_BLOQUEO) {
			teclasAccionesJugador2[1] = false;
		} else if (keycode == JUGADOR2_ESPECIAL) {
			teclasAccionesJugador2[2] = false;
		}
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}
}
