define([], function(){
    "use strict";
    return {
        routes: [
            {path:"/system",templateUrl: "/system/home/home.html", dependencies: ["system/home/home"],permission: null}
        ]
    };
});