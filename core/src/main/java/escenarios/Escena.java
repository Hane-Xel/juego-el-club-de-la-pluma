package escenarios;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import personajes.Aves; // Import necesario

public abstract class Escena {

    protected Array<Rectangle> plataformas;
    protected Vector2 spawnJ1;
    protected Vector2 spawnJ2;
    protected Music musicaFondo;

    public Escena() {
        this.plataformas = new Array<Rectangle>();
    }

    /**
     * Revisa que el personaje no salga de los límites laterales del escenario (muros invisibles).
     */
    public void aplicarLimitesPantalla(Aves personaje, float anchoPantalla) {
        if (personaje == null) return;

        // Muro izquierdo
        if (personaje.getPosX() < 0) {
            personaje.setPosX(0);
        }

        // Muro derecho (pos + ancho del personaje)
        if (personaje.getPosX() + personaje.getAncho() > anchoPantalla) {
            personaje.setPosX(anchoPantalla - personaje.getAncho());
        }
    }

    public void reproducirMusica() {
        if (musicaFondo != null) {
            musicaFondo.setLooping(true);
            musicaFondo.setVolume(0.5f);
            musicaFondo.play();
        }
    }

    public void detenerMusica() {
        if (musicaFondo != null && musicaFondo.isPlaying()) {
            musicaFondo.stop();
        }
    }

    public abstract void render(SpriteBatch batch, float anchoPantalla, float altoPantalla);

    public Array<Rectangle> getPlataformas() { return plataformas; }
    public Vector2 getPosicionSpawnJugador1() { return spawnJ1; }
    public Vector2 getPosicionSpawnJugador2() { return spawnJ2; }
}