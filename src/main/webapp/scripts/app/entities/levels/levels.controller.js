'use strict';

angular.module('ludecolApp')
    .controller('LevelsController', function ($scope, Levels) {
        $scope.levelss = [];
        $scope.loadAll = function() {
            Levels.query(function(result) {
               $scope.levelss = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Levels.save($scope.levels,
                function () {
                    $scope.loadAll();
                    $('#saveLevelsModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.levels = Levels.get({id: id});
            $('#saveLevelsModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.levels = Levels.get({id: id});
            $('#deleteLevelsConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            Levels.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteLevelsConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.levels = {id_level: null, question: null, species_list: null, id: null};
        };
    });
