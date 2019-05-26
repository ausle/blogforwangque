






<#macro pager url p>


        <#if p??>

            <#if url?index_of("?")!=-1>
                <#local cURL=(url+"&pageNo=")/>
                <#else>
                <#local cURL=(url+"?pageNo=")/>
            </#if>

            <ul class="pagination">
                <#--page.number页码，页码从0开始-->
                <#assign pageNo=p.number+1 />
                <#--页数-->
                <#assign pageCount=p.totalPages/>

                <#if (pageNo>1)>
                    <li><a href="${cURL}${pageNo - 1}" pageNo="${pageNo - 1}" class="prev">上一页</a></li>
                <#else>
                    <li class="disabled"><span>上一页</span></li>
                </#if>


                <#--没有数据或页数只有一页，当作一页处理-->
                <#if (pageCount < 1)>
                    <@pagelink 1,1,cURL/>
                <#else>
                    <#list 1..pageCount as index>
                        <@pagelink pageNo,index,cURL></@pagelink>
                    </#list>
                </#if>


                <#if (pageNo < pageCount)>
                    <li><a href="${cURL}${pageNo + 1}" pageNo="${pageNo + 1}" class="next">下一页</a></li>
                <#else>
                    <li class="disabled"><span>下一页</span></li>
                </#if>


            </ul>


        </#if>

</#macro>


<#macro pagelink pageNo index url>

    <#if pageNo==index>
        <li class="active">
            <span>${index}</span>
        </li>
    <#else>
        <li><a href="${url}${index}">${index}</a></li>
    </#if>

</#macro>