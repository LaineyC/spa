define(["angular","application/module","application/service"],function(angular,applicationModule){
    "use strict";
    /*applicationModule.
        directive("message", ["message",function(message) {
            return {
                restrict: "E",
                scope: {
                    data: "=data",
                    type:"@"
                },
                link : function(scope, $element, attrs) {
                    scope.$watch("data", function() {
                        message[scope.type](scope.data);
                    });
                }
            };
        }]);*/
    applicationModule.
        directive("locationGo", ["location",function(location) {
            return {
                restrict: "A",
                link : function(scope, $element, attrs) {
                    var path = attrs.locationGo;
                    attrs.$observe("locationGo",function(newValue){
                        path = newValue;
                    });
                    $element.bind("click",function(){
                        scope.$apply(function () {
                            location.go(path);
                        });
                    })
                }
            };
        }]);
    applicationModule.
        directive("locationBack", ["location",function(location) {
            return {
                restrict: "A",
                link : function(scope, $element, attrs) {
                    var path = attrs.locationBack;
                    attrs.$observe("locationBack",function(newValue){
                        path = newValue;
                    });
                    $element.bind("click",function(){
                        scope.$apply(function () {
                            location.back(path);
                        });
                    })
                }
            };
        }]);

    applicationModule.
        directive("validateSubmit", ["$parse","validator","message", function ($parse,validator,message) {
            return{
                restrict: "A",
                link : function (scope, $form, attrs, ctrl) {
                    var formControls = $form[0],
                        formName = $form.attr("name"),
                        submitFunction = $parse(attrs.validateSubmit),
                        controlTypes = validator.getControlTypes().toString();
                    $form.addClass("validate-form");
                    angular.forEach(formControls,function(control,index){
                        var $control = angular.element(control);
                        if (controlTypes.indexOf(control.type) > -1 && control.name) {
                            scope.$watchCollection(formName + "." + control.name + ".$error", function (newValue) {
                                if(!scope[formName][control.name].$dirty){
                                    return;
                                }
                                if(scope[formName][control.name].$valid){
                                    validator.removeErrorMessage(control);
                                }
                                else{
                                    var errorMessages = validator.getErrorMessages(control, scope[formName][control.name].$error);
                                    validator.showErrorMessage(control, errorMessages[0]);
                                }
                            });
                            $control.bind("blur", function () {
                                if (scope[formName][this.name].$valid) {
                                    validator.removeErrorMessage(this);
                                } else {
                                    var errorMessages = validator.getErrorMessages(this, scope[formName][this.name].$error);
                                    validator.showErrorMessage(this, errorMessages[0]);
                                }
                                scope.$apply();
                            });
                        }
                    });
                    var validate = function () {
                        var errorMessages = [];
                        angular.forEach(formControls,function(control,index){
                            var $control = angular.element(control);
                            if (controlTypes.indexOf(control.type) > -1 && control.name) {
                                if (scope[formName][control.name].$valid) {
                                    $control.removeClass("validate-error");
                                    $control.parent("div").removeClass("has-error");
                                    return;
                                } else {
                                    var elementErrors = validator.getErrorMessages(control, scope[formName][control.name].$error);
                                    errorMessages.push(elementErrors[0]);
                                    $control.addClass("validate-error");
                                    $control.parent("div").addClass("has-error");
                                }
                            }
                        });
                        message.danger(errorMessages);
                        scope.$apply();
                    };
                    $form.bind("submit",function(){
                        validate();
                        if (scope[formName].$valid && angular.isFunction(submitFunction)) {
                            scope.$apply(function () {
                                submitFunction(scope);
                            });
                        }
                    });
                }
            };
        }]);

    applicationModule
        .directive("repeat", [function () {
            return {
                require: "ngModel",
                link   : function (scope, elem, attrs, ctrl) {
                    var otherInput = elem.inheritedData("$formController")[attrs.repeat];
                    ctrl.$parsers.push(function (value) {
                        ctrl.$setValidity("repeat", value === otherInput.$viewValue);
                        return value;
                    });
                    otherInput.$parsers.push(function (value) {
                        if(ctrl.$viewValue){
                            otherInput.$setValidity("repeat", value === ctrl.$viewValue);
                        }
                        return value;
                    });
                }
            };
        }]);

    applicationModule
        .directive("unique", ["$timeout","http",function ($timeout,http) {
            return {
                require: "ngModel",
                link   : function (scope, elem, attrs, ctrl) {
                    var config = scope.$eval(attrs.unique);
                    var url = config.url;
                    var data = config.data;
                    var promise = null
                    ctrl.$parsers.push(function (value) {
                        ctrl.$setValidity('unique', true);
                        ctrl.$loading = false;
                        promise && $timeout.cancel(promise)
                        $timeout(function(){
                            if (ctrl.$valid || ctrl.$error.unique) {
                                promise = $timeout(function(){
                                    if (value && ctrl.$dirty) {
                                        ctrl.$loading = true;
                                        http.post({url:url,data:data},function (isExists) {
                                            ctrl.$loading = false;
                                            ctrl.$setValidity('unique', !isExists);
                                        });
                                    }
                                },400);
                            }
                        })
                        return value;
                    });
                }
            };
        }]);

    applicationModule.
        directive("validators", [function () {
            return{
                restrict: 'A',
                require: 'ngModel',
                link: function (scope, elem, attrs, ctrl) {
                    var validateFn,
                        validators = scope.$eval(attrs.validators);
                    angular.forEach(validators, function (validator, key) {
                        validateFn = function (value) {
                            var expression = scope.$eval(validator,{ "$viewValue": value});
                            if (angular.isObject(expression) && angular.isFunction(expression.then)) {
                                expression.then(function () {
                                    ctrl.$setValidity(key, true);
                                }, function () {
                                    ctrl.$setValidity(key, false);
                                });
                            } else if (expression) {
                                ctrl.$setValidity(key, true);
                            } else {
                                ctrl.$setValidity(key, false);
                            }
                            return value;
                        };
                        //ctrl.$formatters.push(validateFn);
                        ctrl.$parsers.push(validateFn);
                    });
                }
            };
        }]);

    var umeditor_id = 0;
    applicationModule.
        directive("umeditor", ["$timeout",function($timeout) {
            return {
                restrict: "AE",
                transclude: true,
                replace: true,
                template: '<script id="{{umeditor_id}}" type="text/plain" ng-transclude></script>',
                require: "?ngModel",
                scope: {
                    config: "="
                },
                controller:function($scope){
                    $scope.umeditor_id = "umeditor_" + umeditor_id;
                    umeditor_id++;
                },
                link: function (scope, element, attrs, ngModel) {
                    $timeout(function () {
                        var editor = UM.getEditor(scope.umeditor_id, scope.config || {});
                        if (ngModel) {
                            ngModel.$render = function () {
                                if(ngModel.$viewValue != undefined){
                                    editor.setContent(ngModel.$viewValue);
                                }
                            };
                            editor.addListener("contentChange", function () {
                                $timeout(function () {
                                    ngModel.$setViewValue(editor.getContent());
                                })
                            })
                        };
                        editor.ready(function() {
                            editor.setContent(ngModel.$viewValue);
                        });
                        scope.$on("$destroy",function(){
                            editor.destroy();
                        });
                    });
                }
            };
        }]);

    applicationModule.
        directive("echarts", [function() {
            return {
                restrict: "AE",
                transclude: true,
                replace: true,
                template: '<div ng-transclude></div>',
                scope: {
                    option: "="
                },
                link: function (scope, element, attrs) {
                    var chart = echarts.init(element[0]);
                    chart.setOption(scope.option);
                    scope.$on("$destroy",function(){
                        chart.dispose();
                    });
                    scope.chart = chart;
                }
            };
        }]);

    return applicationModule;
});