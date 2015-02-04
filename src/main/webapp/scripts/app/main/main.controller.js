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