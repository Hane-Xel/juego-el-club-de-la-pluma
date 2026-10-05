## Inicio
### Añadido 
-Codeo del menu \
-Se inicializó el proyecto \
-Estructuras basicas \
-Creacion del GitHub (Wiki,Readme,Changeload,archivos)\
-configuracion de los programas \
-Packages Del Programa: \
-controles \
clases: \
●ConfControles \
●Controles \
/escenarios \
clases: \
●BigBen \
●Dojo \
●Escena \
●GranMont \
●Playa \
/interfaz \
clases: \
●BarraUlti \
●BarraVida \
●HUD \
●MensajeCombate \
/jugadores \
clases: \
●Computer \
●Jugadores \
●PlayerOne \
●PlayerTwo \
/miEmpresa \
clases: \
juegoPrincipal \
/pantallas \
clases: \
●Combate \
●Configuracion \
●Pausa \
●Resultados \
●SelectorEscenario \
●SelectorPersonajes \
/personajes \
clases: \
●Ave \
●Corv \
●Emuans \
●Flavia \
●Kiri \
●Mani \
●PG \
/recursos: \

clases: \

●gestorRecursos \

## 27/8

### Añadido 
-estructura inicial de GestorRecursos\
-pantalla de menú para el juego\
-Ajusté la imagen de fondo del menu\

### Problemas encontrados
-La imagen del menú principal esta recortada por los bordes\

## 31/8 

### Añadido
-boton de comenzar\
-boton de salir\


## 16/09

### Cambiado
-botón de comenzar cambiado temporalmente para enviar a la pantalla de Combate con ambos jugadores usando a Ave1 y en el escenario de playa.\

### Añadido
-botones del menú principal cambiados de imágenes a botones de texto.\
-Clase Combate. Esto incluye:\
●personajes visibles en la pantalla\
●escenario con una plataforma principal (todavía sin plataforma secundarias)\
●controles de los personajes para ambos jugadores\
●gravedad\

-Clase GestorRecursosPersonajes creada. Sirve para contener todas las animaciones de todos los personajes. Cuando un personaje quiere usar una animación llama a esta clase. Está pensada para añadir más animaciones a futuro (básicamente todas).\
-Clases avanzadas:\
●Controles: Cambios para pasar los controles al personaje. Preparado para la configuración de los controles en un futuro y ataques direccionales.\
●Jugadores: Cambios para la compatibilidad con los dos Player.\
●PlayerOne: Ahora registra el estado de su personaje y guarda las victorias.\
●PlayerTwo: Ahora registra el estado de su personaje y guarda las victorias.\
●Combate: Todo lo comentado arriba.\
●Aves: Ahora tiene gravedad y procesa el movimiento que hace el personaje con el botón correspondiente. Gira derecha e izquierda el sprite según donde esté mirando. Hitbox y colisiones.\
●MenuPrincipal: Botones de texto en vez de imágenes y cambio temporal del botón de comenzar.\
●Escena: Ahora pone la gravedad y funciona como una base para el resto de escenarios\
●Playa: Ahora se puede usar. Tiene su propia altura mínima y posiciones iniciales\
●GestorRecursos: Ahora funciona para tener los sprites de todos los escenarios.\


## 19/9

### Añadido
-Las animaciones ligadas a acciones\
-acción de ataque\
-acción de defensa\
-Barras de vida\
-Musica para el escenario de playa\
-Retroceso al recibir el daño\

### Cambiado
-Ahora la pantalla es la misma sin importar la resolución. Ayuda a que las propiedades como las hitbox, plataformas, ETC. no presenten problemas al cambiar la resolucion o al achicar y agrandar.

### Arreglado
-Error que hacía que los personajes se cayesen del escenario al achicar y agrandar la pantalla.

