/*global cordova, module*/
var exec = require('cordova/exec');

var ElementSDK = {
    match: function (id, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "ElementFaceMatchingSDK", "match", [id]);
    },
    enroll: function(userId, name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "ElementFaceMatchingSDK", "enroll", [userId, name]);
    }
};

module.exports = ElementSDK;