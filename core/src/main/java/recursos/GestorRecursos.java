package recursos;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;

public class GestorRecursos {

    public static final AssetManager manager = new AssetManager();

    // Métodos para encolar cargas
    public static void cargarMenu() {
        manager.load("scenes/menu.png", Texture.class);
    }

    public static void cargarCombate() {
        // Cargar texturas de interfaz, escenarios, etc.
        // manager.load("personajes/kiri.png", Texture.class);
    }

    /*// Método para obtener una textura rápida
    public static Texture getTexture(String path) {
        return manager.get(path, Texture.class);
    }*/

    // Liberar todo al cerrar la aplicación
    public static void dispose() {
        manager.dispose();
    }
}
