'use strict';

angular.module('ludecolApp')
    .factory('Picture', function ($resource) {
        return $resource('api/pictures/:id', {}, {
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
