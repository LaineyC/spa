define(["membership/module","membership/api"], function (membershipModule) {
    "use strict";
    membershipModule.controller("membership.member.create",["$scope","membership.api","validator","$q",
        function($scope,membershipApi,validator,$q){
            validator.addRule("membership_member_create_form",{
                name:{
                    required:"昵称必输"
                },
                password:{
                    required:"密码必输"
                },
                repeatPassword:{
                    required:"重复密码必输",
                    repeat:"密码不一致"
                },
                level:{
                    required:"等级必选"
                },
                age:{
                    required:"年龄必输",
                    customCheck:"年龄自定义验证(长度大于4)"
                },
                phone:{
                    required:"手机必输",
                    customUnique:"手机自定义验证(后台验证唯一性)"
                },
                email:{
                    required:"邮箱必输"
                },
                remark:{
                    required:"备注必输"
                }
            })
            $scope.member = {hobbys:[],sex:"M",range:0};
            $scope.hobbys = [{id:1,name:"乒乓"},{id:2,name:"DOTA"},{id:3,name:"CS"}];
            $scope.create = function(){
                membershipApi.member.create($scope.member);
            };
            $scope.customCheck = function(scope,model){
                console.log(scope);
                console.log(model.$viewValue);
                return model.$viewValue.length > 4;
            }
            $scope.customUnique = function(scope,model){
                console.log(scope);
                console.log(model.$viewValue);
                function promise(name) {
                    var deferred = $q.defer();
                    setTimeout(function() {
                        deferred.notify(name);
                        if (name === "gg") {
                            deferred.resolve(name);
                        } else {
                            deferred.reject(name);
                        }
                    }, 500);
                    return deferred.promise;
                }
                return promise(model.$viewValue);
            }
        }
    ]);
});
