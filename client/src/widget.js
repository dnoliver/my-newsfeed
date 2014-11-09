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

app.Widget.Controls = app.Widget.Modal.extend({
  selector: "#ModalControlsTemplate",
  initialize: function(){
    app.Widget.Modal.prototype.initialize.apply(this);
    this.model = null;
  },
  
  actions: {
    cancel: function(event){
      this.modal('hide');
    },
    enable: function(event){
      this.model.set('enabled',true);
      this.model.save();
      this.modal('hide');
    },
    disable: function(event){
      this.model.set('enabled',false);
      this.model.save();
      this.modal('hide');
    },
    delete: function(event){
      this.model.set('deleted',true);
      this.model.save();
      this.modal('hide');
    }
  },
  
  control: function(options){
    this.model = options.model;
    this.selectors.delete.toggle(options.delete || false);
    this.selectors.disable.toggle(options.disable || false);
    this.selectors.enable.toggle(options.enable || false);
    this.modal();
  }
});

goog.addSingletonGetter(app.Widget.Controls);

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

goog.addSingletonGetter(app.Widget.Share);

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
    menuItem: _.template('<li role="presentation"><a role="menuitem" tabindex="-1" data-action="changeCareer" data-target="<%= target %>"><%= target %></a></li>')
  },
  
  initialize: function(){
    app.View.Template.prototype.initialize.apply(this);
  },
  
  actions: {
    changeNewsfeed: function(event){
      $(event.target).tab('show');
      var newsfeed = $(event.target).attr('data-child');
      app.Application.Model.getInstance().set('newsfeed',newsfeed);
    },
    changeCareer: function(event){
      var career = $(event.target).attr('data-target');
      this.selectors.career.html(career);
      this.filter(career);
      app.Application.Model.getInstance().set('career',career);
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

goog.addSingletonGetter(app.Widget.Tabs);