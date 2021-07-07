import {
    MenuItem,
    Menu,
    withStyles
} from '@material-ui/core';
import HeaderStyles from "../../styles/HeaderStyles";

const StyledMenu = withStyles({

})((props) => (
    <Menu
        elevation={4}
        getContentAnchorEl={null}
        anchorOrigin={{
            vertical: 'bottom',
            horizontal: 'left',
        }}
        transformOrigin={{
            vertical: 'top',
            horizontal: 'left',
        }}
        {...props}
    />
));

const MenuItems = (props) => {
    const { classes } = props
    return <>
        <StyledMenu id={props.id}
            anchorEl={props.anchorEl}
            open={Boolean(props.anchorEl)}
            onClose={(e) => props.handleClose(e)}
            className={classes.styledMenu1}
        >
            {
                props.menuOptions.map(menu => {
                    return <MenuItem
                        className={classes.styledMenu}
                        onClick={() => props.handleMenuItemClick(menu.url)}
                    >
                        {menu.name}
                    </MenuItem>
                })
            }
        </StyledMenu>
    </>
}
export default withStyles(HeaderStyles)(MenuItems);