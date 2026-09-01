package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import miempresa.LPOO.juegoPRINCIPAL;

public class SelectorPersonajes implements Screen {

    private juegoPRINCIPAL juego;

    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;

    public SelectorPersonajes(juegoPRINCIPAL juego) {

        this.juego = juego;

        camera = new OrthographicCamera();

        viewport = new FitViewport(1920, 1080, camera);
        viewport.apply();

        stage = new Stage(viewport);

 
        crearInterfaz();
    }

    private void crearInterfaz() {

        // Acá irán:
        // - Cuadro jugador 1
        // - Cuadro jugador 2
        // - 6 botones de personajes
        // - Botón aleatorio
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }
}
