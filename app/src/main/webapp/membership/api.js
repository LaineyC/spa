define(["membership/module"],function(membershipModule){
    "use strict";
    membershipModule.factory("membership.api",["http",function(http){
        var provider = {};

        provider.home = function(success) {
            return http.post({url:"/membership/home/home.json?home"},success);
        };

        provider.member = {};
        provider.member.search = function(so,success) {
            return http.post({url:"/membership/member/search.json?search",data:so},success);
        };
        provider.member.create = function(member,success) {
            return http.post({url:"/membership/member/create.json?create",data:member},success);
        };
        provider.member.get = function(member,success) {
            return http.post({url:"/membership/member/get.json?get",data:member},success);
        };
        provider.member.edit = function(member,success) {
            return http.post({url:"/app?method=membership.member.edit",data:member},success);
            //return http.post({url:"/membership/member/get.json?get",data:member},success);
        };

        provider.level = {};

        return provider;
    }]);
    return membershipModule;
});