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
import personajes.Kiri;
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

        if (escenario != null) {
            escenario.reproducirMusica();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (partidaTerminada) {
            return;
        }

        tiempoTranscurridoCombate += delta;

        Array<Rectangle> plataformas = (escenario != null) ? escenario.getPlataformas() : new Array<Rectangle>();

        Aves p1 = (gestorJugadores != null && gestorJugadores.getJugador1() != null) ? gestorJugadores.getJugador1().getPersonaje() : null;
        Aves p2 = (gestorJugadores != null && gestorJugadores.getJugador2() != null) ? gestorJugadores.getJugador2().getPersonaje() : null;

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
                
                // --- COMPROBACIÓN DE ESPECIALES NEUTRALES DE KIRI (Tierra y Aire) ---
                if (p1 instanceof Kiri && p1.getEspecialActual() == Aves.TipoAtaqueEspecial.ESP_NEUTRAL) {
                    p2.recibirAtaqueAereoEspecialKiri(p1.getDanioAtaque(), 1100f); // Elevación terrestre
                } 
                else if (p1 instanceof Kiri && p1.getEspecialActual() == Aves.TipoAtaqueEspecial.ESP_AEREO_NEUTRAL) {
                    p2.recibirAtaqueAereoEspecialKiri(p1.getDanioAtaque(), 750f);  // Elevación aérea (menor altura)
                } 
                else {
                    p2.recibirDanio(p1.getDanioAtaque(), p1.getPosX()); // Daño normal
                }

                p1.ganarEnergiaEspecial(p1.getEspecialAtaqueActual()); 
                p1.setAtaqueProcesado(true);
            }
        }

        // Ataque de Jugador 2 -> Jugador 1
        if (p2.isAtacando() && !p2.isAtaqueProcesado()) {
            if (p2.getHitboxAtaque().overlaps(p1.getHitbox())) {
                
                // --- COMPROBACIÓN DE ESPECIALES NEUTRALES DE KIRI (Tierra y Aire) ---
                if (p2 instanceof Kiri && p2.getEspecialActual() == Aves.TipoAtaqueEspecial.ESP_NEUTRAL) {
                    p1.recibirAtaqueAereoEspecialKiri(p2.getDanioAtaque(), 1100f);
                } 
                else if (p2 instanceof Kiri && p2.getEspecialActual() == Aves.TipoAtaqueEspecial.ESP_AEREO_NEUTRAL) {
                    p1.recibirAtaqueAereoEspecialKiri(p2.getDanioAtaque(), 750f);
                } 
                else {
                    p1.recibirDanio(p2.getDanioAtaque(), p2.getPosX());
                }

                p2.ganarEnergiaEspecial(p2.getEspecialAtaqueActual()); 
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
        
        Gdx.gl.glEnable(GL20.GL_BLEND); 
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); 

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled); 

        float posYVida = viewport.getWorldHeight() - MARGEN_SUPERIOR - ALTO_BARRA; 
        float posYEspecial = posYVida - 22f; 
        float altoEspecial = 14f; 
        float anchoBloque = (ANCHO_BARRA - 10f) / 3f; 

        // -----------------------------------------------------------------
        // JUGADOR 1 (Izquierda)
        // -----------------------------------------------------------------
        float posXJ1 = MARGEN_LATERAL; 
        float porcentajeJ1 = p1.getPorcentajeSalud(); 

        shapeRenderer.setColor(0.15f, 0.15f, 0.15f, 0.8f); 
        shapeRenderer.rect(posXJ1, posYVida, ANCHO_BARRA, ALTO_BARRA); 

        shapeRenderer.setColor(1f - porcentajeJ1, porcentajeJ1, 0.2f, 1f); 
        shapeRenderer.rect(posXJ1, posYVida, ANCHO_BARRA * porcentajeJ1, ALTO_BARRA); 

        float energiaJ1 = p1.getEnergiaEspecial(); 
        for (int i = 0; i < 3; i++) { 
            float xBloque = posXJ1 + (i * (anchoBloque + 5f)); 
            float cargaBloque = Math.min(100f, Math.max(0f, energiaJ1 - (i * 100f))) / 100f; 

            shapeRenderer.setColor(0.1f, 0.1f, 0.1f, 0.8f); 
            shapeRenderer.rect(xBloque, posYEspecial, anchoBloque, altoEspecial); 

            if (cargaBloque > 0) { 
                shapeRenderer.setColor(0f, 0.8f, 1f, 1f); 
                shapeRenderer.rect(xBloque, posYEspecial, anchoBloque * cargaBloque, altoEspecial); 
            }
        }

        // -----------------------------------------------------------------
        // JUGADOR 2 (Derecha)
        // -----------------------------------------------------------------
        float posXJ2 = viewport.getWorldWidth() - MARGEN_LATERAL - ANCHO_BARRA; 
        float porcentajeJ2 = p2.getPorcentajeSalud(); 

        shapeRenderer.setColor(0.15f, 0.15f, 0.15f, 0.8f); 
        shapeRenderer.rect(posXJ2, posYVida, ANCHO_BARRA, ALTO_BARRA); 

        float anchoRellenoJ2 = ANCHO_BARRA * porcentajeJ2; 
        shapeRenderer.setColor(1f - porcentajeJ2, porcentajeJ2, 0.2f, 1f); 
        shapeRenderer.rect(posXJ2 + (ANCHO_BARRA - anchoRellenoJ2), posYVida, anchoRellenoJ2, ALTO_BARRA); 

        float energiaJ2 = p2.getEnergiaEspecial(); 
        for (int i = 0; i < 3; i++) { 
            float xBloque = posXJ2 + ANCHO_BARRA - ((i + 1) * anchoBloque + (i * 5f)); 
            float cargaBloque = Math.min(100f, Math.max(0f, energiaJ2 - (i * 100f))) / 100f; 

            shapeRenderer.setColor(0.1f, 0.1f, 0.1f, 0.8f); 
            shapeRenderer.rect(xBloque, posYEspecial, anchoBloque, altoEspecial); 

            if (cargaBloque > 0) { 
                float anchoCarga = anchoBloque * cargaBloque; 
                shapeRenderer.setColor(0f, 0.8f, 1f, 1f); 
                shapeRenderer.rect(xBloque + (anchoBloque - anchoCarga), posYEspecial, anchoCarga, altoEspecial); 
            }
        }

        shapeRenderer.end(); 

        // -----------------------------------------------------------------
        // MARCOS
        // -----------------------------------------------------------------
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line); 
        shapeRenderer.setColor(Color.WHITE); 

        shapeRenderer.rect(posXJ1, posYVida, ANCHO_BARRA, ALTO_BARRA); 
        shapeRenderer.rect(posXJ2, posYVida, ANCHO_BARRA, ALTO_BARRA); 

        for (int i = 0; i < 3; i++) { 
            float xBloqueJ1 = posXJ1 + (i * (anchoBloque + 5f)); 
            float xBloqueJ2 = posXJ2 + ANCHO_BARRA - ((i + 1) * anchoBloque + (i * 5f)); 
            shapeRenderer.rect(xBloqueJ1, posYEspecial, anchoBloque, altoEspecial); 
            shapeRenderer.rect(xBloqueJ2, posYEspecial, anchoBloque, altoEspecial); 
        }

        shapeRenderer.end(); 
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