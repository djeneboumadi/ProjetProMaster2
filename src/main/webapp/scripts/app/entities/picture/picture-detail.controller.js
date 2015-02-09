'use strict';

angular.module('ludecolApp')
    .controller('PictureDetailController', function ($scope, $stateParams, Picture, Tag, Picture, Level) {
        $scope.picture = {};
        $scope.load = function (id) {
            Picture.get({id: id}, function(result) {
              $scope.picture = result;
            });
        };
        $scope.load($stateParams.id);
    });
