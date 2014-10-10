goog.require('app.Collection');
goog.provide('app.DataBase');

app.DataBase = function DataBase(){
  this.schema = {
    Users: app.Collection.Create('Users'),
    Newsfeeds: app.Collection.Create('Newsfeeds'),
    Comments: app.Collection.Create('Comments')
  };
  
  this.fetchInterval = 5000;
  this.fetch();
};

app.DataBase.prototype.get = function (schema){
    return this.schema[schema];
};

app.DataBase.prototype.fetch = function(){
   for(var i in this.schema){
      this.schema[i].fetch();
  }
};

goog.addSingletonGetter(app.DataBase);