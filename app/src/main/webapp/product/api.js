define(["product/module"],function(productModule){
    "use strict";
    productModule.factory("product.api",["http",function(http){
        var provider = {};
        provider.home = function(success,error) {
            return http.post({url:"/product/home/home.json?home"},success,error);
        };
        return provider;
    }]);
    return productModule;
});