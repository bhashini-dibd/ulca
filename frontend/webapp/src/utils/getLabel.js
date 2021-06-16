import {Language} from '../configs/DatasetItems';


console.log("--------",Language)
export default (value) => {
    let arr = []
    Language.forEach(val => {
        value.forEach(data => {
            if (val.value === data)
                arr.push(val)
        })

    })
    return arr
}