package escenarios;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import personajes.Aves;

public class Plataforma {
    private Rectangle hitbox;
    private Texture textura;
    private boolean traspasablePorAbajo;
    
    // Desfases opcionales para alinear la hitbox con el dibujo de la textura
    private float offsetXHitbox; 
    private float offsetXVisual;
    private float offsetYVisual;

    public Plataforma(float x, float y, float anchoHitbox, float altoHitbox, Texture textura, boolean traspasablePorAbajo) {
        // Margen opcional para ajustar la hitbox (por ejemplo, si quieres que empiece un poco antes)
        this.offsetXHitbox = -40f; 
        
        // La zona física (hitbox) mantiene el tamaño que tú decidas al crearla
        this.hitbox = new Rectangle(x - offsetXHitbox, y, anchoHitbox, altoHitbox);
        this.textura = textura;
        this.traspasablePorAbajo = traspasablePorAbajo;
        
        // Inicializamos los desfases visuales en 0 por defecto
        this.offsetXVisual = 0f;
        this.offsetYVisual = 0f;
    }

    /**
     * Constructor avanzado si quieres ajustar manualmente dónde se dibuja la textura 
     * respecto a la posición lógica de la hitbox.
     */
    public Plataforma(float x, float y, float anchoHitbox, float altoHitbox, Texture textura, boolean traspasablePorAbajo, float offsetXVisual, float offsetYVisual) {
        this(x, y, anchoHitbox, altoHitbox, textura, traspasablePorAbajo);
        this.offsetXVisual = offsetXVisual;
        this.offsetYVisual = offsetYVisual;
    }

    public void verificarColision(Aves personaje) {
        if (personaje == null) return;// para que no explote todo

        Rectangle pHitbox = personaje.getHitbox();
        float velocidadY = personaje.getVelocidadY();

        if (pHitbox.overlaps(hitbox)) {// si las hitbox se estan tocando
            float topPlataforma = hitbox.y + hitbox.height;//calcula la parte de arriba de la plataforma

            if (traspasablePorAbajo) {
                // Lógica para nubes (solo te frenas si vas cayendo y caes justo por encima)
                if (velocidadY <= 0 && pHitbox.y >= topPlataforma - 25f) { // si el personaje esta por encima de la plataforma y su velocidad es positiva
                    personaje.setPosY(topPlataforma);// la hitbox del personaje se queda arriba
                    personaje.setVelocidadY(0);// la velocidad se para
                    personaje.setEnElSuelo(true);
                }
            } else {
                // Lógica para plataformas SÓLIDAS (Suelo principal)
                if (velocidadY <= 0 && pHitbox.y >= topPlataforma - 30f) {// si el personaje esta por encima de la plataforma
                    personaje.setPosY(topPlataforma);// la hitbox del personaje se queda arriba
                    personaje.setVelocidadY(0);// la velocidad se para
                    personaje.setEnElSuelo(true);
                }
            }
        }
    }

    public void render(SpriteBatch batch) {
        if (textura != null) {
            // DIBUJADO USANDO EL TAMAÑO REAL DE LA TEXTURA:
            // Usamos textura.getWidth() y textura.getHeight() en lugar de estirarla.
            float posXDibujo = hitbox.x + offsetXHitbox + offsetXVisual;
            float posYDibujo = hitbox.y + offsetYVisual;
            
            batch.draw(textura, posXDibujo, posYDibujo, textura.getWidth(), textura.getHeight());
        }
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}