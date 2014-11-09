<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script id="ModalControlsTemplate" type="text/template">
<div class="modal-dialog">
  <div class="modal-content">
    <div class="modal-header">
      <h4 class="modal-title">Controls</h4>
    </div>
    <div class="modal-body">
      <div class="well">
        Modify this element
      </div>
      <br>
      <div data-actions>
        <a class="btn btn-default" data-action="cancel" data-selector="cancel">Cancel</a>
        <a class="btn btn-warning" data-action="disable" data-selector="disable">Disable</a>
        <a class="btn btn-success" data-action="enable" data-selector="enable">Enable</a>
        <a class="btn btn-danger" data-action="delete" data-selector="delete">Delete</a>
      </div>
    </div>
  </div>
</div>
</script>