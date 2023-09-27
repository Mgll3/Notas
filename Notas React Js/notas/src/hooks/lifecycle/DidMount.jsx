/**
 * Ejemplo de uso del metodo de ciclo de vida en componente tipo funcional
 */

import React, { useEffect } from 'react'
import PropTypes from 'prop-types'

function DidMount(props) {

    useEffect(() => {
        console.log('Comportamiento antes de que el componente sea añadido al Dom(renderizado)');
    }, []);     //si se deja vacio el array [] solo se ejecuta una vez al inicio del componente nada más


  return (
    <div>
        <h1>Componente DidMount</h1>
    </div>
  )
}

DidMount.propTypes = {

}

export default DidMount






