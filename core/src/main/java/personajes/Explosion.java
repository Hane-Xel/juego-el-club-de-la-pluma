package personajes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Explosion {
    private float x, y;
    private Rectangle hitbox;
    private Texture texturaSprite;
    private Animation<TextureRegion> animacionExplosion;
    private float stateTime = 0f;
    private boolean activo = false;
    private boolean danioAplicado = false;

    // Constructor que carga un spritesheet y crea la animación
    public Explosion(String rutaSprite, int columnas, int filas, float duracionFrame, float anchoHitbox, float altoHitbox) {
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

            // Creamos la animación en modo NORMAL (no se repite en bucle)
            this.animacionExplosion = new Animation<>(duracionFrame, frames);
            this.animacionExplosion.setPlayMode(Animation.PlayMode.NORMAL);
        }
        
        this.hitbox = new Rectangle(-9999, -9999, anchoHitbox, altoHitbox);
    }

    public void activar(float x, float y) {
        this.x = x;
        this.y = y;
        this.activo = true;
        this.stateTime = 0f;          // Reiniciamos el tiempo de la animación desde el frame 0
        this.danioAplicado = false;    // Reiniciamos el control de daño
        this.hitbox.setPosition(x, y);
    }

    public void actualizar(float delta) {
        if (!activo) return;

        stateTime += delta;

        // Si la animación llega al último fotograma, se desactiva sola
        if (animacionExplosion != null && animacionExplosion.isAnimationFinished(stateTime)) {
            activo = false;
            hitbox.setPosition(-9999, -9999);
        }
    }

    public void dibujar(SpriteBatch batch) {
        if (activo && animacionExplosion != null) {
            // Obtenemos el fotograma actual según el tiempo transcurrido
            TextureRegion frameActual = animacionExplosion.getKeyFrame(stateTime, false);
            batch.draw(frameActual, x, y, hitbox.width, hitbox.height);
        }
    }

    public void dispose() {
        if (texturaSprite != null) {
            texturaSprite.dispose();
        }
    }

    public boolean isActivo() { return activo; }
    public Rectangle getHitbox() { return hitbox; }
    
    public boolean isDanioAplicado() { return danioAplicado; }
    public void setDanioAplicado(boolean danioAplicado) { this.danioAplicado = danioAplicado; }

    public void desactivar() { 
        this.activo = false; 
        this.hitbox.setPosition(-9999, -9999); 
    }
}