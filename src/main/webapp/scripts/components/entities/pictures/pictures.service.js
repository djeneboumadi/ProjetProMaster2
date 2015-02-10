'use strict';

angular.module('ludecolApp')
    .factory('Pictures', function ($resource) {
        return $resource('api/picturess/:id', {}, {
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
