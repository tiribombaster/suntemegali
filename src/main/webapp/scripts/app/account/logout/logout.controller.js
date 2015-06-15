'use strict';

angular.module('suntemegaliApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
