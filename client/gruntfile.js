module.exports = function(grunt) {

  require('load-grunt-tasks')(grunt);
  
  require('./tasks/jshint')(grunt);
  require('./tasks/closure')(grunt);
  
  grunt.task.registerTask('build',['jshint','closureDepsWriter']);
  grunt.task.registerTask('default',['build']);
};