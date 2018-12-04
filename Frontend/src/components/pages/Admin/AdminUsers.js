import React from 'react';
import "../Pages.css";
import ButtonList from "../../MenuComponents/ButtonList/ButtonList";
import "./AdminUsers.css";
import TextBox from "../../MenuComponents/TextBox/TextBox";
import ReactTable from "react-table";

export default class AdminUsers extends React.Component {
    constructor(props) {
        super(props);
        const user = this.state.user;

        const userColumns = [
            {Header: "username", accessor: "usertype"},
        ];
  
        this.state = {
            publisher: [
                {name:"Black Betty INC",id: "1"},
                {name:"Toys r us" , id:"2"},
                {name:"Fightclub" , id:"3"},
                {name:"I.C.U.P." , id:"4"},
                {name:"Aalborg Zoo" , id:"5"},
                {name:"Ådal og Søn" , id:"6"},
            ],
            pClient:[
                {name:"We do books", id:"7"},
                {name:"Aprature science", id:"8"},
                {name:"Alef zero",id:"9"},
                {name:"The Ironhands",id:"10"},
                {name:"Dark Angels",id:"11"},
                {name:"Emperor's Children",id:"12"},
                {name:"Iron Warriors",id:"13"},
                {name:"White Scars",id:"14"},
                {name:"Space Wolves",id:"15"},
                {name:"Imperial Fists",id:"16"},
                {name:"Night Lords",id:"17"},
                {name:"Blood Angels",id:"18"},
                {name:"Iron Hands",id:"19"},
            ],
            iClient:[
                {name:"Duke Helbrecht",id:"20"},
                {name:"Triumph",id:"21"},
                {name:"His Will",id:"22"},
                {name:"Godefroy Magnificat",id:"23"},
                {name:"Divine Right",id:"24"},
                {name:"Dominus Astra",id:"25"},
                {name:"Indomitable Wrath",id:"26"},
                {name:"Legatus Stygies",id:"27"},
                {name:"Lord of Light",id:"28"},
                {name:"	Corax",id:"29"},
            ],
            tabs: [
                {name:"Publishers",id:0},
                {name:"Publisher's clients",id:1},
                {name:"Independent clients",id:2}
            ],
            listShown: 0,
            userShown: 0,
        };
    }
    changeList=(input)=>{
        this.setState({listShown: input});
    }

    changeUser=(input)=>{
        this.setState({userShown: input});
        console.log(input)
    }

    buttonListShown=()=>{
        const x=this.state.listShown;
        switch(x){
            case 0:
                return(<ButtonList buttons={this.state.publisher} color="dark" link={false} action={this.changeUser} />)
            case 1:
                return(<ButtonList buttons={this.state.pClient} color="dark" link={false} action={this.changeUser} />)
            case 2:
                return(<ButtonList buttons={this.state.iClient} color="dark" link={false} action={this.changeUser} />)
            default:
                return(<h2 className="color-red">ERROR</h2>)
        }
    }

    displayUser=(id)=>{
        return null
        // todo. Burde nok gette informationen fra backenden, da vi ellers kan nøjes med name og id.
    }
  

    render(){
        return(
            <div className="PageStyle rounded">
                <div className="userPageStyle rounded">
                    <div className="SideBar col sidebar border border-dark rounded bg-secondary"></div>
                        <div className="container row"> 
                        <div className="Table">
                            <ReactTable 
                        showPagination={false}
                        className = "-striped -highlight"
                        /> 
                        </div>
                        </div>
                      
                            <div className="col-sm text-center">
                             <TextBox type="user" id={this.state.userShown}/>
                                <div className="container row">
                                    <div className="col my-2">
                                       <button type="button" className="btn btn-danger">Delete this user</button>
                                       <button type="button" className="btn btn-warning">Edit this user</button>
                                    </div>
                                </div>
                            </div>
                    </div>
                </div>
        );
    }
}
