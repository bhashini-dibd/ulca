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

const getBenchmarkTableDetails = (data) => {
    return {
        // description: data.description,
        // refUrl: data.dataset,
        // language: `${data.languages[0].sourceLanguage} - ${data.languages[0].targetLanguage}`,
        // domain: data.domain ? data.domain.join(", ") : "",
        // modelName: data.name,
        // metric: data.metric ? data.metric.join(", ") : "",
        // task: data.task.type,
        // metricArray: data.metric,
    };
};

const reducer = (state = initialState, action) => {
    console.log('data', action.payload)
    switch (action.type) {
        case C.GET_BENCHMARK_TABLE_DETAILS:
            const data = action.payload;
            return {
                data,
            };
        default:
            return {
                data: state,
            };
    }
};

export default reducer;
