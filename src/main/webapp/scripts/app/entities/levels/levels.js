'use strict';

angular.module('ludecolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('levels', {
                parent: 'entity',
                url: '/levels',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/levels/levelss.html',
                        controller: 'LevelsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('levels');
                        return $translate.refresh();
                    }]
                }
            })
            .state('levelsDetail', {
                parent: 'entity',
                url: '/levels/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/levels/levels-detail.html',
                        controller: 'LevelsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('levels');
                        return $translate.refresh();
                    }]
                }
            });
    });
