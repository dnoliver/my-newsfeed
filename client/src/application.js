goog.provide('app.Application');
goog.require('app.View');
goog.require('app.Widget');
goog.require('app.Config');

app.Application.Model = Backbone.Model.extend({
  initialize: function(){
    this.feed = new Backbone.Collection([],{});
    this.user = app.Model.Create('User');
    this.newsfeeds = new Backbone.Collection([],{
      model:app.Model.Newsfeed
    });
    this.ranking = new Backbone.Collection([],{});
    
    this.feed.url = app.Config.QUERY.FEED;
    
    this.listenToOnce(this.user,'sync',function(){
      this.newsfeeds.url = app.Config.QUERY.NEWSFEED + $.param({user:this.user.id});
      this.ranking.url = app.Config.QUERY.RANKING + $.param({user:this.user.id});
    });
    
    this.listenTo(this.user,'error',function(){
      document.location = '/ubp/Login.jsp';
    });
  },
  
  toJSON: function(){
    return {
      user: this.user.toJSON(),
      newsfeeds: this.newsfeeds.toJSON(),
      feed: this.feed.toJSON()
    };
  },
  
  update: function(){
    this.feed.fetch();
    this.newsfeeds.fetch();
  }
});
goog.addSingletonGetter(app.Application.Model);

app.Application.Controller = app.View.Template.extend({
  selector: '#ApplicationTemplate',
  tagName: 'section',
  
  actions: {
    update: function(event){
      this.model.update();
    },
    logout: function(event){
      if(confirm("Confirm logout")){
        $.ajax({url: '/ubp/account',type: 'DELETE',
          success: function(data) {
            document.location = '/ubp/Login.jsp';
          },
          error: function(){
            alert('error in logout');
          }
        });
      }
    },
    ranking: function(event){
      this.Widgets.Ranking.modal();
      this.Widgets.Ranking.update();
    }
  },
  
  initialize: function(){
    app.View.Template.prototype.initialize.call(this);
    
    this.model = app.Application.Model.getInstance();
            
    this.Widgets = {
      Share: new app.Widget.Share(),
      Tabs: new app.Widget.Tabs(),
      Ranking: new app.Widget.Ranking()
    };
  },
  
  render: function(){
    app.View.Template.prototype.render.apply(this);
    
    this.selectors.ShareWidgetUiRegion.append(this.Widgets.Share.render().$el);
    this.selectors.RankingWidgetUiRegion.append(this.Widgets.Ranking.render().$el);
    
    this.listenToOnce(this.model.user,'sync',function(){
      this.selectors.UserUiRegion.html(this.model.user.id);
      this.model.update();
    });
    
    this.listenTo(this.model.feed,'sync',function(){
      this.selectors.FeedUiRegion.empty();
      var feeds = this.model.feed.toJSON();
      for(var i in feeds){ 
        var $feed = $('<li class="list-group-item">' + feeds[i].owner + ': ' + feeds[i].text + '</li>');
        this.selectors.FeedUiRegion.append($feed);
      }
    });
    
    this.listenToOnce(this.model.newsfeeds,'sync',function(){
      this.Widgets.Tabs.collection = this.model.newsfeeds;
      this.selectors.TabUiRegion.append(this.Widgets.Tabs.render().$el);
      this.startFetching();
    });
    
    this.model.user.fetch();
    return this;
  },
  
  showShareWidget: function(model){
    this.Widgets.Share.share(model);
  },
  
  startFetching: function(){
    var self = this;
    this.FetchInterval = setInterval(function(){
      self.model.update();
    },app.Config.COLLECTION_UPDATE_INTERVAL);
  },
  
  stopFetching: function(){
    clearInterval(this.FetchInterval);
  }
});

goog.addSingletonGetter(app.Application.Controller);