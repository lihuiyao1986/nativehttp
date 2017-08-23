cordova.define("cordova-plugin-nativehttp.NativeHttp", function(require, exports, module) {
function NativeCache() {
}

NativeHttp.prototype.request = function (options, successCallback, errorCallback) {
  cordova.exec(successCallback, errorCallback, "NativeHttp", "request", [options]);
};

NativeHttp.install = function () {
  if (!window.plugins) {
    window.plugins = {};
  }

  window.plugins.nativehttp = new NativeHttp();

  return window.plugins.nativehttp;
};

cordova.addConstructor(NativeHttp.install);

});
