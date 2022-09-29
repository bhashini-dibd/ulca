const FooterStyle = (theme) => ({
    
    grid: {display:"flex",background:"#DFEAFB",padding:"30px 50px", color:"#16337B",alignItems:"center"},
    grid2: {display:"flex",background:"#C6D9F4",padding:"30px 50px", color:"#16337B",alignItems:"center"},
    grid3: {display:"flex",background:"#B3CAED",padding:"30px 50px", color:"#16337B",alignItems:"center"},
    social :{display:"flex",marginLeft:"auto",width:"40%",justifyContent:"space-between","@media  (max-device-width : 650px)": { marginTop:"20px"}},
    container:{width:"100%",display:"flex",boxSizing:"border-box",flexWrap:"wrap" },
    bhasini:{display:"flex",marginRight:"auto",width:"80%",justifyContent:"space-between",marginTop:"10px", "@media  (max-device-width : 650px)": { width:"100%"},},
    info :{display:"flex",marginLeft:"auto",width:"80%",justifyContent:"space-between",marginTop:"30px"},
    copy:{display:"flex",marginRight:"auto",width:"80%",justifyContent:"space-between",marginTop:"30px"},
    parent:{background:"#0F2749 !important"},
    parentDiv:{maxWidth:"1140px",display:"flex",margin:"auto"},
    textAlign:{textAlign:"center",marginTop:"2rem"},
    image:{marginRight:"1rem"},
    link:{fontWeight:"bold", color:"#16337B",marginTop:"10px",overflowWrap:"anywhere"}
  });
  export default FooterStyle;
  