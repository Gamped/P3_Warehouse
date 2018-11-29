import React from 'react';
import "../../Pages.css";
import "./UserStock.css"

export default class UserStock extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userID: props.ID,
            quarry: "",
        };
    }


    /*
    * SOME FUNCTION TO RETRIEVE & SEND INFO FROM DB
    */

    handleQuarry = (event) => {
        this.setState({
            quarry: event.target.value,
        });
    }

    render(){
        return(
            <div className="PageStyle rounded">
                <div className="topBox topBoxStyle">
                    <h2 className="topText text-center text-white"> Stock:</h2>
                </div>

                <div className="stockDeciderBox bottomBoxStyle">
                    <input 
                        type="text" 
                        className="serachBar" 
                        onChange={this.handleQuarry}
                        placeholder="Search for product(s)"/>
                    <button className="stockExportButton stockButton_f btn">Export current list</button>
                </div>

                <div className="listBox contentBoxStyle">

                </div>
            </div>
        );
    }
}