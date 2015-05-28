require.config({
    baseUrl:"/",
    paths:{
        "jquery": "application/library/jquery/jquery",
        "angular": "application/library/angular/angular",
        "echarts": "application/library/echarts/build/echarts-plain-map",
        "umeditor":"application/library/umeditor/umeditor",
        "bootstrap": "application/library/bootstrap/js/bootstrap",
        "angular-route": "application/library/angular/angular-route",
        "angular-animate": "application/library/angular/angular-animate",
        "angular-bootstrap": "application/library/angular-bootstrap/ui-bootstrap-tpls",
        "angular-file-upload": "application/library/angular-file-upload/angular-file-upload"
    },
    map: {
        "*": {
            "css": "application/library/require/css"
        }
    },
    shim:{
        "jquery": {
            exports:"jquery"
        },
        "angular": {
            deps: ["jquery"],
            exports:"angular"
        },
        "umeditor": {
            deps: [
                "application/library/umeditor/umeditor.config",
                "css!application/library/umeditor/themes/default/css/umeditor"
            ],
            exports:"umeditor"
        },
        "bootstrap": {
            deps: ["jquery"],
            exports:"bootstrap"
        },
        "angular-route": {
            deps: ["angular"],
            exports:"angular-route"
        },
        "angular-file-upload": {
            deps: [
                "angular",
                "application/library/angular-file-upload/angular-file-upload-shim"
            ],
            exports:"angular-file-upload"
        },
        "angular-bootstrap": {
            deps: ["angular"],
            exports:"angular-bootstrap"
        },
        "angular-animate": {
            deps: ["angular"],
            exports:"angular-animate"
        }
    },
    urlArgs: "t=" + new Date().getTime()
});
require(
    [
        "jquery",
        "angular",
        "bootstrap",
        "application/module",
        "membership/module",
        "product/module",
        "application/controller",
        "application/interceptor",
        "application/directive"
    ],
    function(jquery,angular){
        "use strict";
        jquery(function() {
            angular.bootstrap(document,
                [
                    "application",
                    "membership",
                    "product"
                ]
            );
        });
    }
);
