/**
 * Created by azgar.h on 7/1/2017.
 */

app.controller('paymentmethodCtrl',
    function ($scope, $http, $location, $filter, Notification, ngTableParams, $timeout) {
    $scope.inactiveStatus="Active";
        //added for pagination purpose @rahul
        $scope.firstPage=true;
        $scope.lastPage=false;
        $scope.pageNo=1;
        $scope.prevPage=false;
        $scope.isPrev=false;
        $scope.isNext=false;

        var location = window.location.origin;
        $scope.taxTypes = [
            {name: 'FullTax', value: 'FullTax'},
            {name: 'SimplifiedTax', value: 'SimplifiedTax'},
        ];
        $scope.notHide = "";
        $scope.updateCustomerId = function (newCustVal) {
            $scope.customer = newCustVal.customerId;
            $scope.removeAllItems();
        }
        $scope.removePaymentMethodDetails = function () {
            $scope.paymentmethodId = "0";
            $scope.PaymentMethodText="0";
            $scope.DescriptionText="0";
            $scope.TypeText="0";
            $scope.account_name="";
            $scope.defaultType=false;
            $scope.validateVoucher=false;
        };
        $scope.companyLogoPath = "";

        $scope.addNewPaymentMethodPopulate = function () {
            $scope.paymentmethodId = "";
            $scope.paymentmethodNameText = "";
            $scope.paymentmethodDescriptionText = "";
            $scope.paymentmethodTypeText = "";
            $scope.account = "";
            $scope.defaultType=false;
            $scope.validateVoucher=false;
            $scope.account_name="";
            $scope.paymentMethodText="Active";
            $('#payment-title').text("Add Payment Method");
            $("#add_new_PaymentMethod_modal").modal('show');

        };

        $scope.clicked = false;
        $scope.ButtonStatus = "InActive";
        $scope.inactivePaymentMethod = function() {

            if($scope.clicked == false){
                $scope.inactiveStatus = "InActive";
                $scope.ButtonStatus = "Active";
                var page="Page";
            }
            else{
                $scope.inactiveStatus = "Active";
                $scope.ButtonStatus = "InActive";
                var page="";
            }
            $scope.clicked = !$scope.clicked;
            $http.post('/pos' + "/getPaymentMethodList?statustext="+  $scope.inactiveStatus).then(function (response) {
                var data = response.data;
                $scope.listStatus=false;
                $scope.paymentMethodList = data;
            })
        };
        $scope.getPaymentMethodList = function (type) {
            if (angular.isUndefined(type)) {
                type = "Active";
            }
            $http.post('/pos' + '/getPaymentMethodList?type='+type).then(function (response) {
                var data = response.data;
                console.log(data);
                $scope.paymentMethodList = data;
                $("#selectItem").modal('show');
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.getPaymentMethodList();
        //Modifying method for pagination purpose on 22/08/2017 @rahul
        $scope.getPaymentMethodList = function (statustext) {

            if (angular.isUndefined(statustext)) {
                statustext = "";
            }
            $http.post('/pos' + '/getPaymentMethodList?statustext='+statustext,{
                params: {
                    "firstPage": $scope.firstPage,
                    "lastPage": $scope.lastPage,
                    "pageNo": $scope.pageNo,
                    "prevPage": $scope.prevPage,
                    "prevPage": $scope.isPrev,
                    "nextPage": $scope.isNext
                }
            }).then(function (response) {
                var data = response.data;
                $scope.paymentMethodList = data;
                /**/
                var i=0;
                $scope.listStatus=true;
                angular.forEach($scope.paymentMethodList,function (value,key) {
                    // if(value.paymentmethodName.toUpperCase()==val.toUpperCase()){
                    //     if(value.status=='InActive')  {
                    //         i++;
                    //     }
                    // }
                })
                if(i>0){
                    Notification.error({
                        message: 'Payment Method is InActive',
                        positionX: 'center',
                        delay: 2000
                    });
                }
                $scope.first = data.first;
                $scope.last = data.last;
                $scope.prev = data.prev;
                $scope.next = data.next;
                $scope.pageNo = data.pageNo;
                /**/

                if($scope.paymentMethodList=='' && $scope.paymentMethodSearchText!='' ){
                    Notification.error({message: 'No Results Found', positionX: 'center', delay: 2000});
                }
            },function (error) {
                Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
            })
        };
        //added method for pagination on 22/08/2017 @rahul
        $scope.getPaginatedPaymentMethodList = function(page) {

            switch (page) {
                case 'firstPage':
                    $scope.firstPage = true;
                    $scope.lastPage = false;
                    $scope.pageNo = 0;
                    break;
                case 'lastPage':
                    $scope.lastPage = true;
                    $scope.firstPage = false;
                    $scope.pageNo = 0;
                    break;
                case 'nextPage':
                    $scope.isNext = true;
                    $scope.isPrev = false;
                    $scope.lastPage = false;
                    $scope.firstPage = false;
                    $scope.pageNo = $scope.pageNo + 1;
                    break;
                case 'prevPage':
                    $scope.isPrev = true;
                    $scope.lastPage = false;
                    $scope.firstPage = false;
                    $scope.isNext = false;
                    $scope.pageNo = $scope.pageNo - 1;
                    break;
                default:
                    $scope.firstPage = true;
            }

         statustext="Active";
            $http.post('/pos' + '/getPaymentMethodList?statustext='+statustext,{
                params: {
                    "firstPage": $scope.firstPage,
                    "lastPage": $scope.lastPage,
                    "pageNo": $scope.pageNo,
                    "prevPage": $scope.prevPage,
                    "prevPage": $scope.isPrev,
                    "nextPage": $scope.isNext
                }
            }).then(function(data) {
                $scope.paymentMethodList = data;
                $scope.first = data.data.first;
                $scope.last = data.data.last;
                $scope.listStatus=false;
                $scope.prev = data.data.prev;
                $scope.next = data.data.next;
                $scope.pageNo = data.data.pageNo;
            });

        }
        // $scope.getAccountList = function (val) {
        //     if (angular.isUndefined(val)) {
        //         val = "";
        //     }
        //     $http.get('/pos' + '/getAccountMasterList?accountSearchText=' + val).then(function (response) {
        //         var data = response.data;
        //         $scope.accountList = angular.copy(data);
        //         $("#selectAccount").modal('show');
        //         $scope.accountSearchText = val;
        //         $scope.searchText = val;
        //     },function (error) {
        //         Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
        //     })
        //     //     // .error(function (data, status, header, config) {
        //     //     // Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
        //     // });
        // };
        //
        // $scope.getAccount = function (accountCode) {
        //     console.log(accountCode),
        //         $http.get('/pos' + '/getAccount?accountCode=' + accountCode).then(function (response) {
        //             var data = response.data;
        //             $scope.addSelectedAccountList(data[0]);
        //             $("#selectAccount").modal('hide');
        //
        //         }, function (error) {
        //             Notification.error({
        //                 message: 'Something went wrong, please try again',
        //                 positionX: 'center',
        //                 delay: 2000
        //             });
        //         })
        //
        // };
        // $scope.selectedAccountList=[];
        // $scope.addSelectedAccountList = function (data) {
        //     $scope.account_name = data.account_name;
        //     $scope.account=data;
        //     $scope.CashAccountIDText =  data.accountid,
        //         $scope.selectedAccountList.push({
        //             account_name: data.account_name,
        //             CashAccountIDText: data.accountid,
        //         });
        //
        // };
        $scope.saveOrUpdatePayment= function () {
            if (angular.isUndefined($scope.paymentmethodNameText) || $scope.paymentmethodNameText == '' ) {
                Notification.warning({message: 'payment Name can not be empty', positionX: 'center', delay: 2000});
            }
            else if (angular.isUndefined($scope.paymentmethodTypeText) || $scope.paymentmethodTypeText == '' ) {
                Notification.warning({message: 'payment Method Type can not be empty', positionX: 'center', delay: 2000});
            }

            else {
                $scope.isDisabled= true;
                $timeout(function(){
                    $scope.isDisabled= false;
                }, 3000)
                var savePaymentMethodDetails;
                savePaymentMethodDetails = {
                    paymentmethodId: $scope.paymentmethodId,
                    paymentmethodName: $scope.paymentmethodNameText,
                    paymentmethodDescription: $scope.paymentmethodDescriptionText,
                    paymentmethodType: $scope.paymentmethodTypeText,
                    accountMaster: $scope.account,
                    validateVoucher:$scope.validateVoucher,
                    defaultType:$scope.defaultType,
                    status:$scope.paymentMethodText
                };
                $http.post('/pos' + '/savePaymentMethod', angular.toJson(savePaymentMethodDetails, "Create")).then(function (response, status, headers, config) {
                    var data = response.data;
                    if(data==""){
                        Notification.success({message: 'Already exists', positionX: 'center', delay: 2000});
                    }
                    else {
                        $scope.removePaymentMethodDetails();
                        $scope.getPaymentMethodList();
                        $("#add_new_PaymentMethod_modal").modal('hide');
                        if (!angular.isUndefined(data) && data !== null) {
                            $scope.paymentMethodSearchText = "";
                        }
                        Notification.success({
                            message: 'Payment Method Created  successfully',
                            positionX: 'center',
                            delay: 2000
                        });
                    }
                },function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                });

            };
        };
        $scope.editPayment = function(data) {
            $scope.setupobj = data;
            $scope.paymentmethodId = data.paymentmethodId;
            $scope.paymentmethodNameText = data.paymentmethodName;
            $scope.paymentmethodDescriptionText=data.paymentmethodDescription;
            $scope.paymentmethodTypeText=data.paymentmethodType;
            $scope.paymentMethodText=data.status;
            if(data.accountMaster!=null) {
                $scope.account_name = data.accountMaster.account_name;
                $scope.account=data.accountMaster;
            }
            else {
                $scope.account_name="";
            }
            if(data.defaultType=='true'){
                $scope.defaultType=true;
            }
            else {
                $scope.defaultType=false;
            }
            if(data.validateVoucher=='true'){
                $scope.validateVoucher=true;
            }
            else {
                $scope.validateVoucher=false;
            }
            $('#payment-title').text("Edit Payment Method");
            $("#add_new_PaymentMethod_modal").modal('show');
        },function (error) {
            Notification.error({message: 'Something went wrong, please try again',positionX: 'center',delay: 2000});

        };
        $scope.deletePayment = function (data) {
            bootbox.confirm({
                title: "Alert",
                message: "Do you want to Continue ?",
                buttons: {
                    confirm: {
                        label: 'OK'
                    },
                    cancel: {
                        label: 'Cancel'
                    }
                },
                callback: function (result) {
                    //  alert("delete");
                    if(result == true){
                        $scope.paymentmethodId = data.paymentmethodId;
                        $scope.paymentmethodNameText=data.paymentmethodName;
                        $scope.paymentmethodDescriptionText=data.paymentmethodDescription;
                        $scope.CashAccountIDText=data.accountMaster;
                        $scope.paymentMethodText=data.status;
                        var deleteDetails = {
                            paymentmethodId : $scope.paymentmethodId,
                            paymentmethodName: $scope.paymentmethodNameText,
                            paymentmethodDescription : $scope.paymentmethodDescriptionText,
                            accountMaster: $scope.account
                        };
                        $http.post('/pos'  + '/deletePaymentMethod', angular.toJson(deleteDetails, "Create")).then(function (response, status, headers, config) {
                            var data = response.data;
                            if(data=="") {
                                Notification.success({
                                    message: 'It Is Already In Use Cant be Deleted',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            }else {
                                $scope.getPaymentMethodList();
                                if($scope.paymentMethodText=="InActive") {
                                    $scope.getPaymentMethodList("","InActive");
                                    Notification.success({
                                        message: 'Status has been changed to Active',
                                        positionX: 'center',
                                        delay: 2000
                                    });
                                }
                                else{
                                    $scope.getPaymentMethodList("","");
                                    Notification.success({
                                        message: 'Status has been changed to InActive',
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
                    }

                }
            });
        };

    });
