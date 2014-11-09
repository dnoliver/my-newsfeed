<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script id="NavbarTemplate" type="text/template">
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
          <!--li><a data-action="update">Update</a></li-->
        </ul>
        <form class="navbar-form navbar-left" action="/ubp/command/logout">
          <button type="submit" class="btn btn-default">Logout</button>
        </form>
      </div>
    </div>
  </nav>
</script>
