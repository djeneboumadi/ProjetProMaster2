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
        	 annotorious.plugin.HelloWorldPlugin = function(opt_config_options) { }

        	 annotorious.plugin.HelloWorldPlugin.prototype.initPlugin = function(anno) {
        	   // Add initialization code here, if needed (or just skip this method if not)
        	 }
        	 $scope.species1 = true;
        	 $scope.species2 = false;
        	 annotorious.plugin.HelloWorldPlugin.prototype.onInitAnnotator = function(annotator) {
        	   // A Field can be an HTML string or a function(annotation) that returns a string
        	   annotator.editor.addField(function(annotation) {
        		   var container = document.createElement('div');  
        		   
        		   var node = document.createElement('div');  
        		   var newlabel = document.createElement("Label");
        		   newlabel.innerHTML = "Species 1";
        			var cb = document.createElement('input');
             	    cb.type = 'checkbox';
             	    cb.name = "species1";
             	    cb.checked = $scope.species1;
             	   cb.addEventListener('click', function() {
             	      $scope.species1= cb.checked;
             	   });
             	  node.appendChild(cb);
             	  node.appendChild(newlabel);
            	   
             	 var node1 = document.createElement('div');  
      		   var newlabel1 = document.createElement("Label");
      		   newlabel1.innerHTML = "Species 2";
      			var cb1 = document.createElement('input');
           	    cb1.type = 'checkbox';
           	    cb1.name = "species2";
           	    cb1.checked = $scope.species2;
           	   cb1.addEventListener('click', function() {
           	      $scope.species2= cb1.checked;
           	   });
           	  node1.appendChild(cb1);
           	  node1.appendChild(newlabel1);
           	  
           	  container.appendChild(node);
           	  container.appendChild(node1);
          	  
             	    return container;
        	   });
        	 }
        
        	 
        	 // Add the plugin like so
        	 anno.addPlugin('HelloWorldPlugin', {});
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
        	  console.log($scope.foo);
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

  
