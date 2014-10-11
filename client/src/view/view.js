goog.provide('app.View');
goog.require('app.Event');
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
 * Shared Observers
 */
app.View.Observers = [];
app.View.Instances = {};

/*
 * Base View
 */
app.View.Base = Backbone.View.extend({
  observers: app.View.Observers,
  
  initialize: function(){
  },
  
  render: function(){
    this.$el.html('');
    return this;
  },
  
  addObserver: function(observer){
    this.observers.push(observer);
  },

  notifyObservers: function(event){
    var action = event.target.dataset.action;
    for(var i in this.observers){
      this.observers[i].notify(this.event[action],this);
    }
    event.stopPropagation();
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
    
  handleEvent: function(event){
    var action = event.target.dataset.action;
    if(this.actions[action]){
      this.actions[action](this);  
    }
    else {
      throw new Error('Cannot handle ' + action);
    }
    
    event.stopPropagation();
  },
  
  actions: {
    //define actions
  },
  
  initialize: function(){
    app.View.Base.prototype.initialize.apply(this);
    this.selectors = {};
    this.compile(this.selector);
  },
  
  attachSelectors: function(){
    for(var key in this.selectors){
      var $selector = this.$('[data-selector='+ key +']');
      if($selector.length > 0){
        this.selectors[key] = $selector;  
      }
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
  type: 'Template',
  
  attributes: function(){
    var self = this;
    
    return {
      id: self.model.id,
      'data-model-type': self.model.get('type'),
      'data-view-type': self.type
    };
  },
  
  initialize: function(){
    app.View.Template.prototype.initialize.apply(this);
    this.listenTo(this.model,'change',this.update);
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
  
  updaters: {
    //define updaters
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
 * Comment Collection View
 */
app.View.CommentCollection = app.View.Model.extend({
  selector: '#CommentCollectionTemplate',
  type: 'CommentCollection',
  event: app.Event.CommentCollection,
  tagName: 'div',

  actions: {
    comment: function(target){
      target.selectors.form.toggle();
      target.selectors.input.focus();
    },
    
    toggle: function(target){
      target.selectors.list.fadeToggle();
      var text = target.selectors.toggle.html();
      target.selectors.toggle.html(text === 'Hide'? 'Show':'Hide');
    },
    
    send: function(target){
      var model = app.Model.Create('Comment',{
        content: target.selectors.input.val(),
        parent: target.model.id,
        user: app.Model.Session.getInstance().get('user')
      });
      
      model.save();
      target.selectors.input.val('');
      target.selectors.form.hide();
      
      //target.uploader.upload(target.selectors.uploader[0].files);
      //$(target.selectors.uploader).fileupload({
        //dataType: 'json',
        //done: function(e,data){
          //console.log(data);
        //}
      //});
    }
  },
  
  updaters: {
    visible: function(target){
      target.$el.toggle(target.model.get('visible'));
    },
    
    available: function(target){
      target.selectors.actions.toggle(target.model.get('available'));
    }
  },
  
  initialize: function(){
    app.View.Model.prototype.initialize.apply(this);
    this.selectors = {
      list: null,
      form: null,
      input: null,
      actions: null,
      toggle: null,
      uploader: null,
      fileform: null
    };

    this.childs = [];
  },
  
  render: function(){
    app.View.Model.prototype.render.apply(this);
    var self = this;
    
    app.DataBase.getInstance().get('Comments')
    .where({parent:self.model.id})
    .forEach(function(model){
      self.add(app.View.Create('Comment',{model:model}));
    });
    
    return this;
  },
  
  add: function(view){
    this.selectors.list.append(view.render().$el);
    this.selectors.list.show();
  },
  
  remove: function(){
    this.childs.forEach(function(child){
      child.remove();
    });
    
    app.View.Model.prototype.remove.apply(this);
  }
});

/*
 * Newsfeed View
 */
app.View.Newsfeed = app.View.Model.extend({
  event: app.Event.Newsfeed,
  selector: '#NewsFeedTemplate',
  type: 'Newsfeed',
  tagName: 'div',
  
  attributes: function(){
    return app.View.Model.prototype.attributes.apply(this);
  },
  
  updaters: {
    visible: function(target){
      target.$el.fadeToggle(target.model.get('visible'));
    }
  },
  
  initialize: function(){
    app.View.Model.prototype.initialize.apply(this);
  },
  
  render: function(){
    var self = this;
    app.View.Model.prototype.render.apply(this);
    
    var c = app.View.Create('CommentCollection',{model:self.model});
    this.$el.append(c.render().$el);
    return this;
  }
});

/*
 * Newsfeed Item View
 */
app.View.NewsfeedItem = app.View.Model.extend({
  event: app.Event.NewsfeedItem,
  selector: '#NewsFeedItemTemplate',
  type: 'NewsfeedItem',
  tagName: 'li',
  
  attributes: function(){
    return app.View.Model.prototype.attributes.apply(this);
  }
});

/*
 * Comment View
 */
app.View.Comment = app.View.Model.extend({
  event: app.Event.Comment,
  selector: '#CommentTemplate',
  type: 'Comment',
  tagName: 'li',
  
  attributes: function(){
    return app.View.Model.prototype.attributes.apply(this);
  },
  
  initialize: function(){
    app.View.Model.prototype.initialize.apply(this);
    this.selectors = {
      actions: null
    };
  },
  
  updaters: {
    available: function(target){
      target.selectors.actions.toggle(target.model.get('available'));
    }
  },
  
  builders: {
    comment: app.Builder.Create("Comment"),
    video: app.Builder.Create("Video"),
    image: app.Builder.Create("Image")
  },
  
  actions: {
    share: function(target){
      app.Widget.Share.getInstance().modal();
    },
    like: function(target){
      var likes = target.model.get('likes')+1;
      target.model.save({likes:likes});  
    }
  },

  render: function(){
    app.View.Model.prototype.render.apply(this);
    
    var $content = this.properties.content;
    var content = this.model.get('content');
    var self = this;
    
    var commentTokens = this.builders.comment.build(content);
    var imageTokens = this.builders.image.build(content);
    var videoTokens = this.builders.video.build(content);
    
    $content.html('');
    $content.append(_.first(videoTokens));
    $content.append(_.first(imageTokens));
    $content.append(commentTokens.join(' '));
    $content.find("video").mediaelementplayer();
    
    var parent = this.model.get("parent");
    
    var parentModel = app.DataBase.getInstance().get("Newsfeeds").findWhere({id:parent});
    if(parentModel){
        var c = app.View.Create('CommentCollection',{model:self.model});
        this.$el.append(c.render().$el);
    }
    
    return this;
  }
});

/*
 * User Item View
 */
app.View.UserItem = app.View.Model.extend({
  selector: '#UserItemTemplate',
  type: 'UserItem',
  tagName: 'li'
});