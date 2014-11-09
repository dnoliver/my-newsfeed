<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<script id="NewsFeedTemplate" type="text/template">
  <div class="panel-heading">
    <h3 class="panel-title">
      <span class="glyphicon glyphicon-book"></span> 
      <span data-model="subject"></span>
      <span class="caret pull-right" data-action="controls" data-selector="controls"></span>
    </h3>
  </div>
  <div class="panel-body" data-selector="body">
    <div data-selector="form"></div>
    <div data-selector="posts"></div>
  </div>
</script>