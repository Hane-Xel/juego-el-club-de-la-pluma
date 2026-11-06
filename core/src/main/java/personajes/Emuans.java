package personajes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import personajes.Proyectil;
import escenarios.Plataforma;

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

    private int balasRestantesCont = 0;
    private float tiempoEntreBalas = 0f;
    private static final float INTERVALO_RAFAGA = 0.12f;

    private Aves oponenteRef;

    public Emuans(float posX, float posY) {
        super("emuans", posX, posY, "characters/emuans/reposo.png");

        ESPECIAL_GANADO_NEUTRAL = 34f;

        ESPECIAL_GANADO_AEREO_NEUTRAL = 40f;
        
        this.explosionActual = new Explosion("characters/emuans/explosion.png", 2, 1, 0.06f, 140f, 120f);
        this.explosionActual.desactivar();
        
        this.proyectilActual = new Proyectil("characters/emuans/bala.png", 1920f);
        this.proyectilActual.desactivar();
        
        this.misilActual = new Proyectil("characters/emuans/misil.png", 1920f);
        this.misilActual.desactivar();
        
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

    @Override
    protected void actualizarAtaqueNeutral() {
        danioAtaque = DANIO_NEUTRAL;
        hitboxAtaque.setSize(10f, 50f); 
        
        float tiempoTranscurrido = DURACION_TOTAL_NEUTRAL - tiempoAccion;
        boolean enDano = tiempoTranscurrido >= TIEMPO_STARTUP_NEUTRAL && 
                         tiempoTranscurrido <= (TIEMPO_STARTUP_NEUTRAL + TIEMPO_ACTIVO_NEUTRAL);

        if (enDano) {
            atacando = true;
            float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width;
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 3f));
        } else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }
    }
    
    @Override
    protected void actualizarAtaqueLateral(float delta) {
        danioAtaque = DANIO_LATERAL;
        float tiempoTranscurrido = DURACION_TOTAL_LATERAL - tiempoAccion;

        if (tiempoTranscurrido < TIEMPO_STARTUP_LATERAL) {
            velocidadX = 0;              
            atacando = false;            
            hitboxAtaque.setPosition(-9999, -9999);
            proyectilDisparado = false; 
        } 
        else if (tiempoTranscurrido <= (TIEMPO_STARTUP_LATERAL + TIEMPO_ACTIVO_LATERAL)) {
            velocidadX = 0; 
            atacando = false; 
            
            if (!proyectilDisparado && proyectilActual != null) {
                float posXProyectil = mirandoDerecha ? (posX + ancho) : (posX - 40f);
                float posYProyectil = posY + (alto / 3f);
                
                proyectilActual.disparar(posXProyectil, posYProyectil, mirandoDerecha, 1800f);
                proyectilDisparado = true;
            }
        } 
        else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }
    }
    
    @Override
    protected void actualizarAtaqueAbajo() {
        danioAtaque = 16f; 
        float tiempoTranscurrido = DURACION_TOTAL_ABAJO - tiempoAccion;

        if (tiempoTranscurrido < TIEMPO_STARTUP_ABAJO) {
            velocidadX = 0;
            atacando = false;
            explosionActivada = false;
        } 
        else if (tiempoTranscurrido <= (TIEMPO_STARTUP_ABAJO + TIEMPO_ACTIVO_ABAJO)) {
            velocidadX = 0;
            atacando = false;
            
            if (!explosionActivada) {
                float posXExplosion = posX + (ancho / 2f) - (60f); 
                float posYExplosion = posY; 
                
                explosionActual.activar(posXExplosion, posYExplosion);
                
                velocidadY = 1200f; 
                enElSuelo = false;
                
                explosionActivada = true;
            }
        } 
        else {
            atacando = false;
        }
    }
    
    @Override
    protected void actualizarAtaqueNeutralAereo() {
        danioAtaque = DANIO_AEREO_NEUTRAL;
        float tiempoTranscurrido = DURACION_TOTAL_AEREO_NEUTRAL - tiempoAccion;

        if (tiempoTranscurrido < TIEMPO_STARTUP_AEREO_NEUTRAL) {
            atacando = false;
            proyectilNeutralAereoDisparado = false;
        } 
        else if (tiempoTranscurrido <= (TIEMPO_STARTUP_AEREO_NEUTRAL + TIEMPO_ACTIVO_AEREO_NEUTRAL)) {
            atacando = false; 
            
            if (!proyectilNeutralAereoDisparado && proyectilActual != null) {
                float posXProyectil = posX + (ancho / 2f) - (7.5f); 
                float posYProyectil = posY + alto;                  
                
                proyectilActual.dispararVertical(posXProyectil, posYProyectil, true, 1600f, 90f, 15f, 25f);
                proyectilNeutralAereoDisparado = true;
            }
        } 
        else {
            atacando = false;
        }
    }

    @Override
    protected void actualizarAtaqueLateralAereo(float delta) {
        danioAtaque = DANIO_AEREO_LATERAL;
        float tiempoTranscurrido = DURACION_TOTAL_AEREO_LATERAL - tiempoAccion;
        float direccion = mirandoDerecha ? 1f : -1f;

        if (tiempoTranscurrido < TIEMPO_STARTUP_AEREO_LATERAL) {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            proyectilAereoDisparado = false;
            velocidadX = direccion * 400f; 
        } 
        else if (tiempoTranscurrido <= (TIEMPO_STARTUP_AEREO_LATERAL + TIEMPO_ACTIVO_AEREO_LATERAL)) {
            atacando = false; 
            
            if (!proyectilAereoDisparado && proyectilActual != null) {
                float posXProyectil = mirandoDerecha ? (posX + ancho) : (posX - 40f);
                float posYProyectil = posY + (alto / 3f);
                
                proyectilActual.disparar(posXProyectil, posYProyectil, mirandoDerecha, 1500f);
                proyectilAereoDisparado = true;
            }

            velocidadX *= 0.90f; 
        } 
        else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            velocidadX *= 0.95f; 
        }
    }
    
    @Override
    protected void actualizarAtaqueAbajoAereo() {
        danioAtaque = 16f; 
        float tiempoTranscurrido = DURACION_TOTAL_AEREO_ABAJO - tiempoAccion;

        if (tiempoTranscurrido < TIEMPO_STARTUP_AEREO_ABAJO) {
            atacando = false;
            velocidadX = 0f;
            velocidadY = 0f; 
            hitboxAtaque.setPosition(-9999, -9999);
            granadaLanzada = false;
        } 
        else if (tiempoTranscurrido <= (TIEMPO_STARTUP_AEREO_ABAJO + TIEMPO_ACTIVO_AEREO_ABAJO)) {
            atacando = false; 
            velocidadX = 0f;
            velocidadY = 0f; 

            if (!granadaLanzada && proyectilActual != null) {
                float posXGranada = posX + (ancho / 2f) - 15f;
                float posYGranada = posY - 20f;
                
                proyectilActual.dispararVertical(posXGranada, posYGranada, false, 1400f, 0f, 30f, 30f);
                granadaLanzada = true;
            }
        } 
        else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }
    }

    @Override
    protected void actualizarEspecialNeutral(float delta) {
        danioAtaque = DANIO_ESP_NEUTRAL; 
        hitboxAtaque.setSize(15f, 60f); 

        float tiempoTranscurrido = DURACION_TOTAL_ESP_NEUTRAL - tiempoAccion;

        if (tiempoTranscurrido <= 0.05f) {
            velocidadY = FUERZA_SALTO * 0.45f; 
            velocidadX = 0;
        }

        boolean enDano = tiempoTranscurrido >= TIEMPO_STARTUP_ESP_NEUTRAL && 
                         tiempoTranscurrido <= (TIEMPO_STARTUP_ESP_NEUTRAL + TIEMPO_ACTIVO_ESP_NEUTRAL);

        if (enDano) {
            atacando = true;
            float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width;
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 3f));
        } else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }
    }

    @Override
    protected void actualizarEspecialLateral(float delta) {
        danioAtaque = DANIO_ESP_LATERAL; 
        hitboxAtaque.setSize(110f, 65f); 

        float tiempoTranscurrido = DURACION_TOTAL_ESP_LATERAL - tiempoAccion;

        if (tiempoTranscurrido < TIEMPO_STARTUP_ESP_LATERAL) {
            velocidadX = 0;
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            balasRestantesCont = 0;
        } 
        else if (tiempoTranscurrido <= (TIEMPO_STARTUP_ESP_LATERAL + TIEMPO_ACTIVO_ESP_LATERAL)) {
            velocidadX = 0; 
            atacando = true;
            
            float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width;
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 4f));

            if (balasRestantesCont == 0 && tiempoTranscurrido >= TIEMPO_STARTUP_ESP_LATERAL && tiempoTranscurrido < TIEMPO_STARTUP_ESP_LATERAL + 0.05f) {
                if (proyectilActual != null) {
                    float posXProyectil = mirandoDerecha ? (posX + ancho) : (posX - 40f);
                    float posYProyectil = posY + (alto / 3f);
                    
                    proyectilActual.disparar(posXProyectil, posYProyectil, mirandoDerecha, 2000f);
                    
                    balasRestantesCont = 2; 
                    tiempoEntreBalas = INTERVALO_RAFAGA;
                }
            }
        } 
        else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            velocidadX = 0;
        }
    }
    
    @Override
    protected void actualizarEspecialAbajo(float delta) {
        velocidadX = 0;
        danioAtaque = 18f; 

        float tiempoTranscurrido = DURACION_TOTAL_ESP_ABAJO - tiempoAccion;

        if (tiempoTranscurrido < TIEMPO_STARTUP_ESP_ABAJO) {
            velocidadX = 0;
            atacando = false;
            explosionActivada = false;
        } 
        else if (tiempoTranscurrido <= (TIEMPO_STARTUP_ESP_ABAJO + TIEMPO_ACTIVO_ESP_ABAJO)) {
            velocidadX = 0;
            atacando = false;
            
            if (!explosionActivada) {
                float posXExplosion = posX + (ancho / 2f) - (60f); 
                float posYExplosion = posY; 
                
                explosionActual.activar(posXExplosion, posYExplosion);
                
                velocidadY = 1200f; 
                enElSuelo = false;
                
                explosionActivada = true;
            }
        } 
        else {
            atacando = false;
        }
    }
    
    @Override
    protected void actualizarEspecialLateralAereo(float delta) {
        danioAtaque = DANIO_ESP_AEREO_LATERAL;
        hitboxAtaque.setSize(100f, 60f);

        float tiempoTranscurrido = DURACION_TOTAL_ESP_AEREO_LATERAL - tiempoAccion;
        float direccion = mirandoDerecha ? 1f : -1f;

        if (tiempoTranscurrido < TIEMPO_STARTUP_ESP_AEREO_LATERAL) {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            velocidadX = direccion * 700f; 
            balasRestantesCont = 0;
            
            if (tiempoAccion >= DURACION_TOTAL_ESP_AEREO_LATERAL - 0.05f) {
                velocidadY = 600f; 
            }
        } 
        else if (tiempoTranscurrido <= (TIEMPO_STARTUP_ESP_AEREO_LATERAL + TIEMPO_ACTIVO_ESP_AEREO_LATERAL)) {
            atacando = true;
            float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width;
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 3f));

            if (balasRestantesCont == 0 && tiempoTranscurrido >= TIEMPO_STARTUP_ESP_AEREO_LATERAL && tiempoTranscurrido < TIEMPO_STARTUP_ESP_AEREO_LATERAL + 0.05f) {
                if (proyectilActual != null) {
                    float posXProyectil = mirandoDerecha ? (posX + ancho) : (posX - 40f);
                    float posYProyectil = posY + (alto / 3f);
                    
                    proyectilActual.disparar(posXProyectil, posYProyectil, mirandoDerecha, 2000f);
                    
                    balasRestantesCont = 2; 
                    tiempoEntreBalas = INTERVALO_RAFAGA;
                }
            }

            velocidadX *= 0.88f; 
            velocidadY *= 0.90f;
        } 
        else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            velocidadX *= 0.92f; 
        }
    }
    
    @Override
    protected void actualizarEspecialNeutralAereo(float delta) {
        danioAtaque = DANIO_ESP_AEREO_NEUTRAL; 
        hitboxAtaque.setSize(15f, 60f); 

        float tiempoTranscurrido = DURACION_TOTAL_ESP_AEREO_NEUTRAL - tiempoAccion;

        if (tiempoTranscurrido <= 0.05f) {
            velocidadY = FUERZA_SALTO * 0.30f; 
            velocidadX = 0;
        }

        boolean enDano = tiempoTranscurrido >= TIEMPO_STARTUP_ESP_AEREO_NEUTRAL && 
                         tiempoTranscurrido <= (TIEMPO_STARTUP_ESP_AEREO_NEUTRAL + TIEMPO_ACTIVO_ESP_AEREO_NEUTRAL);

        if (enDano) {
            atacando = true;
            float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width;
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 3f));

            if (!proyectilNeutralAereoDisparado && proyectilActual != null) {
                float posXProyectil = posX + (ancho / 2f) - (7.5f); 
                float posYProyectil = posY + alto;                  
                
                proyectilActual.dispararEspecialNeutralAereo(posXProyectil, posYProyectil, 1600f, oponenteRef, 1920f);
                proyectilNeutralAereoDisparado = true;
            }
        } else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            if (tiempoTranscurrido < TIEMPO_STARTUP_ESP_AEREO_NEUTRAL) {
                proyectilNeutralAereoDisparado = false;
            }
        }
    }
    
    @Override
    protected void actualizarEspecialAbajoAereo(float delta) {
        danioAtaque = DANIO_ESP_AEREO_ABAJO;
        float tiempoTranscurrido = DURACION_TOTAL_ESP_AEREO_ABAJO - tiempoAccion;

        if (tiempoTranscurrido < TIEMPO_STARTUP_ESP_AEREO_ABAJO) {
            atacando = false;
            velocidadX = 0f;
            velocidadY = 0f; 
            hitboxAtaque.setPosition(-9999, -9999);
            misilLanzado = false;
        } 
        else if (tiempoTranscurrido <= (TIEMPO_STARTUP_ESP_AEREO_ABAJO + TIEMPO_ACTIVO_ESP_AEREO_ABAJO)) {
            atacando = false; 
            velocidadX = 0f;
            velocidadY = 0f; 

            if (!misilLanzado && misilActual != null) {
                float posXMisil = posX + (ancho / 2f) - 15f;
                float posYMisil = posY - 20f;
                
                misilActual.dispararVertical(posXMisil, posYMisil, false, 2000f, 270f, 30f, 35f);
                misilLanzado = true;
            }
        } 
        else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }
    }
    
    public Proyectil getProyectilActual() {
        return proyectilActual;
    }
    
    public Proyectil getMisilActual() {
        return misilActual;
    }
    
    public void dispose() {
        if (proyectilActual != null) {
            proyectilActual.dispose();
        }
        if (misilActual != null) {
            misilActual.dispose();
        }
        if (explosionActual != null) {
            explosionActual.dispose();
        }
    }
}