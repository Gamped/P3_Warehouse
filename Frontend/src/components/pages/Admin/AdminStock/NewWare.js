import React,{Component} from 'react';
import "../../Pages.css";
import "./AdminStock.css";
import { Link } from "react-router-dom";
import {makeCustomerData} from './../../../../handlers/dataHandlers.js';
import {get, post} from './../../../../handlers/requestHandlers.js';
import Dropdown from "../../../MenuComponents/Dropdown/Dropdown";

class NewWare extends Component {

    constructor(props) {
        
        super(props);
        this.state = {
            currentProduct: this.props.product,
            product: {},
            owners: [],
            selectedOwnerHexId: "DEFAULT",
            selectedOwnerUserType: "DEFAULT"
        };

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentDidMount() {
        this.getClients();
        this.getPublishers();
    }

    getClients() {
        get('employee/clients', (data) => {
            const clients = makeCustomerData(data);
            this.concatinateWithNewData(clients);
        });
    }

    getPublishers() {
        get('employee/publishers', (data) => {
             const publishers = makeCustomerData(data);
             this.concatinateWithNewData(publishers);
        });
     }
     
     concatinateWithNewData(newData) {
    
        const ownersCopy = this.state.owners;
        let concatinatedData = [...ownersCopy,...newData];
        this.setState({ owners: concatinatedData });
    }
    onChange = (e) => {
        this.setState({product:{...this.state.product,[e.target.name]:e.target.value}})
        console.log(this.state)
    }

    onSubmit = (e) => {
        e.preventDefault();
        const {productName, productId, quantity} = this.state.product;

           post("employee/products/assignTo=" + this.state.selectedOwnerHexId 
                + "/withUserType=" + this.state.selectedOwnerUserType,
                {productName, productId, quantity}, () => {
                   this.props.history.push("/Admin/Stock")
                }
            )
    }
    
    setSelected = (e) =>{
        this.setState({
            selectedOwnerHexId:e.target.value,
            selectedOwnerUserType:this.state.owners.find(x=>x.hexId===e.target.value).userType.toUpperCase()
        })
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
                        onChange={this.onChange}
                        placeholder="Product Name"/>
                    <input
                        type="text"
                        name="productId"
                        className="my-2 form-control "
                        defaultValue={currentProduct.productId}
                        onChange={this.onChange}
                        placeholder="Product Id"/>
                    <input
                        type="text"
                        name="quantity"
                        className="my-2 form-control"
                        defaultValue={currentProduct.quantity}
                        onChange={this.onChange}
                        placeholder="Quantity"/>
                    <Dropdown owners={this.state.owners} action={this.setSelected}/>
                     
                </form>


                <div className="" action="/Admin/Stock">
                    <button className="btn-success btn-lg btn-block btn my-2" onClick={(hexID,userType)=>this.onSubmit(hexID,userType)}>Create product</button>
                </div>
                
                <Link to="/Admin/Stock" className="btn-info btn-lg btn-block btn my-2">Back</Link>
               
            </div>
        )     
    }
}



export default NewWare;