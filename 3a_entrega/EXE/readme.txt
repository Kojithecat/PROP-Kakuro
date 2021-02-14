Estos juegos son los juegos de prueba que, seguidos en orden, 
comprueban el correcto funcionamiento del proyecto, al final hemos añadido casos alternativos.


Para dar permisos a la aplicacion (hace falta):
	chmod ugo+=rwx kakuroApp.jar
Para ejecutar:
En una termial: ./kakuroApp.jar
En entorno grafico: Doble click

1. Crear usuario (Caso de uso crear usuario):

 - Inserta un nombre de usuario (no vacio) en el campo usuario.
 - Inserta una contrasena (no vacia) en el campo contraseña
 - Pulsa el boton de crear usuario.
 
   Resultado: Se crea el usuario con los atributos dados.	

2. Iniciar sesión (Caso de uso iniciar sesion):

 - Inserta el nombre de un usuario previamente creado.
 - Inserta la contraseña asociada a ese nombre de usuario.
 - Pulsa el boton Iniciar sesión.

   Resultado: Se inicia la sesion del usuario con los atributos dados. 
    
3. Importar kakuros (Casos de uso insertar ficheros, validar kakuro):

 - Inicia sesión con un usuario existente.
 - Pulsa el boton Jugar.
 - Pulsa el boton Crear kakuro.
 - Pulsa el boton Importar kakuro desde un fichero.
 - Desde el navegador de ficheros selecciona de la carpeta "Kakuros" proporcionada el fichero kakuro3.txt (kakuro de 6x6).
 
   Resultado: Se ha creado el kakuro a partir del fichero, este podra ser jugado.
  
   Extra: repetir lo mismo para el kakuro11.txt de la carpeta "Kakuro" proporcionada (kakuro de 19x19).

4. Generar kakuros: (Caso de uso elegir parámetros/dificultad):

 - Inicia sesión con un usuario existente.
 - Pulsa el boton Jugar.
 - Pulsa el boton Crear kakuro.
 - Pulsa el boton Generar kakuro
 - Introduce los parametros Altura, Anchura, Casillas blancas maximas y
   el slider, para probar el correcto funcionamiento recomendamos (4,4,4,2) respectivamente.
   
   Resultado: En la pantalla aparecera en color verde "Kakuro creado", 
	      ese kakuro se ha creado y podra ser jugado en las partidas.

5. Jugar partida nueva (Caso de uso jugar, elegir nivel de ayuda, cargar kakuro):

 - Inicia sesión con un usuario existente.
 - Pulsa el boton Jugar.
 - Pulsa el boton Jugar partida.
 - Pulsa el boton Nueva partida.
 - Escoge un kakuro de la lista "Kakuros disponibles", escoge Nivel de ayuda y Modo de juego.
 - Pulsa el boton jugar partida.
 - La parte de jugar la partida se vera mas adelante, por ahora pulsa Guardar y salir.

   Resultado: Se ha creado la partida y se ha guardado.  

6. Cargar partida (Caso de uso jugar):

 - Inicia sesión con cualquier usuario.
 - Pulsa Jugar partida.
 - Empieza una nueva partida.
 - Pon valores en las casillas editables y/o usa ayudas.
 - Guarda y sal.
 - Pulsa Cargar partida.
 - Empieza la partida anteriormente guardada.

   Resultado: Aparecerá el kakuro de la partida seleccionada como se dejo en el momento de guardar.

7. Rendirse (Caso de uso rendirse):

 - Inicia sesion con cualquier usuario.
 - Pulsa Jugar partida.
 - Empieza una nueva partida.
 - Pulsa Rendirse.
 - Acepta rendirte.

   Resultado: Saldrás automáticamente de la partida y la partida estara rendida.

8. Acabar partida:

 - Inicia sesión con cualquier usuario.
 - Pulsa Jugar partida.
 - Empieza una nueva partida.
 - Rellena todo el kakuro correctamente y pulsa comprobar.

   Resultado: Aparecerá una pantalla diciendo que has terminado y que permitirá acceder al inicio de la aplicación.

9. Consultar historial (Caso de uso partidas anteriores):

 - Inicia sesion con cualquier usuario.
 - Pulsar Historial.
 - Selecciona una partida.

   Resultado: Aparecerá un pop-up con toda la información de la partida seleccionada.

10. Consultar ranking de un kakuro (Caso de uso ranking):

 - Inicia sesión con cualquier usuario.
 - Elige un kakuro creado y acepta el mensaje de pop-up.
 - Elige la opcion de Record personal.

   Resultado: Aparecera el record del usuario relacionado con ese kakuro.

11. Consultar record de un kakuro (Caso de uso record):

 - Inicia sesion con cualquier usuario.
 - Elige un kakuro creado y acepta el mensaje de pop-up.
 - Elige la opcion de Record personal.

   Resultado: Aparecerá el record del usuario relacionado con ese kakuro.

12. Salir de la aplicacion (Caso de uso salir): 

 - Inicia sesión con cualquier usuario.
 - Pulsa el botón de Salir.

   Resultado: La aplicacion se cierra.

CASOS ALTERNATIVOS:

- Inicia sesion con cualquier usuario.
- Pulsa Crear Kakuro.
- Pulsa Generar Kakuro.
- Pulsa Importar kakuro desde un ficher.
- Introduce un kakuro multiple.

Resultado: El sistema dira que el kakuro no es valido.


- Inicia sesion con cualquier usuario.
- Pulsa Crear Kakuro.
- Pulsa Generar Kakuro.
- Pulsa Importar kakuro desde un ficher.
- Introduce un fichero que no sea de kakuro.

Resultado: El sistema dira que hay un error de formato.


- Inicia sesión con un usuario existente.
- Pulsa el boton Jugar.
- Inicia una partida.
- Pulsa comprobar.

Resultado: Aparecera un pop-up diciendo que el kakuro no esta bien solucionado.


- Inicia sesion con cualquier usuario.
- Pulsa Crear Kakuro.
- Pulsa Generar Kakuro.
- Introduce un valor de altura y anchura 4 con una cantidad de casillas blancas maximas de 9 y el slider a 1.

Resultado: El sistema probablemente dira que es una generacion fallida.


- Inicia sesion con cualquier usuario.
- Pulsa Crear Kakuro.
- Pulsa Generar Kakuro.
- Introduce un valor de altura o anchura de 0 o 1.
- Pulsa Generar kakuros.

Resultado: El sistema dira que es una generacion fallida.


- Inicia sesion con cualquier usuario.
- Entra en clasificaciones.
- Elige un kakuro con el que el usuario no ha jugado.
- Pulsa Ranking.

Resultado: Apareceran partidas de otros usuarios.


- Inicia sesion con cualquier usuario con un sistema sin kakuros.
- Entrar en clasificaciones.

Resultado: No aparecerá ningún kakuro.


- Inicia sesion con cualquier usuario.
- Entra en clasificaciones.
- Elige un kakuro con el que el usuario no ha jugado.
- Pulsa Récord Personal.

Resultado: No aparecerá ningún record.

