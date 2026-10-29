package personajes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import recursos.GestorRecursosPersonajes;
import recursos.GestorRecursosPersonajes.EstadoAnimacion;

public class Aves {
	
    protected float FUERZA_KNOCKBACK = 500f;

    protected String ave;

    protected float posX, posY;
    protected float velocidadX, velocidadY;
    protected float ancho = 120f, alto = 120f;
    
    protected static final float GRAVEDAD = -2500f;
    protected float VELOCIDAD_MOVIMIENTO = 400f;
    protected float FUERZA_SALTO = 1300f;

    protected static final float ANCHO_PANTALLA_DEFECTO = 1920f;

    // --- TIPOS DE ATAQUE ---
    public enum TipoAtaque {
        NINGUNO,
        NEUTRAL,
        LATERAL,
        ABAJO
    }
    
    public enum TipoAtaqueAereo {
        NINGUNO,
        AEREO_NEUTRAL,
        AEREO_LATERAL,
        AEREO_ABAJO
    }

    public enum TipoAtaqueEspecial {
        NINGUNO,
        ESP_NEUTRAL,
        ESP_LATERAL,
        ESP_ABAJO,
        ESP_AEREO_NEUTRAL,
        ESP_AEREO_LATERAL,
        ESP_AEREO_ABAJO
    }

    // --- GANANCIA DE ESPECIAL POR ATAQUE (Tierra) ---
    protected float ESPECIAL_GANADO_NEUTRAL = 15f;
    protected float ESPECIAL_GANADO_LATERAL = 25f;
    protected float ESPECIAL_GANADO_ABAJO   = 18f;

    // --- GANANCIA DE ESPECIAL POR ATAQUE (Aire) ---
    protected float ESPECIAL_GANADO_AEREO_NEUTRAL = 12f;
    protected float ESPECIAL_GANADO_AEREO_LATERAL = 22f;
    protected float ESPECIAL_GANADO_AEREO_ABAJO   = 16f;

    protected TipoAtaque ataqueActual = TipoAtaque.NINGUNO;
    protected TipoAtaqueAereo ataqueAereoActual = TipoAtaqueAereo.NINGUNO;
    protected TipoAtaqueEspecial especialActual = TipoAtaqueEspecial.NINGUNO;

    // --- TIEMPOS Y PROPIEDADES DEL ATAQUE AÉREO NEUTRAL ---
    protected float TIEMPO_STARTUP_AEREO_NEUTRAL  = 0.08f;
    protected float TIEMPO_ACTIVO_AEREO_NEUTRAL   = 0.12f;
    protected float TIEMPO_RECOVERY_AEREO_NEUTRAL = 0.15f;
    protected float DURACION_TOTAL_AEREO_NEUTRAL  = TIEMPO_STARTUP_AEREO_NEUTRAL + TIEMPO_ACTIVO_AEREO_NEUTRAL + TIEMPO_RECOVERY_AEREO_NEUTRAL;
    protected float DANIO_AEREO_NEUTRAL           = 6f;

    // --- TIEMPOS Y PROPIEDADES DEL ATAQUE AÉREO LATERAL ---
    protected float TIEMPO_STARTUP_AEREO_LATERAL  = 0.10f;
    protected float TIEMPO_ACTIVO_AEREO_LATERAL   = 0.15f;
    protected float TIEMPO_RECOVERY_AEREO_LATERAL = 0.20f;
    protected float DURACION_TOTAL_AEREO_LATERAL  = TIEMPO_STARTUP_AEREO_LATERAL + TIEMPO_ACTIVO_AEREO_LATERAL + TIEMPO_RECOVERY_AEREO_LATERAL;
    protected float DANIO_AEREO_LATERAL           = 10f;

    // --- TIEMPOS Y PROPIEDADES DEL ATAQUE AÉREO ABAJO ---
    protected float TIEMPO_STARTUP_AEREO_ABAJO  = 0.06f;
    protected float TIEMPO_ACTIVO_AEREO_ABAJO   = 0.10f;
    protected float TIEMPO_RECOVERY_AEREO_ABAJO = 0.18f;
    protected float DURACION_TOTAL_AEREO_ABAJO  = TIEMPO_STARTUP_AEREO_ABAJO + TIEMPO_ACTIVO_AEREO_ABAJO + TIEMPO_RECOVERY_AEREO_ABAJO;
    protected float DANIO_AEREO_ABAJO           = 8f;

    // --- TIEMPOS Y PROPIEDADES DEL ATAQUE NEUTRAL ---
    protected float TIEMPO_STARTUP_NEUTRAL  = 0.10f;
    protected float TIEMPO_ACTIVO_NEUTRAL   = 0.10f;
    protected float TIEMPO_RECOVERY_NEUTRAL = 0.15f;
    protected float DURACION_TOTAL_NEUTRAL  = TIEMPO_STARTUP_NEUTRAL + TIEMPO_ACTIVO_NEUTRAL + TIEMPO_RECOVERY_NEUTRAL;
    protected float DANIO_NEUTRAL           = 5f;

    // --- TIEMPOS Y PROPIEDADES DEL ATAQUE LATERAL ---
    protected float TIEMPO_STARTUP_LATERAL  = 0.12f;
    protected float TIEMPO_ACTIVO_LATERAL   = 0.18f;
    protected float TIEMPO_RECOVERY_LATERAL = 0.20f;
    protected float DURACION_TOTAL_LATERAL  = TIEMPO_STARTUP_LATERAL + TIEMPO_ACTIVO_LATERAL + TIEMPO_RECOVERY_LATERAL;
    protected float VELOCIDAD_CARGA_LATERAL = 800f;
    protected float DANIO_LATERAL           = 12f;

    // --- TIEMPOS Y PROPIEDADES DEL ATAQUE ABAJO ---
    protected float TIEMPO_STARTUP_ABAJO  = 0.08f;
    protected float TIEMPO_ACTIVO_ABAJO   = 0.12f;
    protected float TIEMPO_RECOVERY_ABAJO = 0.18f;
    protected float DURACION_TOTAL_ABAJO  = TIEMPO_STARTUP_ABAJO + TIEMPO_ACTIVO_ABAJO + TIEMPO_RECOVERY_ABAJO;
    protected float ALCANCE_DOBLE_ABAJO   = 200f;
    protected float DANIO_ABAJO           = 7f;

    // --- TIEMPOS Y PROPIEDADES DE LOS ESPECIALES ---
 // --- TIEMPOS Y PROPIEDADES DE LOS ESPECIALES ---
    protected float TIEMPO_STARTUP_ESP_NEUTRAL  = 0.08f;
    protected float TIEMPO_ACTIVO_ESP_NEUTRAL   = 0.15f;
    protected float TIEMPO_RECOVERY_ESP_NEUTRAL = 0.15f;
    protected float DURACION_TOTAL_ESP_NEUTRAL  = TIEMPO_STARTUP_ESP_NEUTRAL + TIEMPO_ACTIVO_ESP_NEUTRAL + TIEMPO_RECOVERY_ESP_NEUTRAL;
    protected float DANIO_ESP_NEUTRAL           = 18f;

    protected float TIEMPO_STARTUP_ESP_LATERAL  = 0.16f;
    protected float TIEMPO_ACTIVO_ESP_LATERAL   = 0.24f;
    protected float TIEMPO_RECOVERY_ESP_LATERAL = 0.20f;
    protected float DURACION_TOTAL_ESP_LATERAL  = TIEMPO_STARTUP_ESP_LATERAL + TIEMPO_ACTIVO_ESP_LATERAL + TIEMPO_RECOVERY_ESP_LATERAL;
    protected float DANIO_ESP_LATERAL           = 18f;
    
 // --- TIEMPOS Y PROPIEDADES DEL ESPECIAL ABAJO ---
    protected float TIEMPO_STARTUP_ESP_ABAJO  = 0.08f;
    protected float TIEMPO_ACTIVO_ESP_ABAJO   = 0.12f;
    protected float TIEMPO_RECOVERY_ESP_ABAJO = 0.18f;
    protected float DURACION_TOTAL_ESP_ABAJO  = TIEMPO_STARTUP_ESP_ABAJO + TIEMPO_ACTIVO_ESP_ABAJO + TIEMPO_RECOVERY_ESP_ABAJO;
    protected float DANIO_ESP_ABAJO           = 16f;
    
 // --- TIEMPOS Y PROPIEDADES DEL ESPECIAL AÉREO LATERAL ---
    protected float TIEMPO_STARTUP_ESP_AEREO_LATERAL  = 0.10f;
    protected float TIEMPO_ACTIVO_ESP_AEREO_LATERAL   = 0.15f;
    protected float TIEMPO_RECOVERY_ESP_AEREO_LATERAL = 0.20f;
    protected float DURACION_TOTAL_ESP_AEREO_LATERAL  = TIEMPO_STARTUP_ESP_AEREO_LATERAL + TIEMPO_ACTIVO_ESP_AEREO_LATERAL + TIEMPO_RECOVERY_ESP_AEREO_LATERAL;
    protected float DANIO_ESP_AEREO_LATERAL           = 16f;
    
 // --- TIEMPOS Y PROPIEDADES DEL ESPECIAL AÉREO NEUTRAL ---
    protected float TIEMPO_STARTUP_ESP_AEREO_NEUTRAL  = 0.08f;
    protected float TIEMPO_ACTIVO_ESP_AEREO_NEUTRAL   = 0.15f;
    protected float TIEMPO_RECOVERY_ESP_AEREO_NEUTRAL = 0.15f;
    protected float DURACION_TOTAL_ESP_AEREO_NEUTRAL  = TIEMPO_STARTUP_ESP_AEREO_NEUTRAL + TIEMPO_ACTIVO_ESP_AEREO_NEUTRAL + TIEMPO_RECOVERY_ESP_AEREO_NEUTRAL;
    protected float DANIO_ESP_AEREO_NEUTRAL           = 16f;
    
 // --- TIEMPOS Y PROPIEDADES DEL ESPECIAL AÉREO ABAJO ---
    protected float TIEMPO_STARTUP_ESP_AEREO_ABAJO  = 0.06f;
    protected float TIEMPO_ACTIVO_ESP_AEREO_ABAJO   = 0.10f;
    protected float TIEMPO_RECOVERY_ESP_AEREO_ABAJO = 0.18f;
    protected float DURACION_TOTAL_ESP_AEREO_ABAJO  = TIEMPO_STARTUP_ESP_AEREO_ABAJO + TIEMPO_ACTIVO_ESP_AEREO_ABAJO + TIEMPO_RECOVERY_ESP_AEREO_ABAJO;
    protected float DANIO_ESP_AEREO_ABAJO           = 16f;

    protected float salud = 1000f;
    protected float saludMaxima = 1000f;
    protected boolean enElSuelo = false;
    protected boolean mirandoDerecha = true;
    protected boolean inermeEnAire = false; 
    
    protected float energiaEspecial = 0f;
    protected float energiaEspecialMaxima = 300f;
    protected float COSTO_BARRA_ESPECIAL = 100f;

    protected float tiempoAccion = 0f;
    protected float DURACION_BLOQUEO = 0.40f;

    protected EstadoAnimacion estadoActual = EstadoAnimacion.REPOSO;
    protected float stateTime = 0f;

    protected Rectangle hitbox;
    protected Rectangle hitboxAtaque;
    protected boolean atacando = false;
    protected boolean ataqueProcesado = false;
    protected float danioAtaque = 5f;

    public Aves(String ave, float posX, float posY, String rutaTextura) {
        this.ave = ave;
        this.posX = posX;
        this.posY = posY;
        this.hitbox = new Rectangle(posX, posY, ancho, alto);
        this.hitboxAtaque = new Rectangle(0, 0, 80f, 60f);
        this.saludMaxima = this.salud;
    }

    public void actualizar(float delta, Array<Rectangle> plataformas) {
        actualizar(delta, plataformas, ANCHO_PANTALLA_DEFECTO);
    }

    public void actualizar(float delta, Array<Rectangle> plataformas, float anchoPantalla) {
        stateTime += delta;

        // 1. Contador de tiempo para las acciones (RESETEO COMPLETO)
        if (tiempoAccion > 0) {
            tiempoAccion -= delta;
            if (tiempoAccion <= 0) {
                tiempoAccion = 0;
                atacando = false;
                ataqueProcesado = false;
                
                // Reseteamos todas las variables de estado de ataque
                ataqueActual = TipoAtaque.NINGUNO;
                ataqueAereoActual = TipoAtaqueAereo.NINGUNO;
                especialActual = TipoAtaqueEspecial.NINGUNO;
                
                estadoActual = EstadoAnimacion.REPOSO;
            }
        }

        // 2. Comprobación completa de estados de ataque
        boolean estaAtacando = (estadoActual == EstadoAnimacion.ATACAR_NEUTRAL ||
                                estadoActual == EstadoAnimacion.ATACAR_LATERAL ||
                                estadoActual == EstadoAnimacion.ATACAR_ABAJO ||
                                estadoActual == EstadoAnimacion.ATACAR_AEREO_NEUTRAL ||
                                estadoActual == EstadoAnimacion.ATACAR_AEREO_LATERAL ||
                                estadoActual == EstadoAnimacion.ATACAR_AEREO_ABAJO ||
                                estadoActual == EstadoAnimacion.ESPECIAL_NEUTRAL ||
                                estadoActual == EstadoAnimacion.ESPECIAL_LATERAL ||
                                estadoActual == EstadoAnimacion.ESPECIAL_ABAJO ||
                                estadoActual == EstadoAnimacion.ESPECIAL_AEREO_NEUTRAL ||
                                estadoActual == EstadoAnimacion.ESPECIAL_AEREO_LATERAL ||
                                estadoActual == EstadoAnimacion.ESPECIAL_AEREO_ABAJO);

        if (estaAtacando && tiempoAccion > 0) {
            if (especialActual != TipoAtaqueEspecial.NINGUNO) {
                actualizarEspeciales(delta);
            } else if (enElSuelo) {
                switch (ataqueActual) {
                    case NEUTRAL: actualizarAtaqueNeutral(); break;
                    case LATERAL: actualizarAtaqueLateral(delta); break;
                    case ABAJO:   actualizarAtaqueAbajo(); break;
                    default: break;
                }
            } else {
                switch (ataqueAereoActual) {
                    case AEREO_NEUTRAL: actualizarAtaqueNeutralAereo(); break;
                    case AEREO_LATERAL: actualizarAtaqueLateralAereo(delta); break;
                    case AEREO_ABAJO:   actualizarAtaqueAbajoAereo(); break;
                    default: break;
                }
            }
        } else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }

        // 3. Físicas
        velocidadY += GRAVEDAD * delta;
        posX += velocidadX * delta;
        posY += velocidadY * delta;

        hitbox.setPosition(posX, posY);
        resolverColisiones(plataformas, anchoPantalla);
        determinarEstado();
    }

    protected void actualizarAtaqueNeutral() {
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
    
    protected void actualizarAtaqueNeutralAereo() {
        hitboxAtaque.setSize(80f, 60f);
        float tiempoTranscurrido = DURACION_TOTAL_AEREO_NEUTRAL - tiempoAccion;
        boolean enDano = tiempoTranscurrido >= TIEMPO_STARTUP_AEREO_NEUTRAL && 
                         tiempoTranscurrido <= (TIEMPO_STARTUP_AEREO_NEUTRAL + TIEMPO_ACTIVO_AEREO_NEUTRAL);

        if (enDano) {
            atacando = true;
            float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width;
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 4));
        } else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }
    }

    protected void actualizarAtaqueLateral(float delta) {
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
                velocidadX = mirandoDerecha ? Math.max(0, velocidadX - desaceleracion) : Math.min(0, velocidadX + desaceleracion);
            }
        }
    }
    
    protected void actualizarAtaqueLateralAereo(float delta) {
        hitboxAtaque.setSize(80f, 60f);
        float VELOCIDAD_CARGA_AEREO_LATERAL = 600f;
        float tiempoTranscurrido = DURACION_TOTAL_AEREO_LATERAL - tiempoAccion;
        float direccion = mirandoDerecha ? 1f : -1f;

        if (tiempoTranscurrido < TIEMPO_STARTUP_AEREO_LATERAL) {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        } else if (tiempoTranscurrido <= (TIEMPO_STARTUP_AEREO_LATERAL + TIEMPO_ACTIVO_AEREO_LATERAL)) {
            velocidadX = direccion * VELOCIDAD_CARGA_AEREO_LATERAL;
            atacando = true;
            float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width;
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 4));
        } else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            if (velocidadX != 0) {
                float desaceleracion = VELOCIDAD_CARGA_AEREO_LATERAL * 5f * delta;
                velocidadX = mirandoDerecha ? Math.max(0, velocidadX - desaceleracion) : Math.min(0, velocidadX + desaceleracion);
            }
        }
    }

    protected void actualizarAtaqueAbajo() {
        velocidadX = 0;
        float tiempoTranscurrido = DURACION_TOTAL_ABAJO - tiempoAccion;
        boolean enDano = tiempoTranscurrido >= TIEMPO_STARTUP_ABAJO && 
                         tiempoTranscurrido <= (TIEMPO_STARTUP_ABAJO + TIEMPO_ACTIVO_ABAJO);

        if (enDano) {
            atacando = true;
            hitboxAtaque.setSize(ALCANCE_DOBLE_ABAJO, 40f);
            float centroPersonajeX = posX + (ancho / 2f);
            hitboxAtaque.setPosition(centroPersonajeX - (hitboxAtaque.width / 2f), posY);
        } else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }
    }
    
    protected void actualizarAtaqueAbajoAereo() {
        float tiempoTranscurrido = DURACION_TOTAL_AEREO_ABAJO - tiempoAccion;
        boolean enDano = tiempoTranscurrido >= TIEMPO_STARTUP_AEREO_ABAJO && 
                         tiempoTranscurrido <= (TIEMPO_STARTUP_AEREO_ABAJO + TIEMPO_ACTIVO_AEREO_ABAJO);

        if (enDano) {
            atacando = true;
            hitboxAtaque.setSize(180f, 40f);
            float centroPersonajeX = posX + (ancho / 2f);
            hitboxAtaque.setPosition(centroPersonajeX - (hitboxAtaque.width / 2f), posY);
        } else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }
    }

    // --- MANEJADOR Y MÉTODOS MODULARES DE ATAQUES ESPECIALES ---
    protected void actualizarEspeciales(float delta) {
        switch (especialActual) {
            case ESP_NEUTRAL:         actualizarEspecialNeutral(delta); break;
            case ESP_LATERAL:         actualizarEspecialLateral(delta); break;
            case ESP_ABAJO:           actualizarEspecialAbajo(delta); break;
            case ESP_AEREO_NEUTRAL:   actualizarEspecialNeutralAereo(delta); break;
            case ESP_AEREO_LATERAL:   actualizarEspecialLateralAereo(delta); break;
            case ESP_AEREO_ABAJO:     actualizarEspecialAbajoAereo(delta); break;
            default:                  actualizarEspecialesDefault(delta); break;
        }
    }

    protected void actualizarEspecialNeutral(float delta) { actualizarEspecialesDefault(delta); }
    protected void actualizarEspecialLateral(float delta) { actualizarEspecialesDefault(delta); }
    protected void actualizarEspecialAbajo(float delta) { actualizarEspecialesDefault(delta); }
    protected void actualizarEspecialNeutralAereo(float delta) { actualizarEspecialesDefault(delta); }
    protected void actualizarEspecialLateralAereo(float delta) { actualizarEspecialesDefault(delta); }
    protected void actualizarEspecialAbajoAereo(float delta) { actualizarEspecialesDefault(delta); }

    private void actualizarEspecialesDefault(float delta) {
        hitboxAtaque.setSize(100f, 70f);
        atacando = true;
        float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width;
        hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 4f));
    }

    public void procesarMovimiento(boolean[] movimientos, float delta) {
        if (tiempoAccion > 0 || inermeEnAire) return; 
        velocidadX = 0;

        if (movimientos[0]) {
            velocidadX = -VELOCIDAD_MOVIMIENTO;
            mirandoDerecha = false;
        }
        if (movimientos[1]) {
            velocidadX = VELOCIDAD_MOVIMIENTO;
            mirandoDerecha = true;
        }
        if (movimientos[2] && enElSuelo) {
            velocidadY = FUERZA_SALTO;
            enElSuelo = false;
        }
    }

    public void procesarAcciones(boolean[] acciones, boolean[] direccion) {
        if (tiempoAccion > 0 || inermeEnAire) return; // <--- Bloquea acciones si está inerme en el aire o en otra acción

        boolean presionaHorizontal = direccion[0] || direccion[1];
        boolean presionaAbajo      = direccion[3];

        if (acciones[0]) { // Botón de Ataque Normal
            if (enElSuelo) {
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
            } else {
                if (presionaAbajo) {
                    ataqueAereoActual = TipoAtaqueAereo.AEREO_ABAJO;
                    estadoActual      = EstadoAnimacion.ATACAR_AEREO_ABAJO;
                    tiempoAccion      = DURACION_TOTAL_AEREO_ABAJO;
                    danioAtaque       = DANIO_AEREO_ABAJO;
                } else if (presionaHorizontal) {
                    ataqueAereoActual = TipoAtaqueAereo.AEREO_LATERAL;
                    estadoActual      = EstadoAnimacion.ATACAR_AEREO_LATERAL;
                    tiempoAccion      = DURACION_TOTAL_AEREO_LATERAL;
                    danioAtaque       = DANIO_AEREO_LATERAL;
                } else {
                    ataqueAereoActual = TipoAtaqueAereo.AEREO_NEUTRAL;
                    estadoActual      = EstadoAnimacion.ATACAR_AEREO_NEUTRAL;
                    tiempoAccion      = DURACION_TOTAL_AEREO_NEUTRAL;
                    danioAtaque       = DANIO_AEREO_NEUTRAL;
                }
            }
            
            velocidadX = 0;
            ataqueProcesado = false;
            stateTime = 0f;
        } 
        else if (acciones[1] && enElSuelo) { // Botón de Bloqueo
            estadoActual = EstadoAnimacion.BLOQUEAR;
            tiempoAccion = DURACION_BLOQUEO;
            velocidadX = 0;
            stateTime = 0f;
        } 
        else if (acciones[2] && energiaEspecial >= COSTO_BARRA_ESPECIAL) { // Botón de Ataque Especial
            energiaEspecial -= COSTO_BARRA_ESPECIAL;
            
            if (enElSuelo) {
                if (presionaAbajo) {
                    especialActual = TipoAtaqueEspecial.ESP_ABAJO;
                    estadoActual   = EstadoAnimacion.ESPECIAL_ABAJO;
                    tiempoAccion   = DURACION_TOTAL_ESP_ABAJO; // <--- Duración total sumada
                } else if (presionaHorizontal) {
                    especialActual = TipoAtaqueEspecial.ESP_LATERAL;
                    estadoActual   = EstadoAnimacion.ESPECIAL_LATERAL;
                    tiempoAccion   = DURACION_TOTAL_ESP_LATERAL; // <--- Duración total sumada
                } else {
                    especialActual = TipoAtaqueEspecial.ESP_NEUTRAL;
                    estadoActual   = EstadoAnimacion.ESPECIAL_NEUTRAL;
                    tiempoAccion   = DURACION_TOTAL_ESP_NEUTRAL;  // <--- Duración total sumada
                }
            } else {
                if (presionaAbajo) {
                    especialActual = TipoAtaqueEspecial.ESP_AEREO_ABAJO;
                    estadoActual   = EstadoAnimacion.ESPECIAL_AEREO_ABAJO;
                    tiempoAccion   = 0.50f;
                } else if (presionaHorizontal) {
                    especialActual = TipoAtaqueEspecial.ESP_AEREO_LATERAL;
                    estadoActual   = EstadoAnimacion.ESPECIAL_AEREO_LATERAL;
                    tiempoAccion   = DURACION_TOTAL_ESP_AEREO_LATERAL; // <--- Duración total sumada
                } else {
                    especialActual = TipoAtaqueEspecial.ESP_AEREO_NEUTRAL;
                    estadoActual   = EstadoAnimacion.ESPECIAL_AEREO_NEUTRAL;
                    tiempoAccion   = 0.50f;
                }
            }
            
            velocidadX = 0;
            ataqueProcesado = false;
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
                    
                    if (inermeEnAire) {
                        inermeEnAire = false;
                        tiempoAccion = 0f;
                        estadoActual = EstadoAnimacion.REPOSO;
                    }
                    
                    hitbox.setPosition(posX, posY);
                    break;
                }
            }
        }

        if (posY < 150f) {
            posY = 150f;
            velocidadY = 0;
            enElSuelo = true;
            
            if (inermeEnAire) {
                inermeEnAire = false;
                tiempoAccion = 0f;
                estadoActual = EstadoAnimacion.REPOSO;
            }
            
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
        if (inermeEnAire) {
            estadoActual = EstadoAnimacion.RECIBIR_DANIO; 
            return;
        }

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

    // --- ENERGÍA ESPECIAL Y CONSULTA DINÁMICA DE GANANCIA ---
    public float getEnergiaEspecial() { return energiaEspecial; }
    public float getEnergiaEspecialMaxima() { return energiaEspecialMaxima; }
    public void ganarEnergiaEspecial(float cantidad) {
        this.energiaEspecial = Math.min(energiaEspecialMaxima, this.energiaEspecial + cantidad);
    }

    public float getEspecialAtaqueActual() {
        if (enElSuelo) {
            switch (ataqueActual) {
                case NEUTRAL: return ESPECIAL_GANADO_NEUTRAL;
                case LATERAL: return ESPECIAL_GANADO_LATERAL;
                case ABAJO:   return ESPECIAL_GANADO_ABAJO;
                default: return 0f;
            }
        } else {
            switch (ataqueAereoActual) {
                case AEREO_NEUTRAL: return ESPECIAL_GANADO_AEREO_NEUTRAL;
                case AEREO_LATERAL: return ESPECIAL_GANADO_AEREO_LATERAL;
                case AEREO_ABAJO:   return ESPECIAL_GANADO_AEREO_ABAJO;
                default: return 0f;
            }
        }
    }
    
    // Método para recibir un ataque que eleva al rival por los aires junto al atacante
    public void recibirAtaqueAereoEspecialKiri(float danio, float fuerzaSaltoY) {
        this.salud -= danio;
        if (this.salud < 0) this.salud = 0;
        
        this.enElSuelo = false;
        this.velocidadY = fuerzaSaltoY; 
        this.velocidadX = 0;
        
        this.estadoActual = EstadoAnimacion.RECIBIR_DANIO;
        this.tiempoAccion = 0.40f;
        this.stateTime = 0f;
        this.inermeEnAire = true; 
    }

    // --- SALUD Y NORMALIZACIÓN DE UI ---
    public float getSalud() { return salud; }
    public float getSaludMaxima() { return saludMaxima; }
    
    public TipoAtaqueEspecial getEspecialActual() {
        return especialActual;
    }
    
    public float getPorcentajeSalud() {
        if (saludMaxima <= 0) return 0f;
        return Math.min(1f, Math.max(0f, salud / saludMaxima));
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
}