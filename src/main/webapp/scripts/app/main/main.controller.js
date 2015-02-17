'use strict';

angular.module('ludecolApp')
    .controller('MainController', function ($scope, Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });        
        $scope.getAnnotations = function(el) {
        	  var all = anno.getAnnotations();
        	  var message = "";
        	  all.forEach(function(entry) {
        	  	var etu = entry.shapes[0].geometry;
        	  	var x = etu.x + etu.width/2;
        	  	var y = etu.y + etu.height/2;
        	  		  	
        	    message += "text : " + entry.text + "	|	posx :" + x + "	|	posy :" +  y + " \r ";
        	  });
        	  $scope.message = message;
        	  
          	 };
    });



angular.module('ludecolApp')
	.directive('annotorious', function() {
	  return {
	    restrict : 'A',
	    link : function(scope, element) {
	    	
	    	element[0].addEventListener('load', function() {
	    		anno.makeAnnotatable(element[0]);
            });
	    
	    	
    	}
  	};
});

  
