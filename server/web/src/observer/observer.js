goog.provide('app.Observer');

app.Observer.Event = function(){
  _.extend(this, Backbone.Events);
};

app.Observer.Event.prototype.notify = function(event,sender){
  this.trigger(event,sender);
};

goog.addSingletonGetter(app.Observer.Event);