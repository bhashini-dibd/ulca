import C from "../../../constants";

export default function ClearDetailedReport() {
    return {     
        type: C.CLEAR_DETAILED_REPORT,
        payload: {
            
        }
    }
}


export  function PageChange(page, constant) {
    return {     
        type: constant,
        payload: page
    }
}

export  function RowChange(count, constant) {
    return {     
        type: constant,
        payload: count
    }
}

export  function ClearTableValue( constant) {
    return {     
        type: constant,
        payload: {}
    }
}

