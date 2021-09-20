import { withStyles, Link, Button } from "@material-ui/core";
import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useHistory } from "react-router-dom";
import DataSet from "../../../../styles/Dataset";
import APITransport from "../../../../../redux/actions/apitransport/apitransport";
import MUIDataTable from "mui-datatables";
import BenchmarkTableAPI from "../../../../../redux/actions/api/Model/ModelSearch/BenchmarkTable";
// import { PageChange, RowChange, FilterTable, clearFilter, tableView } from "../../../../redux/actions/api/DataSet/DatasetView/DatasetAction"
// import ClearReport from "../../../../redux/actions/api/DataSet/DatasetView/DatasetAction";
// import Dialog from "../../../components/common/Dialog"
// import { Cached, DeleteOutline, VerticalAlignTop, GridOn, List } from '@material-ui/icons';
// import UrlConfig from '../../../../configs/internalurlmapping';
import { useParams } from "react-router";
// import C from "../../../../redux/actions/constants";
// import FilterListIcon from '@material-ui/icons/FilterList';
// import FilterList from "./FilterList";
// import GridView from "./GridView";

const BenchmarkTable = (props) => {
    const history = useHistory();
    const columns = [
        {
            name: "benchmarkDatasetId",
            label: "Benchmark ID",
            options: {
                filter: false,
                sort: true,
                display: "excluded"
            },
        },

        {
            name: "benchmarkDatasetName",
            label: "Benchmark Dataset",
            options: {
                filter: false,
                sort: true,
            },
        },

        {
            name: "metric",
            label: "Metric",
            options: {
                filter: false,
                sort: true,
            },
        },
        {
            name: "score",
            label: "Score",
            options: {
                filter: false,
                sort: true,
            },
        },
        {
            name: "createdOn",
            label: "Benchmark Run On",
            options: {
                filter: false,
                sort: true,
            },
        }
    ];




    const options = {
        textLabels: {
            body: {
                noMatch: "No records"
            },
            toolbar: {
                search: "Search",
                viewColumns: "View Column",
            },
            pagination: {
                rowsPerPage: "Rows per page",
            },
            options: { sortDirection: "desc" },
        },
        onRowClick: rowData => history.push(`${process.env.PUBLIC_URL}/model/benchmark-details/${rowData[0]}`),
        // customToolbar: fetchHeaderButton,
        search: false,
        filter: false,
        displaySelectToolbar: false,
        fixedHeader: false,
        filterType: "checkbox",
        download: false,
        print: false,
        viewColumns: false,
        selectableRows: "none",
    };

    const { classes } = props;
    const dispatch = useDispatch();
    const data = useSelector(state => state.benchmarkTableDetails.benchmarkPerformance)
    useEffect(() => {
        const APIObj = new BenchmarkTableAPI(props.modelId)
        dispatch(APITransport(APIObj))
    }, []);
    return (

        <MUIDataTable
            data={data}
            columns={columns}
            options={options}
        />
    )
}

export default withStyles(DataSet)(BenchmarkTable);