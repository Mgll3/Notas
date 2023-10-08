import React, {useRef} from 'react';

const Child = ({name, send, update}) => {

    const messageRef = useRef('');
    const nameRef = useRef('');

    function pressButon(){
        const text = messageRef.current.value;
        alert('Boton pulsado: ' + text)

    }

    function pressButonParams(text){
        alert(`Text: ${text}`)
    }

    function submitName(e){
        e.preventDefault();
        update(nameRef.current.value); 
    }

    return (
        <div style={{background : 'cyan', padding: '30px'}}>
            <p onMouseOver={()=> console.log('On Mouse Over')}>{name}</p>
            <button className='btn btn-primary' onClick={pressButon}>Click me1</button>
            <button className='btn btn-primary' onClick={()=> pressButonParams('hello')}>Click me2</button>
            <input placeholder='Insert Text' onFocus={()=> console.log('on Focus')}
                onChange={(e)=> console.log('Input changed: ', e.target.value)}
                onCopy={()=> console.log('Copied text from input')}
                
                ref = {messageRef}
            ></input>
            <button className='btn btn-primary' 
                onClick={()=> send(messageRef.current.value) }>Send Message Father
            </button>
            <div style={{marginTop:'20px'}}>
                <form onSubmit={submitName}>
                    <input ref={nameRef} placeholder='New name'></input>
                    <button className='btn btn-primary' type='submit'> Update Name</button>
                </form>
                
            </div>
        </div>
    );
}

export default Child;
