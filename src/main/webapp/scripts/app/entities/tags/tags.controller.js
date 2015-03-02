'use strict';

angular.module('ludecolApp')
    .controller('TagsController', function ($scope, Tags) {
        $scope.tagss = [];
        $scope.loadAll = function() {
            Tags.query(function(result) {
               $scope.tagss = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Tags.save($scope.tags,
                function () {
                    $scope.loadAll();
                    $('#saveTagsModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.tags = Tags.get({id: id});
            $('#saveTagsModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.tags = Tags.get({id: id});
            $('#deleteTagsConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            Tags.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTagsConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.tags = {id: null, pos_x: null, pos_y: null, picture: null, species: null, user: null};
        };
    });
