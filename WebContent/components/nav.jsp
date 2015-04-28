<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="pl.grabber.managers.ContentManager,java.util.List" %>
<!-- Navigation -->
<nav class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="/GrabberPro/index.jsp">Grabber</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse" aria-expanded="false" style="height: 1px;">
          <ul class="nav navbar-nav">
            <li class="active"><a href="/GrabberPro/index.jsp">Home</a></li>
            <!--  <li><a href="/GrabberPro/browse.jsp">Browse Categories</a></li>  -->
                   
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Browse Categories<span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
              <%
            	ContentManager contentManager = ContentManager.getInstance();
            	List<String> categories = contentManager.loadCategoriesAll();
            	for(String category : categories){
	            	%>
		            	<li><a href="showCategory?cat=<%=category %>"><%=category %></a></li>
		            <%		
	            }
            	%>
                <li class="divider"></li>
                <li class="dropdown-header">Or just</li>
                <li><a href="showCategory">show all</a></li>
              </ul>
            </li>
             
          </ul>
          
          <ul class="nav navbar-nav navbar-right">
	          <%
	          if (null == session.getAttribute("loggedUser"))
	          {
	            // the user *does not* have a valid session; handle this however you need to.
	        	  %>
	        	  	<!-- 
	        	  	<li class="active"><a href="./">Register <span class="sr-only">(current)</span></a></li>
	        	  	 -->
	        	  	<li ><a href="register">Register</a></li>
	        	  	<li><a href="login">Login</a></li>
	        	  <%
	          }
	          else
	          {
	            // the user *does* have a valid session.
	            // do whatever you need to for logged in users.
	           //String username = (String)session.getValue("loggedUser");
	            		
	            		%>
	            		<li class="active"><a href="showUserPage">UserPanel <span class="sr-only">(current)</span></a></li>
	        	  		<li><a href="/GrabberPro/LoginController?logout=true">Logout</a></li>
	            		<%
	          }
	          %>
          
            
            
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
<!-- ./Navigation -->
