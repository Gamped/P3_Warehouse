import React from 'react'
import AdminTable from "./AdminTable"

class AdminOrder extends React.Component {
    constructor(props) {
        super(props);
        this.state = {  }
    }
    render() { 
        return ( 
            <div className = "AdminOrder">
                <AdminTable />
            </div>
         )
    }
}
 
export default AdminOrder;

