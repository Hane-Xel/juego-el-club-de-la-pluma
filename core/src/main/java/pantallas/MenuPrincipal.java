package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import miempresa.LPOO.juegoPRINCIPAL;
import recursos.GestorRecursos;

public class MenuPrincipal implements Screen {
	
	private OrthographicCamera camera;
	private Viewport viewport;
    private juegoPRINCIPAL juego;
    private SpriteBatch batch;
    private Texture fondo;

    // Componentes para la Interfaz de Usuario (UI)
    private Stage stage;
    private Texture texturaBotonSalir;
    private ImageButton botonSalir;
    private Texture texturaBotonEmpezar;
    private ImageButton botonEmpezar;

    public MenuPrincipal(juegoPRINCIPAL juego) {
        this.juego = juego;
        batch = new SpriteBatch();

        GestorRecursos.cargarMenu();

        fondo = GestorRecursos.getTexture("scenes/fondotitulo.png");

   
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        viewport.apply();

        stage = new Stage(viewport);
        
        texturaBotonEmpezar = new Texture(
        	    Gdx.files.internal("ui/botonbeggin.png")
        	);

        	TextureRegionDrawable drawableEmpezar =
        	        new TextureRegionDrawable(texturaBotonEmpezar);

        	botonEmpezar = new ImageButton(drawableEmpezar);

        	botonEmpezar.setPosition(800, 300);
        	
        	botonEmpezar.addListener(new ClickListener() {

        	    @Override
        	    public void clicked(InputEvent event, float x, float y) {
        	        juego.setScreen(
        	            new SelectorPersonajes(juego)
        	        );
        	    }
        	});
        	
        stage.addActor(botonEmpezar);

       texturaBotonSalir = new Texture(Gdx.files.internal("ui/botonexit.png"));

        TextureRegionDrawable drawableSalir = new TextureRegionDrawable(texturaBotonSalir);
        botonSalir = new ImageButton(drawableSalir);

        botonSalir.setPosition(100, 100);


        botonSalir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); 
            } 
        });

        stage.addActor(botonSalir);
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

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.draw(fondo, 0, 0, 1920, 1080);

        batch.end();

        stage.act(delta);
        stage.draw();
    }

    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        texturaBotonSalir.dispose();
        stage.dispose();
    }
}
