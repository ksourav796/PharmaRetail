/**
 * Created by prasad on 7/1/2017.
 */

app.controller('brandinvCtrl',
    function ($scope, $http, $location, $filter, Notification) {
        $scope.firstPage = true;
        $scope.lastPage = false;
        $scope.pageNo = 0;
        $scope.prevPage = false;
        $scope.isPrev = false;
        $scope.isNext = false;
        $scope.clicked = false;
        $scope.inactiveStatus = "Active";
        $scope.ButtonStatus = "InActive";
        $scope.inactiveUom = function(val) {
            if($scope.clicked == false){
                $scope.inactiveStatus = "InActive";
                $scope.ButtonStatus = "Active";
            }
            else{
                $scope.inactiveStatus = "Active";
                $scope.ButtonStatus = "InActive";
            }
            $scope.clicked = !$scope.clicked;
            $scope.getBrandItemList($scope.inactiveStatus,'firstPage');


        };

        $scope.getBrandItemList = function (page) {
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
                prevPage: $scope.isPrev,
                nextPage: $scope.isNext
            }
            if (angular.isUndefined($scope.brandSearchText)) {
                $scope.brandSearchText = "";
            }
            $http.post('/pos' + '/getBrandItemList?&type=' +  $scope.inactiveStatus + '&searchText=' + $scope.brandSearchText, angular.toJson(paginationDetails)).then(function (response) {
                var data = response.data;
                console.log(data);
                $scope.itemBrandList = data.list;
                $scope.first = data.firstPage;
                $scope.last = data.lastPage;
                $scope.prev = data.prevPage;
                $scope.next = data.nextPage;
                $scope.pageNo = data.pageNo;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };


        $scope.getBrandItemList();
        $scope.clicked = false;
        $scope.ButtonStatus = "InActive";
        $scope.inactiveBrand = function (val) {
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
            $scope.getBrandItemList();

        };


        $scope.importPopup = function(){
            $("#import_Brand").modal('show');
        }

        $scope.saveItemImport = function(){
            $scope.isDisabled= true;
            var formElement = document.getElementById("itemDetails");
            var itemDetails = new FormData(formElement);
            $http.post('/pos' + '/brandImportSave',itemDetails,
                { headers: {'Content-Type': undefined},
                    transformRequest: angular.identity,
                }).then(function (response) {
                    $("#import_Brand").modal('hide');
                $scope.getBrandItemList();
                    $scope.getItemCategoryList();
                    $scope.isDisabled= false;
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                    $scope.isDisabled= false;
                }
            )
        }
        $scope.selectModule = function (x) {
            console.log(x);
        }




        $scope.addNewBrandPopulate = function () {
            $scope.brandStatusText = "Active";
            $('#brand-title').text("Add Brand");
            $("#submit").text("Save");
            $("#add_new_brand_modal").modal('show');
        };


        $scope.saveNewBrand = function () {
            if ($scope.brandNameText == ''||angular.isUndefined($scope.brandNameText)) {
                Notification.error({message: 'Brand Name can not be empty', positionX: 'center', delay: 2000});
            } else {
                $scope.isDisabled = true;
                var saveBrandDetails;
                saveBrandDetails = {
                    brandId: $scope.brandId,
                    brandName: $scope.brandNameText,
                    brandDescription: $scope.brandDescriptionText,
                    status: $scope.brandStatusText
                };
                $http.post('/pos' + '/saveBrand', angular.toJson(saveBrandDetails, "Create")).then(function (response) {
                    var data = response.data;
                    if (data == "") {
                        $scope.isDisabled = false;
                        Notification.error({message: 'Already exists', positionX: 'center', delay: 2000});
                    }
                    else {
                        $scope.removeBrandDetails();
                        $scope.getBrandItemList();
                        $("#add_new_brand_modal").modal('hide');
                        if (!angular.isUndefined(data) && data !== null) {
                            $scope.brandName = "";
                        }
                        Notification.success({
                            message: 'Brand Created  successfully',
                            positionX: 'center',
                            delay: 2000
                        });
                        $scope.getBrandItemList();
                        $scope.removeUOMDetails();
                        $scope.isDisabled= false;
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
        };
        $scope.editbrandPopulate = function (data) {
            $http.post('/pos' + '/editBrand?brandName=' + data.brandName).then(function (response) {
                var data = response.data;
                $scope.setupobj = data;
                $scope.brandId = data.brandId;
                $scope.brandNameText = data.brandName;
                $scope.brandDescriptionText = data.brandDescription;
                $scope.brandStatusText = data.status;
                $scope.operation = 'Edit';
                $('#brand-title').text("Edit Brand");
                $("#submit").text("Update");
                $("#add_new_brand_modal").modal('show');
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            });
        }
        $scope.deletebrand = function (data) {
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
                        $http.post('/pos' + '/deleteBrand?brandName='+data.brandName).then(function (response, status, headers, config) {
                            var data = response.data;
                            $scope.status = data.status;
                            if (data == "") {
                                $scope.getBrandItemList();
                                Notification.error({
                                    message: 'It Is Already In Use Cant be Deleted',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            } else {
                                if ($scope.brandStatus == "InActive") {
                                    $scope.getBrandItemList("InActive");
                                    Notification.success({
                                        message: 'Status has been changed to Active',
                                        positionX: 'center',
                                        delay: 2000
                                    });
                                }
                                else {
                                    $scope.getBrandItemList("");
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