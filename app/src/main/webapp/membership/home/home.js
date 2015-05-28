define(["membership/module","membership/api"], function (membershipModule) {
    "use strict";
    membershipModule.controller("membership.home.home",["$scope","membership.api",
        function($scope,membershipApi){
            $scope.message = membershipApi.home();
        }
    ]);
});
