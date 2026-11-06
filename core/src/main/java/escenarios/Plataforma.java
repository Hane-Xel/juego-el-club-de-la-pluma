package escenarios;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import personajes.Aves;

public class Plataforma {
    private Rectangle hitbox;
    private Texture textura;
    private boolean traspasablePorAbajo;
    private float offsetX; // Desfase opcional para alinear la hitbox

    public Plataforma(float x, float y, float ancho, float alto, Texture textura, boolean traspasablePorAbajo) {
        // Si quieres que la hitbox empiece X píxeles antes (por ejemplo, 10 píxeles antes):
        float margenHitboxAntes = -40f; 
        
        this.offsetX = margenHitboxAntes;
        // La hitbox se desplaza hacia la izquierda esos píxeles
        this.hitbox = new Rectangle(x - offsetX, y, ancho, alto);
        this.textura = textura;
        this.traspasablePorAbajo = traspasablePorAbajo;
    }

    public void verificarColision(Aves personaje) {
        if (personaje == null) return;

        Rectangle pHitbox = personaje.getHitbox();
        float velocidadY = personaje.getVelocidadY();

        if (pHitbox.overlaps(hitbox)) {
            float topPlataforma = hitbox.y + hitbox.height;

            if (traspasablePorAbajo) {
                if (velocidadY <= 0 && pHitbox.y >= topPlataforma - 25f) {
                    personaje.setPosY(topPlataforma);
                    personaje.setVelocidadY(0);
                    personaje.setEnElSuelo(true);
                }
            } else {
                if (velocidadY <= 0 && pHitbox.y >= topPlataforma - 30f) {
                    personaje.setPosY(topPlataforma);
                    personaje.setVelocidadY(0);
                    personaje.setEnElSuelo(true);
                }
            }
        }
    }

    public void render(SpriteBatch batch) {
        if (textura != null) {
            // Dibujamos la textura compensando el offset para que 
            // visualmente empiece donde termina el margen de la hitbox
            batch.draw(textura, hitbox.x + offsetX, hitbox.y, textura.getWidth(), textura.getHeight());
        }
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}