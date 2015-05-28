<div  ng-controller="application.home.home">
<div class="panel panel-info">
    <div class="panel-heading">基于angularJs，requireJs，bootstrap的单页网页应用简单实现。</div>
    <div class="panel-heading">页面布局、错误显示、数据结构、验证处理、页面跳转、文件上传、命名约定、代码目录结构、服务扩展、ui扩展....基本的前端开发事项都解决 除了安全性没考虑 基本就是现成的一套
                  主要是前端的东西 后端简单的设计了mybatis+springMVC 测测数据交互。</div>
    <div class="panel-body">
        <h4><b>定义</b></h4>
        <p class="bg-info">单页 Web 应用 (single-page application 简称为 SPA) 是一种特殊的 Web 应用。它将所有的活动局限于一个Web页面中，仅在该Web页面初始化时加载相应的HTML、JavaScript 和 CSS。一旦页面加载完成了，SPA不会因为用户的操作而进行页面的重新加载或跳转。取而代之的是利用 JavaScript 动态的变换HTML的内容，从而实现UI与用户的交互。由于避免了页面的重新加载，SPA 可以提供较为流畅的用户体验。</p>
        <p class="bg-info">单页Web程序的出现是富客户端发展的必然结果，但是该技术也是有些局限性，所以采用之前需要了解清楚它的优缺点。</p>
        <h4><b>优点</b></h4>
        <h6><b>1.良好的交互体验</b></h6>
        <p class="bg-info">用户不需要重新刷新页面，获取数据也是通过Ajax异步获取，页面显示流畅。</p>
        <h6><b>2.良好的前后端工作分离模式</b></h6>
        <p class="bg-info">单页Web应用可以和RESTful规约一起使用，通过REST API提供接口数据，并使用Ajax异步获取，这样有助于分离客户端和服务器端工作。更进一步，可以在客户端也可以分解为静态页面和页面交互两个部分。</p>
        <h6><b>3.减轻服务器压力</b></h6>
        <p class="bg-info">服务器只用出数据就可以，不用管展示逻辑和页面合成，吞吐能力会提高几倍；</p>
        <h6><b>4.共用一套后端程序代码</b></h6>
        <p class="bg-info">不用修改后端程序代码就可以同时用于Web界面、手机、平板等多种客户端；</p>
        <h4><b>缺点</b></h4>
        <h6><b>1.SEO难度较高</b></h6>
        <p class="bg-info">由于所有的内容都在一个页面中动态替换显示，所以在SEO上其有着天然的弱势，所以如果你的站点对SEO很看重，且要用单页应用，那么就做些静态页面给搜索引擎用吧。</p>
        <h6><b>2.前进、后退管理</b></h6>
        <p class="bg-info">由于单页Web应用在一个页面中显示所有的内容，所以不能使用浏览器的前进后退功能，所有的页面切换需要自己建立堆栈管理，当然此问题也有解决方案，比如利用URI中的散列+iframe实现。</p>
        <h6><b>3.初次加载耗时多</b></h6>
        <p class="bg-info">为实现单页Web应用功能及显示效果，需要在加载页面的时候将JavaScript、CSS统一加载，部分页面可以在需要的时候加载。所以必须对JavaScript及CSS代码进行合并压缩处理，如果使用第三方库，建议使用一些大公司的CDN，因此带宽的消耗是必然的。</p>
        <h4><b>解决</b></h4>
        <h6><b>SEO难度较高</b></h6>
        <p class="bg-info">一般用于内部网，所以SEO显得不是那么重要</p>
        <h6><b>前进、后退管理</b></h6>
        <p class="bg-info">angularJS路由</p>
        <h6><b>初次加载耗时多</b></h6>
        <p class="bg-info">requireJs异步加载资源</p>
        <h6><b>最终，发展的问题还是要靠发展来解决</b></h6>
    </div>
</div>
<div class="panel panel-info">
    <div class="panel-heading">JS库</div>
    <div class="panel-body">
        <p class="bg-info"><code>jquery</code>JS库</p>
        <p class="bg-info"><code>requireJs</code>遵守AMD规范的JS加载器</p>
        <p class="bg-info"><code>angularJs</code>前端MVC框架</p>
        <p class="bg-info"><code>bootstrap</code>HTML和CSS框架</p>
        <p class="bg-info"><code>angular-bootstrap</code>基于bootstrap样式的angularJs指令ui库</p>
        <p class="bg-info"><code>angular-file-upload</code>基于angularJs的文件上传插件</p>
        <p class="bg-info"><code>umeditor</code>百度HTML编辑器</p>
        <p class="bg-info"><code>echarts</code>基于ZRender的百度图标库</p>
        <p class="bg-info"><code>w5c</code>基于angularJs的验证插件，主要是借鉴，未引用</p>
    </div>
</div>
<div class="panel panel-info">
    <div class="panel-heading">原则</div>
    <div class="panel-body">
        <p class="bg-info">前后端完全分离，前端显示数据，后端传纯JSON数据</p>
        <p class="bg-info">内网的程序用HTML5的新特性</p>
        <p class="bg-info">要的不是酷炫的主题和效果，以良好的交互设计到达高操作效率才是硬道理</p>
        <p class="bg-info">不影响交互的前提下，尽量用JS库已提供的指令和UI，不必再封装</p>
        <p class="bg-info">资源仅在需要时菜加载，如切换视图时才加载需要控制器和API服务等</p>
    </div>
</div>
<div class="panel panel-info">
    <div class="panel-heading">约定</div>
    <div class="panel-body">
        <p class="bg-info">数据格式采用JSON</p>
        <p class="bg-info">文件命名方式xxx-xxx.xxx，不采用驼峰原则</p>
        <p class="bg-info">
            根据模块划分，每个业务模块只需要定义api.js，module.js，route.js
        <br>原则上每个业务模块不需额外定义服务、指令、过滤器等
        <br>子业务模块根据方法命名视图、控制器、模型，参看文件目录membership/member
        </p>
        <p class="bg-info">控制器命名采用路径用点号隔开，保证无冲突membership.member.search</p>
    </div>
</div>
<div class="panel panel-info">
    <div class="panel-heading">文件目录</div>
    <div class="panel-body">
        <p class="bg-info">
            <pre>
application             -- APP基础模块
    error               -- APP错误模块 404.html 500.html no-permission.html
    home                -- APP首页子模块路径 home.html home.js
    library             -- JS库路径
    resource            -- 资源路径
        image           -- 图片
        style           -- css样式
    sign                -- APP签名模块 sign.html登入页面 sign-in.js sign-out.js
    api.js              -- APP基础API 签名API
    controller.js       -- APP主控制器
    directive.js        -- APP基础指令模块
    filter.js           -- APP基础过滤器模块
    index.html          -- APP引导页
    interceptor.js      -- APP拦截器
    main.js             -- APP引导JS JS入口
    module.js           -- APP模块定义
    route.js            -- APP路由定义
    service.js          -- APP基础服务模块
membership              -- 会员模块 （参考模块）
    resource            -- 会员模块资源路径（可选）
    home                -- 会员首页模块（要不要首页看具体业务）
    member              -- 会员子模块 根据方法命名视图、控制器、模型
        manage.html     -- 会员管理视图
        manage.js       -- 会员管理控制器
        search.json     -- 会员搜索模型
        ...             -- 更多会员操作
    ...                 -- 更多子模块
    api.js              -- 会员模块API
    module.js           -- 会员模块定义
    route.js            -- 会员模块路由定义
...                     -- 更多业务模块
            </pre>
        </p>
    </div>
</div>
<div class="panel panel-info">
    <div class="panel-heading">架构</div>
    <div class="panel-body">
        <h6><b>config服务</b></h6>
        <p class="bg-info">
            <code>setModuleConfig:function(module,routeConfig)</code>每个业务模块的模块载入和路由配置
        </p>
        <h6><b>security服务</b></h6>
        <p class="bg-info">
            <code>hasPermission:function(permission)</code>判断是否有权限，根据这个服务可以动态控制每个页面的每个按钮的显示（可以做成一个指令）<br>
            <code>getPermission:function(permission)</code>获取权限数据<br>
            <code>getApplicationConfig:function()</code>系统登陆后，别的业务模块可以从这里获得系统配置数据和权限数据<br>
        </p>
        <h6><b>loading服务</b></h6>
        <p class="bg-info">每当有http请求时都有loading显示，配合<code>loadingInterceptor</code>拦截器完成</p>
        <h6><b>message服务</b></h6>
        <p class="bg-info">
            把信息集中显示，信息分为keepMessage（一直会追加不会清除）和transientMessage。每种都有success、info、warning、danger四种类型
            <code>success:function(msg,isKeep)</code><br>
            <code>info:function(msg,isKeep)</code><br>
            <code>warning:function(msg,isKeep)</code><br>
            <code>danger:function(msg,isKeep)</code><br>
        </p>
        <h6><b>interceptor拦截器</b></h6>
        <p class="bg-info">
            <code>loadingInterceptor</code>拦截器，控制全局loading的显示<br>
            <code>errorInterceptor</code>拦截器，一旦服务端返回错误的信息直接通过message服务显示，也就是响应的数据都是成功的数据
        </p>
        <h6><b>location服务</b></h6>
        <p class="bg-info">
            切换页面定义了两个动画go和back，用指令<code>location-go</code>和<code>location-back</code>，指令底层用了location服务<br>
            <code>&lt;a href="javascript:void(0);" location-go="<span>&#123;&#123;</span>path<span>&#125;&#125;</span>"&gt;GO&lt;a/&gt;</code><br>
            <code>&lt;a href="javascript:void(0);" location-back&gt;BACK&lt;a/&gt;</code>
        </p>
        <h6><b>http服务</b></h6>
        <p class="bg-info">
            封装angularJs的$http服务，定义API和后台数据的交互通过以下的方法，配合angularJs的数据绑定特性，调用API就可以像同步代码那样写异步代码<br>
            <code>get:function(config,callback)</code>get方式提交，config参数为$http服务原生参数，callback参数（可选）默认为success回调，如果是object则为：<br>
            <span style="padding-left: 1em;">success:function(value,status,headers,config)成功回调，value为成功响应数据 </span><br>
            <span style="padding-left: 1em;">progress:function(updateEvent)读取文件上传进度 </span><br>
            <span style="padding-left: 1em;">xhr:function(xhr)可以对最底层的XMLHttpRequest请求对象，做任意底层操作 </span><br>
            <span style="padding-left: 1em;">error:function(data,status,headers,config)服务异常回调，下同 </span><br>
            <code>post:function(config,callback)</code>post方式提交<br>
            <code>postForm:function(config,callback)</code>post表单方式提交
        </p>
        <h6><b>UI扩展</b></h6>
        <p class="bg-info">
            基本UI指令（accordion、datepicker、modal、tabs、tooltip、pagination等）用angular-bootstrap提供的指令库，或者用bootstrap提供的JS插件<br>
            文件上传用angular-file-upload提供的指令库<br>
            <code>umeditor</code>指令，百度umeditor编辑器<br>
            <code>echarts</code>指令，百度echarts图表
        </p>
        <h6><b>validator服务</b></h6>
        <p class="bg-info">
            配合以下指令完成业务的统一验证，配合message服务完成统一错误显示<br>
            <code>validateSubmit</code>指令，验证服务的入口，表单提交函数用于此指令<br>
            <code>repeat</code>指令，两次输入是否相同<br>
            <code>unique</code>指令，验证唯一性<br>
            <code>validators</code>指令，自定义验证
            <pre>
HTML
&lt;div ng-controller="membership.member.edit">
    &lt;form name="membership_member_edit_form" class="form-horizontal" validate-submit="edit()" role="form" novalidate&gt;
        &lt;div class="form-group ">
            &lt;label class="col-sm-1 control-label">昵称</label>
            &lt;div class="col-sm-3">
                &lt;input name="name" ng-model="member.name" type="text" class="form-control input-sm" placeholder="昵称"
                validators="{lengthCheck:checkFunction}" required unique="{url:'/membership/member/exists.json'}" >
            &lt;/div>
        &lt;/div>
        ......
    &lt;/form&gt;
&lt;/div>

JS
define(["membership/module","membership/api"], function (membershipModule) {
    "use strict";
    membershipModule.controller("membership.member.edit",["$scope","membership.api","validator","location",
        function($scope,membershipApi,validator,location){
            validator.addRule("membership_member_edit_form",{
                name:{
                    required:"昵称必输",
                    lengthCheck:"昵称长度必须大于4"
                },
                phone:{
                    required:"手机必输"
                }
                ......
            });
            //自定义验证
            $scope.checkFunction = function(scope,model){
                return model.$viewValue.length > 4;
            }
            //通过membershipApi.memberGet（底层通过http服务发送请求）获取会员数据，因为不需要success回调，所以直接用同步方式写
            $scope.member = membershipApi.memberGet({id:1});
            $scope.edit = function(){
                membershipApi.memberEdit($scope.member,
                    function(data){
                        //编辑成功，然后跳转
                        location.go(successPath);
                    }
                );
            };
        }
    ]);
});
            </pre>
        </p>
    </div>
</div>
</div>
