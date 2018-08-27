/**
 * Element Directive for adding new item
 * @param $http
 */
app.directive("selectItemModal",['$http', function($http,Notification){
    return {
        restrict : 'E',
        templateUrl : "partials/selectItemModal.html",
        link : function ($scope, Notification) {
            $scope.hiposServerURL = "/hipos/";
            $scope.customer=1;
            $scope.closeSelectItemSidePopup = function(){
                $scope.showSelectItemSidePopUp = false;
            }
            $scope.updateItemId = function (newItemVal) {
                $scope.item = newItemVal.itemId;
            }
            $scope.appendItem = function (itemId) {
                $scope.itemSearchText = itemId.itemName;
                $scope.itemId = itemId.itemId;
                $scope.item = $scope.itemId;
                $scope.showEmailBox = false;
                $("#selectItem").modal('hide');
            }
            $scope.getItemListForFilter = function (val) {
                $(".loader").css("display", "block");
                if (angular.isUndefined(val)) {1
                    val = "";
                }
                $http.get($scope.hiposServerURL + "/" + $scope.customer + '/getItemList?itemSearchText=' + val).then(function (response) {
                    var data = response.data;
                    console.log(data);
                    $scope.itemList = angular.copy(data);
                    $("#selectItem").modal('show');
                    $scope.itemSearchText = val;
                    $scope.searchText = val;
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                })
            };

            $scope.getCompleteItemList = function (val) {
                $(".loader").css("display", "block");
                if (angular.isUndefined(val)) {
                    val = "";
                }
                $http.get($scope.hiposServerURL + "/" + $scope.customer + '/getItemListForInventoryModule?itemSearchText=' + val).then(function (response) {
                    var data = response.data;
                    $scope.completeItemList = angular.copy(data);
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                })
            };
            $scope.getCompleteItemList();

        }
    }
}]);