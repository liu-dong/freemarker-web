<!DOCTYPE html>
<html lang="en">
<head>
    <title>SpringBoot + Freemarker</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <style>
        html {
            font-size: 14px;
            font-weight: 400;
        }
        .exp {
            font-size: 12px;
            color: lightgray;
        }
    </style>
</head>
<body>
<h1>Hello World</h1><br>
<p>当前时间：${.now?string("yyyy-MM-dd HH:mm:ss.sss")}</p>
<dl>
    <dt>list长度：<span class="exp">${list?size}</span></dt>
    <dt>列表</dt>
    <#list list as item>
        <dd>${item }, 索引：${item_index }，hasNext：${item_has_next}</dd>
    </#list>
    <dt>数字遍历</dt>
    <#list 1..13 as item>
        <dd>数字${item}</dd>
    </#list>
    <dt>map</dt>
    <#list map?keys as key>
        <dd>键：${key}, 值：${map[key] }</dd>
    </#list>
</dl>
</body>
</html>