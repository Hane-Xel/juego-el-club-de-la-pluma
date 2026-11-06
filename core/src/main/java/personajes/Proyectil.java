package personajes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import escenarios.Plataforma;

public class Proyectil {
    private float x, y;
    private float velocidadX, velocidadY;
    private float rotacion = 0f; 
    private Rectangle hitbox;
    private Texture texturaSprite;
    private TextureRegion regionSprite;
    
    private Animation<TextureRegion> animacionProyectil;
    private float stateTime = 0f;
    private boolean esAnimado = false;

    private boolean activo = false;
    private float anchoPantalla;

    private boolean esMeteorito = false;
    private Aves objetivoOponente = null;

    public Proyectil(String rutaSprite, float anchoPantalla) {
        this.anchoPantalla = anchoPantalla;
        this.esAnimado = false;
        
        if (Gdx.files.internal(rutaSprite).exists()) {
            this.texturaSprite = new Texture(Gdx.files.internal(rutaSprite));
            this.texturaSprite.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            this.regionSprite = new TextureRegion(this.texturaSprite);
        }
        
        this.hitbox = new Rectangle(0, 0, 25f, 15f);
    }

    public Proyectil(String rutaSprite, int columnas, int filas, float duracionFrame, float anchoPantalla) {
        this.anchoPantalla = anchoPantalla;
        this.esAnimado = true;

        if (Gdx.files.internal(rutaSprite).exists()) {
            this.texturaSprite = new Texture(Gdx.files.internal(rutaSprite));
            this.texturaSprite.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

            int anchoTotal = texturaSprite.getWidth();
            int altoTotal = texturaSprite.getHeight();
            int anchoFrame = anchoTotal / columnas;
            int altoFrame = altoTotal / filas;

            TextureRegion[][] tmp = TextureRegion.split(texturaSprite, anchoFrame, altoFrame);
            TextureRegion[] frames = new TextureRegion[columnas * filas];
            int index = 0;
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    frames[index++] = tmp[i][j];
                }
            }

            this.animacionProyectil = new Animation<>(duracionFrame, frames);
            this.animacionProyectil.setPlayMode(Animation.PlayMode.LOOP);
        }

        this.hitbox = new Rectangle(0, 0, 30f, 35f);
    }

    public void dispararEspecialNeutralAereo(float x, float y, float velocidad, Aves oponente, float anchoPantalla) {
        this.x = x;
        this.y = y;
        this.velocidadX = 0;
        this.velocidadY = velocidad; 
        this.rotacion = 90f;          
        this.activo = true;
        this.esMeteorito = true;
        this.objetivoOponente = oponente;
        this.anchoPantalla = anchoPantalla;
        this.stateTime = 0f;
        this.hitbox.setSize(15f, 25f);
        this.hitbox.setPosition(x, y);
    }

    public void disparar(float x, float y, boolean mirandoDerecha, float velocidad) {
        this.x = x;
        this.y = y;
        this.velocidadX = mirandoDerecha ? velocidad : -velocidad;
        this.velocidadY = 0;
        this.rotacion = 0f; 
        this.activo = true;
        this.stateTime = 0f;
        this.hitbox.setSize(25f, 15f); 
        this.hitbox.setPosition(x, y);

        if (!esAnimado && regionSprite != null) {
            boolean estaVolteadoHaciaIzquierda = regionSprite.isFlipX();
            if (mirandoDerecha && estaVolteadoHaciaIzquierda) {
                regionSprite.flip(true, false);
            } else if (!mirandoDerecha && !estaVolteadoHaciaIzquierda) {
                regionSprite.flip(true, false);
            }
        }
    }

    public void dispararVertical(float x, float y, boolean haciaArriba, float velocidad, float gradosRotacion, float anchoHb, float altoHb) {
        this.x = x;
        this.y = y;
        this.velocidadX = 0;
        this.velocidadY = haciaArriba ? velocidad : -velocidad;
        this.rotacion = gradosRotacion;
        this.activo = true;
        this.stateTime = 0f;
        
        this.hitbox.setSize(anchoHb, altoHb);
        this.hitbox.setPosition(x, y);
    }

    public void actualizar(float delta, Array<Plataforma> plataformas) {
        if (!activo) return;

        stateTime += delta;

        if (esMeteorito && velocidadY > 0 && y >= 1150f) {
            if (objetivoOponente != null) {
                this.x = objetivoOponente.getPosX() + (objetivoOponente.getAncho() / 2f) - (hitbox.width / 2f);
            }
            this.y = 1150f;
            this.velocidadY = -1800f; 
            this.rotacion = 270f;     
            this.esMeteorito = false; 
        }

        x += velocidadX * delta;
        y += velocidadY * delta;
        
        hitbox.setPosition(x, y);

        if (plataformas != null) {
            for (Plataforma p : plataformas) {
                if (hitbox.overlaps(p.getHitbox())) {
                    activo = false;
                    hitbox.setPosition(-9999, -9999);
                    break;
                }
            }
        }

        if (x < 0 || x > anchoPantalla || y < 0 || y > 1200f) {
            activo = false;
            hitbox.setPosition(-9999, -9999);
        }
    }

    public void actualizar(float delta) {
        actualizar(delta, null);
    }

    public void dibujar(SpriteBatch batch) {
        if (activo) {
            TextureRegion frameActual = null;

            if (esAnimado && animacionProyectil != null) {
                frameActual = animacionProyectil.getKeyFrame(stateTime, true);
            } else {
                frameActual = regionSprite;
            }

            if (frameActual != null) {
                batch.draw(
                    frameActual, 
                    x, y, 
                    hitbox.width / 2f, hitbox.height / 2f, 
                    hitbox.width, hitbox.height, 
                    1f, 1f, 
                    rotacion
                );
            }
        }
    }

    public void dispose() {
        if (texturaSprite != null) {
            texturaSprite.dispose();
        }
    }

    public boolean isActivo() { return activo; }
    public Rectangle getHitbox() { return hitbox; }
    
    public void desactivar() { 
        this.activo = false; 
        this.hitbox.setPosition(-9999, -9999); 
    }
}