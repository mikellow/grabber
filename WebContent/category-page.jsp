<%@ page 
	language="java" 
	contentType="text/html; utf-8"
    pageEncoding="utf-8"
    import="java.util.Iterator,
    		java.util.ArrayList,
    		pl.grabber.objects.Article"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<!DOCTYPE html>
<html lang="en">

<head>
	<!-- Header -->
    <jsp:include page="pages/header.jsp"/>
    <!-- ./Header -->

</head>

<body>

       <!-- Navigation -->
    	<jsp:include page="components/nav.jsp"/>
    <!-- ./Navigation -

    
     <!-- Page Content -->
    <div class="container">

        <!-- Page Heading -->
        <%
        String category = (String) request.getAttribute("category");
        %>
        <div class="row">
            <div class="col-sm-12">
                <h1 class="page-header">
                	Category : <%=  category%>
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
														category doesn't exist
				</div>
            </div>
    </div>
	
	<%
	}else{
		%>
		<!-- category row -->
        <div class="row category" id="category1">
            <div class="col-sm-12">
                <!-- category-container -->
                <div class="container-fluid category-container">
                   <h1><%=articles.size() %></h1>
					</div>
                </div>
                <!-- /.category-container -->
            </div>
        <!-- /.category row -->

        
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
    	<jsp:include page="pages/bottomLibs.jsp"/>
    <!-- ./BottomLibs-->

</body>

</html>
