<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>freemarker测试</title>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body>

<#--单个页面设置，由于在application里面已经设置全局，这里可以单独设置-->
<#setting classic_compatible=false>
<#--如果不设置classic_compatible=true-->
<#--处理不存在的变量，或者为null的变量-->
<h3>${hello+hello?cap_first+hello?lower_case+hello?upper_case}</h3>

<#--第一个直接解析，第二个原文返回-->
${"<b>bold</b>"}
${"<b>bold</b>"?html}


<h3>${test!"null"}</h3>
<h3>${null!"null"}</h3>

<#--处理空字符串,去空格-->
<h3>${empty?trim}</h3>

<#--处理日期格式-->
<h3>${date?string('yyyy-MM-dd HH:mm:ss')}</h3>
<#--这里是全局日期设置-->
<h3>${date?datetime}</h3>
<h3>${date?date}</h3>
<h3>${date?time}</h3>

<#--处理数值-->
<h3>#{double;m2M2}</h3>
<h3>${int?string.number}</h3>
<h3>${double?int}</h3>
<h3>${double+int}</h3>
<#--根据locale来显示不同的货币，所以不能这样用-->
<h3>${int?string.currency}</h3>
<h3>￥#{int;m2M2}</h3>
<h3>${int?string.percent}</h3>

<#--处理布尔值-->
<h3>${boolean?c}</h3>

<#--if else-->
<#if boolean>
    true...<br/>
</#if>

<#--=和==是一样的-->
<#if empty?trim=="hello world!">
   true...<br/>
<#elseif empty?trim=="">
   empty...<br/>
<#else>
  other...<br/>
</#if>

<#list ["星期一","星期二","星期三","星期四","星期五"] as x>
${x_index +1}.${x} <#if x_has_next>,</#if>
    <#if x = "星期四"><#break></#if>
</#list>

<#list list as x>
${x_index +1}.${x.name} <#if x_has_next>,</#if>

</#list>


<#--map处理-->
<br/>
${map.key1}<br/>





<script src="js/jquery-1.11.3.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>