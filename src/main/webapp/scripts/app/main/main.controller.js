'use strict';

angular.module('ludecolApp')
    .controller('MainController', function ($scope, Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
        
    });
    
angular.module('ludecolApp')
	.directive('annotorious', function() {
	  return {
	    restrict : 'A',
	    link : function(scope, element) {
	      anno.makeAnnotatable(element[0]);
    	}
  	};
});

  
function getAnnotations(el) {
	var $scope = angular.element(el).scope();
      var all = anno.getAnnotations();
	  var message = "";
	  all.forEach(function(entry) {
	  	var etu = entry.shapes[0].geometry;
	    message += "text : " + entry.text + "	|	posx :" + etu.x + "	|	posy :" +  etu.y + "	|	height : " + etu.height + "	|	width : " + etu.width + " \r ";
	  });
	  $scope.message = message;
	  $scope.$apply();
  	 }