let packageJson = require("./package.json");
var shell = require("shelljs");
const file = require("fs");
let parsedJson = JSON.parse(JSON.stringify(packageJson));
parsedJson.homepage = `https://dev.ulcacontrib.org/ulca`;
packageJson = JSON.stringify(parsedJson);
file.writeFileSync("./package.json", packageJson);
shell.exec("react-scripts build");
