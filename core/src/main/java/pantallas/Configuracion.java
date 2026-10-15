package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import controles.ConfControles;
import miempresa.LPOO.juegoPRINCIPAL;
import recursos.GestorRecursos;

public class Configuracion implements Screen { 

    private OrthographicCamera camera;
    private Viewport viewport;
    private juegoPRINCIPAL juego;
    private SpriteBatch batch;
    private Texture fondo;

    private Stage stage;
    private BitmapFont font;

    // 1. Slider de Volumen
    private Slider sliderVolumen;
    private Label labelPorcentaje;
    private Texture texSliderBg;
    private Texture texSliderKnob;

    // 2. Botón para ir a Configurar Controles
    private TextButton btnIrAControles;

    // 3. Botón Volver
    private Texture texturaBotonVolver;
    private ImageButton botonVolver;

    public Configuracion(juegoPRINCIPAL juego) {
        this.juego = juego;
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2f);

        fondo = GestorRecursos.obtenerTextura("scenes/fondotitulo.png");

        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        viewport.apply();

        stage = new Stage(viewport);

        float posX = 760f;

        // -------------------------------------------------------------
        // BARRA DE VOLUMEN
        // -------------------------------------------------------------
        texSliderBg = crearTexturaColor(400, 20, Color.GRAY);
        texSliderKnob = crearTexturaColor(20, 40, Color.RED);

        Slider.SliderStyle styleSlider = new Slider.SliderStyle();
        styleSlider.background = new TextureRegionDrawable(texSliderBg);
        styleSlider.knob = new TextureRegionDrawable(texSliderKnob);

        sliderVolumen = new Slider(0f, 1f, 0.01f, false, styleSlider);
        sliderVolumen.setValue(1.0f);
        sliderVolumen.setSize(400, 40);
        sliderVolumen.setPosition(posX, 700);

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        labelPorcentaje = new Label("Volumen: 100%", labelStyle);
        labelPorcentaje.setPosition(posX + 430, 705);

        sliderVolumen.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float valor = sliderVolumen.getValue();
                int porcentaje = Math.round(valor * 100);
                labelPorcentaje.setText("Volumen: " + porcentaje + "%");
            }
        });

        stage.addActor(sliderVolumen);
        stage.addActor(labelPorcentaje);

        // -------------------------------------------------------------
        // BOTÓN "CONFIGURAR CONTROLES"
        // -------------------------------------------------------------
        Texture texBtnBg = crearTexturaColor(400, 80, Color.DARK_GRAY);
        TextButton.TextButtonStyle styleTextBtn = new TextButton.TextButtonStyle();
        styleTextBtn.up = new TextureRegionDrawable(texBtnBg);
        styleTextBtn.font = font;
        styleTextBtn.fontColor = Color.YELLOW;

        btnIrAControles = new TextButton("Configurar Controles", styleTextBtn);
        btnIrAControles.setSize(400, 80);
        btnIrAControles.setPosition(posX, 520);

        btnIrAControles.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Navega hacia la pantalla ConfControles
                juego.setScreen(new ConfControles(juego, null)); // Puedes pasar la instancia de Controles si la tienes
            }
        });

        stage.addActor(btnIrAControles);

        // -------------------------------------------------------------
        // BOTÓN VOLVER (MENÚ PRINCIPAL)
        // -------------------------------------------------------------
        texturaBotonVolver = new Texture(Gdx.files.internal("ui/botonbeggin.png"));
        botonVolver = new ImageButton(new TextureRegionDrawable(texturaBotonVolver));

        botonVolver.setSize(250f, 80f);
        botonVolver.getImageCell().size(250f, 80f);
        botonVolver.setPosition(100, 60);

        botonVolver.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new MenuPrincipal(juego));
            }
        });

        stage.addActor(botonVolver);
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

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        texSliderBg.dispose();
        texSliderKnob.dispose();
        texturaBotonVolver.dispose();
        stage.dispose();
    }

    private Texture crearTexturaColor(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        Texture textura = new Texture(pixmap);
        pixmap.dispose();
        return textura;
    }
}