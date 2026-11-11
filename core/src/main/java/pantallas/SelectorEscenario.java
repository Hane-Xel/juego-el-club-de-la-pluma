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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

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

public class SelectorEscenario implements Screen {

    private juegoPRINCIPAL juego;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;

    private String personajeJ1;
    private String personajeJ2;

    private BitmapFont fuenteTitulo;
    
    // Texturas para las miniaturas de los escenarios
    private Texture texGranmont;
    private Texture texPlaya;
    private Texture texDojo;
    private Texture texturaFallback; // Por si alguna imagen no existe

    public SelectorEscenario(juegoPRINCIPAL juego, String personajeJ1, String personajeJ2) {
        this.juego = juego;
        this.personajeJ1 = personajeJ1;
        this.personajeJ2 = personajeJ2;

        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(1920, 1080, camera);
        this.stage = new Stage(viewport);

        cargarTexturasEscenarios();
        crearFuentes();
        construirInterfaz();
    }

    private void cargarTexturasEscenarios() {
        // Textura por defecto de respaldo (un cuadro gris) si falta alguna imagen
        texturaFallback = crearTexturaBorde(300, 180, 4, Color.GRAY);

        // Granmont
        if (Gdx.files.internal("ui/marcos/granmont_cuadro.png").exists()) {
            texGranmont = new Texture(Gdx.files.internal("ui/marcos/granmont_cuadro.png"));
        } else if (Gdx.files.internal("scenes/granmont.png").exists()) {
            texGranmont = new Texture(Gdx.files.internal("scenes/granmont.png"));
        } else {
            texGranmont = texturaFallback;
        }

        // Playa
        if (Gdx.files.internal("ui/marcos/playa_cuadro.png").exists()) {
            texPlaya = new Texture(Gdx.files.internal("ui/marcos/playa_cuadro.png"));
        } else if (Gdx.files.internal("scenes/playa.png").exists()) {
            texPlaya = new Texture(Gdx.files.internal("scenes/playa.png"));
        } else {
            texPlaya = texturaFallback;
        }

        // Dojo
        if (Gdx.files.internal("ui/marcos/dojo_cuadro.png").exists()) {
            texDojo = new Texture(Gdx.files.internal("ui/marcos/dojo_cuadro.png"));
        } else if (Gdx.files.internal("scenes/dojo.png").exists()) {
            texDojo = new Texture(Gdx.files.internal("scenes/dojo.png"));
        } else {
            texDojo = texturaFallback;
        }
    }

    private Texture crearTexturaBorde(int ancho, int alto, int grosor, Color color) {
        Pixmap pixmap = new Pixmap(ancho, alto, Pixmap.Format.RGBA8888);
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
        tabla.setFillParent(true);
        tabla.center();

        Label.LabelStyle estiloTitulo = new Label.LabelStyle(fuenteTitulo, Color.GOLD);
        Label lblTitulo = new Label("SELECCIONA UN ESCENARIO", estiloTitulo);
        lblTitulo.setAlignment(Align.center);

        // Crear las imágenes interactivas para cada escenario
        Image imgGranmont = new Image(texGranmont);
        imgGranmont.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                iniciarCombate(new Granmont());
            }
        });

        Image imgPlaya = new Image(texPlaya);
        imgPlaya.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                iniciarCombate(new Playa());
            }
        });

        Image imgDojo = new Image(texDojo);
        imgDojo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                iniciarCombate(new Dojo());
            }
        });

        // Tabla interna para organizar las imágenes de forma horizontal o en grilla ordenadamente
        Table tablaOpciones = new Table();
        tablaOpciones.add(imgGranmont).size(350, 200).pad(20);
        tablaOpciones.add(imgPlaya).size(350, 200).pad(20);
        tablaOpciones.add(imgDojo).size(350, 200).pad(20);

        tabla.add(lblTitulo).padBottom(50).row();
        tabla.add(tablaOpciones);

        stage.addActor(tabla);
    }

    private void iniciarCombate(Escena escenarioSeleccionado) {
        GestorRecursos.cargarCombate();
        GestorRecursos.manager.finishLoading();

        GestorRecursosPersonajes.cargarPersonaje(personajeJ1.toLowerCase(), "characters/" + personajeJ1.toLowerCase());
        GestorRecursosPersonajes.cargarPersonaje(personajeJ2.toLowerCase(), "characters/" + personajeJ2.toLowerCase());

        Aves jugador1 = crearInstanciaAve(personajeJ1, 300f, 500f);
        Aves jugador2 = crearInstanciaAve(personajeJ2, 1500f, 500f);

        juego.setScreen(new Combate(juego, jugador1, jugador2, escenarioSeleccionado));
    }

    private Aves crearInstanciaAve(String nombre, float x, float y) {
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
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.08f, 0.08f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
        stage.dispose();
        if (fuenteTitulo != null) fuenteTitulo.dispose();
        if (texGranmont != null && texGranmont != texturaFallback) texGranmont.dispose();
        if (texPlaya != null && texPlaya != texturaFallback) texPlaya.dispose();
        if (texDojo != null && texDojo != texturaFallback);
        	
        	
    }
}