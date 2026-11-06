package miempresa.LPOO;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import pantallas.MenuPrincipal;
import controles.Controles; // <-- Importante para reconocer los controles

public class juegoPRINCIPAL extends Game {

    // Instancia global de controles para que persistan entre pantallas
    private Controles gestorControles;

    @Override
    public void create() {
        // Inicializamos el gestor de controles global (sin personajes asignados todavía)
        gestorControles = new Controles(null, null);

        // Pasa a pantalla completa usando la resolución nativa del monitor
        DisplayMode modoActual = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setFullscreenMode(modoActual);

        setScreen(new MenuPrincipal(this));
    }

    // Método que requiere la pantalla de Configuración para acceder a las teclas
    public Controles getGestorControles() {
        return gestorControles;
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}