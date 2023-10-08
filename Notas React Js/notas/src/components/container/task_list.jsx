import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { Task } from '../../models/task.class.js';
import { levels } from '../../models/levels.enum';
import TaskComponent from '../pure/task.jsx';
import TaskForm from '../pure/forms/taskForm.jsx';

const TaskListComponent = () => {
    const defaultTasks = new Task('Ejemplo', 'Description', true, levels.NORMAL)
    const defaultTasks2 = new Task('Ejemplo2', 'Description2', false, levels.URGENTE)
    const defaultTasks3 = new Task('Ejemplo3', 'Description3', false, levels.BLOCKING)

    //estado del componente
    const [tasks, setTasks] = useState([defaultTasks, defaultTasks2, defaultTasks3])
    //control ciclo de vida
    const [loading, setLoading] = useState(true)
    useEffect(() => {
        console.log('Task State ha sido modificado')
        setLoading(false);
        return () => {
            console.log('TasList va ha ser desmontado')
        }
    }, [tasks]);

    function completeTask(task){
        const index = tasks.indexOf(task);
        const tempTasks = [...tasks];
        tempTasks[index].completed = !tempTasks[index].completed;
        setTasks(tempTasks);
    }

    function deleteTask(task){
        const index = tasks.indexOf(task);
        const tempTasks = [...tasks];
        tempTasks.splice(index,1); //Borra un elemento en la posicion index
        setTasks(tempTasks);
    }

    function addTask(task){
        const index = tasks.indexOf(task);
        const tempTasks = [...tasks];
        tempTasks.push(task);
        setTasks(tempTasks);
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
                                {tasks.map((task,index) => {
                                    return (<TaskComponent 
                                    key={index} task={task} complete = {completeTask} deleteT ={deleteTask} ></TaskComponent>)
                                })}
                            </tbody>
                            
                        </table>
                    </div>
                </div>
                            
                <TaskForm add={addTask}></TaskForm>
            </div>
            
        </div>
    );
};


TaskListComponent.propTypes = {
    task: PropTypes.instanceOf(Task),
};


export default TaskListComponent;
