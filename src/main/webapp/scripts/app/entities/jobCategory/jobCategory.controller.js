'use strict';

angular.module('suntemegaliApp')
    .controller('JobCategoryController', function ($scope, JobCategory, ParseLinks) {
        $scope.jobCategorys = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            JobCategory.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.jobCategorys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            JobCategory.get({id: id}, function(result) {
                $scope.jobCategory = result;
                $('#saveJobCategoryModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.jobCategory.id != null) {
                JobCategory.update($scope.jobCategory,
                    function () {
                        $scope.refresh();
                    });
            } else {
                JobCategory.save($scope.jobCategory,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            JobCategory.get({id: id}, function(result) {
                $scope.jobCategory = result;
                $('#deleteJobCategoryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            JobCategory.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteJobCategoryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveJobCategoryModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.jobCategory = {name: null, description: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
