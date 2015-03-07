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
      throw new Error('Cannot handle action: ' + action);
    }
    event.stopPropagation();
  },
  
  initialize: function(){
    app.View.Base.prototype.initialize.apply(this);
    this.selectors = {};
  },
  
  attachSelectors: function(){
    var elements = this.$el.find('[data-selector]');
    for(var i = 0; i < elements.length; i++){
      var selector = elements[i].dataset.selector;
      this.selectors[selector] = this.$(elements[i]); 
    }
  },
  
  render: function(){
    this.template = _.template($(this.selector).html());
    this.$el.html(this.template());
    this.attachSelectors();
    return this;
  }
});

/*
 *  Model View
 */
app.View.Model = app.View.Template.extend({
  
  updaters: {},
  
  initialize: function(){
    app.View.Template.prototype.initialize.apply(this);
    this.listenTo(this.model,'change',this.update);
    this.listenTo(this.model,'remove',this.remove);
    this.properties = {};
  },
  
  attachProperties: function(){
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
      this.updaters[updater].apply(this,[this]);
    }
  },
  
  update: function(model,val,options){
    for(var changed in model.changed){
      if(this.updaters[changed]){
        this.updaters[changed].apply(this,[this]);
      }
      if(this.properties[changed]){
        this.properties[changed].html(this.model.get(changed));
      }
    }
  },
  
  render: function(){
    app.View.Template.prototype.render.apply(this);
    this.attachProperties();
    this.updateView();
    return this;
  }
});

/*
 * Publication View
 */
app.View.Publication = app.View.Model.extend({
  
  initialize:function(){
    app.View.Model.prototype.initialize.apply(this);
  },
  
  actions: {
    attachment: function(event){
      window.open(this.model.get('attachment'));
    },
    like: function(event){
      this.model.like({});
    },
    share: function(event){
      app.Application.Controller.getInstance().showShareWidget(this.model);
    },
    enable: function(event){
      this.model.save({enabled:true});
    },
    disable: function(event){
      this.model.save({enabled:false});
    },
    remove: function(event){
      this.model.save({deleted:true});
    },
    
    publish: function(event){
      var files = this.selectors.attachment[0].files;
      var form = this.selectors.form[0];
      var attributes = {text: this.selectors.input.val(),attachment:null};
      var self = this;
      
      if(files.length > 0){
        $.ajax({
          method: 'post',
          url: '/ubp/upload',
          data: new FormData(form),
          cache: false,
          contentType: false,
          processData: false,
          success: function (attachment) {
            attributes.attachment = attachment;
            self.model.publish(attributes);
          },
          error: function(){
            alert('error sending attachment');
          }
        });
      }
      else {
        this.model.publish(attributes);
      }
      self.selectors.form[0].reset();
    }
  },
  
  render: function(){
    app.View.Model.prototype.render.apply(this);
    var app_model = app.Application.Model.getInstance(); 
    var category = app_model.user.get('category');
    
    if(category === 'student'){
      this.selectors.actions.find('[data-category=professor]').remove();
    }
    
    var self = this;
    $(this.selectors.form).submit(function(event){
      self.actions['publish'].apply(self,[]);
      return false;
    });
    
    return this;
  }
});

/*
 * Newsfeed View
 */
app.View.Newsfeed = app.View.Publication.extend({
  selector: '#NewsFeedTemplate',
  tagName: 'div',
  className: 'publication newsfeed',
  
  initialize: function(){
    app.View.Publication.prototype.initialize.apply(this);
    
    this.model.publications.on('add',function(post){
      var $child = app.View.Create('Post',{model:post}).render().$el;
      $child.hide().delay(500).fadeIn(1500);
      this.selectors.posts.prepend($child);
    },this);
  },
  
  updaters: {
    enabled: function(target){
      if(this.model.get('enabled')){
        this.selectors.form.show();
      }
      else {
        this.selectors.form.hide();
      }
    }
  },
  
  render: function(){
    app.View.Publication.prototype.render.apply(this);
    return this;
  }
});

/*
 * Post View
 */
app.View.Post = app.View.Publication.extend({
  selector: '#PostTemplate',
  tagName: 'div',
  className: 'publication post',
  
  initialize: function(){
    app.View.Publication.prototype.initialize.apply(this);
    
    this.builders = {
      comment: app.Builder.Create("Comment"),
      video: app.Builder.Create("Video"),
      image: app.Builder.Create("Image")
    };
    
    this.model.publications.on('add',function(comment){
      var $child = app.View.Create('Comment',{model:comment}).render().$el;
      $child.hide().delay(500).fadeIn(1500);
      this.selectors.comments.append($child);
    },this);
    
    this.model.likes.on('sync',function(){
      var app_model = app.Application.Model.getInstance();
      var userLike = this.model.likes.findWhere({owner: app_model.user.id});
      if(userLike){
        this.selectors.like.hide();
      }
    },this);
  },
  
  updaters: {
    enabled: function(target){
      if(this.model.get('enabled')){
        this.selectors.panel.attr('class','panel panel-primary');
        this.selectors.actions.show();
        this.selectors.footer.show();
      }
      else {
        this.selectors.panel.attr('class','panel panel-danger');
        this.selectors.actions.hide();
        this.selectors.footer.hide();
      }
    },
    attachment: function(target){
      if(this.model.get('attachment') === null){
        this.selectors.actions.find('[data-action=attachment]').hide();
      }
      else {
        this.selectors.actions.find('[data-action=attachment]').show();
      }
    }
  },
  
  render: function(){
    app.View.Publication.prototype.render.apply(this);
    var content = this.model.get('text');
    var imageTokens = this.builders.image.build(content);
    var videoTokens = this.builders.video.build(content);
    var commentTokens = this.builders.comment.build(content);
    
    this.selectors.text.append(commentTokens.join(' '));
    
    if(videoTokens.length > 0){
      this.selectors.media.append(_.first(videoTokens));
      this.selectors.media.attr('class','embed-responsive embed-responsive-4by3');
    }
    else if(imageTokens.length > 0){
      this.selectors.media.append(_.first(imageTokens));
    }

    return this;
  }
});

/*
 * Comment View
 */
app.View.Comment = app.View.Publication.extend({
  selector: '#CommentTemplate',
  tagName: 'a',
  className: 'publication comment list-group-item',
  
  initialize: function(){
    app.View.Publication.prototype.initialize.apply(this);
  },
  
  updaters: {
    enabled: function(target){
      if(this.model.get('enabled')){
        this.$el.removeClass('disabled');
      }
      else {
        this.$el.addClass('disabled');
      }
    }
  },
  
  render: function(){
    app.View.Publication.prototype.render.apply(this);
    var content = this.model.get('text');
    var builder = app.Builder.Create("Comment");
    var commentTokens = builder.build(content);
    this.selectors.text.html(commentTokens.join(' '));
    
    return this;
  }
});