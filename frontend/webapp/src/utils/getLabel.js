import { Language } from '../configs/DatasetItems';
import DatasetItems from '../configs/DatasetItems';

export default (value, params = 'language') => {
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


