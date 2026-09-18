package personajes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import recursos.GestorRecursosPersonajes;
import recursos.GestorRecursosPersonajes.EstadoAnimacion;

public class Aves {
	
	// Knockback
	protected static final float FUERZA_KNOCKBACK = 500f;

    // Identificación
    protected String ave;

    // Transformación y Físicas
    protected float posX, posY;
    protected float velocidadX, velocidadY;
    protected float ancho = 120f, alto = 120f;
    
    // Constantes físicas
    protected static final float GRAVEDAD = -1200f;
    protected static final float VELOCIDAD_MOVIMIENTO = 400f;
    protected static final float FUERZA_SALTO = 900f;

    // Estados
    protected float salud = 100f;
    protected float energiaEspecial = 0f;
    protected boolean enElSuelo = false;
    protected boolean mirandoDerecha = true;

    // Duración de acciones (en segundos)
    protected float tiempoAccion = 0f;
    protected static final float DURACION_ATAQUE = 0.35f;
    protected static final float DURACION_BLOQUEO = 0.40f;

    // Animaciones y Tiempos
    protected EstadoAnimacion estadoActual = EstadoAnimacion.REPOSO;
    protected float stateTime = 0f;

    // Cajas de colisión física y combate
    protected Rectangle hitbox;
    protected Rectangle hitboxAtaque;
    protected boolean atacando = false;
    protected boolean ataqueProcesado = false; // Evita infligir daño múltiple en un solo golpe
    protected float danioAtaque = 5f;
    
 // Añadir entre las variables de Estado:
    protected float saludMaxima = 100f;

    // Añadir el getter correspondiente:
    public float getSaludMaxima() { return saludMaxima; }

    public Aves(String ave, float posX, float posY, String rutaTextura) {
        this.ave = ave;
        this.posX = posX;
        this.posY = posY;
        this.hitbox = new Rectangle(posX, posY, ancho, alto);
        this.hitboxAtaque = new Rectangle(0, 0, 80f, 60f);
    }

    public void actualizar(float delta, Array<Rectangle> plataformas) {
        stateTime += delta;

        // 1. Descontar el tiempo de la acción activa
        if (tiempoAccion > 0) {
            tiempoAccion -= delta;
            if (tiempoAccion <= 0) {
                tiempoAccion = 0;
                atacando = false;
                ataqueProcesado = false;
                estadoActual = EstadoAnimacion.REPOSO;
            }
        }

        // 2. Posicionar Hitbox de Ataque
        if (estadoActual == EstadoAnimacion.ATACAR && tiempoAccion > 0) {
            atacando = true;
            float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width;
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 4));
        } else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }

        // 3. Aplicar Gravedad
        velocidadY += GRAVEDAD * delta;

        // 4. Aplicar Movimiento
        posX += velocidadX * delta;
        posY += velocidadY * delta;

        // 5. Actualizar caja de colisión física
        hitbox.setPosition(posX, posY);

        // 6. Resolver Colisiones con Plataformas
        resolverColisiones(plataformas);

        // 7. Determinar Estado de Animación según Físicas
        determinarEstado();
    }
    
    public void recibirDanio(float danio, float atacanteX) {
        if (estadoActual == EstadoAnimacion.BLOQUEAR) {
            // Si bloquea, recibe daño reducido y un retroceso menor
            salud -= danio * 0.2f;
            float direccion = (posX > atacanteX) ? 1f : -1f;
            velocidadX = direccion * (FUERZA_KNOCKBACK * 0.3f);
        } else {
            salud -= danio;
            estadoActual = EstadoAnimacion.RECIBIR_DANIO;
            tiempoAccion = 0.25f;
            stateTime = 0f;

            // Determina la dirección del impulso: empuja a la derecha si el atacante está a la izquierda y viceversa
            float direccion = (posX > atacanteX) ? 1f : -1f;
            velocidadX = direccion * FUERZA_KNOCKBACK;
        }

        if (salud < 0) salud = 0;
    }

    private void resolverColisiones(Array<Rectangle> plataformas) {
        enElSuelo = false;

        for (Rectangle plataforma : plataformas) {
            if (hitbox.overlaps(plataforma)) {
                if (velocidadY <= 0) {
                    posY = plataforma.y + plataforma.height;
                    velocidadY = 0;
                    enElSuelo = true;
                    hitbox.setPosition(posX, posY);
                    break;
                }
            }
        }

        // Límite de contención en pantalla
        if (posY < 0) {
            posY = 0;
            velocidadY = 0;
            enElSuelo = true;
            hitbox.setPosition(posX, posY);
        }
    }
    
    private void determinarEstado() {
        if (tiempoAccion <= 0 && estadoActual != EstadoAnimacion.RECIBIR_DANIO) {
            if (Math.abs(velocidadX) > 0.1f) {
                estadoActual = EstadoAnimacion.CAMINAR;
            } else {
                estadoActual = EstadoAnimacion.REPOSO;
            }
        }
    }

    public void procesarMovimiento(boolean[] movimientos, float delta) {
        if (tiempoAccion > 0) {
            velocidadX = 0;
            return;
        }

        velocidadX = 0;

        if (movimientos[0]) { // Izquierda
            velocidadX = -VELOCIDAD_MOVIMIENTO;
            mirandoDerecha = false;
        }
        if (movimientos[1]) { // Derecha
            velocidadX = VELOCIDAD_MOVIMIENTO;
            mirandoDerecha = true;
        }
        if (movimientos[2] && enElSuelo) { // Salto
            velocidadY = FUERZA_SALTO;
            enElSuelo = false;
        }
    }

    public void procesarAcciones(boolean[] acciones, boolean[] direccion) {
        if (tiempoAccion > 0) return;

        if (acciones[0]) { // Ataque
            estadoActual = EstadoAnimacion.ATACAR;
            tiempoAccion = DURACION_ATAQUE;
            velocidadX = 0;
            ataqueProcesado = false;
            stateTime = 0f;
        } else if (acciones[1]) { // Bloqueo
            estadoActual = EstadoAnimacion.BLOQUEAR;
            tiempoAccion = DURACION_BLOQUEO;
            velocidadX = 0;
            stateTime = 0f;
        } else if (acciones[2] && energiaEspecial >= 100) { // Especial
            estadoActual = EstadoAnimacion.ESPECIAL;
            tiempoAccion = 0.6f;
            velocidadX = 0;
            energiaEspecial = 0;
            stateTime = 0f;
        }
    }

    public void recibirDanio(float danio) {
        if (estadoActual == EstadoAnimacion.BLOQUEAR) {
            salud -= danio * 0.2f; // Reduce el daño recibido un 80% si está bloqueando
        } else {
            salud -= danio;
            estadoActual = EstadoAnimacion.RECIBIR_DANIO;
            tiempoAccion = 0.25f;
            stateTime = 0f;
        }
        if (salud < 0) salud = 0;
    }

    public void dibujar(SpriteBatch batch) {
        TextureRegion frameActual = GestorRecursosPersonajes.obtenerFrame(ave, estadoActual, stateTime);

        if (frameActual != null) {
            if ((mirandoDerecha && frameActual.isFlipX()) || (!mirandoDerecha && !frameActual.isFlipX())) {
                frameActual.flip(true, false);
            }

            batch.draw(frameActual, posX, posY, ancho, alto);
        }
    }

    // Getters y Setters
    public float getPosX() { return posX; }
    public void setPosX(float posX) { this.posX = posX; this.hitbox.x = posX; }
    public float getPosY() { return posY; }
    public void setPosY(float posY) { this.posY = posY; this.hitbox.y = posY; }
    public Rectangle getHitbox() { return hitbox; }
    public Rectangle getHitboxAtaque() { return hitboxAtaque; }
    public boolean isAtacando() { return atacando; }
    public boolean isAtaqueProcesado() { return ataqueProcesado; }
    public void setAtaqueProcesado(boolean procesado) { this.ataqueProcesado = procesado; }
    public float getDanioAtaque() { return danioAtaque; }
    public float getSalud() { return salud; }
}