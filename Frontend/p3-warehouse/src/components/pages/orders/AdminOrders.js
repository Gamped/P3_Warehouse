import React from 'react';
import Menu from "../../MenuComponents/Menu";
import Header from "../../Header";
import AdminTable from "./AdminTable";

class AdminOrders extends React.Component {
    constructor(props) {
        super(props);
        this.state = { 
            menuButtons : [
                {name: "Home",location: "../../AdminIndex", id:"1"},
                {name: "Orders",location:"./AdminOrders", id:"2"},
                {name: "Users",location:"../../../pages/users/Users"},
                {name: "Stock",location:"../../pages/stock/Stock",id:"4"},
                {name: "Profile",location:"../../pages/profile/Profile",id:"5"}
            ] 
        }
    }
    render() { 
        return(
            <div className="userPageWrapper">
                <Header title="Warehouse - Orders"/>
                <div className="menuStyle">
                    <Menu buttons={this.state.menuButtons} current={"./AdminOrders"}/>
                </div>
                <div className = "AdminOrder border border-primary rounded">
                <AdminTable />
                </div>
            </div>

        );
    }
}
 
export default AdminOrders;

