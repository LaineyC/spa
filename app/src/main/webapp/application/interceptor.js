define(
    [
        "application/module",
        "application/service"
    ],
    function(applicationModule){
        "use strict";
        applicationModule.
            config(["$provide","$httpProvider",
                function($provide,$httpProvider){
                    $provide.factory("loadingInterceptor",["$q","loading","message",function($q,loading,message){
                        return {
                            "request": function(config) {
                                loading.run();
                                return config;
                            },
                            "requestError": function(response) {
                                message.danger("loading截器请求错误",true);
                                return $q.reject(response);;
                            },
                            "response": function(response) {
                                loading.stop();
                                return response;
                            },
                            "responseError": function(response) {
                                message.danger("loading拦截器响应错误",true);
                                return $q.reject(response);;
                            }
                        };
                    }]);
                    $provide.factory("errorInterceptor",["$q","message","location","util",function($q,message,location,util){
                        return {
                            "request": function(config) {
                                return config;
                            },
                            "requestError": function(response) {
                                message.danger("error拦截器请求错误",true);
                                return $q.reject(response);
                            },
                            "response": function(response) {
                                var errors = util.parseError(response);
                                if(errors){
                                    message.danger(errors,true);
                                }
                                return response;
                            },
                            "responseError": function(response) {
                                location.go("/500");
                                return response;
                            }
                        };
                    }]);
                    $httpProvider.interceptors.push("loadingInterceptor");
                    $httpProvider.interceptors.push("errorInterceptor");
                }]
            );
        return applicationModule;
    }
);