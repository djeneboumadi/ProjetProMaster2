'use strict';

angular.module('ludecolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('tags', {
                parent: 'entity',
                url: '/tags',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tags/tagss.html',
                        controller: 'TagsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tags');
                        return $translate.refresh();
                    }]
                }
            })
            .state('tagsDetail', {
                parent: 'entity',
                url: '/tags/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tags/tags-detail.html',
                        controller: 'TagsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tags');
                        return $translate.refresh();
                    }]
                }
            });
    });
