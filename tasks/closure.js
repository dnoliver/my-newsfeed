'use strict';

module.exports = function (grunt, options) {
  options = options || {};
  
  var taskConfig = {
    options: {
      closureLibraryPath: './node_modules/closure-library',
      root_with_prefix: [
        '"src/ ../../../../src/"'
      ]
    },
    all: {
      dest: 'src/deps.js'
    }
  };
  
  grunt.config.set('closureDepsWriter',taskConfig);
};
