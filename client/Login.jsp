<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>

<!DOCTYPE html>
<html>
  <jsp:include page="./Header.jsp" />
  <body>
    <div class="container-fluid">
      <div class="page-header">
        <h1>Login Page <small>access page for ubp forums</small></h1>
      </div>
      <div class="row">
        <div class="col-md-6 col-md-offset-3 well">
          <form class="form-horizontal" role="form" method="GET" action="/ubp/command/login">
            <div class="form-group">
              <label for="user" class="col-sm-2 control-label">User</label>
              <div class="col-sm-10">
                <input name="user" type="text" class="form-control" id="email" placeholder="User">
              </div>
            </div>
            <div class="form-group">
              <label for="password" class="col-sm-2 control-label">Password</label>
              <div class="col-sm-10">
                <input name="password" type="password" class="form-control" id="password" placeholder="Password">
              </div>
            </div>
            <div class="form-group">
              <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Login</button>
              </div>
            </div>
          </form>
        </div>   
      </div>
    </div>
  </body>
</html>
