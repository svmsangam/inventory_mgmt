var notifyApp = angular.module('notifyapp', []);

notifyApp.controller('notifyCtrl', function($scope, $http, $interval) {
	load_notifications();
	$interval(function() {
		load_notifications();
	}, 500000);
	
	function load_notifications() {
		$http({
			method : 'GET',
			url : 'viewStockNotifications',
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		}).then(function(response) {
			$scope.notifyList = response.data;
		});
    }
});