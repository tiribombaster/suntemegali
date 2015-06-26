'use strict';

angular.module('suntemegaliApp')
    .controller('JobToJobCategoryController', function ($scope, JobToJobCategory, Job, JobCategory) {
        $scope.jobToJobCategorys = [];
        $scope.jobs = Job.query();
        $scope.jobcategorys = JobCategory.query();
        $scope.loadAll = function() {
            JobToJobCategory.query(function(result) {
               $scope.jobToJobCategorys = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            JobToJobCategory.get({id: id}, function(result) {
                $scope.jobToJobCategory = result;
                $('#saveJobToJobCategoryModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.jobToJobCategory.id != null) {
                JobToJobCategory.update($scope.jobToJobCategory,
                    function () {
                        $scope.refresh();
                    });
            } else {
                JobToJobCategory.save($scope.jobToJobCategory,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            JobToJobCategory.get({id: id}, function(result) {
                $scope.jobToJobCategory = result;
                $('#deleteJobToJobCategoryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            JobToJobCategory.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteJobToJobCategoryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveJobToJobCategoryModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.jobToJobCategory = {id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
