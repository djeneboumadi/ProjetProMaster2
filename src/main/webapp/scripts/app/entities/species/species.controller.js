'use strict';

angular.module('ludecolApp')
    .controller('SpeciesController', function ($scope, Species) {
        $scope.speciess = [];
        $scope.loadAll = function() {
            Species.query(function(result) {
               $scope.speciess = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Species.save($scope.species,
                function () {
                    $scope.loadAll();
                    $('#saveSpeciesModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.species = Species.get({id: id});
            $('#saveSpeciesModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.species = Species.get({id: id});
            $('#deleteSpeciesConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            Species.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteSpeciesConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.species = {id: null, name: null, category: null, description: null, url_picture_species: null, is_in_encyclo: null};
        };
    });
