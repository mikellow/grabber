<%@ page language="java" 
	contentType="text/html; utf-8"
    pageEncoding="utf-8"%>

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
                <h1 class="page-header">Login 
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
						        	<form id="login-form" action="login" method="post" role="form" style="">
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
											<input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password">
										</div>
										<div class="form-group">
											<div class="checkbox">
									          <label>
									            <input type="checkbox" name="remember-me" value="true"> Remember me
									          </label>
									        </div>
										</div>
										<div class="form-group">
											<div class="row">
												<div class="col-sm-6 col-sm-offset-3">
													<input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-primary btn-block" value="Login Now">
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
