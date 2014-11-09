<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script id="PostTemplate" type="text/template">
  <div class="panel-heading">
    Posted by <span data-model="owner" class="label label-primary"></span> on <span data-model="ts"></span>
    <span class="caret pull-right" data-action="controls" data-selector="controls"></span>
  </div>
  <div class="panel-body">
    <div data-selector="media" class="center-block"></div>
    <div><em data-selector="text"></em></div>
    <div data-selector="info"> 
      <span data-model="likes"></span> likes
      <a data-model="attachment" data-action="attachment"></a>
    </div>
    <div data-selector="actions">
      <a data-action="like" data-selector="like">Like</a>
      <a data-action="share">Share</a>
    </div>
  </div>
  <div class="panel-footer">
    <div data-selector="comments"></div>
    <div data-selector="form"></div>
  </div>
</script>
