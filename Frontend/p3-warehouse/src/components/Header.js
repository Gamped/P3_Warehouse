import React from 'react';

// The header component
export default class Header extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            titleText: "P3 Warehouse",
        };
    }

    render(){
        let title = this.state.titleText;

        return(
            <div className="headerStyle">
                <titleText>{title}</titleText>
            </div>
        );
    }
}