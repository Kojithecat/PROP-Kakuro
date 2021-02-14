Este ejecutable prueba el algoritmo de creacion de kakuros

Utiliza los tests de creacion que se encuentran en el directorio testsCreacion 
o siguiendo los pasos en caso de no intoducir uno de estos tests. 
Los tests se diferencian entre crear kakuros con forma de escalera o con forma rectangualar

Ejemplo de prueba de un test: java -jar algoritmoCreacionSimple.jar < ../testsCreacion/kakuro3-5C.txt
      Tambien se puede hacer: java -jar algoritmoCreacionSimple.jar 

ex rectangular: ***** ex escalera: ***** 
                *????              *??**   
                *????              *???*
                *????              **???
                *????              ***??    

Donde * son casillas negras y ? son casillas blancas.

En el caso de generar kakuros rectangulares, estos no pueden mayores que 10x10
En el caso de generar kakuros en L, las dimensiones deben ser cuadradas (nxn)
Nota: para kakuros mas grandes este proceso puede tardar
