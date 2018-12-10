import React from 'react';
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import ReactTable from 'react-table';
import {makeProductsRowsWithOwner} from './../../../../handlers/dataHandlers.js';
import {getColumnsFromArray} from './../../../../handlers/columnsHandlers.js';

import "../../Pages.css";
import "./PublisherClient.css";

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

       this.getPublisherProducts();
    }

    getPublisherProducts() {

        get("publisher/" + this.state.userId + "/clients/products", () => {
        
            const products = makeProductsRowsWithOwner(data);
            this.setState({ products: products });
        })
           
    }
   

  render() {
    //TODO: get a pdf back when sending the userID
    
        const columns = getColumnsFromArray(["Owner Name", "Product Name", "Quantity"]);  
   
        return(
            <div className="PageStyle rounded">
                <nav className="navbar navbar-secondary bg-secondary"><h3> All clients' stock</h3></nav>
                    <div className="mainContent container col">
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
                        />

                        <div className="buttons container row">
                            <div className="col">    
                                <Link to="/User/Clients/Request" className="btn btn-block btn-warning">Request client change</Link>
                            </div>
                            <div className="col">
                                <button className="btn-info btn btn-block">Export their stock</button>
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
