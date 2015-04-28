<%@ page 
	language="java" 
	contentType="text/html; utf-8"
    pageEncoding="utf-8"
    import="java.util.Iterator,
    		java.util.ArrayList,
    		pl.grabber.objects.Article,
    		pl.grabber.objects.Page"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<!DOCTYPE html>
<html lang="en">

<head>
	<!-- Header -->
    <jsp:include page="header.jsp"/>
    <!-- ./Header -->
	
</head>

<body>

       <!-- Navigation -->
    	<jsp:include page="../components/nav.jsp"/>
    <!-- ./Navigation -

    
     <!-- Page Content -->
    <div class="container">
        <!-- Page Heading -->
        <%
        Page thisPage = (Page) request.getAttribute("page");
        %>
        <div class="row">
            <div class="col-sm-12">
                <h1 class="page-header">
                	Category : <%=  thisPage.getCategory().getName() %>
                    <small><%= new java.util.Date() %></small>
                </h1>
            </div>
        </div>
        <!-- /.row -->

<%
	ArrayList<Article> articles = (ArrayList<Article>) request.getAttribute("articles");
	if(articles.size() == 0){
	%>
	<div class="row category" id="category1">
            <div class="col-sm-6 col-sm-offset-3">
	            <div class="alert alert-danger" role="alert">
					<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
														<span class="sr-only">Error:</span>
														no articles for selected category
				</div>
            </div>
    </div>
	<%
	}else{
        Iterator articleIt = articles.iterator();
		while(articleIt.hasNext()){
			Article article = (Article)articleIt.next();
			%>
			<div class="row">
	            <div class="col-sm-3">
	            	<img class="img-responsive img-rounded fullWidth" src="grabbedMedia/<%= article.getTitle().hashCode()%>.jpg" alt="">
	            </div>
	            <div class="col-sm-6">
	            	<a href="showPage?id=<%=article.getId() %>" class="article-link">
					<div class="desc">
						<h2><%=article.getTitle() %></h2>
						<p><%=article.getDescription() %></p>
					</div>
					</a>
	            </div>
	            	
	        </div>
			<%
		}
		%>
        <!-- /.Page Content -->


        <!-- Pagination -->
        <div class="row text-center">
            <div class="col-lg-12">
                <ul class="pagination">
                    <li>
                        <a href="#">&laquo;</a>
                    </li>
                    
                    <li>
                        <a href="#">&raquo;</a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- /.row -->
		<%
	}

%>
        

        <hr>

        <!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12">
                    <p>Copyright &copy; Your Website 2014</p>
                </div>
            </div>
            <!-- /.row -->
        </footer>

    </div>
    <!-- /.container -->

	<!-- BottomLibs -->
    	<jsp:include page="bottomLibs.jsp"/>
    <!-- ./BottomLibs-->

</body>

</html>
