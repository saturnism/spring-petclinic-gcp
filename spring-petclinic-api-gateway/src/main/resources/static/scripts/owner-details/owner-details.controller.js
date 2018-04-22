'use strict';

angular.module('ownerDetails')
    .controller('OwnerDetailsController', ['$http', '$state', '$stateParams', function ($http, $state, $stateParams) {
        var self = this;

        $http.get('api/gateway/owners/' + $stateParams.ownerId).then(function (resp) {
            self.owner = resp.data;
        });

        self.deletePet = function(ownerId, petId) {
            var req = $http.delete("api/customer/owners/" + ownerId + "/pets/" + petId);
            req.then(function () {
                $state.reload();
            }, function (response) {
                var error = response.data;
                alert(error.error + "\r\n" + error.errors.map(function (e) {
                    return e.field + ": " + e.defaultMessage;
                }).join("\r\n"));
            });
        };
    }]);
