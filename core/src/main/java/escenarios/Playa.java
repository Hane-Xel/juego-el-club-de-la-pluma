package escenarios;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import recursos.GestorRecursos;

public class Playa extends Escena {

    private Texture fondo;

    public Playa() {
        super();

        // 1. Puntos de aparición de los jugadores
        this.spawnJ1 = new Vector2(300f, 200f);
        this.spawnJ2 = new Vector2(1500f, 200f);
        
        // Nota: No se añade ninguna plataforma a 'this.plataformas' por solicitud expresa.
        this.plataformas.add(new Plataforma(0f, 0f, 1920f, 150f, null, false));

        // 2. Carga de recursos (Fondo y música de la playa)
        this.fondo = GestorRecursos.obtenerTextura("scenes/playa.png");
        this.musicaFondo = GestorRecursos.obtenerMusica(GestorRecursos.MUSICA_PLAYA);
        
        if (this.musicaFondo != null) {
            reproducirMusica();
        }
    }

    @Override
    public void render(SpriteBatch batch, float anchoPantalla, float altoPantalla) {
        // SEGURIDAD: Si no se cargaron en el constructor, se vuelven a solicitar
        if (fondo == null) {
            fondo = GestorRecursos.obtenerTextura("scenes/playa.png");
        }
        if (musicaFondo == null) {
            musicaFondo = GestorRecursos.obtenerMusica(GestorRecursos.MUSICA_PLAYA);
            reproducirMusica();
        }

        // Dibujar el fondo de la playa en pantalla
        if (fondo != null) {
            batch.draw(fondo, 0, 0, anchoPantalla, altoPantalla);
        }
    }
}