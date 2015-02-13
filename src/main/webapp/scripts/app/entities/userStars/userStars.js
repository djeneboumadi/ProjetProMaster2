'use strict';

angular.module('ludecolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('userStars', {
                parent: 'entity',
                url: '/userStars',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userStars/userStarss.html',
                        controller: 'UserStarsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('userStars');
                        return $translate.refresh();
                    }]
                }
            })
            .state('userStarsDetail', {
                parent: 'entity',
                url: '/userStars/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userStars/userStars-detail.html',
                        controller: 'UserStarsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('userStars');
                        return $translate.refresh();
                    }]
                }
            });
    });
