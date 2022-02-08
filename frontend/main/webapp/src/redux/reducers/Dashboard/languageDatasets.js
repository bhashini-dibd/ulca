import C from '../../actions/constants';

 const reducer = (state = {}, action) => {

    function compare( a, b ) {
        if ( a.value < b.value ){
          return 1;
        }
        if ( a.value > b.value ){
          return -1;
        }
        return 0;
      }

    //   const handleData = (dataValues) =>{

    //     if (dataValues.length > 0) {
    //       let others = dataValues.slice(7, dataValues.length)
    //       let othersCount = 0
    //       others.map(dataVal => {
    //           othersCount = dataVal.value + othersCount
  
    //       })
  
    //       let dataSetValues = dataValues.slice(0, 7)
    //       let obj = {}
  
    //       if (dataValues.length > 7) {
    //           obj.value = othersCount
    //           obj.label = "Others"
    //           dataSetValues.push(obj)
    //       }
  

    //       return {dataSetValues, dataValues} 
         

    //   }

      
    // }


    const handleSum = (data,totalCount) =>{
      let count = data.reduce((acc,rem)=>(acc + Number(rem.value)),0)
      if(count%1!==0) {
        count = Number(count).toFixed(3)
      }
      return {data , count, totalCount}

    }
      
      
    switch (action.type) {
        
        case C.DASHBOARD_DATASETS:
            return handleSum(action.payload.data.sort( compare ),action.payload.count);

        default:
            return state;
    }
}

export default reducer;