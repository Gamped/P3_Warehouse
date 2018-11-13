import React from 'react'
import AdminTable from "./AdminTable"

class AdminOrders extends React.Component {
    constructor(props) {
        super(props);
        this.state = {  }
    }
    render() { 
        return ( 
            <div className = "AdminOrders border border-primary rounded">
                <AdminTable />
                
            </div>
         )
    }
}
 
export default AdminOrders;

