import React,{Component} from 'react';
import { Link } from "react-router-dom";
import "../../Pages.css"
import "./EditOrderAddress.css"
import { get, put } from './../../../../handlers/requestHandlers.js';
import {makeOrderAddressData, makeOrderLinesData} from './../../../../handlers/dataHandlers.js';


export default class EditOrderAddress extends Component{
    constructor(props){
        super(props);
        
        this.state = { order: {} }
    }

    componentDidMount() {

        this.getOrder();
    }

    getOrder() {
        get("employee/order/"+this.props.match.params.id, (data) => {
            const orderLines = makeOrderLinesData(data);
            const order = makeOrderAddressData(data);
            order.orderLines = orderLines;
            order.owner = data.owner;

            console.log(JSON.stringify(order));
            this.setState({order: order})
            
        });
    }

    onChange = (e) => {
        const state = this.state.order;
        state[e.target.name] = e.target.value;
        this.setState({order:state});
    }

    onSubmit = (e) => {
        e.preventDefault();

        const order = this.state.order;
        put("employee/orders/"+this.props.match.params.id, order, () => {
            if (window.confirm("Address successfully updated!")) {
                this.props.history.push("/Admin/Orders")
            }
            })
    }

    
    render() {
        return(
            <div className="PageStyle rounded">
                    <div class="btn-group-vertical my-5 mx-5">
                        <input type="text" name="company" defaultValue={this.state.order.company} className="newForm" onChange={this.onChange} placeholder="Company"/> 
                        <input type="text" name="contactPerson" defaultValue={this.state.order.contactPerson} className="newForm" onChange={this.onChange} placeholder="Contact Person"/>
                        <input type="text" name="phoneNumber" defaultValue={this.state.order.phoneNumber} className="newForm" onChange={this.onChange} placeholder="Phonenumber"/>
                        <input type="text" name="address" defaultValue={this.state.order.address} className="newForm" onChange={this.onChange} placeholder="Address"/>
                        <input type="text" name="zipCode" defaultValue={this.state.order.zipCode} className="newForm" onChange={this.onChange} placeholder="Zipcode"/>
                        <input type="text" name="city" defaultValue={this.state.order.city} className="newForm" onChange={this.onChange} placeholder="City"/>
                        <input type="text" name="country" defaultValue={this.state.order.country} className="newForm" onChange={this.onChange} placeholder="Country"/>

                        <div className="btn-group my-2">
                            <button className="col btn btn-succes mx-2" onClick={this.onSubmit}>Save Changes</button>
                            <button className="col btn btn-warning mx-2">Discard Changes</button>
                            <Link to="/Admin/Orders/Edit" className="col btn btn-info mx-2 " role=" button" >Back</Link>
                         </div>
                    </div>
                </div>
        )
    }
}