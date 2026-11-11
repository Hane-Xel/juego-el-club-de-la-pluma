package miempresa.LPOO;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import pantallas.MenuPrincipal;
import controles.Controles;

/**
 * Clase principal del juego (Núcleo). 
 * Hereda de la clase Game de LibGDX para gestionar el ciclo de vida y la transición de pantallas.
 */
public class juegoPRINCIPAL extends Game {

    // Instancia global del administrador de controles para que persistan entre pantallas
    private Controles gestorControles;

    /**
     * FUNCIÓN: create()
     * QUÉ HACE: Es el punto de entrada y arranque oficial de la aplicación en LibGDX.
     * CÓMO LO HACE: Configura los elementos esenciales de inicio (controles, pantalla completa y menú inicial).
     */
    @Override
    public void create() {
        // Inicializamos el gestor de controles global (pasando 'null' porque ningún personaje ha sido seleccionado aún)
        gestorControles = new Controles(null, null);

        // Consulta al sistema operativo la resolución nativa y tasa de refresco del monitor actual
        DisplayMode modoActual = Gdx.graphics.getDisplayMode();
        
        // Forzará a la ventana de la aplicación a ejecutarse en modo pantalla completa nativa
        Gdx.graphics.setFullscreenMode(modoActual);

        // Carga la primera pantalla interactiva del juego enviando la referencia de la aplicación (this)
        setScreen(new MenuPrincipal(this));
    }

    /**
     * FUNCIÓN: getGestorControles()
     * QUÉ HACE: Actúa como un método de acceso (getter) para recuperar el gestor de entradas global.
     * CÓMO LO HACE: Retorna la instancia de 'gestorControles', permitiendo que pantallas como la de configuración 
     * reasignen las teclas en tiempo real sin perder los datos al navegar.
     */
    public Controles getGestorControles() {
        return gestorControles;
    }

    /**
     * FUNCIÓN: dispose()
     * QUÉ HACE: Limpia y libera los recursos del sistema al cerrar definitivamente la aplicación.
     */
    @Override
    public void dispose() {
        super.dispose();
    }
}