define(
    [
        "angular",
        "angular-route",
        "angular-animate",
        "angular-bootstrap",
        "angular-file-upload"
    ],
    function(angular){
        "use strict";
        var applicationModule = angular.module("application",["ngRoute","ngAnimate","ui.bootstrap","angularFileUpload"]);
        return applicationModule;
    }
);