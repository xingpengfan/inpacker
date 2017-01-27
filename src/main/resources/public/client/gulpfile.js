var gulp = require('gulp');
var browserSync = require('browser-sync');

gulp.task('serve', [], function() {
  // .init starts the server
  browserSync.init({
    server: "./app",
    port: 3000
  });

  gulp.watch(['*.html', 'css/**/*.css', 'js/**/*.js'], {
    cwd: 'app'
  }, browserSync.reload);
});
