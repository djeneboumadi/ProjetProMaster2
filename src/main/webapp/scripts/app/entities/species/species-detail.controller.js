'use strict';

angular.module('ludecolApp')
    .controller('SpeciesDetailController', function ($scope, $stateParams, Species) {
        $scope.species = {};
        $scope.load = function (id) {
            Species.get({id: id}, function(result) {
              $scope.species = result;
            });
        };
        $scope.load($stateParams.id);
    });
