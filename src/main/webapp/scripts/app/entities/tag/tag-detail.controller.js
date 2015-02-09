'use strict';

angular.module('ludecolApp')
    .controller('TagDetailController', function ($scope, $stateParams, Tag, Species, User, Picture) {
        $scope.tag = {};
        $scope.load = function (id) {
            Tag.get({id: id}, function(result) {
              $scope.tag = result;
            });
        };
        $scope.load($stateParams.id);
    });
