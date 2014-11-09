<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script id="PostFormTemplate" type="text/template">
<div class="form-group">
  <div class="input-group">
    <input type="text" 
      class="form-control" 
      placeholder="Send a Post" 
      data-selector="input"
      maxlength="140"
      required>
    </input>
    <a class="input-group-addon" data-action="post">Post</a>
  </div>
</div>
<form enctype="multipart/form-data" data-selector="form">
  <input name="file" type="file" data-selector="file">
</form>
</script>
