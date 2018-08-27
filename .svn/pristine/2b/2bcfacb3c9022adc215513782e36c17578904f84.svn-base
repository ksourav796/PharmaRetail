app.controller('posSaleReportController',
    function ($scope,$http,Notification,$filter) {

    $scope.getSalesInvoiceList=function () {
        $http.post('/sales' + '/getReportSalesList').then(function (response) {
            var data = response.data;
            $scope.salesList=data;

        })
    }
  $scope.getSalesInvoiceList();
    });