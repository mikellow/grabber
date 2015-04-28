<%@ page 
	language="java" 
	contentType="text/html; utf-8"
    pageEncoding="utf-8"
    import="java.util.Iterator,
    		java.util.ArrayList,
    		pl.grabber.objects.Article"
    %>
  
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
    <div class="container-fluid maxWidth">

        <!-- Page Heading -->
        <div class="row">
            <div class="col-sm-12">
                <h1 class="page-header">
                	cat : 
                	${requestScope['category']}
                
                	${requestScope['articles'].size()}
                    <small><%= new java.util.Date() %></small>
                </h1>
            </div>
        </div>
        <!-- /.row -->

        <!-- category row -->
        <div class="row category" id="category1">
            <div class="col-sm-12">
                <!-- category-container -->
                <div class="container-fluid category-container">
                    
                    <%
                    //String name = request.getParameter( "username" );
                    
                    ArrayList<Article> articles = (ArrayList<Article>) request.getAttribute("articles");
                    Iterator articleIt = articles.iterator();
                    int articlesPerRow = 3;
                    int counter= 0;
                    
            		while(articleIt.hasNext()){
            			Article article = (Article)articleIt.next();
            			
            			if(counter%articlesPerRow==0){
            				%><div class="row"><%
            			}
            			%>
            			<div class="col-sm-4">
            				<!--   <a href="<%=article.getSource() %>" class="article-link"> -->
            				<a href="showPage?id=<%=article.getId() %>" class="article-link">
                                    <!--  <img class="img-responsive img-rounded" src="750x450.gif" alt="">  -->
                                    <img class="img-responsive img-rounded fullWidth" src="grabbedMedia/<%= article.getTitle().hashCode()%>.jpg" alt="">
                                    <div class="desc">
                                        <h2><%=article.getTitle() %></h2>
                                        <p><%=article.getDescription() %>
                                        </p>
                                    </div>
                                </a>    
            			</div>
            			<%
            			counter++;
            			if(counter%articlesPerRow==0 || !articleIt.hasNext()){
            				%></div><%
            			}
            		}
                    %>
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
                    <%
                    int activePageNumber = (Integer) request.getAttribute("page");
                    int lastPageNumber = (Integer) request.getAttribute("lastPage");
                    for(int i=1;i<=lastPageNumber;i++){
                    	%>
                    	<li <%=(i==activePageNumber)?"class=\"active\"" :"" %>>
                        	<a href="categoryPage?page=<%=i%>"><%=i%></a>
                    	</li>
                    		<%
                    }
                    %>
                    
                    <li>
                        <a href="#">&raquo;</a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- /.row -->

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
