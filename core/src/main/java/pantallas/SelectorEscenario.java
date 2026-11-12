package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import escenarios.Bigben; 
import escenarios.Dojo;
import escenarios.Escena;
import escenarios.Granmont;
import escenarios.Playa;
import miempresa.LPOO.juegoPRINCIPAL;
import personajes.Aves;
import personajes.Corv;
import personajes.Emuans;
import personajes.Kiri;
import recursos.GestorRecursos;
import recursos.GestorRecursosPersonajes;

/**
 * Pantalla de Selector de Escenario.
 * Permite al usuario elegir el mapa en el que se llevará a cabo el combate.
 */
public class SelectorEscenario implements Screen {

    private juegoPRINCIPAL juego;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;

    private String personajeJ1;
    private String personajeJ2;

    private BitmapFont fuenteTitulo;
    
    private Texture texGranmont;
    private Texture texPlaya;
    private Texture texDojo;
    private Texture texBigBen;
    private Texture texturaFallback;

    public SelectorEscenario(juegoPRINCIPAL juego, String personajeJ1, String personajeJ2) {
        this.juego = juego;
        this.personajeJ1 = personajeJ1;
        this.personajeJ2 = personajeJ2;

        this.camera = new OrthographicCamera();// hace que todo tenga el mismo tamaño sin importar la resolucion
        this.viewport = new FitViewport(1920, 1080, camera); // hace que la pantalla este fija en esta posicion y tamaño
        this.stage = new Stage(viewport); // atributo nativo de libgdx que permite dibujar los elementos, hacer clics en objetos y hacer saber a la muy boluda de la pantalla que mostrar

        cargarTexturasEscenarios();
        crearFuentes();
        construirInterfaz();
    }

    private void cargarTexturasEscenarios() {
        texturaFallback = crearTexturaBorde(300, 180, 4, Color.GRAY);// botoncito fachero por si no hay foto

        if (Gdx.files.internal("scenes/granmont.png").exists()) 
        {
            texGranmont = new Texture(Gdx.files.internal("scenes/granmont.png"));// busca la imagen y la dibuja
        } 
        else 
        {
            texGranmont = texturaFallback;// si no bordecito fachero
        }

        if (Gdx.files.internal("scenes/playa.png").exists()) 
        {
            texPlaya = new Texture(Gdx.files.internal("scenes/playa.png"));
        } 
        else 
        {
            texPlaya = texturaFallback;
        }

        if (Gdx.files.internal("scenes/dojo.png").exists()) 
        {
            texDojo = new Texture(Gdx.files.internal("scenes/dojo.png"));
        } 
        else 
        {
            texDojo = texturaFallback;
        }

        if (Gdx.files.internal("scenes/bigben.png").exists()) 
        {
            texBigBen = new Texture(Gdx.files.internal("scenes/bigben.png"));
        } 
        else 
        {
            texBigBen = texturaFallback;
        }
    }

    private Texture crearTexturaBorde(int ancho, int alto, int grosor, Color color) {// crea el bordecito fachero
        Pixmap pixmap = new Pixmap(ancho, alto, Pixmap.Format.RGBA8888);// dibuja algo sin imagen
        pixmap.setColor(color);
        for (int i = 0; i < grosor; i++) {
            pixmap.drawRectangle(i, i, ancho - (i * 2), alto - (i * 2));
        }
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    private void crearFuentes() {
        fuenteTitulo = new BitmapFont();
        fuenteTitulo.getData().setScale(2.5f);
    }

    private void construirInterfaz() {
        Table tabla = new Table();
        tabla.setFillParent(true); // la tabla ocupa todo el espacio en pantalla (la cobra que carajo)
        tabla.center();

        Label.LabelStyle estiloTitulo = new Label.LabelStyle(fuenteTitulo, Color.GOLD); // pone el coso ese 
        Label lblTitulo = new Label("SELECCIONA UN ESCENARIO", estiloTitulo);
        lblTitulo.setAlignment(Align.center);

        Image imgGranmont = new Image(texGranmont);
        imgGranmont.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                iniciarCombate("Granmont"); // se pasa el escenario a para iniciar
            }
        });

        Image imgPlaya = new Image(texPlaya);
        imgPlaya.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                iniciarCombate("Playa");
            }
        });

        Image imgDojo = new Image(texDojo);
        imgDojo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                iniciarCombate("Dojo");
            }
        });

        Image imgBigBen = new Image(texBigBen);
        imgBigBen.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                iniciarCombate("Bigben");
            }
        });

        Table tablaOpciones = new Table();
        tablaOpciones.add(imgGranmont).size(350, 200).pad(15);// tamaño de cada boton
        tablaOpciones.add(imgPlaya).size(350, 200).pad(15);// pad es el espacio entre cada uno
        tablaOpciones.add(imgDojo).size(350, 200).pad(15);
        tablaOpciones.add(imgBigBen).size(350, 200).pad(15);// se alinean solos por ser un table y no tener row

        tabla.add(lblTitulo).padBottom(50).row();// crea el titulo
        tabla.add(tablaOpciones);// crea las opciones

        stage.addActor(tabla);
    }

    private void iniciarCombate(String nombreEscenario) {
        // 1. PRIMERO cargamos los recursos de combate y forzamos su carga completa
        GestorRecursos.cargarCombate();
        GestorRecursos.manager.finishLoading();

        // 2. SEGUNDO creamos el escenario (ahora las texturas ya existen en memoria con seguridad)
        Escena escenarioSeleccionado;
        switch (nombreEscenario) {// busca que escenario se eligio
            case "Granmont":
                escenarioSeleccionado = new Granmont();
                break;
            case "Playa":
                escenarioSeleccionado = new Playa();
                break;
            case "Dojo":
                escenarioSeleccionado = new Dojo();
                break;
            case "Bigben":
                escenarioSeleccionado = new Bigben();
                break;
            default:
                escenarioSeleccionado = new Granmont();
                break;
        }

        GestorRecursosPersonajes.cargarPersonaje(personajeJ1.toLowerCase(), "characters/" + personajeJ1.toLowerCase());// carga los sprites
        GestorRecursosPersonajes.cargarPersonaje(personajeJ2.toLowerCase(), "characters/" + personajeJ2.toLowerCase());

        Aves jugador1 = crearInstanciaAve(personajeJ1, 300f, 500f);// los recibe del selector
        Aves jugador2 = crearInstanciaAve(personajeJ2, 1500f, 500f);

        juego.setScreen(new Combate(juego, jugador1, jugador2, escenarioSeleccionado));
    }

    private Aves crearInstanciaAve(String nombre, float x, float y) {// hace que el personaje pase de ser un nombre a ser una realidad en forma de objeto :O
        switch (nombre) {
            case "Kiri":
                return new Kiri(x, y);
            case "Emuans":
                return new Emuans(x, y);
            case "Corv":
                return new Corv(x, y);
            default:
                return new Kiri(x, y);
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);// ahora la pantalla puede recibir un clic
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.08f, 0.08f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);// limpia la pantalla de imagenes anteriores (como imagenes fantasma que miedo)

        stage.act(delta);//actualiza el estado de los objetos como que el mouse cambie cuando lo pasas por encima de un boton
        stage.draw();// dibuja los botoncitos y todas esas boludeces
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);// recalcula el tamaño cuando el usuario cambia el tamaño de la pantalla como minimizandola
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}// cosos obligatorios que no me importan

    @Override
    public void dispose() {
        stage.dispose();
        if (fuenteTitulo != null) fuenteTitulo.dispose();
        if (texGranmont != null && texGranmont != texturaFallback) texGranmont.dispose();
        if (texPlaya != null && texPlaya != texturaFallback) texPlaya.dispose();
        if (texDojo != null && texDojo != texturaFallback) texDojo.dispose();
        if (texBigBen != null && texBigBen != texturaFallback) texBigBen.dispose();
        if (texturaFallback != null) texturaFallback.dispose();
    }
}