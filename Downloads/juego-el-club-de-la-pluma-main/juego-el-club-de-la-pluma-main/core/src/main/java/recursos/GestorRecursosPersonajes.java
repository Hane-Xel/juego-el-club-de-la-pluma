package recursos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;

public class GestorRecursosPersonajes {

    public enum EstadoAnimacion {
        REPOSO,
        CAMINAR,
        ATACAR_NEUTRAL,
        ATACAR_LATERAL,
        ATACAR_ABAJO,
        ATACAR_AEREO_NEUTRAL,
        ATACAR_AEREO_LATERAL,
        ATACAR_AEREO_ABAJO,
        // --- ATAQUES ESPECIALES CONFIGURADOS ---
        ESPECIAL_NEUTRAL,
        ESPECIAL_LATERAL,
        ESPECIAL_ABAJO,
        ESPECIAL_AEREO_NEUTRAL,
        ESPECIAL_AEREO_LATERAL,
        ESPECIAL_AEREO_ABAJO,
        // ---------------------------------------
        BLOQUEAR,
        ESPECIAL,
        RECIBIR_DANIO
    }

    private static ObjectMap<String, ObjectMap<EstadoAnimacion, Animation<TextureRegion>>> animacionesPersonajes = new ObjectMap<>();

    /**
     * Carga las animaciones de un personaje usando la configuración específica (columnas y filas).
     */
    public static void cargarPersonaje(String nombrePersonaje, String carpeta, ObjectMap<EstadoAnimacion, ConfigAnimacion> configs) {
        ObjectMap<EstadoAnimacion, Animation<TextureRegion>> mapaAnimaciones = new ObjectMap<>();
        String rutaBase = (carpeta.endsWith("/") || carpeta.isEmpty()) ? carpeta : carpeta + "/";

        for (EstadoAnimacion estado : EstadoAnimacion.values()) {
            if (configs.containsKey(estado)) {
                ConfigAnimacion config = configs.get(estado);
                Animation<TextureRegion> anim = cargarAnimacionAutodetectada(
                    rutaBase + config.nombreArchivo, 
                    config.duracionFrame, 
                    config.modo,
                    config.columnas,
                    config.filas
                );
                if (anim != null) {
                    mapaAnimaciones.put(estado, anim);
                }
            }
        }

        animacionesPersonajes.put(nombrePersonaje, mapaAnimaciones);
    }

    /**
     * Sobrecarga genérica con tiempos y configuraciones por defecto (ideal para prototipos o personajes de 1 sola fila).
     */
    /**
     * Método universal. El selector de personajes solo llamará a esto 
     * sin importar qué personaje se elija.
     */
    public static void cargarPersonaje(String nombrePersonaje, String carpeta) {
        ObjectMap<EstadoAnimacion, ConfigAnimacion> configs = new ObjectMap<>();

        // Normalizamos el nombre a minúsculas para evitar errores
        String id = nombrePersonaje.toLowerCase();

        
        
        if (id.equals("kiri")) {
            // --- CONFIGURACIÓN ESPECÍFICA DE KIRI ---
            configs.put(EstadoAnimacion.REPOSO,        new ConfigAnimacion("reposo.png", 0.15f, Animation.PlayMode.LOOP));
            configs.put(EstadoAnimacion.CAMINAR,       new ConfigAnimacion("caminar.png", 0.10f, Animation.PlayMode.LOOP));
            configs.put(EstadoAnimacion.BLOQUEAR,      new ConfigAnimacion("bloquear.png", 0.20f, Animation.PlayMode.NORMAL,3,1));
            configs.put(EstadoAnimacion.RECIBIR_DANIO, new ConfigAnimacion("dano.png", 0.10f, Animation.PlayMode.NORMAL,5,2));

            // Ataques en grilla de Kiri (5 columnas, 2 filas)
            configs.put(EstadoAnimacion.ATACAR_NEUTRAL,       new ConfigAnimacion("ataque_neutral.png", 0.03f, Animation.PlayMode.NORMAL, 5, 2));
            configs.put(EstadoAnimacion.ATACAR_LATERAL,       new ConfigAnimacion("ataque_lateral.png", 0.10f, Animation.PlayMode.NORMAL, 9, 2));
            configs.put(EstadoAnimacion.ATACAR_ABAJO,         new ConfigAnimacion("ataque_abajo.png", 0.04f, Animation.PlayMode.NORMAL, 10, 2));
            
            configs.put(EstadoAnimacion.ATACAR_AEREO_NEUTRAL, new ConfigAnimacion("ataque_neutral_aereo.png", 0.05f, Animation.PlayMode.NORMAL, 8, 1));
            configs.put(EstadoAnimacion.ATACAR_AEREO_LATERAL, new ConfigAnimacion("ataque_lateral_aereo.png", 0.03f, Animation.PlayMode.NORMAL, 9, 2));
            configs.put(EstadoAnimacion.ATACAR_AEREO_ABAJO,   new ConfigAnimacion("ataque_abajo_aereo.png", 0.05f, Animation.PlayMode.NORMAL, 10, 2));
            
            configs.put(EstadoAnimacion.ESPECIAL_NEUTRAL,     new ConfigAnimacion("especial_neutral.png", 0.03f, Animation.PlayMode.NORMAL, 5, 2));
            configs.put(EstadoAnimacion.ESPECIAL_LATERAL,     new ConfigAnimacion("especial_lateral.png", 0.10f, Animation.PlayMode.NORMAL, 6, 2));
            configs.put(EstadoAnimacion.ESPECIAL_ABAJO,       new ConfigAnimacion("especial_abajo.png", 0.05f, Animation.PlayMode.NORMAL, 9, 2));
            
            configs.put(EstadoAnimacion.ESPECIAL_AEREO_NEUTRAL, new ConfigAnimacion("especial_neutral_aereo.png", 0.08f, Animation.PlayMode.NORMAL, 8, 1));
            configs.put(EstadoAnimacion.ESPECIAL_AEREO_LATERAL, new ConfigAnimacion("especial_lateral_aereo.png", 0.02f, Animation.PlayMode.NORMAL, 8, 2));
            configs.put(EstadoAnimacion.ESPECIAL_AEREO_ABAJO,   new ConfigAnimacion("especial_abajo_aereo.png", 0.03f, Animation.PlayMode.NORMAL, 9, 2));
        } else if (id.equals("ave1")) {
            // --- CONFIGURACIÓN DE AVE1 ---
            configs.put(EstadoAnimacion.REPOSO,        new ConfigAnimacion("reposo.png", 0.15f, Animation.PlayMode.LOOP));
            configs.put(EstadoAnimacion.CAMINAR,       new ConfigAnimacion("caminar.png", 0.10f, Animation.PlayMode.LOOP));
            configs.put(EstadoAnimacion.BLOQUEAR,      new ConfigAnimacion("bloquear.png", 0.20f, Animation.PlayMode.NORMAL, 3, 1));
            
            // ---> AÑADE ESTA LÍNEA PARA QUE RECONOZCA EL DAÑO <---
            // (Ajusta las columnas y filas según cómo tengas la imagen "dano.png" en la carpeta de ave1, ej: 1, 1 o las que uses)
            configs.put(EstadoAnimacion.RECIBIR_DANIO, new ConfigAnimacion("dano.png", 0.10f, Animation.PlayMode.NORMAL, 1, 1));

            configs.put(EstadoAnimacion.ATACAR_NEUTRAL, new ConfigAnimacion("ataque_neutral.png", 0.08f, Animation.PlayMode.NORMAL));
            
        }
        
        

        // Llamamos al método interno que procesa las texturas y recortes
        cargarPersonaje(nombrePersonaje, carpeta, configs);
    }

    private static void configsPorDefectorSiExiste(ObjectMap<EstadoAnimacion, ConfigAnimacion> mapa, EstadoAnimacion estado, String archivo, float duracion, int col, int fil) {
        mapa.put(estado, new ConfigAnimacion(archivo, duracion, Animation.PlayMode.NORMAL, col, fil));
    }

    /**
     * Método central de recorte con filtro Nearest y margen interno (inset) para evitar bleeding.
     */
    private static Animation<TextureRegion> cargarAnimacionAutodetectada(String rutaArchivo, float frameDuration, Animation.PlayMode mode, int columnasConfig, int filasConfig) {
        if (!Gdx.files.internal(rutaArchivo).exists()) {
            System.out.println("ADVERTENCIA: No se encontró el archivo en -> " + Gdx.files.internal(rutaArchivo).file().getAbsolutePath());
            return null;
        }

        Texture textura = new Texture(Gdx.files.internal(rutaArchivo));
        textura.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

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

        TextureRegion[][] tmp = TextureRegion.split(textura, anchoFrame, altoFrame);

        TextureRegion[] frames = new TextureRegion[filas * columnas];
        int index = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                TextureRegion reg = tmp[i][j];
                
                // --- RECORTE INTERNO (INSET) ---
                // Evita que se filtren los bordes o espacios vacíos entre frames vecinos
                int margenInterno = 1; 
                if (anchoFrame > margenInterno * 2 && altoFrame > margenInterno * 2) {
                    reg.setRegion(
                        reg.getRegionX() + margenInterno,
                        reg.getRegionY() + margenInterno,
                        reg.getRegionWidth() - (margenInterno * 2),
                        reg.getRegionHeight() - (margenInterno * 2)
                    );
                }
                
                frames[index++] = reg;
            }
        }

        Animation<TextureRegion> animacion = new Animation<>(frameDuration, frames);
        animacion.setPlayMode(mode);
        return animacion;
    }

    public static TextureRegion obtenerFrame(String nombrePersonaje, EstadoAnimacion estado, float stateTime) {
        if (animacionesPersonajes.containsKey(nombrePersonaje)) {
            ObjectMap<EstadoAnimacion, Animation<TextureRegion>> mapa = animacionesPersonajes.get(nombrePersonaje);
            if (mapa != null && mapa.containsKey(estado) && mapa.get(estado) != null) {
                return mapa.get(estado).getKeyFrame(stateTime);
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