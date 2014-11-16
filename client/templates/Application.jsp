<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script id="ApplicationTemplate" type="text/template">
<nav class="navbar navbar-inverse" role="navigation">
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
        <li><a data-action="update">update</a></li>
      </ul>
      <form class="navbar-form navbar-left" method="POST" action="/ubp/command/logout">
        <button type="submit" class="btn btn-default">Logout</button>
      </form>
    </div>
  </div>
</nav>
<div data-selector="ShareWidgetUiRegion"></div>
<div data-selector="ControlsWidgetUiRegion"></div>
<div class="container-fluid">
  <div class="row">
    <div class="col-md-9" data-selector="TabUiRegion"></div>
    <div class="col-md-3">
      <div class="panel panel-primary">
        <div class="panel-heading">Latest Activity</div>
        <div class="panel-body" data-selector="FeedUiRegion"></div>
      </div>
    </div>
  </div>
</div>
</script>
