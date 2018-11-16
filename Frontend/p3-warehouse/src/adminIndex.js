import React from 'react';
import ReactDOM from 'react-dom';
import './adminIndex.css';
import './style.css';

// The Menu component
export default class Menu extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            homeText: "Home",
            ordersText: "Orders",
            clientsText: "Clients",
            stockText: "Stock",
        };
    }

    render(){
        let home = this.state.homeText;
        let orders = this.state.ordersText;
        let clients = this.state.clientsText;
        let stock = this.state.stockText;

        return(
            <div class="menuStyle">
                <button>{home}</button>
                <button>{orders}</button>
                <button>{clients}</button>
                <button>{stock}</button>
            </div>
        );
    }
}
