goog.provide('app.Config');

app.Config = {
  COLLECTION_UPDATE_INTERVAL: 15 * 1000,
  MODEL: {
    NEWSFEED: '/ubp/model/newsfeed',
    POST: '/ubp/model/post',
    LIKE: '/ubp/model/like',
    SHARE: '/ubp/model/share',
    COMMENT: '/ubp/model/comment',
    USER: '/ubp/account'
  },
  QUERY: {
    NEWSFEED: '/ubp/query/newsfeed?',
    POST: '/ubp/query/post?',
    LIKE: '/ubp/query/like?',
    COMMENT: '/ubp/query/comment?',
    FEED: '/ubp/query/feed?size=5',
    RANKING: '/ubp/query/ranking?'
  }
};
