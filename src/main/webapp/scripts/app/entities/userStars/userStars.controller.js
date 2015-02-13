'use strict';

angular.module('ludecolApp')
    .controller('UserStarsController', function ($scope, UserStars) {
        $scope.userStarss = [];
        $scope.loadAll = function() {
            UserStars.query(function(result) {
               $scope.userStarss = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            UserStars.save($scope.userStars,
                function () {
                    $scope.loadAll();
                    $('#saveUserStarsModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.userStars = UserStars.get({id: id});
            $('#saveUserStarsModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.userStars = UserStars.get({id: id});
            $('#deleteUserStarsConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            UserStars.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUserStarsConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.userStars = {level_played: null, nb_stars: null, id: null};
        };
    });
