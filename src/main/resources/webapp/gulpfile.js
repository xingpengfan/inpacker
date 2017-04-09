'use strict';

var gulp = require('gulp');
var browserSync = require('browser-sync');
var proxy = require('http-proxy-middleware');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var babel  = require('gulp-babel');

gulp.task('serve', [], function() {

  let apiProxy = proxy(['/api', '/packs'], {
      target: 'http://localhost:8080',
      changeOrigin: false,
      logLevel: 'debug'
  });

  // .init starts the server
  browserSync.init({
    server: "./src",
    port: 3000,
    middleware: [apiProxy],
  });

  gulp.watch(['./src/**/*.html', './src/**/*.css', './src/**/*.js'], browserSync.reload);
});

gulp.task('buildjs', function() {
    return gulp.src(['./src/inpacker.module.js',
                     './src/inpacker.constants.js',
                     './src/service/*.js',
                     './src/inpacker.config.js',
                     './src/**/*.js',])
    .pipe(concat('inpacker.min.js'))
    .pipe(babel({presets: ['es2015']}))
    .pipe(uglify())
    .pipe(gulp.dest('dist'));
});
