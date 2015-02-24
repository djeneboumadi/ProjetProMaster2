'use strict';

angular.module('ludecolApp')
.controller('MainController', function ($scope, Principal, Species, Pictures, $http) {
	Principal.identity().then(function(account) {
		$scope.account = account;
		$scope.isAuthenticated = Principal.isAuthenticated;
	});   

	$scope.speciess = [];
	$scope.loadAllSpecies = function() {
		Species.query(function(result) {
			$scope.speciess = result;
		});
	};
	$scope.loadAllSpecies();
	
	
	$scope.picturess = [];
	$scope.loadAllPictures = function() {
		Pictures.query(function(result) {
			$scope.picturess = result;
		});
	};
	$scope.loadAllPictures();

	$scope.$on('$viewContentLoaded', function(){
		$('#openseadragon').css("width",$('#column').width());
		$('#openseadragon').css("height", $('#column').height());
		$('#openseadragon').css("background-color", '#fff');

		

		var viewer = OpenSeadragon({
			id: "openseadragon",
			prefixUrl: "openseadragon/images/",
			showNavigator: false,
			tileSources:   "/tiles/helloworld.dzi"
				/*           	  tileSources: {
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
           	  }*/
		});
		annotorious.plugin.HelloWorldPlugin = function(opt_config_options) { }

		annotorious.plugin.HelloWorldPlugin.prototype.initPlugin = function(anno) {
			// Add initialization code here, if needed (or just skip this method if not)
		}
		annotorious.plugin.HelloWorldPlugin.prototype.onInitAnnotator = function(annotator) {
			// A Field can be an HTML string or a function(annotation) that returns a string
			annotator.editor.addField(function(annotation) {
				var container = document.createElement('div');  
				
				var node = document.createElement('div');
				var newlabel = document.createElement("Label");
				newlabel.innerHTML = "Species : ";
				
				var select = document.createElement("select");
				select.id = "species";
				select.name="species";
				select.style.color="grey";
				
				for(i=0; i<$scope.speciess.length; i++){
								
				var option = document.createElement("option");
				option.style.color="grey";
				option.value=i;
				option.innerHTML= $scope.speciess[i].name;
				option.selected="";
				select.appendChild(option);
				}
	
				node.appendChild(newlabel);
				node.appendChild(select)
				
				container.appendChild(node);
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
			var select = document.getElementById("species");
			var index = select.options[select.selectedIndex].value;
			console.log($scope.speciess[index].name);
			annot.x= x;
			annot.y= x;
			annot.species=$scope.speciess[index];
			annot.text= entry.text;
			annots.push(annot);
			//message += "text : " + entry.text + "	|	posx :" + x + "	|	posy :" +  y + " \r ";
		});
		$scope.message = message;
		$http.post('/api/tags/push', annots).
		success(function(data, status, headers, config) {
			console.log("ok");
		}).
		error(function(data, status, headers, config) {
			console.log(data);

		});
		launchLevel()
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
function launchLevel()
{
	hideUnHide("imageZoomable");
	hideUnHide("gallery");
}
function hideUnHide(anId)
{
	var node = document.getElementById(anId);
	if (node.style.display=="none")
	{
		// Contenu caché, le montrer
		node.style.display = "block";
	}
	else
	{
		// Contenu visible, le cacher
		node.style.display = "none";
	}
}  
