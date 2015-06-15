'use strict';

angular.module('suntemegaliApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


