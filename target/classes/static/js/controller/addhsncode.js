app.controller('addhsncodeCtrl',
    function ($scope, $http, $location, $filter, Notification) {
        $scope.firstPage = true;
        $scope.lastPage = false;
        $scope.pageNo = 0;
        $scope.prevPage = false;
        $scope.isPrev = false;
        $scope.isNext = false;
        $scope.inactiveStatus = "Active";
        $scope.taxTypes = [
            {name: 'FullTax', value: 'FullTax'},
            {name: 'SimplifiedTax', value: 'SimplifiedTax'},
        ];
        $scope.removeHSNCodeDetails = function () {
            $scope.HSNCodeText = "";
            $scope.HSNDescriptionText = "";
            $scope.operation = "Create";
            $scope.isDisabled = false;
        };

        $scope.addNewHsnPopulate = function () {
            $scope.isDisabled = false;
            $scope.hsnStatusText = "Active";
            $(".loader").css("display", "block");
            $('#HSN-title').text("Add HSN Code");
            $("#add_new_hsn_modal").modal('show');
        };

        $scope.edithsnPopulate = function (data) {
            $http.post('/pos' + '/editHsn?msiccode=' + data.msiccode).then(function (response) {
                var data = response.data;
                $scope.setupobj = data;
                $scope.mscid = data.mscid;
                $scope.operation = "Edit";
                $scope.msiccomid = data.msiccomid;
                $scope.HSNCodeText = data.msiccode;
                $scope.HSNDescriptionText = data.descrip;
                $scope.hsnStatusText = data.status;
                $('#HSN-title').text("Edit HSN Code");
                $("#add_new_hsn_modal").modal('show');
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            });
        };

        $scope.delete = function (data) {
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
                        $http.post('/pos' + '/deleteHSN?hsnCode=' + data.msiccode).then(function (response, status, headers, config) {
                            var data = response.data;
                            if (data == "") {
                                $scope.getHSNCodeList();
                                Notification.error({
                                    message: 'It Is Already In Use Cant be Deleted',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            }
                            else {
                                $scope.mscid = undefined;
                                $scope.msiccomid = undefined;
                                $("#add_new_hsn_modal").modal('hide');
                                if (!angular.isUndefined(data) && data !== null) {
                                    $scope.ShippingMethodSearchText = "";
                                }
                                if ($scope.hsnStatusText == "InActive") {
                                    $scope.getHSNCodeList("", "InActive");
                                    Notification.success({
                                        message: 'Status has been changed to Active',
                                        positionX: 'center',
                                        delay: 2000
                                    });
                                }
                                else {
                                    $scope.getHSNCodeList("", "");
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

        $scope.saveHSNCode = function () {
            if (angular.isUndefined($scope.HSNCodeText) || $scope.HSNCodeText == '') {
                Notification.warning({message: 'HSNCode  can not be empty', positionX: 'center', delay: 2000});
            }
            else {
                $scope.isDisabled = true;
                var saveDetails;
                saveDetails = {
                    mscid: $scope.mscid,
                    msiccode: $scope.HSNCodeText,
                    descrip: $scope.HSNDescriptionText,
                    status: $scope.hsnStatusText
                };
                $http.post('/pos' + '/saveHsn', angular.toJson(saveDetails, "Create")).then(function (response) {
                    var data = response.data;
                    if (data == "") {
                        $scope.isDisabled = false;
                        Notification.error({message: 'Already exists', positionX: 'center', delay: 2000});
                    }
                    else {
                        $scope.removeHSNCodeDetails();
                        $scope.getHSNCodeList();
                        $("#add_new_hsn_modal").modal('hide');
                        if (!angular.isUndefined(data) && data !== null) {
                            $scope.hsnCodeSearchText = "";
                        }
                        Notification.success({
                            message: 'HSN Code Created  successfully',
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
                    $scope.isDisabled = false;
                });
            }
            ;
        };

        $scope.getHSNCodeList = function (page) {
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
            if (angular.isUndefined($scope.hsnCodeSearchText)) {
                $scope.hsnCodeSearchText = "";
            }
            $http.post('/pos' + '/getPaginatedHsnList?type='  + $scope.inactiveStatus + '&searchText=' + $scope.hsnCodeSearchText, angular.toJson(paginationDetails)).then(function (response) {
               var data =response;
                $scope.hsnCodeList = data.data.list;
                $scope.first = data.data.firstPage;
                $scope.last = data.data.lastPage;
                $scope.prev = data.data.prevPage;
                $scope.next = data.data.nextPage;
                $scope.pageNo = data.data.pageNo;
                var i = 0;
                $scope.listStatus = true;
                if (i > 0) {
                    Notification.error({
                        message: 'msic code is InActive',
                        positionX: 'center',
                        delay: 2000
                    });
                }
                // if ($scope.hsnCodeList == '' && $scope.hsnCodeSearchText != '') {
                //     Notification.error({message: 'No Results Found', positionX: 'center', delay: 2000});
                // }
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.getHSNCodeList();
        $scope.clicked = false;
        $scope.ButtonStatus = "InActive";
        $scope.inactiveHsn = function (val) {
            if ($scope.clicked == false) {
                $scope.inactiveStatus = "InActive";
                $scope.ButtonStatus = "Active";

            }
            else {
                $scope.inactiveStatus = "Active";
                $scope.ButtonStatus = "InActive";
            }
            $scope.clicked = !$scope.clicked;
            $scope.getHSNCodeList();

        };

    });
