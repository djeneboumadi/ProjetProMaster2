'use strict';

angular.module('ludecolApp')
    .factory('Level', function ($resource) {
        return $resource('api/levels/:id', {}, {
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
