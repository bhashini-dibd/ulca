import C from '../../constants';

export default (option) => {
    return {
        type: C.GET_MENU_OPTION,
        payload: option
    }
}