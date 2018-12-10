import React,{Component} from 'react';
import axios from 'axios';
import "../../Pages.css";
import "./AdminStock.css";
import { Link } from "react-router-dom";
import {makeOwnersData} from './../../../../handlers/dataHandlers.js';
import {get, post} from './../../../../handlers/requestHandlers.js'
//import {successAlert} from '../../../../handlers/alerts.js'

export default class NewWare extends Component {

    constructor(props) {
        
        super(props);
        this.state = {
            product: {},
            owners: [],
            selectedOwnerHexId: "DEFAULT",
            selectedOwnerUserType: "DEFAULT"
        };

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.getCustomers = this.getCustomers.bind(this);
    }

    componentDidMount() {
          
        this.getCustomers();
    }

    
    getCustomers() {

       get('employee/publishers', (data) => {
        let owners = makeOwnersData(data);
        this.setState({owners: owners})
       }) 
    }

    onChange = (e) => {
        const state = this.state.product;
        state[e.target.name] = e.target.value;
        this.setState({product:state});
    }

    onSubmit = (e) => {
        e.preventDefault();
        const {productName, productId, quantity} = this.state.product;

           post('employee/products/'
                + "assignTo=" + this.state.selectedOwnerHexId + "/withUserType=" + 
                this.state.selectedOwnerUserType,
                {productName, productId, quantity}, () => {
                   // let alert = successAlert("Product Added!");
                //    return alert;
                }
            )
    }
    
    getOwnerListItems(owner, i) {
         return (
         <option 
         key={i}
         onSelect={()=> {
            this.setState({selectedOwnerHexId: owner.hexId})
         }} value={owner.hexId}>
         {owner.userType} - {owner.nickName}
         </option>
         )        
    }

    render() {
     
        const currentProduct = this.state.product;

        return (
            <div className="PageStyle rounded">
                <h1 className=" text-center">Add new product</h1>
                <form>
                    <input
                        type="text "
                        name="productName"
                        className="my-2 form-control  "
                        defaultValue={currentProduct.productName}
                        onChange={this.onChangeProductName}
                        placeholder="Product Name"/>
                    <input
                        type="text"
                        name="productId"
                        className="my-2 form-control "
                        defaultValue={currentProduct.productId}
                        onChange={this.onChangeProductId}
                        placeholder="Product Id"/>
                    <input
                        type="text"
                        name="quantity"
                        className="my-2 form-control"
                        defaultValue={currentProduct.quantity}
                        onChange={this.onChangeQuantity}
                        placeholder="Quantity"/>
                    
                    <select className="custom-select my-2" >
                    <option selected>Choose owner</option>
                    {this.state.owners.map(this.getOwnerListItems)}
                    </select>   
                </form>


                <div className="" action="/Admin/Stock">
                    <button className="btn-success btn-lg btn-block btn my-2" onClick={this.onSubmit}>Create product</button>
                </div>
                
                <Link to="/Admin/Stock" className="btn-info btn-lg btn-block btn my-2">Back</Link>
               
            </div>
        )     
    }
}
