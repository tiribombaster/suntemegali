'use strict';

angular.module('suntemegaliApp')
    .controller('JobDetailController', function ($scope, $stateParams, Job) {
        $scope.job = {};
        $scope.load = function (id) {
            Job.get({id: id}, function(result) {
              $scope.job = result;
            });
        };
        $scope.load($stateParams.id);
    });
