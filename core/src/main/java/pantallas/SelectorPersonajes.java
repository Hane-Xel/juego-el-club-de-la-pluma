package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import miempresa.LPOO.juegoPRINCIPAL;

public class SelectorPersonajes implements Screen {

    private juegoPRINCIPAL juego;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;

    // Control de selección
    private boolean turnoJugador1 = true;
    private String personajeJ1 = "";
    private String personajeJ2 = "";

    // Texturas de botones
    private Texture[] texturasRoster;
    private Texture texturaRandom;
    private ImageButton[] botonesRoster;
    private ImageButton botonRandom;

    // Nombres de los 6 personajes según propuesta
    private final String[] nombresPersonajes = {
        "Kiri", "Corvervance", "Emuans", "Flavia", "Mani", "PG"
    };

    public SelectorPersonajes(juegoPRINCIPAL juego) {
        this.juego = juego;

        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        stage = new Stage(viewport);

        crearGrillaSeleccion();
    }

    private void crearGrillaSeleccion() {
        Table tablaGrilla = new Table();
        tablaGrilla.setFillParent(true);
        tablaGrilla.center();

        texturasRoster = new Texture[6];
        botonesRoster = new ImageButton[6];

        // Cargar los 6 cuadros usando el nombre del personaje + "_cuadro.png"
        for (int i = 0; i < 6; i++) {
            final int index = i;
            
            // Convierte el nombre a minúsculas (ej: "Kiri" -> "kiri_cuadro.png")
            String nombreArchivo = "ui/marcos/"+nombresPersonajes[i].toLowerCase() + "_cuadro.png";
            
            // Si tienes las imágenes dentro de la carpeta assets/ui/ usa "ui/" + nombreArchivo
            texturasRoster[i] = new Texture(Gdx.files.internal(nombreArchivo));
            
            TextureRegionDrawable drawable = new TextureRegionDrawable(texturasRoster[i]);
            botonesRoster[i] = new ImageButton(drawable);

            botonesRoster[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    seleccionarPersonaje(nombresPersonajes[index], index);
                }
            });

            // Agrega al Table: 2 columnas x 3 filas
            tablaGrilla.add(botonesRoster[i]).size(150, 150).pad(10);
            if ((i + 1) % 2 == 0) {
                tablaGrilla.row();
            }
        }

        // Casilla aleatoria (cuadrado verde del boceto)
        texturaRandom = new Texture(Gdx.files.internal("ui/marcos/random_cuadro.png"));
        TextureRegionDrawable drawableRandom = new TextureRegionDrawable(texturaRandom);
        botonRandom = new ImageButton(drawableRandom);

        botonRandom.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                seleccionarAleatorio();
            }
        });

        tablaGrilla.add(botonRandom).colspan(2).size(150, 150).padTop(20);

        stage.addActor(tablaGrilla);
    }

    private void seleccionarPersonaje(String nombre, int index) {
        if (turnoJugador1) {
            personajeJ1 = nombre;
            botonesRoster[index].setDisabled(true); // Bloquear para que J2 no lo elija
            turnoJugador1 = false;
        } else if (!nombre.equals(personajeJ1)) {
            personajeJ2 = nombre;
            // Transición inmediata o activación del botón "Siguiente"
        }
    }

    private void seleccionarAleatorio() {
        int rand;
        do {
            rand = (int) (Math.random() * 6);
        } while (!turnoJugador1 && nombresPersonajes[rand].equals(personajeJ1));

        seleccionarPersonaje(nombresPersonajes[rand], rand);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
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
        texturaRandom.dispose();
        for (Texture t : texturasRoster) {
            if (t != null) t.dispose();
        }
    }
}