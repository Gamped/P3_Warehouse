import React from 'react';
import {Link} from "react-router-dom";
import ReactTable from 'react-table';
import "./UserOrder.css";

class UserOrder extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            columns: [],
        };
    }

    render() {
        return(
            <div className="PageStyle">
                <div className="frameBordering">
                    <div className="UserOrderLeft">
                        <ReactTable 
                                className="productTable -striped -highlight"
                                columns={this.state.columns}
                            />
                    </div>
                    <div className="UserOrderRight">
                        <Link to="/Home" className="btn green_BTN btn-block">Create new order</Link>
                        <Link to="/Home" className="btn red_BTN btn-block">Remove order</Link>
                    </div>
                </div>
            </div>
        );
    }
}

export default UserOrder;