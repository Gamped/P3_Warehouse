import React from 'react';
import ReactTable from 'react-table';
import axios from 'axios';
import {connect} from "react-redux"

import "../../Pages.css";
import "./UserStock.css"

class UserStock extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userID: props.ID,
            quarry: "",
            products: [],
            selected: null,
            selectedId: ""
        };
    }

    componentDidMount(){
        axios.get('http://localhost:8080/api/clients/' + this.state.userID + '/products')
            .then((response) => {
                const products = this.makeRow(response);
                this.setState({ products: products });
            })
    }

    makeRow(response){
        var products = [];
        response.data.forEach((product) => {
            products.push({
                productId: product.productId,
                productName: product.productName,
                quantity: product.quantity,
                hexId: product.hexId
            })
        });
        return products;
    }

    /*
    * SOME FUNCTION TO RETRIEVE & SEND INFO FROM DB
    */

    handleQuarry = (event) => {
        this.setState({
            quarry: event.target.value,
        });
    }

    render(){
        const columns=[
            {Header: "Product", accessor: "productName"},
            {Header: "Quantity", accessor: "quantity"}
        ]
        return(
            <div className="PageStyle rounded">
             <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <a class="navbar-brand" href="#">Your Stock</a>
             </nav>
                    <div className="row">
                        <div className="col">
                        <div className="">
                            <ReactTable
                                columns={columns}
                                data={this.state.products}
                                showPagination={false} 
                                className="-striped -highlight"
                                getTrProps={(state, rowInfo) => {
                                    if (rowInfo && rowInfo.row) {
                                        return {
                                        onClick: (e) => {
                                            
                                            this.setState({selected: rowInfo.index, selectedId: rowInfo.original.hexId })
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
                                     // This will force the table body to overflow and scroll, 
                                    // since there is not enough room
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
        userId: state.loginReducer.userId
    }
}

export default connect(mapStateToProps)(UserStock)