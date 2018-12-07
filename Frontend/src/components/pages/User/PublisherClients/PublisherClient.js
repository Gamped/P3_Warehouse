import React from 'react';
import "../../Pages.css";
import "./PublisherClient.css";
import {Link} from "react-router-dom";

export default class PublisherClient extends React.Component {
    constructor(props) {
        super(props);
        this.state = { };
    }
  
  render() {
        return(
            <div className="PageStyle rounded">
  
                <div className="topBoxStyle topBox">
                    <h2 className="stockTxt text-center text-white">Your clients stock:</h2>
                </div>
  
                <div className="leftBoxStyle pickBox">
                    <h1 className="leftTxt customText_b">(List of all clients)</h1>
                </div>
  
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
  