goog.provide('app.Collection');
goog.require('app.Model');

app.Collection.Create = function Create(Class,x){
    return new app.Collection[Class](x);
};

app.Collection.Users = Backbone.Collection.extend({
    url: '/server/users',
    model: app.Model.User
});

app.Collection.Newsfeeds = Backbone.Collection.extend({
    url: '/server/newsfeeds',
    model: app.Model.Newsfeed
});

app.Collection.Comments = Backbone.Collection.extend({
    url: '/server/comments',
    model: app.Model.Comment
});

