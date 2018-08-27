app.directive("salesPrint", function ($http, Notification, $timeout) {
    return {
        restrict: 'E',
        templateUrl: "partials/salesPrint.html",
        link: function ($scope) {
            $scope.saveMultiPaymentPOS = function (paymentType) {
                $scope.saveData = true;
                if ($scope.selectedItemsList.length <= 0) {
                    Notification.error({
                        message: 'Atleast One Item has to be selected ',
                        positionX: 'center',
                        delay: 2000
                    });
                    $scope.saveData = false;
                }else if ($scope.selectedCustomer==""||$scope.selectedCustomer==null) {
                    Notification.error({
                        message: 'Select Customer ',
                        positionX: 'center',
                        delay: 2000
                    });
                    $scope.saveData = false;
                }else if (paymentType === 'AcceptPayment') {
                    if (angular.isUndefined($scope.paymentTotalBalance) || $scope.paymentTotalBalance > 0) {
                        Notification.error({message: 'please Enter Amount', positionX: 'center', delay: 2000});
                        $scope.popUpDivShow('paymentPopup');
                        $scope.saveData = false;
                    }
                    else {
                        $scope.saveData = true;
                    }
                }
                if ($scope.saveData === true) {
                    $scope.isDisabled = true;
                    $timeout(function () {
                        $scope.isDisabled = false;
                    }, 2000)
                    $http.post('/sales' + '/savePOSInvoice',
                        angular.toJson($scope.populateSaveSIMultiPayData(paymentType), "Create")).then(function (response, status, headers, config) {
                        var data = response.data;

                        $scope.populateSIResponceData(data);
                        $scope.sendSMS(data, "DirectSalesInvoice");
                        Notification.success({
                            message: 'Invoice has been saved successfully',
                            positionX: 'center',
                            delay: 2000
                        });
                        $("#paymentNew").modal('hide');
                        $scope.removeAllItems();
                        $scope.billNO = data.formSetUpNo;

                    }, function (error) {

                        Notification.error({
                            message: 'Something went wrong, please try again',
                            positionX: 'center',
                            delay: 2000
                        });
                    });
                }
            };
            $scope.sendSMS = function(purchase,type){
                var Details;
                Details={
                    phoneNumber:purchase.siData.phoneNumber,
                    invoiceNo:purchase.siData.piNo,
                    invAmt:purchase.totalIncludingTax,
                    type:type,
                    name:purchase.siData.customerName
                }
                $http.post("/sms/sendSMSInvoice/",angular.toJson(Details, "Create"))
                    .then(function (response) {
                        var data = response.data;
                        $scope.supplierData = data;
                    });
            };
            $scope.dragHide = function () {
                /* ambi draggable */
                $(".drag_item_head > div").each(function () {
                    if (parseInt($(this).css('left').slice(0, -2)) < 90) {
                        $(this).hide();
                    } else
                        $(this).css('left', (parseInt($(this).css('left').slice(0, -2)) - 90));
                });

                $(".drag_item_body > div").each(function () {
                    if (parseInt($(this).css('left').slice(0, -2)) < 90) {
                        $(this).hide();
                    } else
                        $(this).css('left', (parseInt($(this).css('left').slice(0, -2)) - 90));
                });
            }
            $scope.populateSIResponceData = function (data, paymentType) {
                var descTerm = [];
                angular.forEach(data.siData.selectedItemsList, function (value, key) {
                    data.siData.selectedItemsList[key].cessTaxAmt = ((((value.unitPrice) * (value.qty)) - (value.discountAmt)) * (value.cess)) / 100;
                    str1 = value.itemDescription;
                    descTerm[key] = $.parseHTML(str1);
                    if (data.siData.taxType == 'CGST:SGST/UGST') {
                        data.siData.selectedItemsList[key].cgstsgstsplitvalues = (value.taxamt) / 2;
                        data.siData.selectedItemsList[key].taxPercentageSplit = value.taxpercent / 2;
                        data.siData.selectedItemsList[key].rateTaxPercentage = value.taxpercent;
                        data.siData.selectedItemsList[key].taxpercent = 0;
                        data.siData.selectedItemsList[key].taxamt = 0;
                    }
                    else {
                        data.siData.selectedItemsList[key].rateTaxPercentage = value.taxpercent;
                    }
                });
                $scope.numberToWord = toWords(data.siData.totalCheckOutamt);
                $scope.printData = data;
                var $log = $("#logs");
                str = data.footer;
                console.log(data.footer);
                html = $.parseHTML(str);
                // Append the parsed HTML
                $log.append(html);
                var $memo = $("#memo1");
                str1 = "";
                str1 = data.siData.memo;
                html14 = $.parseHTML(str1);
                // Append the parsed HTML
                $memo.append(html14);
                $("#indianA4Print").modal('show');//Division Is Used To Print Indian A4 Print
                setTimeout(function () {
                    for (var f = 0; f < descTerm.length; f++) {
                        $("#logs_" + f).append(descTerm[f]);
                    }
                    $scope.dragHide();
                }, 100);
            };
        }
    }
});
app.directive("salesReturnPrint", function ($http, Notification, $timeout) {
    return {
        restrict: 'E',
        templateUrl: "partials/salesPrint.html",
        link: function ($scope, element, attrs) {
            $scope.retailServerURL = "/retail/";
            $scope.saveReturnSI = function (paymentType) {
                if ($scope.selectedItemsList.length <= 0) {
                    Notification.error({message: 'Atleast One Item has to be selected ', positionX: 'center', delay: 2000});
                    return;
                }
                if (paymentType === 'AcceptPayment') {
                    if (angular.isUndefined($scope.paymentTotalBalance) || $scope.paymentTotalBalance > 0 ) {
                        Notification.error({message: 'please Enter Amount', positionX: 'center', delay: 2000});
                        $scope.popUpDivShow('paymentPopup');
                        $scope.saveData = false;
                    }
                    else {
                        $scope.saveData = true;
                    }
                }
                if ($scope.saveData === true) {
                    $scope.isDisabled = true;
                    $timeout(function () {
                        $scope.isDisabled = false;
                    }, 2000)
                    $http.post('/sales' + '/returnSI',
                        angular.toJson($scope.populateSaveSIMultiPayData(paymentType), "Create")).then(function (response, status, headers, config) {
                        var data = response.data;
                        Notification.success({
                            message: 'Invoice Returned successfully',
                            positionX: 'center',
                            delay: 2000
                        });
                        $scope.removeAllItems();
                        $scope.populateSRResponseData(data);
                        $scope.sendSMS(data, "SalesCreditNote");
                    }, function (error) {
                        Notification.error({
                            message: 'Something went wrong, please try again',
                            positionX: 'center',
                            delay: 2000
                        });
                    });
                }
            };

            $scope.populateSRResponseData = function (data) {
                var descTerm = [];
                angular.forEach(data.siData.selectedItemsList, function (value, key) {
                    str1 = value.itemDescription;
                    descTerm[key] = $.parseHTML(str1);
                    data.siData.selectedItemsList[key].cessTaxAmt = ((((value.unitPrice) * (value.returnQty)) - (value.discountAmt)) * (value.cess)) / 100;
                    if (data.siData.taxType == 'CGST:SGST/UGST') {
                        data.siData.selectedItemsList[key].cgstsgstsplitvalues = (value.taxamt) / 2;
                        data.siData.selectedItemsList[key].taxPercentageSplit = value.taxpercent / 2;
                        data.siData.selectedItemsList[key].rateTaxPercentage = value.taxpercent;
                        data.siData.selectedItemsList[key].taxpercent = 0;
                        data.siData.selectedItemsList[key].taxamt = 0;
                    }
                    else {
                        data.siData.selectedItemsList[key].rateTaxPercentage = value.taxpercent;
                    }
                });
                $scope.numberToWord = toWords(data.siData.totalCheckOutamt);
                $scope.printData = data;
                $scope.memo = "";
                var $log = $("#log9");
                str = data.footer;
                html = $.parseHTML(str);
                // Append the parsed HTML
                $log.append(html);
                $scope.printType = "Sales Return";
                $scope.printOperation = "salesReturn";
                $("#indianA4Print").modal('show');
                setTimeout(function () {
                    for (var f = 0; f < descTerm.length; f++) {
                        $("#logs333_" + f).append(descTerm[f]);
                    }
                    $scope.dragHide();
                }, 100);


            };
        }
    }
});
