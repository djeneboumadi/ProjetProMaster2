'use strict';

angular.module('ludecolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('species', {
                parent: 'entity',
                url: '/species',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/species/speciess.html',
                        controller: 'SpeciesController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('species');
                        return $translate.refresh();
                    }]
                }
            })
            .state('speciesDetail', {
                parent: 'entity',
                url: '/species/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/species/species-detail.html',
                        controller: 'SpeciesDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('species');
                        return $translate.refresh();
                    }]
                }
            });
    });
