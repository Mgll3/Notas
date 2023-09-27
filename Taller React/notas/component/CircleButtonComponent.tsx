import React from 'react'

interface CircleButtonComponent {
    buttonName: string;
    action: () => void;
}

const CircleButtonComponent = () => {
  return (
    <div>
        <button className="circle-button">+</button>
    </div>
  )
}

export default CircleButtonComponent
