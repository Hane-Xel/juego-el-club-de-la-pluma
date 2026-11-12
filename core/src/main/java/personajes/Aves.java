package personajes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import recursos.GestorRecursosPersonajes;
import recursos.GestorRecursosPersonajes.EstadoAnimacion;
import com.badlogic.gdx.graphics.Color;
import escenarios.Plataforma;

public class Aves {

    protected float posX, posY;
    protected float velocidadX, velocidadY;
	
	protected boolean dobleSaltoDisponible = false;
	protected boolean saltoPresionadoUltimoFrame = false; // sirve para que no se pueda dejar pulsado el boton de saltar y se va
	protected boolean tieneSuperarmor = false; // sirve para comprobar los ataques con superarmor. solo los usa Corvenance
	
    protected float FUERZA_KNOCKBACK = 500f;
    protected float tiempoDebuffProyectil = 0f; // para el debuff de emuans
    protected float tiempoInvulnerable = 0f; // Controla los segundos de invencibilidad tras un bloqueo exitoso
    
    protected String ave; // guarda el nombre del personaje en un identificador. con esto el codigo sabe que personaje es el que tiene el jugador

    protected float ancho = 120f, alto = 120f; // da las medidas necesarias para la hitbox y los sprites
    
    protected static final float GRAVEDAD = -2500f; // la fuerza con la que los personajes caen
    protected float VELOCIDAD_MOVIMIENTO = 400f;
    protected float FUERZA_SALTO = 1300f;

    protected static final float ANCHO_PANTALLA_DEFECTO = 1920f; // sirve para darle a actualizar el tamaño relativo de la pantalla y simplificar varios procesos

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
    // los tres sirven para dar el estado en el que se encuentra el personaje actualmente
    // enum es una lista en la que la variable solo puede tomar los valores que esten listados anteriormente
    
    protected TipoAtaque ataqueActual = TipoAtaque.NINGUNO;
    protected TipoAtaqueAereo ataqueAereoActual = TipoAtaqueAereo.NINGUNO;
    protected TipoAtaqueEspecial especialActual = TipoAtaqueEspecial.NINGUNO;
    // toman los valores de los enum de arriba para definir el estado en el que se encuentra el personaje
    
    protected float ESPECIAL_GANADO_NEUTRAL = 15f;
    protected float ESPECIAL_GANADO_LATERAL = 25f;
    protected float ESPECIAL_GANADO_ABAJO   = 18f;
    protected float ESPECIAL_GANADO_AEREO_NEUTRAL = 12f;
    protected float ESPECIAL_GANADO_AEREO_LATERAL = 22f;
    protected float ESPECIAL_GANADO_AEREO_ABAJO   = 16f;

    protected float TIEMPO_STARTUP_AEREO_NEUTRAL  = 0.08f;
    protected float TIEMPO_ACTIVO_AEREO_NEUTRAL   = 0.12f;
    protected float TIEMPO_RECOVERY_AEREO_NEUTRAL = 0.15f;
    protected float DURACION_TOTAL_AEREO_NEUTRAL  = TIEMPO_STARTUP_AEREO_NEUTRAL + TIEMPO_ACTIVO_AEREO_NEUTRAL + TIEMPO_RECOVERY_AEREO_NEUTRAL;
    protected float DANIO_AEREO_NEUTRAL           = 6f;

    protected float TIEMPO_STARTUP_AEREO_LATERAL  = 0.10f;
    protected float TIEMPO_ACTIVO_AEREO_LATERAL   = 0.15f;
    protected float TIEMPO_RECOVERY_AEREO_LATERAL = 0.20f;
    protected float DURACION_TOTAL_AEREO_LATERAL  = TIEMPO_STARTUP_AEREO_LATERAL + TIEMPO_ACTIVO_AEREO_LATERAL + TIEMPO_RECOVERY_AEREO_LATERAL;
    protected float DANIO_AEREO_LATERAL           = 10f;

    protected float TIEMPO_STARTUP_AEREO_ABAJO  = 0.06f;
    protected float TIEMPO_ACTIVO_AEREO_ABAJO   = 0.10f;
    protected float TIEMPO_RECOVERY_AEREO_ABAJO = 0.18f;
    protected float DURACION_TOTAL_AEREO_ABAJO  = TIEMPO_STARTUP_AEREO_ABAJO + TIEMPO_ACTIVO_AEREO_ABAJO + TIEMPO_RECOVERY_AEREO_ABAJO;
    protected float DANIO_AEREO_ABAJO           = 8f;

    protected float TIEMPO_STARTUP_NEUTRAL  = 0.10f;
    protected float TIEMPO_ACTIVO_NEUTRAL   = 0.10f;
    protected float TIEMPO_RECOVERY_NEUTRAL = 0.15f;
    protected float DURACION_TOTAL_NEUTRAL  = TIEMPO_STARTUP_NEUTRAL + TIEMPO_ACTIVO_NEUTRAL + TIEMPO_RECOVERY_NEUTRAL;
    protected float DANIO_NEUTRAL           = 5f;

    protected float TIEMPO_STARTUP_LATERAL  = 0.12f;
    protected float TIEMPO_ACTIVO_LATERAL   = 0.18f;
    protected float TIEMPO_RECOVERY_LATERAL = 0.20f;
    protected float DURACION_TOTAL_LATERAL  = TIEMPO_STARTUP_LATERAL + TIEMPO_ACTIVO_LATERAL + TIEMPO_RECOVERY_LATERAL;
    protected float DANIO_LATERAL           = 12f;

    protected float TIEMPO_STARTUP_ABAJO  = 0.08f;
    protected float TIEMPO_ACTIVO_ABAJO   = 0.12f;
    protected float TIEMPO_RECOVERY_ABAJO = 0.18f;
    protected float DURACION_TOTAL_ABAJO  = TIEMPO_STARTUP_ABAJO + TIEMPO_ACTIVO_ABAJO + TIEMPO_RECOVERY_ABAJO;
    protected float ALCANCE_DOBLE_ABAJO   = 200f;
    protected float DANIO_ABAJO           = 7f;

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
    
    protected float TIEMPO_STARTUP_ESP_ABAJO  = 0.08f;
    protected float TIEMPO_ACTIVO_ESP_ABAJO   = 0.12f;
    protected float TIEMPO_RECOVERY_ESP_ABAJO = 0.18f;
    protected float DURACION_TOTAL_ESP_ABAJO  = TIEMPO_STARTUP_ESP_ABAJO + TIEMPO_ACTIVO_ESP_ABAJO + TIEMPO_RECOVERY_ESP_ABAJO;
    protected float DANIO_ESP_ABAJO           = 16f;
    
    protected float TIEMPO_STARTUP_ESP_AEREO_LATERAL  = 0.10f;
    protected float TIEMPO_ACTIVO_ESP_AEREO_LATERAL   = 0.15f;
    protected float TIEMPO_RECOVERY_ESP_AEREO_LATERAL = 0.20f;
    protected float DURACION_TOTAL_ESP_AEREO_LATERAL  = TIEMPO_STARTUP_ESP_AEREO_LATERAL + TIEMPO_ACTIVO_ESP_AEREO_LATERAL + TIEMPO_RECOVERY_ESP_AEREO_LATERAL;
    protected float DANIO_ESP_AEREO_LATERAL           = 16f;
    
    protected float TIEMPO_STARTUP_ESP_AEREO_NEUTRAL  = 0.08f;
    protected float TIEMPO_ACTIVO_ESP_AEREO_NEUTRAL   = 0.15f;
    protected float TIEMPO_RECOVERY_ESP_AEREO_NEUTRAL = 0.15f;
    protected float DURACION_TOTAL_ESP_AEREO_NEUTRAL  = TIEMPO_STARTUP_ESP_AEREO_NEUTRAL + TIEMPO_ACTIVO_ESP_AEREO_NEUTRAL + TIEMPO_RECOVERY_ESP_AEREO_NEUTRAL;
    protected float DANIO_ESP_AEREO_NEUTRAL           = 16f;
    
    protected float TIEMPO_STARTUP_ESP_AEREO_ABAJO  = 0.06f;
    protected float TIEMPO_ACTIVO_ESP_AEREO_ABAJO   = 0.10f;
    protected float TIEMPO_RECOVERY_ESP_AEREO_ABAJO = 0.18f;
    protected float DURACION_TOTAL_ESP_AEREO_ABAJO  = TIEMPO_STARTUP_ESP_AEREO_ABAJO + TIEMPO_ACTIVO_ESP_AEREO_ABAJO + TIEMPO_RECOVERY_ESP_AEREO_ABAJO;
    protected float DANIO_ESP_AEREO_ABAJO           = 16f;
    // estadisticas de los ataques
    

    protected float salud = 1000f;
    protected float saludMaxima = 1000f;
    protected boolean enElSuelo = false;
    protected boolean mirandoDerecha = true; // no creo que necesite explicar estos dos
    protected boolean inermeEnAire = false; // para que el personaje no pueda hacer nada. se usa en algunos especiales de kiri
    protected boolean suspenderGravedad = false; // para que la gravedad no afecte. se usa en algunos ataques del cuervo
    
    protected float energiaEspecial = 0f;
    protected float energiaEspecialMaxima = 300f;
    protected float COSTO_BARRA_ESPECIAL = 100f; // costo de los especiales

    protected float tiempoAccion = 0f; // el tiempo pasado desde que inicio una accion
    protected float DURACION_BLOQUEO = 0.40f;

    protected EstadoAnimacion estadoActual = EstadoAnimacion.REPOSO; // cambia segun el estado
    protected float stateTime = 0f; // sirve para medir el tiempo del estado

    protected Rectangle hitbox; // rectangle es un tipo de dato de ligGDX que hace una zona recibiendo 4 valores de posicion X e Y y el ancho y largo
    protected Rectangle hitboxAtaque;
    protected boolean atacando = false;
    protected boolean ataqueProcesado = false;
    protected float danioAtaque = 5f; // cambia segun el ataque

    public Aves(String ave, float posX, float posY, String rutaTextura) {
        this.ave = ave;
        this.posX = posX;
        this.posY = posY;
        this.hitbox = new Rectangle(posX, posY, ancho, alto);
        this.hitboxAtaque = new Rectangle(0, 0, 80f, 60f);
        this.saludMaxima = this.salud;
    }

    public void actualizar(float delta, Array<Plataforma> plataformas) {
        actualizar(delta, plataformas, ANCHO_PANTALLA_DEFECTO);
        // sirve para llamar mas rapido al metodo de actualizar, ya que el original pide el ancho de la pantalla que no solo puede haber uno y asi ahorro ponerlo cada vez que se llame a actualizar
        // el ancho lo pone automaticamente
    }
    public void actualizar(float delta, Array<Plataforma> plataformas, float anchoPantalla) {
        // ejecuta toda la logica del personaje cada frame
    	
    	stateTime += delta; // dice cuanto tiempo lleva el personaje haciendo la animacion actual
        
        actualizarEfectos(delta);
        
        suspenderGravedad = false;
        tieneSuperarmor = false;
     // Hacen que estos estados especiales que solo estan en algunos ataques se reinicien cada frame, lo que hace que se establezcan en false cuando termina el ataque
        
        if (tiempoAccion > 0) // si es mayor a 0 el personaje esta haciendo una accion 
        {
            tiempoAccion -= delta;
            
            if (tiempoAccion <= 0) // cuando se termina el tiempo de accion
            {
                tiempoAccion = 0;
                atacando = false;
                ataqueProcesado = false;
                
                ataqueActual = TipoAtaque.NINGUNO;
                ataqueAereoActual = TipoAtaqueAereo.NINGUNO;
                especialActual = TipoAtaqueEspecial.NINGUNO	;
                
                estadoActual = EstadoAnimacion.REPOSO;
                
                // pone todos los estados en el base
            }
        }

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
        // busca si esta haciendo alguna de los ataques

        if (estaAtacando && tiempoAccion > 0) 
        {
            if (especialActual != TipoAtaqueEspecial.NINGUNO) // si hay un especial activo
            {
                actualizarEspeciales(delta);
            }
            else if (ataqueActual != TipoAtaque.NINGUNO) // si hay un ataque activo
            {
                // Si el ataque se inició en tierra, se sigue actualizando aunque salte o se eleve
                switch (ataqueActual) // si hay un ataque activo 
                {
                    case NEUTRAL: actualizarAtaqueNeutral(); 
                    break;
                    case LATERAL: actualizarAtaqueLateral(delta); 
                    break;
                    case ABAJO:   actualizarAtaqueAbajo(); 
                    break;
                    default: 
                    break;
                    // hace el ataque en cuestion
                }
            }
            else if (ataqueAereoActual != TipoAtaqueAereo.NINGUNO) // si hay un ataque activo  
            {
                switch (ataqueAereoActual) {
                    case AEREO_NEUTRAL: actualizarAtaqueNeutralAereo(); break;
                    case AEREO_LATERAL: actualizarAtaqueLateralAereo(delta); break;
                    case AEREO_ABAJO:   actualizarAtaqueAbajoAereo(); break;
                    default: break;
                    // hace el ataque en cuestion
                }
            }
        } 
        else // si no hay un ataque activo 
        {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999); // para que no pueda atacar
        }

        // La gravedad solo se aplica si la suspensión está desactivada
        if (!suspenderGravedad) 
        {
            velocidadY += GRAVEDAD * delta; // suma a la velocidad en Y mas gravedad con el tiempo. como la gravedad es negativa se resta
        }
        
        posX += velocidadX * delta;
        posY += velocidadY * delta;
        // lo mismo que la gravedad

        hitbox.setPosition(posX, posY);
        resolverColisiones(plataformas, anchoPantalla);
        determinarEstado();
        
        float MAX_VELOCIDAD_CAIDA = -1500f; // para que los personajes no atraviesen el piso cuando van muy rapido
        if (velocidadY < MAX_VELOCIDAD_CAIDA) {
            velocidadY = MAX_VELOCIDAD_CAIDA;
        }
    }
// pueden haber dos actualizar porque reciben parametros diferentes. esto se llama sobrecarga de metodos

    protected void actualizarAtaqueNeutral() {
        hitboxAtaque.setSize(80f, 60f); // hitbox del ataque
        float tiempoTranscurrido = DURACION_TOTAL_NEUTRAL - tiempoAccion;
        boolean enDano = tiempoTranscurrido >= TIEMPO_STARTUP_NEUTRAL && 
                         tiempoTranscurrido <= (TIEMPO_STARTUP_NEUTRAL + TIEMPO_ACTIVO_NEUTRAL); // revisa el momento del ataque
        if (enDano) // el tiempo activo esta activado (pense en otra frase pero es literalmente eso). cualquier momento antes de este estado es el startup
        {
            atacando = true;
            float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width; // primero comprueba si esta mirando a la derecha, si lo esta el ancho de la hitbox es normal y si no es el negativo para elegir la direccion. es el coso ese feo de width porque es un atributo que ya esta en rectangle
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 4)); // el eje x hace que empiece desde la esquina izquierda o derecha segun lo que haya calculado la parte de arriba y el eje Y tiene (alto / 4) para que no empiece exactamente desde el piso si no desde un punto mas arriba
        } 
        else // no esta atacando asi que no hace nada (recovery)
        {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }
    }
    protected void actualizarAtaqueNeutralAereo() {
    	hitboxAtaque.setSize(80f, 60f); // hitbox del ataque
        float tiempoTranscurrido = DURACION_TOTAL_NEUTRAL - tiempoAccion;
        boolean enDano = tiempoTranscurrido >= TIEMPO_STARTUP_NEUTRAL && 
                         tiempoTranscurrido <= (TIEMPO_STARTUP_NEUTRAL + TIEMPO_ACTIVO_NEUTRAL); // revisa el momento del ataque
        if (enDano) // el tiempo activo esta activado (pense en otra frase pero es literalmente eso)
        {
            atacando = true;
            float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width; // primero comprueba si esta mirando a la derecha, si lo esta el ancho de la hitbox es normal y si no es el negativo para elegir la direccion. es el coso ese feo de width porque es un atributo que ya esta en rectangle
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 4)); // el eje x hace que empiece desde la esquina izquierda o derecha segun lo que haya calculado la parte de arriba y el eje Y tiene (alto / 4) para que no empiece exactamente desde el piso si no desde un punto mas arriba
        } 
        else // no esta atacando asi que no hace nada
        {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }
    }
    protected void actualizarAtaqueLateral(float delta) {
        // recibe delta porque todos los ataques laterales necesitan aceleracion y por lo tanto delta
    	float VELOCIDAD_CARGA_LATERAL = 800f;
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
        // recibe delta porque todos los ataques laterales necesitan aceleracion y por lo tanto delta
    	hitboxAtaque.setSize(80f, 60f); // hitbox del ataque
        float tiempoTranscurrido = DURACION_TOTAL_NEUTRAL - tiempoAccion;
        boolean enDano = tiempoTranscurrido >= TIEMPO_STARTUP_NEUTRAL && 
                         tiempoTranscurrido <= (TIEMPO_STARTUP_NEUTRAL + TIEMPO_ACTIVO_NEUTRAL); // revisa el momento del ataque
        if (enDano) // el tiempo activo esta activado (pense en otra frase pero es literalmente eso)
        {
            atacando = true;
            float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width; // primero comprueba si esta mirando a la derecha, si lo esta el ancho de la hitbox es normal y si no es el negativo para elegir la direccion. es el coso ese feo de width porque es un atributo que ya esta en rectangle
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 4)); // el eje x hace que empiece desde la esquina izquierda o derecha segun lo que haya calculado la parte de arriba y el eje Y tiene (alto / 4) para que no empiece exactamente desde el piso si no desde un punto mas arriba
        } 
        else // no esta atacando asi que no hace nada
        {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
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
    	hitboxAtaque.setSize(80f, 60f); // hitbox del ataque
        float tiempoTranscurrido = DURACION_TOTAL_NEUTRAL - tiempoAccion;
        boolean enDano = tiempoTranscurrido >= TIEMPO_STARTUP_NEUTRAL && 
                         tiempoTranscurrido <= (TIEMPO_STARTUP_NEUTRAL + TIEMPO_ACTIVO_NEUTRAL); // revisa el momento del ataque
        if (enDano) // el tiempo activo esta activado (pense en otra frase pero es literalmente eso)
        {
            atacando = true;
            float offsetAtaqueX = mirandoDerecha ? ancho : -hitboxAtaque.width; // primero comprueba si esta mirando a la derecha, si lo esta el ancho de la hitbox es normal y si no es el negativo para elegir la direccion. es el coso ese feo de width porque es un atributo que ya esta en rectangle
            hitboxAtaque.setPosition(posX + offsetAtaqueX, posY + (alto / 4)); // el eje x hace que empiece desde la esquina izquierda o derecha segun lo que haya calculado la parte de arriba y el eje Y tiene (alto / 4) para que no empiece exactamente desde el piso si no desde un punto mas arriba
        } 
        else // no esta atacando asi que no hace nada
        {
            atacando = false;
            hitboxAtaque.setPosition(-9999, -9999);
        }
    }

    protected void actualizarEspeciales(float delta) {
        switch (especialActual) // lo saca de procesar acciones
        {
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
    	
        if (tiempoAccion > 0 || inermeEnAire) return; // que no se puedan mover si pasa alguna de las dos 
        velocidadX = 0; // que el personaje pare cuando no se presiona nada

        if (movimientos[0]) // mover izquierda
        {
            velocidadX = -VELOCIDAD_MOVIMIENTO;
            mirandoDerecha = false;
        }
        if (movimientos[1]) // mover derecha
        {
            velocidadX = VELOCIDAD_MOVIMIENTO;
            mirandoDerecha = true;
        }

        // --- LÓGICA DE SALTO Y DOBLE SALTO ---
        boolean quiereSaltar = movimientos[2]; 

        if (quiereSaltar) {
            if (!saltoPresionadoUltimoFrame) // no puede dejar presionado el salto para volar al infinito y mas alla
            { 
                if (enElSuelo) 
                {
                    velocidadY = FUERZA_SALTO;
                    enElSuelo = false;
                    dobleSaltoDisponible = true; 
                } else if (dobleSaltoDisponible) 
                {
                    velocidadY = FUERZA_SALTO * 0.9f; 
                    dobleSaltoDisponible = false;     
                }
            }
            saltoPresionadoUltimoFrame = true; // mientras el salto este activo marca como que no se puede volver a hacer
        } else {
            saltoPresionadoUltimoFrame = false; // vuelve a poder saltar
        }
    }
    
    public void procesarAcciones(boolean[] acciones, boolean[] direccion) 
    {
        if (tiempoAccion > 0 || inermeEnAire) return; // hace que no pueda hacer una accion mientras otra esta en curso o inermeEnAire esta activado

        boolean presionaHorizontal = direccion[0] || direccion[1];
        boolean presionaAbajo      = direccion[3];
        // las posibles posiciones de los ataques

        if (acciones[0]) // ataque normal
        {
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
                    danioAtaque   = DANIO_LATERAL; // si se esta pulsando a alguna direccion hace ese ataque
                } else {
                    ataqueActual  = TipoAtaque.NEUTRAL;
                    estadoActual  = EstadoAnimacion.ATACAR_NEUTRAL;
                    tiempoAccion  = DURACION_TOTAL_NEUTRAL;
                    danioAtaque   = DANIO_NEUTRAL; // si no hace el ataque neutral
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
            
            velocidadX = 0;// hace que el personaje pare
            ataqueProcesado = false; // dice que el ataque conecto. se pone en false para que la computadora no piense que un ataque nuevo ya conecto porque conecto el anterior y que por eso no pueda volver a conectar
            stateTime = 0f; // hace que la animacion siempre empiece en 0f segundos
        } 
        else if (acciones[1] && enElSuelo) // bloqueo
        {
            estadoActual = EstadoAnimacion.BLOQUEAR;
            tiempoAccion = DURACION_BLOQUEO;
            velocidadX = 0;
            stateTime = 0f;
        } 
        else if (acciones[2] && energiaEspecial >= COSTO_BARRA_ESPECIAL) // ataque especial. solo lo hace si hay suficiente especial
        {
            energiaEspecial -= COSTO_BARRA_ESPECIAL; // le resta lo que cuesta el especial, que son 100
            
            if (enElSuelo) {
                if (presionaAbajo) {
                    especialActual = TipoAtaqueEspecial.ESP_ABAJO;
                    estadoActual   = EstadoAnimacion.ESPECIAL_ABAJO;
                    tiempoAccion   = DURACION_TOTAL_ESP_ABAJO;
                } else if (presionaHorizontal) {
                    especialActual = TipoAtaqueEspecial.ESP_LATERAL;
                    estadoActual   = EstadoAnimacion.ESPECIAL_LATERAL;
                    tiempoAccion   = DURACION_TOTAL_ESP_LATERAL;
                } else {
                    especialActual = TipoAtaqueEspecial.ESP_NEUTRAL;
                    estadoActual   = EstadoAnimacion.ESPECIAL_NEUTRAL;
                    tiempoAccion   = DURACION_TOTAL_ESP_NEUTRAL;
                }
            } else {
                if (presionaAbajo) {
                    especialActual = TipoAtaqueEspecial.ESP_AEREO_ABAJO;
                    estadoActual   = EstadoAnimacion.ESPECIAL_AEREO_ABAJO;
                    tiempoAccion   = DURACION_TOTAL_ESP_AEREO_ABAJO;
                } else if (presionaHorizontal) {
                    especialActual = TipoAtaqueEspecial.ESP_AEREO_LATERAL;
                    estadoActual   = EstadoAnimacion.ESPECIAL_AEREO_LATERAL;
                    tiempoAccion   = DURACION_TOTAL_ESP_AEREO_LATERAL; 
                } else {
                    especialActual = TipoAtaqueEspecial.ESP_AEREO_NEUTRAL;
                    estadoActual   = EstadoAnimacion.ESPECIAL_AEREO_NEUTRAL;
                    tiempoAccion   = DURACION_TOTAL_ESP_AEREO_NEUTRAL;
                }
            }
            
            velocidadX = 0;
            ataqueProcesado = false;
            stateTime = 0f;
        }
    }

    
    public void recibirDanio(float danio, float atacanteX) {
            
            if (tiempoInvulnerable > 0f) return; // Si tiene invencibilidad activa por bloqueo exitoso, ignora el golpe

            if (tieneSuperarmor) {
                salud -= danio; // Recibe el daño en la salud
                if (salud < 0) salud = 0;
                return; // Absorbe el golpe: NO cambia de estado, NO cancela el ataque actual
            }

            // Comprobamos si está bloqueando y el golpe entra en el último frame (Parry)
            boolean esUltimoFrameBloqueo = (estadoActual == EstadoAnimacion.BLOQUEAR && tiempoAccion <= 0.20f);
            // ... (resto del método recibirDanio)
            
        if (esUltimoFrameBloqueo) {
            // --- PARRY / BLOQUEO EXITOSO ---
            salud -= danio * 0.05f; 
            float direccion = (posX > atacanteX) ? 1f : -1f;
            velocidadX = direccion * (FUERZA_KNOCKBACK * 0.1f); // a que direccion se empuja. es menos porque lo bloqueo

            // Cancelamos la animación de bloqueo inmediatamente para poder actuar de nuevo
            tiempoAccion = 0f;
            estadoActual = EstadoAnimacion.REPOSO;
            atacando = false;
            ataqueProcesado = false;

            // Otorgamos 0.5 segundos de invencibilidad para contratacar
            tiempoInvulnerable = 0.5f;
        } else {
            // --- DAÑO NORMAL ---
            salud -= danio;
            estadoActual = EstadoAnimacion.RECIBIR_DANIO;
            tiempoAccion = 0.25f;
            stateTime = 0f;

            float direccion = (posX > atacanteX) ? 1f : -1f;
            velocidadX = direccion * FUERZA_KNOCKBACK; // empuje
        }

        if (salud < 0) 
        	salud = 0;
    }

    public void recibirDanio(float danio) {
        recibirDanio(danio, posX);
        // sobrecarga de funciones
    }

    
    private void resolverColisiones(Array<Plataforma> plataformas, float anchoPantalla) {
        enElSuelo = false;

        if (plataformas != null) // comprueba que hayan plataformas
        {
            for (Plataforma plataforma : plataformas) // revisa las plataformas una por una
            {
                plataforma.verificarColision(this); // pone la colision. this se refiere al objeto, en este caso el personaje
            }
        }

        if (enElSuelo) 
        {
            dobleSaltoDisponible = false; 

            if (inermeEnAire) {
                inermeEnAire = false; // El personaje ya no está inerme
                tiempoAccion = 0f;    // Reseteamos el temporizador de aturdimiento
                estadoActual = EstadoAnimacion.REPOSO; // Forzamos el estado visual a reposo
                stateTime = 0f;       // Reiniciamos el tiempo de la animación
            }
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
        // que no se pueda escapar de la pantalla por los lados

        if (posY < -150f) {
            salud = 0f;
        }
        // si se cae se muere :O
    }
    
    
    public void actualizarEfectos(float delta) 
    {// sirve para que los efectos puedan terminar. como se usa en actualizar funciona cada frame
        if (tiempoDebuffProyectil > 0f) // si el debuff esta activo
        {
            tiempoDebuffProyectil -= delta; // delta es el tiempo transcurrido
        }
        if (tiempoInvulnerable > 0f) // si tiene invencibilidad despues de un parry esta activo
        {
            tiempoInvulnerable -= delta;
        }
    } 
    public void aplicarDebuffProyectil(float segundos) {
        this.tiempoDebuffProyectil = segundos; // setea el tiempo del debuff en segundos para hacer lo de arriba y activar el debuff
    }
    public boolean tieneDebuffProyectil() {
        return tiempoDebuffProyectil > 0f; // instancia que tiene el debuff activado cuando el tiempo es mayor a 0. da un verdadero o falso segun la respuesta de la operacion.
    }
    private void determinarEstado() {
        if (inermeEnAire) {
            if (estadoActual != EstadoAnimacion.RECIBIR_DANIO) {
                estadoActual = EstadoAnimacion.RECIBIR_DANIO;
                stateTime = 0f; // pone la animacion de recibir daño mientras esta el inerme
            }
            return;
        }

        if (tiempoAccion <= 0 && estadoActual != EstadoAnimacion.RECIBIR_DANIO) {
            EstadoAnimacion nuevoEstado = estadoActual;

            if (!enElSuelo) {
                if (velocidadY > 0) {
                    nuevoEstado = EstadoAnimacion.SALTAR;
                } else {
                    nuevoEstado = EstadoAnimacion.CAER;
                }
            } else {
                if (Math.abs(velocidadX) > 0.1f) { // Math.abs hace que los numeros negativos pasen a positivos
                    nuevoEstado = EstadoAnimacion.CAMINAR;
                } else {
                    nuevoEstado = EstadoAnimacion.REPOSO;
                }
            }

            if (estadoActual != nuevoEstado) {
                estadoActual = nuevoEstado;
                stateTime = 0f;
            }
        }
    }
    
    public void dibujar(SpriteBatch batch) { // dibuja al personaje en todo momento
        TextureRegion frameActual = GestorRecursosPersonajes.obtenerFrame(ave, estadoActual, stateTime); // pide lo que esta aca a la izquierda

        if (frameActual == null) {
            System.out.println("ERROR DIBUJO: No existe la clave '" + ave + "' o el estado '" + estadoActual + "' en el Gestor."); // por si no existe la textura que no explote todo
        } else {
            if ((mirandoDerecha && frameActual.isFlipX()) || (!mirandoDerecha && !frameActual.isFlipX())) {
                frameActual.flip(true, false); // la textura para que mire a donde tiene que mirar
            }

            if (tieneDebuffProyectil()) {
                batch.setColor(Color.FIREBRICK); // pone el color del debuff
            } else {
                batch.setColor(Color.WHITE); // white es lo mismo que nada (como me discriminan)
            }

            batch.draw(frameActual, posX, posY, ancho, alto);
            batch.setColor(Color.WHITE);
        }
    }
    
    public void recibirAtaqueAereoEspecialKiri(float danio, float fuerzaSaltoY) { // da un saltito cuando recibe los ataques especiales neutrales de kiri y deja el estado de inerme
        this.salud -= danio;
        if (this.salud < 0) this.salud = 0;
        
        this.enElSuelo = false;
        this.velocidadY = fuerzaSaltoY; 
        this.velocidadX = 0;
        this.inermeEnAire = true; 
    }

    public float getPosX() { return posX; }
    public float getPosY() { return posY; }
    public float getVelocidadY() {return velocidadY;}
    public void setPosX(float posX) { this.posX = posX; this.hitbox.x = posX; }
    public void setPosY(float posY) { this.posY = posY; this.hitbox.y = posY; }
    public void setVelocidadY(float velocidadY) {this.velocidadY = velocidadY;}
    public void setEnElSuelo(boolean enElSuelo) { this.enElSuelo = enElSuelo; }
    public float getSalud() { return salud; }
    public float getSaludMaxima() { return saludMaxima; }  
    public float getPorcentajeSalud() {
        if (saludMaxima <= 0) return 0f;
        return Math.min(1f, Math.max(0f, salud / saludMaxima));
    }
    public float getAncho() { return ancho; }
    public float getAlto() { return alto; }
    public Rectangle getHitbox() { return hitbox; }
    public Rectangle getHitboxAtaque() { return hitboxAtaque; }
    public boolean isAtacando() { return atacando; }
    public boolean isAtaqueProcesado() { return ataqueProcesado; }
    public void setAtaqueProcesado(boolean procesado) { this.ataqueProcesado = procesado; }
    public float getDanioAtaque() { return danioAtaque; }
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
    public TipoAtaqueEspecial getEspecialActual() {
        return especialActual;
    }  
}