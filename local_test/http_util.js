console.log("This text comes from an external script.");
let token = "";
const httpUtils = {
    get: function (body) {
        console.log("start testHttp");
        return new Promise((resolve, reject) => {
            $.ajax({
                headers: {
                    Authorization: token
                },
                url: body.url,
                data: body.data,
                method: 'get',
                dataType: "json",
                success: res => {
                    resolve(res)
                },
                error: err => {
                    reject(err)
                }
            });
        });
    }
};

const stringToObj = (s) => {
    let map = {};
    s.forEach((el) => {
        map[el.split('=')[0]] = el.split('=')[1]
    });
    return map;
};

(function () {
    window.g = {url: "localhost111"};
}());