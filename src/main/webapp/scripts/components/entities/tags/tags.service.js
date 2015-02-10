'use strict';

angular.module('ludecolApp')
    .factory('Tags', function ($resource) {
        return $resource('api/tagss/:id', {}, {
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
