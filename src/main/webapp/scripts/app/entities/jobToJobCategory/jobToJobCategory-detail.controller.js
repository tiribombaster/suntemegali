'use strict';

angular.module('suntemegaliApp')
    .controller('JobToJobCategoryDetailController', function ($scope, $stateParams, JobToJobCategory, Job, JobCategory) {
        $scope.jobToJobCategory = {};
        $scope.load = function (id) {
            JobToJobCategory.get({id: id}, function(result) {
              $scope.jobToJobCategory = result;
            });
        };
        $scope.load($stateParams.id);
    });
