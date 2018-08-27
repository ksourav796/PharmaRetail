/**
 * Created by prasad on 7/1/2017.
 */

app.controller('uominvCtrl',
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
            $scope.getUOMItemList();

        };

        $scope.getUOMItemList = function (page) {
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
            if (angular.isUndefined($scope.UOMSearchText)) {
                $scope.UOMSearchText = "";
            }
            $http.post('/pos' + '/getPaginatedUomList?&type=' +$scope.inactiveStatus+ '&searchText=' + $scope.UOMSearchText, angular.toJson(paginationDetails)).then(function (response) {
                var data = response.data;
                console.log(data);
                $scope.itemUOMList = data.list;
                $scope.first = data.firstPage;
                $scope.last = data.lastPage;
                $scope.prev = data.prevPage;
                $scope.next = data.nextPage;
                $scope.pageNo = data.pageNo;
                var i=0;
                $scope.listStatus=true;
                // angular.forEach($scope.itemUOMList,function (value,key) {
                //     if(value.unitOfMeasurementName.toUpperCase()==type.toUpperCase()){
                //         if(value.unitOfMeasurementStatus=='InActive')  {
                //             i++;
                //         }
                //     }
                // })
                // if(i>0) {
                //     Notification.error({
                //         message: 'Unit Of Measurement Name is InActive',
                //         positionX: 'center',
                //         delay: 2000
                //     });
                // }
            },function (error) {
                Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
            })

        };
        $scope.getUOMItemList();
        $scope.removeUOMDetails = function () {
            $scope.unitofmeasurementid="";
            $scope.UOMName = "";
            $scope.UOMDescription = "";
            $scope.UOMStatus="";
            $scope.uomStatusText="Active";

        };

        $scope.addNewUOMPopulate = function () {
            $scope.operation="Add";
            $scope.uomStatusText="Active";
                $('#uominv-title').text("Add UnitOfMeasurement");
                $("#submit").text("Save");
                $("#add_new_UOM_modal").modal('show');
            },function (error) {
                Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
        };


        $scope.editNewUOM= function(data) {
            $scope.operation="Edit";
            $http.post('/pos' + '/editUom?unitOfMeasurementName='+data.unitOfMeasurementName).then(function (response) {
                var data = response.data;
            $scope.uominvObj = data;
            $scope.unitofmeasurementid = data.unitOfMeasurementId;
            $scope.UOMName=data.unitOfMeasurementName;
            $scope.UOMDescription=data.unitOfMeasurementDescription;
            $scope.uomStatusText=data.unitOfMeasurementStatus;
            $scope.StatusText=data.status;
            $('#uominv-title').text("Edit UnitOfMeasurement");
            $("#submit").text("Update")
            $("#add_new_UOM_modal").modal('show');
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            });
        }

        $scope.saveNewUOM = function () {
            if ($scope.UOMName ==""||$scope.UOMName == undefined) {
                Notification.warning({message: 'UOM Name can not be empty', positionX: 'center', delay: 2000});
            }
            else {
                $scope.isDisabled= true;
                var saveUomDetails;
                saveUomDetails = {
                    unitOfMeasurementId: $scope.unitofmeasurementid,
                    unitOfMeasurementName: $scope.UOMName,
                    unitOfMeasurementDescription: $scope.UOMDescription,
                    unitOfMeasurementStatus: $scope.uomStatusText
                };
                $http.post('/pos' + '/saveUom', angular.toJson(saveUomDetails, "Create")).then(function (response, status, headers, config) {
                    var data = response.data;
                    if(data==""){
                        $scope.isDisabled= false;
                        Notification.error({
                            message: 'UOMName Already Created',
                            positionX: 'center',
                            delay: 2000
                        });
                    }
                    else {
                        $("#add_new_UOM_modal").modal('hide');
                        Notification.success({
                            message: 'UOMName  Created  successfully',
                            positionX: 'center',
                            delay: 2000
                        });
                        $scope.getUOMItemList();
                        $scope.removeUOMDetails();
                        $scope.isDisabled= false;
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
        $scope.deleteUOMItem = function (data) {
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
                            $scope.unitOfMeasurementStatus=data.unitOfMeasurementStatus;
                             if(data=="") {
                                    $scope.getUOMItemList();
                                Notification.success({
                                    message: 'It is Already in use Cannot be deleted',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            }else {
                                 if($scope.UOMStatus=="InActive") {
                                     $scope.getUOMItemList("InActive");
                                     Notification.success({
                                         message: 'Status has been changed to Active',
                                         positionX: 'center',
                                         delay: 2000
                                     });
                                 }
                                 else{
                                     $scope.getUOMItemList("");
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
