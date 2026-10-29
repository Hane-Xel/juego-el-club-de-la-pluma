package personajes;

public class Kiri extends Aves {

    public Kiri(float posX, float posY) {
        super("kiri", posX, posY, "characters/kiri/reposo.png");
        
        this.salud = 900f;
        this.saludMaxima = 900f;
        this.ancho = 110f;
        this.alto = 110f;
        
        // --- ESTADÍSTICAS DE ATAQUES NORMALES ---
        TIEMPO_STARTUP_NEUTRAL  = 0.03f * 2;
        TIEMPO_ACTIVO_NEUTRAL   = 0.03f * 4;
        TIEMPO_RECOVERY_NEUTRAL = 0.03f * 4;
        DURACION_TOTAL_NEUTRAL  = TIEMPO_STARTUP_NEUTRAL + TIEMPO_ACTIVO_NEUTRAL + TIEMPO_RECOVERY_NEUTRAL;
        DANIO_NEUTRAL           = 8f; 
        
        TIEMPO_STARTUP_LATERAL  = 0.10f * 3; 
        TIEMPO_ACTIVO_LATERAL   = 0.10f * 10; 
        TIEMPO_RECOVERY_LATERAL = 0.16f * 4;
        DURACION_TOTAL_LATERAL  = TIEMPO_STARTUP_LATERAL + TIEMPO_ACTIVO_LATERAL + TIEMPO_RECOVERY_LATERAL; 
        DANIO_LATERAL           = 12f;
        
        TIEMPO_STARTUP_ABAJO  = 0.04f * 16;
        TIEMPO_ACTIVO_ABAJO   = 0.04f * 3;
        TIEMPO_RECOVERY_ABAJO = 0.04f * 1;
        DURACION_TOTAL_ABAJO  = TIEMPO_STARTUP_ABAJO + TIEMPO_ACTIVO_ABAJO + TIEMPO_RECOVERY_ABAJO;
        ALCANCE_DOBLE_ABAJO   = 200f;
        DANIO_ABAJO           = 7f;
        
        TIEMPO_STARTUP_AEREO_NEUTRAL  = 0.05f * 3;
        TIEMPO_ACTIVO_AEREO_NEUTRAL   = 0.05f * 2;
        TIEMPO_RECOVERY_AEREO_NEUTRAL = 0.05f * 3; 
        DURACION_TOTAL_AEREO_NEUTRAL  = TIEMPO_STARTUP_AEREO_NEUTRAL + TIEMPO_ACTIVO_AEREO_NEUTRAL + TIEMPO_RECOVERY_AEREO_NEUTRAL;
        DANIO_AEREO_NEUTRAL           = 6f;
        
        TIEMPO_STARTUP_AEREO_LATERAL  = 0.03f * 14;
        TIEMPO_ACTIVO_AEREO_LATERAL   = 0.03f * 2;
        TIEMPO_RECOVERY_AEREO_LATERAL = 0.03f * 2; 
        DURACION_TOTAL_AEREO_LATERAL  = TIEMPO_STARTUP_AEREO_LATERAL + TIEMPO_ACTIVO_AEREO_LATERAL + TIEMPO_RECOVERY_AEREO_LATERAL;
        DANIO_AEREO_LATERAL           = 6f;
        
        TIEMPO_STARTUP_AEREO_ABAJO  = 0.05f * 4;
        TIEMPO_ACTIVO_AEREO_ABAJO   = 0.05f * 12;
        TIEMPO_RECOVERY_AEREO_ABAJO = 0.05f * 4; 
        DURACION_TOTAL_AEREO_ABAJO  = TIEMPO_STARTUP_AEREO_ABAJO + TIEMPO_ACTIVO_AEREO_ABAJO + TIEMPO_RECOVERY_AEREO_ABAJO;
        DANIO_AEREO_LATERAL           = 6f;

        // --- ESTADÍSTICAS DE ATAQUES ESPECIALES ---
     // --- TIEMPOS Y PROPIEDADES DE LOS ESPECIALES ---
        TIEMPO_STARTUP_ESP_NEUTRAL  = 0.03f * 2;
        TIEMPO_ACTIVO_ESP_NEUTRAL   = 0.03f * 4;
        TIEMPO_RECOVERY_ESP_NEUTRAL = 0.03f * 4;
        DURACION_TOTAL_ESP_NEUTRAL  = TIEMPO_STARTUP_ESP_NEUTRAL + TIEMPO_ACTIVO_ESP_NEUTRAL + TIEMPO_RECOVERY_ESP_NEUTRAL;
        DANIO_ESP_NEUTRAL           = 18f;

        TIEMPO_STARTUP_ESP_LATERAL  = 0.10f * 3;
        TIEMPO_ACTIVO_ESP_LATERAL   = 0.10f * 4;
        TIEMPO_RECOVERY_ESP_LATERAL = 0.10f * 0;
        DURACION_TOTAL_ESP_LATERAL  = TIEMPO_STARTUP_ESP_LATERAL + TIEMPO_ACTIVO_ESP_LATERAL + TIEMPO_RECOVERY_ESP_LATERAL;
        DANIO_ESP_LATERAL           = 18f;
        
     // --- ESTADÍSTICAS DEL ESPECIAL ABAJO ---
        TIEMPO_STARTUP_ESP_ABAJO  = 0.05f * 11;
        TIEMPO_ACTIVO_ESP_ABAJO   = 0.05f * 4;
        TIEMPO_RECOVERY_ESP_ABAJO = 0.05f * 3;   
        DURACION_TOTAL_ESP_ABAJO  = TIEMPO_STARTUP_ESP_ABAJO + TIEMPO_ACTIVO_ESP_ABAJO + TIEMPO_RECOVERY_ESP_ABAJO;
        DANIO_ESP_ABAJO           = 18f;    
        
     // --- ESTADÍSTICAS DEL ESPECIAL AÉREO LATERAL ---
        TIEMPO_STARTUP_ESP_AEREO_LATERAL  = 0.03f * 4; 
        TIEMPO_ACTIVO_ESP_AEREO_LATERAL   = 0.03f * 9;  
        TIEMPO_RECOVERY_ESP_AEREO_LATERAL = 0.03f * 3; 
        DURACION_TOTAL_ESP_AEREO_LATERAL  = TIEMPO_STARTUP_ESP_AEREO_LATERAL + TIEMPO_ACTIVO_ESP_AEREO_LATERAL + TIEMPO_RECOVERY_ESP_AEREO_LATERAL;
        DANIO_ESP_AEREO_LATERAL           = 18f;
        
     // --- ESTADÍSTICAS DEL ESPECIAL AÉREO NEUTRAL ---
        TIEMPO_STARTUP_ESP_AEREO_NEUTRAL  = 0.08f;
        TIEMPO_ACTIVO_ESP_AEREO_NEUTRAL   = 0.15f;
        TIEMPO_RECOVERY_ESP_AEREO_NEUTRAL = 0.15f;
        DURACION_TOTAL_ESP_AEREO_NEUTRAL  = TIEMPO_STARTUP_ESP_AEREO_NEUTRAL + TIEMPO_ACTIVO_ESP_AEREO_NEUTRAL + TIEMPO_RECOVERY_ESP_AEREO_NEUTRAL;
        DANIO_ESP_AEREO_NEUTRAL           = 18f;
        
     // --- ESTADÍSTICAS DEL ESPECIAL AÉREO ABAJO ---
        TIEMPO_STARTUP_ESP_AEREO_ABAJO  = 0.03f * 6;
        TIEMPO_ACTIVO_ESP_AEREO_ABAJO   = 0.03f * 9;
        TIEMPO_RECOVERY_ESP_AEREO_ABAJO = 0.03f * 3; 
        DURACION_TOTAL_ESP_AEREO_ABAJO  = TIEMPO_STARTUP_ESP_AEREO_ABAJO + TIEMPO_ACTIVO_ESP_AEREO_ABAJO + TIEMPO_RECOVERY_ESP_AEREO_ABAJO;
        DANIO_ESP_AEREO_ABAJO           = 18f; // Daño potenciado por ser especial
    }
    
    // --- ATAQUES NORMALES ---
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
        hitboxAtaque.setSize(90f, 60f);
        
        float tiempoTranscurrido = DURACION_TOTAL_LATERAL - tiempoAccion;
        float direccion = mirandoDerecha ? 1f : -1f;

        if (tiempoTranscurrido < TIEMPO_STARTUP_LATERAL) {
            velocidadX = 0;              
            atacando = false;            
            hitboxAtaque.setPosition(-9999, -9999);
        } 
        else if (tiempoTranscurrido <= (TIEMPO_STARTUP_LATERAL + TIEMPO_ACTIVO_LATERAL)) {
            float velocidadDisparo = 750f; 
            velocidadX = direccion * velocidadDisparo; 
            
            atacando = true;             
            float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width;
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 4f));
        } 
        else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            
            if (velocidadX != 0) {
                float desaceleracion = VELOCIDAD_CARGA_LATERAL * 5f * delta;
                velocidadX = mirandoDerecha ? Math.max(0, velocidadX - desaceleracion) : Math.min(0, velocidadX + desaceleracion);
            }
        }
    }
    
    @Override
    protected void actualizarAtaqueAbajo() {
        danioAtaque = 14f; 
        float anchoHitboxAbajo = 200f; 
        float altoHitboxAbajo = 40f;   
        hitboxAtaque.setSize(anchoHitboxAbajo, altoHitboxAbajo);

        float tiempoTranscurrido = DURACION_TOTAL_ABAJO - tiempoAccion;
        boolean enDano = tiempoTranscurrido >= TIEMPO_STARTUP_ABAJO && 
                         tiempoTranscurrido <= (TIEMPO_STARTUP_ABAJO + TIEMPO_ACTIVO_ABAJO);

        if (enDano) {
            atacando = true;
            float centroPersonajeX = posX + (ancho / 2f);
            hitboxAtaque.setPosition(centroPersonajeX - (anchoHitboxAbajo / 2f), posY);
        } else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }
    }
    
    @Override
    protected void actualizarAtaqueNeutralAereo() {
        danioAtaque = DANIO_AEREO_NEUTRAL;
        hitboxAtaque.setSize(75f, 75f); 
        
        float tiempoTranscurrido = DURACION_TOTAL_AEREO_NEUTRAL - tiempoAccion;
        boolean enDano = tiempoTranscurrido >= TIEMPO_STARTUP_AEREO_NEUTRAL && 
                         tiempoTranscurrido <= (TIEMPO_STARTUP_AEREO_NEUTRAL + TIEMPO_ACTIVO_AEREO_NEUTRAL);

        if (enDano) {
            atacando = true;
            float offsetAtaqueX = mirandoDerecha ? (ancho * 0.5f) : (-hitboxAtaque.width + (ancho * 0.5f));
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 3f));
        } else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }
    }

    @Override
    protected void actualizarAtaqueLateralAereo(float delta) {
        danioAtaque = DANIO_AEREO_LATERAL;
        hitboxAtaque.setSize(95f, 55f);

        float tiempoTranscurrido = DURACION_TOTAL_AEREO_LATERAL - tiempoAccion;
        float direccion = mirandoDerecha ? 1f : -1f;

        if (tiempoTranscurrido < TIEMPO_STARTUP_AEREO_LATERAL) {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            velocidadX = direccion * 650f; 
            
            if (tiempoAccion >= DURACION_TOTAL_AEREO_LATERAL - 0.05f) {
                velocidadY = 550f; 
            }
        } 
        else if (tiempoTranscurrido <= (TIEMPO_STARTUP_AEREO_LATERAL + TIEMPO_ACTIVO_AEREO_LATERAL)) {
            atacando = true;
            float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width;
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 3f));

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
    protected void actualizarAtaqueAbajoAereo() {
        danioAtaque = DANIO_AEREO_ABAJO;
        float tiempoTranscurrido = DURACION_TOTAL_AEREO_ABAJO - tiempoAccion;

        if (tiempoTranscurrido >= TIEMPO_STARTUP_AEREO_ABAJO && 
            tiempoTranscurrido <= (TIEMPO_STARTUP_AEREO_ABAJO + TIEMPO_ACTIVO_AEREO_ABAJO) && 
            velocidadY == 0f) {
            
            tiempoAccion = TIEMPO_RECOVERY_AEREO_ABAJO;
            stateTime = DURACION_TOTAL_AEREO_ABAJO - TIEMPO_RECOVERY_AEREO_ABAJO;
        }

        tiempoTranscurrido = DURACION_TOTAL_AEREO_ABAJO - tiempoAccion;

        if (tiempoTranscurrido < TIEMPO_STARTUP_AEREO_ABAJO) {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            velocidadX = 0;
            velocidadY = 50f; 
        } 
        else if (tiempoTranscurrido <= (TIEMPO_STARTUP_AEREO_ABAJO + TIEMPO_ACTIVO_AEREO_ABAJO)) {
            atacando = true;
            
            if (velocidadY != 0f) {
                velocidadY = -900f; 
            }
            
            velocidadX = 0; 
            float anchoHitboxPicada = 70f;
            float altoHitboxPicada = 90f;
            hitboxAtaque.setSize(anchoHitboxPicada, altoHitboxPicada);

            float centroX = posX + (ancho / 2f) - (anchoHitboxPicada / 2f);
            hitboxAtaque.setPosition(centroX, posY);
        } 
        else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            velocidadY = 0;
            velocidadX = 0;
        }
    }
    
    // --- ATAQUES ESPECIALES MODULARES ---

@Override
    protected void actualizarEspecialNeutral(float delta) {
        danioAtaque = DANIO_ESP_NEUTRAL; 
        hitboxAtaque.setSize(15f, 60f); 

        float tiempoTranscurrido = DURACION_TOTAL_ESP_NEUTRAL - tiempoAccion;

        if (tiempoTranscurrido <= 0.05f) {
            velocidadY = FUERZA_SALTO * 0.45f; 
            velocidadX = 0;
        }

        // Usamos el rango exacto entre Startup y Activo
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
        float direccion = mirandoDerecha ? 1f : -1f;

        // --- FASE 1: STARTUP ---
        if (tiempoTranscurrido < TIEMPO_STARTUP_ESP_LATERAL) {
            velocidadX = 0;
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        } 
        // --- FASE 2: ACTIVA ---
        else if (tiempoTranscurrido <= (TIEMPO_STARTUP_ESP_LATERAL + TIEMPO_ACTIVO_ESP_LATERAL)) {
            float velocidadDisparoEspecial = 1100f; 
            velocidadX = direccion * velocidadDisparoEspecial; 
            
            atacando = true;
            float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width;
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 4f));
        } 
        // --- FASE 3: RECOVERY ---
        else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            
            if (velocidadX != 0) {
                float desaceleracion = 1200f * delta;
                velocidadX = mirandoDerecha ? Math.max(0, velocidadX - desaceleracion) : Math.min(0, velocidadX + desaceleracion);
            }
        }
    }
    

    @Override
    protected void actualizarEspecialAbajo(float delta) {
        velocidadX = 0;
        danioAtaque = 18f; // Daño específico de este especial

        // --- DEFINIMOS EL ALCANCE DIRECTAMENTE DENTRO DE LA FUNCIÓN ---
        float alcanceEspAbajo = 500f; // Rango masivo de la onda expansiva
        float altoHitboxEspAbajo = 45f;   
        
        hitboxAtaque.setSize(alcanceEspAbajo, altoHitboxEspAbajo);

        float tiempoTranscurrido = DURACION_TOTAL_ESP_ABAJO - tiempoAccion;
        boolean enDano = tiempoTranscurrido >= TIEMPO_STARTUP_ESP_ABAJO && 
                         tiempoTranscurrido <= (TIEMPO_STARTUP_ESP_ABAJO + TIEMPO_ACTIVO_ESP_ABAJO);

        if (enDano) {
            atacando = true;
            
            // Centramos la hitbox usando el alcance local
            float centroPersonajeX = posX + (ancho / 2f);
            hitboxAtaque.setPosition(centroPersonajeX - (alcanceEspAbajo / 2f), posY);
            
        } else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }
    }
    
    @Override
    protected void actualizarEspecialLateralAereo(float delta) {
        danioAtaque = DANIO_ESP_AEREO_LATERAL;
        hitboxAtaque.setSize(100f, 60f); // Hitbox ligeramente mayor por ser especial

        float tiempoTranscurrido = DURACION_TOTAL_ESP_AEREO_LATERAL - tiempoAccion;
        float direccion = mirandoDerecha ? 1f : -1f;

        // --- FASE 1: STARTUP (Preparación e impulso inicial) ---
        if (tiempoTranscurrido < TIEMPO_STARTUP_ESP_AEREO_LATERAL) {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            velocidadX = direccion * 700f; // Impulso aéreo especial más potente
            
            if (tiempoAccion >= DURACION_TOTAL_ESP_AEREO_LATERAL - 0.05f) {
                velocidadY = 600f; // Pequeño salto aéreo de impulso
            }
        } 
        // --- FASE 2: ACTIVA (Golpe activo) ---
        else if (tiempoTranscurrido <= (TIEMPO_STARTUP_ESP_AEREO_LATERAL + TIEMPO_ACTIVO_ESP_AEREO_LATERAL)) {
            atacando = true;
            float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width;
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 3f));

            velocidadX *= 0.88f; 
            velocidadY *= 0.90f;
        } 
        // --- FASE 3: RECOVERY (Frenado y caída) ---
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

        // Impulso vertical menor para Kiri en el aire
        if (tiempoTranscurrido <= 0.05f) {
            velocidadY = FUERZA_SALTO * 0.30f; // Menor altura que el terrestre (0.45f)
            velocidadX = 0;
        }

        boolean enDano = tiempoTranscurrido >= TIEMPO_STARTUP_ESP_AEREO_NEUTRAL && 
                         tiempoTranscurrido <= (TIEMPO_STARTUP_ESP_AEREO_NEUTRAL + TIEMPO_ACTIVO_ESP_AEREO_NEUTRAL);

        if (enDano) {
            atacando = true;
            float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width;
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 3f));
        } else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }
    }
    
    
    protected void actualizarEspecialAbajoAereo() {
        danioAtaque = DANIO_ESP_AEREO_ABAJO;
        float tiempoTranscurrido = DURACION_TOTAL_ESP_AEREO_ABAJO - tiempoAccion;

        // Si toca el suelo mientras está activo, cancela la picada y pasa al recovery
        if (tiempoTranscurrido >= TIEMPO_STARTUP_ESP_AEREO_ABAJO && 
            tiempoTranscurrido <= (TIEMPO_STARTUP_ESP_AEREO_ABAJO + TIEMPO_ACTIVO_ESP_AEREO_ABAJO) && 
            velocidadY == 0f) {
            
            tiempoAccion = TIEMPO_RECOVERY_ESP_AEREO_ABAJO;
            stateTime = DURACION_TOTAL_ESP_AEREO_ABAJO - TIEMPO_RECOVERY_ESP_AEREO_ABAJO;
        }

        tiempoTranscurrido = DURACION_TOTAL_ESP_AEREO_ABAJO - tiempoAccion;

        // --- FASE 1: STARTUP ---
        if (tiempoTranscurrido < TIEMPO_STARTUP_ESP_AEREO_ABAJO) {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            velocidadX = 0;
            velocidadY = 50f; // Pequeño respiro inicial antes de caer
        } 
        // --- FASE 2: ACTIVA (Picada rápida hacia abajo) ---
        else if (tiempoTranscurrido <= (TIEMPO_STARTUP_ESP_AEREO_ABAJO + TIEMPO_ACTIVO_ESP_AEREO_ABAJO)) {
            atacando = true;
            
            if (velocidadY != 0f) {
                velocidadY = -1100f; // Picada especial más rápida y potente
            }
            
            velocidadX = 0; 
            float anchoHitboxPicada = 75f;
            float altoHitboxPicada = 95f;
            hitboxAtaque.setSize(anchoHitboxPicada, altoHitboxPicada);

            float centroX = posX + (ancho / 2f) - (anchoHitboxPicada / 2f);
            hitboxAtaque.setPosition(centroX, posY);
        } 
        // --- FASE 3: RECOVERY ---
        else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            velocidadY = 0;
            velocidadX = 0;
        }
    }

}