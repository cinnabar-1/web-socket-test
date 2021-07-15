console.log("This text comes from an external script.");
const httpUtils = {
    get: function (body) {
        console.log("start testHttp");
        let result;
        return new Promise((resolve, reject) => {
            $.ajax({
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
        })
        return result;
    }
};

(function () {
    window.g = {url: "localhost111"};
}())