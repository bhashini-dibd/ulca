 const TikTac = () => {
    return (
        <div>
        <div> "TicTak"</div>
        <table style={{width:"30%"}}>
            <tr style={{width:"3rem",height:"3rem", background:"black"}}>
                <td></td>
                    <td></td>
                    <td></td>

            </tr >
            <tr style={{width:"3rem",height:"3rem", background:"black"}}>
            <td></td>
                    <td></td>
                    <td></td>
            
                
            </tr>
            <tr style={{width:"3rem",height:"3rem", background:"black"}}>
            <td></td>
                    <td></td>
                    <td></td>
                
            </tr>
            </table>
        </div>
    )
}

export default TikTac;

// 1) only one click will be allowed at a time
// 2) they can't click 2nd time on filled box.
// 3) if 9 box filled or not
// 4) if([1,2,3],[])