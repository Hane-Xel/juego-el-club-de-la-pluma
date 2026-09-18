package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import controles.Controles;
import escenarios.Escena;
import jugadores.Jugadores;
import miempresa.LPOO.juegoPRINCIPAL;
import personajes.Aves;
import recursos.GestorRecursos;
import recursos.GestorRecursosPersonajes;

public class Combate implements Screen {

    private juegoPRINCIPAL juego;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private Viewport viewport;

    private Controles controles;
    private Jugadores gestorJugadores;
    private Escena escenario;

    // Configuración visual del HUD de vida
    private static final float ANCHO_BARRA = 500f;
    private static final float ALTO_BARRA = 40f;
    private static final float MARGEN_SUPERIOR = 50f;
    private static final float MARGEN_LATERAL = 60f;

    public Combate(juegoPRINCIPAL juego, Aves personajeJ1, Aves personajeJ2, Escena escenario) {
        this.juego = juego;
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();

        // Configuración del Viewport base (1920x1080)
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(1920, 1080, camera);

        // Carga de activos
        GestorRecursos.cargarCombate();
        GestorRecursosPersonajes.cargarAnimaciones();

        this.escenario = escenario;

        // Asignación de posiciones iniciales de aparición (Spawns)
        if (escenario.getPosicionSpawnJugador1() != null) {
            personajeJ1.setPosX(escenario.getPosicionSpawnJugador1().x);
            personajeJ1.setPosY(escenario.getPosicionSpawnJugador1().y);
        }

        if (escenario.getPosicionSpawnJugador2() != null) {
            personajeJ2.setPosX(escenario.getPosicionSpawnJugador2().x);
            personajeJ2.setPosY(escenario.getPosicionSpawnJugador2().y);
        }

        // Configuración e inicialización de entradas e instancias de jugadores
        this.controles = new Controles(personajeJ1, personajeJ2);
        Gdx.input.setInputProcessor(this.controles);
        this.gestorJugadores = new Jugadores(personajeJ1, personajeJ2, this.controles);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.controles);

        // Inicia la música asociada al escenario seleccionado
        if (escenario != null) {
            escenario.reproducirMusica();
        }
    }

    @Override
    public void render(float delta) {
        // Limpieza de pantalla
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Array<Rectangle> plataformas = (escenario != null) ? escenario.getPlataformas() : new Array<Rectangle>();

        // 1. Actualización de física y lógica
        if (gestorJugadores != null) {
            if (gestorJugadores.getJugador1() != null && gestorJugadores.getJugador1().getPersonaje() != null) {
                gestorJugadores.getJugador1().getPersonaje().actualizar(delta, plataformas);
            }
            
            if (gestorJugadores.getJugador2() != null && gestorJugadores.getJugador2().getPersonaje() != null) {
                gestorJugadores.getJugador2().getPersonaje().actualizar(delta, plataformas);
            }
            
            gestorJugadores.actualizar(delta);
            verificarColisionesAtaque();
        }

        camera.update();

        // 2. Renderizado del mapa y personajes
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        
        if (escenario != null) {
            escenario.render(batch, viewport.getWorldWidth(), viewport.getWorldHeight());
        }

        if (gestorJugadores != null) {
            if (gestorJugadores.getJugador1() != null && gestorJugadores.getJugador1().getPersonaje() != null) {
                gestorJugadores.getJugador1().getPersonaje().dibujar(batch);
            }
            if (gestorJugadores.getJugador2() != null && gestorJugadores.getJugador2().getPersonaje() != null) {
                gestorJugadores.getJugador2().getPersonaje().dibujar(batch);
            }
        }
        batch.end();

        // 3. Renderizado de la interfaz (Barras de Vida)
        dibujarBarrasVida();
    }

    private void verificarColisionesAtaque() {
        if (gestorJugadores == null) return;

        Aves p1 = gestorJugadores.getJugador1().getPersonaje();
        Aves p2 = gestorJugadores.getJugador2().getPersonaje();

        if (p1 == null || p2 == null) return;

        // Ataque de Jugador 1 -> Jugador 2
        if (p1.isAtacando() && !p1.isAtaqueProcesado()) {
            if (p1.getHitboxAtaque().overlaps(p2.getHitbox())) {
                p2.recibirDanio(p1.getDanioAtaque(), p1.getPosX());
                p1.setAtaqueProcesado(true);
            }
        }

        // Ataque de Jugador 2 -> Jugador 1
        if (p2.isAtacando() && !p2.isAtaqueProcesado()) {
            if (p2.getHitboxAtaque().overlaps(p1.getHitbox())) {
                p1.recibirDanio(p2.getDanioAtaque(), p2.getPosX());
                p2.setAtaqueProcesado(true);
            }
        }
    }

    private void dibujarBarrasVida() {
        if (gestorJugadores == null) return;

        Aves p1 = gestorJugadores.getJugador1().getPersonaje();
        Aves p2 = gestorJugadores.getJugador2().getPersonaje();

        if (p1 == null || p2 == null) return;

        shapeRenderer.setProjectionMatrix(camera.combined);
        
        // Habilitar mezcla de transparencias (Blend)
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        float posY = viewport.getWorldHeight() - MARGEN_SUPERIOR - ALTO_BARRA;

        // --- BARRA JUGADOR 1 (Esquina Izquierda) ---
        float posXJ1 = MARGEN_LATERAL;
        float porcentajeJ1 = Math.max(0, p1.getSalud() / p1.getSaludMaxima());

        // Fondo traslúcido
        shapeRenderer.setColor(0.15f, 0.15f, 0.15f, 0.8f);
        shapeRenderer.rect(posXJ1, posY, ANCHO_BARRA, ALTO_BARRA);

        // Vida activa (Varía el color dinámicamente)
        shapeRenderer.setColor(1f - porcentajeJ1, porcentajeJ1, 0.2f, 1f);
        shapeRenderer.rect(posXJ1, posY, ANCHO_BARRA * porcentajeJ1, ALTO_BARRA);

        // --- BARRA JUGADOR 2 (Esquina Derecha) ---
        float posXJ2 = viewport.getWorldWidth() - MARGEN_LATERAL - ANCHO_BARRA;
        float porcentajeJ2 = Math.max(0, p2.getSalud() / p2.getSaludMaxima());

        // Fondo traslúcido
        shapeRenderer.setColor(0.15f, 0.15f, 0.15f, 0.8f);
        shapeRenderer.rect(posXJ2, posY, ANCHO_BARRA, ALTO_BARRA);

        // Vida activa (Se decrementa hacia la derecha)
        float anchoRellenoJ2 = ANCHO_BARRA * porcentajeJ2;
        shapeRenderer.setColor(1f - porcentajeJ2, porcentajeJ2, 0.2f, 1f);
        shapeRenderer.rect(posXJ2 + (ANCHO_BARRA - anchoRellenoJ2), posY, anchoRellenoJ2, ALTO_BARRA);

        shapeRenderer.end();

        // Marco contenedor blanco
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(posXJ1, posY, ANCHO_BARRA, ALTO_BARRA);
        shapeRenderer.rect(posXJ2, posY, ANCHO_BARRA, ALTO_BARRA);
        shapeRenderer.end();

        // Deshabilitar transparencia
        Gdx.gl.glDisable(GL20.GL_BLEND);
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
    public void hide() {
        if (escenario != null) {
            escenario.detenerMusica();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        if (escenario != null) {
            escenario.detenerMusica();
        }
        GestorRecursos.dispose();
        GestorRecursosPersonajes.dispose();
    }
}