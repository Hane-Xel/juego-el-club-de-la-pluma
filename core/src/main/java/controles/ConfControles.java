package controles;

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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import controles.Controles.inputAction;
import miempresa.LPOO.juegoPRINCIPAL;
import pantallas.Configuracion;
import recursos.GestorRecursos;

public class ConfControles implements Screen {

    private juegoPRINCIPAL juego;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private Texture fondo;

    private Stage stage;
    private BitmapFont font;

    private TextButton botonSeleccionado = null;
    private int jugadorPendiente = 0;
    private inputAction accionPendiente = null;
    private boolean esperandoTecla = false;
    private Label labelEstadoTeclado;

    private Controles gestorControles;
    private Texture texturaBotonVolver;
    private ImageButton botonVolver;

    public ConfControles(juegoPRINCIPAL juego, Controles gestorControles) {
        this.juego = juego;
        this.gestorControles = gestorControles;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.font.getData().setScale(1.8f);

        this.fondo = GestorRecursos.obtenerTextura("scenes/fondotitulo.png");

        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        viewport.apply();

        stage = new Stage(viewport);

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        labelEstadoTeclado = new Label("Haz clic en un control para reasignar", labelStyle);
        labelEstadoTeclado.setPosition(700, 980);
        stage.addActor(labelEstadoTeclado);

        // -------------------------------------------------------------
        // TABLA DE CONTROLES (JUGADOR 1 Y JUGADOR 2)
        // -------------------------------------------------------------
        Table tablaControles = new Table();
        tablaControles.setPosition(960, 520);

        Label.LabelStyle tituloStyle = new Label.LabelStyle(font, Color.GOLD);
        tablaControles.add(new Label("JUGADOR 1", tituloStyle)).padRight(100);
        tablaControles.add(new Label("JUGADOR 2", tituloStyle)).row();

        // Botones para cada movimiento y ataque
        agregarFilaControl(tablaControles, "Izquierda", inputAction.movIzquierda, Keys.A, Keys.LEFT);
        agregarFilaControl(tablaControles, "Derecha", inputAction.movDerecha, Keys.D, Keys.RIGHT);
        agregarFilaControl(tablaControles, "Arriba", inputAction.movArriba, Keys.W, Keys.UP);
        agregarFilaControl(tablaControles, "Abajo", inputAction.movAbajo, Keys.S, Keys.DOWN);
        agregarFilaControl(tablaControles, "Ataque", inputAction.ataque, Keys.U, Keys.NUMPAD_2);
        agregarFilaControl(tablaControles, "Bloqueo", inputAction.bloqueo, Keys.Y, Keys.NUMPAD_1);
        agregarFilaControl(tablaControles, "Especial", inputAction.especial, Keys.I, Keys.NUMPAD_3);

        stage.addActor(tablaControles);

        // -------------------------------------------------------------
        // BOTÓN VOLVER (REGRESA A CONFIGURACIÓN GENERAL)
        // -------------------------------------------------------------
        texturaBotonVolver = new Texture(Gdx.files.internal("ui/botonbeggin.png"));
        botonVolver = new ImageButton(new TextureRegionDrawable(texturaBotonVolver));

        botonVolver.setSize(250f, 80f);
        botonVolver.getImageCell().size(250f, 80f);
        botonVolver.setPosition(100, 60);

        botonVolver.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new Configuracion(juego));
            }
        });

        stage.addActor(botonVolver);
    }

    private void agregarFilaControl(Table tabla, String nombreAccion, final inputAction accion, int teclaDefectoJ1, int teclaDefectoJ2) {
        Texture texBtnBg = crearTexturaColor(280, 50, Color.DARK_GRAY);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(texBtnBg);
        style.font = font;
        style.fontColor = Color.WHITE;

        final TextButton btnJ1 = new TextButton(nombreAccion + ": " + Keys.toString(teclaDefectoJ1), style);
        btnJ1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                prepararReasignacion(btnJ1, 1, accion, nombreAccion);
            }
        });

        final TextButton btnJ2 = new TextButton(nombreAccion + ": " + Keys.toString(teclaDefectoJ2), style);
        btnJ2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                prepararReasignacion(btnJ2, 2, accion, nombreAccion);
            }
        });

        tabla.add(btnJ1).pad(8).width(320).height(55);
        tabla.add(btnJ2).pad(8).width(320).height(55).row();
    }

    private void prepararReasignacion(TextButton boton, int jugador, inputAction accion, String nombreAccion) {
        this.botonSeleccionado = boton;
        this.jugadorPendiente = jugador;
        this.accionPendiente = accion;
        this.esperandoTecla = true;

        boton.setText(nombreAccion + ": [___]");
        labelEstadoTeclado.setText("Presiona una tecla para J" + jugador + " (" + nombreAccion + ")...");
    }

    @Override
    public void show() {
        InputProcessor receptorTeclas = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (esperandoTecla && botonSeleccionado != null) {
                    String textoOriginal = botonSeleccionado.getText().toString().split(":")[0];
                    botonSeleccionado.setText(textoOriginal + ": " + Keys.toString(keycode));

                    if (gestorControles != null) {
                        gestorControles.reconfigurarTecla(jugadorPendiente, accionPendiente, keycode);
                    }

                    labelEstadoTeclado.setText("¡Asignado " + Keys.toString(keycode) + " a J" + jugadorPendiente + "!");
                    
                    esperandoTecla = false;
                    botonSeleccionado = null;
                    accionPendiente = null;
                    return true;
                }
                return false;
            }
        };

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

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
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