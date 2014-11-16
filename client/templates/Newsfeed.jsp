<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<script id="NewsFeedTemplate" type="text/template">
<div class="page-header">
  <h1>
    <span data-model="subject"></span>
    <small data-model="career">career</small>
    <span class="caret pull-right" data-action="controls" data-selector="controls"></span>
  </h1>
</div>  
<div data-selector="form"></div>
<div data-selector="body">
  <div data-selector="posts"></div>
</div>
</script>