define(
    [
        "angular",
        "product/route"
    ],
    function(angular,routeConfig){
        "use strict";
        var productModule = angular.module("product",[]).
            config(["configProvider",function(configProvider){
                configProvider.setModuleConfig(productModule,routeConfig);
            }]);
        return productModule;
    });