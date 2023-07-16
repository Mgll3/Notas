package src;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main{

    
    public static void main(String[] args) {
        
        try {
            PrintStream info = new PrintStream("copia.txt");
            info.println("hola");
            InputStream in = new FileInputStream("copia.txt");
            byte []datos = in.readAllBytes();	

            PrintStream out = new PrintStream("copia2.txt");
            out.write(datos);
            info.close();
            out.close();
            in.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
    

   
}

/* Git hub con repositorio de codigo del bootcamp
 * https://github.com/Open-Bootcamp/java_basico/tree/main
 */

/*  System, imprimir o recibir
 Scanner scanner = new Scanner(System.in);
    System.out.println("introducir nombre y luego un numero");
        String nombre = scanner.nextLine ();     //leer una linea de texto
        int numero= scanner.nextInt();          //leer un numero entero
        System.out.println(nombre + numero);    
        
 */

/*Javadoc comentarios cuando pongo encima del metodo
    /** usando este doble asterisco se crea un comentario javadoc
     * Aqui colocamos lo que queremos que la gente vea al pasar el cursor encima de este metodo de abajo
     * en este caso en impirmir
     * Método para imprimir un mensaje en consola
     * @param a
     * aqui explicamos el parametro para que sirve
     */
    /*public void imprimir(int a){
        System.out.println("Hola mundo");
    }
    
 */

/* Operadores logicos && ||
 *  &&  = y
 *  ||  = o
 * ejemplo
 * if (a>b && a>c){ //Que las dos se cumplan
 * if (a>b || a>c){ //Que una de las dos se cumpla
 */

 /* Else If
  * int a = 2;
        if (a>3){
            System.out.println("a es mayor que 3");
        } else if(a<4){
            System.out.println("a es menor que 4");
        }
  */

/* Switch
 * String dia =  "Lunes";
        switch(dia){
            case "Viernes":
                System.out.println("Hoy es Viernes!! Animo!!");
                break;
            case "Sabado":
                System.out.println("Hoy es Sabado!! Animo!!");
                break;
            case "Domingo":
                System.out.println("Hoy es Domingo!! Animo!!");
                break;
            default:
                System.out.println("No es un día válido");
                break;
        }
 */

/*} For, for each, while
 * for(int i=0; i<10; i++){
            System.out.println("for normal: " + i);

        }
    
        //for each, para cada elemento de args le hace lo que esta dentro del for
        String[] nombres = {"Juan", "Pedro", "Maria", "Luis", "Ana"};
        for (String nombre : nombres) {
            System.out.println(nombre);
            
        }

        //bucle while que va recorriendo los primeros 5 elementos de un array
        int j=0;
        while(j<5){
            System.out.println("while: " + nombres[j]+ j);
            j+=2;
        }
 */

/* Encapsulamiento
 * Getters y Setters, Colocando los atributos en privado para que desde afuera no hagan solo
 * objeto.atributo  sino que tengan que obtenerlo mediante get y set
 * 
 */

/* Herencia y abstracta
 * Heredar de la clase
 * public class Perro extends Animal{
 * private String name;
 * }
 * polimorfismo, que por ejemplo en sonido cada animal se escuche de diferente forma
 * pero que todos tenga el mismo metodo sonido
 * 
 * Una clase abstracta, es la clase para reutilizar codigo en la que no se puede instanciar
 * osea crear un objeto vehiculo porque es muy general, para que las hijas sean las que
 * instancien y así si dar información completa del objeto
 */

/* Static
 * indica que ese metodo pertenece a esa clase y no a los objetos de la clase
 * osea un objeto no lo puede instanciar encambio a misma clase puede venir a llamarlo
 * sin crear un objeto ejemplo
 * correr(); 
 * No necesita para funcionar
 * objeto.correr()
 */

/*  Interfaces   - Bajo acoplamiento
 * Interfaces es el mediador entre el main y clases, que por ejemplo si cambio de base de datos
 * no tenga que ir al main(aplicacion) a cambiar todo sino que solo cambie en la interfaz 
 * No se implementa nada en esta interfaz, solo se declara para que las hijas implementen
 * Es como definir una manera comun de hacer las cosas para que las hijas lo hagan de esa manera
 * Ejemplo aquí: 
 * https://github.com/Open-Bootcamp/java_basico/tree/main/sesiones_4_5_6/ob-poo/src/poo/coninterfaces
 * 
 * Entonces el Main quedaría así:
 * public class Main {
    //dos opciones solo quedaría una en el final
    static EmpleadoCRUD empleadoCRUD = new EmpleadoCRUDSql();
    static EmpleadoCRUD empleadoCRUD = new EmpleadoCRUDMongo();
    //vemos que no importa si es sql o mongo, solo cambiamos la clase hija que va a guardar
    de resto todo sigue exactamente igual

        public static void main(String[] args) {

            empleadoCRUD.findAll();
            empleadoCRUD.save(new Empleado());
        }
    }
 * así mismo podría ser por ejemplo con una clase exportadora, que coje datos y los pasa a pdf
 * y si en algun momento no queremos que sea pdf sino a docx, solo cambiamos la clase hija y listo
 * 
 */

/* Refactoring, tecnicas para mejorar el codigo y su mantenimiento
 * -Comprobaciones con muchos if o metodos muy largos, para que un mismo  metodo no este muy largo mejor
 * crear un metodo aparte y llamarlo 
 * -No tener muchos if, usar switch cases, agrupar condiciones con || y &&:
 * boolean estudios = antiguedad> 2 || educacion > 1;  // para así no tener muchos if
 * 
 * -No tener clases con muchos atributos, mejor crear otra clase y nombrarla en la clase principal
 * public class Person{
 * private String name;
 * private String lastName;
 * 
 * private Address address;
 * }
 * 
 * publica class Address{
 * private String street;
 * private String city;
 * }
 * 
 * -No tener variables que tengan muchos calculos en ella misma, mejor crear varias variables por pasos
 * y hacer los calculos en una variable final
 * 
 * -Todo numero que estemos usando en codigo y pongamos directamente como descuento 10% mejor crear una
 * constante y ponerla en el codigo
 * 
 * -Si una funcion envia unas variables mejor no modificar esas variables y devolver un nuevo objeto
 * 
 * -No tener clases con metodos demasiado grandes, mejor crear otra clase que se dedique solo a eso
 * calcular o hacer el procesos de ese metodo y que la clase principal solo cree un objeto de esa
 * clase con los parametros necesarios y le llame el metodo de allá
 */

 //Tipos de datos complejos
/* String
 * String nombre = "juan";
 * 
 *  convertir un string en la primera mayuscula
 * nombre = nombre.substring(0,1).toUpperCase() + nombre.substring(1);
 * 
 * mirar si un string empieza por a
 * nombre.startsWith("a"); //o endsWith("a");
 * 
 * usar charAt para ver si un string tiene una letra en especifico
 * nombre.charAt(0) == 'a'; //mira primera posicion
 * 
 * recorrer array
 * for (int i = 0; i < nombre.length(); i++) {
        System.out.println(nombre.charAt(i));
    }
 * 
 * 
 */

/* Arrays
 * NO se puede agregar mas cantidad de elementos de los que se declaran inicialmente
 * crear un array nuevo
 * int[] numeros = new int[5];
 * int[] numeros = {1,2,3,4,5};
 * 
 * array con fila y columna
 * int[][] numeros = new int[2][2];
 * 
 * recorrer array de dos dimensiones
 * for (int i = 0; i < numeros.length; i++) {
        for (int j = 0; j < numeros[i].length; j++) {
            System.out.println(numeros[i][j]);
        }
    }
 * 
 */

/* Vectores
 * No se les da un tamaño inicial, pero cada que pase su capacidad se auto pone el doble
 * 
 * crear vector
 * Vector<String> nombres = new Vector();
 * nombres.add("Juan");
 * nombres.remove(0);
 * que es capacidad de un vector?
 * si a un vector le metemos 12 elementos automaticamente se le asigna una capacidad de 20
 * así que el mismo va aumentando su capacidad copiando todos los elementos a un nuevo vector mas grande
 * nombres.capacity();
 * nombres.size(); //elementos usados actualmente
 * 
 * recorrer vector
 * for (int i = 0; i < nombres.size(); i++) {
        System.out.println(nombres.get(i));
    }
 */

/* ArrayList
 * No se les da un tamaño inicial, pero cada que pase su capacidad se auto pone un 50% más
 * No se puede modificar simultaneamente, mientras que el vector si
 * de resto todo es igual que el vector
 * crear un arraylist
 * ArrayList<String> nombres = new ArrayList();
 * nombres.add("Juan");
 * nombres.remove(0);
 * 
 */

/* LinkedList
 * Usa lista doblemente enlazada no como el arraylist que usa un array
 * es más rapido para almacenar y buscar datos
 * más lenta para modificar datos
 * 
 * 
 * crear nueva linkedlist
 * LinkedList<String> nombres = new LinkedList();
 * 
 */

/* BigDecimal
 * Nuevo numero que es más preciso para operaciones matematicas más perfectas, con más decimales
 * Solo se opera con sus mismo numeros bigdecimal
 * 
 * crear bigdecimal
 * BigDecimal numero = new BigDecimal(0.1f);
 * BigDecimal valorB = new BigDecimal(4);
 * numero.multiply(valorB); //multiplicar o sumar en big decimal, no puedo poner 4 directamente
 */

/* Mapas
 * clave valor  No se puede repetir la clave
 * HashMap<String, String> paises = new HashMap<>();    //el mas basico
 * paises.put("CO", "Colombia");
 * paises.replace("ES", "España");      //reemplazar valor de la clave si la clave existe
 * paises.get("CO");                 //obtener valor de la clave
 * paises.remove("CO");              //eliminar clave
 * 
 * recorrer mapa
 * for (Map.Entry<String, String> elemento : paises.entrySet()) {
        System.out.println(elemento.getKey() + " - " + elemento.getValue());
    }
 * por que usar hashmap y no map
 * porque si en algun momento quiero cambiar de hashmap a treemap o a otro, solo cambio la clase
 * y no el codigo
 * 
 */

/* Errores
 * Error en tiempo de ejecución, situación a la cual no se estaba preparado y el programa para
 * 
 * Error de compilación, error en el codigo que no permite que se compile ejemplo falta ; 
 * es un error sintactico
 * 
 * Error logico, es un error de logica 
 * 
 */
/*  try catch
    try {                                 //intentar hacer algo
        int result= 5/0;

    }   catch (ArithmeticException e) {     //agarrar el error si es que hay
        e.printStackTrace();
        System.out.println("Error: " + e.getMessage());

    }   catch(Exception e) {
        System.out.println("Error: " + e.getMessage());

    }   finally {                            //siempre se ejecuta aún halla error o no
        System.out.println("Cierre de recursos");
    }    
*/

/* Throw- Generar excepciones
 * Pasa la excepcion para arriba y no la imprime, para que la imprima en el main
 * //decimos que puede tire ese error probablemente pero no es obligatorio que siempre tire ese error
    public static int divide(int a, int b) throws ArithmeticException{
        int resultado = 0;
        try {
            resultado = a/b;
            return resultado;
        } catch (ArithmeticException e) {
            throw new ArithmeticException(); //generar una excepcion
        } 
    }
 */

/* Warnings
 * 
 * avisos que dejan que el programa se ejecute pero que no es la mejor forma de hacerlo, por ejemplo
 * si una variable o import no se usa
 * o si no se pone un break en un switch, etc
 * 
 * @SuppressWarnings("unchecked") //para quitar el warning de unchecked
 * 
 * 
 */

/* Entradas Input stream 
 * // leer fichero  guardandolo en memoria todo completo y luego usandolo
        try {
            InputStream fichero = new FileInputStream("C:\\Users\\Usuario\\Desktop\\fichero.txt");

            try {
                byte []datos = fichero.readAllBytes();
                for (byte dato : datos) {
                    System.out.print((char)dato);
                }
                fichero.close();

            } catch (IOException e) {
                System.out.println("No puedo leerlo: " + e.getMessage());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        //leer fichero con scanner pero caracter a caracter no guardandolo en memoria todo completo
        Consume muchas operaciones de disco pprque es uno a uno
        try {
            InputStream fichero = new FileInputStream("/etc/passwd");

            try {
                byte []datos = new byte[5];
                int data = fichero.read(datos);


                while (data != -1) {
                    System.out.println("Leido: '" + (char)data + "' que es el código " + data);
                    data = fichero.read();
                }
                fichero.close();
            } catch (IOException e) {
                System.out.println("Error al leer: " + e.getMessage());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error al abrir fichero: " + e.getMessage());
        }
 * 
 *  La opcion mejor es ir leyendo cada tantos caracteres y no todo completo
 * 
 * 
 * 
 */

/* Entradas BufferedInputStream
 * No trabaja con ficheros
 * 
 * 
 * 
 */

/* Scanner
 * 
 * 
 * boolean ok = false;
    do {        //para que se quede pidiendo el numero si no esta correcto
        Scanner scanner = new Scanner(System.in);
        System.out.print("Mete dos numeros: ");
        try {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            ok = true;
        } catch (InputMismatchException e) {   //controlar el error
            System. out.printin ("Numeros invalidos");
        }
    } while (!ok);








 * 
 * 
 * 
 */

/* PrintStream    trabaja con ficheros
 * 
 *      try {
            PrintStream info = new PrintStream("copia.txt"); //crea un fichero nuevo
            info.println("hola");
            InputStream in = new FileInputStream("copia.txt"); //lee el fichero en variable
            byte []datos = in.readAllBytes();	                //lee en bytes el fichero

            PrintStream out = new PrintStream("copia2.txt");    //crea un fichero nuevo
            out.write(datos);                                   //escribe en el fichero
            info.close();
            out.close();
            in.close();
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
 * 
 * 
 */

/* Eventos, swing tax
 * Lo que el usuario ve y hace en la pantalla, como javafx
 * 
 * Eventhandled
 * Codigo que se dispara cuando se produce un evento
 * 
 * 
 * 
 * 
 */

/* XMl y JSON   datos
 * 
 */




