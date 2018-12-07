import React from 'react';
import "../../Pages.css";
import "./PublisherClient.css";
import { Link } from "react-router-dom";
import { connect } from "react-redux";

class PublisherClient extends React.Component {
    constructor(props) {
        super(props);
        this.state = { };
    }

    //Todo: get a pdf back when sending the userID
    //TODO: Indsæt et reactTable.
  render() {
        return(
            <div className="PageStyle rounded">
                <nav className="navbar navbar-secondary bg-secondary"><h3>Clients stock</h3></nav>
            
  
                <div className="stockListBox 'contentBoxStyle'">
                    <table className="table">
                        <tbody>
                            <tr>
                                <th>Product name</th>
                                <th>Product Number</th>
                                <th>Quantity</th>
                            </tr>
                        </tbody>
                    </table>
                </div>
  
                
                <div className="container row">
                    <div className="col">    
                        <Link to="/User/Clients/Request" className="btn btn-block btn-warning">Request client change</Link>
                    </div>
                    <div className="col">
                        <button className="btn-info btn btn-block">Export their stock</button>
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