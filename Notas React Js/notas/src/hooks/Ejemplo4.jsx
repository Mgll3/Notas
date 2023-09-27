import React from 'react';
import PropTypes from 'prop-types';


const Ejemplo4 = (props) => {
    return (
        <div>
            <h1> Ejemplo Children Props</h1>
            <h2> Nombre: { props.nombre }</h2>
            {/* props.children va a traer lo que se haya colocado dentro de <Ejemplo4> esto <Ejemplo4/> */}
            {props.children}
        </div>
    );
};


Ejemplo4.propTypes = {

};


export default Ejemplo4;
