Este ejecutable prueba el algoritmo de resolucion de kakuros

Utiliza los tests de kakuros que se encuentran en el directorio testsResol 
y tienen el formato kakuroI.txt donde I es un numero entre 1 y 11,
estos kakuros tienen una sola solucion y el algoritmo la encuentra 
ademas hay un fichero con un kakuro con multiple solucion para probar
la deteccion de estos por el algoritmo.

Para ejecutar el fichero AlgoritmoResolucion.jar hace falta pasarle un test, sino fallara
Ejemplo de prueba de un test: java -jar algoritmoResolucion.jar ../testsResol/kakuro1.txt

Este algoritmo se comporta de forma esperada, 
resuleve los kakuros correctos (los que tienen solucion unica).
No solo eso sino que detecta los kakuros sin solucion y los kakuros con mas de una solucion.
Los kakuros que resuelve van desde faciles a dificiles (sacados de webs de kakuros) y la solucion 
es identica a la de diversos solvers que hemos encontrado.

Las clases que utiliza este algoritmo (Kakuro, Casilla, Pair) se asumen correctas, 
y sus drivers (que se pueden encontrar en la carpeta FONTS) se consideran
inecesarios ya que han sido previamente testados y para que este algoritmo funcione,
esas clases tienen que funcionar correctamente.
