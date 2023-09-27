/**
 * Ejemplo Hooks:
 * - useState()
 * - useContext()
 */

import React, { useContext, useState } from 'react';
import PropTypes from 'prop-types';


/**
 * 
 * @returns Componente1
 * Dispone de un contexto que va a tener un valor
 * que recibe desde el padre
 */
const miContexto= React.createContext(null);

const Componente1 = () => {
    //Inicializamos un estado vacio para rellenarlo con el contexto del padre
    const state = useContext(miContexto);


    return (
        <div>
            <h1>El Token es: {state.token}</h1>
            {/* pintamos el componente 2 */}
            <Componente2></Componente2>
        </div>
    );
};

const Componente2 = () => {

    const state = useContext(miContexto);

    return (
        <div>
            <h2>La sesion es.  {state.session}</h2>
        </div>
    );
};


export default function MiComponenteConContexto(){
    const estadoInicial = {
        token: '123456789',
        session: 1
    }

    //Creamos el estado de este componente
    const [sessionData, setSessionData] = useState(estadoInicial);

    function actualizarSesion(){
        setSessionData(
            {
                token: 'JWT123456',
                session: sessionData.session + 1
            }
        )
    }

    return (
        <miContexto.Provider value={sessionData}>
            {/* Todo lo que este aqui adentro puede leer los datos de sessionData */}
            {/* Además si se actualiza, los componentes de aquí, tambien lo actualizan */}
            <h1> Ejemplo useState y useContext</h1>
            <Componente1></Componente1>
            <Componente2></Componente2>
            <button onClick={actualizarSesion}>Actualizar Sesión</button>

        </miContexto.Provider>
    )

    
};






