define(["membership/module","membership/api"], function (membershipModule) {
    "use strict";
    membershipModule.controller("membership.member.manage",["$scope","membership.api",
        function($scope,membershipApi){
            $scope.so = {page:1,pageSize:10}
            $scope.search = function(){
                membershipApi.member.search($scope.so,function(data){
                    $scope.pagedResult = data;
                });
            }
            $scope.search();
        }
    ]);
});
