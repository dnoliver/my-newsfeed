<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script id="ModalShareTemplate" type="text/template">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">Share this comment in facebook or tweeter</h4>
      </div>
      <div class="modal-body">
        <div class="well">
          <span data-selector="owner" class="label label-default"></span>
          <em data-selector="text"></em>
        </div>
        <br>
        <div data-actions>
          <div class="facebook-share">
            <span data-action="facebook" class="label label-primary">facebook</span>
          </div>
          <div>
            <a class="twitter-share-button"
              data-text=""
              data-count="none"
              data-size="large"
              data-selector="TweeterButton"
              href="https://twitter.com/share">
              Tweet
            </a>
          </div>
        </div>
      </div>
    </div>
  </div>
</script>
