import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { Task } from '../../models/task.class.js';
import 'bootstrap-icons/font/bootstrap-icons.css'
import '../../styles/task.css'
import { levels } from '../../models/levels.enum.js';

const TaskComponent = ({task}) => {

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
            case levels.MEDIUM:
                return <h6 className='mb-0'><span className='badge bg-warning'>Medium</span></h6>
            case levels.HIGH:
                return <h6 className='mb-0'><span className='badge bg-danger'>High</span></h6>
            default:
                break;
            
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
                {/* TODO: Sustituir por un badge */}
                <span >{task.level}</span>
            </td>
            <td className='align-middle'>
                { task.completed ? 
                    <i className='bi-toggle-on' style ={{color: 'green',fontWeight:'bold'}}></i> 
                    : <i className='bi-toggle-of' style ={{color: 'red',fontWeight:'bold'}}></i> }
                <span >{task.completed ? 'Si' : 'No'}</span>
                <i className='bi-trash'></i>
            </td>
        </tr> 
    );
};

TaskComponent.propTypes = {
    task: PropTypes.instanceOf(Task),
};


export default TaskComponent;
