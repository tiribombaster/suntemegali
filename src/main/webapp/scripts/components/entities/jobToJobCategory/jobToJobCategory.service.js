'use strict';

angular.module('suntemegaliApp')
    .factory('JobToJobCategory', function ($resource, DateUtils) {
        return $resource('api/jobToJobCategorys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
