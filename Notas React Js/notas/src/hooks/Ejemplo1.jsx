/**Ejemplo Hook useState
 * 
 * crear component tipo funcion y acceder a sue stado atraves de un hook y modificarlo
 */

import React, {useState} from 'react';
import PropTypes from 'prop-types';


const Ejemplo1 = () => {

    const valorInicial = 0;

    const personaInicial= {
        nombre: 'Juan',
        email: 'martin@ude.edu.co'
    }
    const [contador, setContador] = useState(valorInicial); 
    const [persona, setPersona] = useState(personaInicial);

    /**
     * Funcion que incrementa el contador
     */
    function incrementarContador(){
        
        setContador(contador + 1);
    }

    /**
     * Funcion actualizar persona
     */
    function actualizarPersona(){
        setPersona({
            nombre: 'Pepe',
            email: 'pepe@dmail.com'
        });
    }

    return (
        <div>
            <h1>Contador: {contador}</h1>
            <button onClick={incrementarContador}>Incrementar</button>
            <h1>Persona: {persona.nombre}</h1>
            <h1>Email: {persona.email}</h1>
            <button onClick={actualizarPersona}>Actualizar</button>
            
        </div>
    );
};


Ejemplo1.propTypes = {

};


export default Ejemplo1;







