import React from 'react';
import "../Pages.css";
import ButtonList from "../../MenuComponents/ButtonList/ButtonList"
/*const AdminUsers = (props) => {


    return(
        <div className="PageStyle">
            <h1 className="customText_b">You are on Admin users page</h1>
        </div>

    );
}
 
export default AdminUsers;*/




// REMOVED, but left here if needed once this page will be built

export default class AdminUsers extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            users: [
                {name:"Black Betty INC",id: "1"},
                {name:"Toys r us" , id:"2"},
                {name:"Fightclub" , id:"3"},
                {name:"I.C.U.P." , id:"4"},
                {name:"Aalborg Zoo" , id:"5"},
                {name:"Ådal og Søn" , id:"6"},
            ]
        };
    }
    
    render(){
        return(
            <div className="PageStyle">
                <div className="userPageStyle">
                    <div className="container row">
                        
                        <div className="col sidebar border border-dark rounded bg-secondary">
                            <ButtonList buttons={this.state.users} link={false} />
                        </div>
                        <div className="col-sm-auto">
                        
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}