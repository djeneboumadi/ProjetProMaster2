'use strict';

angular.module('ludecolApp')
    .controller('TagsDetailController', function ($scope, $stateParams, Tags) {
        $scope.tags = {};
        $scope.load = function (id) {
            Tags.get({id: id}, function(result) {
              $scope.tags = result;
            });
        };
        $scope.load($stateParams.id);
    });
