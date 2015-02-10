'use strict';

angular.module('ludecolApp')
    .factory('UserSpecies', function ($resource) {
        return $resource('api/userSpeciess/:id', {}, {
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
