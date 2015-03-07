goog.provide('app.Widget');
goog.require('app.View');

app.Widget.Create = function Create(Class,x){
  return new app.Widget[Class](x);
};

app.Widget.Modal = app.View.Template.extend({
  tagName: 'div',
  attributes: {
    'class': 'modal fade'
  },
  modal: function(val){
    this.$el.modal(val);
  }
});

app.Widget.Share = app.Widget.Modal.extend({
  selector: "#ModalShareTemplate",
  
  initialize: function(){
    app.Widget.Modal.prototype.initialize.apply(this);
    this.model = null;
  },
  
  actions: {
    facebook: function(event){
      var target = this;
      FB.ui({
        method: 'feed',
        name: 'Check out this comment!',
        link: 'http://www.ubp.edu.ar',
        caption: 'Posted by ' + target.model.get('owner'),
        description: target.model.get('text'),
        message: ''
      });
      
      // here must create a share whit post id = target.model.id and type = 'facebook'
      target.model.share(/*type*/'facebook');
    }
  },
  
  share: function(model){
    this.model = model;
    this.$el.empty();
    this.render();
    this.selectors.owner.html(model.get('owner'));
    this.selectors.text.html(model.get('text'));
    
    var tweet = model.get('owner') + ': ' + model.get('text');
    this.selectors.TweeterButton.attr('data-text',tweet);
    window.twttr.widgets.load();
    this.modal();
  }
});

app.Widget.Ranking = app.Widget.Modal.extend({
  selector: "#ModalRankingTemplate",
  
  initialize: function(){
    app.Widget.Modal.prototype.initialize.apply(this);
  },
  
  update: function(){
    var app_model = app.Application.Model.getInstance();
    this.selectors.table.empty(); // empty the table
    
    this.listenToOnce(app_model.ranking,'sync',function(){
      var ranking = app_model.ranking;
      var self = this;
      ranking.toJSON().forEach(function(row){
        self.selectors.table.append(
          '<tr>' +
          '<td>'+ row.ts +'</td>' +
          '<td>'+ row.owner +'</td>' +
          '<td>'+ row.comments +'</td>' +
          '<td>'+ row.facebook_shares + '</td>' +
          '<td>'+ row.tweeter_shares +'</td>' +
          '<td><a data-action="showText" data-text="'+ row.text +'">Text</a></td>' +
          '</tr>'
        );
      });
    });
    
    this.listenToOnce(app_model.ranking,'error',function(){
      alert('error in query');
    });
    //here set the url
    var query = {};
    var params = {};
    params.career = this.selectors.career.val();
    params.subject = this.selectors.subject.val();
    params.from = this.selectors.from.val();
    params.to = this.selectors.to.val();
    
    this.selectors.career.val('');
    this.selectors.subject.val('');
    this.selectors.from.val('');
    this.selectors.to.val('');
    
    query.user = app_model.user.id;
    if(params.career.length !== 0){
      query.career = params.career;
    }
    
    if(params.subject.length !== 0){
      query.subject = params.subject;
    }
    
    if(params.from.length !== 0){
      query.from = params.from;
    }
    
    if(params.to.length !== 0){
      query.to = params.to;
    }
 
    app_model.ranking.url =  app.Config.QUERY.RANKING + $.param(query);
    app_model.ranking.fetch();
  },
  
  actions: {
    showText: function(event){
      alert(event.target.dataset.text);
    },
    update: function(event){
      this.update();
    }
  }
  
});

app.Widget.Tabs = app.View.Template.extend({
  selector: '#TabsTemplate',
  
  builders: {
    tab: _.template(
      '<li>' +
        '<a role="tab" data-action="changeNewsfeed" ' +
          'data-target="#<%= target %>" '+ 
          'data-child="<%= child %>" '+ 
          'data-toggle="tab"' +
        '><%= title %></a>' +
       '</li>'),
    panel: _.template('<div class="tab-pane" id="<%= target %>" data-child="<%= child %>"></div>'),
    menuItem: _.template('<a class="list-group-item" data-action="changeCareer" data-target="<%= target %>"><%= target %></a>')
  },
  
  initialize: function(){
    app.View.Template.prototype.initialize.apply(this);
  },
  
  actions: {
    changeNewsfeed: function(event){
      $(event.target).tab('show');
    },
    changeCareer: function(event){
      var career = $(event.target).attr('data-target');
      this.selectors.menu.find('a.active').removeClass('active');
      $(event.target).addClass('active');
      this.filter(career);
    }
  },
  
  filter: function(career){
    _.each(this.collection.models,function(newsfeed){
      var $tab = this.selectors.tabs.find('[data-child=' + newsfeed.id + ']');
      if(newsfeed.get('career') === career || career === 'All Careers'){
        $tab.show();
      }
      else {
        $tab.hide();
      }
    },this);
    this.selectors.tabs.find('li.active').removeClass('active');
    this.selectors.panels.find('.tab-pane.active').removeClass('active');
  },
  
  select: function(index){
    var tabs = this.selectors.tabs.find('a[data-action=changeNewsfeed]');
    if(tabs.length >= index){
      $(tabs[index]).click();
    }
  },
  
  addChild: function(newsfeed){
    var target = "tab_" + newsfeed.id;
    var title = newsfeed.get('subject');
    var tab = this.builders.tab({title:title,target:target,child:newsfeed.id});
    var panel = this.builders.panel({target:target,child:newsfeed.id});
    var view = app.View.Create('Newsfeed',{model:newsfeed});

    this.selectors.tabs.append(tab);
    this.selectors.panels.append(panel);
    this.selectors.panels.find('#' + target ).append(view.render().$el);
  },
  
  render: function(){
    app.View.Template.prototype.render.apply(this);
    
    _.each(this.collection.models,function(newsfeed){
      this.addChild(newsfeed);
    },this);
    
    var careers = _.map(this.collection.models,function(newsfeed){ return newsfeed.get('career'); });
    _.each(_.uniq(careers),function(career){
      var item = this.builders.menuItem({target:career});
      this.selectors.menu.append(item);
    },this);
    
    return this;
  }
});