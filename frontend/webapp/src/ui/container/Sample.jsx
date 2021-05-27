import { Typography } from "@material-ui/core";
import React, { useState } from "react";

export default function Sample() {
  const [state, setState] = useState(0);
  return (
    <Typography variant="h1">
      This is your React Redux Router Boilerplate with Material UI
    </Typography>
  );
}
