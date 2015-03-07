goog.provide('app.Model');
goog.require('app.Config');

app.Model.Create = function(Class,x){
  return new app.Model[Class](x);
};

app.Model.Newsfeed = Backbone.Model.extend({
  urlRoot: app.Config.MODEL.NEWSFEED,
  
  initialize: function(){
    if(this.isNew()) return;
    
    this.publications = new Backbone.Collection([],{
      model: app.Model.Post
    });
    
    this.publications.url = app.Config.QUERY.POST + $.param({newsfeed:this.id});
    
    this.listenTo(this.collection,'sync',function(){
      this.publications.fetch();
    });
  },
  
  publish: function(attributes){
    var publication = app.Model.Create('Post',{
      text: attributes.text,
      attachment: attributes.attachment,
      newsfeed: this.id,
      owner: app.Application.Model.getInstance().user.id
    });
    
    publication.once('invalid',function(model,reason){
      alert('Error: ' + reason);
    },this);
    
    publication.once('error',function(){});
    
    publication.once('sync',function(){
      this.publications.fetch();
    },this);
    
    publication.save();
  }
});

app.Model.Post = Backbone.Model.extend({
  urlRoot: app.Config.MODEL.POST,
  
  initialize: function(){
    if(this.isNew()) return;    
    
    this.publications = new Backbone.Collection([],{
      model:app.Model.Comment
    });
    
    this.likes = new Backbone.Collection([],{
      model:app.Model.Like
    });
    
    this.publications.url = app.Config.QUERY.COMMENT + $.param({post:this.id});
    this.likes.url = app.Config.QUERY.LIKE + $.param({post:this.id});
    
    this.listenTo(this.collection,'sync',function(){
      this.likes.fetch();
      this.publications.fetch();
    });
  },
  
  validate: function(attributes,options){
    if(attributes.text.length <= 0){
      return "text cannot be empty";
    }
    
    if(attributes.text.length >= 140){
      return "text cannot be grater than 140";
    }
  },
  
  publish: function(attributes){
    var publication = app.Model.Create('Comment',{
      text: attributes.text,
      attachment: attributes.attachment,
      post: this.id,
      owner: app.Application.Model.getInstance().user.id
    });
    
    publication.once('invalid',function(model,reason){
      alert('Error: ' + reason);
    },this);
    
    publication.once('error',function(){});
    
    publication.once('sync',function(){
      this.publications.fetch();
    },this);
    
    publication.save();
  },
  
  like: function(attributes){
    var like = app.Model.Create('Like',{
      owner: app.Application.Model.getInstance().user.id,
      post: this.id
    });
    
    like.once('invalid',function(model,reason){
      alert('cannot send like');
    },this);
    
    like.once('error',function(){
      alert('cannot send like');
    },this);
    
    like.once('sync',function(){
     this.likes.fetch();
     this.fetch();
    },this);
    
    like.save();
  },
  
  share: function(type){
    var share = app.Model.Create('Share',{
      post: this.id,
      type: type
    });
    
    share.once('invalid',function(model,reason){
      alert('cannot send share');
    },this);
    
    share.once('error',function(){
      alert('cannot send share');
    },this);
    
    share.once('sync',function(){
     console.log('share sync');
    },this);
    
    share.save();
  }
});

app.Model.Comment = Backbone.Model.extend({
  urlRoot: app.Config.MODEL.COMMENT,
  
  validate: function(attributes,options){
    if(attributes.text.length <= 0){
      return "text cannot be empty";
    }
    
    if(attributes.text.length >= 140){
      return "text cannot be grater than 140";
    }
  }
});

app.Model.Like = Backbone.Model.extend({
  urlRoot: app.Config.MODEL.LIKE
});

app.Model.Share = Backbone.Model.extend({
  urlRoot: app.Config.MODEL.SHARE
});


app.Model.User = Backbone.Model.extend({
  urlRoot: app.Config.MODEL.USER
});