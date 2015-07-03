'use strict';

angular.module('suntemegaliApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('jobCategory', {
                parent: 'entity',
                url: '/jobCategory',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'suntemegaliApp.jobCategory.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobCategory/jobCategorys.html',
                        controller: 'JobCategoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jobCategory');
                        return $translate.refresh();
                    }]
                }
            })
            .state('jobCategoryDetail', {
                parent: 'entity',
                url: '/jobCategory/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'suntemegaliApp.jobCategory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobCategory/jobCategory-detail.html',
                        controller: 'JobCategoryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jobCategory');
                        return $translate.refresh();
                    }]
                }
            });
    });
