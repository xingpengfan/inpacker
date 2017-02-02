var gulp = require('gulp');
var browserSync = require('browser-sync');
var proxy = require('http-proxy-middleware');

var apiProxy = proxy('/api', {
    target: 'http://localhost:8080',
    changeOrigin: false,
    logLevel: 'debug'
});

gulp.task('serve', [], function() {
  // .init starts the server
  browserSync.init({
    server: "./",
    port: 3000,
    middleware: [apiProxy],
  });

  gulp.watch(['**/*.html', '**/*.css', '**/*.js'], {
    cwd: '.'
  }, browserSync.reload);
});
