package escenarios;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import personajes.Aves;

public abstract class Escena {

    protected Array<Plataforma> plataformas; // contiene todas las plataformas. es una lista de objetos de la clase plataforma
    protected Vector2 spawnJ1;
    protected Vector2 spawnJ2;// vector dos es un vector bidimensional, asi que puede guardar X e Y
    protected Music musicaFondo;// archivo musica tun tun vine hasta aqui dejando atras el sabor a ciudad

    public Escena() {
        this.plataformas = new Array<Plataforma>(); // inicializa las plataformas en el momento en que se crean
    }

    public void aplicarLimitesPantalla(Aves personaje, float anchoPantalla) {
        if (personaje == null) return;// comprueba si el personaje existe

     
        if (personaje.getPosX() < 0) {
            personaje.setPosX(0);// si se pasa de 0 que seria el borde izquierdo de la pantalla no puede avanzar mas
        }

        if (personaje.getPosX() + personaje.getAncho() > anchoPantalla) {
            personaje.setPosX(anchoPantalla - personaje.getAncho()); // si se pasa de (anchoPantalla - personaje.getAncho() que es el borde derecho de la pantalla no puede avanzar mas
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
        for (Plataforma p : plataformas) // va por las plataformas una por una para cargarlas
        {
            p.render(batch);
        }
    }

    public abstract void render(SpriteBatch batch, float anchoPantalla, float altoPantalla); // como es abstracta todas las clases hijas la tienen que tener
    // batch hace que tenga un diseño y los otros dos hacen lo que dice el nombre
    
    public Array<Plataforma> getPlataformas() { return plataformas; }
    public Vector2 getPosicionSpawnJugador1() { return spawnJ1; }
    public Vector2 getPosicionSpawnJugador2() { return spawnJ2; }
}