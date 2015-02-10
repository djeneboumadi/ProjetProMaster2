'use strict';

angular.module('ludecolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('userSpecies', {
                parent: 'entity',
                url: '/userSpecies',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userSpecies/userSpeciess.html',
                        controller: 'UserSpeciesController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('userSpecies');
                        return $translate.refresh();
                    }]
                }
            })
            .state('userSpeciesDetail', {
                parent: 'entity',
                url: '/userSpecies/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userSpecies/userSpecies-detail.html',
                        controller: 'UserSpeciesDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('userSpecies');
                        return $translate.refresh();
                    }]
                }
            });
    });
