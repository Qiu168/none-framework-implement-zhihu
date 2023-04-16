let token =sessionStorage.getItem("token");

axios.interceptors.request.use(
    config => {
        if(token) config.headers['authorization'] =token
        return config
    },
    error =>{
        console.log(error)
        return Promise.reject(error)
    }
)