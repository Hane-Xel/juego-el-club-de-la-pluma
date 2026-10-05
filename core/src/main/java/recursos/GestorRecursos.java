package recursos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;

public class GestorRecursos {

    public static final AssetManager manager = new AssetManager();
        
    // Constantes de música de escenarios
    public static final String MUSICA_PLAYA = "music/playa.mp3";
    public static final String MUSICA_BIGBEN = "music/bigben.mp3";
    public static final String MUSICA_DOJO = "music/dojo.mp3";
    public static final String MUSICA_GRANMONT = "music/granmont.mp3";

    // Carga del Menú Principal
    public static void cargarMenu() {
        cargarAssetSeguro("scenes/fondotitulo.png", Texture.class);
        manager.finishLoading();
    }

    // Carga de Combate (solo escenarios y música general)
    public static void cargarCombate() {
        // --- ESCENARIOS ---
        cargarAssetSeguro("scenes/playa.png", Texture.class);
        cargarAssetSeguro("scenes/bigben.png", Texture.class);
        cargarAssetSeguro("scenes/dojo.png", Texture.class);
        cargarAssetSeguro("scenes/granmont.png", Texture.class);

        // --- MÚSICA ---
        cargarAssetSeguro(MUSICA_PLAYA, Music.class);
        cargarAssetSeguro(MUSICA_BIGBEN, Music.class);
        cargarAssetSeguro(MUSICA_DOJO, Music.class);
        cargarAssetSeguro(MUSICA_GRANMONT, Music.class);
        
        manager.finishLoading();
    }

    private static <T> void cargarAssetSeguro(String ruta, Class<T> tipo) {
        if (Gdx.files.internal(ruta).exists()) {
            manager.load(ruta, tipo);
        } else {
            System.out.println("ADVERTENCIA [GestorRecursos]: No se encontró el archivo '" + ruta + "'.");
        }
    }

    public static Texture obtenerTextura(String ruta) {
        if (manager.isLoaded(ruta, Texture.class)) {
            return manager.get(ruta, Texture.class);
        }
        return null;
    }

    public static Music obtenerMusica(String ruta) {
        if (manager.isLoaded(ruta, Music.class)) {
            return manager.get(ruta, Music.class);
        }
        return null;
    }

    public static void dispose() {
        manager.dispose();
    }
}