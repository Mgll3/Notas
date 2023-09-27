/**
 * Ejemplo de componente tipo clase con metodos del ciclo de vida de react
 */

import React, { Component } from 'react'

export default class lifeCycleExample extends Component {

    constructor(props){
        super(props);
        console.log('Primera vez que se ejecuta el componente');
    }

    componentWillMount(){
        console.log('Antes del montaje del componente');
    }

    componentDidMount(){
        console.log('Justo en el montaje del componente, pero antes de renderizarlo');
    }

    componentWillReceiveProps(){
        console.log('Cuando va a recibir nuevas props');
    }

    shouldComponentUpdate(){
        console.log('Controlar si el componente debe o no actualizarse');
        //return true or false
    }

    componentWillUpdate(){
        console.log('justo Antes de actualizar el componente');
    }

    componentDidUpdate(){
        console.log('Justo despues de actualizar el componente');
    }

    componentWillUnmount(){
        console.log('Justo antes de desmontar el componente');
    }

    render() {
        return (
            <div>
                
            </div>
        )
    }
}







