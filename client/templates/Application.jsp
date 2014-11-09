<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script id="ApplicationTemplate" type="text/template">
  <div class="container-fluid">
    <nav class="navbar navbar-default" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#_navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Universidad Blas Pascal</a>
        </div>
        <div class="collapse navbar-collapse" id="_navbar">
          <ul class="nav navbar-nav">
            <li><a data-selector="UserUiRegion"></a></li>
            <li><a data-action="update">Update</a></li>
          </ul>
          <form class="navbar-form navbar-left" method="POST" action="/ubp/command/logout">
            <button type="submit" class="btn btn-default">Logout</button>
          </form>
        </div>
      </div>
    </nav>
    <div class="row">
      <div class="col-md-8 col-md-offset-1">
        <div data-selector="TabUiRegion"></div>
      </div>
      <div class="col-md-3">
        <div class="row">
          <h1><span class="glyphicon glyphicon-user"></span> Users</h1>
          <div data-selector="UsersUiRegion"></div>
        </div>
      </div>
    </div>
  </div>

  <div data-selector="ShareWidgetUiRegion"></div>
  <div data-selector="ControlsWidgetUiRegion"></div>
</script>
