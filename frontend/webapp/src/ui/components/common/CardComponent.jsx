import BlueCard from '../../../assets/card.svg';
import GreenCard from '../../../assets/card2.svg';
import { Grid, Typography, Card } from '@material-ui/core';

const CardComponent = (props) => {
    const { value } = props;
    return (
        <Grid container spacing={2} style={{ marginTop: '20px' }}>{
            value.responseData.map((data, i) => {
                return (
                    <Grid item xs={12} sm={6} md={6} lg={4} xl={4}
                        style={{ marginBottom: '20px', background: `url(${i%2===0?BlueCard:GreenCard}) no-repeat`, height: '270px' }}>
                        <div onClick={props.onClick} style={{ padding: '10px 20px', boxSizing: "border-box" }}>
                            <Typography style={{
                                color: 'black',
                                backgroundColor: "#FFD981",
                                borderRadius: '24px',
                                padding: '5px 10px',
                                width: 'fit-content',
                                fontSize: '12px',
                                display: 'flex',
                                alignItems: 'center',
                                justifyContent: 'center'

                            }} variant="body2">{data.task.toUpperCase()}</Typography>
                            <Typography variant="body1" style={{
                                marginTop: '15px',
                                height: '64px',
                                backgroundColor: 'white',
                                maxWidth: '340px',
                                width: 'auto',
                                display: 'flex',
                                alignItems: 'center',
                                paddingLeft: '15px',
                                fontWeight: '600',
                                borderRadius: '12px'
                            }}>{data.modelName}</Typography>
                            <Grid style={{ marginTop: '21px'}} container>
                                <Grid item xs={4} sm={4} md={4} lg={4} xl={4}>
                                    <Typography variant="caption" style={{color:"#ffffff",opacity:'0.6'}} gutterBottom>Source</Typography>
                                    <Typography variant="body2" style={{color:"#ffffff"}}>{data.source}</Typography>
                                </Grid>
                                <Grid item xs={4} sm={4} md={4} lg={4} xl={4}>
                                    <Typography variant="caption" style={{color:"#ffffff",opacity:'0.6'}} gutterBottom>Target</Typography>
                                    <Typography variant="body2" style={{color:"#ffffff"}}>{data.target}</Typography>
                                </Grid>
                            </Grid>
                            <Grid style={{ marginTop: '28px',color:"#ffffff" }} container>
                                <Grid item xs={4} sm={4} md={4} lg={4} xl={4}>
                                    <Typography variant="caption" style={{color:"#ffffff",opacity:'0.6'}} gutterBottom>Domain</Typography>
                                    <Typography variant="body2" style={{color:"#ffffff"}}>{data.domain}</Typography>
                                </Grid>
                                <Grid item xs={4} sm={4} md={4} lg={4} xl={4}>
                                    <Typography variant="caption" style={{color:"#ffffff",opacity:'0.6'}} gutterBottom>Submitter</Typography>
                                    <Typography variant="body2" style={{color:"#ffffff"}}>{data.submitter}</Typography>
                                </Grid>
                                <Grid item xs={4} sm={4} md={4} lg={4} xl={4}>
                                    <Typography variant="caption" style={{color:"#ffffff",opacity:'0.6'}} gutterBottom>Published On</Typography>
                                    <Typography variant="body2" style={{color:"#ffffff"}}>{data.publishedOn.split(",")[0]}</Typography>
                                </Grid>
                            </Grid>
                        </div>
                    </Grid>
                )
            })
        }
        </Grid>
        // <Grid container spacing={2} >
        //     {value.responseData.map((data, i) => {
        //         return <Grid key={i}
        //             style={{}}
        //             item xs={12} sm={12} md={4} lg={4} xl={4}>
        //             <Card onClick={props.onClick} 
        //             style={{width:'100%', border: 'none', cursor: "pointer", background: `url(${BlueCard})`, minHeight: '270px', backgroundRepeat: "no-repeat" }} >
        //                 <div style={{margin:'20px'}}>
        //                 <Typography style={{
        //                     color: 'black',
        //                     // height: '24px',
        //                     backgroundColor: "#FFD981",
        //                     // marginLeft: '20px',
        //                     // marginTop: '20px',
        //                     borderRadius: '24px',
        //                     padding: '5px 10px',
        //                     width: 'fit-content',
        //                     fontSize: '12px',
        //                     display: 'flex',
        //                     alignItems: 'center',
        //                     justifyContent: 'center'

        //                 }} variant="body2">{data.task.toUpperCase()}</Typography>
        //                 {/* <Card style={{ marginTop: '15px', width: '100%', height: '62px', borderRadius: '12px', display: 'flex', alignItems: 'center' }}> */}
        //                     {/* <Typography variant="body1" style={{marginTop:'15px',paddingLeft:'1%',width: '100%', height: '62px', borderRadius: '12px', display: 'flex', alignItems: 'center',backgroundColor:'white' }} >{data.modelName}</Typography> */}
        //                 {/* </Card> */}
        //                 <Grid container style={{marginTop:"15px",backgroundColor:'white',borderRadius:'12px'}}>
        //                     <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
        //                         {data.modelName}
        //                     </Grid>
        //                 </Grid>
        //                 </div>
        //             </Card>
        //         </Grid>
        //     })}

        // </Grid>
    )
}

export default CardComponent;