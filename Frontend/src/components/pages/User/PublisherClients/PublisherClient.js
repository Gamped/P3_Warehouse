import React from 'react';
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import ReactTable from 'react-table';
import {makeClientDetails} from './../../../../handlers/dataHandlers.js';
import {getColumnsFromArray} from './../../../../handlers/columnsHandlers.js';
import {get} from './../../../../handlers/requestHandlers';

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

       this.getPublisherProducts();
    }

    getPublisherProducts() {

        get("publishers/" + this.props.userId, (data) => {
            
            const clients = makeClientDetails(data);
            this.setState({ clients: clients });
        })
           
    }
   

  render() {

        const columns = getColumnsFromArray(["Client", "Phone Number", "Email", "Address"]);  

        return(
            <div className="PageStyle rounded">
                <nav class="navbar navbar-expand-lg navbar-light bg-light">
                    <a class="navbar-brand" href="#">Clients under {this.props.nickName}</a>
                </nav>
                <div className="container row">
                    <div className="col">
                        <ReactTable
                            columns={columns}
                            data={this.state.clients}
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
        userId: state.loginReducer.userId,
        nickName: state.loginReducer.nickName
    }
}
  
export default connect(mapStateToProps)(PublisherClient)
