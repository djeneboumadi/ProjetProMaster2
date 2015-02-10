'use strict';

angular.module('ludecolApp')
    .factory('UserStars', function ($resource) {
        return $resource('api/userStarss/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });
