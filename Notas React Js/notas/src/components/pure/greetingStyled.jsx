/**
 * Definiendo estilos en constantes
 */

import React, { useState } from 'react';


const loggedStyle={
    color: 'blue'
};

const notLoggedStyle={
    color: 'red',
    fontWeight: 'bold'
};

const GreetingStyled = (props) => {

    //generamos estado para el componente y ver si esta logeado o no

    const[logged, setLogged] = useState(true);
    const greetingUser = () =>  (<h1>Estas logeado</h1>) ;


    return (
        <div style={ logged ? loggedStyle : notLoggedStyle }>
            { logged ?      //Renderizado condicional
            greetingUser()
            : 
            (<h1>No estas logeado</h1>)}
            <h1>Hola, {props.name}</h1>
            <button onClick={() => {
                setLogged(!logged);
            }}>
                { logged ? 'Logout' : 'Login'}
            </button>
        </div>
    );
}

export default GreetingStyled;
