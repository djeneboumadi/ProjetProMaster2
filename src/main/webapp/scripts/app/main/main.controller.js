'use strict';
angular.module('ludecolApp')
    .controller('MainController', function ($scope, Tags, Principal, Levels, UserStars, Species, Pictures, $http) {
        $scope.myannotations = [];
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        function hideUnHide(anId) {
            var node = document.getElementById(anId);
            if (node.style.display=="none") {
                // Contenu caché, le montrer
                node.style.display = "block";
            } else {
                // Contenu visible, le cacher
                node.style.display = "none";
            }
        }

        function loadOpenSeeDragon(name) {
            $('#openseadragon').css("width",$('#column').width());
            $('#openseadragon').css("height", $('#column').height());
            $('#openseadragon').css("background-color", '#fff');

            var viewer = OpenSeadragon({
                id: "openseadragon",
                prefixUrl: "bower_components/openseadragon/built-openseadragon/openseadragon/images/",
                showNavigator: false,
                tileSources:   "tiles/"+name+".dzi"
            });
            annotorious.plugin.HelloWorldPlugin = function(opt_config_options) { };

            annotorious.plugin.HelloWorldPlugin.prototype.initPlugin = function(anno) {
                // Add initialization code here, if needed (or just skip this method if not)
            };
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

                    for(var i=0; i<$scope.speciess.length; i++){

                        var option = document.createElement("option");
                        option.style.color="grey";
                        option.value=i;
                        option.innerHTML= $scope.speciess[i].name;
                        option.selected="";
                        select.appendChild(option);
                    }

                    node.appendChild(newlabel);
                    node.appendChild(select);

                    container.appendChild(node);
                    return container;
                });
            };

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
            $scope.viewer = viewer;
            anno.makeAnnotatable(viewer);
        }

        // Vous avez besoin de commencer par initialiser ce truc au load !
        // Et je ne sais pas ce que vous voulez faire avec le name, mais l'idée c'est ça
        $scope.$on('$viewContentLoaded', function () {
         //   loadOpenSeeDragon('marais');
        });

        $scope.launchLevel = function (nb) {
            if(nb != null){
                $scope.myPicture =nb;
                var picture = nb.url_picture;
                var base = picture.substring(picture.lastIndexOf('/') + 1);
                if(base.lastIndexOf(".") != -1){
                    base = base.substring(0, base.lastIndexOf("."));
                }
                //console.log(base);
                loadOpenSeeDragon(base);
            }
            else
            {
                $scope.viewer.destroy();
                //console.log($scope.viewer);
            }
            hideUnHide("imageZoomable");
            hideUnHide("gallery");
        };

        $scope.tagss = [];
        $scope.loadAllTags = function() {
            Tags.query(function(result) {

                $scope.tagss = result;
            });
        };
        $scope.loadAllTags();

        $scope.tags_picture = [];
        $scope.loadTagsPicture = function(id) {
            var j =0;
            for(i=0; i<$scope.tagss.length; i++) {
                console.log("après boucle "+ $scope.tagss[i].picture.id);
                if($scope.tagss[i].picture.id == id) {
                    $scope.tags_picture[j]=$scope.tagss[i];
                    j++;
                }
            }
        };

        $scope.speciess = [];
        $scope.loadAllSpecies = function() {
            Species.query(function(result) {
                $scope.speciess = result;
            });
        };
        $scope.loadAllSpecies();

        $scope.levelss = [];
        $scope.loadAllLevels = function () {
            Levels.query(function(result) {
                $scope.levelss = result;
            });
        };
        $scope.loadAllLevels();


        $scope.picturess = [];
        $scope.loadAllPictures = function () {
            Pictures.query(function(result) {
                $scope.picturess = result;
            });
        };
        $scope.loadAllPictures();

        $scope.userstarss = [];
        $scope.loadAllUserStars = function () {
            UserStars.query(function(result) {
                $scope.userstarss = result;
            });
        };
        $scope.loadAllUserStars();

        $scope.getNbStars = function(level){
            var right = 0;

            for(i=0; i<$scope.userstarss.length; i++){
                if($scope.userstarss[i].level.id == level.id && $scope.userstarss[i].user.login == $scope.account.login ){
                    right = $scope.userstarss[i].nb_stars;
                }
            }
            console.log($scope.userstarss)
            return "/assets/images/"+right+"etoiles.png";
        };

      //récupération des annotations (tags) 
    	$scope.getAnnotations = function(el) {
    		var all = $scope.myannotations;
    		var message = "";
    		var annots = [];
    		var star = new Object();
    		console.log($scope.foo);
    		all.forEach(function(entry) {
    			var etu = entry.shapes[0].geometry;
    			var x = etu.x + etu.width/2;
    			var y = etu.y + etu.height/2;
    			var annot = new Object();
    			var select = document.getElementById("species");
    			var index = select.options[select.selectedIndex].value;		

    			annot.x= x;
    			annot.y= y;
    			annot.user=$scope.account.login;
    			annot.species=$scope.speciess[index];
    			annot.text= entry.text;
    			annot.picture=$scope.myPicture;

    			//annots.push(annot); fonction utilisé lors de la participation a jeu réel. Ou l'on a besoin d'enregistrer 
    			//les tags en base

    			//message += "text : " + entry.text + "	|	posx :" + x + "	|	posy :" +  y + " \r ";
    		}
    		);

    		console.log($scope.myPicture.id);
    		$scope.loadTagsPicture($scope.myPicture.id);

    		// test avec de fausse annotations faite par l'utilisateur
    		var annot0 = new Object();
    		annot0.x= 5;
    		annot0.y= 5;
    		annot0.species=$scope.speciess[0]
    		var annot1 = new Object();
    		annot1.x= 10;
    		annot1.y= 30;
    		annot1.species=$scope.speciess[1]
    		var annot2 = new Object();
    		annot2.x= 80;
    		annot2.y= 25;
    		annot2.species=$scope.speciess[2]

    		annots[0]=annot0;
    		annots[1]=annot1;
    		annots[2]=annot2;

    		// comparaison des annotations fait par l'utilisateur et les tags en base
    		var result=0;
    		var length=$scope.tags_picture.length;
    		console.log($scope.tags_picture);
    		for( i=0; i<length; i++){
    			console.log("debut for");
    			for ( j=0; j<annots.length; j++){
    				if( ($scope.tags_picture[i].pos_x - 5) <= annots[j].x && annots[j].x <= ($scope.tags_picture[i].pos_x+5) ){
    					if( ($scope.tags_picture[i].pos_y-5) <= annots[j].y && annots[j].y <= ($scope.tags_picture[i].pos_y+5) ){
    						if($scope.speciess[i].id==annots[j].species.id){
    							result+=1;
    							break;
    						}					
    					}					
    				}					
    			}
    		}

    		//notation du niveau
    		
    		var res = result/length*100;
    		console.log("notation du niveau");
    		if ( res == 100){
    			star.nbStar=5;
    		}else if(res>80){
    			star.nbStar=4;
    		}else if(res>60){
    			star.nbStar=3;
    		}else if(res>40){
    			stars.nbStar=2;	
    		}else if(res>20){
    			star.nbStar=1;	
    		}else{
    			star.nbStar=0;	
    		}
    		star.level = $scope.myLevel
    		star.user=$scope.account.login;
    		// utilisation de la fonction push pour envoyer coté serveur
    		console.log("utilisation de la fonction push");
    		console.log("nbstar :" + star.nbStar)

    		/*$scope.message = message;
    		$http.post('/api/tags/push', annots).
    		success(function(data, status, headers, config) {
    			console.log("ok");
    		}).
    		error(function(data, status, headers, config) {
    			console.log(data);

    		});*/
    		
    		$scope.message = message;
    		$http.post('/api/userStarss/push', star).
    		success(function(data, status, headers, config) {
    			console.log("ok");
    		}).
    		error(function(data, status, headers, config) {
    			console.log(data);

    		});
    		
    		var nothing = null;
    		$scope.launchLevel(nothing)
    	};

        $scope.annotate = function () {
            var button = document.getElementById('map-annotate-button');
            button.style.color = '#FFF';

            anno.activateSelector(function() {
                // Reset button style
                button.style.color = '#000';
            });
        };
    })
    .filter('array', function() {
        return function(arrayLength) {
            arrayLength = Math.ceil(arrayLength);
            var arr = new Array(arrayLength), i = 0;
            for (; i < arrayLength; i++) {
                arr[i] = i;
            }
            return arr;
        };
    });
