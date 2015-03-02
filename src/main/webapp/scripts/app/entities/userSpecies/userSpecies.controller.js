'use strict';

angular.module('ludecolApp')
    .controller('UserSpeciesController', function ($scope, UserSpecies) {
        $scope.userSpeciess = [];
        $scope.loadAll = function() {
            UserSpecies.query(function(result) {
               $scope.userSpeciess = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            UserSpecies.save($scope.userSpecies,
                function () {
                    $scope.loadAll();
                    $('#saveUserSpeciesModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.userSpecies = UserSpecies.get({id: id});
            $('#saveUserSpeciesModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.userSpecies = UserSpecies.get({id: id});
            $('#deleteUserSpeciesConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            UserSpecies.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUserSpeciesConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.userSpecies = {id: null, species: null, user: null};
        };
    });
