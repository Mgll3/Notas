/**
 * Ejemplo de uso de:
 * -useState()      variables de estado del componente (variables que se pueden modificar) en la vista
 * -useEffect()     cambios en la vista
 * -useRef()        identificar/referenciar valores o elementos dentro de la vista
 */

import React, {useState, useRef, useEffect} from 'react';
import PropTypes from 'prop-types';


const Ejemplo2 = () => {

    //crear dos contadores distintos
    //cada uno en un estado diferente
    const [contador1, setContador1] = useState(0);
    const [contador2, setContador2] = useState(0);
    //crear una referencia useRef() par aasociar una variable con un elemento del DOM del componente(vista html)
    const refContador1 = useRef();

    function incrementar1(){
        setContador1(contador1 + 1);
    }

    function incrementar2(){
        setContador2(contador2 + 1);
    }

    /**
     * Trabajando useEffect
    */

    /**
     * ? Caso 1: Cada que hay un cambio en el estado del componente se ejecuta lo que este en useEffect
     * 
     */
    // useEffect(() => {
    //     console.log('useEffect 1, referencia del DOM: ', refContador1);
    // }
    // );

    /**
     * ? caso 2: Cada que haya cambio en contador 1 si habra ejecucion de useEffect()
     * 
     */
    useEffect(() => {
        console.log('useEffect, cambio contador 1 o 2, referencia del DOM: ', refContador1);
    }, [contador1,contador2]);    //variables a monitoriear si cambia


    return (
        <div>
            <h1>Contador 1: {contador1}</h1>
            <h1>Contador 2: {contador2}</h1>
            
            <hr></hr>
            <h2 ref={refContador1}>Ejemplo de referencia</h2>
            <button onClick={incrementar1}>Incrementar 1</button>
            <button onClick={incrementar2}>Incrementar 2</button>   x
        </div>
    );
};


Ejemplo2.propTypes = {

};


export default Ejemplo2;




