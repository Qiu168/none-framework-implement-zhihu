let common_js_token =sessionStorage.getItem("token");

axios.interceptors.request.use(
    config => {
        if(common_js_token) config.headers['authorization'] =common_js_token
        return config
    },
    error =>{
        console.log(error)
        return Promise.reject(error)
    }
)
function formatDate(timestamp) {
    const date = new Date(timestamp);
    const year = date.getFullYear();
    const month = ("0" + (date.getMonth() + 1)).slice(-2);
    const day = ("0" + date.getDate()).slice(-2);
    const hours = ("0" + date.getHours()).slice(-2);
    const minutes = ("0" + date.getMinutes()).slice(-2);
    return year + "-" + month + "-" + day + " " + hours + ":" + minutes ;
}