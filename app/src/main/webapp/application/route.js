define([], function(){
    "use strict";
    return {
        defaultRoute: "/404",
        routes: [
            {path: "/",templateUrl: "/application/home/home.html", dependencies: ["application/home/home"], permission: null},
            {path: "/404",templateUrl: "/application/error/404.html", dependencies: null, permission: null},
            {path: "/500",templateUrl: "/application/error/500.html", dependencies: null, permission: null}
        ]
    };
});