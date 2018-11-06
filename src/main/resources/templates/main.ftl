<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <div>
        <form method="post" enctype="multipart/form-data">
            <input type="text" name="nameNews" placeholder="Название новости"/>
            <input type="text" name="partTextNews" placeholder="Введите короткую новость"/>
            <input type="text" name="textNews" placeholder="Введите новость"/>
            <input type="text" name="tagNews" placeholder="Тэг"/>
            <input type="file" name="file"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>

            <button type="submit">Добавить</button>
        </form>
    </div>
    <div>Список новостей</div>
    <form method="get" action="/main">
        <input type="text" name="filter" value="${filter?ifExists}">
        <button type="submit">Найти</button>
    </form>
    <#list news as news1>
    <div>
        <b>${news1.id}</b>
        <b>${news1.nameNews}</b>
        <span>${news1.partTextNews}</span>
        <span>${news1.textNews}</span>
        <i>${news1.tagNews}</i>
        <strong>${news1.author.username}</strong>
        <div>
        <#if news1.filename??>
            <img src="/img/${news1.filename}">
        </#if>
        </div>
    </div>
    <#else>
    Нет новостей
    </#list>
</@c.page>