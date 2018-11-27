import React from 'react';
import "../../Pages.css";
import "./PublisherClient.css"

export default class PublisherClient extends React.Component {
    constructor(props) {
        super(props);
        this.state = { };
    }
  
  render() {
        return(
            <div className="PageStyle">
  
                <div className="topBoxStyle topBox">
                    <h1 className="stockTxt customText_w">Your clients stock:</h1>
                </div>
  
                <div className="leftBoxStyle pickBox">
                    <h1 className="leftTxt customText_b">(List of all clients)</h1>
                </div>
  
                <div className="stockListBox 'contentBoxStyle'">
                    <table className="stockTable">
                        <tbody>
                            <tr>
                                <th>Product name</th>
                                <th>Product Number</th>
                                <th>Quantity</th>
                            </tr>
                        </tbody>
                    </table>
                </div>
  
                <div className="bottomBoxStyle bottomBox">
                    <form action="/User/Clients/Request" className="pubForm">
                        <button  className="pubButton_f btn" >Request client change</button>
                    </form>
                    <button className="pubButton btn">Export their stock</button>
                </div>
            </div>
        );
    }
}
  