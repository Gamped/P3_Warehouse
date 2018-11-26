import React from "react";
import Axios from "axios";
//gets and id and shows information in a box.

    //display name in a h2.

    //if client display company name and search for an adresse

    //else if publisher still get the above, but also get a list of clients

    // if order display Products, client, reciever adress, creation date, Title, 


const TextBox = (props) =>{
    const {id, type} = props;

    if (type == "user"){
        return(
            <div>
                <div >
                    <h2>Here goes the name</h2>
                </div>
                <div class="text-muted">
                    <h3>Here goes the company name</h3>
                </div>
                <div> 
                    <p>If adress, then it goes here.</p>
                    <p>If clients, then it goes here. This should probably be a list of buttons.</p>
                </div>
            </div>
        )
    }else if (type == "order"){
       return(
            <div>
                <h2>Here goes the name</h2>


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

export default TextBox;