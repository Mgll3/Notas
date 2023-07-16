console.log("Hola Mundo"); 


/* inicializar proyecto node
Para inicializar un proyecto de Node, ejecutar este comando en terminal:
npm init -y
Luego de eso seleccionar las opciones que se deseen, y se creará un archivo llamado package.json
-y si quiere aceptar todo directamente

*/
// npm start ejecuta el archivo que se le indique en el package.json
// nose archivo.js ejecuta directamente ese archivo

/*Variables, constantes y Let
tipado inferido no tenemos que declararlo como en java

var a = 10; //number
var b = "Hola Mundo"; //string
var c = true; //boolean


null, undefined, false, 0     todas son igual a false

const d = 10; //constante

El let es una variable que se p
let e = 10; //let
for (let i = 0; i < 10; i++) {
    e = e+1;
}
Si imprimo e afuera del for me saldra el resultado 10 encambio si dentro del for me saldra 20
Si en vez de let utilizo var se cambiaria la variable general y no solo la momentanea en el for
console.log(e);
*/

/* [] () {}
[]  para arrays listas etc
const ar = [1,2,3,4,5];
()  para funciones
if (true){
    console.log("Hola Mundo");
}
{}  para objetos
const movil={
    anchura: 5,
    altura:10
}
*/

/*Listas objetos y fechas
const lista = [1, "hola", true, undefined, null, new Date()];
const lista2 = new Array(1, "hola", true, undefined, null, new Date()); //otra forma de crear una lista
const lista3 = new Array(10); //crea una lista de 10 elementos vacios
const lista4 = [lista, lista2, lista3]
console.log(lista4);

//objetos
const movil = {
    anchura: 5,
    altura: 10,
    contactos: { //Objeto dentro de objeto
        nombre: "Juan",
        numero: 123456789
    }
}
console.log(movil.contactos.nombre);
movil.año = 2020; //añadir un atributo al objeto

//fechas
const ahora = new Date();
const fecha = new Date(2020, 0, 20, 10, 30, 0); //año, mes 0 = enero, dia, hora, minuto, segundo
const mes = fecha.getMonth()+1;
//console log que presenta varios parametros
console.log(ahora, fecha, mes);


*/

/* if else, switch, 

let nota = 200;

if (nota === 5) {
    console.log("Enhorabuena, has obtenido un sobresaliente");
} else if (nota === 4) {
    console.log("Buen trabajo, pero podrías haberlo hecho mejor");
}

let nota = 3;

switch (nota) {
    case 5:
        console.log("Buen trabajo, ¡sobresaliente!");
        break;
    case 4:
        console.log("Buen trabajo, pero podrías haberlo hecho mejor");
        break;
    case 3:
        console.log("Has obtenido un suficiente");
        break;
    case 2:
        console.log("No has aprobado por poco");
        break;
    case 1:
        console.log("No has estudiado nada, trabaja un poquito más para la próxima");
        break;
    default:
        console.log("Error")
        break;
}

*/

/* Comparaciones == ===  !=
// == sólo compara el valor
// === compara el valor y el tipo

let a = 5;
let b = "5";

if (a == b) {
    console.log("a es igual a b - Débil")
}

if (a === b) {
    console.log("a es igual a b - Fuerte")
}

diferente:
!= diferente solo en valor
if (a != b) {
}
!== diferente en valor y en tipo de variable
if (a !== b) {
}
<= >= ya me los sé
*/

/* for y while
Continue = salta a la siguiente iteración del blucle
Break = rompe el bucle


Tipico
let lista = [1, 4, 6, 2, 3, 7, 10, 12, 800];
for (let i = 0; i < lista.length; i++) {
    console.log(lista[i] * 2)
}

// Estructura for...of    recorre toda la lista usando variable valor para cada elemento de la lista
for (let valor of lista) {
    console.log(valor)
}

// Estructura forEach, en js a la lista la mandamos a llamar el foreach, 
osea para cada valor de la lista haga tal cosa

lista.forEach(valor => {
    console.log(valor)
}

// Estructura for...in    
let persona = {
    nombre: "Juan",
    edad: 25,
    peso: 70
}
recorre un objeto
for (let propiedad in persona) {
    console.log(propiedad)      //imprime el nombre de las propiedades
    console.log(persona[propiedad])     //imprime el valor de las propiedades
}

let i = 0;
let max = 10;
while (i < max) {
    console.log(i);
    i++;
}

i = 15;
// Do...while
do {
    console.log("Estoy en el do while")
} while (i < max)

*/

/* labels
Lo que hacemos es etiquetar cada bucle para hacerl break a un bucle en concreto

let unidades = 0
let decenas = 0
bucleDecenas: while (true) {
    bucleUnidades: while (true) {
        console.log(`El número actual es: ${decenas}${unidades}`)
        unidades++
        if (unidades === 10) {
            unidades = 0
            break bucleUnidades
        }
        if (decenas === 2) {
            break bucleDecenas
        }
    }
    decenas++
}
console.log("Ya hemos terminado")
*/

/* Strings
Para poner comillas en consola, tambien se puede usar \"
console.log('Hola "Mundo"') 

Colocar variables en strings usando comillas invertidas `` y el signo ${}:
let nombre = "Juan";
let saludo = `Hola ${nombre}, ¿cómo estás?`;
console.log(saludo);

// Plantillas HTML, escribir html en js
let plantilla = `
<html>
    <h1>Página web de ${nombre}</h1>
    <p>Este es un párrafo</p>
</html>
`;

Coger ciertos caracteres de un string, actualmentes solo se usa slice
let str = "Hola soy un string";
let slice_str = str.slice(5, 10)

let substring_str = str.substring(5, 10)

Reemplazar contenido de una cadena de texto:  replace
let cadena = "Hola mi nombre es juan";
console.log(cadena.replace("juan", "pedro"));

let texto_largo = "Tito no es un mono cualquiera. A Tito no le gusta trepar por los árboles y odia comer plátanos. Él prefiere pasear por el bosque, oler las flores y recoger las nueces que se caen de los árboles."

// Al utilizar strings sólo reemplaza la primera instancia
console.log(texto_largo.replace('los', 'cinco'))

// Al utilizar la expresión regular /g (global), reemplaza todas las instancias
console.log(texto_largo.replace(/los/g, 'cinco'))



*/

/* Modificar texto
toLowerCase() y toUpperCase() para poner minuscula y mayuscula

str_1concat(" ", str_2) para concatenar dos strings diferentes con espacio en medio

tambien se puede usar el + para concatenar como en java 

console.log(`${str_1} ${str_2}`) otra forma de concatenar con ${}

str_1.trim() para quitar espacios en blanco al principio y al final

trimStart() y trimEnd() para quitar espacios solo al principio o al final

chartAt(2) para obtener el caracter en la posicion que se le indique

indexOf("hola") para obtener en que posicion esta la palabra que se le indique
lastIndexOf("hola") para obtener en que posicion esta la ultima palabra hola

match(/hola/g) para obtener todas las palabras hola que hay en el string, no la cantidad sino las palabras

includes("hola") para saber si el string contiene la palabra hola, devolviendo un booleano

startsWith("hola") para saber si el string empieza por la palabra hola, devolviendo un booleano

endsWith("hola") para saber si el string termina por la palabra hola, devolviendo un booleano




*/

/* Numbers
Math.round(5.4) redondea el numero
toFixed(2) redondea el numero a dos decimales convirte a string para devolverlo
toPrecision(3) devuelve el numero con solo 2 cifras significativas, de 12.432 a 12.4 convirte a string para devolverlo

// Operador .valueOf() - Obtener valores numéricos a partir de literales
let a = 2;
let b = new Number(3);

console.log(a);
console.log(b);
console.log(a + b);
console.log(a + b.valueOf());

NaN no es sun numero                
let n = Number("Hola");   
console.log(n);

Infinity valor cuando dividimos por 0 o por null

Convertir string a numero
let n = Number("123");

parseInt("123.4") para convertir a entero y quitar decimales
parseFloat("123.4") para convertir a float y seguir con los decimales

convertir hexadecimal a decimal parseInt("0x1f", 16) 16 es la base del numero hexadecimal



*/

/* Listas
Introducir valores array:

let array = [1, 2, 3, 4, 5, 6, 7, "8", null];
array.push(9,"o",3, true); //añade elementos al final de la lista
array.unshift("inicio"); //añade elementos al principio de la lista

array.pop(); //elimina el ultimo elemento de la lista
array.shift(); //elimina el primer elemento de la lista

Eliminar, modificar o añadir valores al array
array.splice(2, 1); //elimina 1 elemento a partir de la posicion 2 (3 en el array)

añadir valor
array.splice(2, 0, "hola"); //añade hola en la posicion 2 (3 en el array)

modificar valor 
array.splice(2, 1, "hola"); //modifica el valor en la posicion 2 (3 en el array) por hola

Unir dos listas
let array2 = [10, 11, 12];
let array3 = array.concat(array2);

... para unir dos listas, factor de propagacion
let array4 = [...array, ...array2]      //los tres puntos se usan para unir cada 
let array5 = [array, array2]    //Este entrega una lista con dos listas dentro 

agarrar una parte de la lista y guardarla en otra
array2 =array.slice(2, -2) //corta la lista desde la posicion 2 hasta la penultima posicion

Iterar valores de una lista
Forma antigua:
for (let i = 0; i < array.length; i++) {
    console.log(array[i]);
}
Nueva forma:
array.forEach(valor => {
    console.log(valor);
});

Buscar un valor en una lista
array.find(valor => valor === 3) //recorriendo la lista con valor y que si valor es = 3 lo imprima

Buscar un valor dentro de una lista de objetos
const listaObjetos=[
    {nombre:"Juan", edad: 25},
    {nombre:"Pedro", edad: 30},
    {nombre:"Ana", edad: 20}
]
const objeto = listaObjetos.find(objeto => o.nombre === "Pedro"); //busca y dev el objeto con nombre pedro



*/

/* Listas Metodos avanzados de obtencion de arrays

const array = [1, 2, 3, 4, 5, 6, 7, 8, 9];
//el indice es 0,1,2,3 cada iteracion se podria decir
array.map(valor, indice => `${indice + 1} - ${valor}`); //devuelve una lista con los valores modificados con un numero y -

const listaObjetos=[
    {nombre:"Juan", edad: 25},
    {nombre:"Pedro", edad: 30},
    {nombre:"Ana", edad: 20}
]
Filtrar por condición
listaObjetos.filter(objeto => objeto.edad > 20); 
//devuelve una lista con los objetos que cumplan la condicion de mas de 20 años

Devolver la lista sin miguel
listaObjetos.filter(obj => obj.nombre !== "Miguel");

Reduce, devuelve un valor unico a partir de una lista de valores:

array.reduce((ant,cur,i, arrayOriginal) => ant + cur); //devuelve la suma de todos los valores de la lista




*/


/*Ordenar lista de objetos
let array = [5, 2, 3, 4, 1, 6, 7, 8, 9];
array.sort((a,b) => a-b); //ordena la lista de menor a mayor sí a-b y de mayor a menor b-a
console.log(array);


const listaObjetos=[
    {nombre:"Juan", edad: 25},
    {nombre:"Pedro", edad: 30},
    {nombre:"Ana", edad: 20}
]
listaObjetos.sort((a,b) => a.edad - b.edad); //ordena la lista de menor a mayor por edad

//Comparar listas
const lista1 = [1,2,3,4];
// Every todos los valores deben cumplir
const resultado = lista1.every(valor => valor > 0); //devuelve true si todos los valores son mayores a 0
const resultado2 = lista1.every(valor => valor === "number"); //devuelve true si todos los valors son tipo numero

const ar1 = [1, 2, 3, 4]
const ar2 = [1, 2, 3, 4]

const compararArrays = (array_1, array_2) => {
    if (array_1.length !== array_2.length) return false
    const res = array_1.every((valor, i) => valor === array_2[i])
    return res
}

//some al menos un valor debe cumplir
const resultado3 = lista1.some(valor => valor === 3); //devuelve true si al menos un valor es igual a 3
console.log(resultado3);
//si fuera encontrar el nombre en una lista de objetos
const resultado4 = listaObjetos.some(objeto => objeto.nombre === "Pedro"); //devuelve true si al menos un valor es igual a 3

//obtener lista a partir de un objeto iterable como un string
const str = "Hola Mundo";
console.log(str[5]);
const ar_str = Array.from(str); //este Array no es el mismo que el de arriba, este es un metodo
console.log(ar_str);

//convertimos una lista a objeto iterable, con las posiciones de la lista
const keys = lista1.keys(); //devuelve un objeto iterable con las posiciones de la lista
console.log(keys);
*/

/* Set o conjuntos que tienen valores unicos dentro
const array = [1, 2, 3, 4, 5, {id: 6}, 7, 8, 9];
const miSet = new Set(array); //crea un conjunto a partir de una lista
miSet.add(10); //añade un valor al conjunto
miSet.delete(10); //elimina un valor del conjunto
//miSet.clear(); //elimina todos los valores del conjunto
miSet.has(1); //tambien se puede con .include devuelve true si el conjunto tiene el valor 1, booleano
miSet.size; //devuelve el tamaño del conjunto

//recorrer un conjunto
miSet.forEach(valor => console.log(valor));
//para que sea un objeto que se pueda miSet[2] se hace lo siguiente
const ar_miSet = [...miSet] //
console.log(ar_miSet[2]);
const it_miSet = miSet.values(); //devuelve un objeto iterable con los valores del conjunto
const it_miSet2 = it_miSet.next(); //devuelve el primer valor del conjunto





*/

/* Objetos

const obj = {
    id : 1,
    nombre : "Juan",
    edad : 25,
    casado : false,
    hijos : ["Pedro", "Ana"],
    "4-juegos": [1,2,3,4]       //para poder definir un atributo con un numero o con un guion usar ""
}

obj.nombre = "Pedro"; //modificar un atributo del objeto

console.log(obj["4-juegos"]); //para acceder a un atributo con un numero o con un guion usar []

//acceder a propiedades con variables
const prop = "edad";
console.log(obj[prop]);

//como copiar objetos, no se puede hacer obj2 = obj1 porque se copia la referencia en memoria
const obj3 = {...obj}; //se crea un nuevo objeto con los mismos atributos que el anterior
const obj2 = new Object(obj); //se crea un nuevo objeto con los mismos atributos que el anterior

//como ordenar listas de objetos en funcion de una propiedad
const listaPeliculas = [
    {titulo: "El señor de los anillos", director: "Peter Jackson", año: 2001},
    {titulo: "El hobbit", director: "Peter Jackson", año: 2012},
    {titulo: "Harry Potter", director: "Chris Columbus", año: 2001},
    {titulo: "Harry Potter 2", director: "Chris Columbus", año: 2002},

]
listaPeliculas.sort((a,b) => a.año - b.año); //ordena la lista de menor a mayor por año
console.log(listaPeliculas);





*/

/* Fechas 
const fecha = new Date(); //fecha actual
const fecha2 = new Date(2020, 0, 20, 10, 30, 0); //año, mes 0 = enero, dia, hora, minuto, segundo
const fecha3 = new Date("October 12 2020 10:30:00"); //fecha en formato string
console.log(fecha3);
console.log(fecha < fecha2); //comparar fechas si fecha esta antes que fecha2

//comparar fechas para ver si son iguales:
console.log(fecha2.getTime() === fecha2.getTime()); //getTime devuelve el numero de milisegundos desde 1970
//obtener día
console.log(fecha2.getDate()); //devuelve el dia de la semana, 0 = domingo
//obtener mes
console.log(fecha2.getMonth()+1); //devuelve el mes pero se le suma 1 porque empieza en 0 enero
//obtener año  
console.log(fecha2.getFullYear()); //devuelve el año

//mostrar fecha en string
console.log(fecha2.toLocaleDateString("en-GB")); //devuelve la fecha en string





*/

/*Consolas
about:blank para abrir una pagina en blanco en explorer
mas herramientas: herramientas del desarrollador
Console
//mostrar un cuadro de dialogo para recibir info en pagina web
const edad = prompt("Introduce edad"); //esto solo funciona en consola



*/

/* Funciones
//se tiene que usar `` para poder poner variables dentro del string
function saludar(a, b, c){
    console.log(`Hola ${a} + ${b} + ${c}`);
    console.log(`hola ${a} + ${b} + ${c}`); 

}

saludar(1, 2, "Ana");
//si envio un objeto a la funcion y modifico el objeto dentro de la funcion esta va a modificar 
// el objeto original

//parametros opcionales o por defecto, no necesita que le envien numeros obligatoriamente
function suma(numero = 9, numero2 = 10, numero3 = 10){
    console.log(numero + numero2 + numero3);
}
suma();

//funciones con parametros infinitos, numeros seria un array con todos los parametros que se le envien
function suma2(...numeros){
    console.log(numeros);
    let resultado = 0;
    for (let i = 0; i < numeros.length; i++) {
        resultado += numeros[i];
    }
    //tambien se puede con reduce
    resultado = numeros.reduce((a,b) => a+b);
    console.log(resultado);
}
suma2(1,2,3,4,5);

//funcion anonima
const array = [1,2,3,4,5,6,7,8,9];

//es anonima porque no tiene nombre solo la definimos en ese parentesis en el momento
const array2 = array.map((valor ) => valor * 2)        //map le hace eso a cada elemento de la lista
console.log(array2);

// tipo flecha =>
const doble = valor => valor * 2 //es una funcion en una variable
console.log(doble(3));

//carga y sobre carga de funciones
// se puede llamar a otra funcion dentro de otra funcion y usar recursividad


//Funciones asincronas y promesas
// Son funciones que quedaran en segundo plano porque tardan mucho en ejecutarse o tienen que
// esperar a terceros

let a =3;
const miPromesa = new Promise((resolve, reject) => {
    if (a > 2){
        resolve()   //si se cumple la condicion se ejecuta resolve
    }else{
        reject()    //si no se cumple la condicion se ejecuta reject
    }})

miPromesa
    //si se cumple la promesa se ejecuta esto
    .then(() => console.log("Se ejecuto de forma correcta")) 
    //si no se cumple la promesa se ejecuta esto
    .catch(() => console.log("Error, Se ejecuto de forma incorrecta")) 
    .finally(() => console.log("Se ejecuta siempre")) //se ejecuta siempre

//funciones generadoras
// se pone * luego de funciont para indicar que es una funcion generadora
function* generaId(){
    let id = 0;
    while(true){
        id++;
        // hasta que vuelvan    a llamar a la funcion
        if (id === 10){
            return id;
        }
        yield id; //lo que hace es retornar pero sin salir de la funcion, se queda esperando 
    }
}
// le colocamos la funcion a un parametro y llamamos gen.next para que vaya a la siguiente iteracion 
// de la funcuin
const gen = generaId();
console.log(gen.next()); //devuelve el valor de id
console.log(gen.next().value);  //voy al  siguiente valor de la funcion pero solo cogiendo el valor

 
 */


/* Errores  try catch finally
const funcion = val => {
    if (typeof val === "number") {  //verificamos que sea un string
        return 2 * val;
    }
    throw new Error("El valor debe ser un numero"); //enviamos un error con el texto que queramos
}
try {       //intenta hacer esto
    console.log(funcion("a"))
}catch (e){ //en caso de fallar ejecuta esto
    console.log(e.message); //imprime el mensaje de error
}finally{   // siempre se ejecuta
    console.log("Se ejecuta siempre");
}



*/

/*  Gestion de logs en Node.js
// npm install winston          para instalar winston que es un guardado de logs para verlos en futuro
// nos crea una carpeta node_modules con el modulo winston


//inicialización de winston y objeto logger, crea los archivos de guardado de logs combined y error.log
const winston = require('winston');

const logger = winston.createLogger({
  level: 'info',
  format: winston.format.json(),
  defaultMeta: { service: 'user-service' },
  transports: [
    //
    // - Write all logs with importance level of `error` or less to `error.log`
    // - Write all logs with importance level of `info` or less to `combined.log`
    //
    new winston.transports.File({ filename: 'error.log', level: 'error' }),
    new winston.transports.File({ filename: 'combined.log' }),
  ],
});
module.exports = logger; //para poder usar el logger en otros archivos

const logger = require('./logger')      //para importar el logger si estuviera en otra carpeta

// logger.log("Hola estoy saliendo por pantalla")
logger.info("Hola esto es un mensaje informativo")
logger.debug("Esto es un mensaje de debug")
logger.warn("Esto es un mensaje de advertencia")
logger.error("Esto es un error")
*/

/* Modulos              son partes de codigo que se pueden usar en cualquier parte del proyecto
// Se crea un archivo centralizado para agarrar las funciones y variables que se quieran usar

// Formas de importar
CommonJS - require
Import ES6 - import

// CommonJS - require   
//en el archivo que se quiere exportar se pone 
module.exports = {
    nombre_variable,
    nombre_funcion,
    nombre_clase
}
//importarlas en otro archivo
const moduloX = require("./modulos/moduloX") //se pone ./ para indicar que esta en la misma carpeta que index
------------para importar directamente y no tener que poner moduloX.funcionX--------------
const {funcionX, funcionY} = require("./modulos/moduloX");
//usar las funciones del otro modulo:
const resultado = funcionX(1,2);


// Import ES6 - import
// para importar con es6 en la otra carpeta de modulos tiene que tener su propio package.json y
//se tiene que poner "type": "module" en el package.json

//Exportar, en el archivo que se quiere exportar a la funcion se le pone export antes de, igual a variable
export function funcionX(a,b){ return a+b}

//importarlas en otro archivo 
import {funcionX, funcionY} from "./modulos/moduloX"
//con esto ya podriamos usar:
const resultado = funcionX(1,2);

//para importar todo lo que hay en el archivo
import * as moduloX from "./modulos/moduloX"

//exportar por default, solamente a uno por archivo
export default funcionX
//para importarlo solo pone sin {}
import funcionX from "./modulos/moduloX"
*/

/*Librerias en Node y Npm
son modulos externos que se pueden usar en cualquier proyecto
npm install para instalar librerias

//instalar axios
npm i axios
va y crea en node_modules una carpeta axios y follow-redirects

import axios from "axios";
//axios es una libreria para usar apis
// ejemplo
axios.get("https://jsonplaceholder.typicode.com/users")
    .then(res => console.log(res.data))
    .catch(err => console.log(err));

// librerias interesantes

*/

/* POO Clases   Objetos avanzados
// metodo que crea personas     -No es la mejor opcion
const creaPersona = (nombre, apellido) => {
    return {
        nombre,
        apellido
    }
}
const nueva_persona = creaPersona("Juan", "Perez");
console.log(nueva_persona);

class Persona{
    //Privado #, para que no se pueda acceder desde afuera solo dentro de esta clase  y no hijas
    _nombre;
    _edad;
    //Protegidas _ No se accede desde afuera solo dentro de la clase y clases hijas tambien
    saludo(){
        console.log(`Hola soy ${this._nombre} y tengo ${this._edad} años`);
    }

    constructor(nombre, edad){
        this._nombre = nombre;
        this._edad = edad;
    }

    getNombre(){
        return this._nombre;
    }
    setNombre(nombre){
        this._nombre = nombre;
    }

}
const nueva_persona = new Persona("Juan", 25);
//instanceof es como typeof pero para objetos, 
console.log(nueva_persona instanceof Persona); //devuelve true si es una instancia de la clase Persona

//herencia y polimorfismo
class Persona2 extends Persona{
    _profesion;
    constructor(nombre, edad, profesion){
        super(nombre, edad); //llama al constructor de la clase padre
        this._profesion = profesion;
    }
    saludo(){       //polimorfismo, sobreescribir el metodo de la clase padre ya creado
        console.log(`Hola soy ${this._nombre} y tengo ${this._edad} y soy ${this._profesion}`);
    }
}
const p = new Persona2("Juan", 25, "Programador");
p.saludo();
console.log(p.getNombre());

/*Interfaces
En Js no hay interfaces, pero se pueden simular con clases abstractas

*/

/* libreria ESLint para estandares
Son estandares predefinidos para que todo el equipo trabaje de misma forma, saca errores predefinidos
puntos y comas tabulaciones etc, sean de tal forma

//.eslintrc.json es para configurar el ESLint



*/

/* html con js
parea instalar http server para ejecutar servidor local
npm install --global http-server

en el html se pone <script src="index.js"></script> para importar el archivo js 
en el package.json se pone "start": "http-server ." para ejecutar el servidor local

en la consola de chrome se puede ejecutar codigo js, y tambien ver lo que esta en este js

en network desactivar cache para que no se quede guardado el archivo js y se actualice cada vez

const a = 4;
const b = 8;
console.log(a+b);

//para agarrar un elemento del html por id:
const p = document.getElementById("parrafo");
console.log(p);

//integrar scripts al html de terceros:
en el src se coloca el link y listo
<script src="https://unpkg.com/typeit@8.2.0/dist/index.umd.js"></script>


*/

/* 

*/
