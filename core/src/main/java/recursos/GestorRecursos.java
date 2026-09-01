package recursos;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;


public class GestorRecursos {

    public static final AssetManager manager = new AssetManager();
    
    // Rutas de texturas de personajes
    public static final String PERSONAJE_AVE1 = "characters/Ave1.png";
    public static final String PERSONAJE_AVE2 = "characters/Ave2.png";
    public static final String PERSONAJE_AVE1_ATAQUE = "characters/Ave1_ataque.png";
    public static final String PERSONAJE_AVE1_BLOQUEO = "characters/Ave1_bloqueo.png";
    public static final String PERSONAJE_AVE1_DAÑO = "characters/Ave1_daño.png";
    public static final String PERSONAJE_AVE2_ATAQUE = "characters/Ave2_ataque.png";
    public static final String PERSONAJE_AVE2_BLOQUEO = "characters/Ave2_bloqueo.png";
    public static final String PERSONAJE_AVE2_DAÑO = "characters/Ave2_daño.png";
    public static final String ESPECIAL = "characters/Especial.png";

    // Métodos para encolar cargas
    public static void cargarMenu() {
        manager.load("scenes/fondotitulo.png", Texture.class);
        manager.finishLoading();
    }

    public static void cargarCombate() {
        // Cargar texturas de personajes
        manager.load(PERSONAJE_AVE1, Texture.class);
        manager.load(PERSONAJE_AVE2, Texture.class);
        manager.load(PERSONAJE_AVE1_ATAQUE, Texture.class);
        manager.load(PERSONAJE_AVE1_BLOQUEO, Texture.class);
        manager.load(PERSONAJE_AVE1_DAÑO, Texture.class);
        manager.load(PERSONAJE_AVE2_ATAQUE, Texture.class);
        manager.load(PERSONAJE_AVE2_BLOQUEO, Texture.class);
        manager.load(PERSONAJE_AVE2_DAÑO, Texture.class);
        manager.load(ESPECIAL, Texture.class);
        manager.finishLoading();
    }

    /**
     * Obtiene la textura de un personaje
     * @param ruta la ruta de la textura (usar constantes de la clase)
     * @return la textura cargada
     */
    public static Texture obtenerTextura(String ruta) {
        if (manager.isLoaded(ruta, Texture.class)) {
            return manager.get(ruta, Texture.class);
        } else {
            System.err.println("Textura no cargada: " + ruta);
            return null;
        }
    }

    /**
     * Carga una textura de forma inmediata (síncrona)
     * @param ruta la ruta de la textura
     * @return la textura cargada
     */
    public static Texture cargarTexturaDirecta(String ruta) {
        return new Texture(ruta);
    }

    // Liberar todo al cerrar la aplicación
    public static void dispose() {
        manager.dispose();
    }
}
