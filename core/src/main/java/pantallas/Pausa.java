package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import miempresa.LPOO.juegoPRINCIPAL;
import recursos.GestorRecursos;

public class Pausa implements Screen {

    private juegoPRINCIPAL juego;
    private Screen pantallaAnterior; // Guardamos la referencia al Combate para poder volver
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private SpriteBatch batch;
    private Texture fondoPausa;
    private BitmapFont font;

    public Pausa(juegoPRINCIPAL juego, Screen pantallaAnterior) {
        this.juego = juego;
        this.pantallaAnterior = pantallaAnterior;
        this.batch = new SpriteBatch();
        
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        stage = new Stage(viewport, batch);

        font = new BitmapFont();
        font.getData().setScale(2.5f);

        // CARGAR IMAGEN DIRECTAMENTE DESDE ASSETS
        // Asegurate de tener tu imagen en assets/scenes/pausa.png (o cambia la ruta)
        if (Gdx.files.internal("scenes/pausa.png").exists()) {
            fondoPausa = new Texture(Gdx.files.internal("scenes/pausa.png"));
        } else {
            // Si por alguna razón no está la imagen específica, usamos el fondo genérico
            fondoPausa = GestorRecursos.obtenerTextura("scenes/fondotitulo.png");
        }

        crearUI();
    }

    private void crearUI() {
        if (fondoPausa != null) {
            Image imagenFondo = new Image(fondoPausa);
            imagenFondo.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
            stage.addActor(imagenFondo);
        }

        Table tabla = new Table();
        tabla.setFillParent(true);
        tabla.center();

        TextButton.TextButtonStyle estiloBoton = new TextButton.TextButtonStyle();
        estiloBoton.font = font;
        estiloBoton.fontColor = Color.YELLOW;

        TextButton btnReanudar = new TextButton("REANUDAR", estiloBoton);
        TextButton btnMenu = new TextButton("MENU PRINCIPAL", estiloBoton);

        // Listener para volver exactamente al combate donde nos quedamos
        btnReanudar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(pantallaAnterior);
            }
        });

        // Listener para volver al menú principal
        btnMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new MenuPrincipal(juego));
            }
        });

        tabla.add(btnReanudar).width(450).height(80).padBottom(30).row();
        tabla.add(btnMenu).width(450).height(80);

        stage.addActor(tabla);
    }

    @Override
    public void show() {
        // Activamos el Stage para que capture los clics de los botones
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        font.dispose();
        if (fondoPausa != null && fondoPausa != GestorRecursos.obtenerTextura("scenes/fondotitulo.png")) {
            fondoPausa.dispose();
        }
    }
}