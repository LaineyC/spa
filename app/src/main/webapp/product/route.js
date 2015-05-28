define([], function(){
    "use strict";
    return {
        routes: [
            {path:"/product",templateUrl: "/product/home/home.html", dependencies: ["product/home/home"],permission: null}
        ]
    };
});