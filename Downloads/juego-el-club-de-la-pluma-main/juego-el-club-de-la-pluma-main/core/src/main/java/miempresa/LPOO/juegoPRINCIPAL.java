package miempresa.LPOO;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import pantallas.MenuPrincipal;

public class juegoPRINCIPAL extends Game {

    @Override
    public void create() {
        // Pasa a pantalla completa usando la resolución nativa del monitor
        DisplayMode modoActual = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setFullscreenMode(modoActual);

        setScreen(new MenuPrincipal(this));
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}