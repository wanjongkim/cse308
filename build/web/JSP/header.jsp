<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <jsp:useBean id="user" class="beans.UserSession" scope="session" />
        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#header-nav-collapsable" aria-expanded="false">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="index">Brand</a>
                </div>
                <div class="collapse navbar-collapse" id="header-nav-collapsable">
                    <form class="navbar-form navbar-left form-inline" method="POST" action="#">
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="Search">
                        </div>
                        <button type="submit" class="btn btn-default">Submit</button>
                    </form>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="#">Movies</a></li>
                        <li><a href="#">Movie news</a></li>
                        <li><a href="#">My Account</a></li>

                        <li>
                        <c:if test="${!user.loggedIn}">
                            <a href="register">Register</a>
                        </c:if>
                        </li>
                        <li>
                        <c:if test="${!user.loggedIn}">
                            <a href="signin">Sign In</a>
                        </c:if>
                        <c:if test="${user.loggedIn}">
                            <a href="signout">Sign Out</a>
                        </c:if>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </body>
</html>
