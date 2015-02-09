'use strict';

angular.module('ludecolApp')
    .controller('LevelDetailController', function ($scope, $stateParams, Level, Picture) {
        $scope.level = {};
        $scope.load = function (id) {
            Level.get({id: id}, function(result) {
              $scope.level = result;
            });
        };
        $scope.load($stateParams.id);
    });
