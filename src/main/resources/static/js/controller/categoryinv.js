/**
 * Created by prasad on 7/1/2017.
 */

app.controller('categoryinvCtrl',
    function ($scope, $http, $location, $filter, Notification){
        $scope.operation = 'Create';
        $scope.inactiveStatus="Active";
        $scope.firstPage = true;
        $scope.lastPage = false;
        $scope.pageNo = 0;
        $scope.prevPage = false;
        $scope.isPrev = false;
        $scope.isNext = false;
        $scope.removeCategoryDetails = function () {
            $scope.itemCategoryId = "";
            // $scope.itemCategoryCode = "";
            $scope.itemCategoryName = "";
            $scope.itemCategoryDesc="";
            $scope.isDisabled= false;
            $scope.operation = "";
        };
        $scope.clicked = false;
        $scope.ButtonStatus = "InActive";
        $scope.inactiveCategory = function() {
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
            $scope.getItemCategoryList();

        };

        $scope.getItemCategoryList = function (page) {
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
            if (angular.isUndefined($scope.searchText)) {
                $scope.searchText = "";
            }
            $http.post('/pos' + '/getPaginatedCategoryList?&type=' +$scope.inactiveStatus+ '&searchText=' + $scope.searchText, angular.toJson(paginationDetails)).then(function (response) {
                var data = response.data;
                console.log(data);
                $scope.itemCategoryDTOList = data.list;
                $scope.first = data.firstPage;
                $scope.last = data.lastPage;
                $scope.prev = data.prevPage;
                $scope.next = data.nextPage;
                $scope.pageNo = data.pageNo;
                var i=0;
                $scope.listStatus=true;

            },function (error) {
                Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
            })

        };






        $scope.addNewItemCategoryPopulate = function () {
            $(".loader").css("display", "block");
                $scope.itemCategoryId="";
                $scope.CategoryText="";
                $scope.CategoryNameText="";
                // $scope.CategoryCodeText="";
                $scope.CategoryDescriptionText="";
                $scope.defaultType=true;
                $scope.ctgryStatusText="Active";
                $('#modelName').text("Add ItemCategory");
            $("#submit").text("Save");
                $("#add_new_ItemCategory_modal").modal('show');
            },function (error) {
                Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
        };

        $scope.importPopup = function(){
            $("#import_category").modal('show');
        }

        $scope.saveItemImport = function(){
            $scope.isDisabled= true;
            var formElement = document.getElementById("itemDetails");
            var itemDetails = new FormData(formElement);
            $http.post('/pos' + '/categoryImportSave',itemDetails,
                { headers: {'Content-Type': undefined},
                    transformRequest: angular.identity,
                }).then(function (response) {
                    $("#import_category").modal('hide');
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


        $scope.editNewItemCategoryPopulate= function(data) {
            $scope.operation="Edit";
            $http.post('/pos' + '/editCategory?catName='+data.CategoryNameText).then(function (response) {
                var data = response.data;
                $scope.ItemObj = data;
                $scope.itemCategoryId = data.itemCategoryId;
                $scope.CategoryNameText=data.itemCategoryName;
                $scope.CategoryDescriptionText=data.itemCategoryDesc;
                $scope.ctgryStatusText=data.status;
                // $scope.StatusText=data.status;
                $('#modal-title').text("Edit Category");
                $("#submit").text("Update")
                $("#add_new_ItemCategory_modal").modal('show');
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            });
        }


        // $scope.editNewItemCategoryPopulate = function(name) {
        //     $http.post('/pos' + '/editCategory?catName='+name).then(function (response) {
        //         var data = response.data;
        //         $scope.itemObj=data;
        //          $scope.itemCategoryId = data.itemCategoryId;
        //         // $scope.CategoryCodeText=data.itemCategoryCode;
        //         $scope.CategoryNameText = data.itemCategoryName;
        //         $scope.CategoryDescriptionText=data.itemCategoryDesc;
        //         $scope.ctgryStatusText=data.status;
        //         $scope.defaultType=data.defaultType== "true";
        //         $scope.operation = 'Edit';
        //         $('#modelName').text("Edit ItemCategory");
        //         $("#submit").text("Update");
        //         $("#add_new_ItemCategory_modal").modal('show');
        //     }, function (error) {
        //         Notification.error({
        //             message: 'Something went wrong, please try again',
        //             positionX: 'center',
        //             delay: 2000
        //         });
        //     });
        // },function (error) {
        //     Notification.error({message: 'Something went wrong, please try again',positionX: 'center',delay: 2000});
        // };
        $scope.removeCategoryDetails();

        $scope.isEmpty = function(card){
            return card == '';
        }
        $scope.deleteCategory = function (data) {
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
                        $http.post('/pos' + '/deleteUom?uomName'+data.unitOfMeasurementName).then(function (response, status, headers, config) {
                            var data = response.data;
                            $scope.status=data.status;
                            if(data=="") {
                                $scope.getItemCategoryList();
                                Notification.success({
                                    message: 'It is Already in use Cannot be deleted',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            }else {
                                if($scope.UOMStatus=="InActive") {
                                    $scope.getItemCategoryList("InActive");
                                    Notification.success({
                                        message: 'Status has been changed to Active',
                                        positionX: 'center',
                                        delay: 2000
                                    });
                                }
                                else{
                                    $scope.getItemCategoryList("");
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

        $scope.saveNewItemCategory = function () {
            if ($scope.isEmpty($scope.CategoryNameText)) {
                Notification.warning({message: 'Category Name can not be empty', positionX: 'center', delay: 2000});
                return false;
            }
            else {
                $scope.isDisabled= true;
            var saveItemCategoryDetails;
            saveItemCategoryDetails = {
                 itemCategoryId:$scope.itemCategoryId,
                // itemCategoryCode: $scope.CategoryCodeText,
                itemCategoryName: $scope.CategoryNameText,
                itemCategoryDesc: $scope.CategoryDescriptionText,
                defaultType:$scope.defaultType,
                status:$scope.ctgryStatusText
            };
            $http.post('/pos' + '/saveCategory', angular.toJson(saveItemCategoryDetails, "Create")).then(function (response, status, headers, config) {
                var data = response.data;
                if(data ===""){
                    $scope.isDisabled= false;
                    Notification.error({
                        message: 'Item Category Already Created',
                        positionX: 'center',
                        delay: 2000
                    });
                }
                else {
                    $("#add_new_ItemCategory_modal").modal('hide');
                    Notification.success({
                        message: 'Item Category Created  successfully',
                        positionX: 'center',
                        delay: 2000
                    });
                    $scope.getItemCategoryList();
                    $scope.removeCategoryDetails();
                }

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
                $scope.isDisabled= false;

            });
        };
    }
        $scope.GetValue = function () {
            $scope.exportsVlues = $scope.exportsType;
        }

        $scope.getItemCategoryList();
});