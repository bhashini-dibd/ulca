import C from '../../constants';

export default (menuType) => {
    return {
        type: C.GET_MENU_TYPE,
        payload: menuType
    }
}