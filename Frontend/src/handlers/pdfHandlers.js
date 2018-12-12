
export function packListPDF () {
    const pdfConverter = require('jspdf');
    const doc = new pdfConverter();

    /* TODO: Find ud af hvad der skal skrives ind i pdfen.*/
    let pdfXPlace = 20;
    let pdfYPlace = 50;
    

    doc.setFontSize(22);
    doc.text(pdfXPlace,pdfYPlace,"Packlist:");
    pdfYPlace +=5;
    doc.line(pdfXPlace,pdfYPlace,175,pdfYPlace);
    doc.setFontSize(15);
    pdfYPlace +=8;
    doc.text(pdfXPlace,pdfYPlace, "Order nummer: "+ "[Insert order number here]" )
    pdfYPlace +=8;
    doc.text(pdfXPlace,pdfYPlace, "Customer: "+ "[Insert Customer here]" )
    pdfYPlace +=8;
    doc.text(pdfXPlace,pdfYPlace,"Order date: "+"[Insert Date here]")
    doc.setFontSize(10);
    pdfYPlace +=10

    doc.setFontStyle("bold")
    doc.text(pdfXPlace,pdfYPlace,"Item ID")
    doc.text(pdfXPlace+50,pdfYPlace,"Item Name")
    doc.text(pdfXPlace+100,pdfYPlace,"Quantity")
    doc.text(pdfXPlace+140,pdfYPlace,"Packed?")
    pdfYPlace +=2
    doc.line(pdfXPlace,pdfYPlace,175,pdfYPlace);

    /*let counter = 0;
    for (const key in elements){
        
        doc.text("Name: "+elements[key].productName,pdfXPlace,pdfYPlace);
        doc.text("Quantity: " + elements[key].quantity,pdfXPlace+120,pdfYPlace);
        doc.line(20,pdfYPlace+5,175,pdfYPlace+5);
        pdfYPlace += 17;
        counter += 1;
        if(counter%25===0){
            doc.addPage()
        }
    }*/

    doc.save("PackList.pdf")

}

  export function  orderNotePDF() {

    //TODO: DELETE ORDER TOO
    const pdfConverter = require('jspdf');
    const doc = new pdfConverter();

    /* TODO: Find ud af hvad der skal skrives ind i pdfen.*/
    let pdfXPlace = 20;
    let pdfYPlace = 50;
    
    doc.line(pdfXPlace,pdfYPlace,175,pdfYPlace);

    pdfYPlace += 7
    doc.setFontSize(12)
    doc.text(pdfXPlace,pdfYPlace,"Leveres til:")
    doc.setFontSize(16)
    doc.text(pdfXPlace+90, pdfYPlace,"Følgeseddel: "+"[INSERT NUMBER]")
    
    pdfYPlace += 5
    doc.setFontSize(12)
    doc.text(pdfXPlace,pdfYPlace,"[Company name]")
    doc.text(pdfXPlace+90, pdfYPlace,"Ordernr.: "+"[INSERT NUMBER]")

    pdfYPlace += 5
    doc.text(pdfXPlace,pdfYPlace,"[Person]")
    doc.text(pdfXPlace+90, pdfYPlace,"Dato.: "+"[INSERT Date]")

    pdfYPlace += 5
    doc.text(pdfXPlace,pdfYPlace,"[Address]")
    doc.text(pdfXPlace+90, pdfYPlace,"Kundenr.: "+"[INSERT NUMBER]")

    pdfYPlace += 5
    doc.text(pdfXPlace,pdfYPlace,"[zip and city name]")
    doc.text(pdfXPlace+90, pdfYPlace,"Sag: "+"[INSERT CASE]")

    pdfYPlace += 5
    doc.text(pdfXPlace,pdfYPlace,"[Country]")
    doc.text(pdfXPlace+90, pdfYPlace,"Reference: "+"[INSERT SPECIFIC PERSON]")

    pdfYPlace += 10

    doc.text(pdfXPlace,pdfYPlace,"Hermed følger:")
    pdfYPlace +=5

    doc.text(pdfXPlace,pdfYPlace,"Titel: " + "[INSERT TITEL]")
    doc.setFontSize(18)
    doc.text(pdfXPlace+90,pdfYPlace,"SLUTLEVERING")
    doc.setFontSize(12)
    pdfYPlace +=5

    doc.text(pdfXPlace,pdfYPlace,"Rekv.nr.: " +"[Insert number]")
    pdfYPlace +=7

    doc.setFontStyle("bold")
    doc.text(pdfXPlace,pdfYPlace,"Item ID")
    doc.text(pdfXPlace+50,pdfYPlace,"Item Name")
    doc.text(pdfXPlace+140,pdfYPlace,"Quantity")
    pdfYPlace +=2

    doc.line(pdfXPlace,pdfYPlace,175,pdfYPlace);

    /*let counter = 0;
    for (const key in elements){
        
        doc.text("Name: "+elements[key].productName,pdfXPlace,pdfYPlace);
        doc.text("Quantity: " + elements[key].quantity,pdfXPlace+120,pdfYPlace);
        doc.line(20,pdfYPlace+5,175,pdfYPlace+5);
        pdfYPlace += 17;
        counter += 1;
        if(counter%25===0){
            doc.addPage()
        }
    }*/

    doc.save("Følgeseddel.pdf")
}

export function entireStockPDF(state) {
    
    const pdfConverter = require('jspdf');
    const doc = new pdfConverter();
    const elements= {...state.products}
    
    doc.setFontSize(22);
    doc.text(20,50,"Entire stock:");
    doc.setFontSize(10);
    let pdfXPlace = 25;
    let pdfYPlace = 65;
    let counter = 0;
    for (const key in elements){
        
        doc.text("Name: "+elements[key].productName,pdfXPlace,pdfYPlace);
        doc.text("Quantity: " + elements[key].quantity,pdfXPlace+120,pdfYPlace);
        doc.line(20,pdfYPlace+5,175,pdfYPlace+5);
        pdfYPlace += 17;
        counter += 1;
        if(counter%25===0){
            doc.addPage()
        }
    }

    doc.save("EntireStock.pdf")

}