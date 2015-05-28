define(["angular","application/module","application/route"],function(angular,applicationModule,routeConfig){
    "use strict";
    function loadDependencies(dependencies){
        if(!dependencies){
            return dependencies;
        }
        var deferred,isload,
            definition ={
                "load": ['$q','$rootScope', function($q, $rootScope){
                    deferred = $q.defer();
                    if(!isload){
                        require(dependencies, function(){
                            $rootScope.$apply(function(){
                                deferred.resolve();
                            });
                        });
                        isload = !isload;
                    }
                    else{
                        deferred.resolve();
                    }
                    return deferred.promise;
                }]
            }
        return definition;
    };

    var providers = {};
    applicationModule.config(["$routeProvider","$locationProvider","$controllerProvider","$compileProvider","$filterProvider","$provide","$httpProvider",
        function($routeProvider, $locationProvider, $controllerProvider, $compileProvider, $filterProvider, $provide){
            providers.route      = $routeProvider;
            providers.location   = $locationProvider;
            providers.controller = $controllerProvider;
            providers.compile    = $compileProvider;
            providers.filter     = $filterProvider;
            providers.provider   = $provide;
        }
    ]);

    applicationModule.factory("security",["config",function(config){
        var provider = {},
            permissionMap = {},
            applicationConfig = null;

        function setPermissionMap(permissions){
            angular.forEach(permissions, function(permission,index) {
                permissionMap[permission.code] = permission;
                if(permission.children){
                    setPermissionMap(permission.children);
                }
            });
        }

        provider.hasPermission = function(permission){
            return !!permissionMap[permission];
        };
        provider.getPermission = function(permission){
            if(!permission){
                return applicationConfig.permissions
            }
            return permissionMap[permission];
        };
        provider.getApplicationConfig = function(){
            return applicationConfig;
        }
        provider.initApplication = function(cfg){
            applicationConfig = cfg;
            setPermissionMap(applicationConfig.permissions);
            var routeConfigs = config.getRouteConfig();
            angular.forEach(routeConfigs, function(routeConfig,module) {
                angular.forEach(routeConfig.routes, function(route,index) {
                    if(provider.hasPermission(route.permission)){
                        provider.getPermission(route.permission).url = route.path;
                    }
                    if(!route.permission || provider.hasPermission(route.permission)){
                        providers.route.when(route.path,{
                            template: route.template,
                            templateUrl: route.templateUrl,
                            resolve: loadDependencies(route.dependencies)
                        });
                    }
                });
            });
        }
        return provider;
    }]);

    applicationModule.provider("config", [function() {
        var routeConfigs = {};
        return {
            setModuleConfig: function(module,routeConfig) {
                if(module.name == "application"){
                    angular.forEach(routeConfig.routes, function(route,index) {
                        if(!route.permission) {
                            providers.route.when(route.path,{
                                template: route.template,
                                templateUrl: route.templateUrl,
                                resolve: loadDependencies(route.dependencies)
                            });
                        }
                    });
                    providers.route.otherwise({redirectTo: routeConfig.defaultRoute});
                }
                routeConfigs[module.name] = routeConfig;
                module.controller = providers.controller.register;
                module.directive  = providers.compile.directive;
                module.filter     = providers.filter.register;
                module.factory    = providers.provider.factory;
                module.service    = providers.provider.service;
                module.provider   = providers.provider.provider;
                //provider.location.html5Mode(true).hashPrefix("!");
            },
            $get:[function() {
                return {
                    getRouteConfig: function(){return routeConfigs;}
                };
            }]
        };
    }]);

    applicationModule.factory("message",[function(){
        var provider = {},
            messages = [],
            total = 100,
            keepMessages  = [],
            transientMessages = [];
        function addMessage(message,type,isKeep){
            if(!message){
                return;
            }
            if(angular.isString(message)){
                if(isKeep){
                    keepMessages.unshift({content:message,type:type});
                }
                else{
                    transientMessages.length = 0;
                    transientMessages.push({content:message,type:type});
                }
                messages.unshift({content:message,type:type});
            }
            else{
                if(!isKeep){
                    transientMessages.length = 0;
                }
                angular.forEach(message,function(msg,index){
                    if(isKeep){
                        keepMessages.unshift({content:msg,type:type});
                    }
                    else{
                        transientMessages.push({content:msg,type:type});
                    }
                    messages.unshift({content:msg,type:type});
                });
            }
            if(messages.length > total){
                messages.length = total;
            }
        }
        provider.success = function(msg,isKeep){
            addMessage(msg,"success",isKeep);
        }
        provider.info = function(msg,isKeep){
            addMessage(msg,"info",isKeep);
        }
        provider.warning = function(msg,isKeep){
            addMessage(msg,"warning",isKeep);
        }
        provider.danger = function(msg,isKeep){
            addMessage(msg,"danger",isKeep);
        }
        provider.clearMessages = function(){
            messages.length = 0;
        }
        provider.clearKeepMessages = function(){
            keepMessages.length = 0;
        }
        provider.clearTransientMessages = function(){
            transientMessages.length = 0;
        }
        provider.getKeepMessages  = function(){
            return keepMessages;
        }
        provider.getTransientMessages  = function(){
            return transientMessages;
        }
        provider.history = function(page,pageSize){
            var index = (page - 1) * pageSize;
            return {
                result: messages.slice(index,index + pageSize),
                total: messages.length
            }
        }
        return provider;
    }]);

    applicationModule.factory("loading",[function() {
        var provider = {},
            status = {
                loading: false,
                progress: 0
            };
        provider.run = function(){
            status.progress += 1;
            status.loading = true;
        }
        provider.status = function(){
            return status;
        }
        provider.stop = function(){
            status.progress -= 1;
            if(status.loading && status.progress == 0){
                status.loading = false;
            }
        }
        return provider
    }]);

    applicationModule.factory("http",["$http","$upload","$q","util",function($http,$upload,$q,util) {
        var provider = {},
            baseUrl = "http://localhost:8080";
        function sendHttp(config) {
            config.method = config.method || 'POST';
            config.headers = config.headers || {};
            var deferred = $q.defer();
            if (window.XMLHttpRequest.__isShim) {
                config.headers['__setXHR_'] = function() {
                    return function(xhr) {
                        if (!xhr) return;
                        config.__XHR = xhr;
                        config.xhrFn && config.xhrFn(xhr);
                        xhr.upload.addEventListener('progress', function(e) {
                            deferred.notify(e);
                        }, false);
                        //fix for firefox not firing upload progress end, also IE8-9
                        xhr.upload.addEventListener('load', function(e) {
                            if (e.lengthComputable) {
                                deferred.notify(e);
                            }
                        }, false);
                    };
                };
            }
            $http(config).then(function(r){deferred.resolve(r)}, function(e){deferred.reject(e)}, function(n){deferred.notify(n)});
            var promise = deferred.promise;
            promise.success = function(fn) {
                promise.then(function(response) {
                    fn(response.data, response.status, response.headers, config);
                });
                return promise;
            };
            promise.error = function(fn) {
                promise.then(null, function(response) {
                    fn(response.data, response.status, response.headers, config);
                });
                return promise;
            };
            promise.progress = function(fn) {
                promise.then(null, null, function(update) {
                    fn(update);
                });
                return promise;
            };
            promise.abort = function() {
                if (config.__XHR) {
                    $timeout(function() {
                        config.__XHR.abort();
                    });
                }
                return promise;
            };
            promise.xhr = function(fn) {
                config.xhrFn = (function(origXhrFn) {
                    return function() {
                        origXhrFn && origXhrFn.apply(promise, arguments);
                        fn.apply(promise, arguments);
                    }
                })(config.xhrFn);
                return promise;
            };
            return promise;
        }
        provider.request = function(config,callback){
            var isArray = config.isArray,
                value = isArray ? [] : {},
                promise,success,error,progress,xhr;
            config.url = baseUrl + config.url;
            if(callback){
                if(angular.isFunction(callback)){
                    success = callback
                }
                else if(angular.isObject(callback)){
                    success = callback.success;
                    error = callback.error;
                    progress = callback.progress;
                    xhr = callback.xhr;
                }
            }
            promise = sendHttp(config);
            promise.success(function(){
                promise.then(function(response) {
                    var data = util.parseData(response);
                    if(isArray){
                        angular.forEach(data,function(value,index){
                            value.push(value);
                        });
                    }
                    else{
                        angular.extend(value,data);
                    }
                    success && success(value,response.status, response.headers, config);
                    return response;
                });
            });
            if(error)
                promise.error(error);
            if(progress)
                promise.progress(progress);
            if(xhr)
                promise.xhr(xhr);
            return value;
        }
        provider.get = function(config,callback){
            config.method = "GET";
            return provider.request(config,callback);
        }
        provider.post = function(config,callback){
            config.method = "POST";
            return provider.request(config,callback);
        }
        provider.postForm = function(config,callback){
            var isArray = config.isArray,
                value = isArray ? [] : {},
                promise,success,error,progress,xhr ;
            config.url = baseUrl + config.url;
            if(callback){
                if(angular.isFunction(callback)){
                    success = callback
                }
                else if(angular.isObject(callback)){
                    success = callback.success;
                    error = callback.error;
                    progress = callback.progress;
                    xhr = callback.xhr;
                }
            }
            promise = $upload.upload(config);
            promise.success(function(){
                promise.then(function(response) {
                    var data = util.parseData(response);
                    if(isArray){
                        angular.forEach(data,function(value,index){
                            value.push(value);
                        });
                    }
                    else{
                        angular.extend(value,data);
                    }
                    success && success(value,response.status, response.headers, config);
                    return response;
                });
            });
            if(error)
                promise.error(error);
            if(progress)
                promise.progress(progress);
            if(xhr)
                promise.xhr(xhr);
            return value;
        }

        return provider
    }]);

    applicationModule.factory("util",[function() {
        var provider = {};
        /**
         *  {
         *      "errors":[],
         *      "data":{}
         *  }
         */
        provider.parseData = function(response){
            return response.data.data;
        }
        provider.parseError = function(response){
            var errors = response.data.errors;
            if(!errors || !errors.length){
                return null;
            }
            return errors;
        }
        return provider
    }]);

    applicationModule.factory("location",["$window","$location",function($window,$location) {
        var provider = {},
            status = {animate:"go"};
        provider.go = function(path){
            status.animate = "go";
            $location.path(path);
        }
        provider.back = function(path){
            status.animate = "back";
            if(path){
                $location.path(path);
            }
            else{
                $window.history.back()
            }
        }
        provider.status = function(){
            return status;
        }
        return provider
    }]);

    applicationModule.factory("validator",["message",function(message) {
        var provider = {},
            controlTypes = ["text", "password", "email", "number", "url", "textarea", "select", "select-one"],
            defaultRules = {
                required    : "该选项不能为空",
                maxlength   : "该选项输入值长度不能大于{maxlength}",
                minlength   : "该选项输入值长度不能小于{minlength}",
                email       : "输入邮件的格式不正确",
                repeat      : "两次输入不一致",
                pattern     : "该选项输入格式不正确",
                number      : "必须输入数字",
                unique      : "该输入值已经存在，请重新输入",
                url         : "输入URL格式不正确",
                max         : "该选项输入值不能大于{max}",
                min         : "该选项输入值不能小于{min}"
            },
            validateRules = {};
        provider.getControlTypes = function(){
            return controlTypes;
        }
        provider.getDefaultRules = function(){
            return defaultRules;
        }
        provider.addRule = function(formName,rule){
            if(formName in validateRules){
                return;
            }
            validateRules[formName] = rule;
        }
        provider.getErrorMessage = function(control,error){
            var message = null,
                $control = angular.element(control),
                formName = $control.parents("form").attr("name"),
                rules = validateRules[formName];
            if(rules && rules[control.name] && rules[control.name][error]){
                message = rules[control.name][error];
            }
           else{
                if(!defaultRules[error]){
                    throw new Error("该验证规则(" + error + ")默认错误信息没有设置！");
                }
                message = defaultRules[error];
            }
            switch (error) {
                case "maxlength":
                    return message.replace("{maxlength}", $control.attr("ng-maxlength"));
                case "minlength":
                    return message.replace("{minlength}", $control.attr("ng-minlength"));
                case "max":
                    return message.replace("{max}", $control.attr("max"));
                case "min":
                    return message.replace("{min}", $control.attr("min"));
            }
            return message;
        }
        provider.getErrorMessages = function(control,errors){
            var errorMessages = [];
            for (var error in errors) {
                if(errors[error]){
                    var message = provider.getErrorMessage(control,error);
                    errorMessages.push(message);
                }
            }
            return errorMessages;
        }
        provider.showErrorMessage = function(control,errorMessages){
           var $control = angular.element(control);
            $control.addClass("validate-error");
            $control.parent("div").addClass("has-error");
            message.danger(errorMessages);
        }
        provider.removeErrorMessage = function(control){
            var $control = angular.element(control);
            $control.removeClass("validate-error");
            $control.parent("div").removeClass("has-error");
            message.clearTransientMessages();
        }
        return provider;
    }]);

    applicationModule.config(["configProvider",function(configProvider){
        configProvider.setModuleConfig(applicationModule,routeConfig);
    }]);

    return applicationModule;
});