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

import miempresa.LPOO.juegoPRINCIPAL;
import recursos.GestorRecursos;

/**
 * Clase que representa la pantalla del Menú Principal del juego.
 * Implementa la interfaz Screen de LibGDX para gestionar su propio ciclo de vida (render, resize, show, etc.).
 */
public class MenuPrincipal implements Screen {

    private juegoPRINCIPAL juego;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage; // Escena de Scene2D para manejar la interfaz gráfica (botones e imagen)

    private BitmapFont fuenteBotones;
    private Texture texFondoBoton;

    private static final Color COLOR_NARANJA = new Color(1.0f, 0.5f, 0.0f, 1.0f);

    /**
     * CONSTRUCTOR: MenuPrincipal
     * QUÉ HACE: Inicializa los componentes visuales, la cámara, el sistema de UI y carga los recursos del menú.
     */
    public MenuPrincipal(juegoPRINCIPAL juego) {
        this.juego = juego;

        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(1920, 1080, camera); // Resolución base de diseño: 1920x1080
        this.stage = new Stage(this.viewport);

        // Carga los recursos específicos del menú mediante el gestor global
        GestorRecursos.cargarMenu();
        crearMenuUI();
    }

    /**
     * FUNCIÓN: crearMenuUI()
     * QUÉ HACE: Construye visualmente la interfaz de usuario del menú principal.
     * CÓMO LO HACE: Coloca la imagen de fondo, genera dinámicamente los estilos de los botones 
     * con bordes personalizados y los organiza dentro de una tabla alineada abajo a la izquierda.
     */
    private void crearMenuUI() {
        // Carga y añade la imagen de fondo del menú principal
        Texture texFondo = GestorRecursos.obtenerTextura("scenes/fondotitulo.png");
        if (texFondo != null) {
            Image imagenFondo = new Image(texFondo);
            imagenFondo.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
            stage.addActor(imagenFondo);
        }

        // Genera una textura procedural para el fondo de los botones con borde y color personalizado
        texFondoBoton = crearFondoBoton(300, 80, 4, Color.BLACK, COLOR_NARANJA);
        TextureRegionDrawable drawableBoton = new TextureRegionDrawable(new TextureRegion(texFondoBoton));

        // Configuración de la tipografía y escala de texto para los botones
        fuenteBotones = new BitmapFont();
        fuenteBotones.getData().setScale(2.0f);

        TextButton.TextButtonStyle estiloBoton = new TextButton.TextButtonStyle();
        estiloBoton.font = fuenteBotones;
        estiloBoton.fontColor = COLOR_NARANJA;
        estiloBoton.downFontColor = Color.YELLOW; // Color al hacer clic
        estiloBoton.up = drawableBoton;
        estiloBoton.down = drawableBoton;

        // Tabla contenedora para alinear los botones en la esquina inferior izquierda
        Table tabla = new Table();
        tabla.setFillParent(true);
        tabla.bottom().left();
        tabla.padLeft(50).padBottom(50);

        // Creación de los botones de interacción
        TextButton btnComenzar = new TextButton("COMENZAR", estiloBoton);
        TextButton btnConfiguracion = new TextButton("CONFIGURACION", estiloBoton);
        TextButton btnSalir = new TextButton("SALIR", estiloBoton);

        // --- LISTENERS DE LOS BOTONES ---
        
        // 1. Botón Comenzar: Transiciona a la pantalla de selección de personajes
        btnComenzar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new SelectorPersonajes(juego));
            }
        });
        
        // 2. Botón Configuración: Transiciona a la pantalla de ajustes/teclas
        btnConfiguracion.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new Configuracion(juego));
            }
        });

        // 3. Botón Salir: Cierra por completo la aplicación de escritorio
        btnSalir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // Añade los botones a la tabla con espaciados y filas independientes
        tabla.add(btnComenzar).width(350).height(80).padBottom(15).row();
        tabla.add(btnConfiguracion).width(350).height(80).padBottom(15).row();
        tabla.add(btnSalir).width(350).height(80);

        stage.addActor(tabla);
    }

    /**
     * FUNCIÓN: crearFondoBoton()
     * QUÉ HACE: Dibuja mediante programación (con Pixmap) una textura rectangular con borde de color sólido.
     * CÓMO LO HACE: Crea un mapa de píxeles, rellena el fondo del color del borde y luego dibuja un rectángulo 
     * interno con el color principal del botón, convirtiéndolo posteriormente en una Textura de LibGDX.
     */
    private Texture crearFondoBoton(int ancho, int alto, int grosorBorde, Color colorFondo, Color colorBorde) {
        Pixmap pixmap = new Pixmap(ancho, alto, Pixmap.Format.RGBA8888);
        pixmap.setColor(colorBorde);
        pixmap.fill();
        pixmap.setColor(colorFondo);
        pixmap.fillRectangle(grosorBorde, grosorBorde, ancho - (grosorBorde * 2), alto - (grosorBorde * 2));
        Texture textura = new Texture(pixmap);
        pixmap.dispose(); // Libera la memoria RAM del pixmap una vez pasado a la GPU
        return textura;
    }

    /**
     * FUNCIÓN: show()
     * QUÉ HACE: Se ejecuta automáticamente cuando esta pantalla se vuelve activa.
     * CÓMO LO HACE: Asigna el `stage` como el procesador de entradas principal para que detecte los clics en los botones.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * FUNCIÓN: render(float delta)
     * QUÉ HACE: Se ejecuta en cada fotograma del bucle principal del juego para actualizar y dibujar la pantalla.
     * CÓMO LO HACE: Limpia la pantalla con color negro y manda a dibujar los actores del Stage (imagen y botones).
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    /**
     * FUNCIÓN: resize(int width, int height)
     * QUÉ HACE: Ajusta la escala de la interfaz cuando se redimensiona la ventana del juego.
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    /**
     * FUNCIÓN: dispose()
     * QUÉ HACE: Libera todos los recursos gráficos y de memoria cuando se destruye la pantalla.
     */
    @Override
    public void dispose() {
        stage.dispose();
        if (fuenteBotones != null) fuenteBotones.dispose();
        if (texFondoBoton != null) texFondoBoton.dispose();
    }
}