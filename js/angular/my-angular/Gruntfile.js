module.exports = function (grunt) {

  grunt.loadNpmTasks('grunt-contrib-clean');
  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-contrib-copy');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-recess');
  grunt.loadNpmTasks('grunt-html2js');


  // Default task.
  grunt.registerTask('default', [
    'clean',
    'copy',
    'html2js',
    'concat',
    'uglify'
  ]);
  grunt.registerTask('devWatch', [
    'watch'
  ]);


  // Project configuration.
  grunt.initConfig({
      distdir: 'dist',
      pkg: grunt.file.readJSON('package.json'),
      banner: '' +
      '/*! <%= pkg.title || pkg.name %> - v<%= pkg.version %> - <%= grunt.template.today("yyyy-mm-dd") %>\n' +
      '<%= pkg.homepage ? " * " + pkg.homepage + "\\n" : "" %>' +
      ' * Copyright (c) <%= grunt.template.today("yyyy") %> <%= pkg.author %>;\n' +
      ' * Licensed <%= _.pluck(pkg.licenses, "type").join(", ") %>\n */\n',

      s: {},

      src: {
        js: ['src/**/*.js'],
        tpl: 'src/app/**/*.tpl.html',
        jsTpl: ['<%= distdir %>/work/templates/**/*.js'],
        specs: ['test/**/*.spec.js'],
        scenarios: ['test/**/*.scenario.js'],
        html: ['src/index.html'],

        less: ['src/less/stylesheet.less'], // recess:build doesn't accept ** in its file patterns
        lessWatch: ['src/less/**/*.less']
      },

      clean: ['<%= distdir %>/*'],

      copy: {
        assets: {
          files: [
            {
              expand: true,
              cwd: 'src/assets/',
              dest: '<%= distdir %>',
              src: '**'
            },
            {
              expand: true,
              cwd: 'src/app',
              src: '**/*.json',
              dest: '<%= distdir %>'
            }
          ]

        },
        angular: {
          files: [
            {
              expand: true,
              cwd: 'bower_components/angular',
              src: '*.min.*',
              dest: '<%= distdir %>/libs/angular/'
            },
            {
              expand: true,
              cwd: 'bower_components/angular-resource',
              src: '*.min.*',
              dest: '<%= distdir %>/libs/angular-resource/'
            },
            {
              expand: true,
              cwd: 'bower_components/angular-route',
              src: '*.min.*',
              dest: '<%= distdir %>/libs/angular-route/'
            },
            {
              expand: true,
              cwd: 'bower_components/angular-ui/build/',
              src: '*.min.*',
              dest: '<%= distdir %>/libs/angular-ui/'
            },
            {
              expand: true,
              cwd: 'bower_components/bootstrap/dist/css/',
              src: '*.min.*',
              dest: '<%= distdir %>/libs/bootstrap/css/'
            },
            {
              expand: true,
              cwd: 'bower_components/bootstrap/dist/fonts/',
              src: '*',
              dest: '<%= distdir %>/libs/bootstrap/fonts/'
            },
            {
              expand: true,
              cwd: 'bower_components/bootstrap/dist/js/',
              src: '*.min.*',
              dest: '<%= distdir %>/libs/bootstrap/js/'
            },
            {
              expand: true,
              cwd: 'bower_components/jquery/dist/',
              src: '*.min.*',
              dest: '<%= distdir %>/libs/jquery/'
            }
          ]
        }
      },


      html2js: {
        app: {
          options: {
            base: 'src/app'
          },
          src: ['<%= src.tpl %>'],
          dest: '<%= distdir %>/work/templates/app.js',
          module: 'jujn-www-front.templates'
        }
      },

      concat: {
        dist: {
          options: {
            banner: "<%= banner %>"
          },
          src: ['<%= src.js %>', '<%= src.jsTpl %>'],
          dest: '<%= distdir %>/<%= pkg.name %>.js'
        },

        index: {
          src: ['src/index.html'],
          dest: '<%= distdir %>/index.html',
          options: {
            process: true
          }
        }
      },

      uglify: {
        dist: {
          options: {
            banner: "<%= banner %>",
            sourceMap: true,
            sourceMapName: function (path) {
              return path.replace(/\.js$/, ".map")
            }
          },
          src: ['<%= distdir %>/<%= pkg.name %>.js'],
          dest: '<%= distdir %>/<%= pkg.name %>.min.js'
        }
      },

      recess: {
        build: {
          files: {
            '<%= distdir %>/<%= pkg.name %>.css': ['<%= src.less %>']
          },
          options: {
            compile: true
          }
        },
        min: {
          files: {
            '<%= distdir %>/<%= pkg.name %>.css': ['<%= src.less %>']
          },
          options: {
            compress: true
          }
        }
      },

      watch: {
        all: {
          files: ['src/**/*'],
          tasks: ['default']
        }
      },

      jshint: {
        files: ['gruntFile.js', '<%= src.js %>', '<%= src.jsTpl %>', '<%= src.specs %>', '<%= src.scenarios %>'],
        options: {
          curly: true,
          eqeqeq: true,
          immed: true,
          latedef: true,
          newcap: true,
          noarg: true,
          sub: true,
          boss: true,
          eqnull: true,
          globals: {}
        }
      }
    }
  );

};