<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="page" type="org.springframework.data.domain.Page" required="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="current" value="${page.number + 1}" />
<c:set var="begin" value="${(current - page.size/2) > 1 ? current - page.size/2 : 1 }" />
<c:set var="end" value="${(begin + (page.size - 1)) < page.totalPages ? begin + (page.size - 1) : page.totalPages }" />

<c:if test="${page.totalPages > 0 }"> 
<div class="row">
	<ul class="pagination">
		 <c:if test="${page.number > 0 }">
               	<li><a href="?page=1&${searchParams}">&lt;&lt;</a></li>
                <li><a href="?page=${current-1}&${searchParams}">&lt;</a></li>
         </c:if>       
         <c:if test="${not (page.number > 0) }">
                <li class="disabled"><a href="#">&lt;&lt;</a></li>
                <li class="disabled"><a href="#">&lt;</a></li>
		</c:if>
		 
		<c:forEach var="i" begin="${begin}" end="${end}">
            <c:choose>
                <c:when test="${i == current}">
                    <li class="active"><a href="?page=${i}&${searchParams}">${i}</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="?page=${i}&${searchParams}">${i}</a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
	  
	  	 <c:if test="${(page.number + 1) * page.size < page.totalElements }">
               	<li><a href="?page=${current+1}&${searchParams}">&gt;</a></li>
                <li><a href="?page=${page.totalPages}&${searchParams}">&gt;&gt;</a></li>
         </c:if>
	  	 <c:if test="${not ((page.number + 1) * page.size < page.totalElements)}">
                <li class="disabled"><a href="#">&gt;</a></li>
                <li class="disabled"><a href="#">&gt;&gt;</a></li>
		</c:if>
	</ul>
</div>
</c:if>
