package recursos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;

public class GestorRecursos {

    public static final AssetManager manager = new AssetManager();
    
    private static float volumenGlobal = 1.0f;
    private static Music musicaActualActiva = null;
        
    public static final String MUSICA_PLAYA = "music/playa.mp3";
    public static final String MUSICA_BIGBEN = "music/bigben.mp3";
    public static final String MUSICA_DOJO = "music/dojo.mp3";
    public static final String MUSICA_GRANMONT = "music/granmont.mp3";
    public static final String MUSICA_PAUSA = "music/pausa.mp3";

    public static void setVolumenGlobal(float volumen) {
        volumenGlobal = Math.max(0f, Math.min(1f, volumen));
        if (musicaActualActiva != null) {
            musicaActualActiva.setVolume(volumenGlobal);
        }
    }

    public static float getVolumenGlobal() {
        return volumenGlobal;
    }

    public static void cargarMenu() {
        cargarAssetSeguro("scenes/fondotitulo.png", Texture.class);
        manager.finishLoading();
    }

    public static void cargarCombate() {
        cargarAssetSeguro("scenes/playa.png", Texture.class);
        cargarAssetSeguro("scenes/bigben.png", Texture.class);
        cargarAssetSeguro("scenes/dojo.png", Texture.class);
        cargarAssetSeguro("scenes/granmont.png", Texture.class);
        
        cargarAssetSeguro("scenes/nube.png", Texture.class);

        cargarAssetSeguro(MUSICA_PLAYA, Music.class);
        cargarAssetSeguro(MUSICA_BIGBEN, Music.class);
        cargarAssetSeguro(MUSICA_DOJO, Music.class);
        cargarAssetSeguro(MUSICA_GRANMONT, Music.class);
        cargarAssetSeguro(MUSICA_PAUSA, Music.class);
        
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
            Music musica = manager.get(ruta, Music.class);
            musica.setVolume(volumenGlobal);
            musicaActualActiva = musica;
            return musica;
        }
        return null;
    }
    
    public static void dispose() {
        manager.dispose();
    }
}