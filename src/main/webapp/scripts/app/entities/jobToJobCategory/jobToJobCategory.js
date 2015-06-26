'use strict';

angular.module('suntemegaliApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('jobToJobCategory', {
                parent: 'entity',
                url: '/jobToJobCategory',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'suntemegaliApp.jobToJobCategory.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobToJobCategory/jobToJobCategorys.html',
                        controller: 'JobToJobCategoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jobToJobCategory');
                        return $translate.refresh();
                    }]
                }
            })
            .state('jobToJobCategoryDetail', {
                parent: 'entity',
                url: '/jobToJobCategory/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'suntemegaliApp.jobToJobCategory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobToJobCategory/jobToJobCategory-detail.html',
                        controller: 'JobToJobCategoryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jobToJobCategory');
                        return $translate.refresh();
                    }]
                }
            });
    });
