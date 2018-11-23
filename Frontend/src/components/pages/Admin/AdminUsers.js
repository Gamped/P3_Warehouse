import React from 'react';
import "../Pages.css";
import ButtonList from "../../MenuComponents/ButtonList/ButtonList"
import TopTabs from "../../MenuComponents/TopTabs/TopTabs"

export default class AdminUsers extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            publisher: [
                {name:"Black Betty INC",id: "1"},
                {name:"Toys r us" , id:"2"},
                {name:"Fightclub" , id:"3"},
                {name:"I.C.U.P." , id:"4"},
                {name:"Aalborg Zoo" , id:"5"},
                {name:"Ådal og Søn" , id:"6"},
            ],
            client:[
                {name:"We do books", id:"1"},
                {name:"Aprature science", id:"2"},
                {name:"Alef zero",id:"3"},
                {name:"The Ironhands",id:"4"},
                {name:"Dark Angels",id:"5"},
                {name:"Emperor's Children",id:"6"},
                {name:"Iron Warriors",id:"7"},
                {name:"White Scars",id:"8"},
                {name:"Space Wolves",id:"9"},
                {name:"Imperial Fists",id:"10"},
                {name:"Night Lords",id:"11"},
                {name:"Blood Angels",id:"12"},
                {name:"Iron Hands",id:"13"},
            ],
            tabs: [
                {name:"Publishers",id:"1"},
                {name:"Publisher's clients",id:"2"},
                {name:"Independent clients",id:"3"}
            ]
        };
    }
    changeList=(event)=>{
        console.log(this.event.value)

    }
    
    render(){
        let shownButtons;
        
        return(
            <div className="PageStyle">
                <div className="userPageStyle">
                    <div className="container row">
                        <div className="col sidebar border border-dark rounded bg-secondary">
                        <div className="border border-light rounded bg-info">
                            <ButtonList buttons={this.state.tabs} color="secondary" link={false} action={this.changeList}/>
                        </div>
                            <ButtonList buttons={this.state.publisher} color="dark" link={false} />
                        </div>
                        <div className="col-sm-auto">
                        
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}