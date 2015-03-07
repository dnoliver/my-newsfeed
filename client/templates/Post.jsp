<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script id="PostTemplate" type="text/template">
<div class="panel panel-primary" data-selector="panel">
  <div class="panel-heading">
    Posted by <span data-model="owner" class="label label-info"></span> on <span data-model="ts"></span>
  </div>
  <div class="panel-body">
    <div data-selector="media" class="center-block"></div>
    <p data-selector="text"></p>
    <div data-selector="info"> 
      <div>
        <span class="glyphicon glyphicon-thumbs-up"></span>
        <span data-model="likes"></span> 
      </div>
    </div>
    <div data-selector="actions">
      <a data-action="like" data-selector="like">Like</a>
      <a data-action="share">Share</a>
      <a data-action="attachment">Attachment</a>
      <a data-action="enable" data-category="professor">Enable</a>
      <a data-action="disable" data-category="professor">Disable</a>
      <a data-action="remove" data-category="professor">Remove</a>
    </div>
  </div>
  <div data-selector="comments" class="list-group"></div>
  <div class="panel-footer" data-selector="footer">
    <form enctype="multipart/form-data" data-selector="form">
      <div class="form-group">
        <input type="text" class="form-control" placeholder="Comment" data-selector="input" required>
      </div>
      <div class="form-group">
        <input name="file" type="file" data-selector="attachment">
      </div>
      <button type="button" class="btn btn-default" data-action="publish">Publish</button>
    </form>
  </div>
</div>
</script>
