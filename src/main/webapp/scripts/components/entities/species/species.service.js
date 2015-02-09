'use strict';

angular.module('ludecolApp')
    .factory('Species', function ($resource) {
        return $resource('api/speciess/:id', {}, {
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
