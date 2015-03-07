<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<script id="NewsFeedTemplate" type="text/template">
<div class="page-header">
  <h1>
    <span data-model="subject"></span>
    <small data-model="career">career</small>
  </h1>
  <div data-selector="actions">
    <a data-action="enable" data-category="professor">Enable</a>
    <a data-action="disable" data-category="professor">Disable</a>
  </div>  
</div>
<form enctype="multipart/form-data" data-selector="form" class="well">
  <div class="form-group">
    <input type="text" class="form-control" placeholder="Comment" data-selector="input" required>
  </div>
  <div class="form-group">
    <input name="file" type="file" data-selector="attachment">
  </div>
  <button type="button" class="btn btn-default" data-action="publish">Publish</button>
</form>
<div data-selector="posts"></div>
</script>