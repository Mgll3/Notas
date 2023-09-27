/**
 * Ejemplo uso de metodo componentDidUpdate() de forma funcional, cuando recibe nuevos props
 * o cambia su estado privado
 */

import React, { useEffect } from 'react'

export default function DidUpdate() {

    useEffect(() => {
        console.log('Comportamiento cuando el componente se actualiza o recibe nuevas props');
    });     //si se deja sin [] entonces se ejecutara todas las veces que se actualice el componente

  return (
    <div>
        <h1>Componente DidUpdate</h1>
    </div>
  )
}
