package recursos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;

public class GestorRecursosPersonajes {

    public enum EstadoAnimacion {// todos los estados posibles
        REPOSO,
        CAMINAR,
        SALTAR,
        SALTO_DOBLE,
        CAER,
        ATACAR_NEUTRAL,
        ATACAR_LATERAL,
        ATACAR_ABAJO,
        ATACAR_AEREO_NEUTRAL,
        ATACAR_AEREO_LATERAL,
        ATACAR_AEREO_ABAJO,
        ESPECIAL_NEUTRAL,
        ESPECIAL_LATERAL,
        ESPECIAL_ABAJO,
        ESPECIAL_AEREO_NEUTRAL,
        ESPECIAL_AEREO_LATERAL,
        ESPECIAL_AEREO_ABAJO,
        BLOQUEAR,
        ESPECIAL,
        RECIBIR_DANIO
    }

    private static ObjectMap<String, ObjectMap<EstadoAnimacion, Animation<TextureRegion>>> animacionesPersonajes = new ObjectMap<>(); // sirve para tener cargadas todas las animaciones del personaje incluso antes de que se utilicen
    // string identifica el personaje, EstadoAnimacion es el EstadoAnimacion y TextureRegion es los frames que tiene cada animacion

  
    public static void cargarPersonaje(String nombrePersonaje, String carpeta) { // carga todas las animaciones de los personajes en su mapa
        ObjectMap<EstadoAnimacion, ConfigAnimacion> configs = new ObjectMap<>();

        // Normalizamos el nombre a minúsculas para evitar errores
        String id = nombrePersonaje.toLowerCase();

        
        
        if (id.equals("kiri")) {
            // --- CONFIGURACIÓN ESPECÍFICA DE KIRI ---
        	// todas las animaciones separadas por personajes
            configs.put(EstadoAnimacion.REPOSO,        new ConfigAnimacion("reposo.png", 0.15f, Animation.PlayMode.LOOP));// pone la animacion en el mapa del personaje con el nombre del estado. para poner la animacion crea un nuevo configAnimation
            configs.put(EstadoAnimacion.CAMINAR,       new ConfigAnimacion("caminar.png", 0.10f, Animation.PlayMode.LOOP));// Animation.PlayMode.LOOP hace que se repita
            configs.put(EstadoAnimacion.BLOQUEAR,      new ConfigAnimacion("bloquear.png", 0.10f, Animation.PlayMode.NORMAL,3,1)); //Animation.PlayMode.NORMAL hace que no se repita
            configs.put(EstadoAnimacion.RECIBIR_DANIO, new ConfigAnimacion("dano.png", 0.10f, Animation.PlayMode.NORMAL,5,2));// el primer coso es el archivo que va a buscar
            configs.put(EstadoAnimacion.SALTAR, new ConfigAnimacion("saltar.png", 0.01f, Animation.PlayMode.NORMAL, 9, 2)); // el segundo coso es la velocidad a la que cambian los cuadros (en segundos)
            configs.put(EstadoAnimacion.CAER,   new ConfigAnimacion("caer.png", 0.10f, Animation.PlayMode.LOOP, 8, 1));// los ultimos dos numeros son las columnas y las filas respectivamente

            // Ataques en grilla de Kiri (x columnas, x filas)
            configs.put(EstadoAnimacion.ATACAR_NEUTRAL,       new ConfigAnimacion("ataque_neutral.png", 0.03f, Animation.PlayMode.NORMAL, 5, 2));
            configs.put(EstadoAnimacion.ATACAR_LATERAL,       new ConfigAnimacion("ataque_lateral.png", 0.05f, Animation.PlayMode.NORMAL, 9, 2));
            configs.put(EstadoAnimacion.ATACAR_ABAJO,         new ConfigAnimacion("ataque_abajo.png", 0.04f, Animation.PlayMode.NORMAL, 10, 2));
            
            configs.put(EstadoAnimacion.ATACAR_AEREO_NEUTRAL, new ConfigAnimacion("ataque_neutral_aereo.png", 0.05f, Animation.PlayMode.NORMAL, 8, 1));
            configs.put(EstadoAnimacion.ATACAR_AEREO_LATERAL, new ConfigAnimacion("ataque_lateral_aereo.png", 0.03f, Animation.PlayMode.NORMAL, 9, 2));
            configs.put(EstadoAnimacion.ATACAR_AEREO_ABAJO,   new ConfigAnimacion("ataque_abajo_aereo.png", 0.05f, Animation.PlayMode.NORMAL, 10, 2));
            
            configs.put(EstadoAnimacion.ESPECIAL_NEUTRAL,     new ConfigAnimacion("especial_neutral.png", 0.03f, Animation.PlayMode.NORMAL, 5, 2));
            configs.put(EstadoAnimacion.ESPECIAL_LATERAL,     new ConfigAnimacion("especial_lateral.png", 0.05f, Animation.PlayMode.NORMAL, 6, 2));
            configs.put(EstadoAnimacion.ESPECIAL_ABAJO,       new ConfigAnimacion("especial_abajo.png", 0.05f, Animation.PlayMode.NORMAL, 6, 2));
            
            configs.put(EstadoAnimacion.ESPECIAL_AEREO_NEUTRAL, new ConfigAnimacion("especial_neutral_aereo.png", 0.08f, Animation.PlayMode.NORMAL, 8, 1));
            configs.put(EstadoAnimacion.ESPECIAL_AEREO_LATERAL, new ConfigAnimacion("especial_lateral_aereo.png", 0.02f, Animation.PlayMode.NORMAL, 8, 2));
            configs.put(EstadoAnimacion.ESPECIAL_AEREO_ABAJO,   new ConfigAnimacion("especial_abajo_aereo.png", 0.05f, Animation.PlayMode.NORMAL, 9, 2));
        }
        else if (id.equals("emuans")) {
            // --- CONFIGURACIÓN ESPECÍFICA DE EMUANS ---
            configs.put(EstadoAnimacion.REPOSO,        new ConfigAnimacion("reposo.png", 0.15f, Animation.PlayMode.LOOP));
            configs.put(EstadoAnimacion.CAMINAR,       new ConfigAnimacion("caminar.png", 0.10f, Animation.PlayMode.LOOP));
            configs.put(EstadoAnimacion.BLOQUEAR,      new ConfigAnimacion("bloquear.png", 0.05f, Animation.PlayMode.NORMAL, 4, 1));
            configs.put(EstadoAnimacion.RECIBIR_DANIO, new ConfigAnimacion("dano.png", 0.10f, Animation.PlayMode.NORMAL, 6, 2));
            configs.put(EstadoAnimacion.SALTAR, new ConfigAnimacion("saltar.png", 0.04f, Animation.PlayMode.NORMAL, 7, 1));
            configs.put(EstadoAnimacion.SALTO_DOBLE,   new ConfigAnimacion("salto_doble.png", 0.04f, Animation.PlayMode.NORMAL, 6, 1)); // <--- Animación de doble salto especial de emuans
            configs.put(EstadoAnimacion.CAER,   new ConfigAnimacion("caer.png", 0.10f, Animation.PlayMode.LOOP, 1, 1));

            // Ataques normales terrestres (ajusta las columnas y filas según tus spritesheets)
            configs.put(EstadoAnimacion.ATACAR_NEUTRAL,       new ConfigAnimacion("ataque_neutral.png", 0.03f, Animation.PlayMode.NORMAL, 8, 1));
            configs.put(EstadoAnimacion.ATACAR_LATERAL,       new ConfigAnimacion("ataque_lateral.png", 0.05f, Animation.PlayMode.NORMAL, 7, 2));
            configs.put(EstadoAnimacion.ATACAR_ABAJO,         new ConfigAnimacion("ataque_abajo.png", 0.8f, Animation.PlayMode.NORMAL, 7, 1));
            
            // Ataques normales aéreos
            configs.put(EstadoAnimacion.ATACAR_AEREO_NEUTRAL, new ConfigAnimacion("ataque_neutral_aereo.png", 0.05f, Animation.PlayMode.NORMAL, 6, 2));
            configs.put(EstadoAnimacion.ATACAR_AEREO_LATERAL, new ConfigAnimacion("ataque_lateral_aereo.png", 0.03f, Animation.PlayMode.NORMAL, 7, 2));
            configs.put(EstadoAnimacion.ATACAR_AEREO_ABAJO,   new ConfigAnimacion("ataque_abajo_aereo.png", 0.05f, Animation.PlayMode.NORMAL, 8, 2));
            
            // Ataques especiales terrestres
            configs.put(EstadoAnimacion.ESPECIAL_NEUTRAL,     new ConfigAnimacion("especial_neutral.png", 0.03f, Animation.PlayMode.NORMAL, 8, 1));
            configs.put(EstadoAnimacion.ESPECIAL_LATERAL,     new ConfigAnimacion("especial_lateral.png", 0.05f, Animation.PlayMode.NORMAL, 7, 2));
            configs.put(EstadoAnimacion.ESPECIAL_ABAJO,       new ConfigAnimacion("especial_abajo.png", 0.05f, Animation.PlayMode.NORMAL, 9, 2));
            
            // Ataques especiales aéreos
            configs.put(EstadoAnimacion.ESPECIAL_AEREO_NEUTRAL, new ConfigAnimacion("especial_neutral_aereo.png", 0.08f, Animation.PlayMode.NORMAL, 8, 1));
            configs.put(EstadoAnimacion.ESPECIAL_AEREO_LATERAL, new ConfigAnimacion("especial_lateral_aereo.png", 0.02f, Animation.PlayMode.NORMAL, 7, 2));
            configs.put(EstadoAnimacion.ESPECIAL_AEREO_ABAJO,   new ConfigAnimacion("especial_abajo_aereo.png", 0.03f, Animation.PlayMode.NORMAL, 7, 2));
        }
        
        else if (id.equals("corv")) {
            // --- CONFIGURACIÓN ESPECÍFICA DE EMUANS ---
            configs.put(EstadoAnimacion.REPOSO,        new ConfigAnimacion("reposo.png", 0.15f, Animation.PlayMode.LOOP));
            configs.put(EstadoAnimacion.CAMINAR,       new ConfigAnimacion("caminar.png", 0.05f, Animation.PlayMode.LOOP));
            configs.put(EstadoAnimacion.BLOQUEAR,      new ConfigAnimacion("bloquear.png", 0.05f, Animation.PlayMode.NORMAL, 4, 1));
            configs.put(EstadoAnimacion.RECIBIR_DANIO, new ConfigAnimacion("dano.png", 0.10f, Animation.PlayMode.NORMAL, 3, 1));
            configs.put(EstadoAnimacion.SALTAR, new ConfigAnimacion("saltar.png", 0.04f, Animation.PlayMode.NORMAL, 4, 1));
            configs.put(EstadoAnimacion.CAER,   new ConfigAnimacion("caer.png", 0.10f, Animation.PlayMode.LOOP, 1, 1));

            // Ataques normales terrestres (ajusta las columnas y filas según tus spritesheets)
            configs.put(EstadoAnimacion.ATACAR_NEUTRAL,       new ConfigAnimacion("ataque_neutral.png", 0.07f, Animation.PlayMode.NORMAL, 8, 1));
            configs.put(EstadoAnimacion.ATACAR_LATERAL,       new ConfigAnimacion("ataque_lateral.png", 0.15f, Animation.PlayMode.NORMAL, 8, 1));
            configs.put(EstadoAnimacion.ATACAR_ABAJO,         new ConfigAnimacion("ataque_abajo.png", 0.04f, Animation.PlayMode.NORMAL, 10, 2));
            
            // Ataques normales aéreos
            configs.put(EstadoAnimacion.ATACAR_AEREO_NEUTRAL, new ConfigAnimacion("ataque_neutral_aereo.png", 0.05f, Animation.PlayMode.NORMAL, 6, 2));
            configs.put(EstadoAnimacion.ATACAR_AEREO_LATERAL, new ConfigAnimacion("ataque_lateral_aereo.png", 0.03f, Animation.PlayMode.NORMAL, 7, 2));
            configs.put(EstadoAnimacion.ATACAR_AEREO_ABAJO,   new ConfigAnimacion("ataque_abajo_aereo.png", 0.04f, Animation.PlayMode.NORMAL, 10, 2));
            
            // Ataques especiales terrestres
            configs.put(EstadoAnimacion.ESPECIAL_NEUTRAL,     new ConfigAnimacion("especial_neutral.png", 0.03f, Animation.PlayMode.NORMAL, 8, 1));
            configs.put(EstadoAnimacion.ESPECIAL_LATERAL,     new ConfigAnimacion("especial_lateral.png", 0.03f, Animation.PlayMode.NORMAL, 8, 1));
            configs.put(EstadoAnimacion.ESPECIAL_ABAJO,       new ConfigAnimacion("especial_abajo.png", 0.05f, Animation.PlayMode.NORMAL, 11, 2));
            
            // Ataques especiales aéreos
            configs.put(EstadoAnimacion.ESPECIAL_AEREO_NEUTRAL, new ConfigAnimacion("especial_neutral_aereo.png", 0.08f, Animation.PlayMode.NORMAL, 6, 2));
            configs.put(EstadoAnimacion.ESPECIAL_AEREO_LATERAL, new ConfigAnimacion("especial_lateral_aereo.png", 0.02f, Animation.PlayMode.NORMAL, 9, 2));
            configs.put(EstadoAnimacion.ESPECIAL_AEREO_ABAJO,   new ConfigAnimacion("especial_abajo_aereo.png", 0.03f, Animation.PlayMode.NORMAL, 11, 2));
        }
        
        

        // Llamamos al método interno que procesa las texturas y recortes
        cargarPersonaje(nombrePersonaje, carpeta, configs);
    }
    
    /**
     * Carga las animaciones de un personaje usando la configuración específica (columnas y filas).
     */
    public static void cargarPersonaje(String nombrePersonaje, String carpeta, ObjectMap<EstadoAnimacion, ConfigAnimacion> configs) {
        
    	ObjectMap<EstadoAnimacion, Animation<TextureRegion>> mapaAnimaciones = new ObjectMap<>(); // crea un mapa vacio que guarda todas las animaciones
        String rutaBase = (carpeta.endsWith("/") || carpeta.isEmpty()) ? carpeta : carpeta + "/"; // busca la carpeta del personaje en cuestion

        
        for (EstadoAnimacion estado : EstadoAnimacion.values()) // recorre todos los estados
        
        {
            if (configs.containsKey(estado)) {// si el jugador tiene una configuracion escrita para este estado. si hay un personaje sin una animacion como podria ser el segundo salto que solo tiene emuans no pasa nada por esto
                ConfigAnimacion config = configs.get(estado); // extrae el paquete de datos
                Animation<TextureRegion> anim = cargarAnimacionAutodetectada( // recorta la animacion en cuadros
                    rutaBase + config.nombreArchivo, // valores que va a tener el nuevo objeto de configAnimation
                    config.duracionFrame, // envia todos estos datos a cargarAnimacionAutodetectada para que haga su magia
                    config.modo,
                    config.columnas,
                    config.filas
                );
                if (anim != null) { // guarda la animacion en el mapa vacio si se genero correctamente. lo que se guarda son los objetos
                    mapaAnimaciones.put(estado, anim);
                }
            }
        }
        // este es un mapa de mapas que guarda el resto de los mapas de los personajes
        animacionesPersonajes.put(nombrePersonaje, mapaAnimaciones); // guarda las animaciones con el nombre del personaje y su mapa de animaciones
    }

    

    private static Animation<TextureRegion> cargarAnimacionAutodetectada(String rutaArchivo, float frameDuration, Animation.PlayMode mode, int columnasConfig, int filasConfig) { // recorta las texturas para volverlas animaciones
        if (!Gdx.files.internal(rutaArchivo).exists()) {// si el archivo no existe
            System.out.println("ADVERTENCIA: No se encontró el archivo en -> " + Gdx.files.internal(rutaArchivo).file().getAbsolutePath());
            return null;
        }

        Texture textura = new Texture(Gdx.files.internal(rutaArchivo)); // crea un tipo de dato que puede buscar un archivo de textura
        textura.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest); // hace que los pixeles mantengan su forma sin importar el tamaño

        int anchoTotal = textura.getWidth();
        int altoTotal = textura.getHeight();

        int filas = filasConfig;
        int columnas = columnasConfig;

        // Si columnas es 0 o menor, autocalcula asumiendo una tira horizontal de 1 sola fila
        if (columnas <= 0) {
            filas = 1;
            columnas = anchoTotal / altoTotal;
            if (columnas < 1) columnas = 1;
        }

        int anchoFrame = anchoTotal / columnas;
        int altoFrame = altoTotal / filas;

        // textureRegion es un tipo de dato que hace algo muy raro y complejo que no creo poder explicar en toda la falcultad que merece, pero separa la textura en regiones y las guarda
        TextureRegion[][] tmp = TextureRegion.split(textura, anchoFrame, altoFrame); // split es algo de libgdx que divide una imagen segun los parametros que le mandes
        // como es un vector guarda un frame en cada espacio
        
        TextureRegion[] frames = new TextureRegion[filas * columnas]; // calcula todos los frames de la cosa esta
        int index = 0; // el fotograma actual
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                TextureRegion reg = tmp[i][j]; // guarda el fotograma que toque
                
                // --- RECORTE INTERNO (INSET) ---
                // Evita que se filtren los bordes o espacios vacíos entre frames vecinos
                int margenInterno = 1; 
                if (anchoFrame > margenInterno * 2 && altoFrame > margenInterno * 2) {
                    reg.setRegion(
                        reg.getRegionX() + margenInterno,
                        reg.getRegionY() + margenInterno,
                        reg.getRegionWidth() - (margenInterno * 2),
                        reg.getRegionHeight() - (margenInterno * 2)
                    ); // todo esto evita que se formen lineas feas
                }
                
                frames[index++] = reg;
            }
        }

        Animation<TextureRegion> animacion = new Animation<>(frameDuration, frames); // crea un objeto de animacion. esta clase es nativa de libgdx
        animacion.setPlayMode(mode);// setea el modo que va a tener
        return animacion;// devuelve la animacion terminada
    }

    public static TextureRegion obtenerFrame(String nombrePersonaje, EstadoAnimacion estado, float stateTime) {
        if (animacionesPersonajes.containsKey(nombrePersonaje)) {// si la etiqueta con el nombre del personaje realmente existe guardada. Si no existe, se salta todo y devuelve null
            ObjectMap<EstadoAnimacion, Animation<TextureRegion>> mapa = animacionesPersonajes.get(nombrePersonaje); // extrae el mapa del personaje
            if (mapa != null && mapa.containsKey(estado) && mapa.get(estado) != null) {// se asegura de que el mapa tenga la animacion, que el mapa exista y que no este vacio
                return mapa.get(estado).getKeyFrame(stateTime);// Si todas las validaciones son exitosas, entra al objeto Animation de ese estado y ejecuta el método nativo getKeyFrame(stateTime). Este método calcula, basándose en la duración de cada cuadro, qué imagen exacta corresponde mostrar según el tiempo transcurrido (stateTime), y la devuelve inmediatamente
            }
        }
        return null;
    }

    public static void dispose() {
        if (animacionesPersonajes != null) {
            animacionesPersonajes.clear();
        }
    }
}