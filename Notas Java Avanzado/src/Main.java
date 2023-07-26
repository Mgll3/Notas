


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

/*Postman
App para apis ejecutar apis desde postman para testearlas
Tiene variables posibles de crear, ejemplo url para no estar colocando siempre el url tan largo

Test: sirve para pruebas y ver si al respuesta de la api es lo que yo quiero que responda
pm.test("Status code is 200", function () {
  pm.response.to.have.status(200);
});

 */

/*Patrones de diseño -Patrones Creacionales:
Estandarizar el desarrollo para que todos programemos igual y se entienda el codigo


Singleton: 
Patron de diseño para que no se creen instancias infinitas(se cree y se cree nuevos objetos de una clase)
//a cualquier clase se le tiene que crear un getInstance, un constructor privado y una instanciacion privada inicial base 
//hacer que funcione el singleton:
    public static class Singleton{
        private static Singleton instance; //creamos una variable privada de tipo singleton
        private Singleton (){} //creamos el constructor privado para que no se pueda crear desde afuera usando new singleton
        
        //la primera vez que llamamos getInstance si dejara crer/instanciar un objeto singleton
        //pero si ya existe un objeto singleton creado, no dejara crear otro y devolvera el que ya existe
        public static Singleton getInstance(){
            if (instance == null){
                instance = new Singleton();
            } 
            return instance;
        }
        //para llamar al singleton desde afuera
        //Singleton singleton = Singleton.getInstance();
    }

Factory:
Patron de diseño para crear objetos de una clase sin tener que usar new sino que los invocamos desde 
el factory y el factory se encarga de ver sí es un objeto u otro y crearlo
public class Factory {
        private Precio precio;
    
        private Factory() {};
        //modificamos el constructor para que se encargue de crear este objeto o este otro objeto
        public Factory(String pais) {
            if (pais.equalsIgnoreCase("España")) {
                this.precio = new PrecioEUR();
            } else {
                this.precio = new PrecioUSD();
            }
        }
        //para que en Main no toque poner factory.precio.getPrecio() que es más largo
        public double getPrecio() {
            return precio.getPrecio();
        }
    }
    interface Precio {
        double getPrecio();
    }
    class PrecioEUR implements Precio {
        public double getPrecio() {
            return 1.3;
        }
    }
    class PrecioUSD implements Precio {
        public double getPrecio() {
            return 0.94;
        }
    }
Factory factory = new Factory("USA");
        System.out.println(factory.getPrecio());


Builder:
Es para construir clases que tienen muchos atributos y no queremos tener que poner todos los atributos
uno por uno en el constructor, sino que los pasamos todos en el builder y el builder se encarga de crear

Es parecido a cuando usamos los stream .map.reduce.filter etc nos van devolviendo el mismo tipo de objeto
que les mandamos pero lo van modificando

public class CocheBuilder {
    Vehiculo vehiculo;  //creamos el objeto que vamos a ir modificando

    private CocheBuilder() {};

    public CocheBuilder(String marca) {
        vehiculo = new Vehiculo(marca);
        vehiculo.marca = marca;
    }
    //le ponemos solo los setters sin getters porque el builder solo construye no devuelve
    public CocheBuilder setPuertas(int puertas) {
        vehiculo.puertas = puertas;
        return this;        //return this para devolver el mismo objeto que estamos modificando
        //pero con el atributo que le pasamos modificado
    }

    public CocheBuilder setMotor(String motor) {
        vehiculo.motor = motor;
        return this;
    }

    public CocheBuilder setTipo(String tipo) {
        vehiculo.tipo = tipo;
        return this;
    }
    //devuelve el objeto vehiculo que ya tiene todos los atributos modificados
    public Vehiculo build() {
        return this.vehiculo;
    }
}
public class Vehiculo {
    public String marca;
    public String tipo;
    public String motor;
    public int puertas;

    private Vehiculo() {}

    public Vehiculo(String marca) {
        this.marca = marca;
    }
    //el objeto solo tendra getters porque para crearlo se debera usar el constructor
    public String getMarca() {
        return marca;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMotor() {
        return motor;
    }

    public int getPuertas() {
        return puertas;
    }
}

//en el Main, le pasamos todos los atributos pero con .set   
luego .build() para que devuelva el objeto que queremos no el objetoBuilder que estaban manejando los setters
Vehiculo coche = new CocheBuilder("Filostro")
    .setTipo("Diesel")
    .setMotor("Combustion")
    .setPuertas(5)
    .build();

Prototype:
Es un patron para poder clonar un objeto con todas sus propiedades y no tener que crearlo de nuevo
usando implements Cloneable que se encarga de todo eso en java

public class Vehiculo implements Cloneable {
    public String marca;
    public String modelo;
    int anyoFabricacion;

    public Vehiculo() {}

    public Vehiculo clonar() throws CloneNotSupportedException {
        return (Vehiculo) this.clone();
    }
}
En el main solo sería llamar al metodo clonar teniendo en cuenta que puede lanzar una excepcion
entonces usar try catch y listo


*/

/* Patrones Estructurales

Decorator:




*/


public class Main {
    
    
    
    


    public static void main(String[] args) {
        

    }


}
   








