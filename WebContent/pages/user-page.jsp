<%@ page language="java" 
	contentType="text/html; utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.Enumeration" %>
<%@ page import="pl.grabber.objects.User" %>
    
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
    <!-- ./Navigation -->
    
    <!-- Page Content -->
    <div class="container">

        <!-- Page Heading -->
        <div class="row">
            <div class="col-sm-12">
                <h1 class="page-header">User Page
                    <small>edit</small>
                </h1>
            </div>
        </div>
        <!-- /.row -->

  		<!-- user content row -->
        <div class="row">
            <div class="col-sm-12">
               <h2>Options</h2>    
            </div>
            <div class="col-sm-12">
            Session attributes<br>
            <%
            Enumeration keys = session.getAttributeNames();
            while (keys.hasMoreElements())
            {
              String key = (String)keys.nextElement();
              out.println(key + ": " + session.getValue(key) + "<br>");
            }
            
            
            User loggedUser =(User) session.getAttribute("loggedUser");
            if(loggedUser!=null){
            	%>
            	<ul>
	            	<li>Login : <%=loggedUser.getUsername() %></li>
	            	<li>Email: <%=loggedUser.getEmail() %></li>
	            	<li>Sms notification : <%=loggedUser.isSmsNotification() %></li>
	            	<li>Email notification : <%=loggedUser.isEmailNotification()%></li>
	            	<li>Last login: <%=loggedUser.getLastLoginDate()%></li>
            	</ul>
            	<%
            }
            %>
            </div>
        </div>
        <hr>

        <!-- Footer -->
        <jsp:include page="../pages/footer.jsp"/>
        

    </div>
    <!-- /.container -->


	<!-- BottomLibs -->
    	<jsp:include page="bottomLibs.jsp"/>
    <!-- ./BottomLibs-->
    

</body>
</html>
