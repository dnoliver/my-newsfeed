goog.provide('app.Config');

app.Config = {
  COLLECTION_UPDATE_INTERVAL: 15 * 1000,
  MODEL: {
    NEWSFEED: '/ubp/model/newsfeed',
    POST: '/ubp/model/post',
    LIKE: '/ubp/model/like',
    COMMENT: '/ubp/model/comment',
    USER: '/ubp/model/user',
    SESSION: '/ubp/model/session'
  },
  QUERY_URL: '/ubp/query/',
  COLLECTION_URL: '/ubp/collection'
};
