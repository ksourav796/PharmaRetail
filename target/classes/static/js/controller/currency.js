/**
 * Created by azgar.h on 7/1/2017.
 */
app.controller('currencyCtrl',
    function ($scope, $http, $location, $filter, Notification, ngTableParams, $timeout) {

        // body...\
        $scope.userRights = [];
        $scope.operation = 'Create';

        $scope.today = new Date();
        $scope.firstPage = true;
        $scope.lastPage = false;
        $scope.pageNo = 0;
        $scope.prevPage = false;
        $scope.isPrev = false;
        $scope.isNext = false;
        $scope.inactiveStatus = "Active";
        $scope.removeCurrencyDetails = function () {
            $scope.currencyId="";
            $scope.CurrencyWordText = "";
            $scope.CurrencyCodeText = "";
            $scope.CurrencysymbolText = "";
            $scope.CurrencyDescriptionText = "";
            $scope.CurrencysymbolplaceText = "";
            $scope.AccountCodeText = "";
            $scope.operation = 'Create';
        };

        $scope.clicked = false;
        $scope.ButtonStatus = "InActive";
        $scope.inactiveCurrency = function (val) {
            if ($scope.clicked == false) {
                $scope.inactiveStatus = "InActive";
                $scope.ButtonStatus = "Active";
                var page = "Page";
            }
            else {
                $scope.inactiveStatus = "Active";
                $scope.ButtonStatus = "InActive";
                var page = "";
            }
            $scope.clicked = !$scope.clicked;
            $scope.getCurrencyList();


        };

        $scope.getCurrencyList = function (page) {
            switch (page) {
                case 'firstPage':
                    $scope.firstPage = true;
                    $scope.lastPage = false;
                    $scope.isNext = false;
                    $scope.isPrev = false;
                    $scope.pageNo = 0;
                    break;
                case 'lastPage':
                    $scope.lastPage = true;
                    $scope.firstPage = false;
                    $scope.isNext = false;
                    $scope.isPrev = false;
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
            var paginationDetails;
            paginationDetails = {
                firstPage: $scope.firstPage,
                lastPage: $scope.lastPage,
                pageNo: $scope.pageNo,
                prevPage: $scope.prevPage,
                prevPage: $scope.isPrev,
                nextPage: $scope.isNext
            }
            if (angular.isUndefined($scope.currencySearchText)) {
                $scope.currencySearchText = "";
            }
            $http.post('/pos' + "/getPaginatedCurrencyList?&type=" + $scope.inactiveStatus+ '&searchText=' + $scope.currencySearchText, angular.toJson(paginationDetails)).then(function (response) {
                var data = response.data;
                console.log(data);
                var i = 0;
                $scope.currencyList = data.list;
                $scope.first = data.firstPage;
                $scope.last = data.lastPage;
                $scope.prev = data.prevPage;
                $scope.next = data.nextPage;
                $scope.pageNo = data.pageNo;
                $scope.listStatus = true;
                // angular.forEach($scope.currencyList, function (value, key) {
                //     if (value.currencyCode.toUpperCase() == type.toUpperCase()) {
                //         if (value.status == 'InActive') {
                //             i++;
                //         }
                //     }
                // })
                // if (i > 0) {
                //     Notification.warning({
                //         message: 'currency Became InActive',
                //         positionX: 'center',
                //         delay: 2000
                //     });
                // }
                $scope.removeCurrencyDetails();

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.getCurrencyList();
        $scope.addNewCurrency = function () {
            $scope.currencyId = "";
            $scope.CurrencyWordText = "";
            $scope.CurrencyCodeText = "";
            $scope.CurrencysymbolText = "";
            $scope.CurrencyDescriptionText = "";
            $scope.CurrencysymbolplaceText = "";
            $scope.AccountCodeText = "";
            $scope.currencyStatusText = "Active";
            $scope.removeCurrencyDetails();
            $("#title").text("Add Currency");
            $("#submit").text("Update");
            $("#add_new_Currency_modal").modal('show');
        };
        $scope.populateCurrencyData = function () {
            var data = {
                CurrencyCode: $scope.CurrencyCode,
                CurrencyWord: $scope.CurrencyWord,
                AccountCode: $scope.AccountCode,
                CurrencyDescription: $scope.CurrencyDescription,
                Currencysymbol: $scope.Currencysymbol
            };

            console.log(data);
            return data;
        };

        $scope.saveNewCurrency = function () {
            if (angular.isUndefined($scope.CurrencyCodeText) || $scope.CurrencyCodeText == '') {
                Notification.warning({message: 'Currency code can not be Empty', positionX: 'center', delay: 2000});
            }
            else if (angular.isUndefined($scope.CurrencyWordText) || $scope.CurrencyWordText == '') {
                Notification.warning({message: 'Currency Word  can not be Empty', positionX: 'center', delay: 2000});
            }
            else if (angular.isUndefined($scope.CurrencysymbolText) || $scope.CurrencysymbolText == '') {
                Notification.warning({
                    message: 'Currency symbol Text can not be Empty',
                    positionX: 'center',
                    delay: 2000
                });
            }
            else {
                $scope.isDisabled = true;
                $timeout(function () {
                    $scope.isDisabled = false;
                }, 3000)
                var saveCurrencyDetails;
                saveCurrencyDetails = {
                    currencyId: $scope.currencyId,
                    currencyName: $scope.CurrencyWordText,
                    currencyCode: $scope.CurrencyCodeText,
                    currencySymbol: $scope.CurrencysymbolText,
                    currencyDescription: $scope.CurrencyDescriptionText,
                    currencySymbolPlace: $scope.CurrencysymbolplaceText,
                    AccountCode: $scope.AccountCodeText,
                    status: $scope.currencyStatusText
                };
            }
            $http.post('/pos' + '/saveCurrency', angular.toJson(saveCurrencyDetails, "Create")).then(function (response) {
                var data = response.data;
                if (data == "") {
                    Notification.error({message: 'Already exists', positionX: 'center', delay: 2000});
                }
                else {
                    $("#add_new_Currency_modal").modal('hide');
                    Notification.success({message: 'Currency Created  successfully', positionX: 'center', delay: 2000});
                    $scope.removeCurrencyDetails();
                    $scope.getCurrencyList();
                }
            }, function (error) {
            });
        };
        $scope.deleteCurrency = function (data) {
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
                    if (result == true) {
                        $http.post('/pos' + '/deleteCurrency?currencyName=', data.currencyName).then(function (response, status, headers, config) {
                            var data = response.data;
                            if ($scope.currencyStatusText == "InActive") {
                                $scope.getCurrencyList("", "InActive");
                                Notification.success({
                                    message: 'Status has been changed to Active',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            }
                            else {
                                $scope.getCurrencyList("", "");
                                Notification.success({
                                    message: 'Status has been changed to InActive',
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
                        });
                    }
                }
            });
        };

        $scope.editCurrencyPopulate = function (name) {
            $http.post('/pos' + '/editCurrency?currencyName=' + name).then(function (response) {
                var data = response.data;
                $scope.operation = 'Edit';
                $scope.currencyeditobj = data;
                $scope.currencyId = data.currencyId;
                $scope.CurrencyWordText = data.currencyName;
                $scope.CurrencyCodeText = data.currencyCode;
                $scope.CurrencysymbolText = data.currencySymbol;
                $scope.CurrencyDescriptionText = data.currencyDescription;
                $scope.CurrencysymbolplaceText = data.currencySymbolPlace;
                $scope.currencyStatusText = data.status;
                $("#add_new_Currency_modal").modal('show');
                $("#title").text("Edit Currency");
                $("#submit").text("Update");
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            });
        };
    })
