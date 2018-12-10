import React from 'react';
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import ReactTable from 'react-table';
import axios from 'axios';

import "../../Pages.css";

class PublisherClient extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userId: props.ID,
            clients: [],

            selected: null,
            selectedId: ""
        };
    }
   

    componentDidMount(){
        axios.get("localhost:8080/api/publishers/" + this.state.userId + "/clients/products")
            .then((response) => {
                const products = this.makeRow(response);
                this.setState({ products: products });
            })
    }

    makeRow(response){
        var products = [];
        response.data.forEach((product) => {
            products.push({
                ownerName: product.owner.name,
                productId: product.productId,
                productName: product.productName,
                quantity: product.quantity,
                hexId: product.hexId
            })
        });
        return products;
    }


    //Todo: get a pdf back when sending the userID
    //TODO: Indsæt et reactTable.
  render() {
      const columns=[
          {Header: "Owner", accessor: "ownerName"},
          {Header: "Product Name", accessor: "productName"},
          {Header: "Quantity", accessor: "productQuantity"}
      ]
        return(
            <div className="PageStyle rounded">
                <nav class="navbar navbar-expand-lg navbar-light bg-light">
                    <a class="navbar-brand" href="#">All clients' stock</a>
                </nav>
                <div className="container row">
                    <div className="col">
                        <ReactTable
                            columns={columns}
                            data={this.state.products}
                            showPagination={false} 
                            className="table -striped -highlight"
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
                    <div class="w-100"></div>
                    <div className="col md-auto">    
                        <div className="button-group my-2">
                            <button type="button" className="btn btn-info mx-2">Export their stock</button>
                             <Link to="/User/Clients/Request" type="button" className="btn btn-warning mx-2">Request client change</Link>
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
  
export default connect(mapStateToProps)(PublisherClient)