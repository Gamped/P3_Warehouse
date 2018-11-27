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
            data:[
                {name:"Item 1",id:"1", amount:"1000", type: "Book",packaged:"false"},
                {name:"Item 2",id:"2", amount:"50", type: "Flyer",packaged:"false"},
                {name:"Item 3",id:"3",amount:"2" ,type:"Boxes",packaged:"false"},
                {name:"Item 4",id:"4",amount:"5000" ,type:"Drugs",packaged:"false"},
                {name:"Item 5",id:"5",amount:"4587545753215" ,type:"Book",packaged:"true"},
            ],
            columns: [
                {text: "Package Name", dataField: "name", sort:true},
                {text: "Package ID", dataField: "id", sort:true},
                {text: "Unit Amount", dataField: "amount", sort:true},
                {text: "Unit Type", dataField: "type", sort:true},
                {text: "Packaged?", dataField: "packaged", sort:true}
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
                        <div className="AdminTable">
                            <Table columns={this.state.columns} data={this.state.data}/>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default AdminOrders;
