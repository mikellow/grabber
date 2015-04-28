<%@ page language="java" 
	contentType="text/html; utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<%@ page isELIgnored="false" %> 
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
    <div class="container-fluid">

        <!-- Page Heading -->
        <div class="row">
            <div class="col-sm-12">
                <h1 class="page-header">Register
                    <small>Page</small>
                </h1>
            </div>
        </div>
        <!-- /.row -->

  		<!-- login content row -->
        <div class="row">
			<div class="col-sm-6 col-sm-offset-3">
				<div class="panel panel-login">
					<div class="panel-body">
						<div class="row">
							<div class="col-sm-12">
								<form id="register-form" action="register" method="post" role="form" style="">
								<%  
								// retrieve your list from the request, with casting 
								String[] errors = (String []) request.getAttribute("errors");
								if(errors != null){
									%><div class="alert alert-danger" role="alert"><%
									for(String error : errors) {
										%>
										<p>
											<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
											<span class="sr-only">Error:</span>
										    <%=error %>
									    </p>
									    <%
									}
									%></div><%
								}
								// print the information about every category of the list
								
								%>
									<div class="form-group">
										<input type="text" name="username" id="username" tabindex="1" class="form-control" placeholder="Username" value="${param.username}">
									</div>
									<div class="form-group">
										<input type="email" name="email" id="email" tabindex="1" class="form-control" placeholder="Email Address" value="${param.email}">
									</div>
									<div class="form-group">
										<input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password">
									</div>
									<div class="form-group">
										<input type="password" name="confirm-password" id="confirm-password" tabindex="2" class="form-control" placeholder="Confirm Password">
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3">
												<input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-primary btn-block" value="Register Now">
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
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
