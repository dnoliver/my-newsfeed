goog.provide('app.Application');
goog.require('app.View');
goog.require('app.Collection');
goog.require('app.Widget');
goog.require('app.Config');

app.Application.Model = Backbone.Model.extend({});
goog.addSingletonGetter(app.Application.Model);

app.Application.Session = Backbone.Model.extend({
  urlRoot: app.Config.MODEL.SESSION,
  initialize: function(){
    var str = document.cookie.split('; ');
    var result = {};
    for (var i = 0; i < str.length; i++) {
      var cur = str[i].split('=');
      result[cur[0]] = cur[1];
    }
    this.set('id',result.session);
  }
});
goog.addSingletonGetter(app.Application.Session);

app.Application.Controller = app.View.Template.extend({
  selector: '#ApplicationTemplate',
  type: 'Application',
  tagName: 'section',
  
  actions: {
    update: function(event){
      this.trigger('UpdateTrigger');
    }
  },
  
  initialize: function(){
    app.View.Template.prototype.initialize.call(this);
    
    this.model = app.Application.Model.getInstance();
    this.session = app.Application.Session.getInstance();
    this.user = app.Model.Create('User');
            
    this.Widgets = {
      Share: new app.Widget.Share(),
      Controls: new app.Widget.Controls(),
      Tabs: new app.Widget.Tabs()
    };
    
    this.Newsfeeds = app.Collection.Create('NewsfeedsQuery');
    this.CommentsFeed = app.Collection.Create('Query');
  },
  
  render: function(){
    app.View.Template.prototype.render.apply(this);
    
    this.selectors.ShareWidgetUiRegion.append(this.Widgets.Share.render().$el);
    this.selectors.ControlsWidgetUiRegion.append(this.Widgets.Controls.render().$el);
    
    this.listenToOnce(this.session,'sync',function(){
      this.user.set('id',this.session.get('owner'));
      this.user.fetch();
    });
    
    this.listenToOnce(this.user,'sync',function(){
      this.model.set('user',this.user.id);
      this.model.set('category',this.user.get('category'));
      this.selectors.UserUiRegion.html(this.model.get('user'));
      
      this.Newsfeeds.setup('newsfeeds','session',this.session.id);
      this.Newsfeeds.fetch();
      
      this.CommentsFeed.setup('comments','feed',this.session.id);
      this.CommentsFeed.fetch();
    });
    
    this.listenToOnce(this.Newsfeeds,'sync',function(){
      this.Newsfeeds.setAutoUpdate(true);
      this.CommentsFeed.setAutoUpdate(true);
      this.Widgets.Tabs.collection = this.Newsfeeds;
      this.selectors.TabUiRegion.append(this.Widgets.Tabs.render().$el);
      this.startFetching();
    });
    
    this.listenTo(this.CommentsFeed,'sync',function(){
      this.selectors.FeedUiRegion.empty();
      var feeds = this.CommentsFeed.toJSON();
      for(var i in feeds){
        var feed = feeds[i];
        var $feed = $('<p class="feed">' + feed.owner + '> ' + feed.text + '</p>');
        $feed.hide();
        this.selectors.FeedUiRegion.append($feed);
        $feed.fadeIn(2000);
      }
    });

    this.session.fetch();
 
    return this;
  },
  
  sendAttachment: function(data,success,error){
    $.ajax({
      method: 'post',
      url: '/ubp/upload',
      data: new FormData(data.form[0]),
      cache: false,
      contentType: false,
      processData: false,
      success: function (resource) {
        success(resource);
      },
      error: function(){
        error();
      }
    });
  },
  
  sendUserPost:  function(data,success,error){
    this.sendModel('Post',data,success,error);
  },
  
  sendUserComment:  function(data,success,error){
    this.sendModel('Comment',data,success,error);
  },
  
  sendModel: function(type,data,success,error){
    data.owner = this.model.get('user');
    var model = app.Model.Create(type);
    
    model.once('invalid',function(model,reason){
      alert('Error: ' + reason);
    },this);
    
    model.save(data,{success:success,error:error});
  },
  
  showControls: function(options){
    this.Widgets.Controls.control(options);
  },
  
  showShareWidget: function(model){
    this.Widgets.Share.share(model);
  },
  
  startFetching: function(){
    var self = this;
    this.FetchInterval = setInterval(function(){
      self.trigger('UpdateTrigger');
    },app.Config.COLLECTION_UPDATE_INTERVAL);
  },
  
  stopFetching: function(){
    clearInterval(this.FetchInterval);
  }
});

goog.addSingletonGetter(app.Application.Controller);