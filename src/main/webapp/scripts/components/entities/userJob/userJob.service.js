'use strict';

angular.module('suntemegaliApp')
    .factory('UserJob', function ($resource, DateUtils) {
        return $resource('api/userJobs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.applyDate = DateUtils.convertLocaleDateFromServer(data.applyDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.applyDate = DateUtils.convertLocaleDateToServer(data.applyDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.applyDate = DateUtils.convertLocaleDateToServer(data.applyDate);
                    return angular.toJson(data);
                }
            }
        });
    });
