<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html>
  <head>
    <title>Forums</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">

    <link rel="stylesheet" href="./bower_components/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="./style/main.css">

    <script src="./bower_components/jquery/dist/jquery.min.js"></script>
    <script src="./bower_components/underscore/underscore-min.js"></script>
    <script src="./bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="./bower_components/backbone/backbone.js"></script>
    <script src="./node_modules/closure-library/closure/goog/base.js"></script>
    <script src="./deps.js"></script>
    <script>goog.require('app.Application');</script>
  </head>
  <body>
    <jsp:include page="./templates/FacebookAPI.jsp" />
    <jsp:include page="./templates/TweeterAPI.jsp" />
    <jsp:include page="./templates/Application.jsp" />
    <jsp:include page="./templates/Newsfeed.jsp" />
    <jsp:include page="./templates/Post.jsp" />
    <jsp:include page="./templates/Comment.jsp" />
    <jsp:include page="./templates/ModalShare.jsp" />
    <jsp:include page="./templates/ModalControls.jsp" />
    <jsp:include page="./templates/Tabs.jsp" />
    <jsp:include page="./templates/PostForm.jsp" />
    <script>
    $(document).ready(function(){
      APP = app.Application.Controller.getInstance();
      APP.setElement($("#ApplicationUiRegion"));
      APP.render();
    });
    </script>
    <div id="ApplicationUiRegion"></div>
  </body>
</html>