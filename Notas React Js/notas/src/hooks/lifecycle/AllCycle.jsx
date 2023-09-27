/**
 * Ejemplo de componente funcional que tiene todo el ciclo de vida
 */

import React, { useEffect } from 'react'

function AllCycle() {

    useEffect(() => {
        console.log('DidMount: Componente actualizado');

        const intervalID = setInterval(() => {
            console.log('DidUpdate: Componente actualizado');
        }, 1000);

        return () => {
            console.log('WillUnMount: Componente desmontado');
            clearInterval(intervalID);
        }
    }, []);




  return (
    <div>
        <h1>Componente AllCycle</h1>
    </div>
  )
}

export default AllCycle 



