import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { Task } from '../../models/task.class.js';
import 'bootstrap-icons/font/bootstrap-icons.css'
import '../../styles/task.css'
import { levels } from '../../models/levels.enum.js';

const TaskComponent = ({task, complete, deleteT}) => {

    useEffect(() => {
        console.log('Tarea creada')
        return () => {
            console.log(`Task: ${task.name}   va a ser desmontada`) // ` se usa para poder poner variables dentro
        }
    },[task]);
    
    /**
     * Funcion que retorna un badge segun el nivel de prioridad
     */
    function taskLevelBadge(){
        switch(task.level){
            case levels.NORMAL:
                return <h6 className='mb-0'><span className='badge bg-primary'>Normal</span></h6>
            case levels.URGENTE:
                return <h6 className='mb-0'><span className='badge bg-warning'>Medium</span></h6>
            case levels.BLOCKING:
                return <h6 className='mb-0'><span className='badge bg-danger'>High</span></h6>
            default:
                break;
            
        }
    }
    

    /**
     * Funcion que retorna icono ependiendo si la tarea esta completada o no
     * @returns 
     */
    function taskCompletedIcon(){
        if (task.completed){
            return (<i onClick={() => complete(task)} className='bi-toggle-on task-action' style ={{color: 'green',fontWeight:'bold'}}></i>) 
        }else{
            return (<i onClick={() => complete(task)} className='bi-toggle-off task-action' style ={{color: 'red',fontWeight:'bold'}}></i> )  
        }
                
    }

    return (
        <tr>
            <th>
                <span className='ms-2'>{task.name}</span>
            </th> 
            <td className='align-middle'>
                <span >{task.description}</span>
            </td>     
            <td className='align-middle'>
                {taskLevelBadge()}
            </td>
            <td className='align-middle'>
                { taskCompletedIcon()}
                <span >{task.completed ? 'Si' : 'No'}</span>
                <i onClick={() => deleteT(task)} className='bi-trash task-action' style={{color:'tomato' }}></i>

                
            </td>
        </tr> 
    );
};

TaskComponent.propTypes = {
    task: PropTypes.instanceOf(Task).isRequired,
    complete: PropTypes.func.isRequired,
    deleteT: PropTypes.func.isRequired
};


export default TaskComponent;
