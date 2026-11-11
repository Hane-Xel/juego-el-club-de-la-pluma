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

        // 1. Suelo principal sólido
        this.plataformas.add(new Plataforma(100f, 0f, 1600f, 180f, null, false));

        // Inicializamos las plataformas
        cargarPlataformasNube();
    }

    /**
     * FUNCIÓN: cargarPlataformasNube()
     * QUÉ HACE: Asigna las texturas de las nubes a las plataformas.
     */
    private void cargarPlataformasNube() {
        Texture texNube = GestorRecursos.obtenerTextura("scenes/nube.png");
        
        // Si las plataformas aún no fueron creadas, las añadimos
        if (this.plataformas.size <= 1) {
            this.plataformas.add(new Plataforma(450f, 450f, 225f, 30f, texNube, true));
            this.plataformas.add(new Plataforma(1200f, 450f, 225f, 30f, texNube, true));
            this.plataformas.add(new Plataforma(825f, 650f, 225f, 30f, texNube, true));
        }
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

        // 1. Dibujar el fondo
        if (fondo != null) {
            batch.draw(fondo, 0, 0, anchoPantalla, altoPantalla);
        }

        // 2. Dibujar las plataformas
        renderPlataformas(batch);
    }
}