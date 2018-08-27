/**
 * Element Directive for adding new item
 * @param $http
 */
app.directive("addNewItemModal", function ($http, $timeout, $window, Notification) {
    return {
        restrict: 'E',
        templateUrl: "partials/addNewItemModal.html",
        link: function ($scope) {
            $scope.getHSNCodeList = function (type) {
                if (angular.isUndefined(type)) {
                    type = "";
                }
                $http.post('/pos' + '/getHsnList?type=' + type, {
                    params: {
                        "searchText": false
                    }
                }).then(function (response) {
                    var data = response.data;
                    $scope.itemMSICDTOList = data;
                    console.log($scope.itemMSICDTOList);
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                })
            };
            $scope.getUOMItemList = function (val, type) {
                $(".loader").css("display", "block");
                if (angular.isUndefined(type)) {
                    type = "";
                }

                $http.post('/pos' + '/getUomList?type=' + type, {}).then(function (response) {
                    var data = response.data;
                    console.log(data);
                    $scope.itemUOMTypeDTOList = data;
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                })
            };
            $scope.addNewItemPopulate = function () {
                $scope.getHSNCodeList();
                $scope.getUOMItemList();
                var requestUrl = '/pos' + '/getItemDetails';
                $http.post(requestUrl).then(function (response) {
                        var data = response.data;
                        $scope.itemCategoryDTOList = data.itemCategoryDTOList;
                        $scope.itemTypeDTOList = data.itemTypeDTOList;
                        $scope.itemUOMTypeDTOList = $scope.itemUOMTypeDTOList;
                        $scope.itemMSICDTOList = $scope.itemMSICDTOList;
                        $scope.itemBrandDTOList = data.itemBrandDTOList;
                        $scope.itemCountTypeDTOList = data.itemCountTypeDTOList;
                        $scope.itemIPTaxDTOList = data.itemIPTaxDTOList;
                        $scope.itemOPTaxDTOList = data.itemOPTaxDTOList;
                        $scope.salesPricingText = 0.0;
                        $scope.purchasePricingText = 0.0;
                        $scope.CESSText = 0.0;
                        $scope.itemCodeText = "";
                        $scope.itemNameText = "";
                        $scope.itemDiscText = "";
                        $scope.productionItem = "";
                        // $scope.reOrderLevelText = "";
                        $scope.showFirst = true;
                        $scope.showSecond = false;
                        $scope.isDisabled = false;
                        $scope.certNoText = "";
                        $("#add_new_item_modal").modal('show');
                    }, function (error) {
                        Notification.error({
                            message: 'Something went wrong, please try again',
                            positionX: 'center',
                            delay: 2000
                        });
                    }
                );
            };

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

            $scope.uploadFile = function () {
                var file = $scope.myFile;
                var uploadUrl = "/fileUpload";
                var fd = new FormData();
                fd.append('file', file);
                $http.post(uploadUrl, fd, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                }).then(function () {
                })
            };
            $scope.importPopup = function () {
                $("#import_modal").modal('show');
            }
            $scope.customer = 1;
            $scope.saveNewItem = function () {
                if (angular.isUndefined($scope.itemCodeText) || $scope.itemCodeText == '') {
                    Notification.warning({message: 'Item Code can not be empty', positionX: 'center', delay: 2000});
                }
                else if (angular.isUndefined($scope.itemNameText) || $scope.itemNameText == '') {
                    Notification.warning({message: 'Item Name can not be empty', positionX: 'center', delay: 2000});
                }
                else if (angular.isUndefined($scope.itemCategoryId) || $scope.itemCategoryId == ''|| $scope.itemCategoryId == null) {
                    Notification.warning({message: 'Category can not be empty', positionX: 'center', delay: 2000});
                }
                else if (angular.isUndefined($scope.itemTypeId) || $scope.itemTypeId == ''|| $scope.itemTypeId == null) {
                    Notification.warning({message: 'Item Type can not be empty', positionX: 'center', delay: 2000});
                }
                else if (angular.isUndefined($scope.unitOfMeasurementId) || $scope.unitOfMeasurementId   == ''|| $scope.unitOfMeasurementId == null) {
                    Notification.warning({message: 'UOM can not be empty', positionX: 'center', delay: 2000});
                }
                else if (angular.isUndefined($scope.mscid) || $scope.mscid == ''|| $scope.mscid == null) {
                    Notification.warning({message: 'HSN Code can not be empty', positionX: 'center', delay: 2000});
                }
                else if (angular.isUndefined($scope.taxIPId) || $scope.taxIPId == ''|| $scope.taxIPId == null) {
                    Notification.warning({message: 'Input tax can not be empty', positionX: 'center', delay: 2000});
                }
                else if (angular.isUndefined($scope.taxOPId) || $scope.taxOPId == ''|| $scope.taxOPId == null) {
                    Notification.warning({message: 'Output tax can not be empty', positionX: 'center', delay: 2000});
                }
                else if (angular.isUndefined($scope.brandId) || $scope.brandId == '' || $scope.brandId == null) {
                    Notification.warning({message: 'Brand can not be empty', positionX: 'center', delay: 2000});
                }
                else {
                    $scope.isDisabled = true;
                    var saveItemDetails;
                    saveItemDetails = {
                        itemCode: $scope.itemCodeText,
                        itemId: $scope.itemId,
                        itemName: $scope.itemNameText,
                        itemDesc: $scope.itemDiscText,
                        salesPrice: $scope.salesPricingText,
                        // reOrderLevel: $scope.reOrderLevelText,
                        purchasePrice: $scope.purchasePricingText,
                        itemStatus: $scope.itemStatusText,
                        itemCatName: $scope.itemCategoryId,
                        itemTyName: $scope.itemTypeId,
                        itemUOMTyName: $scope.unitOfMeasurementId,
                        invmovementTypeName: $scope.inventoryMovementId,
                        itemMSICName: $scope.mscid,
                        itemBrandName: $scope.brandId,
                        itemIPTaxName: $scope.taxIPId,
                        itemOPTaxName: $scope.taxOPId,
                        cess: $scope.CESSText,
                        productionItem: $scope.productionItem,
                        attributeConfiguratorDTOList: $scope.attributeConfiguratorDTOList,
                        inclusiveJSON: "{\"purchases\":" + $scope.inTax + ",\"sales\":" + $scope.outTax + "}",
                        itemImage: $scope.fileToUpload,
                        certificateno: $scope.certNoText
                    };
                }
                $http.post('/pos' + '/saveItem', angular.toJson(saveItemDetails, "Create")).then(function (response, status, headers, config) {
                    var data = response.data;
                    if (data=="") {
                        $scope.isDisabled = false;
                        Notification.error({
                            message: 'Item Already Exists',
                            positionX: 'center',
                            delay: 2000
                        });

                    }

                    else {
                        $("#add_new_item_modal").modal('hide');
                        $scope.getItemList();
                        $scope.removeItemPopUpDetails();
                        $scope.isDisabled = false;
                        // window.location.reload();
                        Notification.success({message: 'Item Created  successfully', positionX: 'center', delay: 2000});
                    }
                }, function (error) {
                    $scope.isDisabled = false;
                });

            };
            $scope.isEmpty = function (card) {
                return card == '';
            }
            $scope.additemcategory = function () {
                $(".loader").css("display", "block");
                $scope.itemCategoryId = "";
                $scope.CategoryNameText = "";
                $scope.CategoryDescriptionText = "";
                $scope.defaultType = true;
                $scope.ctgryStatusText = "Active";
                $('#project-title').text("Add ItemCategory");
                $("#submit").text("Save");
                $("#add_new_ItemCategory_modal").modal('show');
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            };
            $scope.saveNewItemCategory = function () {

                if ($scope.isEmpty($scope.CategoryNameText)) {
                    Notification.warning({message: 'Category Name can not be empty', positionX: 'center', delay: 2000});
                }

                else {
                    $scope.isDisabled = true;
                    var saveItemCategoryDetails;
                    saveItemCategoryDetails = {
                        itemCategoryId: $scope.itemCategoryId,
                        itemCategoryName: $scope.CategoryNameText,
                        itemCategoryDesc: $scope.CategoryDescriptionText,
                        defaultType: $scope.defaultType,
                        status: $scope.ctgryStatusText
                    };

                    $http.post('/pos' + '/saveCategory', angular.toJson(saveItemCategoryDetails, "Create")).then(function (response) {
                        var data = response.data;
                        if (data === "") {
                            $scope.isDisabled = false;
                            Notification.error({
                                message: 'Item Category Already Created',
                                positionX: 'center',
                                delay: 2000
                            });
                        }
                        else {
                            $scope.getItemCategoryList();
                            $scope.isDisabled = false;
                            $("#add_new_ItemCategory_modal").modal('hide');
                            Notification.success({
                                message: 'Item Category Created  successfully',
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
                ;
            }
            $scope.getItemCategoryList = function (type) {

                if (angular.isUndefined(type)) {
                    type = "";
                }
                $http.post('/pos' + '/getItemCategoryList?type=' + type).then(function (response) {
                    var data = response.data;
                    console.log(data);
                    $scope.itemCategoryDTOList = data;
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                })
            };
            $scope.getItemCategoryList();

            $scope.addNewBrand = function () {
                $(".loader").css("display", "block");
                $scope.brandId = "";
                $scope.brandNameText = "";
                $scope.brandDescriptionText = "";
                $scope.brandStatusText = "Active";
                $('#brand-title').text("Add Brand");
                $("#submit").text("Save");
                $("#add_new_brand_modal").modal('show');
                // $scope.searchText = $scope.itemSearchText;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            };
            $scope.saveNewBrand = function () {
                if ($scope.isEmpty($scope.brandNameText)) {
                    Notification.warning({message: 'Brand Name can not be empty', positionX: 'center', delay: 2000});
                } else {
                    $scope.isDisabled = true;
                    var saveBrandDetails;
                    saveBrandDetails = {
                        brandId: $scope.brandId,
                        brandName: $scope.brandNameText,
                        brandDescription: $scope.brandDescriptionText,
                        status: $scope.brandStatusText
                    };

                    $http.post('/pos' + '/saveBrand', angular.toJson(saveBrandDetails, "Create")).then(function (response, status, headers, config) {
                        var data = response.data;
                        if (data == "") {
                            $scope.isDisabled = false;
                            Notification.error({message: 'Already exists', positionX: 'center', delay: 2000});
                        }
                        else {
                            $("#add_new_brand_modal").modal('hide');
                            Notification.success({
                                message: 'Brand Created  successfully',
                                positionX: 'center',
                                delay: 2000
                            });
                            $scope.getBrandItemList();
                            $scope.isDisabled = false;

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
            $scope.getBrandItemList = function (type) {
                $(".loader").css("display", "block");
                if (angular.isUndefined(type)) {
                    type = "";
                }
                $http.post('/pos' + '/getBrandList?&type=' + type, {}).then(function (response) {
                    var data = response.data;
                    console.log(data);
                    $scope.itemBrandDTOList = data;
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                })

            };
            $scope.getBrandItemList();
            $scope.addNewUOM = function () {
                $(".loader").css("display", "block");
                $scope.unitOfMeasurementId = "";
                $scope.UOMName = "";
                $scope.UOMDescription = "";
                $scope.uomStatusText = "Active";
                $('#uominv-title').text("Add UnitOfMeasurement");
                $("#submit").text("Save");
                $("#add_new_UOM_modal").modal('show');
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            };
            $scope.saveNewUOM = function () {

                if ($scope.isEmpty($scope.UOMName)) {
                    Notification.warning({message: 'UOM Name can not be empty', positionX: 'center', delay: 2000});
                }
                else {

                    var saveUomDetails;
                    saveUomDetails = {
                        unitOfMeasurementId: $scope.unitOfMeasurementId,
                        unitOfMeasurementName: $scope.UOMName,
                        unitOfMeasurementDescription: $scope.UOMDescription,
                        unitOfMeasurementStatus: $scope.uomStatusText
                    };

                    $http.post('/pos' + '/saveUom', angular.toJson(saveUomDetails, "Create")).then(function (response, status, headers, config) {
                        var data = response.data;
                        if (data == "") {
                            $scope.isDisabled = false;
                            Notification.success({
                                message: 'UOMName Already Created',
                                positionX: 'center',
                                delay: 2000
                            });
                            $scope.isDisabled = false;
                        }
                        else {
                            $scope.getUOMItemList();
                            $scope.isDisabled = false;
                            $("#add_new_UOM_modal").modal('hide');
                            Notification.success({
                                message: 'UOMName  Created  successfully',
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

            }

            $scope.getUOMItemList();
            // $scope.getHSNCodeListFromFile = function () {
            //     var val=1;
            //     if (angular.isUndefined(val)) {
            //         val = "";
            //     }
            //     $http.get('/pos' + '/getHsnCodesFromFile?countryId=' + val).then(function successCallBack(response) {
            //         $scope.hsnCodes = response.data;
            //         $("#select_new_hsn_modal").modal('show');
            //     });
            // };
            $scope.addNewHsn = function () {
                $(".loader").css("display", "block");
                $scope.hsnStatusText = "Active";
                $scope.HSNDescriptionText = "";
                $('#HSN-title').text("Add HSN Code");
                $("#add_new_hsn_modal").modal('show');
            };
            $scope.selection = [];
            $scope.toggleSelection = function toggleSelection(hsnCode, hsnDesc) {
                var currentHsnCodeObject = {
                    "code": hsnCode,
                    "description": hsnDesc,
                    "status": "Active"
                };
                var status = true;
                for (var i = 0; i < $scope.selection.length; i++) {
                    if (parseFloat($scope.selection[i].code) == parseFloat(hsnCode)) {
                        $scope.selection.splice(i, 1);
                        status = false;
                        break;
                    }
                }
                if (status) {
                    $scope.selection.push(currentHsnCodeObject);
                }
                console.log($scope.selection);
            };
            // $scope.saveHSNCodes = function () {
            //     $scope.isDisabled= true;
            //     var url = "/pos/saveHsnCodes";
            //     $http.post(url, JSON.stringify($scope.selection)).then(function successCallBack(response) {
            //         $scope.selection = [];
            //         var val="";
            //         $scope.hsnCodes = response.data;
            //         $scope.isDisabled= false;
            //         $("#select_new_hsn_modal").modal('hide');
            //         $scope.getHSNCodeList(val);
            //     });
            // }

            $scope.saveHSNCode = function () {
                if (angular.isUndefined($scope.HSNCodeText) || $scope.HSNCodeText == '') {
                    Notification.warning({message: 'HSNCode  can not be empty', positionX: 'center', delay: 2000});
                }
                else {
                    var saveDetails;
                    saveDetails = {
                        mscid: $scope.mscid,
                        msiccomid: $scope.msiccomid,
                        msiccode: $scope.HSNCodeText,
                        descrip: $scope.HSNDescriptionText,
                        status: $scope.hsnStatusText
                    };
                    $http.post('/pos' + '/saveHsn', angular.toJson(saveDetails, "Create")).then(function (response) {
                        var data = response.data;
                        if (data == "") {
                            $scope.isDisabled = false;
                            Notification.success({message: 'Already exists', positionX: 'center', delay: 2000});
                        }
                        else {
                            $("#add_new_hsn_modal").modal('hide');
                            $("#select_new_hsn_modal").modal('hide');
                            $scope.getHSNCodeList();
                            $scope.isDisabled = false;
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
            $scope.next_wizard = function () {
                $("#add_new_item_modal  #sub_step1").removeClass("in active");
                $("#add_new_item_modal  #sub_step2").addClass("tab-pane fade in active");
            }
            $scope.back_wizard = function () {
                $("#add_new_item_modal  #sub_step2").removeClass("in active");
                $("#add_new_item_modal  #sub_step1").addClass("tab-pane fade in active");
            }

            $scope.getHSNCodeList();
            $scope.clicked = false;
            $scope.cartStatus = false;
            $scope.addRemoveCart = "Add To Cart";
            $scope.changeColor = function () {
                var color = $scope.clicked ? '#fff' : 'green';
                $("#colButn").css('background-color', color);
                if ($scope.clicked == false) {
                    Notification.info({message: 'Item Added To Cart ', positionX: 'center', delay: 2000});
                    $scope.cartStatus = true;
                    $scope.addRemoveCart = "Remove From Cart";
                }
                else {
                    Notification.info({message: 'Item Removed From Cart', positionX: 'center', delay: 2000});
                    $scope.cartStatus = false;
                    $scope.addRemoveCart = "Add To Cart";
                }
                $scope.clicked = !$scope.clicked;
            };

            /*
             *  Code for split the add new item model as show more or less using Angularjs instead of Jquery.
             */
            $scope.showFirst = true;
            $scope.showSecond = false;
            $scope.moreOrLessText = "More";
            $scope.showFirstSecond = function (val) {
                if (val == 'second') {
                    $scope.showSecond = true;
                    $scope.showFirst = false;
                    $scope.moreOrLessText = "Less";
                } else {
                    $scope.showSecond = false;
                    $scope.showFirst = true;
                    $scope.moreOrLessText = "More";
                }
            };

            $scope.blankImgSrc = function () {
                $('.image-preview').attr('src', '');
                $('#imageLoad').val('');
            }
        }


    }
});