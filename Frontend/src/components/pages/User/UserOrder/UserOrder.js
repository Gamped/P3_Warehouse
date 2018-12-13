import React from 'react';
import {Link} from "react-router-dom";
import ReactTable from 'react-table';
import "./UserOrder.css";
import {connect} from "react-redux";
import {get} from "./../../../../handlers/requestHandlers.js";
import {makeClientOrdersData, makePublisherAndClientOrdersData} from '../../../../handlers/dataHandlers.js';
import {getColumnsFromArray} from './../../../../handlers/columnsHandlers.js';

class UserOrder extends React.Component {
    
    constructor(props) {
        super(props);
        this.state = { orders: [] };
    
    }
    
    componentDidMount(){
        this.getOrders();
    }

    getOrders = () =>{

        const {userId,userType} = this.props;
        get(userType.toLowerCase() + "s/" +userId + "/orders",(data)=>this.setDataToState(data, userType))
       
    }

    setDataToState = (data, userType) => {
        console.log(data);

        let orders = [];
      //  userType.toLowerCase() === "client" ? orders = makeClientOrdersData(data) : orders = makePublisherAndClientOrdersData(data);
        this.setState({orders: orders});
    }

    render() {
        
        const columns = getColumnsFromArray(["Order Id", "Placed By", "Date"]);
        const orders = this.state.orders;

        return(
            <div className="PageStyle">
                <div className="frameBordering">
                    <div className="UserOrderLeft">
                        <ReactTable 
                                data={orders}
                                className="productTable -striped -highlight"
                                columns={columns}
                                showPagination={false} 
                                className=" -striped -highlight darkenReactTable"

                            />
                    </div>
                    <div className="UserOrderRight">
                        <Link to="/User/Order/Select" className="btn green_BTN btn-block">Create new order</Link>
                        <Link to="/Home" className="btn red_BTN btn-block">Remove order</Link>
                    </div>
                </div>
            </div>
        );
    }
}

const mapStateToProps = (state) =>{
    return {
        userId: state.loginReducer.userId,
        userType: state.loginReducer.userType
    }
}

export default connect(mapStateToProps)(UserOrder);