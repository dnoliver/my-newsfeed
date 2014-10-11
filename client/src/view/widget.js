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
  selector: "#ModalShareTemplate"
});

goog.addSingletonGetter(app.Widget.Share);

app.Widget.Login = app.Widget.Modal.extend({
  selector: "#ModalLoginTemplate",
  
  initialize: function(){
    app.Widget.Modal.prototype.initialize.apply(this);
    this.selectors = {
      user: null,
      password: null,
      form: null
    };
    
    this.listenTo(app.Model.Session.getInstance(),'change:valid',function(model,isValid){
      this.modal(isValid === true? 'hide':'show');
    });
  },
  
  actions: {
    login: function(target){
      var data = {
        user: target.selectors.user.val(),
        password: target.selectors.password.val(),
        valid: true
      };
      
      app.Model.Session.getInstance().save(data);
      //target.selectors.user.val('');
      //target.selectors.password.val('');
    }
  }
});

goog.addSingletonGetter(app.Widget.Login);

app.Widget.Navbar = app.View.Template.extend({
  selector: "#NavbarTemplate",
  
  initialize: function(){
    app.View.Template.prototype.initialize.apply(this);
    this.selectors = {};
  },
  
  actions: {
    logout: function(target){
      app.Model.Session.getInstance().set('valid',false);
    },
    createNewsfeed: function(target){
      
    }
  }
});

goog.addSingletonGetter(app.Widget.Navbar);

app.Widget.Tabs = app.View.Template.extend({
  selector: '#TabsTemplate',
  
  builders: {
    tab: _.template('<li><a role="tab" data-target="#<%= id %>" data-toggle="tab" data-index="<%= index %>"><%= title %></a></li>'),
    panel: _.template('<div class="tab-pane" id="<%= id %>"></div>')
  },
  
  initialize: function(){
    app.View.Template.prototype.initialize.apply(this);
    this.selectors = {
      tabs: null,
      panels: null
    };
    
    this.childs = [];
  },
  
  events: {
    'click a[role=tab]': 'onTabClicked'
  },
  
  onTabClicked: function(event){
    $(event.target).tab('show');
    event.stopPropagation();
  },
  
  add: function(child){
    this.childs.push(child);
    var self = this;
    var id = child.title + child.id;
    var tab = this.builders.tab({title:child.title,id:id,index: self.childs.length});
    var panel = this.builders.panel({id:id});
    
    this.selectors.tabs.append(tab);
    this.selectors.panels.append(panel);
    this.selectors.panels.find('#' + id ).append(child.view.render().$el);
  },
  
  showTab: function(index){
    this.selectors.tabs.find('[data-index=' + index + ']').click();
  }
});

goog.addSingletonGetter(app.Widget.Tabs);