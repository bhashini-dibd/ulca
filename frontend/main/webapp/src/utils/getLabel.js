import { Language, FilterBy, ModelTask } from '../configs/DatasetItems';
import DatasetItems from '../configs/DatasetItems';

export const FilterByDomain =(value) =>{
    let arr = []
    FilterBy.domain.forEach(val => {
        value.forEach(data => {
            if (val.value === data)
                arr.push(val)
        })

    })
            return arr;
        
    
}

export const FilterByCollection =(value) =>{
    let arr = []
    FilterBy.collectionMethod.forEach(val => {
        value.forEach(data => {
            if (val.value === data)
                arr.push(val)
        })

    })
            return arr;
        
    
}

export const getLanguageName = (value)=>{
    let result = value;
    Language.forEach(val => {
        if(val.value===value){
            result =  val.label
        }
    })
    return result;
   
}
export const getTaskName = (value)=>{
    let result =""
    ModelTask.forEach(val => {
        if(val.value===value){
            result =  val.label
        }
    })
    return result;
   
}


 export const getLanguageLabel= (value, params = 'language') => {
    let arr = []
    switch (params) {
        case 'datasetType':
            DatasetItems.forEach(val => {
                if (val.value === value)
                    arr.push(val.label)
            })
            return arr;
        default:
            Language.forEach(val => {
                value.forEach(data => {
                    if (val.value === data)
                        arr.push(val)
                })

            })
            return arr
    }

}

export default getLanguageLabel;



