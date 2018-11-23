import React,{Component} from 'react';
import "../../Pages.css";
import "./AdminStock.css";
import axios from 'axios';


export default class NewWare extends Component {
    constructor(props) {
        super(props);
        this.state = {
            productName: "",
            quantity: 0,
            owner: "",
        };


    }

    onSubmit = (e) => {
        e.preventDefault();

        const {id, productName, quantity, owner} = this.state;
        axios.post('http://localhost:8080/api/products', {productName, quantity, owner}).then((result)=> {
            this.props.history.push("/");
        });
    }

    onChange = (e) => {
        const state = this.state;
        state[e.target.name] = e.target.value;
        this.setState(state);

    }


    render(){
        return(
        <div className="PageStyle">
            <h1 className="title customText_b_big">Add new product</h1>
                <form>
                    <input
                        type="text"
                        className="newForm"
                        onChange={this.onChange}
                        placeholder="Product productName"/>
                    <input
                        type="text"
                        className="newForm"
                        onChange={this.onChange}
                        placeholder="Quantity"/>
                    <input
                        type="text"
                        className="newForm"
                        onChange={this.onChange}
                        placeholder="Owner"/>
                </form>
                <form action="/Admin/Stock" className="newForm stockForm">
                    <button className="newButton stockButton_f btn">Back</button>
                </form>
                <form action="/Admin/Stock" className="newForm stockForm">
                    <button className="newButton stockButton_f btn" onClick={this.onSubmit}>Create product</button>
                </form>
        </div>);
    }
}
