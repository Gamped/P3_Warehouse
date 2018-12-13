import React from 'react';
import {Link} from "react-router-dom";
import ReactTable from 'react-table';
import "./UserOrder.css";
import {connect} from "react-redux";
import {get} from "./../../../../handlers/requestHandlers";

class UserOrder extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            columns: [],
        };
    }
    
    componentDidMount(){
        this.getOrders();
    }

    getOrders = () =>{
        const {userId,userType} = this.props;
        if(userType==="CLIENT"){
            get("clients/"+userId+"/orders",(data)=>this.setDataToState(data))
        }else if(userType === "PUBLISHER"){
            get("publishers/"+userId+"/orders",(data)=>this.setDataToState(data))
        }
    }

    setDataToState = (data) =>{
        
    }

    //TODO: Fix react-table.

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