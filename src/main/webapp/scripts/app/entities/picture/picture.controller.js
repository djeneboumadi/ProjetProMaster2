'use strict';

angular.module('ludecolApp')
    .controller('PictureController', function ($scope, Picture, Tag, Picture, Level) {
        $scope.pictures = [];
        $scope.tags = Tag.query();
        $scope.pictures = Picture.query();
        $scope.levels = Level.query();
        $scope.loadAll = function() {
            Picture.query(function(result) {
               $scope.pictures = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Picture.save($scope.picture,
                function () {
                    $scope.loadAll();
                    $('#savePictureModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.picture = Picture.get({id: id});
            $('#savePictureModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.picture = Picture.get({id: id});
            $('#deletePictureConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            Picture.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePictureConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.picture = {id_picture: null, url_picture: null, height: null, width: null, pos_matrix: null, id: null};
        };
    });
