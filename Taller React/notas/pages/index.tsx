import React, { useEffect, useState } from 'react';
import Style from '../styles/Home.module.css';
import CircleButtonComponent from '@/component/CircleButtonComponent';

const home = () => {

  const [neutro,setNeutro] = useState("neutro");
  const [numero, setNumero] = useState(0);
  const Incrementar = () => {
    console.log("Incrementar");
    setNumero(numero + 1)
  }

  const Decrementar = () => {
    console.log("Decrementar");
    setNumero(numero - 1)
  }
  const validarNumero = () => {
    if (numero < 0) {
      setNeutro("negativo")
    } else if (numero > 0) {
      setNeutro("positivo")
    }
  }
  useEffect(() => {
    console.log("useEffect");
    validarNumero()
  }, [numero])


  return (
    <div className={Style.debug}>
      <p>{neutro}</p>
      <p>{numero}</p>
      <button onClick={Incrementar}>Incrementar</button>
      <button onClick={Decrementar}>Decrementar</button>
      <CircleButtonComponent  />
    </div>
  );
} 

export default home;