app.controller('posPurchaseReportController',
    function ($scope,$http,Notification,$filter) {

        $scope.getPurchaseInvoiceList=function () {
            $http.post('/purchase' + '/getReportPurchaseList').then(function (response) {
                var data = response.data;
                $scope.purchaseList=data;

            })
        }
        $scope.getPurchaseInvoiceList();
    });