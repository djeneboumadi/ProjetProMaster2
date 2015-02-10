'use strict';

angular.module('ludecolApp')
    .controller('PicturesController', function ($scope, Pictures) {
        $scope.picturess = [];
        $scope.loadAll = function() {
            Pictures.query(function(result) {
               $scope.picturess = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Pictures.save($scope.pictures,
                function () {
                    $scope.loadAll();
                    $('#savePicturesModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.pictures = Pictures.get({id: id});
            $('#savePicturesModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.pictures = Pictures.get({id: id});
            $('#deletePicturesConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            Pictures.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePicturesConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.pictures = {id_picture: null, url_picture: null, height: null, width: null, matrix_position: null, id: null};
        };
    });
