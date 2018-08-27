
app.controller('TaxTypeCtrl',
    function ($scope, $http, $location, $filter, Notification) {
        $scope.statusList = [{name: 'Active', value: 'Active'},
            {name: 'InActive', value: 'InActive'}];
        $scope.firstPage = true;
        $scope.lastPage = false;
        $scope.pageNo = 0;
        $scope.prevPage = false;
        $scope.isPrev = false;
        $scope.isNext = false;
        $scope.clicked = false;
        $scope.ButtonStatus = "InActive";
        $scope.inactiveTax = function(val) {

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

            if (angular.isUndefined(val)) {
                val = "";
            }
            $http.post($scope.hiposServerURL + $scope.taxURL + "/getPaginatedTaxList?taxSearchText=" + val+"&type="+  $scope.inactiveStatus+"&pageValue="+ page).then(function (response) {
                var data = response.data;
                $scope.taxList = data.data;
            })
        };

        $scope.getTaxTypeList = function (page) {
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
            if (angular.isUndefined($scope.taxSearchtext)) {
                $scope.taxSearchtext = "";
            }
            $http.post('/pos' +  '/getPaginatedTaxTypeList?searchText=' + $scope.taxSearchtext , angular.toJson(paginationDetails)).then(function (response) {
                var data = response.data;
                $scope.taxTypeList = data.list;
                $scope.first = data.firstPage;
                $scope.last = data.lastPage;
                $scope.prev = data.prevPage;
                $scope.next = data.nextPage;
                $scope.pageNo = data.pageNo;
                if(!!$scope.searchText)
                    $scope.searchText = val;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.getTaxTypeList();
        $scope.saveTaxType = function(){
            if ($scope.TaxTypeText === ""||angular.isUndefined($scope.TaxTypeText)) {
                Notification.warning({message: 'Tax Type can not be empty', positionX: 'center', delay: 2000});
            }else {
                //Save Tax Type
                var saveTaxType = {};
                var taxTypeId;
                if (!!$scope.taxTypeEntity)
                    taxTypeId = $scope.taxTypeEntity.taxTypeId;

                saveTaxType = {
                    taxTypeId: taxTypeId,
                    taxName: $scope.TaxTypeText,
                    taxDescription: $scope.TaxTypeDescriptionText
                    // taxClassId:$scope.selectedTaxClass,
                    // taxGroupId:$scope.selectedTaxGroup
                };

                $http.post('/pos' + '/saveTaxType', angular.toJson(saveTaxType, "Create")).then(function (response, status, headers, config) {
                    var data = response.data;
                    if (data == false) {
                        Notification.error({
                            message: 'Tax Type already Exists',
                            positionX: 'center',
                            delay: 2000
                        });
                    } else {
                        $("#add_New_Tax_Type_modal").modal('hide');
                        if (!angular.isUndefined(data) && data !== null) {
                            Notification.success({
                                message: 'Tax Type added successfully',
                                positionX: 'center',
                                delay: 2000
                            });
                        }
                        $scope.getTaxTypeList();
                        $scope.removeTaxDetails();
                    }
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                });
            }
            $scope.taxTypeEntity = null;
            $scope.getTaxTypeList();
        };

        $scope.removeTaxDetails = function () {
            $scope.selectedTaxClass = "";
            $scope.selectedTaxGroup = "";
            $scope.TaxTypeText = "";
            $scope.TaxTypeDescriptionText = "";
        }
        $scope.addTaxType = function () {
            $scope.TaxTypeText = "";
            $scope.TaxTypeDescriptionText = "";
            // $scope.selectedTaxClass = "";
            // $scope.selectedTaxGroup = "";
            $scope.title = "Add Tax Type";
            $('#add_New_Tax_Type_modal').modal('show');
        };

        $scope.editTaxType = function(taxType){
            $http.post('/pos' + '/editTaxType?taxTypeName='+taxType.taxName).then(function (response) {
                var data = response.data;
            $scope.taxTypeEntity = taxType;
            $scope.title = "Edit Tax Type";
            // $scope.selectedTaxClass = taxType.taxClassId.taxClassId;
            // $scope.selectedTaxGroup = taxType.taxGroupId.taxGroupId;
            $scope.TaxTypeText = data.taxName;
            $scope.TaxTypeDescriptionText = data.taxDescription;
            $('#add_New_Tax_Type_modal').modal('show');
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            });
        }


        $scope.checkTaxCode = function(){
        angular.forEach($scope.taxList,function(value){
                if(value.taxCode === $scope.TaxCodeText) {
                    $scope.taxForm.TaxCode.isDuplicate = 'true';

                    $scope.TextCodeText = "";
                    return;
                }
            });
        };



        // $scope.activateTax = function (){
        //     $scope.selectedList = [];
        //     $("input:checked").each(function () {
        //         $scope.selectedList.push($(this).val());
        //     });
        //
        //     $http.post($scope.hiposServerURL + $scope.taxURL + '/activateTax?selectedList=', angular.toJson($scope.selectedList, "Create")).then(function (response) {
        //         var data = response.data;
        //
        //     }, function (error) {
        //         Notification.error({
        //             message: 'Something went wrong, please try again',
        //             positionX: 'center',
        //             delay: 2000
        //         });
        //     })
        //
        //
        // };
        // $scope.deActivateTax = function (){
        //     $scope.selectedList = [];
        //     $("input:checked").each(function () {
        //         $scope.selectedList.push($(this).val());
        //     });
        //
        //     $http.post($scope.hiposServerURL + $scope.taxURL + '/deActivateTax?selectedList=', angular.toJson($scope.selectedList, "Create")).then(function (response) {
        //         var data = response.data;
        //
        //     }, function (error) {
        //         Notification.error({
        //             message: 'Something went wrong, please try again',
        //             positionX: 'center',
        //             delay: 2000
        //         });
        //     })
        //
        // };
        $scope.deleteTaxType = function (data) {
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
                    if(result == true){
                        $scope.taxTypeId = data.taxTypeId;
                        $http.post('/pos'  + '/deleteTaxType?id='  +$scope.taxTypeId).then(function (response, status, headers, config) {
                            var data = response.data;
                            if(data=="") {
                                $scope.getTaxTypeList();
                                Notification.success({message: 'It Is Already In Use Cant be Deleted', positionX: 'center', delay: 2000});
                            }
                            else {
                                $scope.getTaxTypeList();
                                $("#add_New_Tax_Type_modal").modal('hide');
                                if (!angular.isUndefined(data) && data !== null) {
                                    $scope.taxTypeSearchText = "";
                                }
                                Notification.success({
                                    message: 'Successfully Deleted',
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

        // $scope.generateAccountCode = function (){
        //     var taxGrp = $scope.selectedTaxGroup;
        //     var typ = $scope.selectedTaxType;
        //
        //     if(taxGrp === "")
        //         taxGrp = 0;
        //     if(typ === ""||angular.isUndefined(typ))
        //         typ=0;
        //
        //     $http.get($scope.hiposServerURL + $scope.taxURL + '/getAccountCode?grp='+taxGrp+'&typ='+typ).then(function (response) {
        //         var data = response.data;
        //         $scope.accountCodeText = data;
        //     }, function (error) {
        //         Notification.error({
        //             message: 'Something went wrong, please try again',
        //             positionX: 'center',
        //             delay: 2000
        //         });
        //     })
        //
        //
        //     $http.post($scope.hiposServerURL + $scope.taxURL + '/getTaxType?type='+$scope.selectedTaxGroup).then(function (response) {
        //         var data = response.data;
        //         $scope.taxTypeList = angular.copy(data);
        //     }, function (error) {
        //         Notification.error({
        //             message: 'Something went wrong, please try again',
        //             positionX: 'center',
        //             delay: 2000
        //         });
        //     })
        //
        //     console.log("Generated Account Code"+$scope.accountCodeText);
        //
        // };



        // $scope.getTaxLinkedAccountList();




        /** Script for Date Picker **/

        $scope.today = function () {
            $scope.effectiveDate = new Date();
            $scope.expiryDate = new Date();
        };
        $scope.format = 'dd/MM/yyyy';
        $scope.open1 = function () {
            $scope.popup1.opened = true;
        };
        $scope.popup1 = {
            opened: false
        };
        $scope.open2 = function () {
            $scope.popup2.opened = true;
        };
        $scope.popup2 = {
            opened: false
        };

        /*** End Script**/

    });//end of controller

    $scope.GetValue = function () {
    $scope.exportsVlues = $scope.exportsType;
    }

app.filter('formatDate', function(dateFilter) {
    var formattedDate = '';
    return function(dt) {
        if(!!dt) {
            formattedDate = dateFilter(new Date(dt.split('-').join('/')), 'd/MM/yyyy');
            return formattedDate;
        }
    }
});