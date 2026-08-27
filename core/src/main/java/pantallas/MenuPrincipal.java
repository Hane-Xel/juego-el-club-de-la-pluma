package pantallas;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import miempresa.LPOO.juegoPRINCIPAL;

public class MenuPrincipal implements Screen {

	private juegoPRINCIPAL juego;
	private SpriteBatch batch;
    private Texture fondo;

    public MenuPrincipal(juegoPRINCIPAL juego) {
        this.juego = juego;
        
        batch = new SpriteBatch();
        fondo = new Texture("scenes/menu.png");
    }
	
	    @Override
	    public void show() {
	        
	    }

	    @Override
	    public void render(float delta) {
	    	
	    	// Limpia la pantalla
	        Gdx.gl.glClearColor(0, 0, 0, 1);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	        // Comenzamos a dibujar
	        batch.begin();

	        // Dibuja el fondo
	        batch.draw(fondo, 0, 0);

	        // Terminamos de dibujar
	        batch.end();
	        
	    }

	    @Override
	    public void resize(int width, int height) {
	        
	    }

	    @Override
	    public void pause() {
	        
	    }

	    @Override
	    public void resume() {
	        
	    }

	    @Override
	    public void hide() {
	        
	    }

	    @Override
	    public void dispose() {
	        
	    }
}
