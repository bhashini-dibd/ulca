import React from "react";
import clipboard1 from '../img/deatail-card-img/clipboard1.svg'
import dataClassification1 from '../img/deatail-card-img/dataClassification1.svg'
import dataCollection1 from '../img/deatail-card-img/dataCollection1.svg'
import data1 from '../img/deatail-card-img/data1.svg'
import efficiency1 from '../img/deatail-card-img/efficiency1.svg'
import management1 from '../img/deatail-card-img/management1.svg'
import serverCheck1 from '../img/deatail-card-img/serverCheck1.svg'
import standard1 from '../img/deatail-card-img/standard1.svg'
import training1 from '../img/deatail-card-img/training1.svg'
import { Card, CardContent, Grid, Typography } from "@material-ui/core";

const cardData = [
    {
        icon: clipboard1,
        title: "Task-Specific Excellence",
        description: "Multiple benchmarks defined for each model task."
    },
    {
        icon: dataClassification1,
        title: "Attributing Excellence",
        description: "Proper attribution for every contributor at the record level."
    },
    {
        icon: dataCollection1,
        title: "Comprehensive metadata Collection",
        description: "Collect extensive metadata related to dataset for various analysis."
    },
    {
        icon: data1,
        title: "Seamless Exploration",
        description: "Simple interface to search and download datasets based on various filters."
    },
    {
        icon: efficiency1,
        title: "Efficiency Unleashed",
        description: "Deduplication capability built-in"
    },
    {
        icon: management1,
        title: "Establishing Dominance",
        description: "Aiming to Become the Leading Data Repository for Indian Language Resources."
    },
    {
        icon: serverCheck1,
        title: "Elevating Standards",
        description: "Perform various quality checks on the submitted datasets."
    },
    {
        icon: standard1,
        title: "Curate and Standardize",
        description: "Collect datasets for MT, ASR, TTS, OCR and various NLP tasks in standardized but extensible formats."
    },
    {
        icon: training1,
        title: "Precision Perfected",
        description: "Trained models for language specific tasks."
    },
]

export const WhyULCA = () => {
    return (
        <Grid style={{ backgroundColor: "#F5F7FA", paddingTop: 40}}>
            <Typography style={{textAlign: "center", letterSpacing: 1, marginBottom: 15}} variant="h4">Why ULCA</Typography>
            <div style={{textAlign: "center", display: "flex", justifyContent: "center"}}><Typography style={{width: "60%"}} variant="body2">Your premier hub for Indian language resources, providing curated datasets and enhanced language-specific tasks for
                cutting-edge linguistic innovation and research.</Typography></div>
            <Grid style={{ display: "flex", flexWrap: "wrap", gap: 40, padding: 40 }} direction="row" justifyContent="space-evenly" spacing={3}>
                {cardData.map((el, i) =>
                    <Card style={{ width: window.innerWidth * 0.28, height: window.innerHeight * 0.22 }}>
                        <CardContent>
                            <img src={el.icon} />
                            <Typography variant="h5" style={{ marginTop: 10 }}>{el.title}</Typography>
                            <Typography variant="body2" style={{ marginTop: 10 }}>{el.description}</Typography>
                        </CardContent>
                    </Card>
                )}
            </Grid>
        </Grid>

    )
}