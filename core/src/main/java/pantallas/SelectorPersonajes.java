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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.HashMap;
import java.util.Map;

import escenarios.Escena;
import escenarios.Playa;
import miempresa.LPOO.juegoPRINCIPAL;
import personajes.Aves;
import recursos.GestorRecursos;

public class SelectorPersonajes implements Screen {

    private juegoPRINCIPAL juego;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;

    // Control de selección
    private boolean turnoJugador1 = true;
    private boolean seleccionCompleta = false;
    private String personajeJ1 = "";
    private String personajeJ2 = "";
    private int indexJ1 = -1;
    private int indexJ2 = -1;
    private int indexHover = -1;

    // Fuentes y UI
    private BitmapFont fuenteTitulo;
    private BitmapFont fuenteNormal;
    private Label labelEstado;

    // Componentes del Panel Jugador 1
    private Image imgGranJ1;
    private Label lblNombreJ1;
    private Label lblAtaqueJ1;
    private Label lblVelocidadJ1;
    private Label lblVidaJ1;

    // Componentes del Panel Jugador 2
    private Image imgGranJ2;
    private Label lblNombreJ2;
    private Label lblAtaqueJ2;
    private Label lblVelocidadJ2;
    private Label lblVidaJ2;

    // Botón para iniciar combate
    private TextButton btnIniciarCombate;

    // Auras / Bordes de selección
    private Texture texAuraJ1;
    private Texture texAuraJ2;
    private Texture texAuraHover;
    private Image[] aurasJ1;
    private Image[] aurasJ2;
    private Image[] aurasHover;

    // Texturas de botones y personajes
    private Texture[] texturasRoster;
    private Texture[] texturasAvesGrandes;
    private Texture texturaRandom;

    // Nombres de los 6 personajes según propuesta
    private final String[] nombresPersonajes = {
        "Kiri", "Corvervance", "Emuans", "Flavia", "Mani", "PG"
    };

    // Estructura para estadísticas de cada ave
    private static class Stats {
        int ataque, velocidad, vida;
        Stats(int a, int v, int hp) {
            this.ataque = a;
            this.velocidad = v;
            this.vida = hp;
        }
    }

    private final Map<String, Stats> statsPersonajes = new HashMap<>();

    public SelectorPersonajes(juegoPRINCIPAL juego) {
        this.juego = juego;

        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(1920, 1080, camera);
        this.stage = new Stage(viewport);

        inicializarEstadisticas();
        crearTexturasAuras();
        crearFuentes();
        construirInterfaz();
    }

    // Mapeo corregido para archivos con nombres acortados o diferentes
    private String obtenerNombreArchivoCuadro(String nombre) {
        if (nombre.equalsIgnoreCase("Corvervance")) {
            return "corv"; // corv_cuadro.png
        }
        if (nombre.equalsIgnoreCase("Mani")) {
            return "martin"; // Martin_cuadro.png
        }
        return nombre.toLowerCase();
    }

    private void inicializarEstadisticas() {
        statsPersonajes.put("Kiri", new Stats(80, 85, 100));
        statsPersonajes.put("Corvervance", new Stats(90, 60, 120));
        statsPersonajes.put("Emuans", new Stats(65, 95, 90));
        statsPersonajes.put("Flavia", new Stats(75, 80, 110));
        statsPersonajes.put("Mani", new Stats(95, 50, 130));
        statsPersonajes.put("PG", new Stats(85, 88, 95));
        statsPersonajes.put("Aleatorio", new Stats(0, 0, 0));
    }

    private void crearTexturasAuras() {
        // Aura Azul para Jugador 1
        texAuraJ1 = crearTexturaBorde(150, 150, 8, new Color(0.1f, 0.6f, 1.0f, 1.0f));
        // Aura Roja para Jugador 2
        texAuraJ2 = crearTexturaBorde(150, 150, 8, new Color(1.0f, 0.2f, 0.2f, 1.0f));
        // Aura Hover (Amarillo)
        texAuraHover = crearTexturaBorde(150, 150, 4, new Color(1.0f, 0.9f, 0.3f, 0.8f));
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

        fuenteNormal = new BitmapFont();
        fuenteNormal.getData().setScale(1.8f);
    }

    private void construirInterfaz() {
        Table tablaPrincipal = new Table();
        tablaPrincipal.setFillParent(true);
        tablaPrincipal.center();

        Label.LabelStyle estiloTitulo = new Label.LabelStyle(fuenteTitulo, Color.ORANGE);
        labelEstado = new Label("TURNO: JUGADOR 1 (AZUL)", estiloTitulo);
        labelEstado.setAlignment(Align.center);

        Table panelJ1 = crearPanelJugador("JUGADOR 1", new Color(0.2f, 0.6f, 1.0f, 1.0f), 1);
        Table panelJ2 = crearPanelJugador("JUGADOR 2", new Color(1.0f, 0.3f, 0.3f, 1.0f), 2);
        Table grillaCentral = crearGrillaSeleccion();

        Texture texBtn = crearTexturaBorde(300, 70, 4, Color.GOLD);
        TextButton.TextButtonStyle estiloBoton = new TextButton.TextButtonStyle();
        estiloBoton.font = fuenteNormal;
        estiloBoton.fontColor = Color.GOLD;
        estiloBoton.up = new TextureRegionDrawable(new TextureRegion(texBtn));

        btnIniciarCombate = new TextButton("¡LUCHAR!", estiloBoton);
        btnIniciarCombate.setVisible(false);
        btnIniciarCombate.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                iniciarCombate();
            }
        });

        tablaPrincipal.add(labelEstado).colspan(3).padBottom(30).row();
        tablaPrincipal.add(panelJ1).width(420).pad(20).top();
        tablaPrincipal.add(grillaCentral).width(600).pad(10).top();
        tablaPrincipal.add(panelJ2).width(420).pad(20).top().row();
        tablaPrincipal.add(btnIniciarCombate).colspan(3).width(300).height(70).padTop(20);

        stage.addActor(tablaPrincipal);
    }

    private Table crearPanelJugador(String titulo, Color colorHeader, int numeroJugador) {
        Table panel = new Table();

        Label.LabelStyle estiloHeader = new Label.LabelStyle(fuenteTitulo, colorHeader);
        Label.LabelStyle estiloLabel = new Label.LabelStyle(fuenteNormal, Color.WHITE);

        Label lblTitulo = new Label(titulo, estiloHeader);
        Image imgPreview = new Image();
        Label lblNombre = new Label("-", estiloLabel);

        Label lblVida = new Label("Vida: -", estiloLabel);
        Label lblAtaque = new Label("Ataque: -", estiloLabel);
        Label lblVelocidad = new Label("Velocidad: -", estiloLabel);

        panel.add(lblTitulo).padBottom(15).row();
        panel.add(imgPreview).size(220, 220).padBottom(15).row();
        panel.add(lblNombre).padBottom(20).row();
        panel.add(lblVida).left().padBottom(5).row();
        panel.add(lblAtaque).left().padBottom(5).row();
        panel.add(lblVelocidad).left().padBottom(5).row();

        if (numeroJugador == 1) {
            imgGranJ1 = imgPreview;
            lblNombreJ1 = lblNombre;
            lblVidaJ1 = lblVida;
            lblAtaqueJ1 = lblAtaque;
            lblVelocidadJ1 = lblVelocidad;
        } else {
            imgGranJ2 = imgPreview;
            lblNombreJ2 = lblNombre;
            lblVidaJ2 = lblVida;
            lblAtaqueJ2 = lblAtaque;
            lblVelocidadJ2 = lblVelocidad;
        }

        return panel;
    }

    private Table crearGrillaSeleccion() {
        Table tablaGrilla = new Table();

        texturasRoster = new Texture[6];
        texturasAvesGrandes = new Texture[6];
        aurasJ1 = new Image[7];
        aurasJ2 = new Image[7];
        aurasHover = new Image[7];

        // Se carga primero la textura aleatoria por si se necesita de fallback
        texturaRandom = new Texture(Gdx.files.internal("ui/marcos/random_cuadro.png"));

        for (int i = 0; i < 6; i++) {
            final int index = i;
            String nombre = nombresPersonajes[i];

            String nombreArchivo = obtenerNombreArchivoCuadro(nombre);
            
            // Intenta cargar la ruta en minúscula
            String pathMarco = "ui/marcos/" + nombreArchivo.toLowerCase() + "_cuadro.png";
            
            // Si no existe, intenta con la primera letra en mayúscula (ej: Martin_cuadro.png)
            if (!Gdx.files.internal(pathMarco).exists()) {
                String primeraMayus = Character.toUpperCase(nombreArchivo.charAt(0)) + nombreArchivo.substring(1);
                pathMarco = "ui/marcos/" + primeraMayus + "_cuadro.png";
            }

            // Cargar la textura del marco si existe, o usar la textura random de fallback
            if (Gdx.files.internal(pathMarco).exists()) {
                texturasRoster[i] = new Texture(Gdx.files.internal(pathMarco));
            } else {
                texturasRoster[i] = texturaRandom;
            }

            // Imagen grande
            String pathGrande = "characters/" + nombre + ".png";
            if (Gdx.files.internal(pathGrande).exists()) {
                texturasAvesGrandes[i] = new Texture(Gdx.files.internal(pathGrande));
            } else {
                texturasAvesGrandes[i] = texturasRoster[i];
            }

            Image imgMarco = new Image(texturasRoster[i]);
            Stack stackCell = crearStackConAuras(imgMarco, index);

            stackCell.addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    indexHover = index;
                    actualizarPrevisualizacion(nombresPersonajes[index], index);
                    actualizarAuras();
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    indexHover = -1;
                    actualizarAuras();
                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    seleccionarPersonaje(nombresPersonajes[index], index);
                }
            });

            tablaGrilla.add(stackCell).size(150, 150).pad(10);
            if ((i + 1) % 2 == 0) {
                tablaGrilla.row();
            }
        }

        Image imgRandom = new Image(texturaRandom);
        Stack stackRandom = crearStackConAuras(imgRandom, 6);

        stackRandom.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                indexHover = 6;
                actualizarAuras();
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                indexHover = -1;
                actualizarAuras();
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                seleccionarAleatorio();
            }
        });

        tablaGrilla.add(stackRandom).colspan(2).size(150, 150).padTop(15);

        return tablaGrilla;
    }

    private Stack crearStackConAuras(Image imgBase, int index) {
        Stack stack = new Stack();

        aurasHover[index] = new Image(texAuraHover);
        aurasJ1[index] = new Image(texAuraJ1);
        aurasJ2[index] = new Image(texAuraJ2);

        aurasHover[index].setVisible(false);
        aurasJ1[index].setVisible(false);
        aurasJ2[index].setVisible(false);

        stack.add(imgBase);
        stack.add(aurasHover[index]);
        stack.add(aurasJ1[index]);
        stack.add(aurasJ2[index]);

        return stack;
    }

    private void actualizarPrevisualizacion(String nombre, int index) {
        Stats stats = statsPersonajes.get(nombre);
        Texture texGrande = (index >= 0 && index < 6) ? texturasAvesGrandes[index] : texturaRandom;

        if (turnoJugador1 && indexJ1 == -1) {
            imgGranJ1.setDrawable(new TextureRegionDrawable(new TextureRegion(texGrande)));
            lblNombreJ1.setText(nombre);
            lblVidaJ1.setText("Vida: " + stats.vida);
            lblAtaqueJ1.setText("Ataque: " + stats.ataque);
            lblVelocidadJ1.setText("Velocidad: " + stats.velocidad);
        } else if (!turnoJugador1 && indexJ2 == -1) {
            imgGranJ2.setDrawable(new TextureRegionDrawable(new TextureRegion(texGrande)));
            lblNombreJ2.setText(nombre);
            lblVidaJ2.setText("Vida: " + stats.vida);
            lblAtaqueJ2.setText("Ataque: " + stats.ataque);
            lblVelocidadJ2.setText("Velocidad: " + stats.velocidad);
        }
    }

    private void seleccionarPersonaje(String nombre, int index) {
        if (seleccionCompleta) return;

        if (turnoJugador1) {
            personajeJ1 = nombre;
            indexJ1 = index;
            actualizarPrevisualizacion(nombre, index);
            turnoJugador1 = false;
            labelEstado.setText("TURNO: JUGADOR 2 (ROJO)");
            labelEstado.setColor(Color.RED);
        } else if (index != indexJ1) {
            personajeJ2 = nombre;
            indexJ2 = index;
            actualizarPrevisualizacion(nombre, index);
            seleccionCompleta = true;
            labelEstado.setText("¡SELECCIÓN COMPLETA!");
            labelEstado.setColor(Color.GREEN);
            btnIniciarCombate.setVisible(true);
        }

        actualizarAuras();
    }

    private void seleccionarAleatorio() {
        int rand;
        do {
            rand = (int) (Math.random() * 6);
        } while (rand == indexJ1);

        seleccionarPersonaje(nombresPersonajes[rand], rand);
    }

    private void actualizarAuras() {
        for (int i = 0; i < 7; i++) {
            if (aurasJ1[i] != null) aurasJ1[i].setVisible(i == indexJ1);
            if (aurasJ2[i] != null) aurasJ2[i].setVisible(i == indexJ2);

            boolean esHoverable = (i == indexHover) && (i != indexJ1) && (i != indexJ2);
            if (aurasHover[i] != null) aurasHover[i].setVisible(esHoverable);
        }
    }

    private void iniciarCombate() {
        GestorRecursos.cargarCombate();

        Aves jugador1 = new Aves(personajeJ1, 0, 0, "characters/" + personajeJ1 + ".png");
        Aves jugador2 = new Aves(personajeJ2, 0, 0, "characters/" + personajeJ2 + ".png");
        Escena escenarioPlaya = new Playa();

        juego.setScreen(new Combate(juego, jugador1, jugador2, escenarioPlaya));
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

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        if (texAuraJ1 != null) texAuraJ1.dispose();
        if (texAuraJ2 != null) texAuraJ2.dispose();
        if (texAuraHover != null) texAuraHover.dispose();
        if (texturaRandom != null) texturaRandom.dispose();
        if (fuenteTitulo != null) fuenteTitulo.dispose();
        if (fuenteNormal != null) fuenteNormal.dispose();

        if (texturasRoster != null) {
            for (Texture t : texturasRoster) {
                if (t != null && t != texturaRandom) t.dispose();
            }
        }
        if (texturasAvesGrandes != null) {
            for (Texture t : texturasAvesGrandes) {
                if (t != null && t != texturaRandom) t.dispose();
            }
        }
    }
}