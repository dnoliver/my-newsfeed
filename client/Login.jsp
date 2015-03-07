<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
  <head>
    <title>Login</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">

    <link rel="stylesheet" href="./bower_components/bootstrap/dist/css/bootstrap.min.css">
    
    <script src="./bower_components/jquery/dist/jquery.min.js"></script>
    <script src="./bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
  </head>
  <body>
    <div class="container-fluid">
      <div class="row">
        <div class="col-md-6 col-md-offset-3 ">
          <div class="page-header">
            <h1>Login Page <small>access page for UBP forums</small></h1>
          </div>
          <div class="well">
            <form class="form-horizontal" role="form" method="POST" action="/ubp/account">
              <div class="form-group">
                <label for="user" class="col-sm-2 control-label">User</label>
                <div class="col-sm-10">
                  <input name="user" type="text" class="form-control" placeholder="User" required>
                </div>
              </div>
              <div class="form-group">
                <label for="password" class="col-sm-2 control-label">Password</label>
                <div class="col-sm-10">
                  <input name="password" type="password" class="form-control" placeholder="Password" required>
                </div>
              </div>
              <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                  <button id="login_button" type="submit" class="btn btn-primary">Login</button>
                </div>
              </div>
            </form> 
          </div>
        </div>   
      </div>
    </div>
    <script>
      $(document).ready(function(){
        $("form").submit(function(e){
          e.preventDefault();
          var url = '/ubp/account?' + $(this).serialize();
          
          $.ajax({
            method: 'POST',
            url: url,
            success: function () {
              document.location = '/ubp/Main.jsp';
            },
            error: function(){
              alert('invalid user account');
            }
          });
          
          return false;
        });
      });
    </script>
  </body>
</html>
