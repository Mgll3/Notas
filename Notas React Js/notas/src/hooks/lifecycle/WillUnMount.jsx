/**
 * Ejemplo uso componentWillUnMountControlar cuando el componente va a desaparecer
 */

//rfc
import React, { useEffect } from 'react'

export default function WillUnMount() {

    useEffect(() => {  
        return () => {
            console.log('Comportamiento cuando el componente va a desaparecer');
        }
    }, []);// [] vacio para que se ejecute una vez

  return (
    <div>
        <h1>Componente WillUnMount</h1>
    </div>
  )
}


