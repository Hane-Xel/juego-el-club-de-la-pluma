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
     * Carga las animaciones de un personaje con tiempos personalizados
     * calculando AUTOMÁTICAMENTE filas y columnas según la textura.
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
                    config.modo
                );
                if (anim != null) {
                    mapaAnimaciones.put(estado, anim);
                }
            }
        }

        animacionesPersonajes.put(nombrePersonaje, mapaAnimaciones);
    }

    /**
     * Sobrecarga con tiempos por defecto (sirve para "ave1" y prototipos).
     */
    public static void cargarPersonaje(String nombrePersonaje, String carpeta) {
        ObjectMap<EstadoAnimacion, ConfigAnimacion> configsPorDefecto = new ObjectMap<>();

        configsPorDefecto.put(EstadoAnimacion.REPOSO,               new ConfigAnimacion("reposo.png", 0.15f, Animation.PlayMode.LOOP));
        configsPorDefecto.put(EstadoAnimacion.CAMINAR,              new ConfigAnimacion("caminar.png", 0.10f, Animation.PlayMode.LOOP));
        
        // Ataques Normales de Tierra
        configsPorDefecto.put(EstadoAnimacion.ATACAR_NEUTRAL,       new ConfigAnimacion("ataque_neutral.png", 0.08f, Animation.PlayMode.NORMAL));
        configsPorDefecto.put(EstadoAnimacion.ATACAR_LATERAL,       new ConfigAnimacion("ataque_lateral.png", 0.10f, Animation.PlayMode.NORMAL));
        configsPorDefecto.put(EstadoAnimacion.ATACAR_ABAJO,         new ConfigAnimacion("ataque_abajo.png", 0.09f, Animation.PlayMode.NORMAL));
        
        // Ataques Normales de Aire
        configsPorDefecto.put(EstadoAnimacion.ATACAR_AEREO_NEUTRAL, new ConfigAnimacion("ataque_neutral_aereo.png", 0.08f, Animation.PlayMode.NORMAL));
        configsPorDefecto.put(EstadoAnimacion.ATACAR_AEREO_LATERAL, new ConfigAnimacion("ataque_lateral_aereo.png", 0.10f, Animation.PlayMode.NORMAL));
        configsPorDefecto.put(EstadoAnimacion.ATACAR_AEREO_ABAJO,   new ConfigAnimacion("ataque_abajo_aereo.png", 0.09f, Animation.PlayMode.NORMAL));

        // Ataques Especiales de Tierra
        configsPorDefecto.put(EstadoAnimacion.ESPECIAL_NEUTRAL,     new ConfigAnimacion("especial_neutral.png", 0.08f, Animation.PlayMode.NORMAL));
        configsPorDefecto.put(EstadoAnimacion.ESPECIAL_LATERAL,     new ConfigAnimacion("especial_lateral.png", 0.10f, Animation.PlayMode.NORMAL));
        configsPorDefecto.put(EstadoAnimacion.ESPECIAL_ABAJO,       new ConfigAnimacion("especial_abajo.png", 0.09f, Animation.PlayMode.NORMAL));

        // Ataques Especiales de Aire
        configsPorDefecto.put(EstadoAnimacion.ESPECIAL_AEREO_NEUTRAL, new ConfigAnimacion("especial_neutral_aereo.png", 0.08f, Animation.PlayMode.NORMAL));
        configsPorDefecto.put(EstadoAnimacion.ESPECIAL_AEREO_LATERAL, new ConfigAnimacion("especial_lateral_aereo.png", 0.10f, Animation.PlayMode.NORMAL));
        configsPorDefecto.put(EstadoAnimacion.ESPECIAL_AEREO_ABAJO,   new ConfigAnimacion("especial_abajo_aereo.png", 0.09f, Animation.PlayMode.NORMAL));

        configsPorDefecto.put(EstadoAnimacion.BLOQUEAR,            new ConfigAnimacion("bloquear.png", 0.20f, Animation.PlayMode.NORMAL));
        configsPorDefecto.put(EstadoAnimacion.ESPECIAL,            new ConfigAnimacion("especial.png", 0.10f, Animation.PlayMode.NORMAL));
        configsPorDefecto.put(EstadoAnimacion.RECIBIR_DANIO,       new ConfigAnimacion("dano.png", 0.10f, Animation.PlayMode.NORMAL));

        cargarPersonaje(nombrePersonaje, carpeta, configsPorDefecto);
    }

    /**
     * Método que calcula automáticamente las filas y columnas.
     */
    private static Animation<TextureRegion> cargarAnimacionAutodetectada(String rutaArchivo, float frameDuration, Animation.PlayMode mode) {
        if (!Gdx.files.internal(rutaArchivo).exists()) {
            System.out.println("ADVERTENCIA: No se encontró el archivo en -> " + Gdx.files.internal(rutaArchivo).file().getAbsolutePath());
            return null;
        }

        Texture textura = new Texture(Gdx.files.internal(rutaArchivo));
        
        int altoFrame = textura.getHeight(); 
        int columnas = textura.getWidth() / altoFrame;
        int filas = 1;

        if (columnas < 1) columnas = 1;

        TextureRegion[][] tmp = TextureRegion.split(textura, textura.getWidth() / columnas, textura.getHeight() / filas);

        TextureRegion[] frames = new TextureRegion[filas * columnas];
        int index = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                frames[index++] = tmp[i][j];
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