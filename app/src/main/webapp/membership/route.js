define([], function(){
    "use strict";
    return {
        routes: [
            {path:"/membership",templateUrl: "/membership/home/home.html",dependencies: ["membership/home/home"],permission: null},
            {path:"/membership/member/manage",templateUrl: "/membership/member/manage.html",dependencies: ["membership/member/manage"],permission: "membership.member.manage"},
            {path:"/membership/member/create",templateUrl: "/membership/member/create.html",dependencies: ["membership/member/create"],permission: "membership.member.create"},
            {path:"/membership/member/edit",templateUrl: "/membership/member/edit.html",dependencies: ["umeditor","membership/member/edit"],permission: "membership.member.edit"},
            {path:"/membership/report/member",templateUrl: "/membership/report/member.html",dependencies: ["echarts","membership/report/member"],permission: "membership.report.member"}
        ]
    };
});