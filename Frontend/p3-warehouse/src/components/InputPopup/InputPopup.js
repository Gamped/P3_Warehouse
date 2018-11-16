import React from 'react';
import './InputPopup.css'

export default class inputPopup extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            inputs: [],
            inputFields: props.fields,
            popupTitle: props.title,
            backtext: "Back",
            fufillText: "Send"
        };
    }

    handleInputChange = (index) => {
        //this.setState({})
    }

    render(){
        var i = 0;
        let usedFields = this.state.inputFields;
        var list;
        if (usedFields != null){
        list = usedFields.map(function(element){
            return(
            <input 
            type="text" 
            className="InputField" 
            //onChange={this.}
            placeholder={element}/>)
            i++;
            }
        )
        } else {list = "";}

        return(
        <div className="inputPopup">
            <customText_b className="PopupText">{this.state.popupTitle}</customText_b>
            <form>
                {list}
            </form>
            <button className="PopupButton">{this.state.backtext}</button>
            <button className="PopupButton">{this.state.fufillText}</button>
        </div>);
    }
}

