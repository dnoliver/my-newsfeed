<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script id="ModalRankingTemplate" type="text/template">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">Ranking</h4>
      </div>
      <div class="modal-body">
        <table class="table">
          <thead>
            <tr>
              <td>Date</td>
              <td>Owner</td>
              <td>Comments</td>
              <td>Facebook Shares</td>
              <td>Tweeter Shares</td>
              <td>Text</td>
            </tr>
          </thead>
          <tbody data-selector="table"></tbody>
        </table>

        <div class="input-group">
          <input type="text" class="form-control" placeholder="Carrera" data-selector="career">
        </div>
        <br/>
        <div class="input-group">
          <input type="text" class="form-control" placeholder="Materia" data-selector="subject">
        </div>
        <br/>
        <div class="input-group">
          <input type="text" class="form-control" placeholder="Fecha desde" data-selector="from" disabled>
        </div>
        <br/>
        <div class="input-group">
          <input type="text" class="form-control" placeholder="Fecha hasta" data-selector="to" disabled>
        </div>
        <br/>
        <a class="btn btn-primary" data-action="update">Update</a>
      </div>
    </div>
  </div>
</script>
