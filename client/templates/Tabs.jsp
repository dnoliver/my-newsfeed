<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script id="TabsTemplate" type="text/template">
  <div class="page-header">
    <h1><span data-selector="career">All Careers</span> <small>ubp</small></h1>
  </div>
  <div class="dropdown">
    <button 
      class="btn btn-default dropdown-toggle" 
      type="button" 
      id="CareerMenu" 
      data-toggle="dropdown">
      Change Career
      <span class="caret"></span>
    </button>
    <ul class="dropdown-menu" role="menu" aria-labelledby="CareerMenu" data-selector="menu">
      <li role="presentation"><a role="menuitem" tabindex="-1" data-action="changeCareer" data-target="All Careers">All Careers</a></li>
    </ul>
  </div>
  <br>
  <ul class="nav nav-pills" role="tablist" data-selector="tabs"></ul>
  <br>
  <div class="tab-content" data-selector="panels"></div>
</script>