define(["membership/module","membership/api"], function (membershipModule) {
    "use strict";
    membershipModule.controller("membership.member.edit",["$scope","membership.api","validator",
        function($scope,membershipApi,validator){
            validator.addRule("membership_member_edit_form",{
                name:{
                    required:"昵称必输"
                },
                age:{
                    required:"年龄必输"
                },
                phone:{
                    required:"手机必输"
                }
            });
            $scope.member = membershipApi.member.get({id:1});
            $scope.editorConfig={
                focus:true //自动把光标放到UEditor中。测试config配置
            }
            $scope.selectHead = function($files) {
                var file = $files[0],
                    fileReader = new FileReader();
                $scope.membership_member_edit_form.$pristine = false;
                fileReader.onload = function(event) {
                    $scope.$apply(function() {
                        var result = event.target.result;
                        $scope.member.head = result;
                        var index  = result.indexOf(",");
                        $scope.member.headFile = {
                            name:file.name,
                            size:file.size,
                            contentType:file.type,
                            base64Content:result.substring(index + 1)
                        };
                    });
                }
                fileReader.readAsDataURL(file);
            }
            $scope.edit = function(){
                delete $scope.member.head;
                membershipApi.member.edit($scope.member,{
                    success:function(data){
                        $scope.member = data;
                    },
                    xhr:function(xhr){
                        console.log("xhr:function")
                    },
                    progress:function(evt){
                        console.log("上传进度：" + parseInt(100.0 * evt.loaded / evt.total));
                    }
                });
            };
        }
    ]);
});
