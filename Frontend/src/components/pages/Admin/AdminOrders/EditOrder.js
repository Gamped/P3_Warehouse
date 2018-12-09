import React,{Component} from 'react';
import axios from 'axios';
import ReactTable from 'react-table';
import { Link } from "react-router-dom";
import "../../Pages.css";
import "./EditOrder.css"

export default class EditOrder extends Component{
    constructor(props){
        super(props);
        this.state = {
            //There's prolly a more beautiful way of setting these using arrays.
            //Please refactor them if you know about it.
            companyInfo: props,
            contactPersonInfo: props,
            addressInfo: props,
            zipCodeInfo: props,
            cityInfo: props, 
            orderLineData: [],
            productData: [],
        }
    }

    sendToPage = (address) => {
        this.props.history.push(address);
    }

    render(){
        const orderLineColumns=[ /*You might wanna do something fancy where you can edit the quantity directly in the table*/
            {Header: "Products", accessor:"product"},
            {Header: "Quantity", accessor:"quantity"}
        ];
        
        return(
            <div className="PageStyle rounded">
                <div className="row">
                    <div className="col">
                        <div className=" my-1">
                            <ReactTable 
                                columns={orderLineColumns}
                                showPagination={false}
                                className="OrderLines -striped -highlight"
                                getTrProps={(state, rowInfo) => {
                                    if (rowInfo && rowInfo.row) {
                                        return {
                                        onClick: (e) => {
                                            
                                            this.setState({selectedProduct: rowInfo.index, selectedProductId: rowInfo.original.hexId })
                                            console.log(rowInfo.original)
                                        },
                                        style: {
                                            background: rowInfo.index === this.state.selected ? '#00afec' : 'white',
                                            color: rowInfo.index === this.state.selected ? 'white' : 'black'
                                        }
                                        }
                                    }else{
                                        return {}
                                    }
                                }}
                                defaultPageSize={25}
                                style={{
                                    height: "400px"                                      
                                 }}
                            />
                        </div>
                    </div>   
                    <div class="w-100"></div>
                    <div className="row mx-5"> 
                            <ul class="nav ">
                                <div className="justify-content-center btn-group-h my-1"> 
                                    <button className=" btn btn-warning btn-lg mx-2" onClick={()=>this.sendToPage("/Admin/Orders/Edit/OrderAddress")}>Edit Address</button>
                                    <button className=" btn btn-warning btn-lg mx-2" onClick={()=>this.sendToPage("/Admin/Orders/Edit/OrderContent")}>Edit Contents</button>
                                    <Link to="/Admin/Orders" className=" btn btn-info btn-lg mx-2 " role=" button" >Back</Link>
                                </div>
                            </ul>
                    </div>
                </div>
            </div>
        )
    }
}