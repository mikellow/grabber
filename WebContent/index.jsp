<%@ page language="java" contentType="text/html; utf-8"
    pageEncoding="utf-8"%>
<%@ page import="pl.grabber.objects.User" %>
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
    <!-- ./Navigation -->
    

    <!-- Page Content -->
    <div class="container">

        <!-- Page Heading -->
        <div class="row">
            <div class="col-sm-12">
                <h1 class="page-header">Main page
                    <small>all categories</small>
                </h1>
            </div>
        </div>
        <!-- /.row -->
        
        <div class="row">
            <div class="col-sm-12">
            <%
            String userName = "";
            if(null == session.getAttribute("loggedUser")){
            	
            }else{
            	User loggedUser = (User) session.getAttribute("loggedUser");
            	userName = loggedUser.getUsername();
            }
            
            %>
                    <h2> Hello <%=userName %></h2>
            </div>
        </div>

        <hr>

		<jsp:include page="pages/footer.jsp"/>

    </div>
    <!-- /.container -->


	<!-- BottomLibs -->
    	<jsp:include page="pages/bottomLibs.jsp"/>
    <!-- ./BottomLibs-->

</body>

</html>
