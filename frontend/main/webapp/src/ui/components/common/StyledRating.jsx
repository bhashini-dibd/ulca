import Rating from '@material-ui/lab/Rating';
import { withStyles } from '@material-ui/core/styles';


export const StyledRating = withStyles({
    iconFilled: {
        color: '#FD7F23',
    },
    iconHover: {
        color: '#FD7F23',
    },
})(Rating);