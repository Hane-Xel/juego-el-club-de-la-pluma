package escenarios;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import recursos.GestorRecursos;

public class Bigben extends Escena {

    private Texture fondo;

    public Bigben() {
        super();

        // Puntos de aparición de los jugadores
        this.spawnJ1 = new Vector2(600f, 330f);
        this.spawnJ2 = new Vector2(1200f, 330f); 

        // 1. Suelo principal sólido de la montaña (no traspasable)
        this.plataformas.add(new Plataforma(370f, 0f, 1100f, 90f, null, false));

        
        // Plataforma tipo nube flotante (x, y, ancho, alto, textura, traspasablePorAbajo = true)
        this.plataformas.add(new Plataforma(150f, 480f, 225f, 30f, null, true));
        this.plataformas.add(new Plataforma(1470f, 480f, 225f, 30f, null, true));
        
        // Cargar fondo y música de Granmont
        this.fondo = GestorRecursos.obtenerTextura("scenes/bigben.png");
        this.musicaFondo = GestorRecursos.obtenerMusica(GestorRecursos.MUSICA_BIGBEN);
    }

    @Override
    public void render(SpriteBatch batch, float anchoPantalla, float altoPantalla) {
        if (fondo == null) {
            fondo = GestorRecursos.obtenerTextura("scenes/bigben.png");
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