import React from 'react';
import ReactTable from 'react-table';
import {connect} from "react-redux"

import "../../Pages.css";
import "./UserStock.css"
import {get} from './../../../../handlers/requestHandlers.js';
import {makeProductsData, makeCustomerProductsData} from './../../../../handlers/dataHandlers.js';
import { getColumnsFromArray } from '../../../../handlers/columnsHandlers';

class UserStock extends React.Component {
    
    constructor(props) {
        super(props);
        this.state = {
            quarry: "",
            products: [],
            selected: null,
            selectedId: ""
        };
    }

    componentDidMount(){
       
        this.getStock();           
    }

    getStock() {
        
        const userType = this.props.userType.toLowerCase();
        const id = this.props.userId;
        
        get(userType + 's/' + id + '/products', (data) => {
            let products = [];
            
            userType === 'publisher' ? products = makeCustomerProductsData(data) : products = makeProductsData(data);

            this.setState({ products: products });
    });
    }

    render(){

        const columns = getColumnsFromArray(["Product Id", "Product Name", "Quantity", "Owner Name"]);
        
        return(
            <div className="PageStyle rounded">
             <nav className="navbar navbar-expand-lg navbar-light bg-light">
                <a className="navbar-brand" href="#">Your Stock</a>
             </nav>
                    <div className="row">
                        <div className="col">
                        <div className="">
                            <ReactTable
                                columns={columns}
                                data={this.state.products}
                                showPagination={false} 
                                className="-striped -highlight"
                                defaultPageSize={25}
                                    style={{
                                        height: "400px"                                      
                                     }}
                            
                            />
                        </div>
                    </div>
                </div>
        </div>
        );
    }
}

const mapStateToProps = (state) =>{
    return{
        userId: state.loginReducer.userId,
        userType: state.loginReducer.userType
    }
}

export default connect(mapStateToProps)(UserStock)