import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import { BrowserRouter, Switch, Route, Redirect } from 'react-router-dom'
import MainLayout from "./layouts/MainLayout";
import Main from './views/Main';
import ConvertVideo from './views/ConvertVideo';
import Header from "./layouts/Header";

function App() {

  return (
        <BrowserRouter>
            <Header />
            <div>
                <Switch>
                    <Route exact path="/" component={Main} />
                    <Route exact path="/convertvideo" component={ConvertVideo} />
                    <Redirect to="/" />
                </Switch>
            </div>
        </BrowserRouter>
  );
}

export default App;
