package recursos;

import com.badlogic.gdx.graphics.g2d.Animation;

public class ConfigAnimacion {
    public String nombreArchivo;
    public float duracionFrame;
    public Animation.PlayMode modo;
    public int columnas; // <--- Nuevo
    public int filas;    // <--- Nuevo

    // Constructor para grillas personalizadas (ej: 5 columnas, 2 filas)
    public ConfigAnimacion(String nombreArchivo, float duracionFrame, Animation.PlayMode modo, int columnas, int filas) {
        this.nombreArchivo = nombreArchivo;
        this.duracionFrame = duracionFrame;
        this.modo = modo;
        this.columnas = columnas;
        this.filas = filas;
    }

    // Constructor por defecto para tiras horizontales de 1 sola fila (auto-calcula columnas)
    public ConfigAnimacion(String nombreArchivo, float duracionFrame, Animation.PlayMode modo) {
        this(nombreArchivo, duracionFrame, modo, 0, 1); // columnas 0 significa que se autocalculará
    }

    public ConfigAnimacion(String nombreArchivo, float duracionFrame) {
        this(nombreArchivo, duracionFrame, Animation.PlayMode.NORMAL, 0, 1);
    }
}