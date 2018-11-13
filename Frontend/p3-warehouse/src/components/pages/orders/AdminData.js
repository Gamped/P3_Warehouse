class AdminData{
    name;
    id;
    amount;
    type;
    packed;

    constructor(name, id, amount, type, packed){
        this.name = name;
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.packed = packed;
    }
}

export default AdminData;