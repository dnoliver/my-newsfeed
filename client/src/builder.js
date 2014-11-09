goog.provide('app.Builder');

app.Builder.Create = function Create(Class,x){
  return new app.Builder[Class](x);
};

app.Builder.Creators = {
  link: _.template('<a href="<%= content %>"><%= content %></a>'),
  image: _.template('<img src="<%= content %>" alt="not available" class="img-rounded img-responsive">'),
  video: _.template('<video preload="none"><source type="video/youtube" src="<%= content %>" /></video>'),
  youtube: _.template('<embed width="420" height="315" src="<%= content %>">')
};

app.Builder.Patterns = {
  link: /^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/,
  image: /\.(jpeg|jpg|gif|png)$/,
  video: /^(?:https?:\/\/)?(?:www\.)?youtu(?:\.be|be\.com)\/(?:watch\?v=)?([\w-]{10,})/
};

app.Builder.Base = function BaseBuilder(){
  
};

app.Builder.Base.prototype.build = function(content){
  var tokens = _.map(content.split(' '),this.buildPart);
  return tokens.filter(function(token){ return token; });
};

app.Builder.Base.prototype.buildPart = function(part){
  return part;
};

app.Builder.Comment = function CommentBuilder(){
  goog.base(this);
};

goog.inherits(app.Builder.Comment,app.Builder.Base);

app.Builder.Comment.prototype.buildPart = function(part){
  if(app.Builder.Patterns.link.test(part)){
    return app.Builder.Creators.link({content:part});
  }
  else if(app.Builder.Patterns.video.test(part)){
    return app.Builder.Creators.link({content:part});
  }
  else {
    return part;
  }
};

app.Builder.Image = function ImageBuilder(){
  goog.base(this);
};

goog.inherits(app.Builder.Image,app.Builder.Base);

app.Builder.Image.prototype.buildPart = function(part){
  if(app.Builder.Patterns.link.test(part) && app.Builder.Patterns.image.test(part)){
    return app.Builder.Creators.image({content:part});
  }
};

app.Builder.Video = function VideoBuilder(){
  goog.base(this);
};

goog.inherits(app.Builder.Video,app.Builder.Base);

app.Builder.Video.prototype.buildPart = function(part){
  if(app.Builder.Patterns.video.test(part)){
    return app.Builder.Creators.video({content:part});
  }
};