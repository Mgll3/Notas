import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { Task } from '../../models/task.class.js';
import { levels } from '../../models/levels.enum';
import TaskComponent from '../pure/task.jsx';

const TaskListComponent = () => {
    const defaultTasks = new Task('Ejemplo', 'Ejemplo', false, levels.NORMAL)
    //estado del componente
    const [tasks, setTasks] = useState([defaultTasks])
    //control ciclo de vida
    const [loading, setLoading] = useState(true)
    useEffect(() => {
        console.log('Task State ha sido modificado')
        setLoading(false);
        return () => {
            console.log('TasList va ha ser desmontado')
        }
    }, [tasks]);

    const changeCompleted = (id) => {
        console.log('TODO: Cabmiar estado de una tarea');
    }

    return (
        <div>
            <div className='col-12'>
                <div className='card'>
                    <div className='card-header p-3'>
                        <h5>
                            Tu Task: 
                        </h5>
                    </div>
                    <div className='card-body' data-mdb-perfect-scrollbar = 'true' style={ {position: 'relative', height:'200px' } }>
                        <table>
                            <thead>
                                <tr>
                                    <th scope = 'col'>Title</th>
                                    <th scope = 'col'>Description</th>
                                    <th scope = 'col'>Priority</th>
                                    <th scope = 'col'>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {/* TODO iterar sobre lista de tareas */}
                                <TaskComponent task ={defaultTasks}></TaskComponent>
                            </tbody>
                        </table>
                    </div>
                </div>
                {/* TODO: Aplicar un for/map para renderizas una lista */}
            </div>
            
        </div>
    );
};


TaskListComponent.propTypes = {
    task: PropTypes.instanceOf(Task),
};


export default TaskListComponent;
