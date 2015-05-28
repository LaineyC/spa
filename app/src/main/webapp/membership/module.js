define(
    [
        "angular",
        "membership/route"
    ],
    function(angular,routeConfig){
        "use strict";
        var membershipModule = angular.module("membership",[]).
            config(["configProvider",function(configProvider){
                configProvider.setModuleConfig(membershipModule,routeConfig);
            }]);
    return membershipModule;
});