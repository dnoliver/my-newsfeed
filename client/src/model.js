goog.provide('app.Model');
goog.require('app.Config');

app.Model.Create = function(Class,x){
  return new app.Model[Class](x);
};

app.Model.Base = Backbone.Model.extend({});

app.Model.Newsfeed = app.Model.Base.extend({
  urlRoot: app.Config.MODEL_URL + '/newsfeeds'
});

app.Model.Post = app.Model.Base.extend({
  urlRoot: app.Config.MODEL_URL + '/posts',
  validate: function(attributes,options){
    if(attributes.text.length <= 0){
      return "text cannot be empty";
    }
    
    if(attributes.text.length >= 140){
      return "text cannot be grater than 140";
    }
  }
});

app.Model.Like = app.Model.Base.extend({
  urlRoot: app.Config.MODEL_URL + '/likes'
});

app.Model.Comment = app.Model.Base.extend({
  urlRoot: app.Config.MODEL_URL + '/comments',
  validate: function(attributes,options){
    if(attributes.text.length >= 140){
      return "text cannot be grater than 140";
    }
    if(attributes.text.length <= 0){
      return "text cannot be empty";
    }
  }
});

app.Model.User = app.Model.Base.extend({
  urlRoot: app.Config.MODEL_URL + '/users'
});