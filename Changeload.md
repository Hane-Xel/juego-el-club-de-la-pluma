## [Unreleased]
### Added 
-Codeo del menu \
-Se inicializó el proyecto \
-Estructuras basicas \
-Creacion del GitHub (Wiki,Readme,Changeload,archivos)\
-configuracion de los programas \
-Packages Del Programa: \
-controles \
clases: \
ConfControles \
Controles \
/escenarios \
clases: \
BigBen \
Dojo \
Escena \
GranMont \
Playa \
/interfaz \
clases: \
BarraUlti \
BarraVida \
HUD \
MensajeCombate \
/jugadores \
clases: \
Computer \
Jugadores \
PlayerOne \
PlayerTwo \
/miEmpresa \
clases: \
juegoPrincipal \
/pantallas \
clases: \
Combate \
Configuracion \
Pausa \
Resultados \
SelectorEscenario \
SelectorPersonajes \
/personajes \
clases: \
Ave \
Corv \
Emuans \
Flavia \
Kiri \
Mani \
PG \
/recursos \
clases: \
27/8 Ismael\

estructura inicial de GestorRecursos\
todas las instrucciones para subir una actualización a github\
empecé a crear el botón de salir en el menú principal. recomiendo que si quieren continuarlo usen esto\ https://share.gemini.google/sht2nVXeUg5K \

Helena\
Cree una pantalla de menú para el juego\
Ajusté la imagen (todavía falta corregirla porque está recortada por los lados)\

31/8 \
boton de comenzar\
boton de salir\
inicio de menu de seleccion\


16/09 Ismael\

-botones del menú principal cambiados de imágenes a botones de texto.\
-botón de comenzar cambiado temporalmente para enviar a la pantalla de Combate con ambos jugadores usando a Ave1 y en el escenario de playa.\
-Combate creado. Esto incluye:\
personajes visibles en la pantalla\
escenario con una plataforma principal (todavía sin plataforma secundarias)\
controles de los personajes para ambos jugadores\
gravedad\

-Clase GestorRecursosPersonajes creada. Sirve para contener todas las animaciones de todos los personajes. Cuando un personaje quiere usar una animación llama a esta clase. Está pensada para añadir más animaciones a futuro (básicamente todas).\
-Clases alteradas:\
Controles: Cambios para pasar los controles al personaje. Preparado para la configuración de los controles en un futuro y ataques direccionales.\
Jugadores: Cambios para la compatibilidad con los dos Player.\
PlayerOne: Ahora registra el estado de su personaje y guarda las victorias.\
PlayerTwo: Ahora registra el estado de su personaje y guarda las victorias.\
Combate: Todo lo comentado arriba.\
Aves: Ahora tiene gravedad y procesa el movimiento que hace el personaje con el botón correspondiente. Gira derecha e izquierda el sprite según donde esté mirando. Hitbox y colisiones.\
MenuPrincipal: Botones de texto en vez de imágenes y cambio temporal del botón de comenzar.\
Escena: Ahora pone la gravedad y funciona como una base para el resto de escenarios\
Playa: Ahora se puede usar. Tiene su propia altura mínima y posiciones iniciales (todavía sin plataformas chiquitas)\
GestorRecursos: Ahora funciona para tener los sprites de todos los escenarios.\

ERROR 1:\
el menú de combate no cargaba las imágenes pero si los personajes. Esto se debía a que el gestor de recursos todavía no estaba llamando al sprite del escenario. Para solucionarlo simplemente puse que lo cargue\


ERROR 2:\
A causa de una secuencia que todavía no pude identificar los personajes traspasan el escenario y se caen al vacío. Hasta esta fecha no se bien por que pasa pero según lo poco que lo pude probar pasa después de mover por un tiempo o cantidad indeterminados a los personajes o minimizar y agrandar la pantalla. Parece ser aleatorio ya que ocurre de manera distinta cada vez que lo pruebo. Todavía no está solucionado.\

SUGERENCIAS DE SIGUIENTES PASOS:\
Cambiar que el menú de configuración funcione con los nuevos controles\
Hacer que los personajes puedan atacar (ya hacen algo parecido pero la animación no termina)\
Terminar los sprites de un personaje para ir probando como poner animaciones bien (seguramente kiri)\
Poner barras de vida\
Hacer las nuevas plataformas chiquitas\
Añadir el resto de escenarios\
Hacer los ataques especiales\

25/9 SOLUCION DE ERROR Y COMBATE 2\
-Se soluciono el error 2 de la fecha de 16/9, el suelo ya posee colisión\
-Se soluciono el error 1 de la fecha de 16/9, ya carga bien la batalla\
-Las animaciones ya poseen su fin\
-Se le añadio ya la musica a la batalla\
-Los personajes ya reciben daño y este se muestra en su barra de vida\
-Se les añadio un retroceso al recibir el daño\


