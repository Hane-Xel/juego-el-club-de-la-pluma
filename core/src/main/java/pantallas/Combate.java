package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music; 
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import controles.Controles;
import escenarios.Escena;
import escenarios.Plataforma;
import jugadores.Jugadores;
import miempresa.LPOO.juegoPRINCIPAL;
import personajes.Aves;
import personajes.Kiri;
import personajes.Emuans;
import recursos.GestorRecursos;
import recursos.GestorRecursosPersonajes;

/**
 * Pantalla principal de Combate. 
 * Controla el bucle del juego, físicas, colisiones de ataques, HUD de vida/energía y menú de pausa.
 */
public class Combate implements Screen {
	
    // --- VARIABLES PARA EL SISTEMA DE PAUSA SUPERPUESTO ---
    private boolean pausado = false;
    private Stage stagePausa;
    private Texture texFondoPausa;
    private BitmapFont fontPausa;

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

    // Configuración visual del HUD de vida y energía
    private static final float ANCHO_BARRA = 500f;
    private static final float ALTO_BARRA = 40f;
    private static final float MARGEN_SUPERIOR = 50f;
    private static final float MARGEN_LATERAL = 60f;

    /**
     * CONSTRUCTOR: Combate
     * QUÉ HACE: Inicializa la escena de lucha, posiciona a los combatientes en base al escenario 
     * y configura el menú de pausa flotante (overlay).
     */
    public Combate(juegoPRINCIPAL juego, Aves personajeJ1, Aves personajeJ2, Escena escenario) {
        this.juego = juego;
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();

        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(1920, 1080, camera);

        // 1. Asignar el escenario recibido por parámetro
        this.escenario = escenario;

        // 2. Cargar los recursos necesarios para el combate
        GestorRecursos.cargarCombate();
        GestorRecursosPersonajes.cargarPersonaje("ave1", "characters/ave1");

        // 3. Posicionar a los personajes usando las coordenadas de spawn reales del escenario[cite: 45]
        if (this.escenario != null) {
            if (this.escenario.getPosicionSpawnJugador1() != null) {
                personajeJ1.setPosX(this.escenario.getPosicionSpawnJugador1().x);
                personajeJ1.setPosY(this.escenario.getPosicionSpawnJugador1().y);
            }

            if (this.escenario.getPosicionSpawnJugador2() != null) {
                personajeJ2.setPosX(this.escenario.getPosicionSpawnJugador2().x);
                personajeJ2.setPosY(this.escenario.getPosicionSpawnJugador2().y);
            }
        }

        // Recupera el gestor global que almacena las teclas personalizadas del usuario[cite: 45]
        this.controles = juego.getGestorControles();
        this.controles.setPersonaje1(personajeJ1);
        this.controles.setPersonaje2(personajeJ2);

        Gdx.input.setInputProcessor(this.controles);
        this.gestorJugadores = new Jugadores(personajeJ1, personajeJ2, this.controles);

        // --- CONFIGURACIÓN DEL MENÚ DE PAUSA SUPERPUESTO (OVERLAY) ---[cite: 45]
        this.stagePausa = new Stage(viewport, batch);
        this.fontPausa = new BitmapFont();
        this.fontPausa.getData().setScale(1.8f);

        if (Gdx.files.internal("scenes/pausa.png").exists()) {
            texFondoPausa = new Texture(Gdx.files.internal("scenes/pausa.png"));
        } else {
            texFondoPausa = GestorRecursos.obtenerTextura("scenes/fondotitulo.png"); 
        }

        Table tablaPausa = new Table();
        tablaPausa.setFillParent(true);
        tablaPausa.center();

        Table panelInterno = new Table();
        if (texFondoPausa != null) {
            panelInterno.background(new TextureRegionDrawable(new TextureRegion(texFondoPausa)));
        }

        TextButton.TextButtonStyle estiloBotonPausa = new TextButton.TextButtonStyle();
        estiloBotonPausa.font = fontPausa;
        estiloBotonPausa.fontColor = Color.YELLOW;

        TextButton btnReanudar = new TextButton("REANUDAR", estiloBotonPausa);
        TextButton btnSelector = new TextButton("CAMBIAR PERSONAJES", estiloBotonPausa);
        TextButton btnMenuPausa = new TextButton("MENU PRINCIPAL", estiloBotonPausa);

        // Listener: Reanudar partida[cite: 45]
        btnReanudar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pausado = false;
                
                Music musicaPausa = GestorRecursos.obtenerMusica(GestorRecursos.MUSICA_PAUSA);
                if (musicaPausa != null && musicaPausa.isPlaying()) {
                    musicaPausa.stop();
                }
                if (escenario != null) {
                    escenario.reproducirMusica();
                }

                Gdx.input.setInputProcessor(controles); 
            }
        });

        // Listener: Volver al selector de personajes[cite: 45]
        btnSelector.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Music musicaPausa = GestorRecursos.obtenerMusica(GestorRecursos.MUSICA_PAUSA);
                if (musicaPausa != null && musicaPausa.isPlaying()) {
                    musicaPausa.stop();
                }
                juego.setScreen(new SelectorPersonajes(juego));
            }
        });

        // Listener: Volver al menú principal[cite: 45]
        btnMenuPausa.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Music musicaPausa = GestorRecursos.obtenerMusica(GestorRecursos.MUSICA_PAUSA);
                if (musicaPausa != null && musicaPausa.isPlaying()) {
                    musicaPausa.stop();
                }
                juego.setScreen(new MenuPrincipal(juego));
            }
        });

        panelInterno.add(btnReanudar).width(480).height(70).padBottom(25).row();
        panelInterno.add(btnSelector).width(480).height(70).padBottom(25).row();
        panelInterno.add(btnMenuPausa).width(480).height(70);

        tablaPausa.add(panelInterno).size(675f, 855f);
        stagePausa.addActor(tablaPausa);
    }

    @Override
    public void show() {
        if (!pausado) {
            Gdx.input.setInputProcessor(this.controles);
        }
        if (escenario != null) {
            escenario.reproducirMusica(); 
        }
    }

    /**
     * FUNCIÓN: render(float delta)
     * QUÉ HACE: Controla el bucle de actualización por fotograma, físicas, entradas, renderizado de gráficos y HUD.
     * CÓMO LO HACE: Detecta si se presiona 'P' para alternar la pausa, actualiza las posiciones y estados de combate,
     * dibuja el escenario y los personajes, y finalmente renderiza las barras de vida y el menú superpuesto si está pausado.
     */
    @Override
    public void render(float delta) {
        
        // --- DETECTAR TECLA 'P' PARA ALTERNAR LA PAUSA Y LA MÚSICA ---[cite: 45]
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.P)) {
            pausado = !pausado;
            
            if (pausado) {
                Gdx.input.setInputProcessor(stagePausa); 
                if (escenario != null) escenario.detenerMusica();
                
                Music musicaPausa = GestorRecursos.obtenerMusica(GestorRecursos.MUSICA_PAUSA);
                if (musicaPausa != null) {
                    musicaPausa.setLooping(true);
                    musicaPausa.setVolume(GestorRecursos.getVolumenGlobal());
                    musicaPausa.play();
                }
            } else {
                Gdx.input.setInputProcessor(controles);  
                
                Music musicaPausa = GestorRecursos.obtenerMusica(GestorRecursos.MUSICA_PAUSA);
                if (musicaPausa != null && musicaPausa.isPlaying()) musicaPausa.stop();
                
                if (escenario != null) escenario.reproducirMusica();
            }
        }

        if (pausado) {
            delta = 0f; // Congela el tiempo de juego si está pausado[cite: 45]
        } else {
            if (delta > 0.1f) delta = 0.1f;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // LÓGICA DE COMBATE EN TIEMPO REAL[cite: 45]
        if (!pausado) {
            if (partidaTerminada) return;

            tiempoTranscurridoCombate += delta;

            Array<Plataforma> plataformas = (escenario != null) ? escenario.getPlataformas() : new Array<Plataforma>();

            Aves p1 = (gestorJugadores != null && gestorJugadores.getJugador1() != null) ? gestorJugadores.getJugador1().getPersonaje() : null;
            Aves p2 = (gestorJugadores != null && gestorJugadores.getJugador2() != null) ? gestorJugadores.getJugador2().getPersonaje() : null;

            // Enlace de referencias cruzadas si hay personajes específicos como Emuans[cite: 45]
            if (p1 instanceof Emuans && p2 != null) ((Emuans) p1).setOponente(p2);
            if (p2 instanceof Emuans && p1 != null) ((Emuans) p2).setOponente(p1);

            // Condición de victoria: si la salud de un jugador llega a 0, termina la partida[cite: 45]
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
                if (p1 != null) p1.actualizar(delta, plataformas);
                if (p2 != null) p2.actualizar(delta, plataformas);
                
                gestorJugadores.actualizar(delta);
                verificarColisionesAtaque(); // Comprueba golpes, proyectiles y explosiones
            }
        }

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        
        // Renderizado visual del escenario y plataformas[cite: 45]
        if (escenario != null) {
            escenario.render(batch, viewport.getWorldWidth(), viewport.getWorldHeight());
            escenario.renderPlataformas(batch);
        }

        Aves p1Visual = (gestorJugadores != null && gestorJugadores.getJugador1() != null) ? gestorJugadores.getJugador1().getPersonaje() : null;
        Aves p2Visual = (gestorJugadores != null && gestorJugadores.getJugador2() != null) ? gestorJugadores.getJugador2().getPersonaje() : null;

        if (p1Visual != null) p1Visual.dibujar(batch);
        if (p2Visual != null) p2Visual.dibujar(batch);
        
        batch.end();

        // Dibuja las barras de vida y energía especiales en pantalla[cite: 45]
        dibujarBarrasVida();

        // Dibuja la interfaz de pausa flotante si está activada
        if (pausado) {
            stagePausa.act(Gdx.graphics.getDeltaTime()); 
            stagePausa.draw();
        }
    }
    
    /**
     * FUNCIÓN: verificarColisionesAtaque()
     * QUÉ HACE: Comprueba en cada fotograma si las hitboxes de golpes, proyectiles, misiles o explosiones 
     * intersectan con los personajes contrarios para aplicar daño, debuffs o calcular energía especial.
     */
    private void verificarColisionesAtaque() {
        if (gestorJugadores == null) return;

        Aves p1 = gestorJugadores.getJugador1().getPersonaje();
        Aves p2 = gestorJugadores.getJugador2().getPersonaje();

        if (p1 == null || p2 == null) return;

        // --- COLISIONES DE PROYECTILES Y EXPLOSIONES (Ej: Emuans) ---[cite: 45]
        if (p2 instanceof Emuans) {
            Emuans emuansP2 = (Emuans) p2;
            if (emuansP2.getProyectilActual() != null && emuansP2.getProyectilActual().isActivo()) {
                if (emuansP2.getProyectilActual().getHitbox().overlaps(p1.getHitbox())) {
                    float danioBase = emuansP2.getDanioAtaque();
                    if (p1.tieneDebuffProyectil()) danioBase *= 1.5f; 
                    p1.recibirDanio(danioBase, emuansP2.getPosX());
                    emuansP2.getProyectilActual().desactivar();
                }
            }
        }

        if (p1 instanceof Emuans) {
            Emuans emuansP1 = (Emuans) p1;
            if (emuansP1.getProyectilActual() != null && emuansP1.getProyectilActual().isActivo()) {
                if (emuansP1.getProyectilActual().getHitbox().overlaps(p2.getHitbox())) {
                    float danioBase = emuansP1.getDanioAtaque();
                    if (p2.tieneDebuffProyectil()) danioBase *= 1.5f; 
                    p2.recibirDanio(danioBase, emuansP1.getPosX());
                    emuansP1.getProyectilActual().desactivar();
                }
            }
        }

        // Comprobación de colisión para las explosiones (incluyendo el doble salto de Emuans)[cite: 45]
        if (p2 instanceof Emuans) {
            Emuans emuansP2 = (Emuans) p2;
            if (emuansP2.getExplosionActual() != null && emuansP2.getExplosionActual().isActivo()) {
                if (!emuansP2.getExplosionActual().isDanioAplicado()) {
                    if (emuansP2.getExplosionActual().getHitbox().overlaps(p1.getHitbox())) {
                        p1.recibirDanio(16f, emuansP2.getPosX());
                        if (emuansP2.getEspecialActual() == Aves.TipoAtaqueEspecial.ESP_ABAJO) {
                            p1.aplicarDebuffProyectil(5.0f);
                        }
                        emuansP2.getExplosionActual().setDanioAplicado(true);
                    }
                }
            }
        }

        if (p1 instanceof Emuans) {
            Emuans emuansP1 = (Emuans) p1;
            if (emuansP1.getExplosionActual() != null && emuansP1.getExplosionActual().isActivo()) {
                if (!emuansP1.getExplosionActual().isDanioAplicado()) {
                    if (emuansP1.getExplosionActual().getHitbox().overlaps(p2.getHitbox())) {
                        p2.recibirDanio(16f, emuansP1.getPosX());
                        if (emuansP1.getEspecialActual() == Aves.TipoAtaqueEspecial.ESP_ABAJO) {
                            p2.aplicarDebuffProyectil(5.0f);
                        }
                        emuansP1.getExplosionActual().setDanioAplicado(true);
                    }
                }
            }
        }

        // --- COLISIONES DE GOLPES CORPORALES / CUERPO A CUERPO ---[cite: 45]
        if (p1.isAtacando() && !p1.isAtaqueProcesado()) {
            if (p1.getHitboxAtaque().overlaps(p2.getHitbox())) {
                if (p1 instanceof Emuans && p1.getEspecialActual() == Aves.TipoAtaqueEspecial.ESP_NEUTRAL) {
                    p2.recibirDanio(p1.getDanioAtaque(), p1.getPosX());
                    p2.aplicarDebuffProyectil(5.0f); 
                } else if (p1 instanceof Kiri && p1.getEspecialActual() == Aves.TipoAtaqueEspecial.ESP_NEUTRAL) {
                    p2.recibirAtaqueAereoEspecialKiri(p1.getDanioAtaque(), 1100f);
                } else {
                    p2.recibirDanio(p1.getDanioAtaque(), p1.getPosX()); 
                }

                p1.ganarEnergiaEspecial(p1.getEspecialAtaqueActual()); 
                p1.setAtaqueProcesado(true);
            }
        }

        if (p2.isAtacando() && !p2.isAtaqueProcesado()) {
            if (p2.getHitboxAtaque().overlaps(p1.getHitbox())) {
                if (p2 instanceof Emuans && p2.getEspecialActual() == Aves.TipoAtaqueEspecial.ESP_NEUTRAL) {
                    p1.recibirDanio(p2.getDanioAtaque(), p2.getPosX());
                    p1.aplicarDebuffProyectil(5.0f); 
                } else if (p2 instanceof Kiri && p2.getEspecialActual() == Aves.TipoAtaqueEspecial.ESP_NEUTRAL) {
                    p1.recibirAtaqueAereoEspecialKiri(p2.getDanioAtaque(), 1100f);
                } else {
                    p1.recibirDanio(p2.getDanioAtaque(), p2.getPosX());
                }

                p2.ganarEnergiaEspecial(p2.getEspecialAtaqueActual()); 
                p2.setAtaqueProcesado(true);
            }
        }
    }

    /**
     * FUNCIÓN: dibujarBarrasVida()
     * QUÉ HACE: Utiliza el ShapeRenderer para renderizar geométricamente las barras de vida superior 
     * y los bloques de energía especial de ambos jugadores[cite: 45].
     */
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

        // HUD Jugador 1 (Izquierda)[cite: 45]
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

        // HUD Jugador 2 (Derecha)[cite: 45]
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

        // Marcos exteriores blancos de las barras[cite: 45]
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line); 
        shapeRenderer.setColor(Color.WHITE); 
        shapeRenderer.rect(posXJ1, posYVida, ANCHO_BARRA, ALTO_BARRA); 
        shapeRenderer.rect(posXJ2, posYVida, ANCHO_BARRA, ALTO_BARRA); 
        shapeRenderer.end(); 

        Gdx.gl.glDisable(GL20.GL_BLEND); 
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        if (stagePausa != null) stagePausa.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void hide() {
        Music musicaPausa = GestorRecursos.obtenerMusica(GestorRecursos.MUSICA_PAUSA);
        if (musicaPausa != null && musicaPausa.isPlaying()) musicaPausa.stop();
        if (escenario != null) escenario.detenerMusica();
    }

    /**
     * FUNCIÓN: dispose()
     * QUÉ HACE: Libera la memoria de los renderizadores, stages y recursos de audio/gráficos al cerrar la pantalla.
     */
    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        if (stagePausa != null) stagePausa.dispose();
        if (fontPausa != null) fontPausa.dispose();
        if (texFondoPausa != null && texFondoPausa != GestorRecursos.obtenerTextura("scenes/fondotitulo.png")) {
            texFondoPausa.dispose();
        }
        Music musicaPausa = GestorRecursos.obtenerMusica(GestorRecursos.MUSICA_PAUSA);
        if (musicaPausa != null && musicaPausa.isPlaying()) musicaPausa.stop();
        if (escenario != null) escenario.detenerMusica();
        
        GestorRecursos.dispose();
        GestorRecursosPersonajes.dispose();
    }
}