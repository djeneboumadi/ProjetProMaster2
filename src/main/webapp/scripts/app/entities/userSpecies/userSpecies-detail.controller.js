'use strict';

angular.module('ludecolApp')
    .controller('UserSpeciesDetailController', function ($scope, $stateParams, UserSpecies) {
        $scope.userSpecies = {};
        $scope.load = function (id) {
            UserSpecies.get({id: id}, function(result) {
              $scope.userSpecies = result;
            });
        };
        $scope.load($stateParams.id);
    });
