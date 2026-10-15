package miempresa.LPOO;

import com.badlogic.gdx.Game;

import pantallas.MenuPrincipal;

public class juegoPRINCIPAL extends Game {

	
	
    @Override
    public void create() {
        setScreen(new MenuPrincipal(this));
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}