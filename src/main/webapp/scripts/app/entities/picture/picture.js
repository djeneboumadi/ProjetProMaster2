'use strict';

angular.module('ludecolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('picture', {
                parent: 'entity',
                url: '/picture',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/picture/pictures.html',
                        controller: 'PictureController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('picture');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pictureDetail', {
                parent: 'entity',
                url: '/picture/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/picture/picture-detail.html',
                        controller: 'PictureDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('picture');
                        return $translate.refresh();
                    }]
                }
            });
    });
