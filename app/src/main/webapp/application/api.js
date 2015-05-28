define(["application/module"],function(applicationModule){
    "use strict";
    applicationModule.factory("application.api",["http",function(http){
        var provider = {};
        provider.home = function() {
            return {message:"这一行是首页控制器测试数据！"};
        };
        provider.signIn = function(admin,success) {
            return http.post({url:"/application/sign/sign-in.json?signIn"},success);
        };
        provider.signOut = function(success) {
            return http.post({url:"/application/sign/sign-out.json?signOut"},success);
        };
        return provider;
    }]);
    return applicationModule;
});