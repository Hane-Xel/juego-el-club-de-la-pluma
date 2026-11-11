package personajes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import personajes.Proyectil;
import escenarios.Plataforma;

/**
 * Clase que representa al personaje Emuans. Gestiona sus ataques, proyectiles, 
 * misiles, explosiones y la lógica de su doble salto ofensivo.
 */
public class Emuans extends Aves {

    private Proyectil proyectilActual;
    private boolean proyectilDisparado = false;
    private boolean proyectilAereoDisparado = false;
    private boolean proyectilNeutralAereoDisparado = false;
    
    private Proyectil misilActual;
    private boolean misilLanzado = false;
    private float ultimoXMisil = 0f;
    private float ultimoYMisil = 0f;
    
    private Explosion explosionActual;
    private boolean explosionActivada = false;
    
    private boolean granadaLanzada = false;
    private float ultimoXProyectil = 0f;
    private float ultimoYProyectil = 0f;

    // --- VARIABLES PARA EL DOBLE SALTO OFENSIVO ---
    private boolean puedeDobleSalto = true;
    private boolean saltoPresionadoAnterior = false;

    private int balasRestantesCont = 0;
    private float tiempoEntreBalas = 0f;
    private static final float INTERVALO_RAFAGA = 0.12f;

    private Aves oponenteRef;

    public Emuans(float posX, float posY) {
        super("emuans", posX, posY, "characters/emuans/reposo.png");

        ESPECIAL_GANADO_NEUTRAL = 34f;
        ESPECIAL_GANADO_AEREO_NEUTRAL = 40f;
        
        // Inicialización de la explosión (se reutiliza para el doble salto y ataques abajo)
        this.explosionActual = new Explosion("characters/emuans/explosion.png", 2, 1, 0.06f, 140f, 120f);
        this.explosionActual.desactivar();
        
        this.proyectilActual = new Proyectil("characters/emuans/bala.png", 1920f);
        this.proyectilActual.desactivar();
        
        this.misilActual = new Proyectil("characters/emuans/misil.png", 1920f);
        this.misilActual.desactivar();
        
        // Configuración de tiempos y daños...
        TIEMPO_STARTUP_NEUTRAL  = 0.03f * 2;
        TIEMPO_ACTIVO_NEUTRAL   = 0.03f * 4;
        TIEMPO_RECOVERY_NEUTRAL = 0.03f * 4;
        DURACION_TOTAL_NEUTRAL  = TIEMPO_STARTUP_NEUTRAL + TIEMPO_ACTIVO_NEUTRAL + TIEMPO_RECOVERY_NEUTRAL;
        DANIO_NEUTRAL           = 15f; 
        
        TIEMPO_STARTUP_LATERAL  = 0.05f * 8; 
        TIEMPO_ACTIVO_LATERAL   = 0.05f * 1; 
        TIEMPO_RECOVERY_LATERAL = 0.05f * 4;
        DURACION_TOTAL_LATERAL  = TIEMPO_STARTUP_LATERAL + TIEMPO_ACTIVO_LATERAL + TIEMPO_RECOVERY_LATERAL; 
        DANIO_LATERAL           = 8f;
        
        TIEMPO_STARTUP_ABAJO  = 0.08f * 3;
        TIEMPO_ACTIVO_ABAJO   = 0.08f * 1;
        TIEMPO_RECOVERY_ABAJO = 0.08f * 3;
        DURACION_TOTAL_ABAJO  = TIEMPO_STARTUP_ABAJO + TIEMPO_ACTIVO_ABAJO + TIEMPO_RECOVERY_ABAJO;
        DANIO_ABAJO           = 10f;
        
        TIEMPO_STARTUP_AEREO_NEUTRAL  = 0.05f * 8;
        TIEMPO_ACTIVO_AEREO_NEUTRAL   = 0.05f * 1;
        TIEMPO_RECOVERY_AEREO_NEUTRAL = 0.05f * 2; 
        DURACION_TOTAL_AEREO_NEUTRAL  = TIEMPO_STARTUP_AEREO_NEUTRAL + TIEMPO_ACTIVO_AEREO_NEUTRAL + TIEMPO_RECOVERY_AEREO_NEUTRAL;
        DANIO_AEREO_NEUTRAL           = 12f;
        
        TIEMPO_STARTUP_AEREO_LATERAL  = 0.03f * 7;
        TIEMPO_ACTIVO_AEREO_LATERAL   = 0.03f * 1;
        TIEMPO_RECOVERY_AEREO_LATERAL = 0.03f * 6; 
        DURACION_TOTAL_AEREO_LATERAL  = TIEMPO_STARTUP_AEREO_LATERAL + TIEMPO_ACTIVO_AEREO_LATERAL + TIEMPO_RECOVERY_AEREO_LATERAL;
        DANIO_AEREO_LATERAL           = 10f;
        
        TIEMPO_STARTUP_AEREO_ABAJO  = 0.05f * 9;
        TIEMPO_ACTIVO_AEREO_ABAJO   = 0.05f * 1;
        TIEMPO_RECOVERY_AEREO_ABAJO = 0.05f * 5; 
        DURACION_TOTAL_AEREO_ABAJO  = TIEMPO_STARTUP_AEREO_ABAJO + TIEMPO_ACTIVO_AEREO_ABAJO + TIEMPO_RECOVERY_AEREO_ABAJO;
        DANIO_AEREO_ABAJO          = 12f;

        TIEMPO_STARTUP_ESP_NEUTRAL  = 0.03f * 2;
        TIEMPO_ACTIVO_ESP_NEUTRAL   = 0.03f * 4;
        TIEMPO_RECOVERY_ESP_NEUTRAL = 0.03f * 4;
        DURACION_TOTAL_ESP_NEUTRAL  = TIEMPO_STARTUP_ESP_NEUTRAL + TIEMPO_ACTIVO_ESP_NEUTRAL + TIEMPO_RECOVERY_ESP_NEUTRAL;
        DANIO_ESP_NEUTRAL           = 15f;

        TIEMPO_STARTUP_ESP_LATERAL  = 0.05f * 9;
        TIEMPO_ACTIVO_ESP_LATERAL   = 0.05f * 2;
        TIEMPO_RECOVERY_ESP_LATERAL = 0.05f * 2;
        DURACION_TOTAL_ESP_LATERAL  = TIEMPO_STARTUP_ESP_LATERAL + TIEMPO_ACTIVO_ESP_LATERAL + TIEMPO_RECOVERY_ESP_LATERAL;
        DANIO_ESP_LATERAL           = 8f;
        
        TIEMPO_STARTUP_ESP_ABAJO  = 0.05f * 9;
        TIEMPO_ACTIVO_ESP_ABAJO   = 0.05f * 1;
        TIEMPO_RECOVERY_ESP_ABAJO = 0.05f * 5;   
        DURACION_TOTAL_ESP_ABAJO  = TIEMPO_STARTUP_ESP_ABAJO + TIEMPO_ACTIVO_ESP_ABAJO + TIEMPO_RECOVERY_ESP_ABAJO;
        DANIO_ESP_ABAJO           = 10f;    
        
        TIEMPO_STARTUP_ESP_AEREO_LATERAL  = 0.03f * 7; 
        TIEMPO_ACTIVO_ESP_AEREO_LATERAL   = 0.03f * 2;  
        TIEMPO_RECOVERY_ESP_AEREO_LATERAL = 0.03f * 5; 
        DURACION_TOTAL_ESP_AEREO_LATERAL  = TIEMPO_STARTUP_ESP_AEREO_LATERAL + TIEMPO_ACTIVO_ESP_AEREO_LATERAL + TIEMPO_RECOVERY_ESP_AEREO_LATERAL;
        DANIO_ESP_AEREO_LATERAL           = 8f;
        
        TIEMPO_STARTUP_ESP_AEREO_NEUTRAL  = 0.08f;
        TIEMPO_ACTIVO_ESP_AEREO_NEUTRAL   = 0.15f;
        TIEMPO_RECOVERY_ESP_AEREO_NEUTRAL = 0.15f;
        DURACION_TOTAL_ESP_AEREO_NEUTRAL  = TIEMPO_STARTUP_ESP_AEREO_NEUTRAL + TIEMPO_ACTIVO_ESP_AEREO_NEUTRAL + TIEMPO_RECOVERY_ESP_AEREO_NEUTRAL;
        DANIO_ESP_AEREO_NEUTRAL           = 15f;
        
        TIEMPO_STARTUP_ESP_AEREO_ABAJO  = 0.03f * 7;
        TIEMPO_ACTIVO_ESP_AEREO_ABAJO   = 0.03f * 2;
        TIEMPO_RECOVERY_ESP_AEREO_ABAJO = 0.03f * 5; 
        DURACION_TOTAL_ESP_AEREO_ABAJO  = TIEMPO_STARTUP_ESP_AEREO_ABAJO + TIEMPO_ACTIVO_ESP_AEREO_ABAJO + TIEMPO_RECOVERY_ESP_AEREO_ABAJO;
        DANIO_ESP_AEREO_ABAJO           = 19f;
        
        this.salud = 300f;
        this.saludMaxima = 300f;
        this.ancho = 160f;
        this.alto = 160f;
    }
    
    public void setOponente(Aves oponente) {
        this.oponenteRef = oponente;
    }

    /**
     * FUNCIÓN: procesarMovimiento()
     * QUÉ HACE: Controla las físicas y entradas de movimiento de Emuans.
     * CÓMO LO HACE: Si Emuans está en el aire (!enElSuelo) y se presiona el botón de salto por segunda vez, 
     * ejecuta el doble salto, aplica velocidad vertical y activa una explosión justo debajo de su posición.
     */
    @Override
    public void procesarMovimiento(boolean[] mov, float delta) {
        boolean saltoPresionadoActual = mov[2]; // Índice 2 = Acción de saltar/arriba

        if (enElSuelo) {
            // Cuando toca el suelo, recargamos el doble salto
            puedeDobleSalto = true;
        } else if (saltoPresionadoActual && !saltoPresionadoAnterior && puedeDobleSalto) {
            // --- EJECUCIÓN DEL DOBLE SALTO CON EXPLOSIÓN ---
            velocidadY = 850f;          // Impulso vertical hacia arriba
            puedeDobleSalto = false;     // Consumimos el doble salto

            // Activamos la explosión debajo de Emuans
            if (explosionActual != null && !explosionActual.isActivo()) {
                float posXExplosion = posX + (ancho / 2f) - (70f);
                float posYExplosion = posY - 30f; // Posición bajo los pies
                explosionActual.activar(posXExplosion, posYExplosion);
            }
        }

        saltoPresionadoAnterior = saltoPresionadoActual;
        super.procesarMovimiento(mov, delta);
    }
    
    @Override
    public void actualizar(float delta, Array<Plataforma> plataformas, float anchoPantalla) {
        super.actualizar(delta, plataformas, anchoPantalla);
        
        if (balasRestantesCont > 0) {
            tiempoEntreBalas -= delta;
            if (tiempoEntreBalas <= 0f && proyectilActual != null) {
                float posXProyectil = mirandoDerecha ? (posX + ancho) : (posX - 40f);
                float posYProyectil = posY + (alto / 3f);
                proyectilActual.disparar(posXProyectil, posYProyectil, mirandoDerecha, 2000f);
                balasRestantesCont--;
                tiempoEntreBalas = INTERVALO_RAFAGA;
            }
        }
        
        if (proyectilActual != null) {
            if (proyectilActual.isActivo()) {
                ultimoXProyectil = proyectilActual.getHitbox().x;
                ultimoYProyectil = proyectilActual.getHitbox().y;
            }
            proyectilActual.actualizar(delta, plataformas);
            if (granadaLanzada && !proyectilActual.isActivo()) {
                if (explosionActual != null && !explosionActual.isActivo()) {
                    explosionActual.activar(ultimoXProyectil, ultimoYProyectil);
                }
                granadaLanzada = false;
            }
        }

        if (misilActual != null) {
            if (misilActual.isActivo()) {
                ultimoXMisil = misilActual.getHitbox().x;
                ultimoYMisil = misilActual.getHitbox().y;
            }
            misilActual.actualizar(delta, plataformas);
            if (misilLanzado && !misilActual.isActivo()) {
                if (explosionActual != null && !explosionActual.isActivo()) {
                    explosionActual.activar(ultimoXMisil, ultimoYMisil);
                }
                misilLanzado = false;
            }
        }
        
        if (explosionActual != null) {
            explosionActual.actualizar(delta);
        }
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        super.dibujar(batch);
        
        if (explosionActual != null && explosionActual.isActivo()) {
            explosionActual.dibujar(batch);
        }
        if (proyectilActual != null && proyectilActual.isActivo()) {
            proyectilActual.dibujar(batch);
        }
        if (misilActual != null && misilActual.isActivo()) {
            misilActual.dibujar(batch);
        }
    }

    public Explosion getExplosionActual() {
        return explosionActual;
    }

    // Métodos de ataque heredados/sobrescritos requeridos por la clase Aves...
    @Override protected void actualizarAtaqueNeutral() {}
    @Override protected void actualizarAtaqueLateral(float delta) {}
    @Override protected void actualizarAtaqueAbajo() {}
    @Override protected void actualizarAtaqueNeutralAereo() {}
    @Override protected void actualizarAtaqueLateralAereo(float delta) {}
    @Override protected void actualizarAtaqueAbajoAereo() {}
    @Override protected void actualizarEspecialNeutral(float delta) {}
    @Override protected void actualizarEspecialLateral(float delta) {}
    @Override protected void actualizarEspecialAbajo(float delta) {}
    @Override protected void actualizarEspecialLateralAereo(float delta) {}
    @Override protected void actualizarEspecialNeutralAereo(float delta) {}
    @Override protected void actualizarEspecialAbajoAereo(float delta) {}

    public Proyectil getProyectilActual() { return proyectilActual; }
    public Proyectil getMisilActual() { return misilActual; }
    
    public void dispose() {
        if (proyectilActual != null) proyectilActual.dispose();
        if (misilActual != null) misilActual.dispose();
        if (explosionActual != null) explosionActual.dispose();
    }
}