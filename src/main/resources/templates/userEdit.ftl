<#import "parts/common.ftl" as c>

<@c.page>
User Editor

<form action="/user" method="post">
    <input type="text" name="username" value="${user.username}"/>
    <#list roles as role>
        <div>
            <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("chcked", "")}>${role}</label>
        </div>
    </#list>
    <input type="hidden" name="userId" value="${user.id}"/>
    <input type="hidden" value="${_csrf.token}" name="_csrf"/>
    <button type="submit">Save</button>
</form>

</@c.page>