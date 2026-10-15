package personajes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import recursos.GestorRecursosPersonajes;
import recursos.GestorRecursosPersonajes.EstadoAnimacion;

public class Aves {
	
    protected static final float FUERZA_KNOCKBACK = 500f;

    protected String ave;

    protected float posX, posY;
    protected float velocidadX, velocidadY;
    protected float ancho = 120f, alto = 120f;
    
    protected static final float GRAVEDAD = -2500f;
    protected static final float VELOCIDAD_MOVIMIENTO = 400f;
    protected static final float FUERZA_SALTO = 1300f;

    protected static final float ANCHO_PANTALLA_DEFECTO = 1920f;

    // --- TIPOS DE ATAQUE ---
    public enum TipoAtaque {
        NINGUNO,
        NEUTRAL,
        LATERAL,
        ABAJO
    }

    protected TipoAtaque ataqueActual = TipoAtaque.NINGUNO;

    // --- TIEMPOS Y PROPIEDADES DEL ATAQUE NEUTRAL ---
    protected static final float TIEMPO_STARTUP_NEUTRAL  = 0.10f;
    protected static final float TIEMPO_ACTIVO_NEUTRAL   = 0.10f;
    protected static final float TIEMPO_RECOVERY_NEUTRAL = 0.15f;
    protected static final float DURACION_TOTAL_NEUTRAL  = TIEMPO_STARTUP_NEUTRAL + TIEMPO_ACTIVO_NEUTRAL + TIEMPO_RECOVERY_NEUTRAL;
    protected static final float DANIO_NEUTRAL           = 5f;

    // --- TIEMPOS Y PROPIEDADES DEL ATAQUE LATERAL (EMBESTIDA) ---
    protected static final float TIEMPO_STARTUP_LATERAL  = 0.12f;
    protected static final float TIEMPO_ACTIVO_LATERAL   = 0.18f;
    protected static final float TIEMPO_RECOVERY_LATERAL = 0.20f;
    protected static final float DURACION_TOTAL_LATERAL  = TIEMPO_STARTUP_LATERAL + TIEMPO_ACTIVO_LATERAL + TIEMPO_RECOVERY_LATERAL;
    protected static final float VELOCIDAD_CARGA_LATERAL = 800f;
    protected static final float DANIO_LATERAL           = 12f;

    // --- TIEMPOS Y PROPIEDADES DEL ATAQUE ABAJO (BARRIDA / DOBLE LADO) ---
    protected static final float TIEMPO_STARTUP_ABAJO  = 0.08f;
    protected static final float TIEMPO_ACTIVO_ABAJO   = 0.12f;
    protected static final float TIEMPO_RECOVERY_ABAJO = 0.18f;
    protected static final float DURACION_TOTAL_ABAJO  = TIEMPO_STARTUP_ABAJO + TIEMPO_ACTIVO_ABAJO + TIEMPO_RECOVERY_ABAJO;
    protected static final float ALCANCE_DOBLE_ABAJO   = 200f;
    protected static final float DANIO_ABAJO           = 7f;

    protected float salud = 100f;
    protected float energiaEspecial = 0f;
    protected boolean enElSuelo = false;
    protected boolean mirandoDerecha = true;

    protected float tiempoAccion = 0f;
    protected static final float DURACION_BLOQUEO = 0.40f;

    protected EstadoAnimacion estadoActual = EstadoAnimacion.REPOSO;
    protected float stateTime = 0f;

    protected Rectangle hitbox;
    protected Rectangle hitboxAtaque;
    protected boolean atacando = false;
    protected boolean ataqueProcesado = false;
    protected float danioAtaque = 5f;
    
    protected float saludMaxima = 100f;

    public float getSaludMaxima() { return saludMaxima; }

    public Aves(String ave, float posX, float posY, String rutaTextura) {
        this.ave = ave;
        this.posX = posX;
        this.posY = posY;
        this.hitbox = new Rectangle(posX, posY, ancho, alto);
        this.hitboxAtaque = new Rectangle(0, 0, 80f, 60f);
    }

    public void actualizar(float delta, Array<Rectangle> plataformas) {
        actualizar(delta, plataformas, ANCHO_PANTALLA_DEFECTO);
    }

    public void actualizar(float delta, Array<Rectangle> plataformas, float anchoPantalla) {
        stateTime += delta;

        // 1. Contador de tiempo para las acciones
        if (tiempoAccion > 0) {
            tiempoAccion -= delta;
            if (tiempoAccion <= 0) {
                tiempoAccion = 0;
                atacando = false;
                ataqueProcesado = false;
                ataqueActual = TipoAtaque.NINGUNO;
                estadoActual = EstadoAnimacion.REPOSO;
            }
        }

        // 2. Lógica del Ataque en Proceso
        boolean estaAtacando = (estadoActual == EstadoAnimacion.ATACAR_NEUTRAL ||
                                estadoActual == EstadoAnimacion.ATACAR_LATERAL ||
                                estadoActual == EstadoAnimacion.ATACAR_ABAJO);

        if (estaAtacando && tiempoAccion > 0) {
            
            if (ataqueActual == TipoAtaque.NEUTRAL) {
                actualizarAtaqueNeutral();
            } else if (ataqueActual == TipoAtaque.LATERAL) {
                actualizarAtaqueLateral(delta);
            } else if (ataqueActual == TipoAtaque.ABAJO) {
                actualizarAtaqueAbajo();
            }

        } else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }

        // 3. Físicas generales (Gravedad y Posición)
        velocidadY += GRAVEDAD * delta;

        posX += velocidadX * delta;
        posY += velocidadY * delta;

        hitbox.setPosition(posX, posY);

        // 4. Colisiones
        resolverColisiones(plataformas, anchoPantalla);

        // 5. Estado Visual
        determinarEstado();
    }

    private void actualizarAtaqueNeutral() {
        hitboxAtaque.setSize(80f, 60f);
        float tiempoTranscurrido = DURACION_TOTAL_NEUTRAL - tiempoAccion;
        boolean enDano = tiempoTranscurrido >= TIEMPO_STARTUP_NEUTRAL && 
                         tiempoTranscurrido <= (TIEMPO_STARTUP_NEUTRAL + TIEMPO_ACTIVO_NEUTRAL);

        if (enDano) {
            atacando = true;
            float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width;
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 4));
        } else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }
    }

    private void actualizarAtaqueLateral(float delta) {
        hitboxAtaque.setSize(80f, 60f);
        float tiempoTranscurrido = DURACION_TOTAL_LATERAL - tiempoAccion;
        float direccion = mirandoDerecha ? 1f : -1f;

        if (tiempoTranscurrido < TIEMPO_STARTUP_LATERAL) {
            velocidadX = 0;
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        } else if (tiempoTranscurrido <= (TIEMPO_STARTUP_LATERAL + TIEMPO_ACTIVO_LATERAL)) {
            velocidadX = direccion * VELOCIDAD_CARGA_LATERAL;
            atacando = true;
            
            float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width;
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 4));
        } else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);

            if (velocidadX != 0) {
                float desaceleracion = VELOCIDAD_CARGA_LATERAL * 5f * delta;
                if (velocidadX > 0) {
                    velocidadX = Math.max(0, velocidadX - desaceleracion);
                } else {
                    velocidadX = Math.min(0, velocidadX + desaceleracion);
                }
            }
        }
    }

    private void actualizarAtaqueAbajo() {
        velocidadX = 0;

        float tiempoTranscurrido = DURACION_TOTAL_ABAJO - tiempoAccion;
        boolean enDano = tiempoTranscurrido >= TIEMPO_STARTUP_ABAJO && 
                         tiempoTranscurrido <= (TIEMPO_STARTUP_ABAJO + TIEMPO_ACTIVO_ABAJO);

        if (enDano) {
            atacando = true;
            hitboxAtaque.setSize(ALCANCE_DOBLE_ABAJO, 40f);

            float centroPersonajeX = posX + (ancho / 2f);
            float nuevaPosX = centroPersonajeX - (hitboxAtaque.width / 2f);
            
            hitboxAtaque.setPosition(nuevaPosX, posY);
        } else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }
    }

    public void procesarMovimiento(boolean[] movimientos, float delta) {
        if (tiempoAccion > 0) {
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

        if (acciones[0]) { // Botón de Ataque presionado
            
            boolean presionaHorizontal = direccion[0] || direccion[1]; // Izquierda o Derecha
            boolean presionaAbajo      = direccion[3];                 // Abajo

            if (presionaAbajo) {
                ataqueActual  = TipoAtaque.ABAJO;
                estadoActual  = EstadoAnimacion.ATACAR_ABAJO;
                tiempoAccion  = DURACION_TOTAL_ABAJO;
                danioAtaque   = DANIO_ABAJO;
            } else if (presionaHorizontal) {
                ataqueActual  = TipoAtaque.LATERAL;
                estadoActual  = EstadoAnimacion.ATACAR_LATERAL;
                tiempoAccion  = DURACION_TOTAL_LATERAL;
                danioAtaque   = DANIO_LATERAL;
            } else {
                ataqueActual  = TipoAtaque.NEUTRAL;
                estadoActual  = EstadoAnimacion.ATACAR_NEUTRAL;
                tiempoAccion  = DURACION_TOTAL_NEUTRAL;
                danioAtaque   = DANIO_NEUTRAL;
            }

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

    public void recibirDanio(float danio, float atacanteX) {
        if (estadoActual == EstadoAnimacion.BLOQUEAR) {
            salud -= danio * 0.2f;
            float direccion = (posX > atacanteX) ? 1f : -1f;
            velocidadX = direccion * (FUERZA_KNOCKBACK * 0.3f);
        } else {
            salud -= danio;
            estadoActual = EstadoAnimacion.RECIBIR_DANIO;
            tiempoAccion = 0.25f;
            stateTime = 0f;

            float direccion = (posX > atacanteX) ? 1f : -1f;
            velocidadX = direccion * FUERZA_KNOCKBACK;
        }

        if (salud < 0) salud = 0;
    }

    public void recibirDanio(float danio) {
        recibirDanio(danio, posX);
    }

    private void resolverColisiones(Array<Rectangle> plataformas, float anchoPantalla) {
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

        // Límite inferior seguro para no caer por debajo del piso de Playa (Y = 150)
        if (posY < 150f) {
            posY = 150f;
            velocidadY = 0;
            enElSuelo = true;
            hitbox.setPosition(posX, posY);
        }

        if (posX < 0) {
            posX = 0;
            if (velocidadX < 0) velocidadX = 0;
            hitbox.setPosition(posX, posY);
        }

        if (posX + ancho > anchoPantalla) {
            posX = anchoPantalla - ancho;
            if (velocidadX > 0) velocidadX = 0;
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

    public void dibujar(SpriteBatch batch) {
        TextureRegion frameActual = GestorRecursosPersonajes.obtenerFrame(ave, estadoActual, stateTime);

        if (frameActual == null) {
            System.out.println("ERROR DIBUJO: No existe la clave '" + ave + "' o el estado '" + estadoActual + "' en el Gestor.");
        } else {
            if ((mirandoDerecha && frameActual.isFlipX()) || (!mirandoDerecha && !frameActual.isFlipX())) {
                frameActual.flip(true, false);
            }

            batch.draw(frameActual, posX, posY, ancho, alto);
        }
    }

    // --- GETTERS Y SETTERS ---
    public float getPosX() { return posX; }
    public void setPosX(float posX) { this.posX = posX; this.hitbox.x = posX; }
    public float getPosY() { return posY; }
    public void setPosY(float posY) { this.posY = posY; this.hitbox.y = posY; }
    public float getAncho() { return ancho; }
    public float getAlto() { return alto; }
    public Rectangle getHitbox() { return hitbox; }
    public Rectangle getHitboxAtaque() { return hitboxAtaque; }
    public boolean isAtacando() { return atacando; }
    public boolean isAtaqueProcesado() { return ataqueProcesado; }
    public void setAtaqueProcesado(boolean procesado) { this.ataqueProcesado = procesado; }
    public float getDanioAtaque() { return danioAtaque; }
    public float getSalud() { return salud; }
}