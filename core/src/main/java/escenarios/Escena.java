package escenarios;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public abstract class Escena {

    protected Array<Rectangle> plataformas;
    protected Vector2 spawnJ1;
    protected Vector2 spawnJ2;
    protected Music musicaFondo;

    public Escena() {
        this.plataformas = new Array<Rectangle>();
    }

    /**
     * Inicia la reproducción en bucle de la música de fondo asociada al escenario.
     */
    public void reproducirMusica() {
        if (musicaFondo != null) {
            musicaFondo.setLooping(true);
            musicaFondo.setVolume(0.5f);
            musicaFondo.play();
        }
    }

    /**
     * Detiene la música de fondo del escenario.
     */
    public void detenerMusica() {
        if (musicaFondo != null && musicaFondo.isPlaying()) {
            musicaFondo.stop();
        }
    }

    /**
     * Renderiza la textura de fondo del escenario adaptada a las dimensiones de la pantalla.
     * @param batch SpriteBatch para dibujar.
     * @param anchoPantalla Ancho del viewport de la cámara.
     * @param altoPantalla Alto del viewport de la cámara.
     */
    public abstract void render(SpriteBatch batch, float anchoPantalla, float altoPantalla);

    // Getters
    public Array<Rectangle> getPlataformas() { return plataformas; }
    public Vector2 getPosicionSpawnJugador1() { return spawnJ1; }
    public Vector2 getPosicionSpawnJugador2() { return spawnJ2; }
}