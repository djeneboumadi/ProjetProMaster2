'use strict';

angular.module('ludecolApp')
    .factory('Levels', function ($resource) {
        return $resource('api/levelss/:id', {}, {
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
