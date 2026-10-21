package recursos;

import com.badlogic.gdx.graphics.g2d.Animation;

public class ConfigAnimacion {
    public String nombreArchivo;
    public float duracionFrame;
    public Animation.PlayMode modo;

    public ConfigAnimacion(String nombreArchivo, float duracionFrame, Animation.PlayMode modo) {
        this.nombreArchivo = nombreArchivo;
        this.duracionFrame = duracionFrame;
        this.modo = modo;
    }

    public ConfigAnimacion(String nombreArchivo, float duracionFrame) {
        this(nombreArchivo, duracionFrame, Animation.PlayMode.NORMAL);
    }
}