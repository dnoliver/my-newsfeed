<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!doctype html>
<html>
  <jsp:include page="./Header.jsp" />
  <body>
    <script src="./deps.js"></script>
    <script src="./bootstrap.js"></script>
    <jsp:include page="./templates/FacebookAPI.jsp" />
    <jsp:include page="./templates/TweeterAPI.jsp" />
    <jsp:include page="./templates/Application.jsp" />
    <jsp:include page="./templates/Newsfeed.jsp" />
    <jsp:include page="./templates/Post.jsp" />
    <jsp:include page="./templates/Comment.jsp" />
    <jsp:include page="./templates/UserItem.jsp" />
    <jsp:include page="./templates/ModalShare.jsp" />
    <jsp:include page="./templates/ModalControls.jsp" />
    <jsp:include page="./templates/Navbar.jsp" />
    <jsp:include page="./templates/Tabs.jsp" />
    <jsp:include page="./templates/PostForm.jsp" />
    
    <div id="APP"></div>
    
    <script>
    $(document).ready(function(){
      APP = app.Application.Controller.getInstance();
      APP.setElement($("#APP"));
      APP.render();
    });
    </script>
  </body>
</html>