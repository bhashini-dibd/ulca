export default () => {
    let token = localStorage.getItem('token')
    if (token) {
        return true;
    }
    return false;
}