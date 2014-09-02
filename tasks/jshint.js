'use strict';

module.exports = function (grunt, options) {
  options = options || {};

  var taskConfig = {
    all: ['src/**/*.js']
  };
  
  grunt.config.set('jshint',taskConfig);
};
