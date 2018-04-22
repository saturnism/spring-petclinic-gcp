'use strict';

angular.module('ownerList')
    .controller('OwnerListController', ['$http', '$state', function ($http, $state) {
        var self = this;

        $http.get('api/customer/owners').then(function (resp) {
            self.owners = resp.data;
        });

        self.deleteOwner = function(ownerId) {
            var req = $http.delete("api/customer/owners/" + ownerId);
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
