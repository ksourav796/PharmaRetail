app.directive("paymentModal", function ($http, $filter, Notification, $timeout, $window) {
    return {
        restrict: 'E',
        templateUrl: "partials/paymentModal.html",
        link: function ($scope) {
            $scope.today = function () {
                $scope.dt = new Date();
                $scope.format = 'dd/MM/yyyy';
            };
            $scope.today();
            $scope.opened = [];
            $scope.openDatePicker = function ($event, index) {
                $event.preventDefault();
                $event.stopPropagation();

                $scope.opened[index] = true;
            };
            $scope.opened1 = [];
            $scope.openDatePicker1 = function ($event, index) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.opened1[index] = true;
            };
            $scope.getBankAccountList = function (ind) {
                $scope.val = "";
                $scope.ind = ind;
                $http.get($scope.hiposServerURL + "/" + 1 + '/getBankAccountList?accountSearchText=' + $scope.val).then(function (response) {
                    var data = response.data;
                    $scope.accountList = angular.copy(data);
                    $("#selectAccount1").modal('show');
                    $scope.accountSearchText =$scope.val;
                    $scope.searchText =$scope.val;
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                })
            };

            $scope.openAdvancePaymentForPurchase = function () {
                $scope.advancepayment = true;
                $scope.openpayment();
            }
            $scope.advancepaymentUnCheck = function () {
                $scope.selfBuildInvoice = true;
                $scope.openpayment();
            }

            $scope.addAccount = function (account) {
                $scope.paymentDropdown[$scope.ind].bankAccount = account.account_name;
                $scope.paymentDropdown[$scope.ind].bankAccountId = account.accountid;
                $("#selectAccount1").modal('hide');
            };
            $scope.openpayment = function (data) {
                $scope.openStockValidateMultipaymnetPurchase();
                // $scope.getPaymentTypesList1(1, 0, 'ONLOAD'); //called ONLOAD
                $scope.totalTCSAmountTendered = $scope.totalTCSAmountTendered || 0;
                $scope.totalTDSAmountTendered = $scope.totalTDSAmountTendered || 0;
                var totalAmount = parseFloat(0.00);
                angular.forEach($scope.paymentDropdown, function (payment) {
                    if ('totalCPAmountTendered' in payment) {
                        totalAmount = totalAmount + parseFloat(payment.totalCPAmountTendered);
                    } else if ('cardAmount' in payment) {
                        totalAmount = totalAmount + parseFloat(payment.cardAmount);
                    } else if ('bankAmount' in payment) {
                        totalAmount = totalAmount + parseFloat(payment.bankAmount);
                    } else if ('voucherAmt' in payment) {
                        totalAmount = totalAmount + parseFloat(payment.voucherAmt);
                    }
                });
                var paymentAmount = totalAmount + parseFloat($scope.totalTCSAmountTendered) + parseFloat($scope.totalTDSAmountTendered);
                $scope.totalVPAmountRefunded = parseFloat(paymentAmount.toFixed(2)) - parseFloat($scope.totalVPBeforDiscount);
                $scope.totalPaidAmt = parseFloat(paymentAmount.toFixed(2));
            };
            $scope.itemsNoStockList = [];
            $scope.openStockValidateMultipaymnetPurchase = function (roundingOff) {
                $scope.checkout = true;
                $timeout(function () {
                    $scope.checkout = false;
                }, 2000)
                if ((angular.isUndefined($scope.selectedItemsList) || $scope.selectedItemsList.length <= 0) && (angular.isUndefined($scope.selectedAccountsList) || $scope.selectedAccountsList.length <= 0)) {
                    Notification.error({
                        message: 'At lest One item has to be selected',
                        positionX: 'center',
                        delay: 2000
                    });
                    return false;
                }
                else {
                    if (!$scope.selfBuildInvoice) {
                        $scope.getTotalAmtForSelectedItems();
                    } else {
                        $scope.getTotalAmtForSelectedItemsForSelfBuildInvoice();
                    }
                    if(angular.isUndefined(roundingOff)){
                        $scope.totalVPBeforDiscount = parseFloat($scope.totalBeforDiscount.toFixed(2));
                        $scope.roundingOffValue=0;
                    }else if(roundingOff==true) {
                        $scope.totalVPBeforDiscount = parseFloat($scope.totalBeforDiscount.toFixed(0));
                        $scope.roundingOffValue=$scope.totalVPBeforDiscount-parseFloat($scope.totalBeforDiscount.toFixed(2));
                    }else {
                        $scope.roundingOffValue=0;
                    }
                    $scope.amountWithoutDiscount = parseFloat($scope.totalBeforDiscount.toFixed(2));
                    $scope.totalVPDiscount = parseFloat($scope.totalDiscount.toFixed(2));
                    $scope.totalVPAfterDiscount = parseFloat($scope.totalAfterDiscount.toFixed(2));
                    $scope.totalVPAmountTendered = 0.00;
                    $scope.totalVPAmountRefunded = 0.00;
                    $scope.totalPaidAmt = 0.00;
                    $scope.paymentButton = "Purchase";
                    if($scope.currencyText == $scope.cmpyCurrencyId){
                        $scope.cmpyCurrency = true;
                    }else{
                        $scope.cmpyCurrency = false;
                    }
                    $("#paymentNew1").modal('show');
                }
            };
            $scope.isValidatedData = function () {
                $scope.isValide = true;
                angular.forEach($scope.selectedItemsList, function (item, index) {
                    if(item.message!='Sold') {
                        if (angular.isUndefined(item.unitPrice) || item.unitPrice === '' || parseFloat(item.unitPrice) <= 0) {
                            $scope.isValide = false;
                        } else if (angular.isUndefined(item.qty) || item.qty === '' || !parseFloat(item.qty) > 0 || parseFloat(item.qty) <= 0) {
                            $scope.isValide = false;
                        }
                        else if (angular.isUndefined(item.amtinclusivetax) || item.amtinclusivetax === '' || !parseFloat(item.amtinclusivetax) > 0 || parseFloat(item.amtinclusivetax) <= 0) {
                            $scope.isValide = false;
                        } else if (angular.isUndefined(item.itemName) || item.itemName === '') {
                            $scope.isValide = false;
                        }
                    }
                });
                return $scope.isValide;
            };
            $scope.getTotalAmtForSelectedItems = function () {
                var totalAmt = 0.00;
                var totalTaxAmt = 0.00;
                var totalDiscountAmt = 0.00;
                var cessTotalTaxAmt = 0.00;
                var totalQty = 0.00;
                var itemCount=0.00;
                console.log($scope.selectedItemsList);
                angular.forEach($scope.selectedItemsList, function (item, index) {
                    totalAmt += parseFloat(item.amtinclusivetax);
                    totalTaxAmt += parseFloat(item.taxamt);
                    if (!angular.isUndefined(item.cessTaxAmt)) {
                        cessTotalTaxAmt += parseFloat(item.cessTaxAmt);
                    }
                    totalDiscountAmt += parseFloat(item.discountAmt);
                    totalQty += parseFloat(item.returnQty);
                    itemCount++;
                });
                angular.forEach($scope.selectedAccountsList, function (item, index) {
                    totalAmt += parseFloat(item.amtinclusivetax);
                    totalTaxAmt += parseFloat(item.taxamt);
                });
                var totalAfterDiscount = parseFloat(totalAmt);
                $scope.totalCheckOutamt = parseFloat(totalAmt.toFixed(2));
                $scope.totalBeforDiscount = parseFloat(totalAmt.toFixed(2));
                $scope.totalDiscount = parseFloat(totalDiscountAmt.toFixed(2));
                $scope.totalAfterDiscount = parseFloat(totalAfterDiscount.toFixed(2));
                $scope.totalTaxAmt = parseFloat(totalTaxAmt.toFixed(2));
                $scope.cessTotalTaxAmt = parseFloat(cessTotalTaxAmt).toFixed(2);
                $scope.returnQty = parseFloat(totalQty.toFixed(2));
                $scope.displayItemLength=parseInt(itemCount);
            };
            $scope.getTotalAmtForSelectedItemsForSelfBuildInvoice = function () {
                var totalAmt = 0.00;
                var totalTaxAmt = 0.00;
                var totalDiscountAmt = 0.00;
                var cessTotalTaxAmt = 0.00;
                var totalQty = 0.00;
                angular.forEach($scope.selectedItemsList, function (item, index) {
                    totalAmt += parseFloat(item.amtexclusivetax);
                    totalTaxAmt += parseFloat(item.taxamt);
                    if (!angular.isUndefined(item.cessTaxAmt)) {
                        cessTotalTaxAmt += parseFloat(item.cessTaxAmt);
                    }
                    totalDiscountAmt += parseFloat(item.discountAmt);
                    totalQty += parseFloat(item.returnQty);
                });
                var totalAfterDiscount = parseFloat(totalAmt);
                $scope.totalCheckOutamt = parseFloat(totalAmt.toFixed(2));
                $scope.totalBeforDiscount = parseFloat(totalAmt.toFixed(2));
                $scope.totalDiscount = parseFloat(totalDiscountAmt.toFixed(2));
                $scope.totalAfterDiscount = parseFloat(totalAfterDiscount.toFixed(2));
                $scope.totalTaxAmt = parseFloat(totalTaxAmt.toFixed(2));
                $scope.cessTotalTaxAmt = parseFloat(cessTotalTaxAmt).toFixed(2);
                $scope.returnQty = parseFloat(totalQty.toFixed(2));
            };

            $scope.checkPayment = function (id) {
                var disable = false;
                angular.forEach($scope.paymentDropdown, function (item) {
                    if(item.paymentmethodId == id && id==1){
                        disable = true;
                    }
                });

                return disable;
            };
            /*********** ** ** PAYMENT-NAME && PAYMENT-TYPE ** ** *********************/
            $scope.paymentList = [];
            // $scope.getPaymentTypesList1 = function (paymentTypeIndex, payIndex, flag) {
            //     //alert('onload');
            //     //alert(paymentTypeIndex+' ** '+payIndex);
            //     if (angular.isUndefined(paymentTypeIndex)) {
            //         paymentTypeIndex = 1;
            //     }
            //     $http.get($scope.hiposServerURL + "/" + 1 + '/getPaymentTypes').then(function (response) {
            //         var data = response.data;
            //         $scope.getPaymentTypes = data;
            //         $scope.paymentList[payIndex] = angular.copy(data);
            //         console.log($scope.paymentList[payIndex]);
            //         $scope.paymentTypeIndex = paymentTypeIndex;
            //
            //         if($scope.paymentDropdown && $scope.paymentDropdown.length == 1){
            //             var ptype = $scope.paymentDropdown[0];
            //             if(typeof ptype === 'object' && Object.keys(ptype).length <= 2){
            //                 var makeEmpty = true;
            //                 var paymentmethodType, dboj = {};
            //                 angular.forEach(data, function (value) {
            //                     if (value.defaultType && value.defaultType == 'true') {
            //                         if(makeEmpty){
            //                             makeEmpty = false;
            //                             $scope.paymentDropdown = [];
            //                         }
            //                         paymentmethodType = value.paymentmethodType;
            //                         dobj = {};
            //                         switch (paymentmethodType){
            //                             case "Cash":
            //                                 dobj.DefaultPaymentmethodId = 1;
            //                                 dobj.totalCPAmountTendered = 0;
            //                                 dobj.id =  'paymentDropdown' + ($scope.paymentDropdown.length + 1);
            //                                 $scope.paymentDropdown.push(dobj);
            //                                 $scope.paymentList[$scope.paymentDropdown.length] = angular.copy(data);
            //                                 break;
            //
            //                             case "Card":
            //                                 dobj.cardTransactionNo="";
            //                                 dobj.cardAmount=0;
            //                                 dobj.cardBankName="";
            //                                 dobj.cardBankAccount="";
            //                                 dobj.cardDate=(new Date);
            //                                 dobj.DefaultPaymentmethodId = 3;
            //                                 dobj.id =  'paymentDropdown' + ($scope.paymentDropdown.length + 1);
            //                                 $scope.paymentDropdown.push(dobj);
            //                                 $scope.paymentList[$scope.paymentDropdown.length] = angular.copy(data);
            //                                 break;
            //                             case "Bank":
            //                                 dobj.bankAccountId = "";
            //                                 dobj.bankAccount="";
            //                                 dobj.bankName="";
            //                                 dobj.bankinvoiceNo="";
            //                                 dobj.bankAmount=0;
            //                                 dobj.bankDate=(new Date);
            //                                 dobj.DefaultPaymentmethodId = 2;
            //                                 dobj.id =  'paymentDropdown' + ($scope.paymentDropdown.length + 1);
            //                                 $scope.paymentDropdown.push(dobj);
            //                                 $scope.paymentList[$scope.paymentDropdown.length] = angular.copy(data);
            //                                 break;
            //
            //                             case "Voucher":
            //                                 dobj.voucherNo = "";
            //                                 dobj.voucherAmt = 0;
            //                                 dobj.DefaultPaymentmethodId = 4;
            //                                 dobj.id =  'paymentDropdown' + ($scope.paymentDropdown.length + 1);
            //                                 $scope.paymentDropdown.push(dobj);
            //                                 $scope.paymentList[$scope.paymentDropdown.length] = angular.copy(data);
            //                                 break;
            //                             default:
            //                                 break;
            //
            //                         }
            //
            //
            //                     }
            //                 });
            //
            //             }
            //         }
            //     }, function (error) {
            //         Notification.error({
            //             message: 'Something went wrong, please try again',
            //             positionX: 'center',
            //             delay: 2000
            //         });
            //     })
            // };
            // $scope.getPaymentTypesList1(1, 0, 'ONLOAD'); //called ONLOAD
            //ON CHANGE DROPDOWN FUNC TO SELECT PAYMENT TYPE
            $scope.selectPaymentType = function (selectedOption, ddpStr, payInd,list) {
                angular.forEach(list,function (value,key) {
                    if(value.paymentmethodId==selectedOption){
                        $scope.paymentDropdown[payInd-1].DEFAULT_PAYMENT_TYPE=value.paymentmethodType;
                    }
                })
                $('.bankInfoHide').css('display', 'block');
                var data = $scope.paymentList[payInd - 1];
                //$scope.selectedPaymentType = [];
                angular.forEach(data, function (value, key) {
                    if (value.paymentmethodId == selectedOption) {
                        var selectedPaymentType = value.paymentmethodType;
                        if (selectedPaymentType == 'Cash') {
                            $scope.CASHPAYMENT = true;
                            $('#PaymentTypeCash' + ddpStr).show();
                            $('#PaymentTypeCash' + ddpStr).siblings().hide();

                            /***remove all CASH TYPE payments from drop down array***/
                            function filterPaymentmethodType(pay) {
                                return pay.paymentmethodType != 'Cash';
                            }

                            var filteredData = $scope.paymentList[payInd - 1].filter(filterPaymentmethodType);
                            $scope.paymentList[payInd] = filteredData;
                        } else {
                            $scope.CASHPAYMENT = false;
                            $('#PaymentTypeCash' + ddpStr).hide();
                        }
                        if (selectedPaymentType == 'Card') {
                            $scope.CARDPAYMENT = true;
                            $('#PaymentTypeCard' + ddpStr).show();
                            $('#PaymentTypeCard' + ddpStr).siblings().hide();
                        } else {
                            $scope.CARDPAYMENT = false;
                            $('#PaymentTypeCard' + ddpStr).hide();
                        }
                        if (selectedPaymentType == 'Bank') {
                            $scope.OTHERSPAYMENT = true;
                            $('#PaymentTypeBank' + ddpStr).show();
                            $('#PaymentTypeBank' + ddpStr).siblings().hide();
                        } else {
                            $scope.OTHERSPAYMENT = false;
                            $('#PaymentTypeBank' + ddpStr).hide();
                        }
                        if (selectedPaymentType == 'Voucher') {
                            $scope.VOUCHERPAYMENT = true;
                            $('#PaymentTypeVoucher' + ddpStr).show();
                            $('#PaymentTypeVoucher' + ddpStr).siblings().hide();
                        } else {
                            $scope.VOUCHERPAYMENT = false;
                            $('#PaymentTypeVoucher' + ddpStr).hide();
                        }

                    }
                });
            };
            $scope.addNewSection = function (payInd) {
                //add more button will not work until you choose something in previous dropdown
                if (angular.isUndefined($scope.paymentDropdown[payInd - 1].DefaultPaymentmethodId)) {
                    Notification.error({
                        message: 'Please select something from the previous dropdown',
                        positionX: 'center',
                        delay: 2000
                    });
                } else {
                    var newddpIndex = $scope.paymentDropdown.length + 1;
                    $scope.paymentDropdown.push({'id': 'paymentDropdown' + newddpIndex});
                    //$scope.paymentDropdown[$scope.paymentDropdown.length-1].DefaultPaymentmethodId = filteredData[0].paymentmethodId;
                    $scope.getPaymentTypesList1(newddpIndex, $scope.paymentDropdown.length, 'ADDNEWSECTION');
                    /***remove all CASH TYPE payments from drop down array***/
                    if ($scope.CASHPAYMENT == 'true' || $scope.CASHPAYMENT == true) {
                        function filterPaymentmethodType(pay) {
                            return pay.paymentmethodType != 'Cash';
                        }

                        var filteredData = $scope.paymentList[0].filter(filterPaymentmethodType);
                        $scope.paymentList[payInd] = filteredData;
                    } else {
                        $scope.paymentList[payInd] = $scope.paymentList[payInd - 1];
                    }
                }
            };
            $scope.removePayments = "";
            $scope.ddpIndex = 0;
            $scope.paymentDropdown = [{'id': 'paymentDropdown1'}];
            $scope.removeSection = function (index) {
                $scope.paymentDropdown.splice(index, 1);
                $scope.paymentDropdown = $scope.paymentDropdown;
                $scope.removePayments = "removeField";
                $scope.OnChangeMultiPayament('', currentValue = undefined, 'REMOVE', index);
            };
            $scope.OnChangeMultiPayament = function (oldValue, currentValue, onFocusInputName, paymentDropdownIndex) {
                if (parseFloat($scope.totalTCSAmountTendered) > 0) {
                    $scope.totalTDSAmountTendered = parseFloat(0.00);
                }
                if (parseFloat($scope.totalTDSAmountTendered) > 0) {
                    $scope.totalTCSAmountTendered = parseFloat(0.00);
                }
                //alert(oldValue+' ## '+currentValue+' ## '+onFocusInputName +' ## '+ paymentDropdownIndex);
                var totalAmount = parseFloat(0.00);
                angular.forEach($scope.paymentDropdown, function (payment, key) {
                    if ('totalCPAmountTendered' in payment) {
                        totalAmount = totalAmount + parseFloat(payment.totalCPAmountTendered);
                        currentValue = parseFloat(1);
                    } else if ('cardAmount' in payment) {
                        totalAmount = totalAmount + parseFloat(payment.cardAmount);
                        currentValue = parseFloat(1);
                    } else if ('bankAmount' in payment) {
                        totalAmount = totalAmount + parseFloat(payment.bankAmount);
                        currentValue = parseFloat(1);
                    } else if ('voucherAmt' in payment) {
                        totalAmount = totalAmount + parseFloat(payment.voucherAmt);
                        currentValue = parseFloat(1);
                    }
                });
                //console.log(totalAmount);
                if ($scope.removePayments == "removeField") {
                    currentValue = undefined;
                    $scope.removePayments = "";
                }
                if (totalAmount.toString() == "NaN") {
                    totalAmount = parseFloat(0);
                }
                if ($scope.totalTCSAmountTendered == undefined) {
                    $scope.totalTCSAmountTendered = parseFloat(0.00);
                }
                if ($scope.totalTDSAmountTendered == undefined) {
                    $scope.totalTDSAmountTendered = parseFloat(0.00);
                }
                var paymentAmount = totalAmount + parseFloat($scope.totalTCSAmountTendered) + parseFloat($scope.totalTDSAmountTendered);
                var onFocusedElement = onFocusInputName;
                //alert(onFocusedElement +'&&&'+ currentValue);
                switch (onFocusedElement) {
                    case "TCS":
                        var tempTotalVPBeforDiscount = $scope.totalVPBeforDiscount;
                        var tempTotalVPAfterDiscount = parseFloat(0.00);
                        var tempTotalVPAmountRefunded = parseFloat(0.00);
                        if (angular.isUndefined(currentValue) || currentValue === "" ||
                            !(angular.isNumber(parseFloat(currentValue)))) {
                            $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
                            $scope.totalVPAmountRefunded = parseFloat(tempTotalVPAmountRefunded.toFixed(2));
                            return;
                        } else if (parseFloat($scope.totalTCSAmountTendered) > parseFloat($scope.totalVPBeforDiscount)) {
                            Notification.info({
                                message: 'TCS Amount can not be greater than to be paid amount',
                                positionX: 'center',
                                delay: 2000
                            });
                            $scope.totalTCSAmountTendered = parseFloat(oldValue);
                            var paymentAmount = totalAmount + parseFloat($scope.totalTCSAmountTendered) + parseFloat($scope.totalTDSAmountTendered);
                            $scope.totalPaidAmt = parseFloat(paymentAmount.toFixed(2));
                            $scope.totalVPAmountRefunded = parseFloat(paymentAmount.toFixed(2)) - parseFloat($scope.totalVPBeforDiscount.toFixed(2));
                            $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
                            $scope.totalVPAmountRefunded = parseFloat(tempTotalVPAmountRefunded.toFixed(2));
                            return;
                        }

                        if (parseFloat(paymentAmount) > $scope.totalVPBeforDiscount) {
                            tempTotalVPAfterDiscount = parseFloat(0);
                            var reFoundAmt = parseFloat(0);
                            reFoundAmt = parseFloat(paymentAmount.toFixed(2)) - parseFloat($scope.totalVPBeforDiscount.toFixed(2));
                            $scope.totalVPAmountRefunded = reFoundAmt.toFixed(2);
                            $scope.totalPaidAmt =paymentAmount  - parseFloat($scope.totalTCSAmountTendered) + parseFloat($scope.totalTDSAmountTendered);
                            $scope.totalTCSAmountTendered = parseFloat(oldValue);
                            // $scope.totalPaidAmt = parseFloat(paymentAmount.toFixed(2));
                        } else {
                            $scope.totalVPAmountRefunded = parseFloat(paymentAmount.toFixed(2)) - parseFloat($scope.totalVPBeforDiscount.toFixed(2));
                            $scope.totalPaidAmt = parseFloat(paymentAmount.toFixed(2));
                        }
                        $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
                        break;
                    case "TDS":
                        var tempTotalVPBeforDiscount = $scope.totalVPBeforDiscount;
                        var tempTotalVPAfterDiscount = parseFloat(0.00);
                        var tempTotalVPAmountRefunded = parseFloat(0.00);
                        if (angular.isUndefined(currentValue) || currentValue === "" ||
                            !(angular.isNumber(parseFloat(currentValue)))) {
                            $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
                            $scope.totalVPAmountRefunded = parseFloat(tempTotalVPAmountRefunded.toFixed(2));
                            return;
                        } else if (parseFloat($scope.totalTDSAmountTendered) > parseFloat($scope.totalVPBeforDiscount)) {
                            Notification.info({
                                message: 'TDS Amount can not be greater than to be paid amount',
                                positionX: 'center',
                                delay: 2000
                            });
                            $scope.totalTDSAmountTendered = parseFloat(oldValue);
                            var paymentAmount = totalAmount + parseFloat($scope.totalTCSAmountTendered) + parseFloat($scope.totalTDSAmountTendered);
                            $scope.totalPaidAmt = parseFloat(paymentAmount.toFixed(2));
                            $scope.totalVPAmountRefunded = parseFloat(parseFloat(paymentAmount.toFixed(2)) - $scope.totalVPBeforDiscount.toFixed(2)).toFixed(2);
                            $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
                            $scope.totalVPAmountRefunded = parseFloat(tempTotalVPAmountRefunded.toFixed(2));
                            return;
                        }
                        if (parseFloat(paymentAmount) > $scope.totalVPBeforDiscount) {
                            tempTotalVPAfterDiscount = parseFloat(0);
                            var reFoundAmt = parseFloat(0);
                            reFoundAmt = parseFloat(paymentAmount.toFixed(2)) - parseFloat($scope.totalVPBeforDiscount.toFixed(2));
                            $scope.totalVPAmountRefunded = reFoundAmt.toFixed(2);
                            $scope.totalPaidAmt =paymentAmount  - parseFloat($scope.totalTCSAmountTendered) + parseFloat($scope.totalTDSAmountTendered);
                            $scope.totalTCSAmountTendered = parseFloat(oldValue);
                        } else {
                            $scope.totalVPAmountRefunded = parseFloat(parseFloat(paymentAmount.toFixed(2)) - $scope.totalVPBeforDiscount.toFixed(2)).toFixed(2);
                            $scope.totalPaidAmt = parseFloat(paymentAmount.toFixed(2));
                        }
                        $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
                        break;
                    case "BANK":
                        var tempTotalVPBeforDiscount = $scope.totalVPBeforDiscount;
                        // alert(tempTotalVPBeforDiscount);
                        var tempTotalVPAfterDiscount = parseFloat(0.00);
                        var tempTotalVPAmountRefunded = parseFloat(0.00);
                        if (angular.isUndefined(currentValue) || currentValue === "" ||
                            !(angular.isNumber(parseFloat(currentValue)))) {
                            $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
                            $scope.totalVPAmountRefunded = parseFloat(tempTotalVPAmountRefunded.toFixed(2));
                            return;
                        } else if (parseFloat($scope.paymentDropdown[paymentDropdownIndex].bankAmount) > parseFloat($scope.totalVPBeforDiscount)) {
                            Notification.info({
                                message: 'Bank Amount can not be greater than to be paid amount',
                                positionX: 'center',
                                delay: 2000
                            });
                            var paymentAmount = totalAmount + parseFloat($scope.totalTCSAmountTendered) + parseFloat($scope.totalTDSAmountTendered);
                            $scope.totalPaidAmt =totalAmount - $scope.paymentDropdown[paymentDropdownIndex].bankAmount + parseFloat($scope.totalTCSAmountTendered) + parseFloat($scope.totalTDSAmountTendered);
                            $scope.paymentDropdown[paymentDropdownIndex].bankAmount = parseFloat(oldValue);
                            $scope.totalVPAmountRefunded = parseFloat(parseFloat(paymentAmount.toFixed(2)) - $scope.totalVPBeforDiscount.toFixed(2)).toFixed(2);
                            $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
                            $scope.totalVPAmountRefunded = $scope.totalPaidAmt - $scope.totalVPBeforDiscount;
                            return;
                        }else if (parseFloat(paymentAmount) > parseFloat($scope.totalVPBeforDiscount)) {
                            Notification.info({
                                message: 'Total Amount can not be greater than to be paid amount',
                                positionX: 'center',
                                delay: 2000
                            });
                            var paymentAmount = totalAmount + parseFloat($scope.totalTCSAmountTendered) + parseFloat($scope.totalTDSAmountTendered);
                            $scope.totalPaidAmt =totalAmount - $scope.paymentDropdown[paymentDropdownIndex].bankAmount + parseFloat($scope.totalTCSAmountTendered) + parseFloat($scope.totalTDSAmountTendered);
                            $scope.paymentDropdown[paymentDropdownIndex].bankAmount = parseFloat(oldValue);
                            $scope.totalVPAmountRefunded = parseFloat(parseFloat(paymentAmount.toFixed(2)) - $scope.totalVPBeforDiscount.toFixed(2)).toFixed(2);
                            $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
                            $scope.totalVPAmountRefunded = $scope.totalPaidAmt - $scope.totalVPBeforDiscount;
                            return;
                        }
                        if (parseFloat(paymentAmount) > $scope.totalVPBeforDiscount) {
                            tempTotalVPAfterDiscount = parseFloat(0);
                            var reFoundAmt = parseFloat(0);
                            reFoundAmt = parseFloat(paymentAmount.toFixed(2)) - parseFloat($scope.totalVPBeforDiscount.toFixed(2));
                            $scope.totalVPAmountRefunded = reFoundAmt.toFixed(2);
                            $scope.totalPaidAmt = parseFloat(paymentAmount.toFixed(2));
                        } else {
                            $scope.totalVPAmountRefunded = parseFloat(paymentAmount.toFixed(2)) - parseFloat(tempTotalVPBeforDiscount.toFixed(2));
                            $scope.totalPaidAmt = parseFloat(paymentAmount.toFixed(2));
                        }
                        $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
                        break;
                    case "VOUCHER":
                        var tempTotalVPBeforDiscount = $scope.totalVPBeforDiscount;
                        var tempTotalVPAfterDiscount = parseFloat(0.00);
                        var tempTotalVPAmountRefunded = parseFloat(0.00);
                        if (angular.isUndefined(currentValue) || currentValue === "" ||
                            !(angular.isNumber(parseFloat(currentValue)))) {
                            $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
                            $scope.totalVPAmountRefunded = parseFloat(tempTotalVPAmountRefunded.toFixed(2));
                            return;
                        } else if (parseFloat($scope.paymentDropdown[paymentDropdownIndex].voucherAmt) > parseFloat($scope.totalVPBeforDiscount)) {
                            Notification.info({
                                message: 'Voucher Amount can not be greater than to be paid amount',
                                positionX: 'center',
                                delay: 2000
                            });
                            var paymentAmount = totalAmount + parseFloat($scope.totalTCSAmountTendered) + parseFloat($scope.totalTDSAmountTendered);
                            $scope.totalPaidAmt =totalAmount - $scope.paymentDropdown[paymentDropdownIndex].voucherAmt + parseFloat($scope.totalTCSAmountTendered) + parseFloat($scope.totalTDSAmountTendered);
                            $scope.paymentDropdown[paymentDropdownIndex].voucherAmt = parseFloat(oldValue);
                            $scope.totalVPAmountRefunded = parseFloat(parseFloat(paymentAmount.toFixed(2)) - $scope.totalVPBeforDiscount.toFixed(2)).toFixed(2);
                            $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
                            $scope.totalVPAmountRefunded = $scope.totalPaidAmt - $scope.totalVPBeforDiscount;
                            return;
                        }
                        else if (parseFloat(paymentAmount) > parseFloat($scope.totalVPBeforDiscount)) {
                            Notification.info({
                                message: 'Total Amount can not be greater than to be paid amount',
                                positionX: 'center',
                                delay: 2000
                            });
                            var paymentAmount = totalAmount + parseFloat($scope.totalTCSAmountTendered) + parseFloat($scope.totalTDSAmountTendered);
                            $scope.totalPaidAmt =totalAmount - $scope.paymentDropdown[paymentDropdownIndex].voucherAmt + parseFloat($scope.totalTCSAmountTendered) + parseFloat($scope.totalTDSAmountTendered);
                            $scope.paymentDropdown[paymentDropdownIndex].voucherAmt = parseFloat(oldValue);
                            $scope.totalVPAmountRefunded = parseFloat(parseFloat(paymentAmount.toFixed(2)) - $scope.totalVPBeforDiscount.toFixed(2)).toFixed(2);
                            $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
                            $scope.totalVPAmountRefunded = $scope.totalPaidAmt - $scope.totalVPBeforDiscount;
                            return;
                        }
                        if (parseFloat(paymentAmount) > $scope.totalVPBeforDiscount) {
                            tempTotalVPAfterDiscount = parseFloat(0);
                            var reFoundAmt = parseFloat(0);
                            reFoundAmt = parseFloat(paymentAmount.toFixed(2)) - parseFloat($scope.totalVPBeforDiscount.toFixed(2));
                            $scope.totalVPAmountRefunded = reFoundAmt.toFixed(2);
                            $scope.totalPaidAmt = parseFloat(paymentAmount.toFixed(2));
                        } else {
                            $scope.totalVPAmountRefunded = (parseFloat(paymentAmount.toFixed(2)) - parseFloat(tempTotalVPBeforDiscount.toFixed(2)));
                            $scope.totalPaidAmt = parseFloat(paymentAmount.toFixed(2));
                        }
                        $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
                        break;
                    case "CARD":
                        var tempTotalVPBeforDiscount = $scope.totalVPBeforDiscount;
                        var tempTotalVPAfterDiscount = parseFloat(0.00);
                        var tempTotalVPAmountRefunded = parseFloat(0.00);
                        if (angular.isUndefined(currentValue) || currentValue === "" ||
                            !(angular.isNumber(parseFloat(currentValue)))) {
                            $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
                            $scope.totalVPAmountRefunded = parseFloat(tempTotalVPAmountRefunded.toFixed(2));
                            return;
                        } else if (parseFloat($scope.paymentDropdown[paymentDropdownIndex].cardAmount) > parseFloat($scope.totalVPBeforDiscount)) {
                            Notification.info({
                                message: 'Card  Amount can not be greater than to be paid amount',
                                positionX: 'center',
                                delay: 2000
                            });
                            $scope.totalPaidAmt =paymentAmount  - $scope.paymentDropdown[paymentDropdownIndex].cardAmount;
                            $scope.paymentDropdown[paymentDropdownIndex].cardAmount = parseFloat(oldValue);
                            $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
                            $scope.totalVPAmountRefunded = $scope.totalPaidAmt - $scope.totalVPBeforDiscount;
                            return;
                        }
                        if (parseFloat(paymentAmount) > $scope.totalVPBeforDiscount) {
                            Notification.info({
                                message: 'Total Amount can not be greater than to be paid amount',
                                positionX: 'center',
                                delay: 2000
                            });
                            tempTotalVPAfterDiscount = parseFloat(0);
                            var reFoundAmt = parseFloat(0);
                            reFoundAmt = parseFloat(paymentAmount.toFixed(2)) - parseFloat($scope.totalVPBeforDiscount.toFixed(2));
                            $scope.totalVPAmountRefunded = reFoundAmt.toFixed(2);;
                            $scope.totalPaidAmt =paymentAmount  - $scope.paymentDropdown[paymentDropdownIndex].cardAmount;
                            $scope.paymentDropdown[paymentDropdownIndex].cardAmount = parseFloat(oldValue);
                            $scope.totalVPAmountRefunded = $scope.totalPaidAmt - $scope.totalVPBeforDiscount;
                        } else {
                            $scope.totalVPAmountRefunded = parseFloat(paymentAmount.toFixed(2)) - parseFloat(tempTotalVPBeforDiscount.toFixed(2));
                            $scope.totalPaidAmt = parseFloat(paymentAmount.toFixed(2));
                        }
                        $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
                        break;
                    case "CASH":
                        var tempTotalVPBeforDiscount = $scope.totalVPBeforDiscount;
                        var tempTotalVPAfterDiscount = parseFloat(0.00);
                        var tempTotalVPAmountRefunded = parseFloat(0.00);
                        if (angular.isUndefined(currentValue) || currentValue === "" ||
                            !(angular.isNumber(parseFloat(currentValue)))) {
                            $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
                            $scope.totalVPAmountRefunded = parseFloat(tempTotalVPAmountRefunded.toFixed(2));
                            return;
                        } else if (parseFloat($scope.paymentDropdown[paymentDropdownIndex].totalCPAmountTendered) > parseFloat($scope.totalVPBeforDiscount)) {
                            Notification.info({
                                message: 'Cash  Amount can not be greater than to be paid amount',
                                positionX: 'center',
                                delay: 2000
                            });
                            $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
                            $scope.totalPaidAmt =paymentAmount - $scope.paymentDropdown[paymentDropdownIndex].totalCPAmountTendered;
                            $scope.paymentDropdown[paymentDropdownIndex].totalCPAmountTendered = parseFloat(oldValue);
                            $scope.totalVPAmountRefunded = $scope.totalPaidAmt - $scope.totalVPBeforDiscount;
                            $scope.paymentDropdown[paymentDropdownIndex].totalCPAmountTendered = parseFloat(oldValue);
                            return;
                        }
                        if (parseFloat(paymentAmount) > $scope.totalVPBeforDiscount) {
                            tempTotalVPAfterDiscount = parseFloat(0);
                            var reFoundAmt = parseFloat(0);
                            reFoundAmt = parseFloat(paymentAmount.toFixed(2)) - parseFloat($scope.totalVPBeforDiscount.toFixed(2));
                            $scope.totalVPAmountRefunded = parseFloat(tempTotalVPAmountRefunded.toFixed(2));
                            $scope.totalPaidAmt =paymentAmount - $scope.paymentDropdown[paymentDropdownIndex].totalCPAmountTendered;
                            $scope.totalVPAmountRefunded = $scope.totalPaidAmt - $scope.totalVPBeforDiscount;
                            $scope.paymentDropdown[paymentDropdownIndex].totalCPAmountTendered = parseFloat(oldValue);
                        } else {
                            $scope.totalVPAmountRefunded = parseFloat(parseFloat(paymentAmount.toFixed(2)) - $scope.totalVPBeforDiscount.toFixed(2)).toFixed(2);
                            $scope.totalPaidAmt = parseFloat(paymentAmount.toFixed(2));
                        }
                        $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
                        break;
                    case "REMOVE":
                        var totalAmount = parseFloat(0.00);
                        angular.forEach($scope.paymentDropdown, function (payment, key) {
                            if ('totalCPAmountTendered' in payment) {
                                totalAmount = totalAmount + parseFloat(payment.totalCPAmountTendered) + $scope.totalTCSAmountTendered + $scope.totalTDSAmountTendered;
                                currentValue = parseFloat(1);
                            } else if ('cardAmount' in payment) {
                                totalAmount = totalAmount + parseFloat(payment.cardAmount) + $scope.totalTCSAmountTendered + $scope.totalTDSAmountTendered;
                                currentValue = parseFloat(1);
                            } else if ('bankAmount' in payment) {
                                totalAmount = totalAmount + parseFloat(payment.bankAmount) + $scope.totalTCSAmountTendered + $scope.totalTDSAmountTendered;
                                currentValue = parseFloat(1);
                            }
                        });
                        if (totalAmount.toString() == "NaN") {
                            totalAmount = parseFloat(0);
                        }
                        var paymentAmount = totalAmount;
                        $scope.totalVPAmountRefunded = parseFloat(paymentAmount.toFixed(2)) - parseFloat($scope.totalVPBeforDiscount.toFixed(2));
                        $scope.totalPaidAmt = parseFloat(paymentAmount.toFixed(2));
                        break;


                }
            };
            $scope.updateTCS_TDS = function (account, field) {
                $scope.totalTCSAmountTendered = 0;
                $scope.totalTDSAmountTendered = 0;
                if ($scope.totalVPBeforDiscount < account) {
                    $scope.totalTCSAmountTendered = 0;
                    $scope.totalTDSAmountTendered = 0;
                }
                else {
                    if (field == "TCS") {
                        $scope.totalTDSAmountTendered = 0;
                        $scope.totalTCSAmountTendered = account;
                    } else {
                        $scope.totalTCSAmountTendered = 0;
                        $scope.totalTDSAmountTendered = account;
                    }
                    if ($scope.totalCPAmountTendered == 0) {
                        $scope.totalPaidAmt = $scope.totalTCSAmountTendered + $scope.totalTDSAmountTendered;
                    } else {
                        $scope.totalPaidAmt = $scope.totalCPAmountTendered + ($scope.totalTCSAmountTendered + $scope.totalTDSAmountTendered);
                    }
                }
            };


            /******* ** ** **SAVE , PRINT OF PAYMENT MODAL** ** ** **********************************************************************/
            $scope.saveMultiPaySI1 = function (paymentType) {
                $scope.isDisabled = true;
                $timeout(function () {
                    $scope.isDisabled = false;
                }, 2000)
                if (!$scope.validatePayment1(paymentType)) {
                    return;
                }
                $http.post($scope.posPurchaseServerURL + "/" + $scope.supplier + '/save',
                    angular.toJson($scope.populateSaveSIMultiPayData1(paymentType), "Create")).then(function (response, status, headers, config) {
                    var data = response.data;

                    // console.log("**********");
                    // console.log(data);

                    $("#cashpayment").modal('hide');
                    $("#creditcardpayment").modal('hide');
                    $("#voucherPayment").modal('hide')
                    $("#multiPayment").modal('hide');
                    $("#bankPayment").modal('hide');
                    $scope.sendSMS(data, "DirectPurchaseInvoice");
                    $scope.clearValues();
                    $scope.populateSIResponceData(data, paymentType);
                    Notification.success({
                        message: 'Invoice has been saved successfully',
                        positionX: 'center',
                        delay: 2000
                    });
                    $("#paymentNew1").modal('hide');
                }, function (error) {
                    $scope.isDisabled = false;
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                })

            };

            $scope.validatePayment1 = function (paymentType) {
                $scope.totalOfAllPaymentMethod = 0;
                var total_dis = $scope.totalVPBeforDiscount;
                var total_amount_tendered = parseFloat($scope.totalTenderedAmount);
                var sup_no = $scope.supplierInvNo;
                if (sup_no != '') {
                    if (parseFloat(total_dis) < parseInt(total_amount_tendered)) {
                        Notification.error({
                            message: 'Amount tendered should not be greater than total amount',
                            positionX: 'center',
                            delay: 2000
                        });
                        return false;
                    }
                } else {
                    Notification.error({
                        message: 'Supplier Invoice Number Required',
                        positionX: 'center',
                        delay: 2000
                    });
                    return false;
                }
                return true;
            };
            $scope.populateSaveSIMultiPayData1 = function (paymentType, operation) {
                $scope.BPDetails = [];
                $scope.CCPDetails = [];
                $scope.CPDetails = [];
                $scope.VPDetails = [];
                var CASHPAYMENT = $scope.CASHPAYMENT;
                var CARDPAYMENT = $scope.CARDPAYMENT;
                var OTHERSPAYMENT = $scope.OTHERSPAYMENT;
                var VOUCHERPAYMENT = $scope.VOUCHERPAYMENT;

                $scope.BANK_PAYMENT_DETAILS = [];
                $scope.CARD_PAYMENT_DETAILS = [];
                $scope.CASH_PAYMENT_DETAILS = [];
                $scope.VOUCHER_PAYMENT_DETAILS = [];

                angular.forEach($scope.paymentDropdown, function (value, key) {
                    if (!angular.isUndefined($scope.paymentDropdown[key].DefaultPaymentmethodId)) {
                        var paymentTypeId = $scope.paymentDropdown[key].DefaultPaymentmethodId;
                    }
                    angular.forEach($scope.getPaymentTypes, function (value1, key1) {
                        if ($.trim(paymentTypeId) == value1.paymentmethodId) {
                            /****FOR PAYMENT-TYPE = "OTHERS" (BANK) ***************************************/
                            if (value1.paymentmethodType == 'Bank') {
                                var paymentType = value1.paymentmethodId;
                                $scope.BANK_PAYMENT_DETAILS.push({
                                    'paymentType': paymentType,
                                    'bankAccount': $scope.paymentDropdown[key].bankAccount,
                                    'bankName': $scope.paymentDropdown[key].bankName,
                                    'bankAccountId': $scope.paymentDropdown[key].bankAccountId,
                                    'transactionNo': $scope.paymentDropdown[key].bankinvoiceNo,
                                    'amount': $scope.paymentDropdown[key].bankAmount,
                                    'date': $scope.paymentDropdown[key].bankDate
                                });
                            }
                            if (value1.paymentmethodType == 'Card') {
                                /****FOR PAYMENT-TYPE = "CARD" *********************************************/
                                var paymentType = value1.paymentmethodId;
                                $scope.CARD_PAYMENT_DETAILS.push({
                                    'paymentType': paymentType,
                                    'cardNo': $scope.paymentDropdown[key].cardTransactionNo,
                                    'cardAmt': $scope.paymentDropdown[key].cardAmount,
                                    'cardBankName': $scope.paymentDropdown[key].cardBankName,
                                    'cardBankAccount': $scope.paymentDropdown[key].cardBankAccount,
                                    'cardDate': $scope.paymentDropdown[key].cardDate
                                });
                            }
                            if (value1.paymentmethodType == 'Cash') {
                                /****FOR PAYMENT-TYPE = "CARD" *********************************************/
                                var paymentType = value1.paymentmethodId;
                                $scope.CASH_PAYMENT_DETAILS.push({
                                    'paymentType': paymentType,
                                    'cashAmt': $scope.paymentDropdown[key].totalCPAmountTendered
                                });
                            }
                            if (value1.paymentmethodType == 'Voucher') {
                                /****FOR PAYMENT-TYPE = "CARD" *********************************************/
                                var paymentType = value1.paymentmethodId;
                                $scope.VOUCHER_PAYMENT_DETAILS.push({
                                    'paymentType': paymentType,
                                    'voucherNo': $scope.paymentDropdown[key].voucherNo,
                                    'voucherAmt': $scope.paymentDropdown[key].voucherAmt
                                });
                            }


                        }
                    });
                });
                if ((CASHPAYMENT != true) && (CARDPAYMENT != true) && (OTHERSPAYMENT != true) && (VOUCHERPAYMENT != true)) {
                    var confirm = $window.confirm("No Payment Type Is Selected Do You Want To Continue?");
                    if (confirm == false) {
                        $scope.isDisabled= false;
                        return false;
                    }
                    console.log("should not execute...........");
                }

                $scope.BPDetails = {
                    // totalBPBeforDiscount: $scope.totalVPBeforDiscount,
                    // totalBPDiscount: $scope.totalVPDiscount,
                    // totalBPAmountTendered: $scope.totalPaidAmt,
                    // totalBPAmountRefunded: $scope.totalVPAmountRefunded,
                    multiBankPaymentList: $scope.BANK_PAYMENT_DETAILS
                };
                $scope.CCPDetails = {
                    // totalBPBeforDiscount: $scope.totalVPBeforDiscount,
                    // totalBPDiscount: $scope.totalVPDiscount,
                    // totalBPAmountTendered: $scope.totalPaidAmt,
                    // totalBPAmountRefunded: $scope.totalVPAmountRefunded,
                    cardPaymentList: $scope.CARD_PAYMENT_DETAILS
                };
                $scope.CPDetails = {
                    // totalBPBeforDiscount: $scope.totalVPBeforDiscount,
                    // totalBPDiscount: $scope.totalVPDiscount,
                    // totalBPAmountTendered: $scope.totalPaidAmt,
                    // totalBPAmountRefunded: $scope.totalVPAmountRefunded,
                    multiCashPaymentList: $scope.CASH_PAYMENT_DETAILS
                };
                $scope.VPDetails = {
                    multiVoucherPayments: $scope.VOUCHER_PAYMENT_DETAILS
                };

                var data = {
                    operation: operation,
                    piNo: $scope.pino,
                    selectedAccountList: $scope.selectedAccountsList,
                    selectedItemsList: $scope.selectedItemsList,
                    cashPayment: $scope.CPDetails,
                    creditPayment: $scope.CCPDetails,
                    bankPayment: $scope.BPDetails,
                    voucherPayment: $scope.VPDetails,
                    totalCheckOutamt: $scope.totalVPBeforDiscount,
                    paymentType: paymentType,
                    totalTaxAmt: $scope.totalTaxAmt,
                    cessTotalTaxAmt: $scope.cessTotalTaxAmt,
                    taxType: $scope.fullSimplTax,
                    supplierId: $scope.supplierId,
                    supplierEmail: $scope.supplierEmail,
                    cutomerName: $scope.supplierSearchText,
                    amountReturned: $scope.totalVPAmountRefunded,
                    discountAmount: $scope.totalVPDiscount,
                    totalTenderedAmount: $scope.totalPaidAmt,
                    supplierInvNo: $scope.supplierInvNo,
                    memo: $scope.memo,
                    userName: $('#userName').val(),
                    advancepayment: $scope.advancepayment,
                    exchangerateId: $scope.exchangeRateId,
                    exchangerateValue : $scope.exchangeRateText,
                    currencyId: $scope.currencyText,
                    termsandConditionsId: $scope.termsAndConditionText,
                    agentId: $scope.agentText,
                    shippingmethodId: $scope.shipingmethod,
                    projectId: $scope.projectText,
                    referenceNo: $scope.referenceNo,
                    shippingReferenceNo: $scope.shippingmethodreferenceno,
                    shippingDate: $scope.dt1,
                    selfBuildInvoice: $scope.selfBuildInvoice,
                    dateOfInvoice: $scope.dt,
                    cmpyLoc: $scope.companyLocation,
                    suppLoc: $scope.supplierLocation,
                    purchaseQuotationId:$scope.purchaseQuotationId,
                    purchaseOrderId:$scope.purchaseOrderId,
                    receiveItemId:$scope.receiveItemId,
                    typeOfInvoice:$scope.flag,
                    employeeId:$scope.employeeText,
                    tcsAmount:$scope.totalTCSAmountTendered,
                    tdsAmount:$scope.totalTDSAmountTendered,
                    // roundingOffValue:parseFloat($scope.roundingOffValue.toFixed(2)),
                    otherContactId:$scope.otherContactId,
                    piStatus:$scope.piStatus
                };
                console.log(data);
                return data;
            }

            $scope.saveDraftMultiPayPI1 = function (paymentType) {
                if (!$scope.validatePayment(paymentType)) {
                    return;
                }
                $scope.isDisabled = true;
                $http.post($scope.posPurchaseServerURL + "/" + $scope.supplier + '/saveDraftPurchaseInvoice',
                    angular.toJson($scope.populateSaveSIMultiPayData1(paymentType), "Create")).then(function (response, status, headers, config) {
                    $scope.isDisabled = false;
                    var data = response.data;
                    $("#cashpayment").modal('hide');
                    $("#creditcardpayment").modal('hide');
                    $("#voucherPayment").modal('hide')
                    $("#multiPayment").modal('hide');
                    $scope.sendSMS(data, "DirectPurchaseInvoice");
                    $scope.removeAllItems();
                    $scope.populateSIResponceData(data, paymentType);
                    Notification.success({
                        message: 'Invoice has been saved successfully',
                        positionX: 'center',
                        delay: 2000
                    });
                    $("#paymentNew1").modal('hide');
                }, function (error) {
                    $scope.isDisabled = false;
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                })
            };
            $scope.saveMultiPayPI = function (paymentType) {
                // if (!$scope.validatePayment(paymentType)) {
                //     return;
                // }
                $scope.isDisabled = true;
                var purobject=$scope.populateSaveSIMultiPayData1(paymentType);
                if(purobject!=false) {
                    $http.post('/purchase' +'/save',
                        angular.toJson(purobject, "Create")).then(function (response, status, headers, config) {
                        $scope.isDisabled = false;
                        var data = response.data;
                        $scope.sendSMS(data, "DirectPurchaseInvoice");
                        $scope.removeAllItems();
                        $scope.clearValues();
                        $scope.displayItemLength = $scope.selectedItemsList.length;
                        // $scope.populateSIResponceData(data, paymentType);
                        Notification.success({
                            message: 'Invoice has been saved successfully',
                            positionX: 'center',
                            delay: 2000
                        });
                        $("#paymentNew1").modal('hide');
                    }, function (error) {
                        $scope.isDisabled = false;
                        Notification.error({
                            message: 'Something went wrong, please try again',
                            positionX: 'center',
                            delay: 2000
                        });
                    })
                }
            };
            $scope.clearValues= function () {
                $scope.paymentList = [];
                $scope.selfBuildInvoice=false;
                $scope.exportInvoice=false;
                $scope.totalTCSAmountTendered =0;
                $scope.totalTDSAmountTendered =0;
                $(".resetClass").val('');
                $("#paymentNew1").modal('hide');
            };
            $scope.savePIandEmail = function (paymentType) {
                if (!$scope.validatePayment(paymentType)) {
                    return;
                }

                if (!$scope.supplierEmail) {
                    $scope.supplierEmail = $scope.getEmailOfCustmr($scope.supplierList, $scope.supplier);
                }
                if (!$scope.supplierEmail) {
                    Notification.error({
                        message: 'Email is not configured for supplier please enter email id',
                        positionX: 'center',
                        delay: 2000
                    })
                    $scope.showEmailBox = true;
                    return;
                } else {
                    $scope.showEmailBox = false;
                }
                $scope.isDisabled = true;
                $http.post($scope.posPurchaseServerURL + "/" + $scope.supplier + '/saveAndEmail', angular.toJson($scope.populateSaveSIMultiPayData1(paymentType),
                    "Create")).then(function (response, status, headers, config) {
                    var data = response.data;
                    if (data.result == 'no mail server') {
                        Notification.error({
                            message: 'Please Configure Email Server',
                            positionX: 'center',
                            delay: 2000
                        });
                        $scope.showEmailBox = true;
                        $scope.isDisabled = false;
                        return;
                    }
                    $scope.sendSMS(data, "DirectPurchaseInvoice");
                    $("#cashpayment").modal('hide');
                    $("#creditcardpayment").modal('hide');
                    $("#voucherPayment").modal('hide');
                    $scope.clearMultiPayment();
                    $scope.showEmailBox = false;
                    $scope.isDisabled = false;
                    Notification.success({
                        message: 'Order has been saved and Mailed successfully',
                        positionX: 'center',
                        delay: 2000
                    });
                    $("#paymentNew1").modal('hide');

                }, function (error) {
                    $scope.isDisabled = false;
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                })
            };
            $scope.saveSIandEmail = function (paymentType) {
                if (!$scope.validatePayment(paymentType)) {
                    return;
                }
                if (!$scope.supplierEmail) {
                    $scope.supplierEmail = $scope.getEmailOfCustmr($scope.supplierList, $scope.supplier);
                }
                if (!$scope.supplierEmail) {
                    Notification.error({
                        message: 'Email is not configured for supplier please enter email id',
                        positionX: 'center',
                        delay: 2000
                    })
                    $scope.showEmailBox = true;
                    return;
                } else {
                    $scope.showEmailBox = false;
                }
                $scope.isDisabled = true;
                $http.post($scope.posPurchaseServerURL + '/saveAndEmail', angular.toJson($scope.populateSaveSIMultiPayData1(paymentType),
                    "Create")).then(function (response, status, headers, config) {
                    var data = response.data;
                    if (data.result == 'no mail server') {
                        Notification.error({
                            message: 'Please Configure Email Server',
                            positionX: 'center',
                            delay: 2000
                        });
                        $scope.showEmailBox = true;
                        $scope.isDisabled = false;
                        return;
                    }
                    $scope.sendSMS(data, "DirectPurchaseInvoice");
                    $("#cashpayment").modal('hide');
                    $("#creditcardpayment").modal('hide');
                    $("#voucherPayment").modal('hide');
                    $scope.clearMultiPayment();
                    $scope.showEmailBox = false;
                    $scope.isDisabled = false;
                    Notification.success({
                        message: 'Order has been saved and Mailed successfully',
                        positionX: 'center',
                        delay: 2000
                    });
                    $("#paymentNew").modal('hide');

                }, function (error) {
                    $scope.isDisabled = false;
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                })
            };
            $scope.openAdvancePartialPayment = function (apBalance, formNo,currency,exchangeRateValue) {
                $scope.totalPaidAmt = 0.00;
                $scope.totalVPAmountRefunded = 0.00;
                $scope.totalVPAmountTendered = 0.00;
                $scope.totalVPAmountRefunded = 0.00;
                $scope.totalVPDiscount = parseFloat($scope.totalDiscount.toFixed(2));
                $scope.totalVPBeforDiscount = parseFloat(apBalance.toFixed(2));
                $scope.amountWithoutDiscount = parseFloat(apBalance.toFixed(2));
                $scope.totalVPAfterDiscount = parseFloat(apBalance.toFixed(2));
                $scope.formNo = formNo;
                $scope.cardAmount = 0.00;
                $scope.totalVoucherAmt = 0.00;
                $scope.totalBankAmt = 0.00;
                $scope.cashcheck = false;
                $scope.card_checked = false;
                $scope.voucher_checked = false;
                $scope.Bank_checked = false;
                $scope.paymentButton = "AdvancePayment";
                $('#amount_refunded').val(' ');
                $('#trans_no').val(' ');
                $('#card_amt').val(' ');
                $('#voucher_no').val(' ');
                $('#voucher_amt').val(' ');
                $('#totalAmountTendered').val(' ');
                $('#transNo').val(' ');
                $('#cardAmount').val(' ');
                $('#voucherNum').val(' ');
                $('#voucherAmt').val(' ');
                if($scope.currencyText == $scope.cmpyCurrencyId){
                    $scope.cmpyCurrency = true;
                }else{
                    $scope.cmpyCurrency = false;
                }
                $scope.exchangeRateText=exchangeRateValue;
                $scope.currencyText = currency;

                $("#PosInvoiceAdvanceList").modal('hide');
                $("#paymentNew1").modal('show');
            }

            $scope.openMultiAdvancePartialPayment = function(data) {
                console.log("data",data);

                angular.forEach(data, function (value, key) {
                    $scope.apBalance += value.amountToPay;
                });
                $scope.totalPaidAmt = 0.00;
                $scope.totalVPAmountRefunded = 0.00;
                $scope.totalVPAmountTendered = 0.00;
                $scope.totalVPAmountRefunded = 0.00;
                $scope.cardAmount = 0.00;
                $scope.totalVoucherAmt = 0.00;
                $scope.totalBankAmt = 0.00;

                $scope.cashcheck = false;
                $scope.card_checked = false;
                $scope.voucher_checked = false;
                $scope.Bank_checked = false;
                $scope.paymentButton = "AdvancePayment";
                $('#amount_refunded').val(' ');
                $('#trans_no').val(' ');
                $('#card_amt').val(' ');
                $('#voucher_no').val(' ');
                $('#voucher_amt').val(' ');
                $('#totalAmountTendered').val(' ');
                $('#transNo').val(' ');
                $('#cardAmount').val(' ');
                $('#voucherNum').val(' ');
                $('#voucherAmt').val(' ');

                $("#PosInvoiceAdvanceList").modal('hide');
                $("#paymentNew1").modal('show');
            }
        }
    }
});