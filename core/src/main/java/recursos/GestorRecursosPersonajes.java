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
        BLOQUEAR,
        ESPECIAL,
        RECIBIR_DANIO
    }

    private static ObjectMap<String, ObjectMap<EstadoAnimacion, Animation<TextureRegion>>> animacionesPersonajes = new ObjectMap<>();

    public static void cargarPersonaje(String nombrePersonaje, String carpeta) {
        ObjectMap<EstadoAnimacion, Animation<TextureRegion>> mapaAnimaciones = new ObjectMap<>();

        String rutaBase = (carpeta.endsWith("/") || carpeta.isEmpty()) ? carpeta : carpeta + "/";

        System.out.println("Cargando personaje: " + nombrePersonaje + " desde la ruta absoluta de assets: " + Gdx.files.internal(rutaBase).file().getAbsolutePath());

        // Hojas de sprite de 1 fila x 1 columna
        mapaAnimaciones.put(EstadoAnimacion.REPOSO,         cargarAnimacion(rutaBase + "reposo.png", 1, 1, 0.15f, Animation.PlayMode.LOOP));
        mapaAnimaciones.put(EstadoAnimacion.CAMINAR,        cargarAnimacion(rutaBase + "caminar.png", 1, 1, 0.10f, Animation.PlayMode.LOOP));
        
        mapaAnimaciones.put(EstadoAnimacion.ATACAR_NEUTRAL, cargarAnimacion(rutaBase + "ataque_neutral.png", 1, 1, 0.08f, Animation.PlayMode.NORMAL));
        mapaAnimaciones.put(EstadoAnimacion.ATACAR_LATERAL, cargarAnimacion(rutaBase + "ataque_lateral.png", 1, 1, 0.10f, Animation.PlayMode.NORMAL));
        mapaAnimaciones.put(EstadoAnimacion.ATACAR_ABAJO,   cargarAnimacion(rutaBase + "ataque_abajo.png",   1, 1, 0.09f, Animation.PlayMode.NORMAL));

        mapaAnimaciones.put(EstadoAnimacion.BLOQUEAR,      cargarAnimacion(rutaBase + "bloquear.png", 1, 1, 0.20f, Animation.PlayMode.NORMAL));
        mapaAnimaciones.put(EstadoAnimacion.ESPECIAL,      cargarAnimacion(rutaBase + "especial.png", 1, 1, 0.10f, Animation.PlayMode.NORMAL));
        mapaAnimaciones.put(EstadoAnimacion.RECIBIR_DANIO, cargarAnimacion(rutaBase + "dano.png",     1, 1, 0.10f, Animation.PlayMode.NORMAL));

        animacionesPersonajes.put(nombrePersonaje, mapaAnimaciones);
    }

    private static Animation<TextureRegion> cargarAnimacion(String rutaArchivo, int filas, int columnas, float frameDuration, Animation.PlayMode mode) {
        if (!Gdx.files.internal(rutaArchivo).exists()) {
            System.out.println("ADVERTENCIA: No se encontró el archivo en -> " + Gdx.files.internal(rutaArchivo).file().getAbsolutePath());
            return null;
        }

        Texture textura = new Texture(Gdx.files.internal(rutaArchivo));
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