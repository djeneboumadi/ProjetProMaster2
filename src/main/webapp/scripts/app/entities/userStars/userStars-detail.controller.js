'use strict';

angular.module('ludecolApp')
    .controller('UserStarsDetailController', function ($scope, $stateParams, UserStars) {
        $scope.userStars = {};
        $scope.load = function (id) {
            UserStars.get({id: id}, function(result) {
              $scope.userStars = result;
            });
        };
        $scope.load($stateParams.id);
    });
