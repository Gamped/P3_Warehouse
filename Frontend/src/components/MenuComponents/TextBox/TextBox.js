import React from "react";
//gets and id and shows information in a box.

    //display name in a h2.

    //if client display company name and search for an adresse

    //else if publisher still get the above, but also get a list of clients

    // if order display Products, client, reciever adress, creation date, Title, 


class TextBox extends React.Component{

    constructor(props){
        super(props);
        this.state = {}
    }

    displayItem(){
        const type = this.props.type;
        if (type === "user"){
            return(
                <div>
                    <div >
                        <h2>Here goes the name</h2>
                    </div>
                    <div className="text-muted">
                        <h3>Here goes the company name</h3>
                    </div>
                    <div> 
                        <p>If adress, then it goes here.</p>
                        <p>If clients, then it goes here. This should probably be a list of buttons.</p>
                    </div>
                </div>
            )
        }else if (type === "order"){
            return(
                <div>
                    <h2>Here goes the title</h2>
                    <h4>Owner goes here</h4>
                    <h4>Recipient goes here</h4>
                </div>
            )
        }else{
            return(
                <div>
                    <h1>ERROR</h1>
                </div>
            )
        }
    }
    render(){
        return(
            <div>
                {this.displayItem()}
            </div>
        )
    }
}

export default TextBox;