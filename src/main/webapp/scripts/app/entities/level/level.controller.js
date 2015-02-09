'use strict';

angular.module('ludecolApp')
    .controller('LevelController', function ($scope, Level, Picture) {
        $scope.levels = [];
        $scope.pictures = Picture.query();
        $scope.loadAll = function() {
            Level.query(function(result) {
               $scope.levels = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Level.save($scope.level,
                function () {
                    $scope.loadAll();
                    $('#saveLevelModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.level = Level.get({id: id});
            $('#saveLevelModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.level = Level.get({id: id});
            $('#deleteLevelConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            Level.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteLevelConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.level = {id_level: null, question: null, species_list: null, id: null};
        };
    });
