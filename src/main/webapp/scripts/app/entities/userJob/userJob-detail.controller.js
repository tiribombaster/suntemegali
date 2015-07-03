'use strict';

angular.module('suntemegaliApp')
    .controller('UserJobDetailController', function ($scope, $stateParams, UserJob, User, Job) {
        $scope.userJob = {};
        $scope.load = function (id) {
            UserJob.get({id: id}, function(result) {
              $scope.userJob = result;
            });
        };
        $scope.load($stateParams.id);
    });
