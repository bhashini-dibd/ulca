import C from "../../../actions/constants";

const initialState = {
    benchmarkPerformance: [],
    //   benchmarkId: "",
    //   name: "",
    //   description: "",
    //   metric: null,
    //   dataset: "",
    //   domain: [],
    //   task: "",
    //   languages: [],
    //   createdOn: null,
    //   submittedOn: null,
};

const updateDate=(data)=>{
    return data.map(value=>{
        value.createdOn = dateConversion(value.createdOn)
        return value;
    })
}

const getBenchmarkTableDetails = (data) => {
    data.benchmarkPerformance = updateDate(data.benchmarkPerformance);
    return {
        // description: data.description,
        // refUrl: data.dataset,
        // language: `${data.languages[0].sourceLanguage} - ${data.languages[0].targetLanguage}`,
        // domain: data.domain ? data.domain.join(", ") : "",
        // modelName: data.name,
        // metric: data.metric ? data.metric.join(", ") : "",
        // task: data.task.type,
        // metricArray: data.metric,
        benchmarkPerformance: data.benchmarkPerformance,
    };
};

const dateConversion = (value) => {
    var myDate = new Date(value);
    let result = myDate.toLocaleString("en-IN", {
      day: "2-digit",
      month: "2-digit",
      year: "numeric",
      hour: "numeric",
      minute: "numeric",
      second: "numeric",
      hour12: true,
    });
    return result.toUpperCase();
  };
  

const reducer = (state = initialState, action) => {
    switch (action.type) {
      case C.BENCHMARK_TABLE:
        const data = getBenchmarkTableDetails(action.payload);
        return {
          ...data,
        };
      default:
        return {
          ...state,
        };
    }
  };

export default reducer;
