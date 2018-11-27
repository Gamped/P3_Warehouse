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
            <div className="PageStyle">
                <div className="topBox topBoxStyle">
                    <h1 className="topText customText_w"> Stock:</h1>
                </div>

                <div className="deciderBox leftBoxStyle">
                    <input 
                        type="text" 
                        className="serachBar" 
                        onChange={this.handleQuarry}
                        placeholder="Search for product(s)"/>
                    <button className="exportButton stockButton_f btn">Export current list</button>
                </div>

                <div className="listBox contentBoxStyle">

                </div>
            </div>
        );
    }
}