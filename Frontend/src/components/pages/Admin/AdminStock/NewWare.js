import React,{Component} from 'react';
import "../../Pages.css";
import "./AdminStock.css";
import { Link } from "react-router-dom";
import {makeCustomerData} from './../../../../handlers/dataHandlers.js';
import {get, post} from './../../../../handlers/requestHandlers.js';
import Dropdown from "../../../MenuComponents/Dropdown/Dropdown";
import {newProductIsValid} from './../../../../handlers/fieldsValidator.js';

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
    }

    onSubmit = (e) => {
        e.preventDefault();

        if (newProductIsValid(this.state.product)) {     
            const {productName, productId, quantity} = this.state.product;
            console.log("OWNERID: ",this.state.selectedOwnerHexId);
            console.log("USERTYPE: ",this.state.selectedOwnerUserType);
            post("employee/products/assignTo=" + this.state.selectedOwnerHexId 
                + "/withUserType=" + this.state.selectedOwnerUserType,
                {productName, productId, quantity}, () => {
                    
                   this.props.history.push("/Admin/Stock");
                });
        }
    }
    
    setSelected = (e) => {

        this.setState({
            selectedOwnerHexId:e.target.value,
            selectedOwnerUserType:this.state.owners.find(x=>x.hexId===e.target.value).userType.toUpperCase()
        })
    }

    render() {

        const currentProduct = this.state.product;

        return (
            <div className="PageStyle adminReduceFontSize customText_b">
                <div className="frameBordering">
                    <h1 className=" text-center">Add new product</h1>
                    <form>
                        <input
                            type="text"
                            name="productName"
                            className="my-2 form-control  "
                            defaultValue={currentProduct.productName}
                            onChange={this.onChange}
                            placeholder="Product Name" required/>
                        <input
                            type="number"
                            name="productId"
                            className="my-2 form-control "
                            defaultValue={currentProduct.productId}
                            onChange={this.onChange}
                            placeholder="Product Id" required/>
                        <input
                            type="number"
                            name="quantity"
                            className="my-2 form-control"
                            defaultValue={currentProduct.quantity}
                            onChange={this.onChange}
                            placeholder="Quantity" required/>
                        <Dropdown className="dropdownSmallSizeForNew" actors={this.state.owners} action={this.setSelected}/>

                        <div className="" action="/Admin/Stock">
                            <button className="green_BTN btn-lg btn-block btn my-2" type="submit" onClick={(hexID,userType)=>this.onSubmit(hexID,userType)}>Create product</button>
                        </div>
                    </form>
                    <Link to="/Admin/Stock" className="std_BTN btn-lg btn-block btn my-2">Back</Link>
                </div>
            </div>
        )     
    }
}

export default NewWare;
