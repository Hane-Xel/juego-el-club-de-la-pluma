package recursos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class GestorRecursosPersonajes {

    public enum EstadoAnimacion {
        REPOSO,
        CAMINAR,
        ATACAR,
        BLOQUEAR,
        RECIBIR_DANIO,
        ESPECIAL
    }

    private static final ObjectMap<String, ObjectMap<EstadoAnimacion, Animation<TextureRegion>>> animaciones = new ObjectMap<>();

    public static void cargarAnimaciones() {
        animaciones.clear();
        cargarAve1();

        // Estructura lista para las subclases de personajes
        String[] nombres = {"Kiri", "Corvervance", "Emuans", "Flavia", "Mani", "PG"};
        for (String nombre : nombres) {
            if (!animaciones.containsKey(nombre)) {
                animaciones.put(nombre, new ObjectMap<>());
            }
        }
    }

    private static void cargarAve1() {
        ObjectMap<EstadoAnimacion, Animation<TextureRegion>> mapaAve1 = new ObjectMap<>();

        Texture texBase = GestorRecursos.obtenerTextura("characters/Ave1.png");
        if (texBase != null) mapaAve1.put(EstadoAnimacion.REPOSO, crearEstatica(texBase));

        Texture texAtaque = GestorRecursos.obtenerTextura("characters/Ave1_ataque.png");
        if (texAtaque != null) mapaAve1.put(EstadoAnimacion.ATACAR, crearEstatica(texAtaque));

        Texture texBloqueo = GestorRecursos.obtenerTextura("characters/Ave1_bloqueo.png");
        if (texBloqueo != null) mapaAve1.put(EstadoAnimacion.BLOQUEAR, crearEstatica(texBloqueo));

        Texture texDanio = GestorRecursos.obtenerTextura("characters/Ave1_daño.png");
        if (texDanio != null) mapaAve1.put(EstadoAnimacion.RECIBIR_DANIO, crearEstatica(texDanio));

        Texture texEspecial = GestorRecursos.obtenerTextura("characters/Especial.png");
        if (texEspecial != null) mapaAve1.put(EstadoAnimacion.ESPECIAL, crearEstatica(texEspecial));

        animaciones.put("Ave1", mapaAve1);
    }

    private static Animation<TextureRegion> crearEstatica(Texture textura) {
        Array<TextureRegion> frames = new Array<>();
        frames.add(new TextureRegion(textura));
        return new Animation<>(1.0f, frames);
    }

    public static TextureRegion obtenerFrame(String nombrePersonaje, EstadoAnimacion estado, float stateTime) {
        ObjectMap<EstadoAnimacion, Animation<TextureRegion>> mapa = animaciones.get(nombrePersonaje);
        if (mapa != null && mapa.containsKey(estado)) {
            return mapa.get(estado).getKeyFrame(stateTime);
        }
        
        // Fallback a Ave1 en reposo
        ObjectMap<EstadoAnimacion, Animation<TextureRegion>> ave1 = animaciones.get("Ave1");
        if (ave1 != null && ave1.containsKey(EstadoAnimacion.REPOSO)) {
            return ave1.get(EstadoAnimacion.REPOSO).getKeyFrame(stateTime);
        }
        return null;
    }

    /**
     * Limpia la memoria y remueve todas las referencias de animaciones.
     */
    public static void dispose() {
        animaciones.clear();
    }
}