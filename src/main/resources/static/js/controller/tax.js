
app.controller('TaxCtrl',
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
        $scope.inactiveStatus="Active"
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

            $scope.getTaxList();
        };
        $scope.getTaxList = function (page) {
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
            if (angular.isUndefined($scope.taxsearchText)) {
                $scope.taxsearchText = "";
            }
            $http.post('/pos' + "/getPaginatedTaxList?&type="+ $scope.inactiveStatus+ '&searchText=' + $scope.taxsearchText, angular.toJson(paginationDetails)).then(function (response) {
                var data = response.data;
                $scope.taxList = data.list;
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
        $scope.getTaxList();
        $scope.getTaxTypeList = function (type) {
            $scope.selectedTaxGroup = "";
            $http.post('/pos' + '/getTaxTypeList?type=' + $scope.selectedTaxGroup).then(function (response) {
                var data = response.data;
                $scope.taxTypeList = angular.copy(data);
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        }
        $scope.getTaxTypeList();
        $scope.saveTax = function () {
            var saveTaxDetails;
            var taxID;
            if(!!$scope.taxEntity)
                taxID = $scope.taxEntity.taxId;
            saveTaxDetails = {
                //for edit
                taxId:$scope.taxId,
                taxTypeName: $scope.selectedTaxType,
                taxGroup:$scope.selectedTaxGroup,
                taxCode: $scope.TaxCodeText,
                taxName: $scope.TaxNameText,
                tax_Per: parseFloat(parseFloat($scope.TaxPercText).toFixed(2)),
                taxDescription: $scope.DescriptionText,
                taxStatus: $scope.selectedStatusText,

            };
            $http.post('/pos' + '/saveTax', angular.toJson(saveTaxDetails, "Create")).then(function (response) {
                var data = response.data;
                if(data==false){
                    Notification.error({
                        message: 'Tax already Exists',
                        positionX: 'center',
                        delay: 2000
                    });
                }else {
                    $("#add_New_Tax_modal").modal('hide');
                    $scope.getTaxList("");
                    if (!angular.isUndefined(data) && data !== null) {
                        Notification.success({message: 'Tax added successfully', positionX: 'center', delay: 2000});
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

        $scope.addTax = function () {
            $scope.taxId="";
            $scope.selectedTaxType = "";
            $scope.selectedTaxClass ="";
            $scope.selectedLinkedAccountIDText = "";
            $scope.selectedTaxGroup = "";
            $scope.TaxCodeText = "";
            $scope.TaxNameText = "";
            $scope.TaxPercText = "";
            $scope.DescriptionText = "";
            $scope.selectedStatusText = "Active";
            $scope.accountCodeText = "";
            $scope.effectiveDate = "";
            $scope.expiryDate = "";
            $scope.taxEntity = null;
            $scope.title = "Add Tax";
            $scope.today();
            $('#add_New_Tax_modal').modal('show');
        }


        $scope.editTax = function(tax){
            $http.post('/pos' + '/editTax?taxName='+tax.taxName+'&groupName='+tax.taxGroup).then(function (response) {
                var data = response.data;
            // $scope.taxEntity = tax;
            $scope.title = "Edit Tax";
            // $scope.selectedTaxClass = tax.tcid.taxClassId;
            $scope.selectedTaxGroup = data.taxGroup;
            $scope.taxId=data.taxId;
            $scope.selectedStatusText = data.taxStatus;
            $scope.selectedTaxType = data.taxTypeName;
            // $scope.effectiveDate = tax.effectiveDate;
            // $scope.expiryDate = tax.expiryDate;
            $scope.TaxCodeText =  data.taxCode;
            // $scope.accountCodeText = tax.taxAccountCode;
            $scope.TaxNameText = data.taxName;
            $scope.TaxPercText =data.tax_Per;
            $scope.DescriptionText =data.taxDescription;
            $('#add_New_Tax_modal').modal('show');

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            });
        }

        $scope.getTaxList("");
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