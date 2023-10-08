import React, { useRef } from 'react';
import PropTypes from 'prop-types';
import { levels } from '../../../models/levels.enum';
import { Task } from '../../../models/task.class';
const TaskForm = ({add}) => {

    const nameRef = useRef('');
    const descriptionRef = useRef('');
    const levelRef = useRef(levels.NORMAL);

    function addTask(e){
        e.preventDefault();
        const name = nameRef.current.value;
        const description = descriptionRef.current.value;
        const level = levelRef.current.value;
        const newTask = new Task(name, description, false, level);
        add(newTask);
    }

    return (
        <form onSubmit={addTask} className='d-flex justify-content-center align-items-center mb-4'>
            <div className='form-outline flex-fill'>
                <input ref = {nameRef} id='inputName' type='text' 
                className='form-control form-control-lg' required autoFocus placeholder='Task Name'></input>
                <input ref = {descriptionRef} id='inputDescription' type='text' 
                className='form-control form-control-lg' required placeholder='TaskDescription'></input>
                <label htmlFor='selectLevel' className='sr-only'>Priority</label>
                <select ref={levelRef} defaultValue={levels.NORMAL} id='selectLevel'>
                    <option value={levels.NORMAL}>Normal</option>
                    <option value={levels.URGENTE}>Urgent</option>
                    <option value={levels.BLOCKING}>Blocking</option>
                </select>
                
            </div>
            <button type='submit' className='btn btn-succes btn-lg ms-3'> Add </button>
        </form>
    );
}

TaskForm.propTypes = {
    add: PropTypes.func.isRequired
};

export default TaskForm;
