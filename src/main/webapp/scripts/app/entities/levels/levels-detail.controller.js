'use strict';

angular.module('ludecolApp')
    .controller('LevelsDetailController', function ($scope, $stateParams, Levels) {
        $scope.levels = {};
        $scope.load = function (id) {
            Levels.get({id: id}, function(result) {
              $scope.levels = result;
            });
        };
        $scope.load($stateParams.id);
    });
