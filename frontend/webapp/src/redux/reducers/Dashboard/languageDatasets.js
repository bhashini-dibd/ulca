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


    const handleExpand = (data) =>{

    }
      
      
    switch (action.type) {
        
        case C.DASHBOARD_DATASETS:
            return action.payload.data.sort( compare );

        default:
            return state;
    }
}

export default reducer;