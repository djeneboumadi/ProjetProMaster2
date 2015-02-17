'use strict';

angular.module('ludecolApp')
    .controller('MainController', function ($scope, Principal,$http) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });        
        $scope.$on('$viewContentLoaded', function(){
        	 var viewer = OpenSeadragon({
           	  id: "openseadragon",
           	  prefixUrl: "openseadragon/images/",
           	  showNavigator: false,
           	  tileSources: {
           	    type: 'legacy-image-pyramid',
           	    levels:[{
           	      url: '2003rosen1799/0001q.jpg',
           	      height: 889,
           	      width:  600
           	    },{
           	      url: '2003rosen1799/0001r.jpg',
           	      height: 2201,
           	      width:  1485
           	    },{
           	      url: '2003rosen1799/0001v.jpg',
           	      height: 4402,
           	      width:  2970
           	    }]
           	  }
           	});
        	 $scope.myannotations = [];
	    		anno.addHandler("onAnnotationCreated", function (an) {
	    			$scope.myannotations.push(an);
	    		});
	    		anno.addHandler("onAnnotationRemoved", function (an) {
	    			$scope.myannotations.splice($scope.myannotations.indexOf(an),1);
	    		});
	    		anno.addHandler("onAnnotationUpdated", function (an,an1) {
	    			console.log('toto', arguments);
	    			$scope.myannotations.splice($scope.myannotations.indexOf(an),1);
	    			$scope.myannotations.push(an1);
	    			    		});
           anno.makeAnnotatable(viewer);

        });
        
        $scope.getAnnotations = function(el) {
        	  var all = $scope.myannotations;
        	  var message = "";
        	  var annots = []
        	  
        	  all.forEach(function(entry) {
        		var etu = entry.shapes[0].geometry;
        	  	var x = etu.x + etu.width/2;
        	  	var y = etu.y + etu.height/2;
        	  	var annot = new Object();
        	  	annot.x= x;
        	  	annot.y= x;
        	  	annot.text= entry.text;
        	  	annots.push(annot);
        	    message += "text : " + entry.text + "	|	posx :" + x + "	|	posy :" +  y + " \r ";
        	  });
        	  $scope.message = message;
        	  $http.post('/api/tags/push', annots).
        	  success(function(data, status, headers, config) {
        	    console.log("ok");
        	  }).
        	  error(function(data, status, headers, config) {
          	    console.log(data);

        	  });
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

function annotate() {
    var button = document.getElementById('map-annotate-button');
    button.style.color = '#FFF';

    anno.activateSelector(function() {
      // Reset button style
      button.style.color = '#000';
    });
  }

  
