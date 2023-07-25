


//Rest : carpeta de controladores


/* Programacion funcional 
Es en vez de nosotros usar fors y etc, vamos es a llamar metodos que hagan todo

--Funcion pura:
Que devuelve siempre exactamente los mismos resultados con los mismos parametros recibidos
ejemplo suma de 1 y 2 siempre dara 3

No tener efectos colaterales
No modificar variables globales, o estaticas, solo las que son locales de la función
Hacer exactamente lo que dice si es suma solamente suma sin extras o llamar funciones que no
tienen que ver con suma

public static suma(int a, int b){
    return a + b;
}

--Funciónes de alto orden o nivel:
Una funcion tiene que devolver otra función

public static primera(String a){
    return a.startsWith("a");
}
Funcion que recibe una funcion como parametro
ejemplo de alto orden, Variable tipo función
 //el primer String define lo que recibe, el segundo string define el tipo que devuelve
private Function<String, String> mayus = (a) -> a.toUpperCase(); 
//para llamarla usamos el apply 
mayus.apply("hola");

//para llamar una función variable con un metodo
public void llamador(Function<String, String> mayus, String a){
    mayus.apply(a);
}
//llamarla
llamador(mayus, "hola");


--Stream usando map             Los stream solo se pueden usar una vez pues se consumen
Stream<String> valores = nombres.stream().map((a) -> a.toUpperCase())
    .filter((a) -> a.startsWith("A"));
//lo que hacemos es que a un array nombres lo convertimos a stream para poder iterar facilmente
luego llamamos a map para que cada valor del stream lo convierta a mayusculas
y luego llamamos a filter para que nos devuelva solo los que empiezan por A

Para que el steam? para iterar sobre ellos:
valores.forEach((a) -> System.out.println(a));  //imprimo cada valor del stream con prog funcional
valores.forEach(System.out::println); //imprimo cada valor del stream con prog funcional

--Reduce:         
Pasar de varios numeros a un solo numero

//pasamos 0 porque es el valor inicial donde se empezara a iterar
.reduce(0, (a, b) -> a + b) //devuelve un solo valor del arreglo o stream de valores
//iterando con cada valor del stream y haciendo lo que le dijimos -> siendo a el acumulado de 
cada iteracion y b el valor actual de la iteracion

--Recursion
headrecursion: cuando la llamada recursiva esta al principio de la impresion (sout)
Esto significa que si es por cabeza se imprime antes de llamar a la recursividad, osea o imprime al inicio

tailrecursion: cuando la llamada recursiva esta al final de la impresion (sout)
y cuando es por cola se imprime luego de que todas las llamadas recursivas se hayan hecho osea imprime al final



*/

/*Servicio Rest
App web con patrones determinados
Usar verbos http  que son tipo de peticiones qe enviamos  a un servidor usando http:
Get: obtiene un recurso/info del servidor 
post: Envia info al servidor
put: actualiza un recurso/algo que hay existe en el servidor, enviando el usuario completa
patch: actualiza un recurso/algo que ay existe en el servidor, enviando solo los datos cambiados
delete: borra un recurso en el servidor
Creación de proyecto con springboot intellij : https://www.youtube.com/watch?v=9j05x-jBl_M&t=740s&ab_channel=OpenBootcamp

Controlador: Es lo que recibe las peticiones por http y las procesa
Servicios: parte logica de la app rest y se conecta con el modelo

Para desde cmd ejecutar comando http y que nos muestre en crudo la respuesta:
curl localhost:8080
hola


*/



public class Main {


    public static void main(String[] args) {
        System.out.println("Hola mundo");
    }


}
   








