package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import escenarios.Escena;
import escenarios.Playa;
import miempresa.LPOO.juegoPRINCIPAL;
import personajes.Aves;
import recursos.GestorRecursos;

public class MenuPrincipal implements Screen {

    private juegoPRINCIPAL juego;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;

    private BitmapFont fuenteBotones;
    private Texture texFondoBoton;

    // Color naranja para bordes y texto
    private static final Color COLOR_NARANJA = new Color(1.0f, 0.5f, 0.0f, 1.0f);

    public MenuPrincipal(juegoPRINCIPAL juego) {
        this.juego = juego;

        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(1920, 1080, camera);
        this.stage = new Stage(this.viewport);

        GestorRecursos.cargarMenu();
        crearMenuUI();
    }

    private void crearMenuUI() {
        // 1. Imagen de Fondo
        Texture texFondo = GestorRecursos.obtenerTextura("scenes/fondotitulo.png");
        if (texFondo != null) {
            Image imagenFondo = new Image(texFondo);
            imagenFondo.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
            stage.addActor(imagenFondo);
        }

        // 2. Crear textura del fondo del botón (Negro con borde Naranja)
        texFondoBoton = crearFondoBoton(300, 80, 4, Color.BLACK, COLOR_NARANJA);
        TextureRegionDrawable drawableBoton = new TextureRegionDrawable(new TextureRegion(texFondoBoton));

        // 3. Fuente y Estilo
        fuenteBotones = new BitmapFont();
        fuenteBotones.getData().setScale(2.0f);

        TextButton.TextButtonStyle estiloBoton = new TextButton.TextButtonStyle();
        estiloBoton.font = fuenteBotones;
        estiloBoton.fontColor = COLOR_NARANJA;
        estiloBoton.downFontColor = Color.YELLOW;
        estiloBoton.up = drawableBoton;
        estiloBoton.down = drawableBoton;

        // 4. Configuración de la Tabla (Esq. Inferior Izquierda)
        Table tabla = new Table();
        tabla.setFillParent(true);
        tabla.bottom().left();
        tabla.padLeft(50).padBottom(50);

        TextButton btnComenzar = new TextButton("COMENZAR", estiloBoton);
        TextButton btnConfiguracion = new TextButton("CONFIGURACION", estiloBoton);
        TextButton btnSalir = new TextButton("SALIR", estiloBoton);

        // 5. Listeners de interacción
        btnComenzar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // 1. Cargar primero los recursos en el AssetManager
                GestorRecursos.cargarCombate();

                // 2. Instanciar los objetos del juego
                Aves jugador1 = new Aves("Ave1", 0, 0, "characters/Ave1.png");
                Aves jugador2 = new Aves("Ave1", 0, 0, "characters/Ave1.png");
                Escena escenarioPlaya = new Playa();

                // 3. Cambiar a la pantalla de Combate
                juego.setScreen(new Combate(juego, jugador1, jugador2, escenarioPlaya));
            }
        });

        btnConfiguracion.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new Configuracion(juego));
            }
        });

        btnSalir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        tabla.add(btnComenzar).width(350).height(80).padBottom(15).row();
        tabla.add(btnConfiguracion).width(350).height(80).padBottom(15).row();
        tabla.add(btnSalir).width(350).height(80);

        stage.addActor(tabla);
    }

    private Texture crearFondoBoton(int ancho, int alto, int grosorBorde, Color colorFondo, Color colorBorde) {
        Pixmap pixmap = new Pixmap(ancho, alto, Pixmap.Format.RGBA8888);

        pixmap.setColor(colorBorde);
        pixmap.fill();

        pixmap.setColor(colorFondo);
        pixmap.fillRectangle(grosorBorde, grosorBorde, ancho - (grosorBorde * 2), alto - (grosorBorde * 2));

        Texture textura = new Texture(pixmap);
        pixmap.dispose();
        return textura;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
        stage.dispose();
        if (fuenteBotones != null) fuenteBotones.dispose();
        if (texFondoBoton != null) texFondoBoton.dispose();
    }
}