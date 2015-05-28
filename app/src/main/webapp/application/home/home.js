define(["application/module","application/api"], function (applicationModule) {
    "use strict";
    applicationModule.controller("application.home.home",["$scope","application.api",
        function($scope,applicationApi){
            $scope.message = applicationApi.home();
        }
    ]);
});