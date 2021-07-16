let currentToken;
const ip = "http://localhost:8080";
const httpUrls = {
    login: (account, password) => {
        return {
            url: `${ip}/login?account=${account}&password=${password}`,
            dataType: "json"
        }
    },
    userInfo: (token) => {
        return {
            url: `${ip}/user/userInfo/${token}`,
            dataType: "json"
        }
    }
};