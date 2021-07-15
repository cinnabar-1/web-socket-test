let currentToken;
const httpUrls = {
    login: (account, password) => {
        return {
            url: `http://localhost:8080/login?account=${account}&password=${password}`,
            data: "",
            dataType: "json"
        }
    }
};