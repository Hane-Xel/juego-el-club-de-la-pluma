package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import miempresa.LPOO.juegoPRINCIPAL;

public class Resultados implements Screen {

    private juegoPRINCIPAL juego;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private BitmapFont font;
    private SpriteBatch batch;
    private String ganadorTexto;

    public Resultados(juegoPRINCIPAL juego, String ganadorTexto) {
        this.juego = juego;
        this.ganadorTexto = ganadorTexto;

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        stage = new Stage(viewport, batch);

        font = new BitmapFont();
        font.getData().setScale(3.0f); // Fuente grande

        crearUI();
    }

    private void crearUI() {
        Table tabla = new Table();
        tabla.setFillParent(true);
        tabla.center();

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.YELLOW);
        Label lblGanador = new Label("¡" + ganadorTexto + " GANA EL COMBATE!", labelStyle);

        // Estilo provisional de texto para el botón
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.WHITE;

        TextButton btnMenu = new TextButton("Volver al Menu", textButtonStyle);
        btnMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new MenuPrincipal(juego));
            }
        });

        tabla.add(lblGanador).padBottom(50).row();
        tabla.add(btnMenu).width(400).height(80);

        stage.addActor(tabla);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // Limpieza de pantalla con color sólido para evitar parpadeos
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        stage.dispose();
        font.dispose();
    }
}