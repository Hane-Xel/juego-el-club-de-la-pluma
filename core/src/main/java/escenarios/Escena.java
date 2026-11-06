package escenarios;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import personajes.Aves;

public abstract class Escena {

    protected Array<Plataforma> plataformas;
    protected Vector2 spawnJ1;
    protected Vector2 spawnJ2;
    protected Music musicaFondo;

    public Escena() {
        this.plataformas = new Array<Plataforma>();
    }

    /**
   
     */
    public void aplicarLimitesPantalla(Aves personaje, float anchoPantalla) {
        if (personaje == null) return;

     
        if (personaje.getPosX() < 0) {
            personaje.setPosX(0);
        }

        if (personaje.getPosX() + personaje.getAncho() > anchoPantalla) {
            personaje.setPosX(anchoPantalla - personaje.getAncho());
        }
    }

    public void reproducirMusica() {
        if (musicaFondo != null) {
            musicaFondo.setLooping(true);
            musicaFondo.setVolume(recursos.GestorRecursos.getVolumenGlobal()); 
            musicaFondo.play();
        }
    }

    public void detenerMusica() {
        if (musicaFondo != null && musicaFondo.isPlaying()) {
            musicaFondo.stop();
        }
    }

   
    public void renderPlataformas(SpriteBatch batch) {
        for (Plataforma p : plataformas) {
            p.render(batch);
        }
    }

    public abstract void render(SpriteBatch batch, float anchoPantalla, float altoPantalla);

    public Array<Plataforma> getPlataformas() { return plataformas; }
    public Vector2 getPosicionSpawnJugador1() { return spawnJ1; }
    public Vector2 getPosicionSpawnJugador2() { return spawnJ2; }
}