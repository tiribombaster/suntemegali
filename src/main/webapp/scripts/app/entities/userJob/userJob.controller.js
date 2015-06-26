'use strict';

angular.module('suntemegaliApp')
    .controller('UserJobController', function ($scope, UserJob, User, Job) {
        $scope.userJobs = [];
        $scope.users = User.query();
        $scope.jobs = Job.query();
        $scope.loadAll = function() {
            UserJob.query(function(result) {
               $scope.userJobs = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            UserJob.get({id: id}, function(result) {
                $scope.userJob = result;
                $('#saveUserJobModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.userJob.id != null) {
                UserJob.update($scope.userJob,
                    function () {
                        $scope.refresh();
                    });
            } else {
                UserJob.save($scope.userJob,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            UserJob.get({id: id}, function(result) {
                $scope.userJob = result;
                $('#deleteUserJobConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            UserJob.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUserJobConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveUserJobModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.userJob = {applyDate: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
