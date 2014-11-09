<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script id="CommentTemplate" type="text/template">
  <div class="panel-heading">
    Posted by <span data-model="owner" class="label label-primary"></span> on <span data-model="ts"></span>
    <span class="caret pull-right" data-action="controls" data-selector="controls"></span>
  </div>
  <div class="panel-body">
    <div data-selector="media" class="center-block"></div>
    <div><em data-selector="text"></em></div>
    <div data-selector="info">
      <a data-model="attachment" data-action="attachment"></a>
    </div>
  </div>
</script>
