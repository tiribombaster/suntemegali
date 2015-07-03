'use strict';

angular.module('suntemegaliApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('userJob', {
                parent: 'entity',
                url: '/userJob',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'suntemegaliApp.userJob.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userJob/userJobs.html',
                        controller: 'UserJobController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('userJob');
                        return $translate.refresh();
                    }]
                }
            })
            .state('userJobDetail', {
                parent: 'entity',
                url: '/userJob/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'suntemegaliApp.userJob.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userJob/userJob-detail.html',
                        controller: 'UserJobDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('userJob');
                        return $translate.refresh();
                    }]
                }
            });
    });
