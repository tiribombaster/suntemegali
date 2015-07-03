'use strict';

angular.module('suntemegaliApp')
    .controller('JobCategoryDetailController', function ($scope, $stateParams, JobCategory) {
        $scope.jobCategory = {};
        $scope.load = function (id) {
            JobCategory.get({id: id}, function(result) {
              $scope.jobCategory = result;
            });
        };
        $scope.load($stateParams.id);
    });
