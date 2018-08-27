/**
 * Created by prasad on 7/1/2017.
 */
app.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

app.controller('iteminvCtrl',
    function ($scope,$rootScope, $http, $location, $filter, Notification, ngTableParams, $timeout, $window) {
        window.$scope = $scope;
        $("#inventory").addClass('active');
        $('#inventory').siblings().removeClass('active');
        $('#sidebar-menu ul li ul li').removeClass('active');
        $('#sidebar-menu ul li ul').css('display','none');
        $scope.taxTypes = [
            {name: 'FullTax', value: 'FullTax'},
            {name: 'SimplifiedTax', value: 'SimplifiedTax'},
        ];
        $scope.notHide = "";
        $scope.inactiveStatus="Active";
        $scope.companyLogoPath = "";
        $scope.firstPage = true;
        $scope.lastPage = false;
        $scope.pageNo = 0;
        $scope.prevPage = false;
        $scope.isPrev = false;
        $scope.isNext = false;
        $scope.importPopup = function(){
            $("#import_modal").modal('show');
        }
        $scope.editItemPopulate = function (dataOBJ) {
            $scope.productionItem="";
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
                }
            );
            $http.post('/pos' + '/editItem?itemName=' + dataOBJ.itemName).then(function (response) {
                    var data = response.data;
                    console.log(data);
                    $scope.itemId = data.itemId;
                    $scope.itemCodeText = data.itemCode;
                    $scope.itemNameText = data.itemName;
                    $scope.itemDiscText = data.itemDesc;
                    $scope.itemCategoryId = data.itemCatName;
                    $scope.itemTypeId = data.itemTypeId;
                    $scope.certNoText=data.certificateno;
                    var itemProductStatus = data.productionItem;
                    if(itemProductStatus == "productionName"){
                    $scope.productionItem=true;}
                    $scope.unitOfMeasurementId = data.uom;
                    $scope.mscid = data.itemMSICName;
                    $scope.salesPricingText = data.salesPrice;
                    $scope.purchasePricingText = data.purchasePrice;
                    // $scope.reOrderLevelText=data.reOrderLevel;
                    $scope.inventoryMovementId = data.invmovementTypeName;
                    $scope.itemStatusText = data.itemStatus;
                    $scope.taxIPId = data.itemIPTaxName;
                    $scope.brandId = data.itemBrandName;
                    $scope.taxOPId = data.itemOPTaxName;
                    $scope.itemTypeId = data.itemTyName;
                    $scope.CESSText = data.cess;
                    $scope.inclusiveJSON = data.inclusiveJSON;
                    $scope.imageLocation = data.imageLocation;
                $scope.attributeConfiguratorDTOList=data.attributeConfiguratorDTOList;
                angular.forEach($scope.attributeConfiguratorDTOList, function (value, key) {
                    $scope.attributeConfiguratorDTOList[key].status = value.status == "true";
                })
                var inclusive = JSON.parse(data.inclusiveJSON);
                $scope.inTax = inclusive.purchases;
                $scope.outTax = inclusive.sales;
                    $("#add_new_item_modal").modal('show');
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                }
            )
        };
        $scope.clicked = false;
        $scope.ButtonStatus = "InActive";
        $scope.inactiveItem = function(val) {
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
            $scope.getItemList();

        };
        $scope.removeItemPopUpDetails=function(){
            $scope.itemId="";
            $scope.itemCodeText="";
            $scope.itemNameText="";
            $scope.itemDiscText="";
            $scope.purchasePricingText="";
            $scope.salesPricingText="";
            $scope.CESSText="";
            $scope.searchText="";
        };
        $scope.getItemList = function (page) {
            $(".loader").css("display", "block");
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
            if (angular.isUndefined($scope.itemSearchText)) {
                $scope.itemSearchText = "";
            }
            $http.post('/pos' + '/getPaginatedItemList?type=' + $scope.inactiveStatus+ '&searchText=' + $scope.itemSearchText, angular.toJson(paginationDetails)).then(function (response) {
                var data = response.data;
                console.log(data);
                $scope.itemList = data.list;
                $scope.first = data.firstPage;
                $scope.last = data.lastPage;
                $scope.prev = data.prevPage;
                $scope.next = data.nextPage;
                $scope.pageNo = data.pageNo;

                $("#selectItem").modal('show');
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.getItemList();
        $scope.next_wizard = function(){
            $("#sub_step1").removeClass("in active");
            $("#sub_step2").addClass("tab-pane fade in active");
        }
        $scope.back_wizard = function(){
            $("#sub_step2").removeClass("in active");
            $("#sub_step1").addClass("tab-pane fade in active");
        }

    });