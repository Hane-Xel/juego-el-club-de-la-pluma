package personajes;

import controles.Controles;

public class Aves {
	private String ave;
	private float posX;
	private float posY;
	private float velocidadX;
	private float velocidadY;
	private float salud;
	private float energiaEspecial;
	
	public Aves(String nombreAve, float x, float y) {
		this.ave = nombreAve;
		this.posX = x;
		this.posY = y;
		this.velocidadX = 0;
		this.velocidadY = 0;
		this.salud = 100;
		this.energiaEspecial = 0;
	}
	
	/**
	 * Procesa los comandos de movimiento del personaje
	 * @param movimientos array con [arriba, abajo, izquierda, derecha]
	 * @param velocidad velocidad de movimiento
	 */
	public void procesarMovimiento(boolean[] movimientos, float velocidad) {
		// movimientos[0] = arriba, movimientos[1] = abajo
		// movimientos[2] = izquierda, movimientos[3] = derecha
		
		velocidadX = 0;
		velocidadY = 0;
		
		if (movimientos[3]) { // Derecha
			velocidadX = velocidad;
		}
		if (movimientos[2]) { // Izquierda
			velocidadX = -velocidad;
		}
		if (movimientos[0]) { // Arriba
			velocidadY = velocidad;
		}
		if (movimientos[1]) { // Abajo
			velocidadY = -velocidad;
		}
		
		// Actualizar posición
		posX += velocidadX;
		posY += velocidadY;
	}
	
	/**
	 * Ejecuta una acción de combate
	 * @param acciones array con [ataque, bloqueo, especial]
	 */
	public void procesarAcciones(boolean[] acciones) {
		// acciones[0] = ataque, acciones[1] = bloqueo, acciones[2] = especial
		
		if (acciones[0]) {
			ejecutarAtaque();
		}
		if (acciones[1]) {
			ejecutarBloqueo();
		}
		if (acciones[2]) {
			ejecutarEspecial();
		}
	}
	
	/**
	 * Ejecuta un ataque básico
	 */
	private void ejecutarAtaque() {
		System.out.println(ave + " realiza un ataque!");
		// Lógica de ataque aquí
	}
	
	/**
	 * Ejecuta un bloqueo
	 */
	private void ejecutarBloqueo() {
		System.out.println(ave + " se está bloqueando!");
		// Lógica de bloqueo aquí
	}
	
	/**
	 * Ejecuta un movimiento especial
	 */
	private void ejecutarEspecial() {
		if (energiaEspecial >= 100) {
			System.out.println(ave + " realiza un movimiento especial!");
			energiaEspecial = 0;
			// Lógica de especial aquí
		}
	}
	
	/**
	 * Recibe daño
	 * @param cantidad cantidad de daño a recibir
	 */
	public void recibirDaño(float cantidad) {
		salud -= cantidad;
		System.out.println(ave + " recibe " + cantidad + " de daño. Salud: " + salud);
	}
	
	/**
	 * Incrementa la energía especial
	 * @param cantidad cantidad a incrementar
	 */
	public void incrementarEnergiaEspecial(float cantidad) {
		energiaEspecial += cantidad;
		if (energiaEspecial > 100) {
			energiaEspecial = 100;
		}
	}
	
	// Getters y Setters
	public String getAve() {
		return ave;
	}
	
	public void setAve(String ave) {
		this.ave = ave;
	}
	
	public float getPosX() {
		return posX;
	}
	
	public void setPosX(float posX) {
		this.posX = posX;
	}
	
	public float getPosY() {
		return posY;
	}
	
	public void setPosY(float posY) {
		this.posY = posY;
	}
	
	public float getVelocidadX() {
		return velocidadX;
	}
	
	public float getVelocidadY() {
		return velocidadY;
	}
	
	public float getSalud() {
		return salud;
	}
	
	public float getEnergiaEspecial() {
		return energiaEspecial;
	}
	
	public void setSalud(float salud) {
		this.salud = salud;
	}
	
	public void setEnergiaEspecial(float energia) {
		this.energiaEspecial = energia;
	}
}
