import { DatasetItems } from '../configs/DatasetItems';



export default (value) => {

    debugger
   
    let name = ""
    DatasetItems.forEach(val => {
                
                if(value === val.value){
                    name = val.label
                }

            
            
    })
    return name;
}

