goog.provide('app.Event');

app.Event.Newsfeed = {
  comment: 'newsfeed::comment',
  toggle: 'newsfeed::toggle'
};

app.Event.NewsfeedItem = {
  load: 'newsfeeditem::load'
};

app.Event.Comment = {
  share: 'comment::share',
  like: 'comment::like'
};

app.Event.CommentCollection = {
  comment: 'comment::comment'
};