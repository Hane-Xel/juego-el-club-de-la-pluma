package personajes;

public class Kiri extends Aves {

    public Kiri(float posX, float posY) {
        super("kiri", posX, posY, "characters/kiri/reposo.png");
        
        this.salud = 300f;
        this.saludMaxima = 300f;
        this.ancho = 130f;
        this.alto = 130f;
        
        
        // --- ESTADÍSTICAS DE ATAQUES NORMALES ---
        TIEMPO_STARTUP_NEUTRAL  = 0.03f * 2; // las duraciones son asi para que sean mas faciles de leer y programar. el primer numero es la velocidad a la que se cambian los cuadros y el segundo es la cantidad de frames que le corresponden a cada fase
        TIEMPO_ACTIVO_NEUTRAL   = 0.03f * 4;
        TIEMPO_RECOVERY_NEUTRAL = 0.03f * 4;
        DURACION_TOTAL_NEUTRAL  = TIEMPO_STARTUP_NEUTRAL + TIEMPO_ACTIVO_NEUTRAL + TIEMPO_RECOVERY_NEUTRAL;
        DANIO_NEUTRAL           = 8f; 
        
        TIEMPO_STARTUP_LATERAL  = 0.05f * 3; 
        TIEMPO_ACTIVO_LATERAL   = 0.05f * 10; 
        TIEMPO_RECOVERY_LATERAL = 0.05f * 4;
        DURACION_TOTAL_LATERAL  = TIEMPO_STARTUP_LATERAL + TIEMPO_ACTIVO_LATERAL + TIEMPO_RECOVERY_LATERAL; 
        DANIO_LATERAL           = 12f;
        
        TIEMPO_STARTUP_ABAJO  = 0.04f * 16;
        TIEMPO_ACTIVO_ABAJO   = 0.04f * 3;
        TIEMPO_RECOVERY_ABAJO = 0.04f * 1;
        DURACION_TOTAL_ABAJO  = TIEMPO_STARTUP_ABAJO + TIEMPO_ACTIVO_ABAJO + TIEMPO_RECOVERY_ABAJO;
        DANIO_ABAJO           = 20f;
        
        TIEMPO_STARTUP_AEREO_NEUTRAL  = 0.05f * 3;
        TIEMPO_ACTIVO_AEREO_NEUTRAL   = 0.05f * 2;
        TIEMPO_RECOVERY_AEREO_NEUTRAL = 0.05f * 3; 
        DURACION_TOTAL_AEREO_NEUTRAL  = TIEMPO_STARTUP_AEREO_NEUTRAL + TIEMPO_ACTIVO_AEREO_NEUTRAL + TIEMPO_RECOVERY_AEREO_NEUTRAL;
        DANIO_AEREO_NEUTRAL           = 10f;
        
        TIEMPO_STARTUP_AEREO_LATERAL  = 0.03f * 14;
        TIEMPO_ACTIVO_AEREO_LATERAL   = 0.03f * 2;
        TIEMPO_RECOVERY_AEREO_LATERAL = 0.03f * 2; 
        DURACION_TOTAL_AEREO_LATERAL  = TIEMPO_STARTUP_AEREO_LATERAL + TIEMPO_ACTIVO_AEREO_LATERAL + TIEMPO_RECOVERY_AEREO_LATERAL;
        DANIO_AEREO_LATERAL           = 6f;
        
        TIEMPO_STARTUP_AEREO_ABAJO  = 0.05f * 4;
        TIEMPO_ACTIVO_AEREO_ABAJO   = 0.05f * 12;
        TIEMPO_RECOVERY_AEREO_ABAJO = 0.05f * 4; 
        DURACION_TOTAL_AEREO_ABAJO  = TIEMPO_STARTUP_AEREO_ABAJO + TIEMPO_ACTIVO_AEREO_ABAJO + TIEMPO_RECOVERY_AEREO_ABAJO;
        DANIO_AEREO_ABAJO          = 12f;

        // --- ESTADÍSTICAS DE ATAQUES ESPECIALES ---
     // --- TIEMPOS Y PROPIEDADES DE LOS ESPECIALES ---
        TIEMPO_STARTUP_ESP_NEUTRAL  = 0.03f * 2;
        TIEMPO_ACTIVO_ESP_NEUTRAL   = 0.03f * 4;
        TIEMPO_RECOVERY_ESP_NEUTRAL = 0.03f * 4;
        DURACION_TOTAL_ESP_NEUTRAL  = TIEMPO_STARTUP_ESP_NEUTRAL + TIEMPO_ACTIVO_ESP_NEUTRAL + TIEMPO_RECOVERY_ESP_NEUTRAL;
        DANIO_ESP_NEUTRAL           = 10f;

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
        DANIO_ESP_ABAJO           = 50f;    
        
     // --- ESTADÍSTICAS DEL ESPECIAL AÉREO LATERAL ---
        TIEMPO_STARTUP_ESP_AEREO_LATERAL  = 0.03f * 4; 
        TIEMPO_ACTIVO_ESP_AEREO_LATERAL   = 0.03f * 9;  
        TIEMPO_RECOVERY_ESP_AEREO_LATERAL = 0.03f * 3; 
        DURACION_TOTAL_ESP_AEREO_LATERAL  = TIEMPO_STARTUP_ESP_AEREO_LATERAL + TIEMPO_ACTIVO_ESP_AEREO_LATERAL + TIEMPO_RECOVERY_ESP_AEREO_LATERAL;
        DANIO_ESP_AEREO_LATERAL           = 15f;
        
     // --- ESTADÍSTICAS DEL ESPECIAL AÉREO NEUTRAL ---
        TIEMPO_STARTUP_ESP_AEREO_NEUTRAL  = 0.08f;
        TIEMPO_ACTIVO_ESP_AEREO_NEUTRAL   = 0.15f;
        TIEMPO_RECOVERY_ESP_AEREO_NEUTRAL = 0.15f;
        DURACION_TOTAL_ESP_AEREO_NEUTRAL  = TIEMPO_STARTUP_ESP_AEREO_NEUTRAL + TIEMPO_ACTIVO_ESP_AEREO_NEUTRAL + TIEMPO_RECOVERY_ESP_AEREO_NEUTRAL;
        DANIO_ESP_AEREO_NEUTRAL           = 10f;
        
     // --- ESTADÍSTICAS DEL ESPECIAL AÉREO ABAJO ---
        TIEMPO_STARTUP_ESP_AEREO_ABAJO  = 0.05f * 4;
        TIEMPO_ACTIVO_ESP_AEREO_ABAJO   = 0.05f * 12;
        TIEMPO_RECOVERY_ESP_AEREO_ABAJO = 0.05f * 4; 
        DURACION_TOTAL_ESP_AEREO_ABAJO  = TIEMPO_STARTUP_ESP_AEREO_ABAJO + TIEMPO_ACTIVO_ESP_AEREO_ABAJO + TIEMPO_RECOVERY_ESP_AEREO_ABAJO;
        DANIO_ESP_AEREO_ABAJO           = 18f; // Daño potenciado por ser especial
    }
    
    // --- ATAQUES NORMALES ---
    @Override
    protected void actualizarAtaqueNeutral() {// ataque normal
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
    protected void actualizarAtaqueLateral(float delta) { // la misma logica que el del cuervo. si lo quieren ver vayan a esa clase
    	float VELOCIDAD_CARGA_LATERAL=800;
        hitboxAtaque.setSize(90f, 60f);
        
        float tiempoTranscurrido = DURACION_TOTAL_LATERAL - tiempoAccion;
        float direccion = mirandoDerecha ? 1f : -1f;

        if (tiempoTranscurrido < TIEMPO_STARTUP_LATERAL) {
            velocidadX = 0;              
            atacando = false;            
            hitboxAtaque.setPosition(-9999, -9999);
        } 
        else if (tiempoTranscurrido <= (TIEMPO_STARTUP_LATERAL + TIEMPO_ACTIVO_LATERAL)) {
            float velocidadDisparo = 900f; 
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
        float anchoHitboxAbajo = 200f; // para que sea mas ancho de lo normal
        float altoHitboxAbajo = 40f;   
        hitboxAtaque.setSize(anchoHitboxAbajo, altoHitboxAbajo);

        float tiempoTranscurrido = DURACION_TOTAL_ABAJO - tiempoAccion;
        boolean enDano = tiempoTranscurrido >= TIEMPO_STARTUP_ABAJO && 
                         tiempoTranscurrido <= (TIEMPO_STARTUP_ABAJO + TIEMPO_ACTIVO_ABAJO);

        if (enDano) {
            atacando = true;
            float centroPersonajeX = posX + (ancho / 2f); // calcula donde esta el centro
            hitboxAtaque.setPosition(centroPersonajeX - (anchoHitboxAbajo / 2f), posY); // ataque normal, pero hace que esto (anchoHitboxAbajo / 2f) sea 2f para que salga mas de abajo
        } else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }
    }
    
    @Override
    protected void actualizarAtaqueNeutralAereo() { // ataque normal solo que en el aire
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
        hitboxAtaque.setSize(95f, 55f);

        float tiempoTranscurrido = DURACION_TOTAL_AEREO_LATERAL - tiempoAccion;
        float direccion = mirandoDerecha ? 1f : -1f;

        if (tiempoTranscurrido < TIEMPO_STARTUP_AEREO_LATERAL) {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            velocidadX = direccion * 650f; // velocidad agregada
            
            if (tiempoAccion >= DURACION_TOTAL_AEREO_LATERAL - 0.05f) {
                velocidadY = 550f; // saltito
            }
        } 
        else if (tiempoTranscurrido <= (TIEMPO_STARTUP_AEREO_LATERAL + TIEMPO_ACTIVO_AEREO_LATERAL)) {
            atacando = true;
            float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width;
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 3f));

            velocidadX *= 0.88f; 
            velocidadY *= 0.90f; // desaceleracion
        } 
        else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            velocidadX *= 0.92f; 
        }
    }

    @Override
    protected void actualizarAtaqueAbajoAereo() {
        float tiempoTranscurrido = DURACION_TOTAL_AEREO_ABAJO - tiempoAccion;

        
        
        if (tiempoTranscurrido >= TIEMPO_STARTUP_AEREO_ABAJO && 
            tiempoTranscurrido <= (TIEMPO_STARTUP_AEREO_ABAJO + TIEMPO_ACTIVO_AEREO_ABAJO) && 
            velocidadY == 0f) {
            
            tiempoAccion = TIEMPO_RECOVERY_AEREO_ABAJO;
            stateTime = DURACION_TOTAL_AEREO_ABAJO - TIEMPO_RECOVERY_AEREO_ABAJO;
        }

        tiempoTranscurrido = DURACION_TOTAL_AEREO_ABAJO - tiempoAccion;

        if (tiempoTranscurrido < TIEMPO_STARTUP_AEREO_ABAJO) {
            velocidadY = 50f; // para que se quede un poco en el aire
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
    
    // son basicamente lo mismo pero con estadisticas cambiadas
    // --- ATAQUES ESPECIALES MODULARES ---

@Override
    protected void actualizarEspecialNeutral(float delta) {// lo especial de este ataque esta en la clase combate en verificar colisiones ataque, que da el saltito y no se mueve hasta que toca el piso
        hitboxAtaque.setSize(15f, 60f); 

        float tiempoTranscurrido = DURACION_TOTAL_ESP_NEUTRAL - tiempoAccion;

        if (tiempoTranscurrido <= 0.05f) {
            velocidadY = FUERZA_SALTO * 0.45f; // el saltito este sirve para que se vea un poco distinto al normal
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
    protected void actualizarEspecialLateral(float delta) {// igual que el original
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
    protected void actualizarEspecialLateralAereo(float delta) { // a grandes rasgos igual que el original
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
    protected void actualizarEspecialNeutralAereo(float delta) {// lo especial de este ataque esta en la clase combate en verificar colisiones ataque, que da el saltito y no se mueve hasta que toca el piso
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
                 velocidadY = -1500f; 
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

}