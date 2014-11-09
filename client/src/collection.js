goog.provide('app.Collection');
goog.require('app.Model');
goog.require('app.Config');

app.Collection.Create = function Create(Class,x){
  return new app.Collection[Class](x);
};

app.Collection.Sync = Backbone.Collection.extend({
  initialize: function(){
    this.isAutoUpdate = false;
  },
  
  setAutoUpdate: function(isAutoUpdate){
    if(isAutoUpdate && !this.isAutoUpdate ){
      this.listenTo(app.Application.Controller.getInstance(),'UpdateTrigger',function(){
        this.fetch();
      });
    }
    else {
      this.stopListening(app.Application.Controller.getInstance(),'UpdateTrigger');
      this.isAutoUpdate = false;
    }
  }
});

app.Collection.Users = Backbone.Collection.extend({
    url: app.Config.COLLECTION_URL + '/users',
    model: app.Model.User
});

app.Collection.Newsfeeds = Backbone.Collection.extend({
    url: app.Config.COLLECTION_URL + '/newsfeeds',
    model: app.Model.Newsfeed
});

app.Collection.Posts = Backbone.Collection.extend({
    url: app.Config.COLLECTION_URL + '/posts',
    comparator: 'id',
    model: app.Model.Post
});

app.Collection.Comments = Backbone.Collection.extend({
    url: app.Config.COLLECTION_URL + '/comments',
    comparator: 'id',
    model: app.Model.Comment
});

app.Collection.Query = app.Collection.Sync.extend({
  setup: function(resource,filter,value){
    this.url = app.Config.QUERY_URL;
    this.url += resource + '/';
    this.url += filter + '/';
    this.url += value ;
  }
});

app.Collection.NewsfeedsQuery = app.Collection.Query.extend({
  model: app.Model.Newsfeed
});

app.Collection.PostsQuery = app.Collection.Query.extend({
    model: app.Model.Post
});

app.Collection.LikesQuery = app.Collection.Query.extend({
    model: app.Model.Like
});

app.Collection.CommentsQuery = app.Collection.Query.extend({
    model: app.Model.Comment
});

app.Collection.UsersQuery = app.Collection.Query.extend({
    model: app.Model.User
});