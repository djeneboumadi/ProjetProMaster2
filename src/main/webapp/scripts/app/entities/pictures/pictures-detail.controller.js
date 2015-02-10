'use strict';

angular.module('ludecolApp')
    .controller('PicturesDetailController', function ($scope, $stateParams, Pictures) {
        $scope.pictures = {};
        $scope.load = function (id) {
            Pictures.get({id: id}, function(result) {
              $scope.pictures = result;
            });
        };
        $scope.load($stateParams.id);
    });
