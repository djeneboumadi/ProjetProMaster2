'use strict';

angular.module('ludecolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pictures', {
                parent: 'entity',
                url: '/pictures',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pictures/picturess.html',
                        controller: 'PicturesController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pictures');
                        return $translate.refresh();
                    }]
                }
            })
            .state('picturesDetail', {
                parent: 'entity',
                url: '/pictures/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pictures/pictures-detail.html',
                        controller: 'PicturesDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pictures');
                        return $translate.refresh();
                    }]
                }
            });
    });
