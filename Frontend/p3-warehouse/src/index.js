import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import * as serviceWorker from './serviceWorker';


// The header component
class Header extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            titleText: "P3 Warehouse",
        };
    }

    render(){
        let title = this.state.titleText;

        return(
            <div class="headerStyle"> 
                <titleText>{title}</titleText>
            </div>
        );
    }
}


// Send components to HTML
ReactDOM.render(
    <div>
        <Header />
    </div>
    , document.getElementById('root')   
);


// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
