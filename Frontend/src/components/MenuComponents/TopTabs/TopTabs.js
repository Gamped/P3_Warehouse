import React from 'react';
import './TopTabs.css'

export default class TopTabs extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            inputs: [],
            tabNames: props.titles,
            onAction: props.onAction,
            default: "",
        };
    }

    handleInputChange = (index) => {
        //this.setState({})
    }

    render(){
        var i = -1;
        let tabTitles = this.state.tabNames;
        let action = this.state.onAction;
        var tabs;
        
        if (tabTitles != null && action != null){
            tabs = tabTitles.map(function(element){
                i++;
                return(
                    <form action={action[i]} className="buttonTab">
                        <button>{element}</button>
                    </form>)
                }
            )
        } else {tabs = "";}

        return(
        <div className="topTabs">
            {tabs}
        </div>);
    }
}
