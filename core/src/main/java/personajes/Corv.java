package personajes;

public class Corv extends Aves {

	
    public Corv(float posX, float posY) {
        super("corv", posX, posY, "characters/corv/reposo.png");
        
        ESPECIAL_GANADO_NEUTRAL = 10f;
        ESPECIAL_GANADO_LATERAL = 25f;
        ESPECIAL_GANADO_ABAJO   = 18f;

        ESPECIAL_GANADO_AEREO_NEUTRAL = 12f;
        ESPECIAL_GANADO_AEREO_LATERAL = 22f;
        ESPECIAL_GANADO_AEREO_ABAJO   = 16f;
        
        // --- ESTADÍSTICAS BÁSICAS ---
        this.salud = 400f;
        this.saludMaxima = 400f;
        this.ancho = 160f;
        this.alto = 160f;

        FUERZA_KNOCKBACK = 250f;
        
     // --- ESTADÍSTICAS DE ATAQUES NORMALES ---
        TIEMPO_STARTUP_NEUTRAL  = 0.07f * 5;
        TIEMPO_ACTIVO_NEUTRAL   = 0.07f * 2;
        TIEMPO_RECOVERY_NEUTRAL = 0.07f * 1;
        DURACION_TOTAL_NEUTRAL  = TIEMPO_STARTUP_NEUTRAL + TIEMPO_ACTIVO_NEUTRAL + TIEMPO_RECOVERY_NEUTRAL;
        DANIO_NEUTRAL           = 14f; 
        
        TIEMPO_STARTUP_LATERAL  = 0.15f * 2; 
        TIEMPO_ACTIVO_LATERAL   = 0.15f * 4; 
        TIEMPO_RECOVERY_LATERAL = 0.15f * 2;
        DURACION_TOTAL_LATERAL  = TIEMPO_STARTUP_LATERAL + TIEMPO_ACTIVO_LATERAL + TIEMPO_RECOVERY_LATERAL; 
        DANIO_LATERAL           = 18f;
        
        TIEMPO_STARTUP_ABAJO  = 0.04f * 11;
        TIEMPO_ACTIVO_ABAJO   = 0.04f * 6;
        TIEMPO_RECOVERY_ABAJO = 0.04f * 3;
        DURACION_TOTAL_ABAJO  = TIEMPO_STARTUP_ABAJO + TIEMPO_ACTIVO_ABAJO + TIEMPO_RECOVERY_ABAJO;
        DANIO_ABAJO           = 12f;
        
        TIEMPO_STARTUP_AEREO_NEUTRAL  = 0.05f * 3;
        TIEMPO_ACTIVO_AEREO_NEUTRAL   = 0.05f * 2;
        TIEMPO_RECOVERY_AEREO_NEUTRAL = 0.05f * 3; 
        DURACION_TOTAL_AEREO_NEUTRAL  = TIEMPO_STARTUP_AEREO_NEUTRAL + TIEMPO_ACTIVO_AEREO_NEUTRAL + TIEMPO_RECOVERY_AEREO_NEUTRAL;
        DANIO_AEREO_NEUTRAL           = 10f;
        
        TIEMPO_STARTUP_AEREO_LATERAL  = 0.03f * 2;
        TIEMPO_ACTIVO_AEREO_LATERAL   = 0.03f * 9;
        TIEMPO_RECOVERY_AEREO_LATERAL = 0.03f * 2; 
        DURACION_TOTAL_AEREO_LATERAL  = TIEMPO_STARTUP_AEREO_LATERAL + TIEMPO_ACTIVO_AEREO_LATERAL + TIEMPO_RECOVERY_AEREO_LATERAL;
        DANIO_AEREO_LATERAL           = 12f;
        
        TIEMPO_STARTUP_AEREO_ABAJO  = 0.04f * 11;
        TIEMPO_ACTIVO_AEREO_ABAJO   = 0.04f * 6;
        TIEMPO_RECOVERY_AEREO_ABAJO = 0.04f * 3; 
        DURACION_TOTAL_AEREO_ABAJO  = TIEMPO_STARTUP_AEREO_ABAJO + TIEMPO_ACTIVO_AEREO_ABAJO + TIEMPO_RECOVERY_AEREO_ABAJO;
        DANIO_AEREO_ABAJO          = 12f;

        // --- ESTADÍSTICAS DE ATAQUES ESPECIALES ---
     // --- TIEMPOS Y PROPIEDADES DE LOS ESPECIALES ---
        TIEMPO_STARTUP_ESP_NEUTRAL  = 0.03f * 5;
        TIEMPO_ACTIVO_ESP_NEUTRAL   = 0.03f * 2;
        TIEMPO_RECOVERY_ESP_NEUTRAL = 0.03f * 1;
        DURACION_TOTAL_ESP_NEUTRAL  = TIEMPO_STARTUP_ESP_NEUTRAL + TIEMPO_ACTIVO_ESP_NEUTRAL + TIEMPO_RECOVERY_ESP_NEUTRAL;
        DANIO_ESP_NEUTRAL           = 20f;

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
        DANIO_ESP_ABAJO           = 30f;    
        
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
        hitboxAtaque.setSize(80f, 60f); 
        
        float tiempoTranscurrido = DURACION_TOTAL_NEUTRAL - tiempoAccion;
        boolean enDano = tiempoTranscurrido >= TIEMPO_STARTUP_NEUTRAL && 
                         tiempoTranscurrido <= (TIEMPO_STARTUP_NEUTRAL + TIEMPO_ACTIVO_NEUTRAL);

        if (enDano) {
            atacando = true;
            float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width;
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 4f));
        } else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }
    }
    
    @Override
    protected void actualizarAtaqueLateral(float delta) {
    	danioAtaque = DANIO_LATERAL;
        float VELOCIDAD_CARGA_LATERAL = 990f;
        hitboxAtaque.setSize(80f, 60f);
        float tiempoTranscurrido = DURACION_TOTAL_LATERAL - tiempoAccion;
        float direccion = mirandoDerecha ? 1f : -1f;

        if (tiempoTranscurrido < TIEMPO_STARTUP_LATERAL) {
            velocidadX = 0;              
            atacando = false;            
            hitboxAtaque.setPosition(-9999, -9999);
        } 
        else if (tiempoTranscurrido <= (TIEMPO_STARTUP_LATERAL + TIEMPO_ACTIVO_LATERAL)) {
            velocidadX = direccion * VELOCIDAD_CARGA_LATERAL; 
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
        danioAtaque = DANIO_ABAJO; 
        velocidadX = 0; // Quieto horizontalmente

        float tiempoTranscurrido = DURACION_TOTAL_ABAJO - tiempoAccion;
        
        // Cada frame dura 0.03f, por lo que 2 frames equivalen a 0.06f
        float duracionDosFrames = 0.03f * 2f; 
        float inicioPausaStartup = TIEMPO_STARTUP_ABAJO - duracionDosFrames;
        float finActivo = TIEMPO_STARTUP_ABAJO + TIEMPO_ACTIVO_ABAJO;

        // FASE 1A: Impulso inicial hacia arriba durante el startup
        if (tiempoTranscurrido < inicioPausaStartup) {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            
            velocidadY = 1200f; 
            enElSuelo = false;
            suspenderGravedad = false; // Gravedad activa durante el impulso
        } 
        // FASE 1B: Últimos 2 frames del startup (SUSPENDIDO EN EL AIRE)
        else if (tiempoTranscurrido < TIEMPO_STARTUP_ABAJO) {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            
            velocidadY = 0f; 
            suspenderGravedad = true; // Congela la gravedad por completo
        }
        // FASE 2: ACTIVA (Caída veloz y violenta con la hitbox abajo)
        else if (tiempoTranscurrido <= finActivo) {
            atacando = true;
            
            velocidadY = -1300f; // Desplome ultra rápido
            suspenderGravedad = false; // Gravedad/velocidad de caída libre habilitada

            // Dimensiones y posición de la hitbox de ataque debajo del personaje
            float anchoHitboxAbajo = 150f;
            float altoHitboxAbajo = 50f;
            hitboxAtaque.setSize(anchoHitboxAbajo, altoHitboxAbajo);

            float centroPersonajeX = posX + (ancho / 2f);
            float posYHitbox = posY - altoHitboxAbajo; 

            hitboxAtaque.setPosition(centroPersonajeX - (anchoHitboxAbajo / 2f), posYHitbox);
        } 
        // FASE 3: RECOVERY (SUSPENDIDO EN EL AIRE DURANTE LA RECUPERACIÓN)
        else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            
            velocidadY = 0f; 
            suspenderGravedad = true; // Congela la gravedad también en el recovery
        }
    }
    
    @Override
    protected void actualizarAtaqueNeutralAereo() {
        danioAtaque = DANIO_AEREO_NEUTRAL; // Puedes cambiarlo a DANIO_NEUTRAL si prefieres el mismo daño exacto
        hitboxAtaque.setSize(80f, 60f); 
        
        // --- SUSPENSIÓN EN EL AIRE ---
        velocidadY = 0f;          // Congela cualquier velocidad vertical previa
        suspenderGravedad = true; // Anula la gravedad para que no caiga mientras ataca
        
        float tiempoTranscurrido = DURACION_TOTAL_AEREO_NEUTRAL - tiempoAccion;
        boolean enDano = tiempoTranscurrido >= TIEMPO_STARTUP_AEREO_NEUTRAL && 
                         tiempoTranscurrido <= (TIEMPO_STARTUP_AEREO_NEUTRAL + TIEMPO_ACTIVO_AEREO_NEUTRAL);

        if (enDano) {
            atacando = true;
            float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width;
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 4f));
        } else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }
    }

    @Override
    protected void actualizarAtaqueLateralAereo(float delta) {
        danioAtaque = DANIO_AEREO_LATERAL;
    	float VELOCIDAD_CARGA_LATERAL = 990f;
    	suspenderGravedad = true;
    	velocidadY = 0f; 
        hitboxAtaque.setSize(80f, 60f);
        float tiempoTranscurrido = DURACION_TOTAL_LATERAL - tiempoAccion;
        float direccion = mirandoDerecha ? 1f : -1f;

        if (tiempoTranscurrido < TIEMPO_STARTUP_LATERAL) {
            velocidadX = 0;              
            atacando = false;            
            hitboxAtaque.setPosition(-9999, -9999);
        } 
        else if (tiempoTranscurrido <= (TIEMPO_STARTUP_LATERAL + TIEMPO_ACTIVO_LATERAL)) {
            velocidadX = direccion * VELOCIDAD_CARGA_LATERAL; 
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
    protected void actualizarAtaqueAbajoAereo() {
    	danioAtaque = DANIO_AEREO_ABAJO; 
        velocidadX = 0; // Quieto horizontalmente

        float tiempoTranscurrido = DURACION_TOTAL_ABAJO - tiempoAccion;
        
        // Cada frame dura 0.03f, por lo que 2 frames equivalen a 0.06f
        float duracionDosFrames = 0.03f * 2f; 
        float inicioPausaStartup = TIEMPO_STARTUP_ABAJO - duracionDosFrames;
        float finActivo = TIEMPO_STARTUP_ABAJO + TIEMPO_ACTIVO_ABAJO;

        // FASE 1A: Impulso inicial hacia arriba durante el startup
        if (tiempoTranscurrido < inicioPausaStartup) {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            
            velocidadY = 1200f; 
            enElSuelo = false;
            suspenderGravedad = false; // Gravedad activa durante el impulso
        } 
        // FASE 1B: Últimos 2 frames del startup (SUSPENDIDO EN EL AIRE)
        else if (tiempoTranscurrido < TIEMPO_STARTUP_ABAJO) {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            
            velocidadY = 0f; 
            suspenderGravedad = true; // Congela la gravedad por completo
        }
        // FASE 2: ACTIVA (Caída veloz y violenta con la hitbox abajo)
        else if (tiempoTranscurrido <= finActivo) {
            atacando = true;
            
            velocidadY = -1300f; // Desplome ultra rápido
            suspenderGravedad = false; // Gravedad/velocidad de caída libre habilitada

            // Dimensiones y posición de la hitbox de ataque debajo del personaje
            float anchoHitboxAbajo = 150f;
            float altoHitboxAbajo = 50f;
            hitboxAtaque.setSize(anchoHitboxAbajo, altoHitboxAbajo);

            float centroPersonajeX = posX + (ancho / 2f);
            float posYHitbox = posY - altoHitboxAbajo; 

            hitboxAtaque.setPosition(centroPersonajeX - (anchoHitboxAbajo / 2f), posYHitbox);
        } 
        // FASE 3: RECOVERY (SUSPENDIDO EN EL AIRE DURANTE LA RECUPERACIÓN)
        else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            
            velocidadY = 0f; 
            suspenderGravedad = true; // Congela la gravedad también en el recovery
        }
    }
    
    // --- ATAQUES ESPECIALES ---
    @Override
    protected void actualizarEspecialNeutral(float delta) {
        danioAtaque = DANIO_ESP_NEUTRAL;
        hitboxAtaque.setSize(80f, 60f); 
        tieneSuperarmor = true; // Activa la superarmadura durante todo el especial neutral
        
        float tiempoTranscurrido = DURACION_TOTAL_ESP_NEUTRAL - tiempoAccion;
        boolean enDano = tiempoTranscurrido >= TIEMPO_STARTUP_ESP_NEUTRAL && 
                         tiempoTranscurrido <= (TIEMPO_STARTUP_ESP_NEUTRAL + TIEMPO_ACTIVO_ESP_NEUTRAL);

        if (enDano) {
            atacando = true;
            float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width;
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 4f));
        } else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }
    }

    @Override
    protected void actualizarEspecialLateral(float delta) {
        danioAtaque = DANIO_ESP_LATERAL; 
        tieneSuperarmor = true;
        float VELOCIDAD_CARGA_LATERAL = 990f;
        hitboxAtaque.setSize(80f, 60f);
        float tiempoTranscurrido = DURACION_TOTAL_LATERAL - tiempoAccion;
        float direccion = mirandoDerecha ? 1f : -1f;

        if (tiempoTranscurrido < TIEMPO_STARTUP_LATERAL) {
            velocidadX = 0;              
            atacando = false;            
            hitboxAtaque.setPosition(-9999, -9999);
        } 
        else if (tiempoTranscurrido <= (TIEMPO_STARTUP_LATERAL + TIEMPO_ACTIVO_LATERAL)) {
            velocidadX = direccion * VELOCIDAD_CARGA_LATERAL; 
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
    protected void actualizarEspecialAbajo(float delta) {
        danioAtaque = DANIO_ESP_ABAJO; 
        tieneSuperarmor = true;
        velocidadX = 0; // Quieto horizontalmente

        float tiempoTranscurrido = DURACION_TOTAL_ABAJO - tiempoAccion;
        
        // Cada frame dura 0.03f, por lo que 2 frames equivalen a 0.06f
        float duracionDosFrames = 0.03f * 2f; 
        float inicioPausaStartup = TIEMPO_STARTUP_ABAJO - duracionDosFrames;
        float finActivo = TIEMPO_STARTUP_ABAJO + TIEMPO_ACTIVO_ABAJO;

        // FASE 1A: Impulso inicial hacia arriba durante el startup
        if (tiempoTranscurrido < inicioPausaStartup) {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            
            velocidadY = 1200f; 
            enElSuelo = false;
            suspenderGravedad = false; // Gravedad activa durante el impulso
        } 
        // FASE 1B: Últimos 2 frames del startup (SUSPENDIDO EN EL AIRE)
        else if (tiempoTranscurrido < TIEMPO_STARTUP_ABAJO) {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            
            velocidadY = 0f; 
            suspenderGravedad = true; // Congela la gravedad por completo
        }
        // FASE 2: ACTIVA (Caída veloz y violenta con la hitbox abajo)
        else if (tiempoTranscurrido <= finActivo) {
            atacando = true;
            
            velocidadY = -1300f; // Desplome ultra rápido
            suspenderGravedad = false; // Gravedad/velocidad de caída libre habilitada

            // Dimensiones y posición de la hitbox de ataque debajo del personaje
            float anchoHitboxAbajo = 150f;
            float altoHitboxAbajo = 50f;
            hitboxAtaque.setSize(anchoHitboxAbajo, altoHitboxAbajo);

            float centroPersonajeX = posX + (ancho / 2f);
            float posYHitbox = posY - altoHitboxAbajo; 

            hitboxAtaque.setPosition(centroPersonajeX - (anchoHitboxAbajo / 2f), posYHitbox);
        } 
        // FASE 3: RECOVERY (SUSPENDIDO EN EL AIRE DURANTE LA RECUPERACIÓN)
        else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            
            velocidadY = 0f; 
            suspenderGravedad = true; // Congela la gravedad también en el recovery
        }
    }
    
    @Override
    protected void actualizarEspecialLateralAereo(float delta) {
        danioAtaque = DANIO_ESP_AEREO_LATERAL;
        tieneSuperarmor = true;
        float VELOCIDAD_CARGA_LATERAL = 990f;
    	suspenderGravedad = true;
    	velocidadY = 0f; 
        hitboxAtaque.setSize(80f, 60f);
        float tiempoTranscurrido = DURACION_TOTAL_LATERAL - tiempoAccion;
        float direccion = mirandoDerecha ? 1f : -1f;

        if (tiempoTranscurrido < TIEMPO_STARTUP_LATERAL) {
            velocidadX = 0;              
            atacando = false;            
            hitboxAtaque.setPosition(-9999, -9999);
        } 
        else if (tiempoTranscurrido <= (TIEMPO_STARTUP_LATERAL + TIEMPO_ACTIVO_LATERAL)) {
            velocidadX = direccion * VELOCIDAD_CARGA_LATERAL; 
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
    protected void actualizarEspecialNeutralAereo(float delta) {
        danioAtaque = DANIO_ESP_AEREO_NEUTRAL; 
        tieneSuperarmor = true;
        hitboxAtaque.setSize(80f, 60f); 
        
        // --- SUSPENSIÓN EN EL AIRE ---
        velocidadY = 0f;          // Congela cualquier velocidad vertical previa
        suspenderGravedad = true; // Anula la gravedad para que no caiga mientras ataca
        
        float tiempoTranscurrido = DURACION_TOTAL_AEREO_NEUTRAL - tiempoAccion;
        boolean enDano = tiempoTranscurrido >= TIEMPO_STARTUP_AEREO_NEUTRAL && 
                         tiempoTranscurrido <= (TIEMPO_STARTUP_AEREO_NEUTRAL + TIEMPO_ACTIVO_AEREO_NEUTRAL);

        if (enDano) {
            atacando = true;
            float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width;
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 4f));
        } else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }
    }
    
    @Override
    protected void actualizarEspecialAbajoAereo(float delta) {
        danioAtaque = DANIO_ESP_AEREO_ABAJO;
        tieneSuperarmor = true;
        velocidadX = 0; // Quieto horizontalmente

        float tiempoTranscurrido = DURACION_TOTAL_ABAJO - tiempoAccion;
        
        // Cada frame dura 0.03f, por lo que 2 frames equivalen a 0.06f
        float duracionDosFrames = 0.03f * 2f; 
        float inicioPausaStartup = TIEMPO_STARTUP_ABAJO - duracionDosFrames;
        float finActivo = TIEMPO_STARTUP_ABAJO + TIEMPO_ACTIVO_ABAJO;

        // FASE 1A: Impulso inicial hacia arriba durante el startup
        if (tiempoTranscurrido < inicioPausaStartup) {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            
            velocidadY = 1200f; 
            enElSuelo = false;
            suspenderGravedad = false; // Gravedad activa durante el impulso
        } 
        // FASE 1B: Últimos 2 frames del startup (SUSPENDIDO EN EL AIRE)
        else if (tiempoTranscurrido < TIEMPO_STARTUP_ABAJO) {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            
            velocidadY = 0f; 
            suspenderGravedad = true; // Congela la gravedad por completo
        }
        // FASE 2: ACTIVA (Caída veloz y violenta con la hitbox abajo)
        else if (tiempoTranscurrido <= finActivo) {
            atacando = true;
            
            velocidadY = -1300f; // Desplome ultra rápido
            suspenderGravedad = false; // Gravedad/velocidad de caída libre habilitada

            // Dimensiones y posición de la hitbox de ataque debajo del personaje
            float anchoHitboxAbajo = 150f;
            float altoHitboxAbajo = 50f;
            hitboxAtaque.setSize(anchoHitboxAbajo, altoHitboxAbajo);

            float centroPersonajeX = posX + (ancho / 2f);
            float posYHitbox = posY - altoHitboxAbajo; 

            hitboxAtaque.setPosition(centroPersonajeX - (anchoHitboxAbajo / 2f), posYHitbox);
        } 
        // FASE 3: RECOVERY (SUSPENDIDO EN EL AIRE DURANTE LA RECUPERACIÓN)
        else {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
            
            velocidadY = 0f; 
            suspenderGravedad = true; // Congela la gravedad también en el recovery
        }
    }
}