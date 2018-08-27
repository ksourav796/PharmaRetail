app.controller('purchaseCtrl',
    function ($rootScope, $scope, $http, $interval, $location, $filter, Notification, ngTableParams, $timeout, keyboardManager2) {
        window.$scope = $scope;
        $scope.getPageLoadData=function(){
            $http.post('/pos' + '/getFormSetup?type='+'DirectPurchaseInvoice').then(function (response) {
                var data = response.data;
                $scope.billNO=data.formNo;
            })
        }
        $scope.getPageLoadData();
        $scope.addItem = function (itemCode, keyEvent) {
            if (angular.isUndefined($scope.supplierSearchText) || $scope.supplierSearchText == "") {
                Notification.info({
                    message: 'please select supplier',
                    positionX: 'center',
                    delay: 2000
                });
                return false;
            }
            if (itemCode.serializableStatus == 'Serialize' || itemCode.serializableStatus == 'Batch') {
                if (itemCode.serializableStatus == 'Batch') {
                    $scope.name = 'batch';
                }
                if (itemCode.serializableStatus == 'Serialize') {
                    $scope.name = 'Serialize';
                }
                var localItemCode;
                localItemCode = itemCode.itemName;
                $scope.getItem(localItemCode);
                $scope.barcodeInput = "";
            } else {
                var localItemCode;
                localItemCode = itemCode.itemName;
                if (keyEvent === 13 && ($scope.barcodeInput === "" || angular.isUndefined($scope.barcodeInput))) {
                    return false;
                }
                $('#barcodeInput').val('');
                $scope.iteamIndex = $scope.itemIndexOfItemCode($scope.selectedItemsList, localItemCode);
                if (!angular.isUndefined($scope.iteamIndex) && $scope.iteamIndex !== null && $scope.iteamIndex !== -1) {
                    $scope.currentQty = $scope.selectedItemsList[$scope.iteamIndex].qty;
                    $scope.selectedItemsList[$scope.iteamIndex].qty = parseFloat($scope.currentQty) + 1;
                    $scope.selectedItemsList[$scope.iteamIndex].convertedQuantity = parseFloat($scope.currentQty) + 1;
                    $scope.editSelectedItemList($scope.selectedItemsList[$scope.iteamIndex]);
                } else {
                    $scope.getItem(localItemCode);
                }
                $scope.barcodeInput = "";
            }
        };
        $scope.taxIndexOf = function (array, searchVal) {
            var taxIndex = -1;
            if ($scope.isUndefinedOrNull(searchVal)) {
                taxIndex = -1;
            } else {
                var foundIndex = $filter('filter')(array, {
                    taxName: searchVal
                }, true)[0];
                taxIndex = array.indexOf(foundIndex);
            }
            return taxIndex;
        };
        $scope.getTaxList = function () {
            $http.post('/pos' + "/getPageLoadData").then(function (response) {
                var data = response.data;
                $scope.taxList = data.taxList;
                $scope.uomList = data.itemUOMTypeDTOList;

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.getTaxList();
        $scope.removeAllItemsWithoutSupplier = function () {
            $scope.totalCheckOutamt = parseFloat('0.00');
            $scope.totalBeforDiscount = parseFloat('0.00');
            $scope.totalDiscount = parseFloat('0.00');
            $scope.totalAfterDiscount = parseFloat('0.00');
            $scope.totalTaxAmt = parseFloat('0.00');
            $scope.invokeOrderName = "";
            $scope.invokeOrderId = "";
            $scope.invokeorder = "";
            $scope.selectedItemsList = [];
            $scope.itemSearchText = "";
            $scope.operation = 'Create';
            $scope.op = 'Create';
            $scope.supplierEmail = "";
            $scope.supplierInvNo = "";
            $scope.memo = "";
            $scope.serialItems = "";

        };
        $scope.appendSupplier = function (supplierId) {
            $scope.supplierSearchText = supplierId.supplierName;
            $scope.supplierId = supplierId.supplierId;
            $scope.supplier = $scope.supplierId;
            $scope.showEmailBox = false;
            $scope.removeAllItemsWithoutSupplier();
            $("#selectSupplier").modal('hide');
        }

        $scope.updatesimplifiedTax = function () {
            var taxType = $scope.fullSimplTax;
            if (taxType === 'CGST:SGST/UGST') {
                angular.forEach($scope.selectedItemsList, function (value, key) {
                    var splitValue = value.taxamt.toFixed(2) / 2;
                    $scope.selectedItemsList[key].taxAmountSplit = splitValue + ":" + splitValue
                });
            } else {
                if (taxType === 'IGST') {
                    angular.forEach($scope.selectedItemsList, function (value, key) {
                        var splitValue = value.taxamt;
                        $scope.selectedItemsList[key].taxAmountSplit = splitValue
                    });
                }
            }
        };
        $scope.getTotalAmtForSelectedItems = function () {
            var totalAmt = 0.00;
            var totalTaxAmt = 0.00;
            var totalDiscountAmt = 0.00;
            var cessTotalTaxAmt = 0.00;
            var totalQty = 0.00;
            angular.forEach($scope.selectedItemsList, function (item, index) {
                totalAmt += parseFloat(item.amtinclusivetax);
                totalTaxAmt += parseFloat(item.taxamt);
                if (!angular.isUndefined(item.cessTaxAmt)) {
                    cessTotalTaxAmt += parseFloat(item.cessTaxAmt);
                }
                totalDiscountAmt += parseFloat(item.discountAmt);
                totalQty += parseFloat(item.returnQty);
            });
            angular.forEach($scope.selectedAccountsList, function (item, index) {
                totalAmt += parseFloat(item.amtinclusivetax);
                totalTaxAmt += parseFloat(item.taxamt);
            });
            var totalAfterDiscount = parseFloat(totalAmt);
            $scope.displayTotalAmount = totalAfterDiscount;
            $scope.totalCheckOutamt = parseFloat(totalAmt.toFixed(2));
            $scope.totalBeforDiscount = parseFloat(totalAmt.toFixed(2));
            $scope.totalDiscount = parseFloat(totalDiscountAmt.toFixed(2));
            $scope.totalAfterDiscount = parseFloat(totalAfterDiscount.toFixed(2));
            $scope.totalTaxAmt = parseFloat(totalTaxAmt.toFixed(2));
            $scope.cessTotalTaxAmt = parseFloat(cessTotalTaxAmt).toFixed(2);
            $scope.returnQty = parseFloat(totalQty.toFixed(2));
        };
        $scope.itemIndexOfItemCode = function (array, searchVal) {
            var itemIndex = -1;
            if ($scope.isUndefinedOrNull(searchVal)) {
                itemIndex = -1;
            } else {
                var foundIndex = $filter('filter')(array, {
                    itemName: searchVal
                }, true)[0];
                var foundIndex1 = $filter('filter')(array, {
                    itemName: searchVal
                }, true)[0];
                itemIndex = array.indexOf(foundIndex);
                if (!$scope.isUndefinedOrNull(foundIndex1)) {
                    if (foundIndex1.serializableStatus == 'Serialize')
                        itemIndex = -1
                }
            }
            return itemIndex;
        };
        $scope.isUndefinedOrNull = function (data) {
            return (angular.isUndefined(data) || data === null || data === '' || data === 'null');
        };
        window.$scope = $scope;
        $scope.countVal = 0;
        $scope.hiposServerURL = "/hipos/";
        $scope.retailServerURL = "/retail/";
        $scope.billingServerURL = "/posbill/";
        $scope.posPurchaseServerURL = "/purchase/";
        $scope.returnQty = parseFloat('0.00');
        $scope.totalBeforDiscount = parseFloat('0.00');
        $scope.totalDiscount = parseFloat('0.00');
        $scope.totalAfterDiscount = parseFloat('0.00');
        $scope.totalTaxAmt = parseFloat('0.00');
        $scope.supplierId = 1;
        $scope.supplierSearchText = "Cash supplier";
        $scope.supplierEmail = "";
        $scope.invokeOrderName = "";
        $scope.invokeOrderId = "";
        $scope.userRights = [];
        $scope.operation = 'Create';
        $scope.cursorPosVal = 0;
        $scope.custId = 2;
        $scope.SIId = 0;
        $scope.memo = "";
        $scope.taxList = [];
        $scope.serializableItemsList = [];
        $scope.itemList = [];
        $scope.posSalesList = [];
        $scope.posAdvanceSalesList = [];
        $scope.SIList = [];
        $scope.invokeOrderId = '';
        $scope.InvokeOrderList = [];
        $scope.selectedItemsList = [];
        $scope.retailPostData = [];
        $scope.supplierSearchText = "";
        $scope.cardPayementList = [];
        $scope.voucherPayementList = [];
        $scope.removePayments = "";
        $scope.checkbox = "";
        $scope.totaldupltax = parseFloat(0);
        $scope.totaltaxdupltax = parseFloat(0);
        $scope.serialNo = "";
        $scope.fileToUpload = "";
        $scope.today = new Date();
        $scope.companyName = "";
        $scope.cashPayment = parseFloat(0);
        $scope.taxSummaryList = [];
        $scope.companyAddress = "";
        $scope.op = "Create";
        $scope.companyPhoneNo = "";
        $scope.companyFax = "";
        $scope.companyGstNo = "";
        $scope.cutomerName = "";
        $scope.inventoryMovementId = 1;
        $scope.balanceAmt = parseFloat(0);
        $scope.inventoryadress = "";
        $scope.inventoryphone = "";
        $scope.inventoryfax = "";
        $scope.formNo = "";
        $scope.returnType = "";
        $scope.salesOrderList = [];
        $scope.receiptPaymentList = [];
        $scope.disableButtons = false;
        $scope.supplierDetails = [];
        $scope.supplierNameText = "";
        $scope.taxString = "";
        $scope.hiConnectNotificationList = [];
        $scope.fullUserName = "";
        $scope.countryName = "";
        $scope.currencyId = 1;
        $scope.currencyName = "Indian Rupee";
        $scope.maxlength = "15";
        $scope.hiposServerURL = "/hipos/";
        $scope.supplier = 1;
        $scope.morebtn = true;
        $scope.lessbtn = false;
        $scope.moreShow = false;
        $scope.paymentButton = "PosBilling";
        $scope.selectedList = [];
        $scope.saveButton = 'ExactCash';
        $scope.selectedTemItemsList = [];
        $scope.selectedTemItemsList.push({
            itemCode: ""
        });

        $scope.today = function () {
            $scope.dt = new Date();
            $scope.dt1 = new Date();
        };
        $scope.today();
        $scope.format = 'dd/MM/yyyy';

        $scope.open1 = function () {
            $scope.popup1.opened = true;
        };

        $scope.popup1 = {
            opened: false
        };
        $scope.openDate = function () {
            $scope.popup.opened = true;
        };

        $scope.popup = {
            opened: false
        };
        var ch = 0;
        var count, taxPercent, unitPrice, unitPrice1, cess, totalTax, qty, discountConfigAmt, discountAmt,
            amtexclusivetax, taxamt, cessTaxAmt, igtaxamt, amtinclusivetax, data1, temp;

        $scope.readonlyItemDescription = true;
        $scope.readonlyQty = false;
        $scope.readonlyUomName = false;
        $scope.readonlyUnitPrice = false;
        $scope.readonlyDiscountAmt = false;
        $scope.readonlyDiscountConfigAmt = false;
        $scope.readonlyTaxpercent = false;
        $scope.appendToLocation = function () {
            getsupplierId = {
                supplierIdForLoc: $scope.supplierId
            }
            $http.post('/pos' + '/supplierLocation', angular.toJson(getsupplierId, "Create")).then(function (response) {
                var data = response.data;
                $scope.custlocationList = data;
                $scope.supplierId = getsupplierId.supplierIdForLoc;
                angular.forEach($scope.custlocationList, function (value, key) {
                    $scope.toLocation = parseInt($scope.custlocationList[key].nextRef);
                })
                $scope.changingTax();

            })
        };
        $scope.changingTax = function () {
            var invState;
            angular.forEach($scope.cmpyinvLocationList, function (value) {
                if (value.inventoryLocationId == $scope.fromLocation) {
                    invState = value.stateId.stateName;
                }
            })
            var cmpyState = invState;
            var custState = $scope.toLocation;
            $scope.custlocationList.filter(function (location) {
                if (location.nextRef == custState) {
                    if (location.stateName == null) {
                        if (cmpyState == location.inventoryLocationName) {
                            if ($scope.countryId == 1) {
                                $scope.fullSimplTax = "CGST:SGST/UGST";
                            } else {
                                $scope.fullSimplTax = "Full Tax";
                            }
                        } else {
                            if ($scope.countryId == 1) {
                                $scope.fullSimplTax = "IGST";
                            } else {
                                $scope.fullSimplTax = "Simplified Tax";
                            }
                        }
                    } else {
                        if (cmpyState == location.stateName) {
                            if ($scope.countryId == 1) {
                                $scope.fullSimplTax = "CGST:SGST/UGST";
                            } else {
                                $scope.fullSimplTax = "Full Tax";
                            }
                        } else {
                            if ($scope.countryId == 1) {
                                $scope.fullSimplTax = "IGST";
                            } else {
                                $scope.fullSimplTax = "Simplified Tax";
                            }
                        }
                    }
                }
                ;
            })
        };
        $scope.getPageLoadData();
        $rootScope.showSideMenu = false;
        $("body").addClass("loginBody");
        $("#pageWindowbtn").click();
        $(document).on("click", "#pageWindowbtn", function () {
            var docElm = document.documentElement;
            if (docElm.requestFullscreen) {
                docElm.requestFullscreen();
            }
            else if (docElm.msRequestFullscreen) {
                docElm.msRequestFullscreen();
            }
            else if (docElm.mozRequestFullScreen) {
                docElm.mozRequestFullScreen();
            }
            else if (docElm.webkitRequestFullScreen) {
                docElm.webkitRequestFullScreen();
            }
        });


        $timeout(function () {
            $('.billingTable tbody tr:first-child').addClass('itemFirstRowhide');
        }, 1000);
        $scope.morebtn = true;
        $scope.lessbtn = false;
        $scope.moreShow = false;
        $scope.popUpDivHide = function (val) {
            $scope.popUPValue = val;
            $timeout(function () {
                $("#" + $scope.popUPValue).hide();
            }, 500);

        }


        $scope.popUpDivShow = function (val) {
            $timeout(function () {

                if (val === 'paymentPopup') {
                    if ($scope.selectedItemsList.length <= 0) {
                        Notification.error({
                            message: 'Atleast One Item has to be selected ',
                            positionX: 'center',
                            delay: 2000
                        });
                        return false;
                    } else {
                        $("#" + val).show();
                        if ($scope.tenderedAmt == 0) {
                            $scope.paymentTotalBalance = $scope.paymentTotalDue;
                        } else {
                            $scope.paymentDue('cash');
                        }
                        $scope.cashShow = true;
                        var color = $scope.cashShow = true ? '#FFA500' : 'orange';
                        $("#cashButton").css('background-color', color);
                        $scope.cardShow = false;
                        var color = $scope.cardShow = false ? '#376092' : '';
                        $("#cardButton").css('background-color', color);
                        $scope.creditNoteShow = false;
                        var color = $scope.creditNoteShow = false ? '#376092' : '';
                        $("#creditButton").css('background-color', color);
                        $scope.CouponShow = false;
                        var color = $scope.CouponShow = false ? '#376092' : '';
                        $("#couponButton").css('background-color', color);
                        $scope.eWalletShow = false;
                        var color = $scope.eWalletShow = false ? '#376092' : '';
                        $("#eWalletButton").css('background-color', color);
                        $scope.giftShow = false;
                        var color = $scope.giftShow = false ? '#376092' : '';
                        $("#giftButton").css('background-color', color);
                        $scope.chequeShow = false;
                        var color = $scope.chequeShow = false ? '#376092' : '';
                        $("#chequeButton").css('background-color', color);
                    }
                }
                else {
                    $("#" + val).show();
                }
            }, 500);
        }
        $scope.dateOptions = {
            minDate : new Date()
        };
        $scope.isVisible = false;
        $scope.isItemVisible = false;
        $scope.isBatchItem = false;
        $scope.isAgent = false;
        $scope.isUser = false;
        $scope.displayPopUp = function (popUpValue, itemCode) {
            $timeout(function () {
                $("#" + popUpValue).show();
            }, 500);
            if(angular.isUndefined(itemCode)){
                itemCode="";
            }
            $http.post('/pos' + '/getItemList?type=' + "Active"+'&searchText='+itemCode).then(function (response) {
                var data = response.data;
                $scope.itemList = angular.copy(data);
                // $scope.serializableItemsList = data[0].serializableItemsDTOList;
            })
            if (popUpValue === 'itemPopup') {
                $scope.isItemVisible = true;
            } else if (popUpValue === 'batchItemPopup') {
                $scope.isBatchItem = true;
            } else if (popUpValue === 'DoctorPopUp') {
                $scope.isAgent = true;
            }
            else if (popUpValue === 'UserPopup') {
                $scope.isUser = true;
            }
            else {
                $scope.isVisible = true;
            }
        }
        /***Here Doctor Act as a Agent***/
        $scope.addNewDoctorPopulate = function () {
            $scope.doctorStatusText = "Active"
            $('#doctor-title').text("Add Doctor");
            $("#add_new_doctor_modal").modal('show');
        }
        $scope.saveNewDoctor = function () {
            if ($scope.DoctorNameText == "") {
                Notification.warning({message: 'Doctor Name can not be empty', positionX: 'center', delay: 2000});
                return false;
            }
            if ($scope.dt1 == "") {
                Notification.warning({message: 'Effective Date can not be empty', positionX: 'center', delay: 2000});
                return false;
            }
            if ($scope.EcommerceText == "") {
                Notification.warning({message: 'E-Commerce can not be empty', positionX: 'center', delay: 2000});
                return false;
            }
            else {
                $scope.isDisabled = true;
                $timeout(function () {
                    $scope.isDisabled = false;
                }, 3000)
                var saveItemDetails;
                saveItemDetails = {
                    agentId: $scope.agentId,
                    agentName: $scope.DoctorNameText,
                    effectiveDate: $scope.dt1,
                    email: $scope.EmailText,
                    mobile: $scope.MobileText,
                    address: $scope.AddressText,
                    commission: $scope.CommissionText,
                    gstinNo: $scope.GSTINText,
                    ecommerce: $scope.EcommerceText,
                    status: $scope.doctorStatusText

                };
                $http.post('/pos' + '/saveNewAgent', angular.toJson(saveItemDetails, "Create")).then(function (response, status, headers, config) {
                    var data = response.data;
                    if (data == "") {
                        Notification.error({
                            message: 'Doctor Already Created',
                            positionX: 'center',
                            delay: 2000
                        });
                    }
                    else {
                        $scope.removeDoctorDetails();
                        $("#add_new_doctor_modal").modal('hide');
                        Notification.success({
                            message: 'Doctor Created  successfully',
                            positionX: 'center',
                            delay: 2000
                        });
                        $scope.getAgentList();
                    }
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                });

            }
            ;
        }
        $scope.removeDoctorDetails = function () {
            $scope.agentId = "";
            $scope.DoctorNameText = "";
            $scope.effectiveDateText = "";
            $scope.EmailText = "";
            $scope.MobileText = "";
            $scope.AddressText = "";
            $scope.CommissionText = "";
            $scope.GSTINText = "";
            $scope.EcommerceText = "";
            $scope.GSTINText = "";
        };
        $scope.addUser = function () {
            $scope.addOReditUserTitle = "Add User";
            $scope.isUserAccountInEditMode = false;
            $("#add_useraccountsetupwithpermissions_modal").modal('show');

        };
        $scope.saveUserAccountSetup = function () {
            if (angular.isUndefined($scope.AddressText) || $scope.itemCodeText == '') {
                Notification.warning({message: 'User Name can not be empty', positionX: 'center', delay: 2000});
                return false;
            }
            if (angular.isUndefined($scope.PasswordText) || $scope.PasswordText == '') {
                Notification.warning({message: 'Password  can not be empty', positionX: 'center', delay: 2000});
                return false;
            }
            if (angular.isUndefined($scope.FullNameText) || $scope.FullNameText == '') {
                Notification.warning({message: 'Full Name can not be empty', positionX: 'center', delay: 2000});
                return false;
            }
            if (angular.isUndefined($scope.SecurityQuestionText) || $scope.SecurityQuestionText == '') {
                Notification.warning({message: 'Security Question can not be empty', positionX: 'center', delay: 2000});
                return false;
            }
            if (angular.isUndefined($scope.AnswerText) || $scope.AnswerText == '') {
                Notification.warning({message: 'Answer can not be empty', positionX: 'center', delay: 2000});
                return false;
            }
            if (angular.isUndefined($scope.TelephoneNumberText) || $scope.TelephoneNumberText == '') {
                Notification.warning({message: 'Telephone Number can not be empty', positionX: 'center', delay: 2000});
                return false;
            }
            if (angular.isUndefined($scope.EmailAddressText) || $scope.EmailAddressText == '') {
                Notification.warning({message: 'Email can not be empty', positionX: 'center', delay: 2000});
                return false;
            }
            if (angular.isUndefined($scope.inventoryLocationId) || $scope.inventoryLocationId == '') {
                Notification.warning({
                    message: 'Inventory Location can not be empty',
                    positionX: 'center',
                    delay: 2000
                });
                return false;
            }
            if (angular.isUndefined($scope.status) || $scope.status == '') {
                Notification.warning({message: 'status can not be empty', positionX: 'center', delay: 2000});
                return false;
            }
            else {
                $scope.isDisabled = true;
                $timeout(function () {
                    $scope.isDisabled = false;
                }, 3000)
                var saveUserAccountSetupDetails;
                saveUserAccountSetupDetails = {
                    useraccount_id: $scope.useraccount_id,
                    user_loginId: $scope.AddressText,
                    passwordUser: $scope.PasswordText,
                    currentPassword: $scope.currentPassword,
                    full_name: $scope.FullNameText,
                    securityQuestion: $scope.SecurityQuestionText,
                    securityAnswer: $scope.AnswerText,
                    phone: $scope.TelephoneNumberText,
                    email: $scope.EmailAddressText,
                    location: {inventoryLocationId: $scope.inventoryLocationId},
                    status: $scope.status
                };
                console.log($scope.currentPassword);
                $http.post('/pos' + '/saveUserAccountSetup', angular.toJson(saveUserAccountSetupDetails, "Create")).then(function (response, status, headers, config) {
                    var data = response.data;
                    if (data == "") {
                        Notification.error({message: 'Already exists', positionX: 'center', delay: 2000});
                    }
                    else {
                        if (data.apimessage == '' || data.apimessage == undefined) {
                            Notification.success({
                                message: 'UserAccount Created  successfully ',
                                positionX: 'center',
                                delay: 2000
                            });
                            $("#add_useraccountsetupwithpermissions_modal").modal('hide');
                            $scope.removeUserAccountSetupDetails();
                            // $scope.getUserAccountSetupList();
                        } else {
                            Notification.success({
                                message: data.apimessage,
                                positionX: 'center',
                                delay: 2000
                            });
                        }
                    }
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });

                });
            };
        };
        $scope.removeUserAccountSetupDetails = function () {
            $scope.AddressText = "0";
            $scope.PasswordText = "0";
            $scope.FullNameText = "0";
            $scope.SecurityQuestionText = "0";
            $scope.AnswerText = "0";
            $scope.TelephoneNumberText = "0";
            $scope.EmailAddressText = "0";

        };
        $scope.selectsupplier = function (supplier) {
            $scope.selectedsupplier = supplier.supplierName;
            $scope.supplierId = supplier.supplierId;
            $scope.supplierSearchText = supplier.supplierName;
            $scope.isVisible = false;
        }
        $scope.selectDoctor = function (doctor) {
            $scope.agentId = doctor.agentId;
            $scope.agentSearchText = doctor.agentName;
            $scope.selectedDoctor = doctor.agentName
        }
        $scope.selectUser = function (user) {
            $scope.selectedUser = user.full_name;
            $scope.userId = user.userId;
            $scope.userName = user.full_name;
        }
        $scope.selectBatch = function (batch) {
            $scope.sORbNumbers = batch.sORbNumbers;
        }
        $scope.loadsuppliers = function () {
            var data = $scope.popUpData;
            console.log("Input Text : " + data);
        }

        /****getting supplier List Method***/

        $scope.getsupplierListSearch = function (val) {
            $http.post('/pos' + '/getSupplierList?type=' + "Active").then(function (response) {
                    var data = response.data;
                    $scope.supplierList = angular.copy(data);
                    $("#selectSupplier").modal('show');
                    $scope.searchSupplierText = val;
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                }
            )
        };

        $scope.getsupplierListSearch();
        $scope.getDoctorList = function (val) {
            if (angular.isUndefined(val)) {
                val = "";
            }
            $http.post('/pos' + '/getAgentList?agentSearchText=' + val).then(function (response) {
                    var data = response.data;
                    $scope.agentList = angular.copy(data);
                    $scope.agentSearchText = val;
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                }
            )
        }

        temp = "1";
        $scope.readonlyItemDescription = true;
        $scope.readonlyQty = false;
        $scope.readonlyUomName = false;
        $scope.readonlyUnitPrice = false;
        $scope.readonlyDiscountConfigAmt = false;

        var j1 = 0;
        $scope.pushItemList = function (key, modelval) {
            ch++;
            temp = 1;
            var pushItem = true;
            var discountRegex = /^[0-9]\d{0,9}(\.\d{1,3})?%?$/;
            if ($scope.selectedTemItemsList.length > 0 && $scope.selectedTemItemsList[0].itemCode != "")
                var Itemqty = $scope.selectedTemItemsList[0].qty;
            var ItemPrice = $scope.selectedTemItemsList[0].unitPrice;
            if (ch > 1 && (key == 13) && temp !== undefined) {

                // if (Itemqty == 0) {
                //     pushItem =false;
                //     Notification.error({
                //         message: ' Qty Should not be zero or Empty',
                //         positionX: 'center',
                //         delay: 5000
                //     });
                // }
                // else  if (ItemPrice == 0) {
                //     pushItem =false;
                //     Notification.error({
                //         message: ' UnitPrice Should not be zero or Empty',
                //         positionX: 'center',
                //         delay: 5000
                //     });
                //
                // }
                // else if(Itemqty !=0 && Itemqty!='' ){
                //     if (String(Itemqty).match(discountRegex)) {
                //         pushItem = true;
                //     }else{
                //         Notification.error({
                //             message: ' Please Enter a valid Quantity',
                //             positionX: 'center',
                //             delay: 5000
                //         });
                //         pushItem = false;
                //     }
                // }
                // else if(ItemPrice !=0&&ItemPrice!=''){
                //     if (String(ItemPrice).match(discountRegex)) {
                //         pushItem = true;
                //     }else{
                //         Notification.error({
                //             message: ' Please Enter a valid price',
                //             positionX: 'center',
                //             delay: 5000
                //         });
                //         pushItem = false;
                //     }
                // }

                if (pushItem == true) {

                    if ($scope.selectedTemItemsList.length > 0 && $scope.selectedTemItemsList[0].itemCode != "")
                        $scope.selectedItemsList.unshift({
                            itemCode: $scope.selectedTemItemsList[0].itemCode,
                            itemId: $scope.selectedTemItemsList[0].itemId,
                            itemName: $scope.selectedTemItemsList[0].itemName,
                            itemDescription: $scope.selectedTemItemsList[0].itemDescription,
                            serializableStatus: $scope.selectedTemItemsList[0].serializableStatus,
                            unitPrice: $scope.selectedTemItemsList[0].unitPrice,
                            unitPriceIn: $scope.selectedTemItemsList[0].unitPriceIn,
                            discountAmt: $scope.selectedTemItemsList[0].discountAmt,
                            discountConfigAmt: $scope.selectedTemItemsList[0].discountConfigAmt,
                            type: $scope.selectedTemItemsList[0].type,
                            isDiscountInPercent: $scope.selectedTemItemsList[0].isDiscountInPercent,
                            discPercent: $scope.selectedTemItemsList[0].discountAmt,
                            cess: $scope.selectedTemItemsList[0].cess,
                            qty: $scope.selectedTemItemsList[0].qty,
                            returnQty: $scope.selectedTemItemsList[0].returnQty,
                            outputTaxId: $scope.selectedTemItemsList[0].outputTaxId,
                            taxString: $scope.selectedTemItemsList[0].taxString,
                            uomName: $scope.selectedTemItemsList[0].uomName,
                            remainingQty: $scope.selectedTemItemsList[0].remainingQty,
                            amtexclusivetax: $scope.selectedTemItemsList[0].amtexclusivetax,
                            uom: $scope.selectedTemItemsList[0].uom,
                            convertedQuantity: $scope.selectedTemItemsList[0].convertedQuantity,
                            uomConvertorList: $scope.selectedTemItemsList[0].uomConvertorList,
                            uomConvertorId: $scope.selectedTemItemsList[0].uomConvertorId,
                            taxpercent: $scope.selectedTemItemsList[0].taxpercent,
                            taxName: $scope.selectedTemItemsList[0].taxName,
                            cessTaxAmt: $scope.selectedTemItemsList[0].cessTaxAmt,
                            taxamt: $scope.selectedTemItemsList[0].taxamt,
                            igTax: $scope.selectedTemItemsList[0].igTax,
                            amtinclusivetax: $scope.selectedTemItemsList[0].amtinclusivetax,
                            hsnCode: $scope.selectedTemItemsList[0].hsnCode,
                            serializeNo: $scope.selectedTemItemsList[0].serializeNo,
                            cessTotalTaxAmt: $scope.selectedTemItemsList[0].cessTotalTaxAmt,
                            inclusiveJSON: $scope.selectedTemItemsList[0].inclusiveJSON,
                            salesPrice: $scope.selectedTemItemsList[0].salesPrice,
                            serializableItemsDTOList: $scope.selectedTemItemsList[0].serializableItemsDTOList
                        });

                    $scope.selectedTemItemsList[0] = [];
                    temp = $scope.selectedTemItemsList[0].itemCode;
                    $scope.selectedTemItemsList = [];
                    $scope.selectedTemItemsList.push({
                        itemCode: ""
                    });
                    $scope.getTotal();
                    $("#supplierPopup").modal('hide');
                }
            }
        }
        $scope.list = {};
        $scope.addlist = function () {
            angular.forEach($scope.posPermissionList, function (value, key) {
                if (value.status == 'Active') {
                    $scope.list[value.permissionString] = value.status;
                } else if (value.status == 'Restrict') {
                    $scope.list[value.permissionString] = "Restrict";
                }
                angular.forEach(value.children, function (value1, key1) {
                    if (value1.status == 'Active') {
                        $scope.list[value1.permissionString] = value1.status;
                    }
                    else if (value1.status == 'Restrict') {
                        $scope.list[value1.permissionString] = "Restrict";
                    }
                    else if (value1.status == 'InActive') {
                        $scope.list[value1.permissionString] = "InActive";
                    }

                })
            })
        }

        $scope.savePosPermissions = function () {
            $scope.addlist();
            var postRequest = {
                url: "/posbill/saveNewUserPermissions",
                method: "POST",
                data: $scope.list,
                params: {}
            }
            $http(postRequest).then(function (response) {
                $("#add_new_permissions_modal_1").modal('hide');
            });
        };

        /**getting searched Item List Method*****/
        $scope.addPosSelectedItemsList = function (data) {
            $('.billingTable tbody tr:first-child').removeClass('itemFirstRowhide');
            count = $scope.countVal;
            $scope.taxIndex = $scope.taxIndexOf($scope.taxList, data.itemIPTaxName);
            taxPercent = $scope.taxList[$scope.taxIndex].taxString.split('|');
            unitPrice = data.salesPrice;
            unitPrice1 = data.salesPrice;
            if (JSON.parse(data.inclusiveJSON).sales) {
                unitPrice = unitPrice / (1 + (Number(taxPercent[0]) * 0.01));
            }
            cess = data.cess;
            totalTax = (parseFloat(taxPercent[0]));
            qty = 1;
            discountConfigAmt = 0;
            discountAmt = 0;
            amtexclusivetax = (parseFloat(unitPrice) * parseFloat(qty));
            if (data.type == "Percentage") {
                discountAmt = ((unitPrice * qty) * data.discountAmt) / 100;
                discountConfigAmt = data.discountAmt;
            }
            else if (data.type == "Amount") {
                if (unitPrice > data.discountAmt) {
                    discountConfigAmt = data.discountAmt;
                    discountAmt = qty * data.discountAmt;
                }
                else {
                    Notification.error({
                        message: 'Discount Amount Should Be Less Than UnitPrice ',
                        positionX: 'center',
                        delay: 2000
                    });
                }
            }
            taxamt = (parseFloat(amtexclusivetax) - discountAmt) * ((parseFloat(totalTax) / 100));
            if (cess != "") {
                cessTaxAmt = (parseFloat(amtexclusivetax) - parseFloat(discountAmt)) * ((parseFloat(cess) / 100));
            } else {
                cessTaxAmt = 0;
            }
            igtaxamt = (parseFloat(amtexclusivetax) - discountAmt) * ((parseFloat(taxPercent[0]) / 100));
            amtinclusivetax = ((parseFloat(amtexclusivetax) + parseFloat(taxamt) + parseFloat(cessTaxAmt)) - parseFloat(discountAmt));
            ch++;
            if (ch > 1 && temp !== undefined) {
                if ($scope.selectedTemItemsList.length > 0 && $scope.selectedTemItemsList[0].itemCode != "")
                    $scope.selectedItemsList.unshift({
                        itemCode: $scope.selectedTemItemsList[0].itemCode,
                        itemId: $scope.selectedTemItemsList[0].itemId,
                        itemName: $scope.selectedTemItemsList[0].itemName,
                        itemDescription: $scope.selectedTemItemsList[0].itemDescription,
                        serializableStatus: $scope.selectedTemItemsList[0].serializableStatus,
                        unitPrice: $scope.selectedTemItemsList[0].unitPrice,
                        unitPriceIn: $scope.selectedTemItemsList[0].unitPriceIn,
                        discountAmt: $scope.selectedTemItemsList[0].discountAmt,
                        discountConfigAmt: $scope.selectedTemItemsList[0].discountConfigAmt,
                        type: $scope.selectedTemItemsList[0].type,
                        isDiscountInPercent: $scope.selectedTemItemsList[0].isDiscountInPercent,
                        discPercent: $scope.selectedTemItemsList[0].discountAmt,
                        cess: $scope.selectedTemItemsList[0].cess,
                        outputTaxId: $scope.selectedTemItemsList[0].outputTaxId,
                        taxString: $scope.selectedTemItemsList[0].taxString,
                        qty: $scope.selectedTemItemsList[0].qty,
                        returnQty: $scope.selectedTemItemsList[0].returnQty,
                        uomName: $scope.selectedTemItemsList[0].uomName,
                        remainingQty: $scope.selectedTemItemsList[0].remainingQty,
                        amtexclusivetax: $scope.selectedTemItemsList[0].amtexclusivetax,
                        uom: $scope.selectedTemItemsList[0].uom,
                        stock: $scope.selectedTemItemsList[0].stock,
                        convertedQuantity: $scope.selectedTemItemsList[0].convertedQuantity,
                        uomConvertorList: $scope.selectedTemItemsList[0].uomConvertorList,
                        uomConvertorId: $scope.selectedTemItemsList[0].uomConvertorId,
                        taxpercent: $scope.selectedTemItemsList[0].taxpercent,
                        taxName: $scope.selectedTemItemsList[0].taxName,
                        cessTaxAmt: $scope.selectedTemItemsList[0].cessTaxAmt,
                        taxamt: $scope.selectedTemItemsList[0].taxamt,
                        igTax: $scope.selectedTemItemsList[0].igTax,
                        amtinclusivetax: $scope.selectedTemItemsList[0].amtinclusivetax,
                        hsnCode: $scope.selectedTemItemsList[0].hsnCode,
                        serializeNo: $scope.selectedTemItemsList[0].serializeNo,
                        cessTotalTaxAmt: $scope.selectedTemItemsList[0].cessTotalTaxAmt,
                        inclusiveJSON: $scope.selectedTemItemsList[0].inclusiveJSON,
                        salesPrice: $scope.selectedTemItemsList[0].salesPrice,
                        serializableItemsDTOList: $scope.selectedTemItemsList[0].serializableItemsDTOList
                    });
            }
            console.log($scope.selectedItemsList);
            $scope.displayItemLength = $scope.selectedItemsList.length;
            $scope.countVal = count + 1;

            $scope.selectedTemItemsList = [];
            $scope.selectedTemItemsList.unshift({
                itemCode: data.itemCode,
                itemId: data.itemId,
                itemName: data.itemName,
                itemDescription: data.itemDesc,
                serializableStatus: data.serializableStatus,
                unitPrice: parseFloat(unitPrice.toFixed(2)),
                unitPriceIn: parseFloat(unitPrice1).toFixed(2),
                discountAmt: discountAmt,
                discountConfigAmt: discountConfigAmt,
                type: data.type,
                isDiscountInPercent: data.isDiscountInPercent,
                discPercent: data.discountAmt,
                cess: data.cess,
                qty: 1,
                returnQty: 0,
                uomName: data.itemUOMTyName,
                remainingQty: 1,
                amtexclusivetax: parseFloat(amtexclusivetax.toFixed(2) - discountAmt),
                taxName: data.itemIPTaxName,
                uom: data.uom,
                stock: data.stock,
                convertedQuantity: data.convertedQuantity,
                uomConvertorList: data.uomConvertorDTOList,
                uomConvertorId: data.uomConvertorId,
                taxpercent: taxPercent[0],
                cessTaxAmt: parseFloat(cessTaxAmt.toFixed(2)),
                taxamt: parseFloat(taxamt.toFixed(2)),
                igTax: parseFloat(igtaxamt),
                amtinclusivetax: parseFloat(amtinclusivetax.toFixed(2)),
                hsnCode: data.hsnCode,
                serializeNo: "",
                cessTotalTaxAmt: cessTaxAmt,
                inclusiveJSON: data.inclusiveJSON,
                salesPrice: data.salesPrice,
                serializableItemsDTOList: data.serializableItemsDTOList
            });
            temp = $scope.selectedTemItemsList[0].itemCode;
            $scope.getTotal();
            $("#supplierPopup").modal('hide');

        }
        $scope.configDiscount = function (disConfig, data, index, type) {
            if (type == 'Config') {
                if (disConfig != null) {
                    $scope.discType = disConfig.toString().slice(-1);
                    if ($scope.discType == "%") {
                        $scope.selectedTemItemsList[index].type = "Percentage";
                        $scope.selectedTemItemsList[index].discPercent = parseFloat(disConfig.toString().slice(0, -1));
                    }
                    else {
                        $scope.selectedTemItemsList[index].type = "Amount";
                        $scope.selectedTemItemsList[index].discPercent = parseFloat(disConfig);
                    }
                }

                else {
                    $scope.selectedTemItemsList[index].type = "Amount";
                    $scope.selectedTemItemsList[index].discPercent = parseFloat(0);
                }
            }
            else if (type == 'Amt') {
                if (!angular.isUndefined(data.discountAmt)) {
                    $scope.selectedTemItemsList[index].type = "Amount";
                    $scope.selectedTemItemsList[index].discPercent = parseFloat(data.discountAmt / data.qty);
                }
                else {
                    $scope.selectedTemItemsList[index].type = "Amount";
                    $scope.selectedTemItemsList[index].discPercent = 0;
                }
            }
        }
        $scope.editSelectedItemList = function (selectedList) {
            var disQty = selectedList.qty;
            if (selectedList.type == "Percentage") {
                selectedList.discountAmt = parseFloat(((selectedList.unitPrice * disQty) * selectedList.discPercent) / 100).toFixed(2);
                selectedList.discountConfigAmt = parseFloat(selectedList.discPercent).toFixed(2);
            }
            else if (selectedList.type == "Amount") {
                if (selectedList.unitPrice > selectedList.discPercent) {
                    selectedList.discountConfigAmt = parseFloat(selectedList.discPercent).toFixed(2);
                    selectedList.discountAmt = parseFloat(disQty * selectedList.discPercent).toFixed(2);
                }
                else {
                    selectedList.discountAmt = 0;
                    selectedList.discountConfigAmt = 0;
                    selectedList.discPercent = 0;
                    Notification.error({
                        message: 'Discount Amount Should Be Less Than Amount(EX) ',
                        positionX: 'center',
                        delay: 2000
                    });
                }
            }
            $scope.taxIndex = $scope.taxIndexOf($scope.taxList, selectedList.taxName);
            var taxPercent = $scope.taxList[$scope.taxIndex].taxString.split('|');
            var cess = selectedList.cess;
            if (cess < 0) {
                cess = 0
            }
            var taxName = selectedList.taxName;
            var totalTax = (parseFloat(taxPercent[0]));
            if (selectedList.unitPrice == "") {
                selectedList.unitPrice = parseInt(0);
            }
            var unitPrice = selectedList.unitPrice;
            var unitPrice1 = selectedList.unitPriceIn;
            if (JSON.parse(selectedList.inclusiveJSON).sales) {
                unitPrice = unitPrice1 / (1 + (Number(taxPercent[0]) * 0.01));
            }
            var amntEX = selectedList.unitPrice * selectedList.qty;
            var qty = selectedList.qty;
            var disAmt = selectedList.discountAmt;
            if (parseFloat(amntEX) > 0) {
                if (parseFloat(disAmt) >= parseFloat(amntEX)) {
                    Notification.error({
                        message: 'Discount Amount Should Be Less Than Amount(EX) ',
                        positionX: 'center',
                        delay: 2000
                    });
                    selectedList.discountAmt = 0;
                    selectedList.discountConfigAmt = 0;
                    var amtexclusivetax = (parseFloat(unitPrice) * parseFloat(qty)) - disAmt;
                    var taxamt = (parseFloat(amtexclusivetax)) * ((parseFloat(totalTax) / 100));
                    var cessTaxAmt = (parseFloat(amtexclusivetax)) * ((parseFloat(cess) / 100));
                    var igtaxamt = (parseFloat(amtexclusivetax)) * ((parseFloat(taxPercent[0]) / 100));
                    // var taxamt = (parseFloat(amtexclusivetax) - parseFloat(discountAmt)) * ((parseFloat(taxPercent[0]) / 100));
                    var amtinclusivetax = ((parseFloat(amtexclusivetax) + parseFloat(taxamt) + parseFloat(cessTaxAmt)));

                    selectedList.taxamt = parseFloat(taxamt.toFixed(2));
                    selectedList.amtinclusivetax = Math.round(amtinclusivetax);
                    selectedList.igTax = parseFloat(igtaxamt.toFixed(2));
                    selectedList.cessTaxAmt = parseFloat(cessTaxAmt.toFixed(2));
                    return;
                }
            }
            if ($scope.operation === 'Return') {
                if (parseFloat(selectedList.returnQty) > parseFloat(qty)) {
                    Notification.error({
                        message: 'Return quantity should be less or equal than quantity',
                        positionX: 'center',
                        delay: 2000
                    });
                    selectedList.returnQty=0;
                    return;
                }
                else {
                    qty = parseFloat(qty) - parseFloat(selectedList.returnQty);
                    selectedList.remainingQty = parseFloat(qty);
                    selectedList.returnQty = parseFloat(selectedList.returnQty);
                    var amtexclusivetax = (parseFloat(unitPrice) * parseFloat(selectedList.returnQty)) - disAmt;
                    var taxamt = (parseFloat(amtexclusivetax)) * ((parseFloat(totalTax) / 100));
                    if (cess != "") {
                        var cessTaxAmt = (parseFloat(amtexclusivetax)) * ((parseFloat(cess) / 100));
                    } else {
                        var cessTaxAmt = 0;
                    }
                    var igtaxamt = (parseFloat(amtexclusivetax)) * ((parseFloat(taxPercent[0]) / 100));
                    var amtinclusivetax = ((parseFloat(amtexclusivetax) + parseFloat(taxamt) + parseFloat(cessTaxAmt)));
                    selectedList.taxpercent = taxPercent[0];
                    selectedList.amtexclusivetax = parseFloat(amtexclusivetax.toFixed(2));
                    selectedList.taxamt = Math.abs(parseFloat(taxamt.toFixed(2)));
                    selectedList.igTax = parseFloat(igtaxamt.toFixed(2));
                    selectedList.amtinclusivetax = Math.abs(parseFloat(amtinclusivetax.toFixed(2)));
                    selectedList.discountAmt = parseFloat(disAmt);
                    selectedList.taxName = taxName;
                    selectedList.cessTaxAmt = parseFloat(cessTaxAmt.toFixed(2));
                    $scope.getTotal();
                }
            }
            if ($scope.operation !== 'Return') {
                selectedList.qty = qty;
                selectedList.remainingQty = qty;
                var amtexclusivetax = (parseFloat(unitPrice) * parseFloat(qty)) - disAmt;
                var taxamt = (parseFloat(amtexclusivetax)) * ((parseFloat(totalTax) / 100));
                if (cess != "") {
                    var cessTaxAmt = (parseFloat(amtexclusivetax)) * ((parseFloat(cess) / 100));
                } else {
                    var cessTaxAmt = 0;
                }
                var igtaxamt = (parseFloat(amtexclusivetax)) * ((parseFloat(taxPercent[0]) / 100));
                //to do
                var amtinclusivetax = ((parseFloat(amtexclusivetax) + parseFloat(taxamt) + parseFloat(cessTaxAmt)));
                selectedList.taxpercent = taxPercent[0];
                if (angular.isUndefined(qty) || angular.isUndefined(unitPrice) || qty == "" || angular.isUndefined(disAmt)) {
                    selectedList.taxamt = parseFloat(0).toFixed(2);
                    selectedList.igTax = parseFloat(igtaxamt.toFixed(2));
                    selectedList.amtexclusivetax = parseFloat(0).toFixed(2);
                    selectedList.amtinclusivetax = parseFloat(0).toFixed(2);
                    selectedList.cessTaxAmt = parseFloat(cessTaxAmt.toFixed(2));
                }
                else {
                    selectedList.unitPrice = parseFloat(unitPrice);
                    selectedList.amtexclusivetax = parseFloat(amtexclusivetax.toFixed(2)).toFixed(2);
                    selectedList.taxamt = parseFloat(taxamt.toFixed(2)).toFixed(2);
                    selectedList.igTax = parseFloat(igtaxamt.toFixed(2));
                    selectedList.amtinclusivetax = amtinclusivetax;
                    selectedList.cessTaxAmt = parseFloat(cessTaxAmt.toFixed(2));
                }
                selectedList.discountAmt = parseFloat(disAmt);
                selectedList.taxName = taxName;
                $scope.getTotal();
            }
        };
        $scope.removesupplierDetails = function () {
            $scope.supplierNameText = "";
            $scope.supplierEmailText = "";
            $scope.supplierContactText = "";
            $scope.supplierContactText = "";
            $scope.supplierAddressText = "";
            $scope.fromRegNo = "";
            $scope.companyRegNo = "";
            $scope.Hi_Conn_company_Name = false;
            $scope.getsupplierListSearch();
        };
        $scope.editSelectedItemListPrice = function (selectedList, tax, index) {
            $scope.message = "Success";
            if (selectedList.type == "Percentage") {
                selectedList.discountAmt = ((selectedList.unitPrice * selectedList.qty) * selectedList.discPercent) / 100;
                selectedList.discountConfigAmt = data.discPercent;
            }
            else if (selectedList.type == "Amount") {
                if (selectedList.unitPrice > selectedList.discPercent) {
                    selectedList.discountConfigAmt = selectedList.discPercent;
                    selectedList.discountAmt = selectedList.qty * selectedList.discPercent;
                }
                else {
                    $scope.message = "Error";
                    Notification.error({
                        message: 'Discount Amount Should Be Less Than Amount(EX) ',
                        positionX: 'center',
                        delay: 2000
                    });
                }
            }
            if ($scope.message !== "Error") {
                $scope.taxIndex = $scope.taxIndexOf($scope.taxList, selectedList.taxName);
                var taxPercent = $scope.taxList[$scope.taxIndex].taxString.split('|');
                var cess = selectedList.cess;
                var taxName =selectedList.taxName;
                var totalTax = (parseFloat(taxPercent[0]));
                var unitPrice = selectedList.unitPrice;
                var unitPrice1 = unitPrice;
                if (JSON.parse(selectedList.inclusiveJSON).sales) {
                    unitPrice = unitPrice1 / (1 + (Number(taxPercent[0]) * 0.01));

                }
                var amntEX = selectedList.unitPrice * selectedList.qty;
                var qty = selectedList.qty;
                var disAmt = selectedList.discountAmt;
                if (parseFloat(amntEX) > 0) {
                    if (parseFloat(disAmt) >= parseFloat(amntEX)) {
                        Notification.error({
                            message: 'Discount Amount Should Be Less Than Amount(EX) ',
                            positionX: 'center',
                            delay: 2000
                        });
                        var amtexclusivetax = (parseFloat(unitPrice) * parseFloat(qty)) - disAmt;
                        var taxamt = (parseFloat(amtexclusivetax)) * ((parseFloat(totalTax) / 100));
                        var cessTaxAmt = (parseFloat(amtexclusivetax)) * ((parseFloat(cess) / 100));
                        var igtaxamt = (parseFloat(amtexclusivetax)) * ((parseFloat(taxPercent[0]) / 100));
                        var amtinclusivetax = ((parseFloat(amtexclusivetax) + parseFloat(taxamt) + parseFloat(cessTaxAmt)));

                        selectedList.taxamt = parseFloat(taxamt.toFixed(2));
                        selectedList.amtinclusivetax = amtinclusivetax;
                        selectedList.igTax = parseFloat(igtaxamt.toFixed(2));
                        selectedList.cessTaxAmt = parseFloat(cessTaxAmt.toFixed(2));

                        return;
                    }
                }
                if ($scope.operation === 'Return') {
                    if (parseFloat(data.returnQty) > parseFloat(qty)) {
                        Notification.error({
                            message: 'Return quantity should be less or equal than quantity',
                            positionX: 'center',
                            delay: 2000
                        });
                        return;
                    } else {
                    }
                }
                if ($scope.operation !== 'Return') {
                    selectedList.qty = qty;
                    selectedList.remainingQty = qty;
                    var amtexclusivetax = (parseFloat(unitPrice) * parseFloat(qty)) - disAmt;
                    var taxamt = (parseFloat(amtexclusivetax)) * ((parseFloat(totalTax) / 100));
                    if (cess != "") {
                        var cessTaxAmt = (parseFloat(amtexclusivetax)) * ((parseFloat(cess) / 100));
                    } else {
                        var cessTaxAmt = 0;
                    }
                    var igtaxamt = (parseFloat(amtexclusivetax)) * ((parseFloat(taxPercent[0]) / 100));
                    //to do
                    var amtinclusivetax = ((parseFloat(amtexclusivetax) + parseFloat(taxamt) + parseFloat(cessTaxAmt)));
                    selectedList.taxpercent = taxPercent[0];
                    if (angular.isUndefined(qty) || angular.isUndefined(unitPrice) || unitPrice == "" || angular.isUndefined(disAmt)) {
                        selectedList.taxamt = parseFloat(0).toFixed(2);
                        selectedList.igTax = parseFloat(igtaxamt.toFixed(2));
                        selectedList.amtexclusivetax = parseFloat(0).toFixed(2);
                        selectedList.amtinclusivetax = parseFloat(0).toFixed(2);
                        selectedList.cessTaxAmt = parseFloat(cessTaxAmt.toFixed(2));
                    }
                    else {
                        selectedList.unitPrice = parseFloat(unitPrice);
                        selectedList.unitPriceIn = parseFloat(unitPrice1);
                        selectedList.amtexclusivetax = parseFloat(amtexclusivetax.toFixed(2)).toFixed(2);
                        selectedList.taxamt = parseFloat(taxamt.toFixed(2)).toFixed(2);
                        selectedList.igTax = parseFloat(igtaxamt.toFixed(2));
                        selectedList.amtinclusivetax = amtinclusivetax;
                        selectedList.cessTaxAmt = parseFloat(cessTaxAmt.toFixed(2));
                    }
                    selectedList.discountAmt = parseFloat(disAmt);
                    selectedList.taxName = taxName;
                    $scope.getTotal();
                } else {
                    qty = parseFloat(qty) - parseFloat(selectedList.returnQty);
                    selectedList.remainingQty = parseFloat(qty);
                    selectedList.returnQty = parseFloat(selectedList.returnQty);
                    var amtexclusivetax = (parseFloat(unitPrice) * parseFloat(selectedList.returnQty));
                    var taxamt = (parseFloat(amtexclusivetax)) * ((parseFloat(totalTax) / 100));
                    if (cess != "") {
                        var cessTaxAmt = (parseFloat(amtexclusivetax)) * ((parseFloat(cess) / 100));
                    } else {
                        var cessTaxAmt = 0;
                    }
                    var igtaxamt = (parseFloat(amtexclusivetax)) * ((parseFloat(taxPercent[0]) / 100));
                    //to do
                    // var discountAmt = $scope.getDiscountAmt(data.discountAmt, data.isDiscountInPercent, amtexclusivetax);
                    var amtinclusivetax = ((parseFloat(amtexclusivetax) + parseFloat(taxamt) + parseFloat(cessTaxAmt)));
                    selectedList.taxpercent = taxPercent[0];
                    selectedList.amtexclusivetax = parseFloat(amtexclusivetax.toFixed(2));
                    selectedList.taxamt = Math.abs(parseFloat(taxamt.toFixed(2)));
                    selectedList.igTax = parseFloat(igtaxamt.toFixed(2));
                    selectedList.amtinclusivetax = Math.abs(parseFloat(amtinclusivetax.toFixed(2)));
                    selectedList.discountAmt = parseFloat(disAmt);
                    selectedList.taxName = taxName;
                    selectedList.cessTaxAmt = parseFloat(cessTaxAmt.toFixed(2));
                    $scope.getTotal();
                }
            }
        };
        $scope.itemIndexOf = function (array, searchVal) {
            var itemIndex = -1;
            if ($scope.isUndefinedOrNull(searchVal)) {
                itemIndex = -1;
            } else {
                var foundIndex = $filter('filter')(array, {
                    itemName: searchVal
                }, true)[0];
                itemIndex = array.indexOf(foundIndex);
            }
            return itemIndex;
        };

        $scope.cardShow = false;
        $scope.creditNoteShow = false;
        $scope.CouponShow = false;
        $scope.eWalletShow = false;
        $scope.cashShow = false;
        $scope.giftShow = false;
        $scope.chequeShow = false;
        $scope.paymentOption = function (val) {
            $scope.cardShow = false;
            $scope.creditNoteShow = false;
            $scope.CouponShow = false;
            $scope.eWalletShow = false;
            $scope.cashShow = false;
            $scope.giftShow = false;
            $scope.chequeShow = false;
            $scope[val] = true;
            if (val === 'cardShow') {
                var color = $scope[val] = true ? '#FFA500' : 'orange';
                $("#cardButton").css('background-color', color);
            } else {
                $scope.cardShow = false;
                var color = $scope.cardShow = false ? '#376092' : '';
                $("#cardButton").css('background-color', color);
            }

            if (val === 'cashShow') {
                var color = $scope[val] = true ? '#FFA500' : 'orange';
                $("#cashButton").css('background-color', color);
            } else {
                $scope.cashShow = false;
                var color = $scope.cashShow = false ? '#376092' : '';
                $("#cashButton").css('background-color', color);
            }

            if (val === 'creditNoteShow') {
                var color = $scope[val] = true ? '#FFA500' : 'orange';
                $("#creditButton").css('background-color', color);
            } else {
                $scope.creditNoteShow = false;
                var color = $scope.creditNoteShow = false ? '#376092' : '';
                $("#creditButton").css('background-color', color);
            }

            if (val === 'CouponShow') {
                var color = $scope[val] = true ? '#FFA500' : 'orange';
                $("#couponButton").css('background-color', color);
            } else {
                $scope.CouponShow = false;
                var color = $scope.CouponShow = false ? '#376092' : '';
                $("#couponButton").css('background-color', color);
            }

            if (val === 'chequeShow') {
                var color = $scope[val] = true ? '#FFA500' : 'orange';
                $("#chequeButton").css('background-color', color);
            } else {
                $scope.eWalletShow = false;
                var color = $scope.eWalletShow = false ? '#376092' : '';
                $("#chequeButton").css('background-color', color);
            }

            if (val === 'eWalletShow') {
                var color = $scope[val] = true ? '#FFA500' : 'orange';
                $("#eWalletButton").css('background-color', color);
            } else {
                $scope.eWalletShow = false;
                var color = $scope.eWalletShow = false ? '#376092' : '';
                $("#eWalletButton").css('background-color', color);
            }

            if (val === 'giftShow') {
                var color = $scope[val] = true ? '#FFA500' : 'orange';
                $("#giftButton").css('background-color', color);
            } else {
                $scope.giftShow = false;
                var color = $scope.giftShow = false ? '#376092' : '';
                $("#giftButton").css('background-color', color);
            }

        }

        $scope.cashDetails = [];
        $scope.cardDetails = [];
        $scope.creditNoteDetails = [];
        $scope.couponDetails = [];
        $scope.chequeDetails = [];
        $scope.ewalletDetails = [];
        $scope.giftCardDetails = [];

        $scope.cash_Paymnet_Details = [];
        $scope.card_Paymnet_Details = [];
        $scope.credit_Note_Paymnet_Details = [];
        $scope.coupon_Paymnet_Details = [];
        $scope.cheque_Paymnet_Details = [];
        $scope.eWallet_Paymnet_Details = [];
        $scope.giftCard_Paymnet_Details = [];
        $scope.multiPaymentType = function (payType) {
            if (payType === 'cash') {

                $scope.cash_Paymnet_Details = [{
                    'paymentType': payType,
                    'cashAmt': $scope.tenderedAmt,
                    'totalCPAmountRefunded': $scope.changeAmt
                }];
            }
            if (payType === 'card') {
                $scope.card_Paymnet_Details = [{
                    'voucherAmt': $scope.cardAmount,
                    'voucherNo': $scope.cardNumber,
                    'vouchersupplierName': $scope.cardCustName,
                    'VoucherExpiryDAte': $scope.cardExpiryDate,
                    'voucherCode': $scope.cardApprovalCode
                }];
            }
            if (payType === 'creditNote') {
                $scope.credit_Note_Paymnet_Details = [{
                    'paymentType': payType,
                    'cardAmt': $scope.creditNoteAmt,
                    'cardNo': $scope.creditNoteNo,
                    'cardDate': $scope.creditCNDate
                }];
            }
            if (payType == 'coupon') {
                $scope.coupon_Paymnet_Details = [{
                    'paymentType': payType,
                    'couponAmt': $scope.couponAmount,
                    'couponNo': $scope.couponNo,
                    'couponDate': $scope.couponDate
                }];
            }
            if (payType == 'cheque') {
                $scope.cheque_Paymnet_Details = [{
                    'paymentType': payType,
                    'bankName': $scope.bankName,
                    'amount': $scope.chequeAmt,
                    'chequeDate': $scope.chequeDate,
                    'transactionNo': $scope.chequeNo
                }];
            }
            if (payType == 'eWallet') {
                $scope.eWallet_Paymnet_Details = [{
                    'paymentType': payType,
                    'eWalletAmt': $scope.ewalletAmt,
                    'ewalletMobile': $scope.ewalletMobile,
                    'ewalletCode': $scope.ewalletCode
                }];
            }
            if (payType == 'gift') {
                $scope.giftCard_Paymnet_Details = [{
                    'paymentType': payType,
                    'giftCardNo': $scope.giftCardNumber,
                    'giftCardAmt': $scope.giftAmount,
                    'AmountUsed': $scope.giftAmtUsed
                }];
            }

        }

        $scope.saveData = true;



        $scope.removeAllItems = function () {
            $scope.totalCheckOutamt = parseFloat('0.00');
            $scope.totalBeforDiscount = parseFloat('0.00');
            $scope.totalDisc = parseFloat('0.00');
            $scope.totalAfterDiscount = parseFloat('0.00');
            $scope.totalTaxAmt = parseFloat('0.00');
            $scope.totalBillAmount = parseFloat('0.00');
            $scope.totalQty = "";
            $scope.netAmount = parseFloat('0.00');
            $scope.totalDiscountValue = parseFloat('0.00');
            $scope.tenderedAmt = parseFloat('0.00');
            $scope.changeAmt = parseFloat('0.00');
            $scope.paymentTotalDue = parseFloat('0.00');
            $scope.selectedItemsList = [];
            $scope.selectedTemItemsList = [];
            $scope.selectedTemItemsList.push({
                itemCode: ""
            });
            $scope.itemSearchText = "";
            $scope.piid = null;
            $scope.operation = 'Create';
            $scope.op = 'Create';
            $scope.custId = 2;
            $scope.supplier = 2;
            $scope.displayTotalAmount = 0;
            $scope.displayItemLength = 0;
            $scope.barcodeInput = "";
            $scope.arBalance = parseFloat('0.00');
            $scope.advancepayment = false;
            $scope.salesOrderId = "";
            $scope.cardShow = false;
            $scope.creditNoteShow = false;
            $scope.CouponShow = false;
            $scope.eWalletShow = false;
            $scope.cashShow = false;
            $scope.giftShow = false;
            $scope.chequeShow = false;
            $scope.tenderedAmt = 0;
            $scope.changeAmt = 0;
            $scope.changeAmt = 0;
            $scope.creditNoteAmt = 0;
            $scope.cardAmount = 0;
            $scope.ewalletAmt = 0;
            $scope.couponAmount = 0;
            $scope.supplierId = 1;
            $scope.isAcceptDisabled = true;
        };

        $scope.editInvoiceList = function (val) {
            if (angular.isUndefined(val)) {
                val = "";
            }
            $http.post('/purchase' + '/getPurchaseInvoicesList?status='+ "Prepared" + "&search=" + val).then(function (response) {
                var data = response.data;
                $scope.posSalesList = angular.copy(data);
                $("#PosInvoiceEdit").modal('show');
                $scope.searchText = val;
                $scope.itemSearchText = val;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        }
        $scope.editDraftInvoiceList = function (val) {
            if (angular.isUndefined(val)) {
                val = "";
            }
            $http.post('/purchase' + '/getPurchaseInvoicesList?status='+ "Draft" + "&search=" + val).then(function (response) {
                var data = response.data;
                $scope.posSalesList = angular.copy(data);
                $("#PosInvoiceDraft").modal('show');
                $scope.searchText = val;
                $scope.itemSearchText = val;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        }
        $scope.getEditPurchaseInvoice = function (invoiceNo,type) {
            if (invoiceNo === "") {
                Notification.info({message: "Please Enter Invoice No", positionX: 'center', delay: 2000});
                return;
            }
            $http.post('/purchase' + '/getPurchaseInvoice?no=' + invoiceNo).then(function (response) {
                var data = response.data;
                var status=true;
                angular.forEach(data.selectedItemsList,function (val,key) {
                    if(val.remainingQty!=val.qty){
                        status=false;
                    }
                })
                if(status==true){
                    $scope.operation = 'Edit';
                    $scope.op = 'Edit';
                    $scope.selectedItemsList = data.selectedItemsList;
                    $scope.getTotal();
                    $scope.tenderedAmt = data.balanceAmount;
                    if ($scope.netAmount < data.balanceAmount) {
                        $scope.changeAmt = data.balanceAmount - $scope.netAmount;
                        $scope.saveButton = 'save';
                    } else {
                        $scope.changeAmt = 0;
                        $scope.saveButton = 'save';
                    }
                    if (data.balanceAmount == 0) {
                        $scope.saveButton = 'ExactCash';
                    }
                    $scope.piid = data.piid;
                    $scope.billNO = data.piNo;
                    $scope.agentText = parseInt(data.agentIdOfInvoice);
                    $scope.currencyText = parseInt(data.currencyIdOfInvoice);
                    $scope.employeeText = parseInt(data.employeeId);
                    $scope.exchangeRateText = data.exchangerateValue;
                    $scope.supplierPoText = data.supplierPo;
                    $scope.shippingMethodText = data.shippingmethodId;
                    $scope.shipingmethod = parseInt(data.shippingmethodId);
                    $scope.shippingmethodreferenceno = data.shippingReferenceNo;
                    $scope.referenceNo = data.referenceNo;
                    $scope.supplierSearchText = data.supplierName;
                    $scope.selectedsupplier = data.supplierName;
                    $scope.supplierId = data.supplierId;
                    var dateObject = new Date(data.date);
                    $scope.dt = dateObject;
                    $scope.fullSimplTax = data.taxType;
                    $scope.arBalance = data.arbalance;
                    if(type=='Return'){
                        angular.forEach($scope.selectedItemsList, function (value, key) {
                            value.taxamt=0;
                            $scope.selectedItemsList[key]["amtexclusivetax"] = 0;
                            $scope.selectedItemsList[key].discountAmt = value.returnQty * value.discountConfigAmt;
                            $scope.selectedItemsList[key].discPercent = value.discountConfigAmt;
                        });
                        $scope.operation='Return';
                        $("#PosInvoiceReturn").modal('hide');
                    }else {
                        $("#PosInvoiceEdit").modal('hide');
                    }
                }else {
                    Notification.error({message: "Already Returned", positionX: 'center', delay: 2000});
                }
            }, function (error) {
                if (error.status == 500) {
                    Notification.error({message: "Something went wrong in server", positionX: 'center', delay: 2000});
                } else {
                    Notification.error({message: data.message, positionX: 'center', delay: 2000});
                }
            })
        };


        $scope.populateSavePIMultiPayData = function (paymentType, operation) {
            $scope.cashDetails = {
                multiCashPaymentList: $scope.cash_Paymnet_Details,
                totalCPBeforDiscount: $scope.totalBillAmount,
                totalCPDiscount: $scope.netAmount,
                totalCPAmountTendered: $scope.tenderedAmt,
                totalCPAmountRefunded: $scope.changeAmt

            };
            $scope.cardDetails = {
                multiVoucherPayments: $scope.card_Paymnet_Details,
                totalVPBeforDiscount: $scope.totalBillAmount,
                totalVPDiscount: $scope.netAmount,
                totalVPAmountTendered: $scope.tenderedAmt,
                totalVPAmountRefunded: $scope.changeAmt

            };
            $scope.creditNoteDetails = {
                cardPaymentList: $scope.credit_Note_Paymnet_Details,
                totalCCPBeforDiscount: $scope.totalBillAmount,
                totalCCPDiscount: $scope.netAmount,
                totalCCPAmountTendered: $scope.tenderedAmt,
                totalCCPAmountRefunded: $scope.changeAmtOfCredit

            };
            $scope.couponDetails = {
                couponPaymentList: $scope.coupon_Paymnet_Details,
                totalCOBeforeDiscount: $scope.totalBillAmount,
                totalCODiscount: $scope.netAmount,
                totalCOAmountTendered: $scope.tenderedAmt,
                totalCOAmountRefunded: $scope.changeAmt

            };
            $scope.chequeDetails = {
                chequePayments: $scope.cheque_Paymnet_Details,
                totalBPBeforeDiscount: $scope.totalBillAmount,
                totalBPDiscount: $scope.netAmount,
                totalBPAmountTendered: $scope.tenderedAmt,
                totalBPAmountRefunded: $scope.changeAmt
            };
            $scope.ewalletDetails = {
                ewalletDEtailsList: $scope.eWallet_Paymnet_Details,
                totalEWBeforDiscount: $scope.totalBillAmount,
                totalEWDiscount: $scope.netAmount,
                totalEWAmountTendered: $scope.tenderedAmt,
                totalEWAmountRefunded: $scope.changeAmt

            };
            $scope.giftCardDetails = {
                giftCardDetails: $scope.giftCard_Paymnet_Details,
                totalGiBeforDiscount: $scope.totalBillAmount,
                totalGiDiscount: $scope.netAmount,
                totalGiAmountTendered: $scope.tenderedAmt,
                totalGiAmountRefunded: $scope.changeAmt
            };
            var data = {
                operation: operation,
                selectedItemsList: $scope.selectedItemsList,
                piid: $scope.piid,
                piNo: $scope.billNO,
                totalTaxAmt: $scope.totalTaxAmt,
                totalCheckOutamt: $scope.netAmount,
                totalTenderedAmount: $scope.tenderedAmt,
                supplierId: $scope.supplierId,
                supplierName: $scope.supplierSearchText,
                amountReturned: $scope.changeAmt,
                discountAmount: $scope.totalDiscountValue,
                userName: $('#userName').val(),
                agentId: $scope.agentId,
                userId: $scope.userId,
                currencyIdOfInvoice: $scope.currencyId,
                cmpyLocation: $scope.fromLocation,
                custLocation: $scope.toLocation,
                referenceNo: $scope.referenceNo,
                dateOfInvoice: $scope.dt,
                shippingMethodName: $scope.shippingMethodText,
                memo: $scope.memo,
                vehicleNo: $scope.vehicleNo,
                shippingReferenceNo: $scope.shippingRefNo,
                ewayBillNo: $scope.ewayBillNo,
                distance: $scope.destination,
                cashPayment: $scope.cashDetails,
                cardPayment: $scope.creditNoteDetails,
                creditPayment: $scope.creditNoteDetails,
                bankPayment: $scope.chequeDetails,
                voucherPayment: $scope.cardDetails,
                ewalletDetails: $scope.ewalletDetails,
                couponDetails: $scope.couponDetails,
                totalCashPayment: $scope.tenderedAmt,
                totalVoucherPayment: $scope.cardAmount,
                totalCardPayment: $scope.creditNoteAmt,
                totalEWalletPayment: $scope.ewalletAmt,
                totalCouponPayment: $scope.couponAmount,
                prescriptionImage: $scope.fileToUpload,
                paymentType: paymentType
            };
            console.log("POSDETAILS");
            console.log(data);
            return data;
        }

        $scope.editSelectedItemList1 = function (selectedItemList, index) {
            if ($scope.selectedTemItemsList.length > 0 && $scope.selectedTemItemsList[0].itemCode != "") {
                $scope.selectedItemsList[index + 1] = $scope.selectedTemItemsList[0];
                $scope.selectedTemItemsList[0] = $scope.selectedItemsList[index];
            } else {
                $scope.selectedTemItemsList[0] = $scope.selectedItemsList[index];
            }
            data1 = $scope.selectedTemItemsList;
            $scope.selectedItemsList.splice(index, 1);
            $("#supplierPopup").modal('hide');
            j1 = 0;
            $scope.getTotal();
            $scope.tenderedAmt = parseInt(0);
            $scope.changeAmt = parseInt(0);
            $scope.totalDisc = parseInt(0);
            $scope.totalDiscountValue = parseInt(0);
        }

        $scope.cancelInvoice = function (no) {
            $http.post('/purchase' + '/cancelPurchaseInvoice?no=' + no).then(function (response) {
                var data = response.data;
                $("#PosInvoiceEdit").modal('hide');
                Notification.info({
                    message: 'Invoice Cancelled Successfully',
                    positionX: 'center',
                    delay: 2000
                })
            });
        };
        $scope.deletePOSInvoice = function (no) {
            $http.post('/purchase'+ '/deletePOSPurchaseBilling?invoiceNo=' + no).then(function (response) {
                var data = response;
                $("#PosInvoiceEdit").modal('hide');
                Notification.info({message: 'Invoice Deleted Successfully', positionX: 'center', delay: 2000})
                $scope.getPageLoadData();

            });
        };
        $scope.exactCash = function () {
            if ($scope.selectedItemsList.length <= 0) {
                Notification.error({message: 'Atleast One Item has to be selected ', positionX: 'center', delay: 2000});
                return;
            }
            $scope.cashDetails = [];
            $scope.cash_Paymnet_Details = [];
            $scope.cash_Paymnet_Details.push({
                'paymentType': 'cash',
                'cashAmt': $scope.netAmount,
                'totalCPAmountRefunded': 0
            });
            $scope.cashDetails = {
                totalCPBeforDiscount: $scope.totalBillAmount,
                totalCPDiscount: $scope.netAmount,
                totalCPAmountTendered: $scope.netAmount,
                totalCPAmountRefunded: 0,
                multiCashPaymentList: $scope.cash_Paymnet_Details
            };
            $scope.saveMultiPaymentPOS('ExactCash');

        }
        $scope.saveWithTenderedCash = function () {
            if ($scope.selectedItemsList.length <= 0) {

                Notification.error({message: 'Atleast One Item has to be selected ', positionX: 'center', delay: 2000});
                return;
            }
            $scope.cashDetails = [];
            $scope.cash_Paymnet_Details = [];
            $scope.cash_Paymnet_Details.push({
                'paymentType': 'cash',
                'cashAmt': $scope.tenderedAmt,
                'totalCPAmountRefunded': $scope.changeAmt
            });
            $scope.cashDetails = {
                totalCPBeforDiscount: $scope.totalBillAmount,
                totalCPDiscount: $scope.netAmount,
                totalCPAmountTendered: $scope.tenderedAmt,
                totalCPAmountRefunded: $scope.changeAmt,
                multiCashPaymentList: $scope.cash_Paymnet_Details
            };
            $scope.saveMultiPaymentPOS('Cash');
        }


        $scope.getTotal = function () {
            var totalAmt = 0.00;
            var totalTaxAmt = 0.00;
            var cessTotalTaxAmt = 0.00;
            var totalDiscountAmt = 0.00;
            var totalQty = 0.00;
            var itemCount = 0;
            var netAmt = 0.00;
            angular.forEach($scope.selectedItemsList, function (item, index) {
                totalAmt += parseFloat(item.amtexclusivetax);
                totalTaxAmt += parseFloat(item.taxamt);
                totalQty += parseFloat(item.qty);
                cessTotalTaxAmt += parseFloat(item.cessTaxAmt);
                totalDiscountAmt += parseFloat(item.discountAmt);
                totalQty += parseFloat(item.returnQty);
                itemCount++;
                netAmt = totalAmt + totalTaxAmt;
            });
            var totalAfterDiscount = parseFloat(netAmt);
            $scope.totalBillAmount = parseFloat(totalAmt.toFixed(2));
            $scope.displayItemLength = parseInt(itemCount);
            $scope.totalTaxAmt = parseFloat(totalTaxAmt.toFixed(2));
            $scope.totalQty = parseFloat(totalQty.toFixed(2));
            $scope.netAmount = parseFloat(netAmt.toFixed(2));
            $scope.displayTotalAmount = parseFloat(totalAmt.toFixed(2));
            $scope.totalBeforDiscount = parseFloat(totalAmt.toFixed(2));
            $scope.totalDiscount = parseFloat(totalDiscountAmt.toFixed(2));
            $scope.totalAfterDiscount = parseFloat(totalAfterDiscount.toFixed(2));
            $scope.cessTotalTaxAmt = parseFloat(cessTotalTaxAmt).toFixed(2);
            $scope.paymentTotalDue = $scope.netAmount;
            if (isNaN(cessTotalTaxAmt)) {
                $scope.cessTotalTaxAmt = parseFloat(0);
            }
            $scope.returnQty = parseFloat(totalQty.toFixed(2));
        };
        $scope.discRegex = /^[0-9]\d{0,9}(\.\d{1,3})?%?$/;

        $scope.configDiscountTotal = function (disConfig) {
            var TotalBillAmt = $scope.totalBillAmount;
            if (disConfig != null) {
                var discount = disConfig;
                var discountRegex = $scope.discRegex;
                if (discount.match(discountRegex)) {
                    console.log(console);
                    $scope.discType = disConfig.toString().slice(-1);
                    if ($scope.discType == "%") {
                        $scope.billValue = "Percentage";
                        $scope.discPercent = parseFloat(disConfig.toString().slice(0, -1));
                        $scope.totalDiscountValue = parseFloat(((TotalBillAmt * $scope.discPercent) / 100).toFixed(2));
                        if ($scope.totalDiscountValue > $scope.netAmount || $scope.totalDiscountValue == $scope.netAmount) {
                            Notification.error({
                                message: 'Amount should not be greater than NetAmount',
                                positionX: 'center',
                                delay: 2000
                            });
                            $scope.totalDisc = parseInt(0);
                            $scope.totalDiscountValue = parseInt(0);

                        } else {
                            $scope.netAmount = parseFloat(((TotalBillAmt - $scope.totalDiscountValue) + $scope.totalTaxAmt).toFixed(2));
                            $scope.paymentTotalDue = $scope.netAmount;
                        }

                    }
                    else {
                        $scope.billValue = "Amount";
                        $scope.discPercent = parseFloat(disConfig);
                        $scope.totalDiscountValue = disConfig;
                        if ($scope.totalDiscountValue > $scope.netAmount || $scope.totalDiscountValue == $scope.netAmount) {
                            Notification.error({
                                message: 'Amount should not be greater than NetAmount',
                                positionX: 'center',
                                delay: 2000
                            });
                            $scope.totalDisc = parseInt(0);
                            $scope.totalDiscountValue = parseInt(0);
                            $scope.getTotal();
                        }
                        else {
                            $scope.netAmount = parseFloat(((TotalBillAmt - disConfig) + $scope.totalTaxAmt).toFixed(2));
                            $scope.paymentTotalDue = $scope.netAmount;
                        }

                    }
                }
                else {
                    Notification.error({message: 'Please Enter Valid Percentage ', positionX: 'center', delay: 2000});
                    $scope.totalDiscountValue = parseInt(0);
                    $scope.totalDisc = parseInt(0);
                }


            } else {
                $scope.totalDisc = parseInt(0);
                $scope.totalDiscountValue = parseInt(0);
                $scope.getTotal();
            }

        }
        $scope.totalCashAmt = 0.00;
        $scope.totalCardAmt = 0.00;
        $scope.totalcreditNoteAmt = 0.00;
        $scope.totalCouponAmt = 0.00;
        $scope.totalEWalletAmt = 0.00;
        $scope.totalChequeAmt = 0.00;
        $scope.tenderedAmt = 0.00;
        $scope.paymentTotalBalance = 0.00;
        $scope.changeAmt = 0.0;
        $scope.totalBalance = 0.00;
        $scope.cardAmount = 0.00;
        $scope.creditNoteAmt = 0.00;
        $scope.couponAmount = 0.00;
        $scope.ewalletAmt = 0.00;
        $scope.giftAmount = 0.00;
        $scope.changeAmtOfCredit = 0.00;
        $scope.paymentTotalDue = $scope.netAmount;

        $scope.dueAmount = function (tenderedAmt) {
            if (angular.isUndefined(tenderedAmt)) {
                $scope.tenderedAmt = parseInt(0);
                return false;
            }
            $scope.paymentTotalDue = $scope.netAmount;
            $scope.tenderedAmt = tenderedAmt;
            if (tenderedAmt > $scope.netAmount) {
                $scope.changeAmt = parseFloat((tenderedAmt - $scope.netAmount).toFixed(2));
                $scope.paymentTotalBalance = 0.00;
            } else {
                $scope.changeAmt = 0.0;
                $scope.paymentTotalBalance = parseFloat(($scope.netAmount - tenderedAmt).toFixed(2));
            }
            if ($scope.tenderedAmt > 0) {
                $scope.saveButton = 'save';
            }
            else {
                $scope.saveButton = 'ExactCash';
            }

        }
        $scope.cardOriginal = parseInt(0);
        $scope.paymentDue = function (pay) {
            if ($scope.tenderedAmt == $scope.paymentTotalDue) {
                $scope.cardAmount = parseInt(0);
                $scope.creditNoteAmt = parseInt(0);
                $scope.couponAmount = parseInt(0);
                $scope.chequeAmt = parseInt(0);
                $scope.ewalletAmt = parseInt(0);
                $scope.giftAmount = parseInt(0);
            }
            if ($scope.cardAmount == $scope.paymentTotalDue) {
                $scope.tenderedAmt = parseInt(0);
                $scope.creditNoteAmt = parseInt(0);
                $scope.couponAmount = parseInt(0);
                $scope.chequeAmt = parseInt(0);
                $scope.ewalletAmt = parseInt(0);
                $scope.giftAmount = parseInt(0);
            }
            if ($scope.creditNoteAmt == $scope.paymentTotalDue) {
                $scope.tenderedAmt = parseInt(0);
                $scope.cardAmount = parseInt(0);
                $scope.couponAmount = parseInt(0);
                $scope.chequeAmt = parseInt(0);
                $scope.ewalletAmt = parseInt(0);
                $scope.giftAmount = parseInt(0);
            }
            if ($scope.couponAmount == $scope.paymentTotalDue) {
                $scope.cardAmount = parseInt(0);
                $scope.creditNoteAmt = parseInt(0);
                $scope.tenderedAmt = parseInt(0);
                $scope.chequeAmt = parseInt(0);
                $scope.ewalletAmt = parseInt(0);
                $scope.giftAmount = parseInt(0);
            }
            if ($scope.chequeAmt == $scope.paymentTotalDue) {
                $scope.tenderedAmt = parseInt(0);
                $scope.creditNoteAmt = parseInt(0);
                $scope.couponAmount = parseInt(0);
                $scope.cardAmount = parseInt(0);
                $scope.ewalletAmt = parseInt(0);
                $scope.giftAmount = parseInt(0);
            }
            if ($scope.ewalletAmt == $scope.paymentTotalDue) {
                $scope.cardAmount = parseInt(0);
                $scope.creditNoteAmt = parseInt(0);
                $scope.couponAmount = parseInt(0);
                $scope.chequeAmt = parseInt(0);
                $scope.tenderedAmt = parseInt(0);
                $scope.giftAmount = parseInt(0);
            }
            if ($scope.giftAmount == $scope.paymentTotalDue) {
                $scope.tenderedAmt = parseInt(0);
                $scope.creditNoteAmt = parseInt(0);
                $scope.couponAmount = parseInt(0);
                $scope.chequeAmt = parseInt(0);
                $scope.ewalletAmt = parseInt(0);
                $scope.cardAmount = parseInt(0);
            }
            $scope.totalPayAmount = parseInt(0);
            if (angular.isUndefined($scope.tenderedAmt)) {
                $scope.tenderedAmt = parseInt(0);
            }
            if (angular.isUndefined($scope.cardAmount)) {
                $scope.cardAmount = parseInt(0);
            }
            if (angular.isUndefined($scope.creditNoteAmt)) {
                $scope.creditNoteAmt = parseInt(0);
            }
            if (angular.isUndefined($scope.couponAmount)) {
                $scope.couponAmount = parseInt(0);
            }
            if (angular.isUndefined($scope.ewalletAmt)) {
                $scope.ewalletAmt = parseInt(0);
            }
            if (angular.isUndefined($scope.creditNoteAmt)) {
                $scope.creditNoteAmt = parseInt(0);
            }
            if (angular.isUndefined($scope.giftAmount)) {
                $scope.giftAmount = parseInt(0);
            }
            if (angular.isUndefined($scope.chequeAmt)) {
                $scope.chequeAmt = parseInt(0);
            }
            $scope.totalPayAmount = ($scope.tenderedAmt - 0) + ($scope.cardAmount - 0) + ($scope.creditNoteAmt - 0) + ($scope.couponAmount - 0) + ($scope.ewalletAmt - 0) + ($scope.giftAmount - 0);
            if ($scope.totalPayAmount < $scope.netAmount) {
                $scope.paymentTotalBalance = parseFloat($scope.netAmount - $scope.totalPayAmount).toFixed(2);
                if (pay == 'cash') {
                    $scope.changeAmt = 0.0;
                }
            }
            else {
                $scope.paymentTotalBalance = 0.00;
                if (pay == 'cash') {
                    $scope.changeAmt = parseFloat(($scope.totalPayAmount - $scope.netAmount).toFixed(2));
                }
                else if (pay == 'creditNote') {
                    $scope.changeAmtOfCredit = parseFloat(($scope.totalPayAmount - $scope.netAmount).toFixed(2));
                } else {
                    // Notification.error("Amount should not be greater than Total Amount");
                    if (pay == 'card') {
                        if ($scope.cardAmount > $scope.netAmount) {
                            Notification.error("Amount should not be greater than Total Amount");
                            $scope.cardAmount = $scope.netAmount + $scope.changeAmt - $scope.tenderedAmt - $scope.creditNoteAmt - $scope.couponAmount - $scope.ewalletAmt - $scope.giftAmount - $scope.chequeAmt;
                        } else {
                            $scope.cardAmount = $scope.netAmount + $scope.changeAmt - $scope.tenderedAmt - $scope.creditNoteAmt - $scope.couponAmount - $scope.ewalletAmt - $scope.giftAmount - $scope.chequeAmt;
                        }
                    }
                    if (pay == 'coupon') {
                        if ($scope.couponAmount > $scope.netAmount) {
                            Notification.error("Amount should not be greater than Total Amount");
                            $scope.couponAmount = $scope.netAmount + $scope.changeAmt - $scope.tenderedAmt - $scope.creditNoteAmt - $scope.cardAmount - $scope.ewalletAmt - $scope.giftAmount - $scope.chequeAmt;
                        } else {
                            $scope.couponAmount = $scope.netAmount + $scope.changeAmt - $scope.tenderedAmt - $scope.creditNoteAmt - $scope.cardAmount - $scope.ewalletAmt - $scope.giftAmount - $scope.chequeAmt;
                        }
                    }
                    if (pay == 'eWallet') {
                        if ($scope.ewalletAmt > $scope.netAmount) {
                            Notification.error("Amount should not be greater than Total Amount");
                            $scope.ewalletAmt = $scope.netAmount + $scope.changeAmt - $scope.tenderedAmt - $scope.creditNoteAmt - $scope.couponAmount - $scope.cardAmount - $scope.giftAmount - $scope.chequeAmt;
                        }
                        else {
                            $scope.ewalletAmt = $scope.netAmount + $scope.changeAmt - $scope.tenderedAmt - $scope.creditNoteAmt - $scope.couponAmount - $scope.cardAmount - $scope.giftAmount - $scope.chequeAmt;
                        }

                    }
                    if (pay == 'gift') {
                        if ($scope.giftAmount > $scope.netAmount) {
                            Notification.error("Amount should not be greater than Total Amount");
                            $scope.giftAmount = $scope.netAmount + $scope.changeAmt - $scope.tenderedAmt - $scope.creditNoteAmt - $scope.couponAmount - $scope.ewalletAmt - $scope.cardAmount - $scope.chequeAmt;

                        } else {
                            $scope.giftAmount = $scope.netAmount + $scope.changeAmt - $scope.tenderedAmt - $scope.creditNoteAmt - $scope.couponAmount - $scope.ewalletAmt - $scope.cardAmount - $scope.chequeAmt;
                        }
                    }
                    if (pay == 'cheque') {
                        if ($scope.giftAmount > $scope.netAmount) {
                            Notification.error("Amount should not be greater than Total Amount");
                            $scope.chequeAmt = $scope.netAmount + $scope.changeAmt - $scope.tenderedAmt - $scope.creditNoteAmt - $scope.couponAmount - $scope.ewalletAmt - $scope.cardAmount - $scope.giftAmount;

                        } else {
                            $scope.chequeAmt = $scope.netAmount + $scope.changeAmt - $scope.tenderedAmt - $scope.creditNoteAmt - $scope.couponAmount - $scope.ewalletAmt - $scope.cardAmount - $scope.giftAmount;
                        }
                    }
                }
            }
            if ($scope.paymentTotalBalance > 0) {
                $scope.isAcceptDisabled = true;
            } else {
                $scope.isAcceptDisabled = false;
            }
        }

        $scope.balanceCalc = function (selectedType) {

            if (($scope.paymentTotalBalance) > 0) {
                if (selectedType === 'cash' && parseInt($scope.tenderedAmt) == 0) {
                    $scope.tenderedAmt = $scope.paymentTotalBalance;
                }
                else if (selectedType === 'card' && parseInt($scope.cardAmount) == 0) {
                    $scope.cardAmount = $scope.paymentTotalBalance;
                }
                else if (selectedType === 'creditNote' && parseInt($scope.creditNoteAmt) == 0) {
                    $scope.creditNoteAmt = $scope.paymentTotalBalance;
                }
                else if (selectedType === 'coupon' && parseInt($scope.couponAmount) == 0) {
                    $scope.couponAmount = $scope.paymentTotalBalance;
                }
                else if (selectedType === 'cheque' && parseInt($scope.chequeAmt) == 0) {
                    $scope.chequeAmt = $scope.paymentTotalBalance;
                }
                else if (selectedType === 'ewallet' && parseInt($scope.ewalletAmt) == 0) {
                    $scope.ewalletAmt = $scope.paymentTotalBalance;
                }
                else if (selectedType === 'gift' && parseInt($scope.giftAmount) == 0) {
                    $scope.giftAmount = $scope.paymentTotalBalance;
                }
                $scope.totalBalance = ($scope.tenderedAmt - 0) + ($scope.cardAmount - 0) + ($scope.creditNoteAmt - 0) + ($scope.couponAmount - 0) + ($scope.ewalletAmt - 0) + ($scope.giftAmount - 0);
                $scope.paymentTotalBalance = $scope.paymentTotalDue - $scope.totalBalance;
            }
        }
        $scope.selectedItemListRemoval = {};
        $scope.removeSelectedItems = function () {
            var length = $scope.selectedItemsList.length;
            if (angular.isUndefined($scope.selectedItemsList) || $scope.selectedItemsList.length <= 0) {
                Notification.error({message: 'At lest One item has to be selected', positionX: 'center', delay: 2000});
            } else {
                $scope.selectedItemsList = $scope.selectedItemsList.filter(function (data, index) {
                    return !($scope.selectedItemListRemoval[index] !== undefined && $scope.selectedItemListRemoval[index]);
                });
                $scope.displayItemLength = $scope.selectedItemsList.length;
                $scope.selectedItemListRemoval = {};
                $scope.getTotal();
                if ($scope.selectedItemsList.length == length) {
                    Notification.error({
                        message: 'At lest One item has to be selected',
                        positionX: 'center',
                        delay: 2000
                    });
                }
            }
        };

        $scope.updatesimplifiedTax = function () {
            var taxType = $scope.fullSimplTax;
            if (taxType === 'CGST:SGST/UGST') {
                angular.forEach($scope.selectedItemsList, function (value, key) {
                    var splitValue = value.igTax / 2;
                    $scope.selectedItemsList[key].taxAmountSplit = parseFloat(splitValue).toFixed(2) + ":" + parseFloat(splitValue).toFixed(2)
                });
            } else {
                if (taxType === 'IGST') {
                    angular.forEach($scope.selectedItemsList, function (value, key) {
                        var splitValue = value.igTax;
                        $scope.selectedItemsList[key].taxAmountSplit = splitValue.toString();
                    });
                }
            }
        }

        $scope.imageUpload = function (event) {
            var files = event.target.files;
            var file = files[0];
            var srcString;
            var imageCompressor = new ImageCompressor;
            var compressorSettings = {
                toWidth: 200,
                toHeight: 200,
                mimeType: 'image/png',
                mode: 'strict',
                quality: 1,
                grayScale: false,
                sepia: false,
                threshold: false,
                speed: 'low'
            };
            if (files && file) {
                var reader = new FileReader();
                reader.onload = function (readerEvt) {
                    binaryString = readerEvt.target.result;
                    $('.image-preview').attr('src', binaryString);
                };
                reader.readAsDataURL(file);
                reader.onloadend = function () {
                    srcString = $('.image-preview').attr("src");
                    imageCompressor.run(srcString, compressorSettings, proceedCompressedImage);
                };
            }
        };

        function proceedCompressedImage(compressedSrc) {
            $('#image-preview').attr('src', compressedSrc);
            $scope.fileToUpload = compressedSrc;
        }
        $scope.isUndefinedOrNull = function (data) {
            return (angular.isUndefined(data) || data === null || data === '' || data === 'null');
        };

        var div = $('.textdiv'),
            height = div.height();
        $scope.addItem = function (itemCode, keyEvent) {
            j1 = 0;
            div.animate({scrollTop: height}, 500);
            height += div.height();
            if (itemCode.serializableStatus == 'Serialize' || itemCode.itemTypeName == 'Service' || itemCode.serializableStatus == 'Batch') {
                var localItemCode;
                localItemCode = itemCode.itemName;
                $scope.getItem(localItemCode);
                $scope.barcodeInput = "";
            }
            else {
                div.animate({scrollTop: height}, 500);
                height += div.height();
                var localItemCode;
                localItemCode = itemCode.itemName;
                if (keyEvent === 13 && ($scope.barcodeInput === "" || angular.isUndefined($scope.barcodeInput))) {
                    return false;
                }
                $('#barcodeInput').val('');
                $scope.iteamIndex = $scope.itemIndexOfItemCode($scope.selectedItemsList, itemCode.itemCode);
                if (!angular.isUndefined($scope.iteamIndex) && $scope.iteamIndex !== null && $scope.iteamIndex !== -1) {
                    $scope.currentQty = $scope.selectedItemsList[$scope.iteamIndex].qty;
                    $scope.selectedItemsList[$scope.iteamIndex].qty = parseFloat($scope.currentQty) + 1;
                    $scope.selectedList = $scope.selectedItemsList;
                    $scope.editSelectedItemList($scope.selectedList[$scope.iteamIndex]);
                } else {
                    $scope.getItem(localItemCode);
                }

                $scope.barcodeInput = "";
            }
            $scope.popUpDivHide('itemPopup');
        };
        $scope.removePaymentPopUpDetails = function () {
            $scope.cardNumber = "";
            $scope.cardCustName = "";
            $scope.cardExpiryDate = "";
            $scope.cardApprovalCode = "";
            $scope.creditNoteNo = "";
            $scope.creditCNDate = "";
            $scope.couponNo = "";
            $scope.couponDate = "";
            $scope.ewalletMobile = "";
            $scope.ewalletCode = "";
            $scope.chequeNo = "";
            $scope.bankName = "";
            $scope.chequeDate = "";
            $scope.giftCardNumber = "";
            $scope.giftAmount = "";
            $scope.giftAmount = "";
            $scope.giftAmtUsed = "";

        }
        $scope.editInvoiceListReturn = function (val,val1) {
            if (angular.isUndefined(val1)) {
                val1 = "";
            }
            if(val=='Return'||val==""){
                val="Prepared";
                $scope.operation="Return";
            }
            $http.post('/purchase' + '/getPurchaseInvoicesList?status='+val + "&search=" + val1).then(function (response) {
                var data = response.data;
                $scope.posSalesList = angular.copy(data);
                $("#PosInvoiceReturn").modal('show');
                $scope.searchText = val;
                $scope.itemSearchText = val;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        }
        $scope.getItem = function (itemName) {
            $http.post('/pos' + '/getItem?itemName=' + itemName).then(function (response) {
                var data = response.data;
                data1 = data;
                $scope.operation = "Create";
                angular.forEach(data, function (value, key) {
                    value.uom = parseInt(0);
                });
                if (data.length > 0) {
                    var status = data[0].status;
                    if (status == "InActive") {
                        Notification.info({
                            message: 'Item Status has been changed to Inactive ' + itemName,
                            positionX: 'center',
                            delay: 2000
                        });
                    }
                    else {
                        $scope.iteamIndex = $scope.itemIndexOfItemCode($scope.selectedTemItemsList, itemName);
                        if (!angular.isUndefined($scope.iteamIndex) && $scope.iteamIndex !== null && $scope.iteamIndex !== -1) {
                            $scope.currentQty = $scope.selectedTemItemsList[$scope.iteamIndex].qty;
                            $scope.selectedTemItemsList [$scope.iteamIndex].qty = parseFloat($scope.currentQty) + 1;
                            $scope.selectedList = $scope.selectedTemItemsList;
                            $scope.editSelectedItemList($scope.selectedList[$scope.iteamIndex]);
                            $scope.popUpDivHide('supplierPopup');
                        } else {
                            $scope.addPosSelectedItemsList(data[0]);
                        }
                    }
                } else {
                    Notification.info({
                        message: 'Item not found with barcode ' + itemCode,
                        positionX: 'center',
                        delay: 2000
                    });
                }
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };

        $(".bg_row_info, .bg_sub_row_info").css("background", "transparent");
        $(".bg_sub_row_info").css({"margin": "0px", "width": "100%"});
        $(".right_col").css("background", "#fff");

        setTimeout(function () {
            $scope.focus = function (val) {
                if (val == "stock") {
                    $scope.moreShow = false;
                    $(".hiWindowTable  .itemFirstRow  td #pos_stock_no").focus();
                    return true;
                }
            }
            $("#pos_supplier").focus();
        }, 1000);
        /** Shortcut keys Bining************/
        keyboardManager2.bind('f9', function () {
            $scope.popUpDivShow('paymentPopup');
        });
        keyboardManager2.bind('ctrl+d', function () {
            $scope.open1();
        });
        keyboardManager2.bind('ctrl+e', function () {
            $scope.editInvoiceList();
        });
        keyboardManager2.bind('f1', function () {
            $scope.paymentOption('cashShow');
            $scope.balanceCalc('cash');
        });
        keyboardManager2.bind('f2', function () {
            $scope.paymentOption('cardShow');
            $scope.balanceCalc('card');
        });
        keyboardManager2.bind('f3', function () {
            $scope.paymentOption('creditNoteShow');
            $scope.balanceCalc('creditNote');
        });
        keyboardManager2.bind('f4', function () {
            $scope.paymentOption('CouponShow');
            $scope.balanceCalc('coupon');
        });
        keyboardManager2.bind('f5', function () {
            $scope.paymentOption('chequeShow');
            $scope.balanceCalc('cheque');
        });
        keyboardManager2.bind('f6', function () {
            $scope.paymentOption('eWalletShow');
            $scope.balanceCalc('ewallet');
        });
        keyboardManager2.bind('f7', function () {
            $scope.paymentOption('giftShow');
            $scope.balanceCalc();
        });
        keyboardManager2.bind('f8', function () {
            var saveValue = $scope.saveButton;
            if (saveValue == 'ExactCash') {
                $scope.exactCash();
            }

        });
        keyboardManager2.bind('ctrl+l', function () {
            $scope.removeAllItems();
        });
        keyboardManager2.bind('escape', function () {
            $scope.popUpDivHide('paymentPopup');
        });
        keyboardManager2.bind('f10', function () {
            $("#pos_cash_tendered").focus();
        });
        $scope.focusVal = function (val) {
            if (val=="customer") {
                $scope.moreShow = false;
                $(".hiWindowTable  .itemFirstRow  td #pos_stock_no").focus();
            } else if (val=='stock' && $scope.selectedTemItemsList[0].itemCode != "") {
                $(".hiWindowTable  .itemFirstRow  td #pos_qty").focus();
            } else if (val=='qty' && $scope.selectedTemItemsList[0].itemCode != "") {
                $(".hiWindowTable  .itemFirstRow  td #pos_price").focus();
            } else if (val=='price' && $scope.selectedTemItemsList[0].itemCode != "") {
                $(".hiWindowTable  .itemFirstRow  td #pos_discount_per").focus();
            }
        }
    });