package escenarios;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import recursos.GestorRecursos;

public class Dojo extends Escena {

    private Texture fondo;

    public Dojo() {
        super();

        // 1. Puntos de aparición para los jugadores
        this.spawnJ1 = new Vector2(300f, 50f);
        this.spawnJ2 = new Vector2(1500f, 50f);
        
        this.plataformas.add(new Plataforma(0f, 0f, 1920f, 25f, null, false));

        // 2. Intento inicial de carga de recursos
        this.fondo = GestorRecursos.obtenerTextura("scenes/dojo.png");
        this.musicaFondo = GestorRecursos.obtenerMusica(GestorRecursos.MUSICA_DOJO);
        
        if (this.musicaFondo != null) {
            reproducirMusica();
        }
    }

    @Override
    public void render(SpriteBatch batch, float anchoPantalla, float altoPantalla) {
        // SEGURIDAD: Si no se cargaron en el constructor por el orden de ejecución, se obtienen aquí
        if (fondo == null) {
            fondo = GestorRecursos.obtenerTextura("scenes/dojo.png");
        }
        if (musicaFondo == null) {
            musicaFondo = GestorRecursos.obtenerMusica(GestorRecursos.MUSICA_DOJO);
            reproducirMusica();
        }

        if (fondo != null) {
            batch.draw(fondo, 0, 0, anchoPantalla, altoPantalla);
        }
    }
}