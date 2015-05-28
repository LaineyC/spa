define(["system/module"],function(systemModule){
    "use strict";
    systemModule.factory("system.api",["http",function(http){
        var provider = {};
        provider.home = function(success,error) {
            return http.post({url:"/system/home/home.json?home"},success,error);
        };
        return provider;
    }]);
    return systemModule;
});