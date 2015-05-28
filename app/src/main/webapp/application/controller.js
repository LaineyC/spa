define(
    [
        "application/module",
        "application/api",
        "application/service"
    ],
    function(applicationModule){
    "use strict";
    applicationModule.controller("application.controller",["$scope","application.api","security","message","loading","location","$modal",
        function($scope,applicationApi,security,message,loading,location,$modal){
            applicationApi.signIn(null,function(data){
                security.initApplication(data);
                $scope.permissions = security.getPermission();
            });
            $scope.loading = {status:loading.status()};
            $scope.location = {status:location.status()};
            $scope.keepMessages =  message.getKeepMessages();
            $scope.transientMessages =  message.getTransientMessages();
            $scope.messageSo = {page:1,pageSize:10}
            $scope.messageSearch = function(){
                $scope.messagePagedResult = message.history($scope.messageSo.page,$scope.messageSo.pageSize);
            }
            $scope.pageTabs = [{code:"main",url:"/",name:"首页"}];
            $scope.addBusinessTab = function(permission){
                var i;
                angular.forEach($scope.pageTabs,function(tab,index){
                    if(tab.code === permission.code) i = index;
                    tab.active = false;
                });
                if(i)$scope.pageTabs.splice(i,1);
                $scope.pageTabs.splice(1,0,angular.extend({},permission));
                $scope.pageTabs[1].active = true;
                if($scope.pageTabs.length >= 25){
                    $scope.pageTabs.length = 25;
                }
            }
            $scope.openExitModal = function(){
                $modal.open({
                    templateUrl: "application/exit.html",
                    controller: ["$scope","$modalInstance",function ($scope,$modalInstance){
                        $scope.exit = function(){
                            applicationApi.signOut(function(data){
                                $modalInstance.close();
                            });
                        }
                    }]
                });
            };
        }
    ]);
    return applicationModule;
});

