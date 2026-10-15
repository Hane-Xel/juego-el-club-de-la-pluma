package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
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

    // 1. Componentes del Slider de Volumen
    private Slider sliderVolumen;
    private Label labelPorcentaje;
    private Texture texSliderBg;
    private Texture texSliderKnob;

    // 2. Componentes de Reasignación de Teclas
    private TextButton btnTeclaSalto;
    private Label labelEstadoTeclado;
    private boolean esperandoTecla = false;
    private int teclaSalto = Keys.SPACE; // Tecla predeterminada: Espacio

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

        float posX = 700f; // Alineación central horizontal

        // -------------------------------------------------------------
        // BARRA DE VOLUMEN (SLIDER)
        // -------------------------------------------------------------
        texSliderBg = crearTexturaColor(400, 20, Color.GRAY);
        texSliderKnob = crearTexturaColor(20, 40, Color.RED);

        Slider.SliderStyle styleSlider = new Slider.SliderStyle();
        styleSlider.background = new TextureRegionDrawable(texSliderBg);
        styleSlider.knob = new TextureRegionDrawable(texSliderKnob);

        sliderVolumen = new Slider(0f, 1f, 0.01f, false, styleSlider);
        sliderVolumen.setValue(1.0f); // 100% inicio
        sliderVolumen.setSize(400, 40);
        sliderVolumen.setPosition(posX, 700);

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        labelPorcentaje = new Label("Volumen: 100%", labelStyle);
        labelPorcentaje.setPosition(posX + 420, 705);

        sliderVolumen.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float valor = sliderVolumen.getValue();
                int porcentaje = Math.round(valor * 100);
                labelPorcentaje.setText("Volumen: " + porcentaje + "%");
                // Aquí puedes actualizar la música activa:
                // GestorRecursos.obtenerMusica("mi_musica.mp3").setVolume(valor);
            }
        });

        stage.addActor(sliderVolumen);
        stage.addActor(labelPorcentaje);

        // -------------------------------------------------------------
        // CAMBIAR CONTROLES (REASIGNACIÓN)
        // -------------------------------------------------------------
        Texture texBtnBg = crearTexturaColor(350, 60, Color.DARK_GRAY);
        TextButton.TextButtonStyle styleTextBtn = new TextButton.TextButtonStyle();
        styleTextBtn.up = new TextureRegionDrawable(texBtnBg);
        styleTextBtn.font = font;
        styleTextBtn.fontColor = Color.YELLOW;

        btnTeclaSalto = new TextButton("Salto: " + Keys.toString(teclaSalto), styleTextBtn);
        btnTeclaSalto.setSize(350, 60);
        btnTeclaSalto.setPosition(posX, 520);

        labelEstadoTeclado = new Label("", labelStyle);
        labelEstadoTeclado.setPosition(posX + 370, 535);

        btnTeclaSalto.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                esperandoTecla = true;
                btnTeclaSalto.setText("Salto: [___]"); // Se borra el control actual
                labelEstadoTeclado.setText("Presiona una tecla...");
            }
        });

        stage.addActor(btnTeclaSalto);
        stage.addActor(labelEstadoTeclado);

        // -------------------------------------------------------------
        // BOTÓN VOLVER (ESQUINA INFERIOR IZQUIERDA)
        // -------------------------------------------------------------
        texturaBotonVolver = new Texture(Gdx.files.internal("ui/botonbeggin.png")); // Cambiar por ui/botonvolver.png cuando la tengas
        botonVolver = new ImageButton(new TextureRegionDrawable(texturaBotonVolver));

        float anchoBoton = 350f;
        float altoBoton = 160f;
        botonVolver.setSize(anchoBoton, altoBoton);
        botonVolver.getImageCell().size(anchoBoton, altoBoton);
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
        // Escuchador de teclado en vivo para capturar la nueva tecla presionada
        InputProcessor receptorTeclas = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (esperandoTecla) {
                    teclaSalto = keycode; // Asigna la nueva tecla retenida
                    btnTeclaSalto.setText("Salto: " + Keys.toString(teclaSalto));
                    labelEstadoTeclado.setText("¡Guardado!");
                    esperandoTecla = false;
                    return true;
                }
                return false;
            }
        };

        // Permite interactuar con la UI y con las teclas físicas simultáneamente
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(receptorTeclas);
        Gdx.input.setInputProcessor(multiplexer);
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
        font.dispose();
        texSliderBg.dispose();
        texSliderKnob.dispose();
        texturaBotonVolver.dispose();
        stage.dispose();
    }

    // Método auxiliar para crear recuadros de color sin requerir imágenes externas
    private Texture crearTexturaColor(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        Texture textura = new Texture(pixmap);
        pixmap.dispose();
        return textura;
    }
}