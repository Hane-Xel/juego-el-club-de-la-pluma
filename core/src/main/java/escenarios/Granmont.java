package escenarios;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import recursos.GestorRecursos;

public class Granmont extends Escena {

    private Texture fondo;

    public Granmont() {
        super();

        // Puntos de aparición de los jugadores
        this.spawnJ1 = new Vector2(600f, 260f);
        this.spawnJ2 = new Vector2(1200f, 260f); 

        // 1. Suelo principal sólido de la montaña (no traspasable)
        this.plataformas.add(new Plataforma(100f, 0f, 1600f, 180f, null, false));

        // 2. Cargar textura de la plataforma de nube y añadirla al escenario
        Texture texNube = GestorRecursos.obtenerTextura("scenes/nube.png");
        
        // Plataforma tipo nube flotante (x, y, ancho, alto, textura, traspasablePorAbajo = true)
        this.plataformas.add(new Plataforma(450f, 450f, 225f, 30f, texNube, true));
        this.plataformas.add(new Plataforma(1200f, 450f, 225f, 30f, texNube, true));
        this.plataformas.add(new Plataforma(825f, 650f, 225f, 30f, texNube, true));
        
        // Cargar fondo y música de Granmont
        this.fondo = GestorRecursos.obtenerTextura("scenes/granmont.png");
        this.musicaFondo = GestorRecursos.obtenerMusica(GestorRecursos.MUSICA_GRANMONT);
    }

    @Override
    public void render(SpriteBatch batch, float anchoPantalla, float altoPantalla) {
        if (fondo == null) {
            fondo = GestorRecursos.obtenerTextura("scenes/granmont.png");
        }
        if (musicaFondo == null) {
            musicaFondo = GestorRecursos.obtenerMusica(GestorRecursos.MUSICA_GRANMONT);
            reproducirMusica();
        }

        // 1. Dibujar el fondo primero
        if (fondo != null) {
            batch.draw(fondo, 0, 0, anchoPantalla, altoPantalla);
        }

        // 2. ¡IMPORTANTE! Dibujar las plataformas encima del fondo
        renderPlataformas(batch);
    }
}