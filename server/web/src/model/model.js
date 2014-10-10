goog.provide('app.Model');

app.Model.Create = function(Class,x){
  return new app.Model[Class](x);
};

app.Model._base = {
  type: 'Base',
  id: null
};

app.Model.Base = Backbone.Model.extend({
  defaults: app.Model._base,
  
  initialize: function(){
    if(this.get('id') === null){
      this.set('id', Math.round(Math.random()*10000000)); 
    }
  },
  
  validate: function(attrs,options){
    
  }
});

app.Model._newsfeed = _.defaults({
  type: 'Newsfeed',
  title: 'NewsFeed',
  visible: true,
  available: true
},app.Model._base);

app.Model.Newsfeed = app.Model.Base.extend({
  defaults: app.Model._newsfeed,
  urlRoot: '/server/newsfeeds',
  
  initialize: function(){
    app.Model.Base.prototype.initialize.apply(this);
  }
});

app.Model._comment = _.defaults({
  type: 'Comment',
  parent: null,
  user: 'Anonymous',
  content: 'I am a Comment',
  likes: 0,
  visible: true,
  available: true
},app.Model._base);

app.Model.Comment = app.Model.Base.extend({
  defaults: app.Model._comment,
  urlRoot: '/server/comments',
  
  initialize: function(){
    app.Model.Base.prototype.initialize.apply(this);
  },
  
  validate: function(attrs,options){
    app.Model.Base.prototype.validate.apply(this,attrs,options);
    if(attrs.id === attrs.parent){
      return 'Validation Error';
    }
  }
});

app.Model._user = _.defaults({
  type: 'User',
  name: 'Anonymous',
  category: 'student',
  connected: true
},app.Model._base);

app.Model.User = app.Model.Base.extend({
  defaults: app.Model._user,
  urlRoot: '/server/users'
});

app.Model._session = _.defaults({
  type: 'Session',
  user: '',
  password: '',
  date: null,
  valid: false
},app.Model._base);

app.Model.Session = app.Model.Base.extend({
  defaults: app.Model._session,
  urlRoot: '/server/sessions'
});

goog.addSingletonGetter(app.Model.Session);
