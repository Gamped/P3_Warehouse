import React from 'react';
import Table from "../../MenuComponents/Table/Table";
import "../Pages.css";
import "./AdminOrders.css";
import ButtonList from "../../MenuComponents/ButtonList/ButtonList";

//TODO: Implement data fetching from backend/database
class AdminOrders extends React.Component {
    constructor(props){
        super(props);
        this.state={
            columns: [
                {Header: "Package Name", accessor: "name"},
                {Header: "Package ID", accessor: "id"},
                {Header: "Unit Amount", accessor: "amount"},
                {Header: "Unit Type", accessor: "type"},
                {Header: "Packaged?", accessor: "packaged"}
            ],
            tabs: [
                {name:"In Progress Orders",id:0},
                {name:"Finished Orders",id:1},
                {name:"Pending Orders",id:2}
            ],
            finishedOrders: [
                {name: "DragonDildos", id:"1"},
                {name: "Ground Beef", id:"2"},
                {name: "Iron Halo", id:"3"},
                {name: "Plasma Blaster", id:"4"},
                {name: "Fist of the dragon", id:"5"},
                {name: "Russian Molotov Cocktail", id:"6"},
            ],
            inProgressOrders: [
                {name: "Oatmeal", id:"7"},
                {name: "Cactus and lube", id:"8"},
                {name: "Firebowl", id:"9"},
            ],
            pendingOrders: [
                {name: "The golden Bra Of Ming", id:"10"},
                {name: "Silver girls", id:"11"},
                {name: "Fidget spinners", id:"12"},
            ],
            listShown: 0,

        }
    }
    
    changeList=(input)=>{
        this.setState({listShown: input});
    }

    buttonListShown=()=>{
        const x=this.state.listShown;
        switch (x){
            case 0:
                return(<ButtonList buttons={this.state.inProgressOrders} color="dark" link={false} action={this.changeUser} />)
            case 1:
                return(<ButtonList buttons={this.state.finishedOrders} color="dark" link={false} action={this.changeUser} />)
            case 2: 
                return(<ButtonList buttons={this.state.pendingOrders} color="dark" link={false} action={this.changeUser} />)
            default:
                return(<h2 className="text-color-red">ERROR</h2>)
        }
    }

    render(){
        const tableHeight = window.innerHeight * 0.8;
        return(
            <div className="PageStyle">
                <div className="container row">
                    <div className="col sidebar fullbar border border-dark rounded bg-secondary">
                        <div className="border border-light rounded bg-info">
                            <ButtonList buttons={this.state.tabs} color="secondary" link={false} action={this.changeList}/>
                        </div>
                        {this.buttonListShown()}
                    </div>
                    <div className="col">
                        <div className="AdminTable"><Table columns={this.state.columns} height={tableHeight}/></div>
                    </div>
                </div>
            </div>
        )
    }
}

export default AdminOrders;
