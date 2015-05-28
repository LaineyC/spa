define(
    [
        "angular",
        "system/route"
    ],
    function(angular,routeConfig){
        "use strict";
        var systemModule = angular.module("system",[]).
            config(["configProvider",function(configProvider){
                configProvider.setModuleConfig(systemModule,routeConfig);
            }]);
        return systemModule;
    });