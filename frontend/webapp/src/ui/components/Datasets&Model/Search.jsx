import SearchIcon from "@material-ui/icons/Search";
import { InputBase } from "@material-ui/core";
import { withStyles } from "@material-ui/core/styles";
import DatasetStyle from "../../styles/Dataset";

const Search = (props) => {
  const { classes, handleSearch, searchValue } = props;
  return (
    <div className={classes.search}>
      <div className={classes.searchIcon}>
        <SearchIcon fontSize="small" />
      </div>
      <InputBase
        placeholder="Search..."
        onChange={(e) => handleSearch(e)}
        value={searchValue}
        classes={{
          root: classes.inputRoot,
          input: classes.inputInput,
        }}
        inputProps={{ "aria-label": "search" }}
      />
    </div>
  );
};

export default withStyles(DatasetStyle)(Search);
