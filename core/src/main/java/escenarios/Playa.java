package escenarios;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import recursos.GestorRecursos;

public class Playa extends Escena {

    private Texture fondo;

    public Playa() {
        super();

        // 1. Puntos de aparición y piso
        this.spawnJ1 = new Vector2(300f, 200f);
        this.spawnJ2 = new Vector2(1500f, 200f);
        this.plataformas.add(new Rectangle(0, 0, 1920f, 150f));

        // 2. Intentar asignar los recursos al instanciar
        this.fondo = GestorRecursos.obtenerTextura("scenes/playa.png");
        this.musicaFondo = GestorRecursos.obtenerMusica(GestorRecursos.MUSICA_PLAYA);
    }

    @Override
    public void render(SpriteBatch batch, float anchoPantalla, float altoPantalla) {
        // SEGURIDAD: Si no se cargaron en el constructor, los vuelve a pedir
        if (fondo == null) {
            fondo = GestorRecursos.obtenerTextura("scenes/playa.png");
        }
        if (musicaFondo == null) {
            musicaFondo = GestorRecursos.obtenerMusica(GestorRecursos.MUSICA_PLAYA);
            reproducirMusica(); // Inicia la canción si aún no había sonado
        }

        // Dibujar el fondo en pantalla
        if (fondo != null) {
            batch.draw(fondo, 0, 0, anchoPantalla, altoPantalla);
        }
    }
}