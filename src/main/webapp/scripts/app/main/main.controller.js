'use strict';

angular.module('jhipsterApp')
    .controller('MainController', function ($scope, Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
        $scope.$on('$viewContentLoaded', function(){
          function blackNote() {
            return $(document.createElement('span')).
              addClass('black circle note')
          }
          console.log($('#nutmeg').get());
          $('#nutmeg').annotatableImage(blackNote);

          $('#serialize').click(function(event){
            event.preventDefault();
            console.log("ok");
            $.each($('#nutmeg span.note').seralizeAnnotations(), function(){
              console.log('x: ' + this.x + ' y: ' + this.y + ' response_time: ' + this.response_time + 'ms');
            });

          });
        });

    });
