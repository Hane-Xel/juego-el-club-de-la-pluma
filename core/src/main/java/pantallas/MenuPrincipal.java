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

    public MenuPrincipal(juegoPRINCIPAL juego) {
        this.juego = juego;
        batch = new SpriteBatch();

        GestorRecursos.cargarMenu();

        fondo = GestorRecursos.getTexture("scenes/fondotitulo.png");

        // Cámara y viewport del juego
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        viewport.apply();

        // Stage usando el mismo viewport
        stage = new Stage(viewport);

       /* texturaBotonSalir = new Texture(Gdx.files.internal("ui/boton_salir.png"));

        TextureRegionDrawable drawableSalir = new TextureRegionDrawable(texturaBotonSalir);
        botonSalir = new ImageButton(drawableSalir);

        botonSalir.setPosition(100, 100);


        botonSalir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); 
            } 
        });

        stage.addActor(botonSalir); */
    }

    @Override
    public void show() {
        // MUY IMPORTANTE: Le indica a LibGDX que el Stage debe procesar la entrada del ratón/teclado
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
        // Liberar los recursos de esta pantalla
        batch.dispose();
        texturaBotonSalir.dispose();
        stage.dispose();
    }
}
