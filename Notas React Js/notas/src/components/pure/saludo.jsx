import React, { Component } from 'react';
import PropTypes from 'prop-types';


class Saludo extends Component {

    constructor(props) {
        super(props);
        this.state = {  //inicializamos edad en 29
            age : 29
        };
    }

    render() {
        return (
            <div>
                <h1>    
                Hola {this.props.name} edad: {this.state.age}
                </h1>
                <div>
                    {/*Boton que ejecuta la funcion birthday */}
                    <button onClick={this.birthday}>Cumplir años</button>
                    
                </div>
            </div>
        );
    }
    
    birthday = () => {          //Funcion que hace que se actualice la edad con estados
        this.setState((prevState) => (  //actualiza, o renderiza de nuevo la vista para poder ver cambios
            {
                age: prevState.age + 1, //modificamos el estado de age 
            }
        ))
        
    }

}




Saludo.propTypes = {        //En la clase saludo le definimos que el name es un string
    name: PropTypes.string,   //y que es obligatorio que se le pase un name al componente
}; 


export default Saludo;

