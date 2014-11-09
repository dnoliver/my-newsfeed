goog.provide('app.View');
goog.require('app.Model');
goog.require('app.Builder');

/*
 * Factory Method
 */
app.View.Create = function(Class,x){
  if(!app.View.Instances[Class]){
    app.View.Instances[Class] = {};  
  }
  
  if(x && x.model && x.model.id && !app.View.Instances[Class][x.model.id]){
    app.View.Instances[Class][x.model.id] = new app.View[Class](x);
    return app.View.Instances[Class][x.model.id];
  }
  else {
    return new app.View[Class](x);
  }
};

/*
 * Instances Pool
 */
app.View.Instances = {};

/*
 * Base View
 */
app.View.Base = Backbone.View.extend({
  render: function(){
    this.$el.html('');
    return this;
  }
});

/*
 * Template View
 */
app.View.Template = app.View.Base.extend({
  selector: null,
  
  events: {
    'click [data-action]': 'handleEvent'
  },
  
  actions: {},
    
  handleEvent: function(event){
    var action = event.target.dataset.action;
    if(this.actions[action]){
      this.actions[action].apply(this,[event]);  
    }
    else {
      throw new Error('Cannot handle ' + action);
    }
    event.stopPropagation();
  },
  
  initialize: function(){
    app.View.Base.prototype.initialize.apply(this);
    this.selectors = {};
    this.compile(this.selector);
  },
  
  attachSelectors: function(){
    var elements = this.$el.find('[data-selector]');
    
    for(var i = 0; i < elements.length; i++){
      var selector = elements[i].dataset.selector;
      this.selectors[selector] = this.$(elements[i]); 
    }
  },
  
  compile: function(selector){
    this.template = _.template($(selector).html());
  },
  
  render: function(){
    this.$el.html(this.template());
    this.attachSelectors();
    return this;
  }
});

/*
 *  Model View
 */
app.View.Model = app.View.Template.extend({
  type: 'Model',
  
  attributes: function(){
    var attrs = {};
    attrs['data-view-type'] = this.type;
    return attrs;
  },
  
  updaters: {},
  
  initialize: function(){
    app.View.Template.prototype.initialize.apply(this);
    this.listenTo(this.model,'change',this.update);
    this.listenTo(this.model,'remove',this.remove);
    this.properties = {};
  },
  
  attachSelectors: function(){
    app.View.Template.prototype.attachSelectors.apply(this);
    
    for(var key in this.model.toJSON()){
      var $selector = this.$('[data-model='+ key +']');
      
      if($selector.length > 0){
        this.properties[key] = $selector;
      }
    }
  },
  
  updateView: function(){
    for(var property in this.properties){
      this.properties[property].html(this.model.get(property));
    }
    for(var updater in this.updaters){
      this.updaters[updater](this);
    }
  },
  
  update: function(model,val,options){
    for(var changed in model.changed){
      if(this.updaters[changed]){
        this.updaters[changed](this);    
      }
      if(this.properties[changed]){
        this.properties[changed].html(this.model.get(changed));
      }
    }
  },
  
  render: function(){
    app.View.Template.prototype.render.apply(this);
    this.updateView();
    return this;
  }
});

/*
 * Collection View
 */
app.View.Collection = app.View.Base.extend({
  tagName: 'ul',
  
  initialize: function(){
    app.View.Base.prototype.initialize.apply(this);
    this.childs = [];
  },   
                                           
  render: function(){
    app.View.Base.prototype.render.apply(this);
    return this;
  },
  
  add: function(view){
    this.$el.append(view.render().$el);
    this.childs.push(view);
  },
  
  remove: function(){
    for(var child in this.childs){
      this.childs[child].remove();
    }
    app.View.Base.prototype.remove.apply(this);
  }
});

/*
 * Post Form View
 */
app.View.PostForm = app.View.Template.extend({
  selector: '#PostFormTemplate',
  tagName: 'div',
  
  attributes: function(){
    var attrs = {};
    attrs.class = 'well';
    attrs.style = 'background-color:#DFF0D8';
    return attrs;
  },
  
  actions: {
    post: function(event){
      var target = this;
      var type = target.postdata.type;
      var data = _.clone(target.postdata.data);

      data.text = target.selectors.input.val();
      var attachment = {
        files: target.selectors.file[0].files,
        form: target.selectors.form
      };

      var success = function(){
        target.selectors.form[0].reset();
        target.selectors.input.val('');
        target.postdata.success && target.postdata.success();
      };

      var error = function(){
        target.postdata.error && target.postdata.error();
      };

      var AppController = app.Application.Controller.getInstance();
      if(attachment.files.length > 0){
        AppController.sendAttachment(attachment,function(resource){
          data.attachment = resource;
          AppController.sendModel(type,data,success,error);
        },error);
      }
      else{
        AppController.sendModel(type,data,success,error);
      }
    }
  },
  
  initialize: function(){
    app.View.Template.prototype.initialize.apply(this);
    this.postdata = {};
  },
  
  setPostData: function(postdata){
    this.postdata = postdata;
  },
  
  render: function(){
    app.View.Template.prototype.render.apply(this);
    return this;
  }
});

/*
 * Newsfeed View
 */
app.View.Newsfeed = app.View.Model.extend({
  selector: '#NewsFeedTemplate',
  type: 'Newsfeed',
  tagName: 'div',
  
  attributes: function(){
    var attrs = app.View.Model.prototype.attributes.apply(this);
    attrs.class = 'panel';
    return attrs;
  },
  
  updaters: {
    enabled: function(target){
      if(target.model.get('enabled') === true){
        target.$el.attr('class','panel panel-primary');
        target.selectors.form.show();
      }
      else {
        target.$el.attr('class','panel panel-danger');
        target.selectors.form.hide();
      }
    }
  },
  
  actions: {
    controls: function(event){
      var target = this;
      app.Application.Controller.getInstance().showControls({model:target.model,enable:true,disable:true});
    }
  },
  
  initialize: function(){
    app.View.Model.prototype.initialize.apply(this);
    
    var postdata = {data:{},type:''};
    var self = this;
    postdata.data.newsfeed = this.model.id;
    postdata.type = 'Post';
    postdata.success = function(){ self.collection.fetch(); };
    postdata.error = function(){ alert('error sending post'); };
    
    this.collection = app.Collection.Create('PostsQuery');
    this.form = app.View.Create('PostForm');
    this.form.setPostData(postdata);
    
    this.collection.setup('posts','newsfeed',this.model.id);
    
    this.collection.on('add',function(post){
      this.selectors.posts.prepend(app.View.Create('Post',{model:post}).render().$el);
    },this);
    
    this.collection.once('sync',function(){
      this.collection.setAutoUpdate(true);
    },this);
  },
  
  render: function(){
    app.View.Model.prototype.render.apply(this);
    
    this.selectors.form.append(this.form.render().$el);
    this.collection.fetch();
    
    if(app.Application.Model.getInstance().get('category') === 'professor'){
      this.selectors.controls.show();
    }
    else {
      this.selectors.controls.hide();
    }
    return this;
  }
});

/*
 * Comment View
 */
app.View.Comment = app.View.Model.extend({
  selector: '#CommentTemplate',
  type: 'Comment',
  tagName: 'div',
  
  attributes: function(){
    var attrs = app.View.Model.prototype.attributes.apply(this);
    attrs.class = 'panel';
    return attrs;
  },
  
  initialize: function(){
    app.View.Model.prototype.initialize.apply(this);
  },
  
  updaters: {
    enabled: function(target){
      var isEnabled = target.model.get('enabled') === true;
      if(isEnabled){
        target.$el.attr('class','panel panel-default');
      }
      else {
        target.$el.attr('class','panel panel-danger');
      }
    }
  },
  
  builders: {
    comment: app.Builder.Create("Comment"),
    video: app.Builder.Create("Video"),
    image: app.Builder.Create("Image")
  },
  
  actions: {
    controls: function(event){
      var target = this;
      app.Application.Controller.getInstance().showControls({model:target.model,delete:true});
    },
    attachment: function(event){
      var target = this;
      window.open(target.model.get('attachment'));
    }
  },

  render: function(){
    app.View.Model.prototype.render.apply(this);
    
    var $content = this.selectors.text;
    var $media = this.selectors.media;
    
    var content = this.model.get('text');
    var commentTokens = this.builders.comment.build(content);
    var imageTokens = this.builders.image.build(content);
    var videoTokens = this.builders.video.build(content);
    
    if(videoTokens.length > 0){
      $media.append(_.first(videoTokens));
      $media.find("video").mediaelementplayer();
    }
    else if(imageTokens.length > 0){
      $media.append(_.first(imageTokens));
    }
    
    $content.append(commentTokens.join(' '));
    
    if(app.Application.Model.getInstance().get('category') === 'professor'){
      this.selectors.controls.show();
    }
    else {
      this.selectors.controls.hide();
    }
    
    return this;
  }
});

/*
 * Post View
 */
app.View.Post = app.View.Comment.extend({
  selector: '#PostTemplate',
  type: 'Post',
  tagName: 'div',
  
  updaters: {
    enabled: function(target){
      var isEnabled = target.model.get('enabled') === true;
      if(isEnabled){
        target.$el.attr('class','panel panel-info');
        target.selectors.form.show();
        target.selectors.actions.show();
      }
      else {
        target.$el.attr('class','panel panel-danger');
        target.selectors.form.hide();
        target.selectors.actions.hide();
      }
    }
  },
  
  initialize: function(){
    app.View.Comment.prototype.initialize.apply(this);
    var self = this;
    var postdata = {data:{},type: ''};
    postdata.data.post = this.model.id;
    postdata.type = 'Comment';
    postdata.success = function(){ self.collection.fetch(); };
    postdata.error = function(){ alert('error sending post'); };
    
    this.form = app.View.Create('PostForm');
    this.form.setPostData(postdata);
    
    this.likes = app.Collection.Create('LikesQuery');
    this.likes.setup('likes','post',this.model.id);
    
    this.collection = app.Collection.Create('CommentsQuery');
    this.collection.setup('comments','post',this.model.id);
    
    this.collection.on('add',function(comment){
      this.selectors.comments.append(app.View.Create('Comment',{model:comment}).render().$el);
    },this);
    
    this.collection.once('sync',function(){
      this.collection.setAutoUpdate(true);
    },this);
    
    this.likes.once('sync',function(){
      var userLike = this.likes.findWhere({owner: app.Application.Model.getInstance().get('user')});
      if(userLike){
        this.selectors.like.hide();
      }
    },this);
  },
  
  actions: {
    controls: function(event){
      var target = this;
      app.Application.Controller.getInstance().showControls({model:target.model,delete:true});
    },
    share: function(event){
      var target = this;
      app.Application.Controller.getInstance().showShareWidget(target.model);
    },
    attachment: function(event){
      var target = this;
      window.open(target.model.get('attachment'));
    },
    like: function(event){
      var target = this;
      var data = {
        owner: app.Application.Model.getInstance().get('user'),
        post: target.model.id
      };
      var Like = app.Model.Create('Like');
      Like.save(data,{
        success:function(){
          target.likes.fetch();
          target.selectors.like.hide();
        },
        error:function(){
          alert('cannot send like');
        }
      });
    }
  },

  render: function(){
    app.View.Comment.prototype.render.apply(this);
    this.selectors.form.append(this.form.render().$el);
    this.collection.fetch();
    this.likes.fetch();
    return this;
  }
});

/*
 * User Item View
 */
app.View.UserItem = app.View.Model.extend({
  selector: '#UserItemTemplate',
  type: 'UserItem',
  tagName: 'li',
  
  initialize: function(){
    app.View.Model.prototype.initialize.call(this);
    this.selectors = {
      status: null
    };
  },
  
  setConnected: function(){
    this.selectors.status.attr('class','label label-success');
  },
  
  setDisconnected: function(){
    this.selectors.status.attr('class','label label-default');
  },
  
  updaters: {
    status: function(target){
      var isConnected = target.model.get('status') === 'connected';
      if(isConnected){ 
        target.setConnected();
      }
      else { 
        target.setDisconnected(); 
      }
    }
  }
});