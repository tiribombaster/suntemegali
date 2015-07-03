'use strict';

angular.module('suntemegaliApp')
    .controller('JobController', function ($scope, Job, ParseLinks) {
        $scope.jobs = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Job.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.jobs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Job.get({id: id}, function(result) {
                $scope.job = result;
                $('#saveJobModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.job.id != null) {
                Job.update($scope.job,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Job.save($scope.job,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Job.get({id: id}, function(result) {
                $scope.job = result;
                $('#deleteJobConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Job.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteJobConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveJobModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.job = {name: null, city: null, description: null, requirements: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
