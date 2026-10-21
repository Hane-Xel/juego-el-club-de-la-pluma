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
    
    private boolean partidaTerminada = false;
    private float tiempoTranscurridoCombate = 0f;

    // Configuración visual del HUD de vida
    private static final float ANCHO_BARRA = 500f;
    private static final float ALTO_BARRA = 40f;
    private static final float MARGEN_SUPERIOR = 50f;
    private static final float MARGEN_LATERAL = 60f;

    public Combate(juegoPRINCIPAL juego, Aves personajeJ1, Aves personajeJ2, Escena escenario) {
        this.juego = juego;
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();

  
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(1920, 1080, camera);

        
        GestorRecursos.cargarCombate();
        
        
        GestorRecursosPersonajes.cargarPersonaje("ave1", "characters/ave1");

        this.escenario = escenario;

   
        if (escenario != null && escenario.getPosicionSpawnJugador1() != null) {
            personajeJ1.setPosX(escenario.getPosicionSpawnJugador1().x);
            personajeJ1.setPosY(escenario.getPosicionSpawnJugador1().y);
        }

        if (escenario != null && escenario.getPosicionSpawnJugador2() != null) {
            personajeJ2.setPosX(escenario.getPosicionSpawnJugador2().x);
            personajeJ2.setPosY(escenario.getPosicionSpawnJugador2().y);
        }

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

        // Si la partida ya terminó, bloqueamos completamente la ejecución
        if (partidaTerminada) {
            return;
        }

        // Acumulamos tiempo para ignorar la verificación de muerte recién iniciado el combate
        tiempoTranscurridoCombate += delta;

        Array<Rectangle> plataformas = (escenario != null) ? escenario.getPlataformas() : new Array<Rectangle>();

        // Obtener referencias a los personajes de forma segura
        Aves p1 = (gestorJugadores != null && gestorJugadores.getJugador1() != null) ? gestorJugadores.getJugador1().getPersonaje() : null;
        Aves p2 = (gestorJugadores != null && gestorJugadores.getJugador2() != null) ? gestorJugadores.getJugador2().getPersonaje() : null;

        // --- TRANSICIÓN A RESULTADOS AL QUEDARSE SIN VIDA (Solo después de 1 segundo de gracia) ---
        if (tiempoTranscurridoCombate > 1.0f && p1 != null && p2 != null) {
            if (p1.getSalud() <= 0) {
                partidaTerminada = true;
                juego.setScreen(new Resultados(juego, "JUGADOR 2"));
                return;
            } else if (p2.getSalud() <= 0) {
                partidaTerminada = true;
                juego.setScreen(new Resultados(juego, "JUGADOR 1"));
                return;
            }
        }

        // 1. Actualización de física y lógica
        if (gestorJugadores != null) {
            if (p1 != null) {
                p1.actualizar(delta, plataformas);
            }
            
            if (p2 != null) {
                p2.actualizar(delta, plataformas);
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
            if (p1 != null) {
                p1.dibujar(batch);
            }
            if (p2 != null) {
                p2.dibujar(batch);
            }
        }
        batch.end();

        // 3. Renderizado de la interfaz (Barras de Vida)
        dibujarBarrasVida();
    }
    
 // En pantallas/Combate.java:

    private void verificarColisionesAtaque() {
        if (gestorJugadores == null) return;

        Aves p1 = gestorJugadores.getJugador1().getPersonaje();
        Aves p2 = gestorJugadores.getJugador2().getPersonaje();

        if (p1 == null || p2 == null) return;

        // Ataque de Jugador 1 -> Jugador 2
        if (p1.isAtacando() && !p1.isAtaqueProcesado()) {
            if (p1.getHitboxAtaque().overlaps(p2.getHitbox())) {
                p2.recibirDanio(p1.getDanioAtaque(), p1.getPosX());
                p1.ganarEnergiaEspecial(p1.getEspecialAtaqueActual()); // <--- Carga especial a P1 al impactar
                p1.setAtaqueProcesado(true);
            }
        }

        // Ataque de Jugador 2 -> Jugador 1
        if (p2.isAtacando() && !p2.isAtaqueProcesado()) {
            if (p2.getHitboxAtaque().overlaps(p1.getHitbox())) {
                p1.recibirDanio(p2.getDanioAtaque(), p2.getPosX());
                p2.ganarEnergiaEspecial(p2.getEspecialAtaqueActual()); // <--- Carga especial a P2 al impactar
                p2.setAtaqueProcesado(true);
            }
        }
    }

    private void dibujarBarrasVida() {
        if (gestorJugadores == null) return;

        Aves p1 = gestorJugadores.getJugador1().getPersonaje(); //
        Aves p2 = gestorJugadores.getJugador2().getPersonaje(); //

        if (p1 == null || p2 == null) return; //

        shapeRenderer.setProjectionMatrix(camera.combined); //
        
        Gdx.gl.glEnable(GL20.GL_BLEND); //
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); //

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled); //

        float posYVida = viewport.getWorldHeight() - MARGEN_SUPERIOR - ALTO_BARRA; //
        float posYEspecial = posYVida - 22f; //
        float altoEspecial = 14f; //
        float anchoBloque = (ANCHO_BARRA - 10f) / 3f; //

        // -----------------------------------------------------------------
        // JUGADOR 1 (Izquierda)
        // -----------------------------------------------------------------
        float posXJ1 = MARGEN_LATERAL; //
        
        // Normalizado siempre entre 0.0 y 1.0 sin importar si la vida es 100 o 1000
        float porcentajeJ1 = p1.getPorcentajeSalud(); 

        // Fondo Oscuro (Siempre mide ANCHO_BARRA = 500px)
        shapeRenderer.setColor(0.15f, 0.15f, 0.15f, 0.8f); //
        shapeRenderer.rect(posXJ1, posYVida, ANCHO_BARRA, ALTO_BARRA); //

        // Relleno de Vida (Ancho proporcional: 500px * Porcentaje)
        shapeRenderer.setColor(1f - porcentajeJ1, porcentajeJ1, 0.2f, 1f); //
        shapeRenderer.rect(posXJ1, posYVida, ANCHO_BARRA * porcentajeJ1, ALTO_BARRA); //

        // Barra de Especial J1
        float energiaJ1 = p1.getEnergiaEspecial(); //
        for (int i = 0; i < 3; i++) { //
            float xBloque = posXJ1 + (i * (anchoBloque + 5f)); //
            float cargaBloque = Math.min(100f, Math.max(0f, energiaJ1 - (i * 100f))) / 100f; //

            shapeRenderer.setColor(0.1f, 0.1f, 0.1f, 0.8f); //
            shapeRenderer.rect(xBloque, posYEspecial, anchoBloque, altoEspecial); //

            if (cargaBloque > 0) { //
                shapeRenderer.setColor(0f, 0.8f, 1f, 1f); //
                shapeRenderer.rect(xBloque, posYEspecial, anchoBloque * cargaBloque, altoEspecial); //
            }
        }

        // -----------------------------------------------------------------
        // JUGADOR 2 (Derecha)
        // -----------------------------------------------------------------
        float posXJ2 = viewport.getWorldWidth() - MARGEN_LATERAL - ANCHO_BARRA; //
        
        // Normalizado siempre entre 0.0 y 1.0
        float porcentajeJ2 = p2.getPorcentajeSalud(); 

        // Fondo Oscuro (Siempre mide ANCHO_BARRA = 500px)
        shapeRenderer.setColor(0.15f, 0.15f, 0.15f, 0.8f); //
        shapeRenderer.rect(posXJ2, posYVida, ANCHO_BARRA, ALTO_BARRA); //

        // Relleno de Vida (Alineado a la derecha)
        float anchoRellenoJ2 = ANCHO_BARRA * porcentajeJ2; //
        shapeRenderer.setColor(1f - porcentajeJ2, porcentajeJ2, 0.2f, 1f); //
        shapeRenderer.rect(posXJ2 + (ANCHO_BARRA - anchoRellenoJ2), posYVida, anchoRellenoJ2, ALTO_BARRA); //

        // Barra de Especial J2
        float energiaJ2 = p2.getEnergiaEspecial(); //
        for (int i = 0; i < 3; i++) { //
            float xBloque = posXJ2 + ANCHO_BARRA - ((i + 1) * anchoBloque + (i * 5f)); //
            float cargaBloque = Math.min(100f, Math.max(0f, energiaJ2 - (i * 100f))) / 100f; //

            shapeRenderer.setColor(0.1f, 0.1f, 0.1f, 0.8f); //
            shapeRenderer.rect(xBloque, posYEspecial, anchoBloque, altoEspecial); //

            if (cargaBloque > 0) { //
                float anchoCarga = anchoBloque * cargaBloque; //
                shapeRenderer.setColor(0f, 0.8f, 1f, 1f); //
                shapeRenderer.rect(xBloque + (anchoBloque - anchoCarga), posYEspecial, anchoCarga, altoEspecial); //
            }
        }

        shapeRenderer.end(); //

        // -----------------------------------------------------------------
        // MARCOS
        // -----------------------------------------------------------------
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line); //
        shapeRenderer.setColor(Color.WHITE); //

        shapeRenderer.rect(posXJ1, posYVida, ANCHO_BARRA, ALTO_BARRA); //
        shapeRenderer.rect(posXJ2, posYVida, ANCHO_BARRA, ALTO_BARRA); //

        for (int i = 0; i < 3; i++) { //
            float xBloqueJ1 = posXJ1 + (i * (anchoBloque + 5f)); //
            float xBloqueJ2 = posXJ2 + ANCHO_BARRA - ((i + 1) * anchoBloque + (i * 5f)); //
            shapeRenderer.rect(xBloqueJ1, posYEspecial, anchoBloque, altoEspecial); //
            shapeRenderer.rect(xBloqueJ2, posYEspecial, anchoBloque, altoEspecial); //
        }

        shapeRenderer.end(); //
        Gdx.gl.glDisable(GL20.GL_BLEND); //
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