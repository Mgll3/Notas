import React, { useState } from 'react';
import Child from '../pure/child';

const Father = () => {

    const [name, setname] = useState('Martin');

    function showMessage(text){
        alert('Hello from father: '+ text)
    }

    function updateName(newName){
        setname(newName);
    }

    return (
        <div style={{background:'tomato', padding: '10px' }}>
            <Child name={name} send={showMessage} update={updateName}></Child>
        </div>
    );
}

export default Father;
