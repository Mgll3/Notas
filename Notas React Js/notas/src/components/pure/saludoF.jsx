import React, {useState} from 'react';
import PropTypes from 'prop-types';

//forma de hacerlo por funciones (como se usa actujalmente mejor)
const SaludoF = (props) => {

    const [age,setAge] = useState(29);

    const birthday = () => {          //Funcion que hace que se actualice la edad con estados
        setAge(age + 1);
    }

    return (
        <div>
            <h1>    
            Hola {props.name} 
            edad: {age}
             
            </h1>
            <div>
                {/*Boton que ejecuta la funcion birthday */}
                <button onClick={birthday}>Cumplir años</button>
                    
            </div>
        </div>
    );
};


SaludoF.propTypes = {
    name: PropTypes.string,  
};


export default SaludoF;
