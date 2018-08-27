app.directive("purchaseInvoicePrint",function($http,Notification,$timeout) {
    return {
        restrict: 'E',
        templateUrl: "partials/purchasePrint.html",
        link: function ($scope) {
            $scope.saveMultiPaymentPOS = function (paymentType) {
                $scope.saveData = true;
                if ($scope.selectedItemsList.length <= 0) {
                    Notification.error({message: 'Atleast One Item has to be selected ', positionX: 'center', delay: 2000});
                    $scope.saveData = false;
                }else  if ($scope.supplierSearchText==""||$scope.supplierSearchText==null||angular.isUndefined($scope.supplierSearchText)) {
                    Notification.error({
                        message: 'Select Supplier ',
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
                    $http.post('/purchase' + '/save',
                        angular.toJson($scope.populateSavePIMultiPayData(paymentType), "Create")).then(function (response, status, headers, config) {
                        var data = response.data;
                        $scope.billNO=data.piData.formSetupNo;
                        Notification.success({
                            message: 'Invoice has been saved successfully',
                            positionX: 'center',
                            delay: 2000
                        });
                        $("#paymentNew").modal('hide');
                        $scope.removeAllItems();
                        $scope.populatePIResponceData(data);
                        $scope.sendSMS(data, "DirectPurchaseInvoice");
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
                    phoneNumber:purchase.piData.phoneNumber,
                    invoiceNo:purchase.piData.piNo,
                    invAmt:purchase.totalIncludingTax,
                    type:type,
                    name:purchase.piData.supplierName
                }
                $http.post("/sms/sendSMSInvoice/",angular.toJson(Details, "Create"))
                    .then(function (response) {
                        var data = response.data;
                        $scope.supplierData = data;
                    });
            };
            $scope.savePosDraft = function (paymentType) {
                if ($scope.selectedItemsList.length <= 0) {
                    $scope.editDraftInvoiceList();
                    $scope.draftInv = true;
                }
                else {
                    $http.post('/purchase' + '/saveDraftPurchaseInvoice',
                        angular.toJson($scope.populateSavePIMultiPayData(paymentType), "Create")).then(function (response, status, headers, config) {
                        var data = response.data;
                        $scope.removeAllItems();
                        if (data.status == "Invoice has be Modified SuccessFully") {
                            Notification.success({
                                message: 'Invoice has been saved successfully',
                                positionX: 'center',
                                delay: 2000
                            });
                        } else {
                            Notification.success({message: data.status, positionX: 'center', delay: 2000});
                        }
                        $scope.getPageLoadData();
                        $scope.populatePIResponceData(data);
                    }, function (error) {

                        Notification.error({
                            message: 'Something went wrong, please try again',
                            positionX: 'center',
                            delay: 2000
                        });
                    });
                }
            };
            $scope.dragHide = function (){
                /* ambi draggable */
                $(".drag_item_head > div").each(function () {
                    if(parseInt($(this).css('left').slice(0,-2)) < 90){
                        $(this).hide();
                    }else
                        $(this).css('left', (parseInt($(this).css('left').slice(0,-2)) - 90));
                });

                $(".drag_item_body > div").each(function () {
                    if (parseInt($(this).css('left').slice(0, -2)) < 90) {
                        $(this).hide();
                    } else
                        $(this).css('left', (parseInt($(this).css('left').slice(0, -2)) - 90));
                });
            }
            $scope.populatePIResponceData = function (data, paymentType) {
                var descTerm = [];
                angular.forEach(data.piData.selectedItemsList, function (value, key) {
                    data.piData.selectedItemsList[key].cessTaxAmt = ((value.amtexclusivetax) * (value.cess)) / 100;
                    str1 = value.itemDescription;
                    descTerm[key] = $.parseHTML( str1 );
                    if (data.piData.taxType == 'CGST:SGST/UGST') {
                        data.piData.selectedItemsList[key].cgstsgstsplitvalues = (value.taxamt) / 2;
                        data.piData.selectedItemsList[key].taxPercentageSplit = value.taxpercent / 2;
                        data.piData.selectedItemsList[key].rateTaxPercentage = value.taxpercent;
                        data.piData.selectedItemsList[key].taxpercent = 0;
                        data.piData.selectedItemsList[key].taxamt = 0;

                    }
                    else {
                        data.piData.selectedItemsList[key].rateTaxPercentage = value.taxpercent;
                    }
                    console.log(data);
                });
                $scope.numberToWord = toWords(data.piData.totalCheckOutamt);
                $scope.printData = data;
                var $log = $("#log15");
                str="";
                str = data.footer;
                html = $.parseHTML(str);
                // Append the parsed HTML
                $log.append(html);
                var $memo = $("#memo1");
                str1="";
                str1 = data.piData.memo;
                html14 = $.parseHTML(str1);
                // Append the parsed HTML
                $memo.append(html14);
                $scope.printType = "Purchase Invoice";
                $scope.printOperation="purchaseInvoice";
                $("#indianA4Print").modal('show');
                setTimeout(function(){
                    for(var f=0; f< descTerm.length; f++) {
                        $("#logs_"+f).append(descTerm[f]);
                    }
                    $scope.dragHide();
                }, 100);
                $scope.clearMultiPayment();
            };
        }
    }
});
app.directive("purchaseReturnPrint", function($http,Notification,$timeout) {
    return {
        restrict: 'E',
        templateUrl: "partials/purchasePrint.html",
        link: function ($scope) {
            $scope.saveReturnPI = function (paymentType) {
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
                    $http.post('/purchase' + '/returnPI',
                        angular.toJson($scope.populateSavePIMultiPayData(paymentType), "Create")).then(function (response, status, headers, config) {
                        var data = response.data;
                        Notification.success({
                            message: 'Invoice Returned successfully',
                            positionX: 'center',
                            delay: 2000
                        });
                        $scope.removeAllItems();
                        $scope.populatePRResponseData(data);
                        $scope.sendSMS(data, "PurchaseDebitNote");
                        // $scope.billNO = data.formSetUpNo;

                    }, function (error) {

                        Notification.error({
                            message: 'Something went wrong, please try again',
                            positionX: 'center',
                            delay: 2000
                        });
                    });
                }
            };
            $scope.populatePRResponseData = function (data) {
                var descTerm = [];
                $scope.purchaseinvoicedragable = data.otherDetailsDto;
                angular.forEach(data.piData.selectedItemsList, function (value, key) {
                    str1 = value.itemDescription;
                    descTerm[key] = $.parseHTML( str1 );
                    data.piData.selectedItemsList[key].cessTaxAmt = ((value.amtexclusivetax) * (value.cess)) / 100;
                    if (data.piData.taxType == 'CGST:SGST/UGST') {
                        data.piData.selectedItemsList[key].cgstsgstsplitvalues = (value.taxamt) / 2;
                        data.piData.selectedItemsList[key].taxPercentageSplit = value.taxpercent / 2;
                        data.piData.selectedItemsList[key].rateTaxPercentage = value.taxpercent;
                        data.piData.selectedItemsList[key].taxpercent = 0;
                        data.piData.selectedItemsList[key].taxamt = 0;
                    }
                    else {
                        data.piData.selectedItemsList[key].rateTaxPercentage = value.taxpercent;
                    }
                });
                $scope.numberToWord = toWords(data.totalIncludingTax);
                $scope.printData = data;
                $scope.printType = "Purchase Return";
                $scope.printOperation="purchaseReturn";
                var $log = $( "#log10" );
                str = data.footer;
                html = $.parseHTML( str );
                // Append the parsed HTML
                $log.append( html );
                $("#indianA4Print").modal('show');
                setTimeout(function(){
                    for(var f=0; f< descTerm.length; f++) {
                        $("#logs222_"+f).append(descTerm[f]);
                    }
                    $scope.dragHide();
                }, 100);
            };
        }
    }

});

