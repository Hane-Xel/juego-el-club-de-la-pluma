package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import miempresa.LPOO.juegoPRINCIPAL;
import recursos.GestorRecursos;

public class MenuPrincipal implements Screen {

    private juegoPRINCIPAL juego;
    private SpriteBatch batch;
    private Texture fondo;

    // Componentes para la Interfaz de Usuario (UI)
    private Stage stage;
    private Texture texturaBotonSalir;
    private ImageButton botonSalir;

    public MenuPrincipal(juegoPRINCIPAL juego) {
        this.juego = juego;
        batch = new SpriteBatch();
        
        // Obtener la textura cargada desde GestorRecursos
        fondo = GestorRecursos.getTexture("scenes/menu.png");

        // 1. Inicializar el Stage (contenedor de botones)
        stage = new Stage(new ScreenViewport());

        // 2. Cargar textura del botón (Asegúrate de colocar tu imagen en la carpeta assets)
        texturaBotonSalir = new Texture(Gdx.files.internal("ui/boton_salir.png"));

        // 3. Crear la apariencia gráfica del botón
        TextureRegionDrawable drawableSalir = new TextureRegionDrawable(texturaBotonSalir);
        botonSalir = new ImageButton(drawableSalir);

        // 4. Posicionar el botón en pantalla (X, Y)
        botonSalir.setPosition(100, 100);

        // 5. Asignar el evento al hacer clic
        botonSalir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Acción de salir de la aplicación
                Gdx.app.exit();
            }
        });

        // 6. Agregar el botón al Stage
        stage.addActor(botonSalir);
    }

    @Override
    public void show() {
        // MUY IMPORTANTE: Le indica a LibGDX que el Stage debe procesar la entrada del ratón/teclado
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // Limpiar la pantalla
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Dibujar el fondo con el SpriteBatch habitual
        batch.begin();
        batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        // Actualizar y dibujar los botones/actores del Stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Actualizar el viewport de la interfaz al cambiar el tamaño de la ventana
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
        // Liberar los recursos de esta pantalla
        batch.dispose();
        texturaBotonSalir.dispose();
        stage.dispose();
    }
}