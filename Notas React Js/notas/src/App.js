import logo from './logo.svg';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import TaskListComponent from './components/container/task_list';
import Ejemplo1 from './hooks/Ejemplo1';
import Ejemplo2 from './hooks/Ejemplo2';
import MiComponenteConContexto from './hooks/Ejemplo3';
import Ejemplo4 from './hooks/Ejemplo4';
import AllCycle from './hooks/lifecycle/AllCycle';
import GreetingStyled from './components/pure/greetingStyled';
import Father from './components/container/father';


function App() {
  return (
    <div className="App">
      <TaskListComponent></TaskListComponent>
      {/* <header className="App-header"> */}
        {/* <img src={logo} className="App-logo" alt="logo" /> */}
        
        {/* <MiComponenteConContexto></MiComponenteConContexto> */}
        {/* <Ejemplo4 nombre="Juan">
          <h1>Esto no se veria si no fuera por que imprimi el props.children</h1>
        </Ejemplo4> */}
        {/* <AllCycle></AllCycle> */}
        {/* <GreetingStyled name="Juan"></GreetingStyled> */}
        {/* <Father></Father> */}

      {/* </header> */}
    </div>
  );
}

export default App;
